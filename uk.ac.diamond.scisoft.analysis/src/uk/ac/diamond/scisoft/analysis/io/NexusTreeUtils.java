/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.measure.IncommensurableException;
import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.measure.quantity.Length;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.diffraction.MatrixUtils;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.CompoundDataset;
import org.eclipse.january.dataset.CompoundDoubleDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.Stats;
import org.eclipse.january.dataset.StringDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.UnitMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.uom.NonSI;
import tec.units.indriya.format.SimpleUnitFormat;
import tec.units.indriya.unit.MetricPrefix;
import tec.units.indriya.unit.Units;
import uk.ac.diamond.scisoft.analysis.axis.AxisChoice;
import uk.ac.diamond.scisoft.analysis.crystallography.ReciprocalCell;
import uk.ac.diamond.scisoft.analysis.crystallography.ReciprocalCell.Ortho_Convention;
import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionSample;

@SuppressWarnings("deprecation")
public class NexusTreeUtils {

	private static final Logger logger = LoggerFactory.getLogger(NexusTreeUtils.class);

	private static final String TRANSFORMATIONS_TYPE = "transformation_type";
	private static final String TRANSFORMATIONS_DEPENDSON = "depends_on";
	private static final String TRANSFORMATIONS_VECTOR = "vector";
	private static final String TRANSFORMATIONS_ROOT = ".";
	private static final String TRANSFORMATIONS_OFFSET = "offset";
	private static final String TRANSFORMATIONS_OFFSET_UNITS = "offset_units";
	private static final String TRANSFOMATIONS_ROTATION = "rotation";
	private static final String TRANSFOMATIONS_TRANSLATION = "translation";


	private static final String DETECTOR_DISTANCE = "distance";
	private static final String DETECTOR_XPIXELSIZE = "x_pixel_size";
	private static final String DETECTOR_YPIXELSIZE = "y_pixel_size";
	private static final String DETECTOR_BEAMCENTERX = "beam_center_x";
	private static final String DETECTOR_BEAMCENTERY = "beam_center_y";
	private static final String DETECTOR_XPIXELNUMBER = "x_pixel_number";
//	private static final String DETECTOR_YPIXELNUMBER = "y_pixel_number";
	private static final String DETECTOR_PIXELMASK = "pixel_mask";
	private static final String DETECTOR_SATURATION = "saturation_value";
	private static final String DETECTOR_UNDERLOAD = "underload_value";

	private static final String DMOD_DATASIZE = "data_size";
	private static final String DMOD_DATAORIGIN = "data_origin";
	private static final String DMOD_MODULEOFFSET = "module_offset";
	private static final String DMOD_FASTPIXELDIRECTION = "fast_pixel_direction";
	private static final String DMOD_SLOWPIXELDIRECTION = "slow_pixel_direction";

	private static final String ATTR_DEFAULT = "default";

	static {
		SimpleUnitFormat.getInstance().alias(NonSI.ANGSTROM, "Angstrom");
		SimpleUnitFormat.getInstance().alias(NonSI.ANGSTROM, "angstrom");
		SimpleUnitFormat.getInstance().alias(MetricPrefix.KILO(NonSI.ELECTRON_VOLT), "keV");
		SimpleUnitFormat.getInstance().alias(NonSI.DEGREE_ANGLE, "degree");
	}

	private static final Unit<Length> MILLIMETRE = MetricPrefix.MILLI(Units.METRE);

	private NexusTreeUtils() {
	}

	/**
	 * Get lazy dataset (augmented with metadata) from first NXdata group
	 * @param tree
	 * @return lazy dataset 
	 * @throws NexusException if entry or data group is missing
	 */
	public static ILazyDataset getFirstNXdata(Tree tree) throws NexusException {
		GroupNode r = tree.getGroupNode();
		GroupNode e = getDefaultNXobject(r, "entry", NexusConstants.ENTRY);
		if (e == null) {
			throw new NexusException("Could not find default or first NXentry");
		}
		GroupNode d = getDefaultNXobject(e, "data", NexusConstants.DATA);
		if (d == null) {
			throw new NexusException("Could not find default or first NXdata");
		}

		return getAugmentedSignalDataset(d);
	}

	private static GroupNode getGroup(NodeLink l) {
		return l == null || !l.isDestinationGroup() ? null : (GroupNode) l.getDestination();
	}

	/**
	 * Get first group in group that is either directed by a default attribute, or
	 * whose name has the given prefix and is the given class. Otherwise get the
	 * first group of given class, directed by default attribute, or whose name has
	 * given prefix
	 * @param g group
	 * @param prefix prefix of name
	 * @param nxClass NeXus class
	 * @return group or null if not found
	 */
	public static GroupNode getDefaultNXobject(GroupNode g, String prefix, String nxClass) {
		Attribute a = g.getAttribute(ATTR_DEFAULT);
		String eName = null;
		GroupNode d = null;
		if (a != null) {
			eName = getFirstString(a);
			d = getGroup(g.getNodeLink(eName));
			if (!isNXClass(d, nxClass)) {
				d = null;
			}
		}
		if (d == null) {
			d = getGroup(findFirstNode(g, prefix, nxClass));
		}
		if (d == null) {
			d = getGroup(findFirstNode(g, null, nxClass));
		}
		if (d == null && eName != null) {
			d = g.getGroupNode(eName);
		}
		if (d == null) {
			d = getGroup(findFirstNode(g, prefix, null));
		}
		return d;
	}

	public static void augmentTree(Tree tree) {
		augmentNodeLink(tree instanceof TreeFile treeFile ? treeFile.getFilename() : null, Tree.ROOT, tree.getNodeLink(), true);
	}

	/**
	 * Augment a node with metadata that is pointed by link
	 * @param filePath
	 * @param groupPath
	 * @param link
	 * @param isAxisFortranOrder in most cases, this should be set to true
	 */
	public static void augmentNodeLink(String filePath, String groupPath, NodeLink link, final boolean isAxisFortranOrder) {
		if (link.isDestinationSymbolic()) {
			SymbolicNode sn = (SymbolicNode) link.getDestination();
			link = sn.getNodeLink();
			if (link == null) {
				logger.warn("Symbolic link {} from {} could not be resolved", link, filePath);
				return;
			}
			groupPath = groupPath + link.getName();
		}

		if (link.isDestinationGroup()) {
			GroupNode gn = (GroupNode) link.getDestination();
			if (parseNXdataAndAugment(groupPath, gn)) {
				return;
			}
			for (NodeLink l : gn) {
				augmentNodeLink(filePath, groupPath + l.getName(), l, isAxisFortranOrder);
			}
			return;
		}

		DataNode dNode = (DataNode) link.getDestination();
		if (dNode.isAugmented()) {
			logger.debug("Node has already been augmented: {}", link.getName());
			return;
		}
		dNode.setAugmented();

		if (!dNode.isSupported()) {
			logger.warn("Node is not supported: {}", link);
			return;
		}

		ILazyDataset cData = dNode.getDataset();
		if (cData == null || cData.getSize() == 0) {
			logger.info("Chosen data {}, has zero size", dNode);
			return;
		}

		// find possible long name
		String string = getFirstString(dNode.getAttribute(NexusConstants.DATA_NAME));
		if (string != null && string.length() > 0) {
			cData.setName(string);
		}

		// Fix to http://jira.diamond.ac.uk/browse/DAWNSCI-333. We put the path in the meta
		// data in order to put a title containing the file in the plot.
		if (filePath != null) {
			final IMetadata meta = new Metadata();
			meta.setFilePath(filePath);
			cData.setMetadata(meta);
			// TODO Maybe	dNode.getAttributeNameIterator()
		}

		GroupNode gNode = (GroupNode) link.getSource(); // before hunting for axes
		Attribute stringAttr = dNode.getAttribute(NexusConstants.DATA_SIGNAL);
		boolean isSignal = false;
		if (stringAttr != null) {
			isSignal = true;
			if (parseFirstInt(stringAttr) != 1) {
				logger.debug("Node has {} attribute but is not 1: {}", NexusConstants.DATA_SIGNAL, link);
				isSignal = false;
			}
		} else {
			String sName = getFirstString(dNode.getAttribute(NexusConstants.DATA_SIGNAL));
			if (sName != null) {
				if (gNode.containsDataNode(sName)) {
					isSignal = dNode == gNode.getDataNode(sName);
				} else {
					logger.debug("Given signal {} does not exist in group {}", sName, gNode);
				}
			}
			if (!isSignal) {
				isSignal = gNode.containsDataNode(NexusConstants.DATA_DATA) && dNode == gNode.getDataNode(NexusConstants.DATA_DATA);
			}
		}

		// add errors
		ILazyDataset eData = cData.getErrors();
		String cName;
		String eName;

		if (eData == null) {
			cName = cData.getName();
			if (!NexusConstants.DATA_ERRORS.equals(cName)) { 
				eName = cName + NexusConstants.DATA_ERRORS_SUFFIX;
				if (!gNode.containsDataNode(eName) && !cName.equals(link.getName())) {
					eName = link.getName() + NexusConstants.DATA_ERRORS_SUFFIX;
				}
				if (gNode.containsDataNode(eName)) {
					eData = gNode.getDataNode(eName).getDataset();
					if (eData == null) {
						logger.warn("Error dataset {} is empty", eName);
					} else {
						eData.setName(eName);
					}
				} else if (isSignal && gNode.containsDataNode(NexusConstants.DATA_ERRORS)) { // fall back for signal dataset
					eData = gNode.getDataNode(NexusConstants.DATA_ERRORS).getDataset();
					if (eData == null) {
						logger.warn("Error dataset {} is empty", eName);
					} else {
						eData.setName(NexusConstants.DATA_ERRORS);
					}
				}
			}
			try {
				cData.setErrors(eData);
			} catch (RuntimeException e) {
				logger.warn("Could not set error ({}) for node as it was not broadcastable: {}", eData, link);
			}
		}

		// set up slices
		int[] shape = cData.getShape();
		int rank = shape.length;
		if (rank == 0) {
			return;
		}

		// scan children for datasets as possible axes (could be referenced by @axes)
		List<AxisChoice> choices = new ArrayList<>();
		for (NodeLink l : gNode) {
			if (!l.isDestinationData())
				continue;

			DataNode d = (DataNode) l.getDestination();
			if (!d.isSupported() || d.isString() || dNode == d)
				continue;
			stringAttr = d.getAttribute(NexusConstants.DATA_SIGNAL);
			if (stringAttr != null && parseFirstInt(stringAttr) == 1)
				continue;

			ILazyDataset a = d.getDataset();

			if (a == null) {
				logger.warn("Dataset {} is empty", l.getName());
				continue;
			}
			try {
				int[] ashape = a.getShape();

				AxisChoice choice = new AxisChoice(a);
				String n = getFirstString(dNode.getAttribute(NexusConstants.DATA_NAME));
				if (n != null)
					choice.setLongName(n);

				cName = choice.getName();
				if (cName == null)
					cName = l.getName();
				// avoid using errors for axes
				if (cName.contains(NexusConstants.DATA_ERRORS)) {
					continue;
				}
				
				// add errors
				if (a.getErrors() == null) {
					eName = cName + NexusConstants.DATA_ERRORS_SUFFIX;
					if (!gNode.containsDataNode(eName) && !cName.equals(l.getName())) {
						eName = l.getName() + NexusConstants.DATA_ERRORS_SUFFIX;
					}
					if (gNode.containsDataNode(eName)) {
						eData = gNode.getDataNode(eName).getDataset();
						if (eData == null) {
							logger.warn("Error dataset {} is empty", eName);
						} else {
							eData.setName(eName);
						}
						try {
							a.setErrors(eData);
						} catch (RuntimeException e) {
							logger.warn("Could not set error ({}) for node as it was not broadcastable: {}", eData, l);
						}
					}
				}
				Attribute attr;
				attr = d.getAttribute(NexusConstants.DATA_PRIMARY);
				if (attr != null) {
					choice.setPrimary(parseFirstInt(attr));
				}

				int[] intAxis = null;
				if (isSignal) {
					String indAttr = l.getName() + NexusConstants.DATA_INDICES_SUFFIX;
					if (gNode.containsAttribute(indAttr)) {
						// deal with index mapping from @*_indices
						attr = gNode.getAttribute(indAttr);
						if (attr != null) {
							intAxis = parseIntArray(attr);
							choice.setPrimary(1);
						}
					}
				}

				if (intAxis == null) {
					attr = d.getAttribute(NexusConstants.DATA_AXIS);
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
									logger.debug("Axis attribute {} does not match shape {} != {}", a.getName(),
											Arrays.toString(a.getShape()), Arrays.toString(shape));
									break;
								}
							}
						} else {
							intAxis = null;
							logger.debug("Axis attribute {} does not match rank {} != {}", a.getName(),
									a.getRank(), shape.length);
						}
					}
				}

				if (intAxis == null) {
					// remedy bogus or missing @axis by simply pairing matching dimension
					// lengths to the signal dataset shape (this may be wrong as transposes in
					// common dimension lengths can occur)
					Map<Integer, Integer> dims = new LinkedHashMap<>();
					for (int i = 0; i < rank; i++) {
						dims.put(i, shape[i]);
					}
					intAxis = new int[ashape.length];
					for (int i = 0; i < intAxis.length; i++) {
						int al = ashape[i];
						intAxis[i] = -1;
						for (Entry<Integer, Integer> e : dims.entrySet()) {
							if (al == e.getValue()) { // find first signal dimension length that matches
								Integer k = e.getKey();
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
				if (intAxis.length > 0) {
					choice.setAxisNumber(intAxis[intAxis.length-1]);
				}
				choices.add(choice);
			} catch (Exception e) {
				logger.debug("Axis attributes in {} are invalid - {}", a.getName(), e.getMessage());
			}
		}

		List<String> aNames = new ArrayList<>();
		Attribute axesAttr = dNode.getAttribute(NexusConstants.DATA_AXES);
		if (axesAttr == null && isSignal) { // cope with @axes being in group
			axesAttr = gNode.getAttribute(NexusConstants.DATA_AXES);
			if (axesAttr != null)
				logger.trace("Found @{} tag in group (not in '{}' dataset)", NexusConstants.DATA_AXES, gNode.findLinkedNodeName(dNode));
		}

		if (axesAttr == null && choices.isEmpty()) {
			return;
		} else if (axesAttr != null) { // check axes attribute for list axes
			// check if axes referenced by data's @axes tag exists
			String[] names = parseStringArray(axesAttr);
			for (String s : names) {
				boolean flg = false;
				for (AxisChoice c : choices) {
					if (s.equals(c.getName())) {
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

		List<ILazyDataset> axisList = new ArrayList<>();
		AxesMetadata amd;
		try {
			amd = MetadataFactory.createMetadata(AxesMetadata.class, rank);
		} catch (MetadataException e) {
			logger.error("Problem creating metadata", e);
			return;
		}
		for (AxisChoice c : choices) {
			ILazyDataset ad = c.getValues();
			for (int i = 0; i < rank; i++) {
				int len = shape[i];
				if (c.getAxisNumber() == i) {
					// add if choice has been designated as for this dimension
					int p = c.getPrimary();
					if (p < axisList.size())
						axisList.add(p, ad);
					else
						axisList.add(ad);
				} else if (c.isDimensionUsed(i)) {
					// add if axis index mapping refers to this dimension
					axisList.add(ad);
				} else if (aNames.contains(c.getName())) {
					// assume order of axes names FIXME
					// add if name is in list of axis names
					if (aNames.indexOf(c.getName()) == i && ArrayUtils.contains(ad.getShape(), len)) {
						axisList.add(0, ad);
					}
				}
				if (!axisList.isEmpty()) {
					amd.setAxis(i, axisList.toArray(new ILazyDataset[0]));
					axisList.clear();
				}
			}
		}
		cData.addMetadata(amd);
	}

	public static ILazyDataset getAugmentedSignalDataset(GroupNode gn) {
		
		ILazyDataset is2014 = parseNXdataAndAugmentStrict(gn);
		
		if (is2014 != null) return is2014;
		
		return parseLegacyNXdataAndAugment(gn); 

	}

	/**
	 * Parse strict old NXdata class and augment with metadata
	 * Group must have 1 signal tagged dataset, axes datasets must be tagged, labelled
	 * from left, starting with 1.
	 * @param gn
	 * @return signal dataset if conforms to standard
	 */
	public static ILazyDataset parseLegacyNXdataAndAugment(GroupNode gn) {
		
		if (!isNXClass(gn, NexusConstants.DATA)) {
			logger.warn("'{}' must be an {} class", gn, NexusConstants.DATA);
			return null;
		}
		
		Iterator<String> it = gn.getNodeNameIterator();
		
		String signal = null;
		List<AxisChoice> choices = new ArrayList<>();
		
		while (it.hasNext()) {
			String name = it.next();
			DataNode dataNode = gn.getDataNode(name);
			if (dataNode != null) {
				Attribute attribute = dataNode.getAttribute(NexusConstants.DATA_SIGNAL);
				if (attribute != null) {
					if (parseFirstInt(attribute) == 1 && signal == null) {
						signal = name;
					} else {
						logger.warn("Bad signal tagging in {}", gn);
						return null;
					}
				} else {
					attribute = dataNode.getAttribute(NexusConstants.DATA_AXIS);
					if (attribute != null) {
						int[] intArray = parseIntArray(attribute);
						if (intArray != null) {
							AxisChoice choice = new AxisChoice(dataNode.getDataset());
							choice.setIndexMapping(intArray);
							if (gn.getAttribute(NexusConstants.DATA_PRIMARY) != null) {
								choice.setPrimary(1);
							}
							choices.add(choice);
						}
					}
				}
			}
		}
		
		if (signal == null) return null;
		
		ILazyDataset lz = gn.getDataNode(signal).getDataset().getSliceView();
		lz.clearMetadata(null);
		
		if (!choices.isEmpty()) {
			try {
				AxesMetadata axm = MetadataFactory.createMetadata(AxesMetadata.class, lz.getRank());
				for (AxisChoice choice : choices) {
					int[] m = choice.getIndexMapping();
					//only rank 1 or 2 axis for now...
					if (m.length == 2) {
						for (int i = 0 ; i < m.length;i++) m[i]--;
						try {
							//work out for which dimension the data varies the most
							//and set that as the main axis
							Dataset x = DatasetUtils.sliceAndConvertLazyDataset(choice.getValues());
							Dataset m0 = Stats.median(x, 1);
							Dataset m1 = Stats.median(x, 0);
							
							double max0 = m0.max(true).doubleValue();
							double min0 = m0.min(true).doubleValue();
							
							double max1 = m1.max(true).doubleValue();
							double min1 = m1.min(true).doubleValue();
							
							double p0 = max0-min0;
							double p1 = max1-min1;
							
							int index = p0 > p1 ? 0 : 1;
							if (lz.getShape()[index] == choice.getValues().getShape()[index] ) {
								axm.addAxis(index, choice.getValues().getSliceView(), m);
							}
							
						} catch (DatasetException e) {
							logger.error("TODO put description of error here", e);
						}
					} else if (m.length == 1) {
						int dm = m[0] - 1;
						if (lz.getShape()[dm] == choice.getValues().getSize() ) {
							axm.addAxis(dm, choice.getValues());
						}
					}
				}
				
				lz.addMetadata(axm);
				
			} catch (MetadataException e) {
				logger.error("Could not create Axes Metadata!", e);
			}
		}
		
		if (gn.containsDataNode(NexusConstants.DATA_ERRORS)) {
			ILazyDataset lze = gn.getDataNode(NexusConstants.DATA_ERRORS).getDataset();
			if (Arrays.equals(lz.getShape(), lze.getShape())) {
				lz.setErrors(lze);
			}
		}
		
		return lz;
	}

	/**
	 * Parse new style (2014) NXdata class and augment with metadata
	 * @param groupPath
	 * @param gn
	 * @return true if it conforms to standard
	 */
	public static boolean parseNXdataAndAugment(String groupPath, GroupNode gn) {
		if (!isNXClass(gn, NexusConstants.DATA)) {
			logger.debug("'{}' must be an {} class", gn, NexusConstants.DATA);
			return false;
		}

		String signal = getFirstString(gn.getAttribute(NexusConstants.DATA_SIGNAL));
		if (signal == null) {
			signal = NexusConstants.DATA_DATA;
		}
		if (!gn.containsDataNode(signal)) {
			return false;
		}

		DataNode dNode = gn.getDataNode(signal);
		ILazyDataset cData = dNode.getDataset();
		if (cData == null || cData.getSize() == 0) {
			logger.warn("Chosen data '{}', has zero size", signal);
			return false;
		}

		// find possible @long_name
		String string = getFirstString(dNode.getAttribute(NexusConstants.DATA_NAME));
		if (string != null && string.length() > 0) {
			cData.setName(string);
		}

		// set up slices
		int[] shape = cData.getShape();
		int rank = shape.length;

		List<String> namedAxes = new ArrayList<>();
		String[] tmp = getStringArray(gn.getAttribute(NexusConstants.DATA_AXES));
		if (tmp == null) {
			return false;
		}
		Collections.addAll(namedAxes, tmp);
		if (namedAxes.size() < rank) {
			logger.warn("Number of axes given in @axes is less than the signal rank ({} < {})", namedAxes.size(), rank);
		}

		List<ILazyDataset> axes = new ArrayList<>();
		for (String a : namedAxes) {
			if (NexusConstants.DATA_AXESEMPTY.equals(a)) {
				continue;
			}

			if (!gn.containsDataNode(a)) {
				logger.error("Axis '{}' is missing for '{}'", a, signal);
				return false;
			}
			try {
				addAxis(gn, a, rank, axes);
			} catch (IllegalArgumentException e) {
				logger.warn("Ignoring axis '{}' for '{}': {}", a, signal, e.getMessage());
			}
		}

		boolean foundSuffixedUncertainties = false;
		
		// Add other datasets that have _indices attributes too
		Iterator<String> it = gn.getAttributeNameIterator();
		while (it.hasNext()) {
			String aName = it.next();
			int i = aName.lastIndexOf(NexusConstants.DATA_INDICES_SUFFIX);
			if (i > 0) {
				String a = aName.substring(0, i);
				if (!namedAxes.contains(a)) {
					namedAxes.add(a);
					if (gn.containsDataNode(a)) {
						try {
							addAxis(gn, a, rank, axes);
						} catch (IllegalArgumentException e) {
							logger.warn("Ignoring axis '{}' for '{}': {}", a, signal, e.getMessage());
						}
					} else {
						logger.warn("An index '{}' attribute refers to a missing dataset in '{}': {}", aName, signal, a);
					}
				}
			}
			
			i = aName.lastIndexOf(NexusConstants.DATA_UNCERTAINTY_SUFFIX);
			if (i > 0) {
				String a = aName.substring(0, i);
				Attribute uncertAttr = gn.getAttribute(aName);
				if (gn.containsDataNode(a) && uncertAttr != null && gn.containsDataNode(uncertAttr.getFirstElement())) {
					DataNode d = gn.getDataNode(a);
					DataNode e = gn.getDataNode(uncertAttr.getFirstElement());
					if (Arrays.equals(d.getDataset().getShape(), e.getDataset().getShape())){
						d.getDataset().setErrors(e.getDataset());
						foundSuffixedUncertainties = true;
					} else {
						logger.warn("Dataset '{}' and error'{}' have incompatible shapes", aName, uncertAttr.getFirstElement());
					}
				} else {
					logger.warn("Could not add errors for '{}', despite uncertainty attribute", aName);
				}
			}

		}

		// Try adding any dataset named 'errors' to the dataset with the signal
		if (!foundSuffixedUncertainties) {
			
			// Get the first dataset marked as a signal. Do this by looping through the attributes again.
			it = gn.getAttributeNameIterator();
			String signalName = "";
			while (it.hasNext()) {
				String aName = it.next();
				if (NexusConstants.DATA_SIGNAL.equalsIgnoreCase(aName)) 
					signalName = gn.getAttribute(aName).getFirstElement();
			}
			DataNode signalNode = gn.getDataNode(signalName);
			
			// Check for a Dataset named as NexusConstants.DATA_ERRORS
			String[] uncertaintyNames = new String[] {NexusConstants.DATA_ERRORS};
			DataNode uncertaintyNode = null;
			for (String uncertainName : uncertaintyNames) {
				uncertaintyNode = gn.getDataNode(uncertainName);
				if (uncertaintyNode != null) break;
			}
			// Found a valid-looking uncertainty DataNode? As well as a valid
			// signal? Then add the uncertainty to the signal.
			if (uncertaintyNode != null && signalNode != null) {
				signalNode.getDataset().setErrors(uncertaintyNode.getDataset());
			}
		}

		AxesMetadata amd;
		try {
			amd = MetadataFactory.createMetadata(AxesMetadata.class, rank);
		} catch (MetadataException e) {
			return false;
		}
		boolean anySliced = false;

		@SuppressWarnings("unchecked")
		List<Integer>[] perAxisIndex = new List[rank];
		int nAxes = axes.size();
		for (int i = 0; i < rank; i++) {
			List<Integer> axisIndex = new ArrayList<>();
			perAxisIndex[i] = axisIndex;
			int len = shape[i];
			int gt = 0;
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < nAxes; j++) {
				ILazyDataset a = axes.get(j);
				int[] aShape = a.getShape();
				int aLen = aShape[i];
				if (aLen == len) {
					axisIndex.add(j);
				} else if (aLen != 1) {
					if (aLen > len) {
						gt++;
					} else {
						min = Math.min(min, aLen);
					}
				}
			}
			if (gt > 0) {
				if (min < len) {
					logger.warn("NXdata {} has mismatching axes in {}-th dimension", groupPath, i);
				} else {
					logger.warn("NXdata {} has longer axes in {}-th dimension", groupPath, i);
					for (int j = 0; j < nAxes; j++) {
						ILazyDataset a = axes.get(j);
						int[] aShape = a.getShape();
						int aLen = aShape[i];
						if (aLen != 1 && aLen != len) {
							SliceND slice = new SliceND(aShape);
							slice.setSlice(i, 0, len, 1);
							a = a.getSliceView(slice);
							axes.set(j, a);
							anySliced = true;
						}
					}
				}
			} else if (min < len) {
				logger.warn("NXdata {} has shorter axes in {}-th dimension", groupPath, i);
				SliceND slice = new SliceND(shape);
				slice.setSlice(i, 0, min, 1);
				cData = cData.getSliceView(slice);
				String units = getFirstString(dNode.getAttribute(NexusConstants.UNITS));
				gn.addDataNode(signal, createDataNode(signal, cData, units));
				anySliced = true;
				axisIndex.clear(); // reset
				for (int j = 0; j < nAxes; j++) {
					ILazyDataset a = axes.get(j);
					int[] aShape = a.getShape();
					int aLen = aShape[i];
					if (aLen != 1) {
						if (aLen > min) {
							slice = new SliceND(aShape);
							slice.setSlice(i, 0, min, 1);
							axes.set(j, a.getSliceView(slice));
						}
						axisIndex.add(j);
					}
				}
				
				logger.warn("NXdata {} cropped shape from {} to {}", groupPath, Arrays.toString(shape), Arrays.toString(slice.getShape()));
			}
		}

		for (int i = 0; i < rank; i++) {
			List<Integer> axisIndex = perAxisIndex[i];
			int n = axisIndex.size();
			if (n > 0) {
				ILazyDataset[] axisList = new ILazyDataset[n];
				for (int j = 0; j < n; j++) {
					axisList[j] = axes.get(axisIndex.get(j));
				}
				amd.setAxis(i, axisList);
			}
		}
		cData.addMetadata(amd);
		if (anySliced) {
			for (String n : namedAxes) {
				Optional<ILazyDataset> axis = axes.stream().filter(a -> n.equals(a.getName())).findFirst();
				if (axis.isPresent()) {
					DataNode a = gn.getDataNode(n);
					ILazyDataset d = axis.get();
					if (!ShapeUtils.areShapesCompatible(a.getDataset().getShape(), d.getShape())) {
						String units = getFirstString(a.getAttribute(NexusConstants.UNITS));
						gn.addDataNode(n, createDataNode(n, d, units));
					}
				}
			}
		}
		dNode.setAugmented();
		return true;
	}

	/**
	 * Strict parse new style (2014) NXdata class and augment with metadata
	 * @param gn
	 * @return ILazyDataset with metadata or null
	 */
	public static ILazyDataset parseNXdataAndAugmentStrict(GroupNode gn) {
		if (!isNXClass(gn, NexusConstants.DATA)) {
			logger.warn("'{}' must be an {} class", gn, NexusConstants.DATA);
			return null;
		}

		String signal = getFirstString(gn.getAttribute(NexusConstants.DATA_SIGNAL));
		if (signal == null) {
			logger.info("Signal is null, defaulting to {}", NexusConstants.DATA_DATA);
			signal = NexusConstants.DATA_DATA;
		}

		DataNode dNode = gn.getDataNode(signal);
		if (dNode == null) {
			logger.warn("Group node does not contain {}", signal);
			return null;
		}

		ILazyDataset cData = dNode.getDataset();
		if (cData == null || cData.getSize() == 0) {
			logger.warn("Chosen data '{}', has zero size", signal);
			return null;
		}

		// find possible @long_name
		String string = getFirstString(dNode.getAttribute(NexusConstants.DATA_NAME));
		if (string != null && string.length() > 0) {
			cData.setName(string);
		}

		String[] tmp = getStringArray(gn.getAttribute(NexusConstants.DATA_AXES));
		if (tmp == null) {
			logger.debug("Could not read axes attribute");
			return null;
		}
		
		Iterator<String> it = gn.getAttributeNameIterator();
		Set<String> s = new HashSet<>();
		while(it.hasNext()) s.add(it.next());
		parseAllAnnotations(signal, cData, gn, tmp,s);
		
		return cData;
	}

	private static void parseAllAnnotations(String signalName, ILazyDataset lz, GroupNode gn, String[] primaryAxes, Set<String> annotations) {
		
		AxesMetadata ax = null;
		try {
			ax = MetadataFactory.createMetadata(AxesMetadata.class, lz.getRank());
		} catch (MetadataException e) {
			logger.error("TODO put description of error here", e);
			return;
		}
		
		if (annotations.contains(signalName+NexusConstants.DATA_UNCERTAINTY_SUFFIX)) {
			Attribute attribute = gn.getAttribute(signalName+NexusConstants.DATA_UNCERTAINTY_SUFFIX);
			
			DataNode dn = gn.getDataNode(attribute.getFirstElement());
			ILazyDataset sv = dn.getDataset().getSliceView();
			lz.setErrors(sv);
		}
		
		for (int i = 0; i < primaryAxes.length; i++) {
			if (NexusConstants.DATA_AXESEMPTY.equals(primaryAxes[i])) continue;
			updateMetadata(primaryAxes[i], gn, annotations, ax, i);
		}
		
		updateMetadata(annotations.iterator().next(), gn, annotations, ax, -1);
		
		lz.setMetadata(ax);
		
	}

	private static void updateMetadata(String name, GroupNode gn, Set<String> allAnnotations, AxesMetadata metadata, int primaryDimension) {
		if (name.endsWith(NexusConstants.DATA_AXESSET_SUFFIX)) {
			if (allAnnotations.contains(name + NexusConstants.DATA_INDICES_SUFFIX)) {
				int[] indices = parseIntArray(gn.getAttribute(name + NexusConstants.DATA_INDICES_SUFFIX));
				ILazyDataset view = gn.getDataNode(name).getDataset().getSliceView();
				view.setName(name);
				metadata.addAxis(primaryDimension, view, indices);
				allAnnotations.remove(name + NexusConstants.DATA_INDICES_SUFFIX);
			}
			
			String rb = name.substring(0, name.length() - NexusConstants.DATA_AXESSET_SUFFIX.length());
			
			if (gn.containsDataNode(rb) && allAnnotations.contains(rb + NexusConstants.DATA_INDICES_SUFFIX)) {
				int[] indices = parseIntArray(gn.getAttribute(rb + NexusConstants.DATA_INDICES_SUFFIX));
				ILazyDataset view = gn.getDataNode(rb).getDataset().getSliceView();
				view.setName(rb);
				metadata.addAxis(primaryDimension, view,indices);
				allAnnotations.remove(rb + NexusConstants.DATA_INDICES_SUFFIX);
				allAnnotations.remove(rb);
			}
		} else {
			if (name.endsWith(NexusConstants.DATA_INDICES_SUFFIX)) {
				String notIndices = name.substring(0, name.length() - NexusConstants.DATA_INDICES_SUFFIX.length());
				if (gn.containsDataNode(notIndices)) {
					int[] indices = parseIntArray(gn.getAttribute(name));
					ILazyDataset view = gn.getDataNode(notIndices).getDataset().getSliceView();
					view.setName(notIndices);
					metadata.addAxis(primaryDimension == -1 && indices != null ? indices[0] : primaryDimension, view, indices);
					allAnnotations.remove(notIndices);
				}
			} else {
				final String nameWithIndices = name + NexusConstants.DATA_INDICES_SUFFIX;
				if (allAnnotations.contains(nameWithIndices)) {
					int[] indices = parseIntArray(gn.getAttribute(nameWithIndices));
					DataNode dataNode = null;
					try {
						dataNode = gn.getDataNode(name);
						if (dataNode == null)
							logger.warn("'{}' refers to a non-existent data node", nameWithIndices);
					} catch (Exception e) {
						logger.warn(e.getMessage());
					}
					allAnnotations.remove(nameWithIndices);
					if (dataNode == null) {
						return;
					}
					ILazyDataset view = dataNode.getDataset().getSliceView();
					view.setName(name);
					metadata.addAxis(primaryDimension == -1 && indices != null ? indices[0] : primaryDimension, view, indices);
				}
			}
		}
		
		allAnnotations.remove(name);
		
		Iterator<String> it = allAnnotations.iterator();
		
		if (primaryDimension == -1 && it.hasNext()) updateMetadata(it.next(), gn, allAnnotations, metadata, -1);
	}

	private static void addAxis(GroupNode gn, String a, int rank, List<ILazyDataset> axes) throws IllegalArgumentException {
		DataNode aNode = gn.getDataNode(a);
		ILazyDataset aData = aNode.getDataset();
		if (aData == null) {
			throw new IllegalArgumentException("Axis '" + a + "' dataset is empty");
		}

		int aRank = aData.getRank();
		int[] indices = parseIntArray(gn.getAttribute(a + NexusConstants.DATA_INDICES_SUFFIX));
		if (indices == null) {
			throw new IllegalArgumentException("No indices attribute for axis '" + a + "' in " + gn);
		} else if (indices.length != aRank) {
			throw new IllegalArgumentException("Indices array of axis '" + a + "' must have same length equal to its rank");
		}
		boolean[] used = new boolean[rank];
		for (int i : indices) {
			if (i < 0 || i >= rank) {
				throw new IllegalArgumentException("Index value (" + i + ") for axis '" + a + "' is out of bounds");
			}
			if (used[i]) {
				throw new IllegalArgumentException("Index value (" + i + ") for axis '" + a + "' is not unique");
			}
			used[i] = true;
		}
		if (indices.length > 1) {
			List<Integer> map = IntStream.range(0, aRank).mapToObj(Integer::valueOf).collect(Collectors.toList());
			map.sort((o1, o2) -> Integer.compare(indices[o1], indices[o2]));
			int[] aMap = map.stream().mapToInt(i -> i).toArray();
			aData = aData.getTransposedView(aMap);
			Arrays.sort(indices);
		}
		int[] aShape = aData.getShape();
		if (aRank != rank) { // broadcast axis dataset
			int[] nShape = new int[rank];
			Arrays.fill(nShape, 1);
			int j = 0;
			for (int i: indices) {
				nShape[i] = aShape[j++];
			}
			aData = aData.clone();
			aData.setShape(nShape);
		}
		axes.add(aData);
	}

	static class Transform {
		String depend;
		String name;
		Matrix4d matrix;
	}

	static class TransformedVectors {
		String depend;
		double[] magnitudes;
		Vector4d vector;
		Vector4d offset;
	}

	/**
	 * Parse a group that is an NXdetector class from a tree for shape of detector scan parameters
	 * @param path to group
	 * @param tree
	 * @return shape or null if detector or any of its module not found
	 * @throws NexusException 
	 */
	public static int[] parseDetectorScanShape(String path, Tree tree) throws NexusException {
		NodeLink link = TreeUtils.findNodeLink(tree, path, true);
		if (link == null) {
			logAndThrow("Could not find detector node at '%s'", path);
		}

		GroupNode gNode = (GroupNode) link.getDestination();
		if (!isNXClass(gNode, NexusConstants.DETECTOR)) {
			logAndThrow("'%s' must be an %s class", gNode, NexusConstants.DETECTOR);
		}

		int[] shape = null;
		for (NodeLink l : gNode) {
			if (isNXClass(l.getDestination(), NexusConstants.DETECTORMODULE)) {
				shape = parseSubDetectorShape(TreeUtils.join(path, l.getName()), tree, l);
				if (shape == null) {
					logAndThrow("Could not parse detector module '{}' shape from `{}`", l.getName(), path);
				}
				break; // TODO multiple modules
			}
		}

		if (shape == null) {
			logger.debug("No {} in detector group {}", NexusConstants.DETECTORMODULE, link.getName());
		}
		return shape;
	}

	/**
	 * Parse a group that is an NXdetector class from a tree
	 * @param path to group
	 * @param tree
	 * @param pos
	 * @return an array of detector modules
	 * @throws NexusException 
	 */
	public static DetectorProperties[] parseDetector(String path, Tree tree, int... pos) throws NexusException {
		NodeLink link = TreeUtils.findNodeLink(tree, path, true);
		if (link == null) {
			logAndThrow("Could not find detector node at '%s'", path);
		}

		GroupNode gNode = (GroupNode) link.getDestination();
		if (!isNXClass(gNode, NexusConstants.DETECTOR)) {
			logAndThrow("'%s' must be an %s class", gNode, NexusConstants.DETECTOR);
		}

		Map<String, Transform> ftrans = new HashMap<>();
		NodeLink tl = findFirstNode(gNode, NexusConstants.TRANSFORMATIONS);
		if (tl != null) {
			parseTransformations(path, tree, tl, ftrans, pos);
		}

		// initial dependency chain
		NodeLink nl = gNode.getNodeLink(TRANSFORMATIONS_DEPENDSON);
		String first = nl == null ? null : getSingleString(nl.getDestination());
		if (first != null) {
			first = canonicalizeDependsOn(path, tree, first);
			if (!ftrans.containsKey(first)) {
				Transform ta = parseTransformation(first.substring(0, first.lastIndexOf(Node.SEPARATOR)), tree, tree.findNodeLink(first), pos);
				if (ta != null) {
					ftrans.put(first, ta);
				}
			}
		}

		// Find all dependencies
		Map<String, Transform> mtrans = new HashMap<>();
		for (Transform t: ftrans.values()) {
			String dpath = t.depend;
			while (!dpath.equals(TRANSFORMATIONS_ROOT) && !ftrans.containsKey(dpath) && !mtrans.containsKey(dpath)) {
				NodeLink l = tree.findNodeLink(dpath);
				try {
					Transform nt = parseTransformation(dpath.substring(0, dpath.lastIndexOf(Node.SEPARATOR)), tree, l, pos);
					mtrans.put(nt.name, nt);
					dpath = nt.depend;
				} catch (IllegalArgumentException e) {
					logAndThrow(e, "Could not find dependency: %s", dpath);
				} catch (Exception e) {
					logAndThrow(e, "Problem parsing transformation: %s", dpath);
				}
			}
		}
		ftrans.putAll(mtrans);

		Matrix4d m;
		m = new Matrix4d();
		m.setIdentity();

		List<DetectorProperties> detectors = new ArrayList<>();
		for (NodeLink l : gNode) {
			if (isNXClass(l.getDestination(), NexusConstants.DETECTORMODULE)) {
				detectors.add(parseSubDetector(TreeUtils.join(path, l.getName()), tree, ftrans, m, l, pos));
			}
		}

		// check recorded data_size in detector module is correct (for single module case only)
		if (detectors.size() == 1) {
			boolean isFirst = IntStream.of(pos).anyMatch(p -> p != 0);

			DetectorProperties dp = detectors.get(0);
			DataNode idn = gNode.getDataNode("data");
			if (idn == null) {
				idn = gNode.getDataNode("image_data"); // for I16
			}
			if (idn != null) {
				int[] dataShape = idn.getDataset().getShape();
				int rank = dataShape.length;
				// fastest dimension should match detector width (X) and next-fastest should match height (Y)
				if (dp.getPx() != dataShape[rank - 1] || dp.getPy() != dataShape[rank - 2]) {
					if (isFirst) {
						logger.warn("Detector module's data_size does not match detector data's shape. Correcting former");
					}
					dp.setPx(dataShape[rank - 1]);
					dp.setPy(dataShape[rank - 2]);
				}
			}
		}

		int[] array = getIntArray(gNode.getDataNode(DETECTOR_UNDERLOAD));
		Integer l = array != null ? array[0] : null;
		array = getIntArray(gNode.getDataNode(DETECTOR_SATURATION));
		Integer u = array != null ? array[0] : null;
		Dataset mask = null;
		if (gNode.containsDataNode(DETECTOR_PIXELMASK)) {
			try {
				mask = DatasetUtils.sliceAndConvertLazyDataset(gNode.getDataNode(DETECTOR_PIXELMASK).getDataset());
			} catch (DatasetException e) {
				logger.error("Could not load pixel_mask", e);
			}
		}
		for (DetectorProperties d : detectors) {
			if (l != null) {
				d.setLowerThreshold(l);
			}
			if (u != null) {
				d.setUpperThreshold(u);
			}
			if (mask != null) {
				try {
					int x = d.getStartX();
					int y = d.getStartY();
					d.setMask(mask.getSliceView(new Slice(y, y + d.getPy()), new Slice(x, x + d.getPx())));
				} catch (Exception e) {
					logger.error("Could not set detector mask slice", e);
				}
			}
		}

		// XXX NexusConstants.GEOMETRY support or not?
		// with parseGeometry(m, l);

//		double[] beamCentre = new double[2];
//		nl = gNode.getNodeLink(DETECTOR_BEAMCENTERX);
//		if (nl != null) {
//			Node n = nl.getDestination();
//			double[] c = parseDoubleArray(n, 1);
//			beamCentre[0] = convertIfNecessary(SI.MILLIMETRE, getFirstString(n.getAttribute(NexusConstants.UNITS)), c[0]);
//		}
//		
//		nl = gNode.getNodeLink(DETECTOR_BEAMCENTERY);
//		if (nl != null) {
//			Node n = nl.getDestination();
//			double[] c = parseDoubleArray(n, 1);
//			beamCentre[1] = convertIfNecessary(SI.MILLIMETRE, getFirstString(n.getAttribute(NexusConstants.UNITS)), c[0]);
//		}

		// determine beam direction from centre position
//		Vector4d p4 = new Vector4d(beamCentre[0], beamCentre[1], 0, 1);
//		m.transform(p4);
//		Vector3d bv = new Vector3d(p4.x, p4.y, p4.z);
//		bv.normalize();
//		
		DetectorProperties[] da = detectors.toArray(new DetectorProperties[0]);
//		for (DetectorProperties dp : da) {
//			dp.setBeamVector(new Vector3d(bv));
//		}
		return da;
	}

	/**
	 * Parse a group that is an NXattenuator class from a tree
	 * @param path to group
	 * @param tree
	 * @return a dataset of attenuator transmission values or null if path is null or not found
	 */
	public static Dataset parseAttenuator(String path, Tree tree) {
		return parseNXGroup(path, tree, NexusConstants.ATTENUATOR, "attenuator_transmission");
	}

	/**
	 * Parse a group that is an NXpositioner class from a tree
	 * @param path to group
	 * @param tree
	 * @return a dataset of positioner values or null if path is null or not found
	 */
	public static Dataset parsePositioner(String path, Tree tree) {
		return parseNXGroup(path, tree, NexusConstants.POSITIONER, "value");
	}

	/**
	 * Parse a group that is an NeXus class from a tree
	 * @param path to group
	 * @param tree
	 * @param nxClass (can be null)
	 * @param fields candidate field names
	 * @return a dataset of monitor values or null if path is null or not found
	 */
	public static Dataset parseNXGroup(String path, Tree tree, String nxClass, String... fields) {
		NodeLink link = TreeUtils.findNodeLink(tree, path, true);
		if (link == null) {
			return null;
		}

		GroupNode gNode = (GroupNode) link.getDestination();
		if (nxClass != null && !isNXClass(gNode, nxClass)) {
			logger.warn("'{}' must be an {} class", path, nxClass);
			return null;
		}

		return getDataset(link.getName(), gNode, null, fields);
	}

	/**
	 * Get a dataset from one of candidate field names
	 * @param gName group name
	 * @param gNode group node
	 * @param unit (can be null)
	 * @param fields candidate field names
	 * @return a dataset or null if no fields are found
	 */
	public static Dataset getDataset(String gName, GroupNode gNode, Unit<?> unit, String... fields) {
		DataNode value = null;
		for (String f : fields) {
			value = gNode.getDataNode(f);
			if (value != null) {
				break;
			}
			logger.debug("'{}' does not contain a '{}' dataset", gName, f);
		}
		if (value == null) {
			logger.warn("'{}' does not contain any of the candidate field datasets", gName);
			return null;
		}

		return unit == null ? getAndCacheData(value) : getConvertedData(value, unit);
	}

	/**
	 * Get a dataset
	 * @param path
	 * @param tree
	 * @return dataset
	 */
	public static Dataset getDataset(String path, Tree tree) {
		return getDataset(path, tree, null);
	}

	/**
	 * Get a dataset
	 * @param path
	 * @param tree
	 * @param unit (can be null)
	 * @return dataset
	 */
	public static Dataset getDataset(String path, Tree tree, Unit<?> unit) {
		NodeLink link = TreeUtils.findNodeLink(tree, path, false);
		if (link == null) {
			return null;
		}

		DataNode node = (DataNode) link.getDestination();
		return unit == null ? getAndCacheData(node) : getConvertedData(node, unit);
	}

	/**
	 * Parse detector module for scan shape
	 * @param path
	 * @param tree
	 * @param link
	 * @return scan shape or null if not found
	 * @throws NexusException 
	 */
	public static int[] parseSubDetectorShape(String path, Tree tree, NodeLink link) throws NexusException {
		if (!link.isDestinationGroup()) {
			logAndThrow("'%s' must be a group", link.getName());
		}

		GroupNode gNode = (GroupNode) link.getDestination();

		int[] shape = null;
		for (NodeLink l : gNode) {
			String name = l.getName();
			if (DMOD_FASTPIXELDIRECTION.equals(name) || DMOD_SLOWPIXELDIRECTION.equals(name)) {
				shape = parseNodeShape(path, tree, l, shape);
			}
		}
		return shape;
	}

	public static DetectorProperties parseSubDetector(String path, Tree tree, Map<String, Transform> ftrans, Matrix4d m1, NodeLink link, int[] pos) throws NexusException {
		if (!link.isDestinationGroup()) {
			logAndThrow("'%s' must be a detector module group in ", path);
		}

		GroupNode gNode = (GroupNode) link.getDestination();

		Transform mo = null;
		TransformedVectors fpd = null, spd = null;
		int[] origin = null, size = null;
		for (NodeLink l : gNode) {
			String name = l.getName();
			switch(name) {
			case DMOD_DATAORIGIN:
				origin = getIntArray(l.getDestination(), 2);
				break;
			case DMOD_DATASIZE: // number of pixels in slow then fast; i.e. #rows, #cols
				size = getIntArray(l.getDestination(), 2);
				break;
			case DMOD_MODULEOFFSET:
				mo = parseTransformation(path, tree, l, pos);
				if (mo != null) {
					ftrans.put(mo.name, mo);
				}
				break;
			case DMOD_FASTPIXELDIRECTION:
				fpd = parseTransformedVectors(path, tree, l);
 				break;
			case DMOD_SLOWPIXELDIRECTION:
				spd = parseTransformedVectors(path, tree, l);
				break;
			default:
				break;
			}
		}
		if (mo == null) {
			logger.error("No module offset defined");
		}
		if (size == null) {
			logAndThrow("No size defined in '%s'", path);
		}
		if (origin == null) {
			logAndThrow("No origin defined in '%s'", path);
		}
		if (fpd == null) {
			logAndThrow("No fast direction defined in '%s'", path);
		} else if (fpd.magnitudes.length > 1) {
			logger.warn("Only using first value of fast pixel size");
		}
		if (spd == null) {
			logAndThrow("No slow direction defined in '%s'", path);
		} else if (spd.magnitudes.length > 1) {
			logger.warn("Only using first value of slow pixel size");
		}

		Vector3d off;
		Matrix4d m = new Matrix4d();
		if (mo == null) {
			off = new Vector3d();
		} else {
			m.mul(calcForwardTransform(ftrans, mo.name), m1);
			Vector4d v = new Vector4d();
			v.setW(1);
			off = transform(m, v);
		}

		m.mul(calcForwardTransform(ftrans, fpd.depend), m1);
		Vector3d xdir = transform(m, fpd.vector);

		if (!spd.depend.equals(fpd.depend)) {
			m.mul(calcForwardTransform(ftrans, spd.depend), m1);
		}
		Vector3d ydir = transform(m, spd.vector);

		Matrix3d ori = new Matrix3d();
		m1.getRotationScale(ori);
		ori.mul(MatrixUtils.computeFSOrientation(xdir, ydir));
		ori.transpose(); // as we need the passive transformation
		DetectorProperties dp = new DetectorProperties(off, size[0], size[1], spd.magnitudes[0], fpd.magnitudes[0], ori);
		dp.setStartX(origin[1]);
		dp.setStartY(origin[0]);
		return dp;
	}

	// follow dependency chain forward and right multiply
	private static Matrix4d calcForwardTransform(Map<String, Transform> ftrans, String dep) throws NexusException {
		Matrix4d m = new Matrix4d();
		m.setIdentity();

		Transform t;
		do {
			t = ftrans.get(dep);
			if (t == null) {
				logAndThrow("Could not find transformation dependency '%s'", dep);
			}

			m.mul(t.matrix, m);
			dep = t.depend;
		} while (!TRANSFORMATIONS_ROOT.equals(dep));

		return m;
	}

	private static Vector3d transform(Matrix4d m, Vector4d v) {
		Vector4d vt = new Vector4d();
		m.transform(v, vt);
		Vector3d vout = new Vector3d();
		vout.set(vt.x, vt.y, vt.z);
		return vout;
	}

	public static void parseGeometry(Matrix4d m, NodeLink link) {
		if (!link.isDestinationGroup()) {
			logger.warn("'{}' must be a group", link.getName());
			return;
		}

		GroupNode gNode = (GroupNode) link.getDestination();

		// find orientation first as parseSubGeometry multiplies from the right
		NodeLink l = gNode.getNodeLink("orientation");
		if (l != null && isNXClass(l.getDestination(), NexusConstants.ORIENTATION)) {
			parseSubGeometry(m, l, false);
		}
		l = gNode.getNodeLink(TRANSFOMATIONS_TRANSLATION);
		if (l != null && isNXClass(l.getDestination(), NexusConstants.TRANSLATION)) {
			parseSubGeometry(m, l, true);
		}
	}

	public static void parseSubGeometry(Matrix4d m, NodeLink link, boolean translate) {
		if (!link.isDestinationGroup()) {
			logger.warn("'{}' must be a group", link.getName());
			return;
		}

		GroupNode gNode = (GroupNode) link.getDestination();

		NodeLink l = gNode.getNodeLink(translate ? "distances" : "value");
		if (l == null || !l.isDestinationData()) {
			throw new IllegalArgumentException("Geometry subnode is missing dataset");
		}
		DataNode dNode = (DataNode) l.getDestination();
		DoubleDataset ds = getCastAndCacheData(dNode, DoubleDataset.class);
		if (ds == null) {
			logger.warn("Geometry subnode {} has an empty dataset", link.getName());
			return;
		}

		int[] shape = ds.getShapeRef();
		if (shape.length != 2 || shape[1] != (translate ? 3 : 6)) {
			throw new IllegalArgumentException("Geometry subnode has wrong shape");
		}
		double[] da = ds.getData();
		Matrix4d m2 = new Matrix4d();
		if (translate) {
			da = da.clone(); // necessary to stop clobbering cached values
			UnitMetadata um = ds.getFirstMetadata(UnitMetadata.class);
			convertIfNecessary(MILLIMETRE, um == null ? null : um.getUnit(), da);
			m2.setIdentity();
			m2.setColumn(3, da[0], da[1], da[2], 1);
		} else {
			m2.setRow(0, da[0], da[1], da[2], 0);
			m2.setRow(1, da[3], da[4], da[5], 0);
			m2.setRow(2, -da[2], -da[5], da[0]*da[4] - da[1]*da[3], 0);
			m2.setElement(3, 3, 1);
		}
		m.mul(m2); // right multiply
	
		l = gNode.getNodeLink("geometry");
		if (l != null && isNXClass(l.getDestination(), NexusConstants.GEOMETRY)) {
			parseGeometry(m, l);
		}
	}

	public static void parseTransformations(String path, Tree tree, NodeLink link, Map<String, Transform> ftrans, int[] pos) throws NexusException {
		if (!link.isDestinationGroup()) {
			logAndThrow("'%s' must be a group", link.getName());
		}

		GroupNode gNode = (GroupNode) link.getDestination();
		String gpath = TreeUtils.join(path, link.getName());
		for (NodeLink l : gNode) {
			Transform t = null;
			try {
				t = parseTransformation(gpath, tree, l, pos);
			} catch (Exception e) {
//				logger.warn("Problem parsing transformation {}", l);
			}
			if (t != null) {
				ftrans.put(t.name, t);
			}
		}
	}

	private static final String INCIDENT_WAVELENGTH = "incident_wavelength";
	private static final String INCIDENT_ENERGY = "incident_energy";

	public static void parseBeam(Tree tree, GroupNode group, DiffractionCrystalEnvironment sample, int... pos) {
		parseForDCE(true, tree == null ? null : TreeUtils.getPath(tree, group), tree, INCIDENT_WAVELENGTH, INCIDENT_ENERGY, group, sample, pos);
	}

	public static void parseBeam(Tree tree, NodeLink link, DiffractionCrystalEnvironment sample, int... pos) {
		parseForDCE(true, tree == null ? null : TreeUtils.getPath(tree, link.getDestination()), tree, INCIDENT_WAVELENGTH, INCIDENT_ENERGY, link, sample, pos);
	}

	public static void parseBeam(boolean warn, String path, Tree tree, NodeLink link, DiffractionCrystalEnvironment sample, int... pos) {
		parseForDCE(warn, path, tree, INCIDENT_WAVELENGTH, INCIDENT_ENERGY, link, sample, pos);
	}

	public static void parseMonochromator(Tree tree, NodeLink link, DiffractionCrystalEnvironment sample, int... pos) {
		parseForDCE(true, tree == null ? null : TreeUtils.getPath(tree, link.getDestination()), tree, "wavelength", "energy", link, sample, pos);
	}

	public static void parseForDCE(boolean warn, String path, Tree tree, String wavelengthName, String energyName, NodeLink link, DiffractionCrystalEnvironment sample, int... pos) {
		if (!link.isDestinationGroup()) {
			logger.warn("'{}' must be a group", link.getName());
			return;
		}
		parseForDCE(warn, path, tree, wavelengthName, energyName, (GroupNode) link.getDestination(), sample, pos);
	}

	public static void parseForDCE(boolean warn, String path, Tree tree, String wavelengthName, String energyName, GroupNode gNode, DiffractionCrystalEnvironment sample, int... pos) {
		DataNode wavelength = gNode.getDataNode(wavelengthName);
		if (wavelength != null) {
			Dataset w = getConvertedData(wavelength, NonSI.ANGSTROM);
			sample.setWavelength(w.getSize() == 1 ? w.getElementDoubleAbs(0) : w.getDouble(pos));
		} else {
			DataNode energy = gNode.getDataNode(energyName);
			if (energy != null) {
				Dataset e = getConvertedData(energy, MetricPrefix.KILO(NonSI.ELECTRON_VOLT));
				sample.setWavelengthFromEnergykeV(e.getSize() == 1 ? e.getElementDoubleAbs(0) : e.getDouble(pos));
			} else {
				logger.error("Both wavelength {} and energy {} was missing in {}", wavelengthName, energyName, gNode);
				throw new IllegalArgumentException("Could not read wavelength or energy of beam"); 
			}
		}

		DataNode depNode = gNode.getDataNode(TRANSFORMATIONS_DEPENDSON);
		String dependsOn = "direction";
		GroupNode tNode = null;
		if (depNode == null) {
			if (warn) {
				logger.warn("NXbeam is missing a depends_on field so defaulting to NXtransformations/direction");
			}
		} else if (tree != null && path != null) {
			try {
				dependsOn = getFirstString(depNode);
			} catch (NexusException e) {
				logger.error("Could not read depends_on so using '{}'", dependsOn, e);
			}
			dependsOn = canonicalizeDependsOn(path, tree, dependsOn);

			NodeLink dNodeLink = TreeUtils.findNodeLink(tree, dependsOn, false);
			Node pDNode = dNodeLink.getSource();
			if (pDNode instanceof GroupNode pgNode) {
				tNode = pgNode;
				if (!isNXClass(tNode, NexusConstants.TRANSFORMATIONS)) {
					logger.error("Depends_on parent is not an {} group: {}", NexusConstants.TRANSFORMATIONS, tNode);
				}
			} else {
				logger.error("Depends_on parent is not a group: {}", dependsOn);
			}
			dependsOn = dependsOn.substring(dependsOn.lastIndexOf(Node.SEPARATOR) + 1);
		}

		if (tNode == null) { // fallback to 1st transformations group
			NodeLink tNodeLink = findFirstNode(gNode, NexusConstants.TRANSFORMATIONS);
			tNode = getGroup(tNodeLink);
		}

		if (tNode == null) {
			if (warn) {
				logger.warn("Beam transformation was missing in {}", gNode);
			}
		} else {
			DataNode direction = tNode.getDataNode(dependsOn);
			if (direction == null) {
				logger.warn("Direction was missing in {}", gNode);
			} else {
				double[] d = parseDoubleArray(direction.getAttribute(TRANSFORMATIONS_VECTOR), 3);
				sample.setBeamDirection(new Vector3d(d));
			}
			DataNode reference = tNode.getDataNode("reference_plane");
			if (reference == null) {
				logger.warn("Polarization reference was missing in {}", gNode);
			} else {
				double[] d = parseDoubleArray(reference.getAttribute(TRANSFORMATIONS_VECTOR), 3);
				sample.setReferenceNormal(new Vector3d(d));
			}
		}
		DataNode stokes = gNode.getDataNode("incident_polarization_stokes");
		if (stokes == null) {
			stokes = gNode.getDataNode("final_polarization_stokes");
		}
		if (stokes == null) {
			if (warn) {
				logger.warn("Polarization state was missing in {}", gNode);
			}
		} else {
			CompoundDoubleDataset stokesPoln = getCastAndCacheData(stokes, 4, CompoundDoubleDataset.class);
			double[] d = stokesPoln.getSize() == 1 ? stokesPoln.getDoubleArray() : stokesPoln.getDoubleArray(pos);
			sample.setStokesVector(new Vector4d(d));
		}
	}

	public static DetectorProperties parseSaxsDetector(NodeLink link) {
		try {
			GroupNode node = (GroupNode)link.getDestination();
			DataNode distanceNode = node.getDataNode(DETECTOR_DISTANCE);
			double distanceMm = getConvertedData(distanceNode, MILLIMETRE).get(0);
			DataNode bxNode = node.getDataNode(DETECTOR_BEAMCENTERX);
			double bx = bxNode.getDataset().getSlice().getDouble(0);
			DataNode byNode = node.getDataNode(DETECTOR_BEAMCENTERY);
			double by = byNode.getDataset().getSlice().getDouble(0);
			DataNode pxNode = node.getDataNode(DETECTOR_XPIXELSIZE);
			double px = getConvertedData(pxNode, MILLIMETRE).get(0);
			DataNode pyNode = node.getDataNode(DETECTOR_YPIXELSIZE);
			double py = getConvertedData(pyNode, MILLIMETRE).get(0);
			DataNode nxNode = node.getDataNode(DETECTOR_XPIXELNUMBER);

			if (nxNode == null) {
				DataNode dataNode = node.getDataNode(NexusConstants.DATA_DATA);
				if (dataNode == null) return null;
				long[] shape = dataNode.getMaxShape();
				DetectorProperties dp = new DetectorProperties(distanceMm, bx*px, by*py, (int)shape[shape.length-2], (int)shape[shape.length-1], py, px);
				return dp;
			}

			int nx = nxNode.getDataset().getSlice().getInt(0);
			DataNode nyNode = node.getDataNode(DETECTOR_XPIXELNUMBER);
			int ny = nyNode.getDataset().getSlice().getInt(0);
			DetectorProperties dp = new DetectorProperties(distanceMm, bx*px, by*py, nx, ny, py, px);
			return dp;
		} catch (Exception e) {
			logger.debug("Could not read SAXS detector properties", e);
		}

		return null;
	}

	/**
	 * Parse data node's shape and its transformation ancestors
	 * @param path parent path of link
	 * @param tree
	 * @param link
	 * @param shape existing shape to check against
	 * @return shape or null if not found
	 */
	public static int[] parseNodeShape(String path, Tree tree, NodeLink link, int[] shape) {
		if (!link.isDestinationData()) {
			logger.warn("'{}' must be a dataset", link.getName());
			return null;
		}

		DataNode dNode = (DataNode) link.getDestination();
		ILazyDataset dataset = dNode.getDataset();
		if (dataset == null) {
			logger.warn("'{}' has an empty dataset", link.getName());
			return null;
		}

		int[] nshape = dataset.getShape();

		String dep = getFirstString(dNode.getAttribute(TRANSFORMATIONS_DEPENDSON));
		if (dep == null || dep.equals(TRANSFORMATIONS_ROOT)) {
			return checkShapes(shape, nshape);
		}

		NodeLink l = null;
		if (!dep.startsWith(Tree.ROOT)) { // try local group
			Node n = link.getSource();
			if (n instanceof GroupNode gn) {
				l = gn.getNodeLink(dep);
			}
		}
		if (l == null) {
			dep = canonicalizeDependsOn(path, tree, dep);
			l = tree.findNodeLink(dep);
			if (l == null) {
				logger.error("A depends_on node is missing: '{}'", dep);
				return null;
			}
			path = dep.substring(0, dep.lastIndexOf(Node.SEPARATOR));
		}
		int[] dshape = parseNodeShape(path, tree, l, shape);

		return checkShapes(dshape, nshape);
	}

	private static int[] checkShapes(int[] mshape, int[] nshape) {
		if (mshape == null) {
			return nshape;
		}
		int msize = ShapeUtils.calcSize(mshape);
		int nsize = ShapeUtils.calcSize(nshape);
		if (msize != 1 && nsize != 1) {
			if (msize != nsize) {
				throw new IllegalArgumentException("Non-trivial shapes must have same size");
			}
			if (mshape.length != nshape.length) {
				throw new IllegalArgumentException("Non-trivial shapes must have same rank");
			}
			if (!Arrays.equals(mshape, nshape)) {
				throw new IllegalArgumentException("Non-trivial shapes must match");
			}
			return mshape;
		}

		if (msize == 1) {
			if (nsize > msize || nshape.length >= mshape.length) {
				return nshape;
			}
			throw new IllegalArgumentException("Something is very wrong");
		}
		if (msize > nsize || mshape.length >= nshape.length) {
			return mshape;
		}

		throw new IllegalArgumentException("Something is very wrong");
	}

	/**
	 * Parse a group that is NXsample class from a tree for shape of sample scan parameters
	 * @param path to group
	 * @param tree
	 * @param shape
	 * @return shape or null if sample or its transformation ancestors not found
	 * @throws NexusException 
	 */
	public static int[] parseSampleScanShape(String path, Tree tree, int[] shape) throws NexusException {
		NodeLink link = TreeUtils.findNodeLink(tree, path, true);
		if (link == null) {
			return null;
		}

		GroupNode gNode = (GroupNode) link.getDestination();
		if (!isNXClass(gNode, NexusConstants.SAMPLE)) {
			logger.warn("'{}' must be an {} class", gNode, NexusConstants.SAMPLE);
			return null;
		}

		// when depends_on does not exist!?!
		NodeLink nl = gNode.getNodeLink(TRANSFORMATIONS_DEPENDSON);
		if (nl == null) {
			logAndThrow("Sample '%s' must have a %s field", link.getName(), TRANSFORMATIONS_DEPENDSON);
		}
		String dep = canonicalizeDependsOn(path, tree, getSingleString(nl.getDestination()));
		shape = parseNodeShape(path, tree, tree.findNodeLink(dep), shape);

		if (shape == null) {
			logAndThrow("Could not parse sample scan shape from `{}`", dep);
		}
		return shape;
	}

	/**
	 * 
	 * @param path
	 * @param tree
	 * @param useOMatrixAsUB if true, then interpret orientation matrix as UB matrix 
	 * @param xUp is X up in laboratory frame
	 * @param pos
	 * @return diffraction sample
	 * @throws NexusException 
	 */
	public static DiffractionSample parseSample(String path, Tree tree, boolean useOMatrixAsUB, boolean xUp, int... pos) throws NexusException {
		NodeLink link = TreeUtils.findNodeLink(tree, path, true);
		if (link == null) {
			logAndThrow("Node '%s' not found", path);
		}

		GroupNode gNode = (GroupNode) link.getDestination();
		if (!isNXClass(gNode, NexusConstants.SAMPLE)) {
			logAndThrow("'%s' must be an %s class", gNode, NexusConstants.SAMPLE);
		}

		DiffractionCrystalEnvironment env = new DiffractionCrystalEnvironment();
		if (xUp) { // reference plane normal is vertically up
			env.setReferenceNormal(new Vector3d(1, 0, 0));
		}

		boolean isFirst = IntStream.of(pos).anyMatch(p -> p != 0);

		// get wavelength and transformations
		boolean getTransformations = true;
		boolean getBeam = true;
		Map<String, Transform> ftrans = new HashMap<>();
		for (NodeLink l : gNode) {
			if (isNXClass(l.getDestination(), NexusConstants.TRANSFORMATIONS) && getTransformations) {
				parseTransformations(path, tree, l, ftrans, pos);
				getTransformations = false;
			}
			if (isNXClass(l.getDestination(), NexusConstants.BEAM) && getBeam) {
				parseBeam(isFirst, TreeUtils.join(path, l.getName()), tree, l, env, pos);
				getBeam = false;
			}
		}

		if (getBeam) {
			logAndThrow("Could not find beam in %s", link.getName());
		}

		// Find all dependencies
		Map<String, Transform> mtrans = new HashMap<>();
		for (Transform t: ftrans.values()) {
			String dpath = t.depend;
			while (!dpath.equals(TRANSFORMATIONS_ROOT) && !ftrans.containsKey(dpath) && !mtrans.containsKey(dpath)) {
				NodeLink l = tree.findNodeLink(dpath);
				if (l == null) {
					logAndThrow("Could not find dependency: %s", dpath);
				}
				try {
					Transform nt = parseTransformation(dpath.substring(0, dpath.lastIndexOf(Node.SEPARATOR)), tree, l, pos);
					if (nt != null) {
						mtrans.put(nt.name, nt);
						dpath = nt.depend;
					}
				} catch (IllegalArgumentException e) {
					logAndThrow(e, "Could not find dependency: %s", dpath);
				} catch (Exception e) {
					logger.error("Problem parsing transformation: {}", dpath, e);
					break;
				}
			}
		}
		ftrans.putAll(mtrans);

		UnitCell unitCell = requireUnitCell(gNode, "unit_cell");
		Matrix3d u;
		NodeLink l = gNode.getNodeLink("orientation_matrix");
		if (l == null) {
			l = gNode.getNodeLink("ub_matrix");
			useOMatrixAsUB = true;
		}
		if (useOMatrixAsUB) {
			// Historic i16 files have orientation_matrix was really ub_matrix
			Matrix3d ub = parse3DMatrix(l);
			// remove orthogonalization to find orientation
			u = new Matrix3d();
			u.invert(new ReciprocalCell(unitCell, Ortho_Convention.BUSING_LEVY).orthogonalization());
			u.mul(ub, u);
		} else {
			u = parse3DMatrix(l);
		}

		Matrix3d m3 = new Matrix3d();
		NodeLink nl = gNode.getNodeLink(TRANSFORMATIONS_DEPENDSON);
		if (nl != null && nl.isDestinationData()) {
			String dep = canonicalizeDependsOn(path, tree, getSingleString(nl.getDestination()));
			Matrix4d m = calcForwardTransform(ftrans, dep);
			m.getRotationScale(m3);
		} else {
			m3.setIdentity();
		}
		// get net orientation
		m3.mul(u);
		env.setOrientation(m3);
		
		return new DiffractionSample(env, unitCell);
	}

	private static Matrix3d parse3DMatrix(NodeLink link) {
		if (!link.isDestinationData()) {
			logger.warn("'{}' must be a dataset", link.getName());
			return null;
		}

		double[] matrix = getDoubleArray(link.getDestination(), 9);

		return new Matrix3d(matrix);
	}

	private static UnitCell requireUnitCell(GroupNode group, String node) throws NexusException {
		DataNode dNode = requireDataNode(group, node);

		double[] parms = getDoubleArray(dNode, 6);

		return new UnitCell(parms);
	}

	public static Transform parseTransformation(String ppath, Tree tree, NodeLink link, int[] pos) throws NexusException {
		String name = link.getName();
		if (!link.isDestinationData()) {
			logAndThrow("'%s' must be a dataset", name);
		}

		DataNode dNode = (DataNode) link.getDestination();
		Dataset dataset = getAndCacheData(dNode);
		if (dataset == null) {
			logger.warn("'{}' had an empty dataset", name);
			return null;
		}

		String type = getFirstString(dNode.getAttribute(TRANSFORMATIONS_TYPE));
		Matrix4d m4 = new Matrix4d();
		if (type == null) { // general axis for documentation which we will keep in chain
			m4.setIdentity();
		} else {
			double value = dataset.getSize() == 1 ? dataset.getElementDoubleAbs(0) : dataset.getDouble(pos);
			double[] vector = null;
			try {
				vector = parseDoubleArray(dNode.getAttribute(TRANSFORMATIONS_VECTOR), 3);
			} catch (IllegalArgumentException e) {
				logAndThrow("Vector attribute of '%s' has wrong length", name);
			}
			if (vector == null) {
				logAndThrow("Vector attribute of '%s' is missing", name);
			}
			Vector3d v3 = new Vector3d(vector);
			UnitMetadata um = dataset.getFirstMetadata(UnitMetadata.class);
			Unit<?> units = um != null ? um.getUnit() : null;
			switch(type) {
			case TRANSFOMATIONS_TRANSLATION:
				Matrix3d m3 = new Matrix3d();
				m3.setIdentity();
//				v3.normalize(); // XXX I16 written with magnitude too
				v3.scale(convertIfNecessary(MILLIMETRE, units, value));
				m4.set(m3, v3, 1);
				break;
			case TRANSFOMATIONS_ROTATION:
				AxisAngle4d aa = new AxisAngle4d(v3, convertIfNecessary(Units.RADIAN, units, value));
				m4.set(aa);
				break;
			default:
				logAndThrow("Transformations field '%s' has unknown type '%s'", name, type);
			}

			double[] offset = null;
			try {
				offset = parseDoubleArray(dNode.getAttribute(TRANSFORMATIONS_OFFSET), 3);
			} catch (IllegalArgumentException e) {
				logAndThrow("Offset attribute of '%s' has wrong length", name);
			}
			if (offset != null) {
				convertIfNecessary(MILLIMETRE, getFirstString(dNode.getAttribute(TRANSFORMATIONS_OFFSET_UNITS)), offset);
				for (int i = 0; i < 3; i++) {
					m4.setElement(i, 3, offset[i] + m4.getElement(i, 3));
				}
			}
		}

		Transform t = new Transform();
		t.name = TreeUtils.canonicalizePath(TreeUtils.join(ppath, name));
		String dep = canonicalizeDependsOn(ppath, tree, getFirstString(dNode.getAttribute(TRANSFORMATIONS_DEPENDSON)));
		t.depend = dep;
		t.matrix = m4;
		return t;
	}

	private static String canonicalizeDependsOn(String ppath, Tree tree, String dep) {
		if (dep == null) {
			dep = TRANSFORMATIONS_ROOT;
		} else if (dep.equals(TRANSFORMATIONS_ROOT)) {
			// do nothing
		} else if (dep.startsWith(RELATIVE_PREFIX)) {
			dep = TreeUtils.join(ppath, dep);
			dep = TreeUtils.canonicalizePath(dep);
		} else if (!dep.startsWith(Tree.ROOT)) {
			// check if absolute, if not assume relative 
			String test = Tree.ROOT.concat(dep);
			if (tree.findNodeLink(test) == null) {
				dep = TreeUtils.join(ppath, dep);
				dep = TreeUtils.canonicalizePath(dep);
			} else {
				dep = test;
			}
		}
		return dep;
	}

	public static TransformedVectors parseTransformedVectors(String path, Tree tree, NodeLink link) {
		if (!link.isDestinationData()) {
			logger.warn("'{}' must be a dataset", link.getName());
			return null;
		}

		DataNode dNode = (DataNode) link.getDestination();
		double[] vector = parseDoubleArray(dNode.getAttribute(TRANSFORMATIONS_VECTOR), 3);
		Vector3d v3 = new Vector3d(vector);
		DoubleDataset dataset = getConvertedData(dNode, MILLIMETRE);
		if (dataset == null) {
			logger.warn("Transform {} has an empty dataset", link.getName());
			return null;
		}
		double[] values = dataset.getData();
		String type = getFirstString(dNode.getAttribute(TRANSFORMATIONS_TYPE));
		if (type != null && !TRANSFOMATIONS_TRANSLATION.equals(type)) {
			throw new IllegalArgumentException("Transformed vector node has wrong type");
		}
		v3.normalize();

		Vector3d o3 = new Vector3d();
		double[] offset = parseDoubleArray(dNode.getAttribute(TRANSFORMATIONS_OFFSET), 3);
		if (offset != null) {
			convertIfNecessary(MILLIMETRE, getFirstString(dNode.getAttribute(TRANSFORMATIONS_OFFSET_UNITS)), offset);
			o3.set(offset);
		}

		TransformedVectors tv = new TransformedVectors();
		String dep = canonicalizeDependsOn(path, tree, getFirstString(dNode.getAttribute(TRANSFORMATIONS_DEPENDSON)));
		tv.depend = dep;
		tv.magnitudes = values;
		tv.vector = new Vector4d(v3);
		tv.offset = new Vector4d(o3);
		tv.offset.setW(1);
		return tv;
	}

	/**
	 * Require that a node link in group to be of given Nexus class is found
	 * @param group
	 * @param clazz
	 * @return node link
	 * @throws NexusException
	 */
	public static Node requireNode(GroupNode group, String clazz) throws NexusException {
		return requireNode(group, null, clazz);
	}

	/**
	 * Require that a group node in group to be of given Nexus class is found
	 * @param group
	 * @param clazz
	 * @return group node
	 * @throws NexusException
	 */
	public static GroupNode requireGroupNode(GroupNode group, String clazz) throws NexusException {
		if (requireNode(group, null, clazz) instanceof GroupNode g) {
			return g;
		}
		throw new NexusException("Found node was not a group: " + clazz);
	}

	/**
	 * Require that a node in group with prefix as name to be of given Nexus class is found
	 * @param group
	 * @param prefix (can be null)
	 * @param clazz
	 * @return node
	 * @throws NexusException
	 */
	public static Node requireNode(GroupNode group, String prefix, String clazz) throws NexusException {
		NodeLink link = findFirstNode(group, prefix, clazz);
		if (link == null) {
			logAndThrow("Could not find NeXus class '%s' in %s", clazz, group);
		}
		return link.getDestination();
	}

	private static void logAndThrow(String msgFormat, Object... args) throws NexusException {
		logAndThrow(null, msgFormat, args);
	}

	private static void logAndThrow(Exception e, String msgFormat, Object... args) throws NexusException {
		String msg = String.format(msgFormat, args);
		if (e == null) {
			logger.error(msg);
			throw new NexusException(msg);
		}
		logger.error(msg, e);
		throw new NexusException(msg, e);
	}

	/**
	 * Require that a node link in group with prefix as name to be of given Nexus class is found
	 * @param group
	 * @param name
	 * @return node
	 * @throws NexusException
	 */
	public static DataNode requireDataNode(GroupNode group, String name) throws NexusException {
		NodeLink link = group.getNodeLink(name);
		if (link == null) {
			logAndThrow("Could not find node '%s' in %s", name, group);
		}
		while (link.isDestinationSymbolic()) {
			SymbolicNode sNode = (SymbolicNode) link.getDestination();
			NodeLink nLink = sNode.getNodeLink();
			if (nLink == null) {
				logAndThrow("Could not find resolve symbolic node that points to '%s'", sNode.getPath());
			}
			link = nLink;
		}
		if (!link.isDestinationData()) {
			logAndThrow("Node found in '%s/%s' is not data node", group, name);
		}
		return (DataNode) link.getDestination();
	}

	/**
	 * Find node link to first item in group to be of given Nexus class
	 * @param group
	 * @param clazz
	 * @return node link to first 
	 */
	public static NodeLink findFirstNode(GroupNode group, String clazz) {
		return findFirstNode(group, null, clazz);
	}

	/**
	 * Find node link to first item in group with prefix as name to be of given Nexus class
	 * @param group
	 * @param prefix (can be null but only if clazz is not null)
	 * @param clazz (can be null but only if prefix is not null)
	 * @return node link to first 
	 */
	public static NodeLink findFirstNode(GroupNode group, final String prefix, final String clazz) {
		if (prefix == null && clazz == null) {
			throw new IllegalArgumentException("Prefix or clazz must be defined");
		}
		for (NodeLink l : group) {
			if (prefix != null && !l.getName().startsWith(prefix)) {
				continue;
			}
			if (clazz == null || isNXClass(l.getDestination(), clazz)) {
				return l;
			}
		}
		return null;
	}

	/**
	 * Find node link to first data node in group with a signal attribute
	 * @param group
	 * @return node link to first or null
	 */
	public static NodeLink findFirstSignalDataNode(GroupNode group) {
		String signal = getFirstString(group.getAttribute(NexusConstants.DATA_SIGNAL));
		if (signal != null) {
			if (group.containsDataNode(signal)) {
				return group.getNodeLink(signal);
			}
			logger.warn("Signal {} not found in group {}", signal, group);
		}

		// Check according to pre-2014 standard
		for (NodeLink l : group) {
			if (l.isDestinationData()) {
				DataNode d = (DataNode) l.getDestination();
				if (d.containsAttribute(NexusConstants.DATA_SIGNAL)) {
					return l;
				}
			}
		}
		return null;
	}

	/**
	 * Find first entry with a process group
	 * @param group
	 * @return process group or null
	 */
	public static GroupNode findFirstEntryWithProcess(GroupNode group) {
		for (GroupNode g : group.getGroupNodes()) {
			if (isNXClass(g, NexusConstants.ENTRY)) {
				NodeLink l = findFirstNode(g, NexusConstants.PROCESS);
				if (l != null && l.isDestinationGroup()) {
					return g;
				}
			}
		}

		return null;
	}

	/**
	 * Find first sub-entry with given application definition
	 * @param group
	 * @param appDef application definition
	 * @return sub entry or null
	 */
	public static GroupNode findFirstSubEntry(GroupNode group, String appDef) {
		for (GroupNode g : group.getGroupNodes()) {
			if (isNXClass(g, NexusConstants.SUBENTRY)) {
				String definition = NexusTreeUtils.getFirstString(group.getAttribute("definition"));
				if (appDef.equals(definition)) {
					return g;
				}
			}
		}

		return null;
	}

	/**
	 * Find first detector group in first entry's first instrument
	 * @param tree tree
	 * @return detector group
	 * @throws NexusException 
	 */
	public static GroupNode findFirstDetector(Tree tree) throws NexusException {
		GroupNode g = requireGroupNode(tree.getGroupNode(), NexusConstants.ENTRY);
		g = requireGroupNode(g, NexusConstants.INSTRUMENT);
		return requireGroupNode(g, NexusConstants.DETECTOR);
	}

	/**
	 * Find node links in group to be of given Nexus class
	 * @param group
	 * @param clazz (can be null but only if prefix is not null)
	 * @return list of nodes link
	 */
	public static List<NodeLink> findNodes(GroupNode group, final String clazz) {
		return findNodes(group, null, clazz);
	}

	/**
	 * Find node links in group with prefix as name to be of given Nexus class
	 * @param group
	 * @param prefix (can be null but only if clazz is not null)
	 * @param clazz (can be null but only if prefix is not null)
	 * @return list of nodes link
	 */
	public static List<NodeLink> findNodes(GroupNode group, final String prefix, final String clazz) {
		if (prefix == null && clazz == null) {
			throw new IllegalArgumentException("Prefix or clazz must be defined");
		}
		List<NodeLink> nodes = new ArrayList<>();
		for (NodeLink l : group) {
			if (prefix != null && !l.getName().startsWith(prefix)) {
				continue;
			}
			if (clazz == null || isNXClass(l.getDestination(), clazz)) {
				nodes.add(l);
			}
		}
		return nodes;
	}

	/**
	 * @param attr
	 * @return string or null
	 */
	public static String getFirstString(Attribute attr) {
		return attr != null && attr.isString() ? attr.getFirstElement() : null;
	}

	/**
	 * @param attr
	 * @return string or null
	 */
	public static String[] getStringArray(Attribute attr) {
		if (attr == null || !attr.isString()) {
			return null;
		}
	
		return ((StringDataset) DatasetUtils.convertToDataset(attr.getValue())).getData();
	}

	/**
	 * Check if node has given NXclass attribute
	 * @param node
	 * @param clazz
	 * @return true if it does
	 */
	public static boolean isNXClass(Node node, String clazz) {
		if (node == null) {
			return false;
		}
		String nxClass = getFirstString(node.getAttribute(NexusConstants.NXCLASS));
		return clazz.equals(nxClass);
	}

	/**
	 * Parse elements of attribute as string array. Converts if parsable
	 * @param attr
	 * @return string array or null if attribute does not exist
	 */
	public static String[] parseStringArray(Attribute attr) {
		if (attr == null)
			return null;

		return attr.getSize() == 1 ? parseString(attr.getFirstElement()) :
			DatasetUtils.cast(StringDataset.class, attr.getValue()).getData();
	}

	/**
	 * Get elements of data node as string array. Converts if exists
	 * @param n
	 * @return string array or null if not a data node
	 */
	public static String[] getStringArray(Node n) {
		if (!(n instanceof DataNode)) {
			return null;
		}

		StringDataset sd = getCastAndCacheData((DataNode) n, StringDataset.class);
		return sd != null ? sd.getData() : null;
	}

	/**
	 * Get elements of data node as string array. Converts if exists
	 * @param n
	 * @param length
	 * @return string array or null if not a data node
	 * @throws IllegalArgumentException if node exists and is not of required length
	 */
	public static String[] getStringArray(Node n, int length) {
		String[] array = getStringArray(n);
		if (array != null && array.length != length) {
			throw new IllegalArgumentException("Data node does not have array of required length");
		}
		return array;
	}

	/**
	 * Get first element of data node as string array. Converts if exists
	 * @param n
	 * @return string or null if not a data node
	 * @throws IllegalArgumentException if node exists and is not of unit length
	 */
	public static String getSingleString(Node n) {
		String[] array = getStringArray(n, 1);
		return array == null ? null : array[0];
	}

	/**
	 * Get first element of data node as String. Converts if exists
	 * @param n node
	 * @return string value
	 * @throws NexusException if node is null or not a data node
	 */
	public static String getFirstString(Node n) throws NexusException {
		if (!(n instanceof DataNode)) {
			throw new NexusException("Node is null or not a data node");
		}

		StringDataset sd = getCastAndCacheData((DataNode) n, StringDataset.class);
		if (sd == null) {
			throw new NexusException("Dataset is not defined in data node");
		}
		return sd.getString();
	}

	/**
	 * Parse first element of attribute as integer. Converts if parsable
	 * @param attr
	 * @return integer
	 */
	public static int parseFirstInt(Attribute attr) {
		if (attr.isString()) {
			return Integer.parseInt(attr.getFirstElement());
		}
		Dataset attrd = DatasetUtils.convertToDataset(attr.getValue());
		return attrd.getInt();
	}

	/**
	 * Parse elements of attribute as integer array. Converts if parsable
	 * @param attr
	 * @param length
	 * @return integer array
	 * @throws IllegalArgumentException if attribute exists and is not of required length
	 */
	public static int[] parseIntArray(Attribute attr, int length) {
		int[] array = parseIntArray(attr);
		if (array != null && array.length != length) {
			throw new IllegalArgumentException("Attribute does not have array of required length");
		}
		return array;
	}

	/**
	 * Parse elements of attribute as integer array. Converts if parsable
	 * @param attr
	 * @return integer array or null if attribute does not exist
	 */
	public static int[] parseIntArray(Attribute attr) {
		if (attr == null) {
			return null;
		}

		int[] array;
		if (attr.isString()) {
			String[] str = attr.getSize() == 1 ? parseString(attr.getFirstElement()) :
				DatasetUtils.cast(StringDataset.class, attr.getValue()).getData();
			array = new int[str.length];
			for (int i = 0; i < str.length; i++) {
				array[i] = Integer.parseInt(str[i]);
			}
		} else {
			IntegerDataset id = DatasetUtils.cast(IntegerDataset.class, attr.getValue());
			array = id.getData().clone();
		}
		return array;
	}

	/**
	 * Get elements of data node as integer array. Converts if exists
	 * @param n
	 * @param length
	 * @return integer array
	 * @throws IllegalArgumentException if node exists and is not of required length
	 */
	public static int[] getIntArray(Node n, int length) {
		int[] array = getIntArray(n);
		if (array == null || array.length != length) {
			throw new IllegalArgumentException("Data node does not have array of required length");
		}
		return array;
	}

	/**
	 * Get first element of data node as integer array. Converts if exists
	 * @param n
	 * @return int
	 * @throws IllegalArgumentException if node exists and is not of unit length
	 */
	public static int getSingleInt(Node n) {
		int[] array = getIntArray(n, 1);
		return array[0];
	}

	/**
	 * Get elements of data node as integer array. Converts if exists
	 * @param n
	 * @return integer array or null if not a data node or data node is empty
	 */
	public static int[] getIntArray(Node n) {
		if (!(n instanceof DataNode)) {
			return null;
		}

		IntegerDataset id = getCastAndCacheData((DataNode) n, IntegerDataset.class);
		if (id == null) {
			return null;
		}
		return id.getData();
	}

	/**
	 * Get first element of data node as double. Converts if exists
	 * @param n node
	 * @return double value
	 * @throws NexusException if node is null or not a data node
	 */
	public static int getFirstInt(Node n) throws NexusException {
		if (!(n instanceof DataNode)) {
			throw new NexusException("Node is null or not a data node");
		}

		IntegerDataset id = getCastAndCacheData((DataNode) n, IntegerDataset.class);
		if (id == null) {
			throw new NexusException("Dataset is not defined in data node");
		}
		return id.getInt();
	}

	/**
	 * Parse first element of attribute as double. Converts if parsable
	 * @param attr
	 * @return double
	 */
	public static double parseFirstDouble(Attribute attr) {
		if (attr.isString()) {
			return Double.parseDouble(attr.getFirstElement());
		}
		Dataset attrd = DatasetUtils.convertToDataset(attr.getValue());
		return attrd.getDouble();
	}

	/**
	 * Parse elements of attribute as double array. Converts if parsable
	 * @param attr
	 * @param length
	 * @return double array
	 * @throws IllegalArgumentException if attribute exists and is not of required length
	 */
	public static double[] parseDoubleArray(Attribute attr, int length) {
		double[] array = parseDoubleArray(attr);
		if (array != null && array.length != length) {
			throw new IllegalArgumentException("Attribute does not have array of required length");
		}
		return array;
	}

	/**
	 * Parse elements of attribute as double array. Converts if parsable
	 * @param attr
	 * @return double array or null if attribute does not exist
	 */
	public static double[] parseDoubleArray(Attribute attr) {
		if (attr == null) {
			return null;
		}

		double[] array;
		if (attr.isString()) {
			String[] str = attr.getSize() == 1 ? parseString(attr.getFirstElement()) :
				DatasetUtils.cast(StringDataset.class, attr.getValue()).getData();
			array = new double[str.length];
			for (int i = 0; i < str.length; i++) {
				array[i] = Double.parseDouble(str[i]);
			}
		} else {
			DoubleDataset dd = DatasetUtils.cast(DoubleDataset.class, attr.getValue());
			array = dd.getData().clone();
		}
		return array;
	}

	/**
	 * Get elements of data node as double array. Converts if exists
	 * @param n
	 * @param length
	 * @return double array
	 * @throws IllegalArgumentException if node exists and is not of required length
	 */
	public static double[] getDoubleArray(Node n, int length) {
		double[] array = getDoubleArray(n);
		if (array == null || array.length != length) {
			throw new IllegalArgumentException("Data node does not have array of required length");
		}
		return array;
	}

	/**
	 * Parse elements of data node as double array. Converts if exists
	 * @param n
	 * @return double array or null if not a data node or data node is empty
	 */
	public static double[] getDoubleArray(Node n) {
		if (!(n instanceof DataNode)) {
			return null;
		}

		DoubleDataset dd = getCastAndCacheData((DataNode) n, DoubleDataset.class);
		if (dd == null) {
			return null;
		}
		return dd.getData();
	}

	/**
	 * Get first element of data node as double. Converts if exists
	 * @param n node
	 * @return double value
	 * @throws NexusException if node is null or not a data node
	 */
	public static double getFirstDouble(Node n) throws NexusException {
		if (!(n instanceof DataNode)) {
			throw new NexusException("Node is null or not a data node");
		}

		DoubleDataset dd = getCastAndCacheData((DataNode) n, DoubleDataset.class);
		if (dd == null) {
			throw new NexusException("Dataset is not defined in data node");
		}
		return dd.getDouble();
	}

	/**
	 * Parse out items which are comma- or colon-separated
	 * @param s string can be enclosed by square brackets
	 * @return arrays of strings
	 */
	private static String[] parseString(String s) {
		s = s.trim();
		if (s.startsWith("[")) { // strip opening and closing brackets
			s = s.substring(1, s.length() - 1);
		}
	
		return s.split("[:,]");
	}

	private static Dataset getAndCacheData(DataNode dNode) {
		return getCastAndCacheData(dNode, null);
	}

	private static <D extends Dataset> D getCastAndCacheData(DataNode dNode, Class<D> clazz) {
		return getCastAndCacheData(dNode, 1, clazz);
	}

	@SuppressWarnings("unchecked")
	private static <D extends Dataset> D getCastAndCacheData(DataNode dNode, int itemSize, Class<D> clazz) {
		ILazyDataset ld = dNode.getDataset();
		Dataset dataset;
		if (ld == null) {
			return null;
		}
		if (ld instanceof Dataset td) {
			dataset = td;
		} else {
			try {
				dataset = DatasetUtils.sliceAndConvertLazyDataset(ld);
			} catch (DatasetException e) {
				logger.error("Could not get data from lazy dataset", e);
				return null;
			}
			dNode.setDataset(dataset);
		}
		if (clazz != null && !dataset.getClass().equals(clazz)) {
			if (dataset instanceof CompoundDataset cd) {
				dataset = DatasetUtils.createDatasetFromCompoundDataset(cd, true);
			}
			boolean asCompound = false;
			if (InterfaceUtils.isCompound(clazz)) {
				Class<?> ec = InterfaceUtils.getElementClass(clazz);
				dataset = dataset.cast(InterfaceUtils.getInterfaceFromClass(1, ec));
				asCompound = true;
			} else {
				dataset = dataset.cast(clazz);
				asCompound = itemSize > 1;
			}
			if (asCompound) {
				int r = dataset.getRank();
				if (r > 1 && dataset.getShapeRef()[r] == itemSize) {
					dataset = DatasetUtils.createCompoundDatasetFromLastAxis(dataset, true);
				} else {
					dataset = DatasetUtils.createCompoundDataset(dataset, itemSize);
				}
			}
			dNode.setDataset(dataset);
		}
		Unit<?> unit = parseUnit(getFirstString(dNode.getAttribute(NexusConstants.UNITS)));
		if (unit != null) {
			try {
				dataset.addMetadata(MetadataFactory.createMetadata(UnitMetadata.class, unit));
			} catch (MetadataException e) {
				// do nothing
			}
		}
		return (D) dataset;
	}

	private static DoubleDataset getConvertedData(DataNode data, Unit<? extends Quantity<?>> unit) {
		DoubleDataset values = getCastAndCacheData(data, DoubleDataset.class);
		if (values != null) {
			values = values.clone(); // necessary to stop clobbering cached values
			UnitMetadata um = values.getFirstMetadata(UnitMetadata.class);
			convertIfNecessary(unit, um == null ? null : um.getUnit(), values.getData());
		}
		return values;
	}

	private static void convertIfNecessary(Unit<?> unit, String attr, double[] values) {
		convertIfNecessary(unit, parseUnit(attr), values);
	}

	private static void convertIfNecessary(Unit<?> nUnit, Unit<?> oUnit, double[] values) {
		UnitConverter c = getConvertIfNecessary(nUnit, oUnit);
		if (c != null) {
			for (int i = 0, imax = values.length; i < imax; i++) {
				values[i] = c.convert(values[i]);
			}
		}
	}

	private static double convertIfNecessary(Unit<?> nUnit, Unit<?> oUnit, double value) {
		UnitConverter c = getConvertIfNecessary(nUnit, oUnit);
		if (c != null) {
			return c.convert(value);
		}
		return value;
	}

	private static UnitConverter getConvertIfNecessary(Unit<?> nUnit, Unit<?> oUnit) {
		if (oUnit != null && nUnit != null && !nUnit.equals(oUnit)) {
			try {
				return oUnit.getConverterToAny(nUnit);
			} catch (UnconvertibleException | IncommensurableException e) {
				String error = MessageFormat.format("Could not convert attribute''s ({}) unit to given unit ({})", oUnit, nUnit);
				logger.error(error, e);
				throw new IllegalArgumentException(error);
			}
		}
		return null;
	}

	static Unit<?> parseUnit(String attr) {
		if (attr != null) {
			try {
				return SimpleUnitFormat.getInstance().parse(attr);
			} catch (Exception e) {
				logger.error("Could not parse unit '{}'", attr, e);
			}
		}
		return null;
	}

	private static final String RELATIVE_PREFIX = ".";
	private static final String CURRENT_DIR_PREFIX = "./";

	/**
	 * @param dp
	 * @return group containing fields and classes for a detector
	 */
	public static GroupNode createNXDetector(DetectorProperties dp) {
		GroupNode g = createNXGroup(NexusConstants.DETECTOR);

		return addDetectorProperties(dp, g);
	}
	
	/**
	 * @param dp
	 * @return group containing fields and classes for a detector
	 */
	public static GroupNode addDetectorProperties(DetectorProperties dp, GroupNode g) {

		addDataNode(g, DETECTOR_DISTANCE, dp.getBeamCentreDistance(), "mm");

		double[] bc = dp.getBeamCentreCoords();
		addDataNode(g, DETECTOR_BEAMCENTERX, dp.getHPxSize()*bc[0], "mm");
		addDataNode(g, DETECTOR_BEAMCENTERY, dp.getVPxSize()*bc[1], "mm");
		
		addDataNode(g, DETECTOR_XPIXELSIZE, dp.getHPxSize(), "mm");
		addDataNode(g, DETECTOR_YPIXELSIZE, dp.getVPxSize(), "mm");

		addDataNode(g, TRANSFORMATIONS_DEPENDSON, CURRENT_DIR_PREFIX + "transformations/euler_c", null);

		GroupNode sg = createNXGroup(NexusConstants.DETECTORMODULE);
		g.addGroupNode("detector_module", sg);

		addDataNode(sg, DMOD_DATAORIGIN, new int[] {0, 0}, null);
		addDataNode(sg, DMOD_DATASIZE, new int[] {dp.getPy(), dp.getPx()}, null);

		double[] zeros = new double[3]; 
		addNXTransform(sg, DMOD_MODULEOFFSET, "mm", true, zeros, zeros, "mm", "../transformations/euler_c", 0);
		addNXTransform(sg, DMOD_FASTPIXELDIRECTION, "mm", true, new double[] {-1,0,0}, zeros, "mm", DMOD_MODULEOFFSET, dp.getHPxSize());
		addNXTransform(sg, DMOD_SLOWPIXELDIRECTION, "mm", true, new double[] {0,-1,0}, zeros, "mm", DMOD_MODULEOFFSET, dp.getVPxSize());

		sg = createNXGroup(NexusConstants.TRANSFORMATIONS);
		g.addGroupNode("transformations", sg);

		Matrix3d inv = new Matrix3d();
		inv.transpose(dp.getOrientation());
		double[] angles = MatrixUtils.calculateEulerZYZ(inv);
		// Euler ZYZ angles
		addNXTransform(sg, "euler_a", "deg", false, new double[] {0,0,1}, zeros, "mm", "origin_offset", angles[2]);
		addNXTransform(sg, "euler_b", "deg", false, new double[] {0,1,0}, zeros, "mm", "euler_a", angles[1]);
		addNXTransform(sg, "euler_c", "deg", false, new double[] {0,0,1}, zeros, "mm", "euler_b", angles[0]);
		Vector3d v = new Vector3d(dp.getOrigin());
		double d = v.length();
		v.normalize();
		double[] dv = new double[3];
		v.get(dv);
		addNXTransform(sg, "origin_offset", "mm", true, dv, zeros, "mm", TRANSFORMATIONS_ROOT, d);

		return g;
	}
	
	
	private static boolean addRelative(String dependsOn) {
		if (dependsOn.equals(TRANSFORMATIONS_ROOT) || dependsOn.startsWith(Tree.ROOT)) {
			return false;
		}
		return !dependsOn.startsWith(RELATIVE_PREFIX);
	}

	public static DataNode createNXTransform(String name, String units, boolean translation, double[] direction, double[] offset, String offsetUnits, String dependsOn, Object values) {
		DataNode d = createDataNode(name, values, units);
		d.addAttribute(TreeFactory.createAttribute(TRANSFORMATIONS_TYPE, translation ? TRANSFOMATIONS_TRANSLATION : TRANSFOMATIONS_ROTATION));
		d.addAttribute(TreeFactory.createAttribute(TRANSFORMATIONS_VECTOR, direction));
		d.addAttribute(TreeFactory.createAttribute(TRANSFORMATIONS_OFFSET, offset));
		d.addAttribute(TreeFactory.createAttribute(TRANSFORMATIONS_OFFSET_UNITS, offsetUnits));
		if (addRelative(dependsOn)) {
			dependsOn = CURRENT_DIR_PREFIX.concat(dependsOn);
		}
		d.addAttribute(TreeFactory.createAttribute(TRANSFORMATIONS_DEPENDSON, dependsOn));
		return d;
	}

	public static void addNXTransform(GroupNode group, String name, String units, boolean translation, double[] direction, double[] offset, String offsetUnits, String dependsOn, Object values) {
		group.addDataNode(name, createNXTransform(name, units, translation, direction, offset, offsetUnits, dependsOn, values));
	}

	/**
	 * @param nxClass
	 * @return group of given NXclass
	 */
	public static GroupNode createNXGroup(String nxClass) {
		GroupNode g = TreeFactory.createGroupNode(0);
		g.addAttribute(TreeFactory.createAttribute(NexusConstants.NXCLASS, nxClass));

		return g;
	}

	/**
	 * Add a data note to a group
	 * @param group
	 * @param name
	 * @param value
	 * @param units can be null
	 */
	public static void addDataNode(GroupNode group, String name, Object value, String units) {
		group.addDataNode(name, createDataNode(name, value, units));
	}

	/**
	 * Create a data note
	 * @param name
	 * @param value
	 * @param units can be null
	 * @return data node
	 */
	public static DataNode createDataNode(String name, Object value, String units) {
		DataNode d = TreeFactory.createDataNode(0);
		if (units != null && !units.isEmpty()) {
			d.addAttribute(TreeFactory.createAttribute(NexusConstants.UNITS, units));
		}
		if (value instanceof ILazyDataset) {
			d.setDataset((ILazyDataset) value);
		} else {
			Dataset vd = DatasetFactory.createFromObject(value);
			vd.setName(name);
			d.setDataset(vd);
		}
		return d;
	}
}
