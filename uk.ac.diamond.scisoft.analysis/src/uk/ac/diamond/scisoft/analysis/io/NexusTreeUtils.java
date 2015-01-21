/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Quantity;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.axis.AxisChoice;

public class NexusTreeUtils {

	protected static final Logger logger = LoggerFactory.getLogger(NexusTreeUtils.class);

	static class Transform {
		String name;
		Matrix4d matrix;
	}

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
	public static final String NX_DETECTOR = "NXdetector";
	public static final String NX_DETECTOR_MODULE = "NXdetector_module";
	public static final String NX_GEOMETRY = "NXgeometry";
	public static final String NX_TRANSLATION = "NXtranslation";
	public static final String NX_ORIENTATION = "NXorientation";
	public static final String NX_TRANSFORMATIONS = "NXtransformations";
	public static final String NX_TRANSFORMATIONS_ROOT = ".";

	public static void augmentTree(Tree tree) {
		augmentNodeLink(tree.getNodeLink(), true);
	}

	/**
	 * Augment a node with metadata that is pointed by link
	 * @param link
	 * @param isAxisFortranOrder in most cases, this should be set to true
	 */
	public static void augmentNodeLink(NodeLink link, final boolean isAxisFortranOrder) {
		if (link.isDestinationSymbolic()) {
			SymbolicNode sn = (SymbolicNode) link.getDestination();
			augmentNodeLink(sn.getNodeLink(), isAxisFortranOrder);
			return;
		}

		if (link.isDestinationGroup()) {
			GroupNode gn = (GroupNode) link.getDestination();
			for (NodeLink l : gn) {
				augmentNodeLink(l, isAxisFortranOrder);
			}
			return;
		}

		DataNode dNode = (DataNode) link.getDestination();
		if (dNode.isAugmented()) {
			logger.debug("Node has already been augmented: {}", link);
			return;
		}
		dNode.setAugmented();

		if (!dNode.isSupported()) {
			logger.warn("Node is not supported: {}", link);
			return;
		}

		if (!isNXClass(dNode, SDS)) {
			logger.trace("Data node does not have {} attribute: {}", NX_CLASS, link);
		}

		ILazyDataset cData = dNode.getDataset();
		if (cData == null || cData.getSize() == 0) {
			logger.warn("Chosen data {}, has zero size", dNode);
			return;
		}

		// find possible @long_name
		String string = parseStringAttr(dNode, NX_NAME);
		if (string != null && string.length() > 0) {
			cData.setName(string);
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
		Attribute stringAttr = dNode.getAttribute(NX_SIGNAL);
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
				String n = parseStringAttr(d, NX_NAME);
				if (n != null)
					choice.setLongName(n);

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

				Attribute attrLabel = null;
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
									logger.debug("Axis attribute {} does not match shape", a.getName());
									break;
								}
							}
						} else {
							intAxis = null;
							logger.debug("Axis attribute {} does not match rank", a.getName());
						}
					}
				}

				if (intAxis == null) {
					// remedy bogus or missing @axis by simply pairing matching dimension
					// lengths to the signal dataset shape (this may be wrong as transposes in
					// common dimension lengths can occur)
					logger.debug("Creating index mapping from axis shape");
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
				logger.debug("Axis attributes in {} are invalid - {}", a.getName(), e.getMessage());
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
	}

	public static void parseDetector(Matrix4d m, NodeLink link) {
		if (!link.isDestinationGroup())
			return;
		GroupNode gNode = (GroupNode) link.getDestination();
		if (!isNXClass(gNode, NX_DETECTOR) || !isNXClass(gNode, NX_DETECTOR_MODULE))
			return;
	
		for (NodeLink l : gNode) {
			String nxClass = parseStringAttr(l.getDestination(), NX_CLASS);
			switch(nxClass) {
			case NX_DETECTOR_MODULE:
				parseDetector(m, l);
				break;
			case NX_GEOMETRY:
				parseGeometry(m, l);
				break;
			case NX_TRANSFORMATIONS:
				Map<String, Transform> trans = new HashMap<String, Transform>();
				parseTransformation(l, trans);
				{
					Transform t = trans.get(NX_TRANSFORMATIONS_ROOT);
					do {
						m.mul(t.matrix, m);
						t = trans.get(t.name);
					} while (t != null);
				}
				break;
			default:
				break;
			}
		}
	}

	public static void parseGeometry(Matrix4d m, NodeLink link) {
		if (!link.isDestinationGroup())
			return;
		GroupNode gNode = (GroupNode) link.getDestination();
	
		// find orientation first as parseSubGeometry multiplies from the right
		NodeLink l = gNode.getNodeLink("orientation");
		if (l != null) {
			if (isNXClass(l.getDestination(), NX_ORIENTATION))
				parseSubGeometry(m, l, false);
		}
		l = gNode.getNodeLink("translation");
		if (l != null) {
			if (isNXClass(l.getDestination(), NX_TRANSLATION))
				parseSubGeometry(m, l, true);
		}
	}

	public static void parseSubGeometry(Matrix4d m, NodeLink link, boolean translate) {
		if (!link.isDestinationGroup())
			return;
	
		GroupNode gNode = (GroupNode) link.getDestination();
	
		NodeLink l = gNode.getNodeLink(translate ? "distances" : "value");
		if (l == null || !l.isDestinationData()) {
			throw new IllegalArgumentException("Geometry subnode is missing dataset");
		}
		DataNode dNode = (DataNode) l.getDestination();
		ILazyDataset d = dNode.getDataset();
		int[] shape = d.getShape();
		if (shape.length != 2 || shape[1] != (translate ? 3 : 6)) {
			throw new IllegalArgumentException("Geometry subnode has wrong shape");
		}
		DoubleDataset ds = (DoubleDataset) DatasetUtils.cast(d.getSlice(), Dataset.FLOAT64);
		double[] da = ds.getData();
		Matrix4d m2 = new Matrix4d();
		if (translate) {
			m2.setIdentity();
			m2.setColumn(3, da[0], da[1], da[2], 1);
		} else {
			m2.setRow(0, da[0], da[1], da[2], 0);
			m2.setRow(1, da[3], da[4], da[5], 0);
			m2.setRow(2, -da[2], -da[5], da[0]*da[4] - da[1]*da[3], 0);
			m2.setElement(3, 3, 1);
		}
		m.mul(m2); // right multiply
	
		l = gNode.getNodeLink(NX_GEOMETRY);
		if (l != null) {
			parseGeometry(m, l);
		}
	}

	// XXX need to form list and reorder according to dependency chain
	// use a map where key is depend on name then can work back from root
	public static void parseTransformation(NodeLink link, Map<String, Transform> trans) {
		if (!link.isDestinationData())
			return;
		DataNode dNode = (DataNode) link.getDestination();
		String units = parseStringAttr(dNode, "units");
		double[] vector = parseDoubleArray(dNode.getAttribute("vector"));
		double value = dNode.getDataset().getSlice().getDouble(0);
		Matrix4d m4 = null;
		String type = parseStringAttr(dNode, "transformation_type");
		Unit<? extends Quantity> u;
		switch(type) {
		case "translate":
			Matrix3d m3 = new Matrix3d();
			m3.setIdentity();
			Vector3d v3 = new Vector3d(vector);
			v3.scale(value);
			m4 = new Matrix4d(m3, v3, 1);
			break;
		case "rotate":
			u = Unit.valueOf(units);
			if (!u.equals(SI.RADIAN)) {
				value = u.getConverterTo(SI.RADIAN).convert(value);
			}
			AxisAngle4d aa = new AxisAngle4d(new Vector3d(vector), value);
			m4 = new Matrix4d();
			m4.set(aa);
			break;
		default:
			throw new IllegalArgumentException("Transformations node has wrong type");
		}
		double[] offset = parseDoubleArray(dNode.getAttribute("offset"));
		String ounits = parseStringAttr(dNode, "offset_units");
		if (offset != null) {
			int imax = offset.length;
			if (imax != 0 || imax != 3) {
				throw new IllegalArgumentException("Offset must be a 3D vector");
			}
			if (units != null && !units.equals(ounits)) {
				// do conversion
				u = Unit.valueOf(units);
				Unit<? extends Quantity> ou = Unit.valueOf(ounits);
				UnitConverter c = ou.getConverterTo(u);
				for (int i = 0; i < imax; i++) {
					offset[i] = c.convert(offset[i]);
				}
			}
			for (int i = 0; i < imax; i++) {
				m4.setElement(i, 3, offset[i] + m4.getElement(i, 3));
			}
		}
	
		Transform t = new Transform();
		t.name = link.getName();
		String dep = parseStringAttr(dNode, "depends_on");
		if (dep != null && !NX_TRANSFORMATIONS_ROOT.equals(dep)) {
			Node sNode = link.getSource();
			if (sNode != null && sNode instanceof GroupNode) {
				NodeLink dl = ((GroupNode) sNode).getNodeLink(dep);
				if (dl != null) {
					t.matrix = m4;
					trans.put(dep, t);
					parseTransformation(dl, trans);
					return;
				}
			}
			throw new IllegalArgumentException("Depends_on attribute is missing or contains invalid name");
		}
		trans.put(NX_TRANSFORMATIONS_ROOT, t);
	}

	/**
	 * @param node
	 * @param attr
	 * @return string or null
	 */
	public static String parseStringAttr(Node node, String attr) {
		Attribute stringAttr = node.getAttribute(attr);
		return stringAttr != null && stringAttr.isString() ? stringAttr.getFirstElement() : null;
	}

	/**
	 * Check if node has given NXclass attribute
	 * @param node
	 * @param clazz
	 * @return true if it does
	 */
	public static boolean isNXClass(Node node, String clazz) {
		String nxClass = parseStringAttr(node, NX_CLASS);
		return clazz.equals(nxClass);
	}

	/**
	 * Parse first element of attribute as integer
	 * @param attr
	 * @return integer
	 */
	public static int parseFirstInt(Attribute attr) {
		if (attr.isString()) {
			int value = Integer.parseInt(attr.getFirstElement());
			return value;
		}
		IDataset attrd = attr.getValue();
		return attrd.getInt(0);
	}

	/**
	 * Parse elements of attribute as integer array
	 * @param attr
	 * @return integer array
	 */
	public static int[] parseIntArray(Attribute attr) {
		int[] array;
		if (attr.isString()) {
			String[] str = parseString(attr.getFirstElement());
			array = new int[str.length];
			for (int i = 0; i < str.length; i++) {
				array[i] = Integer.parseInt(str[i]);
			}
		} else {
			IntegerDataset id = (IntegerDataset) DatasetUtils.cast(attr.getValue(), Dataset.INT32);
			array = id.getData().clone();
		}
		return array;
	}

	/**
	 * Parse first element of attribute as double
	 * @param attr
	 * @return double
	 */
	public static double parseFirstDouble(Attribute attr) {
		if (attr.isString()) {
			double value = Double.parseDouble(attr.getFirstElement());
			return value;
		}
		IDataset attrd = attr.getValue();
		return attrd.getDouble(0);
	}

	/**
	 * Parse elements of attribute as double array
	 * @param attr
	 * @return double array
	 */
	public static double[] parseDoubleArray(Attribute attr) {
		double[] array;
		if (attr.isString()) {
			String[] str = parseString(attr.getFirstElement());
			array = new double[str.length];
			for (int i = 0; i < str.length; i++) {
				array[i] = Double.parseDouble(str[i]);
			}
		} else {
			DoubleDataset dd = (DoubleDataset) DatasetUtils.cast(attr.getValue(), Dataset.FLOAT64);
			array = dd.getData().clone();
		}
		return array;
	}

	private static String[] parseString(String s) {
		s = s.trim();
		if (s.startsWith("[")) { // strip opening and closing brackets
			s = s.substring(1, s.length() - 1);
		}
	
		return s.split("[:,]");
	}
}
