/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;

import uk.ac.diamond.scisoft.analysis.axis.AxisChoice;

/**
 * This class is a HDF5Loader with extra things associated by the nexus standard. Primarily if an ILazyDataset is
 * loaded, it will attempt to load the errors associated with the dataset.
 */
public class NexusHDF5Loader extends HDF5Loader {
	/**
	 * Attribute name for a NeXus class
	 */
	public static final String NX_CLASS = "NX_class";

	public static final String NX_AXES = "axes";
	public static final String NX_AXIS = "axis";
	public static final String NX_LABEL = "label";
	public static final String NX_PRIMARY = "primary";
	public static final String NX_SIGNAL = "signal";
	public static final String NX_DATA = "NXdata";
	public static final String NX_ERRORS = "errors";
	public static final String NX_ERRORS_SUFFIX = "_" + NX_ERRORS;
	public static final String NX_NAME = "long_name";
	public static final String NX_INDICES_SUFFIX = "_indices";
	public static final String SDS = "SDS";

	public static final String DATA = "data";
	
	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {

		DataHolder dh = super.loadFile(mon);
		if (dh == null)
			return null;

		// TODO Add in unit metadata information
		// Augment data as required
		// get all data with signal attribute
		try {
			// Parse Metadata.
			List<String> metaNames = new ArrayList<String>(dh.getMetadata().getMetaNames());
			Collections.sort(metaNames);
			for (int dataPos = 0; dataPos < metaNames.size(); dataPos++) {
				String metaKey = metaNames.get(dataPos);
				if (metaKey.contains("@signal")) {
					// find the data
					String key = metaKey.replace("@signal", "");
					ILazyDataset data = dh.getLazyDataset(key);

					// reparse all metadata to see associated metadata
					ArrayList<String> additionalMetadata = new ArrayList<String>(0);
					String[] result = key.split("/");
					String parentKey = "";
					for (int i = 0; i < result.length - 1; i++) {
						if (result[i].length() > 0) {
							parentKey += "/" + result[i];
						}
					}
					
					// Check Forward through list for matching metadata
					for (int axisPos = dataPos+1; axisPos < metaNames.size(); axisPos++) {
						String repassKey = metaNames.get(axisPos);
						if (repassKey.startsWith(parentKey)) {
							additionalMetadata.add(repassKey);
						} else {
							// End of entries so we can exit the loop
							break;
						}
					}
					
					// Check Backwards through list for matching metadata
					for (int axisPos = dataPos-1; axisPos > 0; axisPos--) {
						String repassKey = metaNames.get(axisPos);
						if (repassKey.startsWith(parentKey)) {
							additionalMetadata.add(repassKey);
						} else {
							// End of entries so we can exit the loop
							break;
						}
					}

					// get existing axis metadata
					List<AxesMetadata> list = data.getMetadata(AxesMetadata.class);
					AxesMetadataImpl axesMetadata;
					if (list == null || list.size() == 0) {
						axesMetadata = new AxesMetadataImpl(data.getRank());
						data.addMetadata(axesMetadata);
					} else {
						axesMetadata = (AxesMetadataImpl) list.get(0);
					}

					// look through the additional metadata for axis information
					// TODO Should take @primary into account when adding axes.
					// Good test file which fails if this is not done right is:
					// /dls/i12/data/2014/cm4963-4/rawdata/41781.nxs
					
					int[] dShape = data.getShape();
					for (String goodKey : additionalMetadata) {
						if (goodKey.endsWith("@axis")) {
							String axisName = goodKey.replace("@axis", "");
							ILazyDataset axisData = dh.getLazyDataset(axisName);
							
							// This string is a comma separated list of numbers, normally one number
							// but occasionally, two.
							String axes = (String) dh.getMetadata().getMetaValue(goodKey);
							String[] laxes = axes.split(",");

							for (String axis : laxes) {

								int axisDim = Integer.parseInt(axis) - 1; // zero-based
								ILazyDataset axisDataset = axisData.clone();
								int[] aShape = axisData.getShape();
								if (aShape.length == 1) {
									int[] shape = new int[dShape.length];
									int aLength = aShape[0];
									Arrays.fill(shape, 1);
									if (dShape[axisDim] != aLength) { // sanity check
										if (dShape[dShape.length - 1 - axisDim] == aLength) { // Fortran order!
											axisDim = dShape.length - 1 - axisDim;
										} else {
											logger.warn("Axis attribute of {} does not match dimension {} of signal dataset", goodKey, axisDim);
											axisDim = -1;
											for (int i = 0; i < shape.length ; i++) {
												if (dShape[i] == aLength) {
													axisDim = i;
													break;
												}
											}
										}
										if (axisDim < 0) {
											logger.error("Axis attribute of {} does not match any dimension of signal dataset", goodKey);
											break;
										}
									}
									shape[axisDim] = aLength;
									axisDataset.setShape(shape);
									axesMetadata.addAxis(axisDim, checkDatasetShapeSlicable(axisDataset, dShape));
								} else {
									if (axisDataset.getRank() == data.getRank()){
										axesMetadata.addAxis(axisDim, axisDataset);
									} else {
										//TODO this might need to be generic'd up a bit... try-catch incase anything troublesome happens
										try {
											int[] shape = new int[dShape.length];
											Arrays.fill(shape, 1);

											int[] overlap = Arrays.copyOfRange(dShape, axisDim-aShape.length+1, axisDim+1);

											if (Arrays.equals(aShape, overlap)) {

												for (int i = axisDim-aShape.length+1; i < axisDim+1; i++) shape[i] = dShape[i];
												axisDataset.setShape(shape);
											}

											axesMetadata.addAxis(axisDim, checkDatasetShapeSlicable(axisDataset, dShape));

										} catch (Exception e) {
											logger.warn("Trouble with multidimensional axis {} for {} dim of signal dataset", goodKey, axisDim);
										}

									}

								}
							}

							
						}
					}

				}
			}
		} catch (Exception e) {
			// Non fatal exception, as the file does not support NeXus for axis definitions
			logger.warn("Failed to augment data with axis metadata", e);
		}
		
		// We assign errors in ILazyDatasets read by nexus error
		// "standard"
		// TODO FIXME Also there is the attribute way of specifying and error.
		try {
			for (String name : dh.getNames()) {
				ILazyDataset data = dh.getLazyDataset(name);
				if (data == null)
					continue;
	
				ILazyDataset error = null;
				String errorName = name + NX_ERRORS_SUFFIX;
				if (dh.contains(errorName)) {
					error = dh.getLazyDataset(errorName);
				} else if (name.endsWith("/" + DATA)) {
					final String ep = name.substring(0, name.length() - DATA.length()) + NX_ERRORS;
					error = dh.getLazyDataset(ep);
				}
				if (error != null)
					//TODO need better slicable metadata clearing to stop stack overflow
					error.clearMetadata(AxesMetadata.class);
				data.setError(error);
			}
		} catch (Exception e) {
			// Non fatal exception, as the file does not support NeXus for error definitions
			logger.warn("Failed to augment data with error dataset", e);
		}
		
		return dh;
	}
	
	private ILazyDataset checkDatasetShapeSlicable(ILazyDataset lz, int[] originalShape) {
		
		boolean correct = true;
		
		int[] s = lz.getShape();
		
		if (s.length != originalShape.length) return null;
		
		for (int i = 0; i < originalShape.length; i++) {
			if (s[i] == 1) continue;
			if (s[i] != originalShape[i]) correct = false;
		}
		
		return correct ? lz : null;
	}

	/**
	 * Make a dataset with metadata that is pointed by link
	 * @param link
	 * @param isAxisFortranOrder in most cases, this should be set to true
	 * @return dataset augmented with metadata
	 */
	@Override
	public ILazyDataset createAugmentedDataset(NodeLink link, final boolean isAxisFortranOrder) {
		// two cases: axis and primary or axes
		// iterate through each child to find axes and primary attributes
		Node node = link.getDestination();
		GroupNode gNode = null;
		DataNode dNode = null;

		// see if chosen node is a NXdata class
		Attribute stringAttr = node.getAttribute(NX_CLASS);
		String nxClass = stringAttr != null ? stringAttr.getFirstElement() : null;
		if (nxClass == null || nxClass.equals(SDS)) {
			if (!(node instanceof DataNode))
				return null;

			dNode = (DataNode) node;
			if (!dNode.isSupported())
				return null;

			gNode = (GroupNode) link.getSource(); // before hunting for axes
		} else if (nxClass.equals(NX_DATA)) {
			assert node instanceof GroupNode;
			gNode = (GroupNode) node;
			// check if group has signal attribute (is this official?)
			if (gNode.containsAttribute(NX_SIGNAL)) {
				Attribute a = gNode.getAttribute(NX_SIGNAL);
				if (a.isString()) {
					String n = a.getFirstElement();
					if (gNode.containsDataNode(n)) {
						dNode = gNode.getDataNode(n);						
					} else {
						logger.warn("Signal attribute in group points to a missing dataset called {}", n);
					}
				} else {
					logger.warn("Signal attribute in group is not a string");
				}
			}

			if (dNode == null) {
				// find data (@signal=1)
				for (NodeLink l : gNode) {
					if (l.isDestinationData()) {
						dNode = (DataNode) l.getDestination();
						if (dNode.containsAttribute(NX_SIGNAL) && dNode.getAttribute(NX_SIGNAL).getFirstElement().equals("1")
								&& dNode.isSupported()) {
							link = l;
							break; // only one signal per NXdata item
						}
						dNode = null;
					}
				}
			}

			if (dNode == null && gNode.containsDataNode(DATA)) {
				// fallback to "data" when no signal attribute is found
				dNode = gNode.getDataNode(DATA);
			}
		}

		if (dNode == null || gNode == null) return null;
		ILazyDataset cData = dNode.getDataset(); // chosen dataset

		if (cData == null || cData.getSize() == 0) {
			logger.warn("Chosen data {}, has zero size", dNode);
			return null;
		}

		if (cData.getSize() == 1) {
			logger.warn("Chosen data {}, has only one value", dNode);
			return null;
		}

		// find possible @long_name
		stringAttr = dNode.getAttribute(NX_NAME);
		if (stringAttr != null && stringAttr.isString()) {
			String n = stringAttr.getFirstElement();
			if (n != null && n.length() > 0)
				cData.setName(n);
		}

		// Fix to http://jira.diamond.ac.uk/browse/DAWNSCI-333. We put the path in the meta
		// data in order to put a title containing the file in the plot.
		Tree tree = link.getTree();
		if (tree != null && tree instanceof TreeFile) {
			String name = ((TreeFile) tree).getFilename();
			if (name != null) {
				final Metadata meta = new Metadata();
				meta.setFilePath(name);
				cData.setMetadata(meta);
			// TODO Maybe	dNode.getAttributeNameIterator()
			}
		}

		// add errors
		ILazyDataset eData = null;
		String cName = cData.getName();
		String eName;
		if (!NX_ERRORS.equals(cName)) { 
			eName = cName + NX_ERRORS_SUFFIX;
			if (!gNode.containsDataNode(eName) && !cName.equals(link.getName())) {
				eName = link.getName() + NX_ERRORS_SUFFIX;
			}
			if (gNode.containsDataNode(eName)) {
				eData = gNode.getDataNode(eName).getDataset();
				eData.setName(eName);
			} else if (gNode.containsDataNode(NX_ERRORS)) { // fall back
				eData = gNode.getDataNode(NX_ERRORS).getDataset();
				eData.setName(NX_ERRORS);
			}
		}
		if (eData != null && !AbstractDataset.areShapesCompatible(cData.getShape(), eData.getShape(), -1)) {
			eData = null;
		}
		cData.setError(eData);

		// remove extraneous dimensions // FIXME???
//		cData.squeeze(true);
		
		// set up slices
		int[] shape = cData.getShape();
		int rank = shape.length;

		// scan children for SDS as possible axes (could be referenced by @axes)
		List<AxisChoice> choices = new ArrayList<AxisChoice>();
		for (NodeLink l : gNode) {
			if (!l.isDestinationData())
				continue;

			DataNode d = (DataNode) l.getDestination();
			if (!d.isSupported() || d.isString() || dNode == d)
				continue;
			if (d.containsAttribute(NX_SIGNAL) && d.getAttribute(NX_SIGNAL).getFirstElement().equals("1"))
				continue;

			ILazyDataset a = d.getDataset();

			try {
				// FIXME
				int[] s = a.getShape();
				s = AbstractDataset.squeezeShape(s, true);

				if (s.length == 0 || a.getSize() == 1)
					continue;  // don't make a 0D dataset as the data has already been squeezed

				a.squeeze(true);

				int[] ashape = a.getShape();

				AxisChoice choice = new AxisChoice(a);
				stringAttr = d.getAttribute(NX_NAME);
				if (stringAttr != null && stringAttr.isString())
					choice.setLongName(stringAttr.getFirstElement());

				// add errors
				cName = choice.getName();
				if (cName == null)
					cName = l.getName();
				eName = cName + NX_ERRORS_SUFFIX;
				if (!gNode.containsDataNode(eName) && !cName.equals(l.getName())) {
					eName = l.getName() + NX_ERRORS_SUFFIX;
				}
				if (gNode.containsDataNode(eName)) {
					eData = gNode.getDataNode(eName).getDataset();
					eData.setName(eName);
					if (s.length != 0) // don't make a 0D dataset
						eData.squeeze(true);

					if (AbstractDataset.areShapesCompatible(ashape, eData.getShape(), -1)) {
						a.setError(eData);
					}
				}

				Attribute attr;
				attr = d.getAttribute(NX_PRIMARY);
				if (attr != null) {
					if (attr.isString()) {
						Integer intPrimary = Integer.parseInt(attr.getFirstElement());
						choice.setPrimary(intPrimary);
					} else {
						IDataset attrd = attr.getValue();
						choice.setPrimary(attrd.getInt(0));
					}
				}

				int[] intAxis = null;
				Attribute attrLabel = null;
				String indAttr = l.getName() + NX_INDICES_SUFFIX;
				if (gNode.containsAttribute(indAttr)) {
					// deal with index mapping from @*_indices
					attr = gNode.getAttribute(indAttr);
					if (attr.isString()) {
						String[] str = parseString(attr.getFirstElement());
						intAxis = new int[str.length];
						for (int i = 0; i < str.length; i++) {
							intAxis[i] = Integer.parseInt(str[i]) - 1;
						}
						choice.setPrimary(1);
					}
				}

				if (intAxis == null) {
					attr = d.getAttribute(NX_AXIS);
					attrLabel = d.getAttribute(NX_LABEL);
					if (attr != null) {
						if (attr.isString()) {
							String[] str = attr.getFirstElement().split(",");
							if (str.length == ashape.length) {
								intAxis = new int[str.length];
								for (int i = 0; i < str.length; i++) {
									int j = Integer.parseInt(str[i]) - 1;
									intAxis[i] = isAxisFortranOrder ? j : rank - 1 - j; // fix C (row-major) dimension
								}
							}
						} else {
							Dataset attrd = DatasetUtils.convertToDataset(attr.getValue());
							if (attrd.getSize() == ashape.length) {
								intAxis = new int[attrd.getSize()];
								IndexIterator it = attrd.getIterator();
								int i = 0;
								while (it.hasNext()) {
									int j = (int) attrd.getElementLongAbs(it.index) - 1;
									intAxis[i++] = isAxisFortranOrder ? j : rank - 1 - j; // fix C (row-major) dimension
								}
							}
						}

						if (intAxis == null) {
							logger.warn("Axis attribute {} does not match rank", a.getName());
						} else {
							// check that @axis matches data dimensions
							for (int i = 0; i < intAxis.length; i++) {
								int al = ashape[i];
								int il = intAxis[i];
								if (il < 0 || il >= rank || al != shape[il]) {
									intAxis = null;
									logger.warn("Axis attribute {} does not match shape", a.getName());
									break;
								}
							}
						}
					}

				}

				if (intAxis == null) {
					// remedy bogus or missing @axis by simply pairing matching dimension
					// lengths to the signal dataset shape (this may be wrong as transposes in
					// common dimension lengths can occur)
					logger.warn("Creating index mapping from axis shape");
					Map<Integer, Integer> dims = new LinkedHashMap<Integer, Integer>();
					for (int i = 0; i < rank; i++) {
						dims.put(i, shape[i]);
					}
					intAxis = new int[ashape.length];
					for (int i = 0; i < intAxis.length; i++) {
						int al = ashape[i];
						intAxis[i] = -1;
						for (int k : dims.keySet()) {
							if (al == dims.get(k)) { // find first signal dimension length that matches
								intAxis[i] = k;
								dims.remove(k);
								break;
							}
						}
						if (intAxis[i] == -1)
							throw new IllegalArgumentException(
									"Axis dimension does not match any data dimension");
					}
				}

				choice.setIndexMapping(intAxis);
				if (attrLabel != null) {
					if (attrLabel.isString()) {
						int j = Integer.parseInt(attrLabel.getFirstElement()) - 1;
						choice.setAxisNumber(isAxisFortranOrder ? j : rank - 1 - j); // fix C (row-major) dimension
					} else {
						int j = attrLabel.getValue().getInt(0) - 1;
						choice.setAxisNumber(isAxisFortranOrder ? j : rank - 1 - j); // fix C (row-major) dimension
					}
				} else {
					choice.setAxisNumber(intAxis[intAxis.length-1]);
				}

				choices.add(choice);
			} catch (Exception e) {
				logger.warn("Axis attributes in {} are invalid - {}", a.getName(), e.getMessage());
				continue;
			}
		}

		List<String> aNames = new ArrayList<String>();
		Attribute axesAttr = dNode.getAttribute(NX_AXES);
		if (axesAttr == null) { // cope with @axes being in group
			axesAttr = gNode.getAttribute(NX_AXES);
			if (axesAttr != null)
				logger.warn("Found @{} tag in group (not in '{}' dataset)", new Object[] {NX_AXES, gNode.findLinkedNodeName(dNode)});
		}

		if (axesAttr != null) { // check axes attribute for list axes
			// check if axes referenced by data's @axes tag exists
			String[] names = parseString(axesAttr.getFirstElement());
			for (String s : names) {
				boolean flg = false;
				for (AxisChoice c : choices) {
					if (c.equals(s)) {
						flg = true;
						break;
					}
				}
				if (flg) {
					aNames.add(s);
				} else {
					logger.warn("Referenced axis {} does not exist in tree node {}", s, node);
					aNames.add(null);
				}
			}
		}

		List<ILazyDataset> axisList = new ArrayList<ILazyDataset>();
		AxesMetadata amd = new AxesMetadataImpl(rank);
		for (int i = 0; i < rank; i++) {
			int len = shape[i];
			for (AxisChoice c : choices) {
				ILazyDataset ad = c.getValues();
				if (c.getAxisNumber() == i) {
					// add if choice has been designated as for this dimension
					int p = c.getPrimary();
					if (p < axisList.size())
						axisList.add(p, ad);
					else
						axisList.add(ad);
//					aSel.addChoice(c, c.getPrimary());
				} else if (c.isDimensionUsed(i)) {
					// add if axis index mapping refers to this dimension
//					aSel.addChoice(c, 0);
					axisList.add(ad);
				} else if (aNames.contains(c.getName())) {
					// assume order of axes names FIXME
					// add if name is in list of axis names
					if (aNames.indexOf(c.getName()) == i && ArrayUtils.contains(ad.getShape(), len)) {
//						aSel.addChoice(c, 1);
						axisList.add(0, ad);
					}
				}
			}

			amd.setAxis(i, axisList.toArray(new ILazyDataset[0]));
			axisList.clear();
			
			// add in an automatically generated axis with top order so it appears after primary axes
//			Dataset axis = DatasetFactory.createRange(len, Dataset.INT32);
//			axis.setName(AbstractExplorer.DIM_PREFIX + (i + 1));
//			AxisChoice newChoice = new AxisChoice(axis);
//			newChoice.setAxisNumber(i);
//			aSel.addChoice(newChoice, aSel.getMaxOrder() + 1);
			
		}
		cData.addMetadata(amd);
		return cData;
	}

	private static String[] parseString(String s) {
		s = s.trim();
		if (s.startsWith("[")) { // strip opening and closing brackets
			s = s.substring(1, s.length() - 1);
		}

		return s.split("[:,]");
	}
}
