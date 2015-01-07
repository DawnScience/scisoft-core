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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
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

	/**
	 * Make a dataset with metadata that is pointed by link
	 * @param link
	 * @param isAxisFortranOrder in most cases, this should be set to true
	 * @return dataset augmented with metadata
	 */
	@Override
	public ILazyDataset createAugmentedDataset(NodeLink link, final boolean isAxisFortranOrder) {
		return augmentDataset(link, isAxisFortranOrder);
	}

	public static ILazyDataset augmentDataset(NodeLink link, final boolean isAxisFortranOrder) {
		if (!link.isDestinationData()) {
			logger.warn("Cannot augment non-data node: {}", link);
			return null;
		}

		DataNode dNode = (DataNode) link.getDestination();
		if (dNode.isAugmented()) {
			logger.debug("Node has already been augmented: {}", link);
			return dNode.getDataset();
		}
		dNode.setAugmented();

		if (!dNode.isSupported()) {
			logger.warn("Node is not supported: {}", link);
			return null;
		}

		Attribute stringAttr = dNode.getAttribute(NX_CLASS);
		String nxClass = stringAttr != null ? stringAttr.getFirstElement() : null;
		if (!SDS.equals(nxClass)) {
			logger.warn("Data node does not have {} attribute: {}", NX_CLASS, link);
		}

		ILazyDataset cData = dNode.getDataset();
		if (cData == null || cData.getSize() == 0) {
			logger.warn("Chosen data {}, has zero size", dNode);
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

		GroupNode gNode = (GroupNode) link.getSource(); // before hunting for axes
		stringAttr = dNode.getAttribute(NX_SIGNAL);
		boolean isSignal;
		if (stringAttr != null) {
			isSignal = true;
			if (parseFirstInt(stringAttr) != 1) {
				logger.warn("Node has {} attribute but is not 1: {}", NX_SIGNAL, link);
				isSignal = false;
			}
		} else {
			isSignal = gNode.containsDataNode(DATA) && dNode == gNode.getDataNode(DATA);
		}

		// add errors
		ILazyDataset eData = cData.getError();
		String cName;
		String eName;

		if (eData == null) {
			cName = cData.getName();
			if (!NX_ERRORS.equals(cName)) { 
				eName = cName + NX_ERRORS_SUFFIX;
				if (!gNode.containsDataNode(eName) && !cName.equals(link.getName())) {
					eName = link.getName() + NX_ERRORS_SUFFIX;
				}
				if (gNode.containsDataNode(eName)) {
					eData = gNode.getDataNode(eName).getDataset();
					eData.setName(eName);
				} else if (isSignal && gNode.containsDataNode(NX_ERRORS)) { // fall back for signal dataset
					eData = gNode.getDataNode(NX_ERRORS).getDataset();
					eData.setName(NX_ERRORS);
				}
			}
			try {
				cData.setError(eData);
			} catch (RuntimeException e) {
				logger.warn("Could not set error ({}) for node as it was not broadcastable: {}", eData, link);
			}
		}

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
			stringAttr = d.getAttribute(NX_SIGNAL);
			if (stringAttr != null && parseFirstInt(stringAttr) == 1)
				continue;

			ILazyDataset a = d.getDataset();

			try {
				int[] ashape = a.getShape();

				AxisChoice choice = new AxisChoice(a);
				stringAttr = d.getAttribute(NX_NAME);
				if (stringAttr != null && stringAttr.isString())
					choice.setLongName(stringAttr.getFirstElement());

				cName = choice.getName();
				if (cName == null)
					cName = l.getName();
				// avoid using errors for axes
				if (cName.contains(NX_ERRORS)) {
					continue;
				}
				
				// add errors
				if (a.getError() == null) {
					eName = cName + NX_ERRORS_SUFFIX;
					if (!gNode.containsDataNode(eName) && !cName.equals(l.getName())) {
						eName = l.getName() + NX_ERRORS_SUFFIX;
					}
					if (gNode.containsDataNode(eName)) {
						eData = gNode.getDataNode(eName).getDataset();
						eData.setName(eName);
						try {
							a.setError(eData);
						} catch (RuntimeException e) {
							logger.warn("Could not set error ({}) for node as it was not broadcastable: {}", eData, l);
						}
					}
				}
				Attribute attr;
				attr = d.getAttribute(NX_PRIMARY);
				if (attr != null) {
					choice.setPrimary(parseFirstInt(attr));
				}

				int[] intAxis = null;
				Attribute attrLabel = null;
				if (isSignal) {
					String indAttr = l.getName() + NX_INDICES_SUFFIX;
					if (gNode.containsAttribute(indAttr)) {
						// deal with index mapping from @*_indices
						attr = gNode.getAttribute(indAttr);
						if (attr != null) {
							intAxis = parseIntArray(attr);
							for (int i = 0, imax = intAxis.length; i < imax; i++) {
								intAxis[i]--;
							}
							choice.setPrimary(1);
						}
					}
				}

				if (intAxis == null) {
					attr = d.getAttribute(NX_AXIS);
					attrLabel = d.getAttribute(NX_LABEL);
					if (attr != null) {
						intAxis = parseIntArray(attr);
						if (intAxis.length == ashape.length) {
							for (int i = 0, imax = intAxis.length; i < imax; i++) {
								int j = intAxis[i] - 1;
								int il = isAxisFortranOrder ? rank - 1 - j : j; // fix C (row-major) dimension
								intAxis[i] = il;
								int al = ashape[i];
								if (il < 0 || il >= rank || al != shape[il]) {
									intAxis = null;
									logger.warn("Axis attribute {} does not match shape", a.getName());
									break;
								}
							}
						} else {
							intAxis = null;
							logger.warn("Axis attribute {} does not match rank", a.getName());
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
					int j = parseFirstInt(attrLabel) - 1;
					choice.setAxisNumber(isAxisFortranOrder ? rank - 1 - j : j); // fix C (row-major) dimension
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
		if (axesAttr == null && isSignal) { // cope with @axes being in group
			axesAttr = gNode.getAttribute(NX_AXES);
			if (axesAttr != null)
				logger.warn("Found @{} tag in group (not in '{}' dataset)", NX_AXES, gNode.findLinkedNodeName(dNode));
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
					logger.warn("Referenced axis {} does not exist in tree node {}", s, link);
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
		}
		cData.addMetadata(amd);
		return cData;
	}

	/**
	 * Parse first argument as integer
	 * @param attr
	 * @return integer
	 */
	public static int parseFirstInt(Attribute attr) {
		if (attr.isString()) {
			Integer intPrimary = Integer.parseInt(attr.getFirstElement());
			return intPrimary;
		}
		IDataset attrd = attr.getValue();
		return attrd.getInt(0);
	}

	private static int[] parseIntArray(Attribute attr) {
		int[] intArray;
		if (attr.isString()) {
			String[] str = parseString(attr.getFirstElement());
			intArray = new int[str.length];
			for (int i = 0; i < str.length; i++) {
				intArray[i] = Integer.parseInt(str[i]);
			}
		} else {
			IntegerDataset id = (IntegerDataset) DatasetUtils.cast(attr.getValue(), Dataset.INT32);
			intArray = id.getData().clone();
		}
		return intArray;
	}

	private static String[] parseString(String s) {
		s = s.trim();
		if (s.startsWith("[")) { // strip opening and closing brackets
			s = s.substring(1, s.length() - 1);
		}

		return s.split("[:,]");
	}
}
