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
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
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
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;
import org.eclipse.dawnsci.analysis.dataset.metadata.AxesMetadataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.axis.AxisChoice;
import uk.ac.diamond.scisoft.analysis.diffraction.MatrixUtils;

public class NexusTreeUtils {

	protected static final Logger logger = LoggerFactory.getLogger(NexusTreeUtils.class);

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
	public static final String NX_UNITS = "units";
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
		augmentNodeLink(tree instanceof TreeFile ? ((TreeFile) tree).getFilename() : null, tree.getNodeLink(), true);
	}

	/**
	 * Augment a node with metadata that is pointed by link
	 * @param filePath
	 * @param link
	 * @param isAxisFortranOrder in most cases, this should be set to true
	 */
	public static void augmentNodeLink(String filePath, NodeLink link, final boolean isAxisFortranOrder) {
		if (link.isDestinationSymbolic()) {
			SymbolicNode sn = (SymbolicNode) link.getDestination();
			augmentNodeLink(filePath, sn.getNodeLink(), isAxisFortranOrder);
			return;
		}

		if (link.isDestinationGroup()) {
			GroupNode gn = (GroupNode) link.getDestination();
			for (NodeLink l : gn) {
				augmentNodeLink(filePath, l, isAxisFortranOrder);
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
		if (filePath != null) {
			final Metadata meta = new Metadata();
			meta.setFilePath(filePath);
			cData.setMetadata(meta);
			// TODO Maybe	dNode.getAttributeNameIterator()
		}

		GroupNode gNode = (GroupNode) link.getSource(); // before hunting for axes
		Attribute stringAttr = dNode.getAttribute(NX_SIGNAL);
		boolean isSignal = false;
		if (stringAttr != null) {
			isSignal = true;
			if (parseFirstInt(stringAttr) != 1) {
				logger.warn("Node has {} attribute but is not 1: {}", NX_SIGNAL, link);
				isSignal = false;
			}
		} else {
			String sName = parseStringAttr(gNode, NX_SIGNAL);
			if (sName != null) {
				if (gNode.containsDataNode(sName)) {
					isSignal = dNode == gNode.getDataNode(sName);
				} else {
					logger.warn("Given signal {} does not exist in group {}", sName, gNode);
				}
			}
			if (!isSignal) {
				isSignal = gNode.containsDataNode(DATA) && dNode == gNode.getDataNode(DATA);
			}
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
							choice.setPrimary(1);
						}
					}
				}

				if (intAxis == null) {
					attr = d.getAttribute(NX_AXIS);
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
				choice.setAxisNumber(intAxis[intAxis.length-1]);
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
			String[] names = parseStringArray(axesAttr);
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
	 * Parse a group that is NXdetector class
	 * @param link
	 * @return an array of detector modules
	 */
	public static DetectorProperties[] parseDetector(NodeLink link) {
		if (!link.isDestinationGroup())
			return null;

		GroupNode gNode = (GroupNode) link.getDestination();
		if (!isNXClass(gNode, NX_DETECTOR))
			return null;

		Map<String, Transform> ftrans = new HashMap<String, Transform>();
		for (NodeLink l : gNode) {
			if (isNXClass(l.getDestination(), NX_TRANSFORMATIONS)) {
				parseTransformations(l, ftrans);
				break;
			}
		}

		Matrix4d m;
		NodeLink nl = gNode.getNodeLink("depends_on");
		if (nl != null && nl.isDestinationData()) {
			m = calcForwardTransform(ftrans, parseStringArray(nl.getDestination(), 1)[0]);
		} else {
			// reconstruct net transformation
			// In order to reconstruct dependency chain of transformations,
			// use a map where key is depend on name then can work back from root
			Map<String, Transform> btrans = new HashMap<String, Transform>();
			for (Transform t : ftrans.values()) {
				btrans.put(t.depend, t);
			}
			Transform t = btrans.get(NX_TRANSFORMATIONS_ROOT);
			m = new Matrix4d();
			m.setIdentity();
			do {
				m.mul(t.matrix, m); // left multiply as working back
				t = btrans.get(t.name);
			} while (t != null);
		}

		// XXX NX_GEOMETRY support or not?
		// with parseGeometry(m, l);

		List<DetectorProperties> detectors = new ArrayList<>();
		for (NodeLink l : gNode) {
			if (isNXClass(l.getDestination(), NX_DETECTOR_MODULE)) {
				detectors.add(parseSubDetector(ftrans, l));
			}
		}

		double[] beamCentre = new double[2];
		nl = gNode.getNodeLink("beam_centre_x");
		if (nl != null) {
			Node n = nl.getDestination();
			double[] c = parseDoubleArray(n, 1);
			beamCentre[0] = convertIfNecessary(SI.MILLIMETRE, parseStringAttr(n, NX_UNITS), c[0]);
		}
		
		nl = gNode.getNodeLink("beam_centre_y");
		if (nl != null) {
			Node n = nl.getDestination();
			double[] c = parseDoubleArray(n, 1);
			beamCentre[1] = convertIfNecessary(SI.MILLIMETRE, parseStringAttr(n, NX_UNITS), c[1]);
		}

		// determine beam direction from centre position
		Vector4d p4 = new Vector4d(beamCentre[0], beamCentre[1], 0, 1);
		m.transform(p4);
		Vector3d bv = new Vector3d(p4.x, p4.y, p4.z);
		bv.normalize();
		
		DetectorProperties[] da = detectors.toArray(new DetectorProperties[0]);
		for (DetectorProperties dp : da) {
			dp.setBeamVector(new Vector3d(bv));
		}
		return da;
	}

	public static DetectorProperties parseSubDetector(Map<String, Transform> ftrans, NodeLink link) {
		if (!link.isDestinationGroup())
			return null;
		GroupNode gNode = (GroupNode) link.getDestination();

		Transform mo = null;
		TransformedVectors fpd = null, spd = null;
		int[] origin = null, size = null;
		for (NodeLink l : gNode) {
			String name = l.getName();
			switch(name) {
			case "data_origin":
				origin = parseIntArray(l.getDestination(), 2);
				break;
			case "data_size":
				size = parseIntArray(l.getDestination(), 2);
				break;
			case "module_offset":
				mo  = parseTransformation(l);
				if (mo != null) {
					ftrans.put(mo.name, mo);
				}
				break;
			case "fast_pixel_direction":
				fpd = parseTransformedVectors(l);
 				break;
			case "slow_pixel_direction":
				spd = parseTransformedVectors(l);
				break;
			default:
				break;
			}
		}
		if (mo == null) {
			logger.error("No module offset defined");
		}
		if (size == null) {
			logger.error("No size defined");
			throw new IllegalArgumentException("No size defined");
		}
		if (origin == null) {
			logger.error("No origin defined");
			throw new IllegalArgumentException("No origin defined");
		}
		if (fpd == null) {
			logger.error("No fast direction defined");
			throw new IllegalArgumentException("No fast direction defined");
		} else if (fpd.magnitudes.length > 1) {
			logger.warn("Only using first value of fast pixel size");
		}
		if (spd == null) {
			logger.error("No slow direction defined");
			throw new IllegalArgumentException("No slow direction defined");
		} else if (spd.magnitudes.length > 1) {
			logger.warn("Only using first value of slow pixel size");
		}

		Vector3d off = new Vector3d();
		if (mo != null) {
			Matrix4d m = calcForwardTransform(ftrans, mo.depend);
			Vector4d v = new Vector4d();
			v.setW(1);
			off.set(transform(m, v));
		}

		Vector3d xdir = new Vector3d();
		Matrix4d m = calcForwardTransform(ftrans, fpd.depend);
		xdir.set(transform(m, fpd.vector));
		
		Vector3d ydir = new Vector3d();
		m = calcForwardTransform(ftrans, spd.depend);
		ydir.set(transform(m, spd.vector));

		Matrix3d ori = MatrixUtils.computeFSOrientation(xdir, ydir);
		DetectorProperties dp = new DetectorProperties(off, size[0], size[1], spd.magnitudes[0], fpd.magnitudes[0], ori);
		dp.setStartX(origin[1]);
		dp.setStartY(origin[0]);
		return dp;
	}

	// follow dependency chain forward and right multiply
	private static Matrix4d calcForwardTransform(Map<String, Transform> ftrans, String dep) {
		Matrix4d m = new Matrix4d();
		m.setIdentity();
		Transform t;
		do {
			t = ftrans.get(dep);
			m.mul(t.matrix);
			dep = t.depend;
		} while (!NX_TRANSFORMATIONS_ROOT.equals(dep));

		return m;
	}

	private static Vector3d transform(Matrix4d m, Vector4d v) {
		m.transform(v);
		Vector3d vout = new Vector3d();
		vout.set(v.x, v.y, v.z);
		return vout;
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
			convertIfNecessary(SI.MILLIMETRE, parseStringAttr(dNode, NX_UNITS), da);
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
		if (l != null && isNXClass(l.getDestination(), NX_GEOMETRY)) {
			parseGeometry(m, l);
		}
	}

	public static void parseTransformations(NodeLink link, Map<String, Transform> ftrans) {
		if (!link.isDestinationGroup())
			return;

		GroupNode gNode = (GroupNode) link.getDestination();

		for (NodeLink l : gNode) {
			Transform t = parseTransformation(l);
			if (t != null) {
				ftrans.put(t.name, t);
			}
		}
	}

	public static Transform parseTransformation(NodeLink link) {
		if (!link.isDestinationData())
			return null;
		DataNode dNode = (DataNode) link.getDestination();

		double value = dNode.getDataset().getSlice().getDouble(0);
		double[] vector = parseDoubleArray(dNode.getAttribute("vector"), 3);
		Vector3d v3 = new Vector3d(vector);
		Matrix4d m4 = null;
		String type = parseStringAttr(dNode, "transformation_type");
		String units = parseStringAttr(dNode, NX_UNITS);
		switch(type) {
		case "translation":
			Matrix3d m3 = new Matrix3d();
			m3.setIdentity();
			v3.normalize();
			v3.scale(convertIfNecessary(SI.MILLIMETRE, units, value));
			m4 = new Matrix4d(m3, v3, 1);
			break;
		case "rotation":
			AxisAngle4d aa = new AxisAngle4d(v3, convertIfNecessary(SI.RADIAN, units, value));
			m4 = new Matrix4d();
			m4.set(aa);
			break;
		default:
			throw new IllegalArgumentException("Transformations node has wrong type");
		}
		double[] offset = parseDoubleArray(dNode.getAttribute("offset"), 3);
		if (offset != null) {
			convertIfNecessary(SI.MILLIMETRE, parseStringAttr(dNode, "offset_units"), offset);
			for (int i = 0; i < 3; i++) {
				m4.setElement(i, 3, offset[i] + m4.getElement(i, 3));
			}
		}

		Transform t = new Transform();
		t.name = link.getName();
		String dep = parseStringAttr(dNode, "depends_on");
		t.depend = dep == null ? NX_TRANSFORMATIONS_ROOT : dep;
		t.matrix = m4;
		return t;
	}

	public static TransformedVectors parseTransformedVectors(NodeLink link) {
		if (!link.isDestinationData())
			return null;
		DataNode dNode = (DataNode) link.getDestination();
		double[] vector = parseDoubleArray(dNode.getAttribute("vector"), 3);
		Vector3d v3 = new Vector3d(vector);
		double[] values = ((DoubleDataset) DatasetUtils.cast(dNode.getDataset().getSlice(), Dataset.FLOAT64)).getData();
		convertIfNecessary(SI.MILLIMETRE, parseStringAttr(dNode, NX_UNITS), values);
		String type = parseStringAttr(dNode, "transformation_type");
		if (!"translation".equals(type)) {
			throw new IllegalArgumentException("Transformed vector node has wrong type");
		}
		v3.normalize();

		double[] offset = parseDoubleArray(dNode.getAttribute("offset"), 3);
		Vector3d o3 = new Vector3d();
		if (offset != null) {
			convertIfNecessary(SI.MILLIMETRE, parseStringAttr(dNode, "offset_units"), offset);
			o3.set(offset);
		}

		TransformedVectors tv = new TransformedVectors();
		String dep = parseStringAttr(dNode, "depends_on");
		tv.depend = dep == null ? NX_TRANSFORMATIONS_ROOT : dep;
		tv.magnitudes = values;
		tv.vector = new Vector4d(v3);
		tv.offset = new Vector4d(o3);
		tv.offset.setW(1);
		return tv;
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
	 * Parse elements of attribute as string array
	 * @param attr
	 * @return string array or null if attribute does not exist
	 */
	public static String[] parseStringArray(Attribute attr) {
		if (attr == null)
			return null;

		return attr.getSize() == 1 ? parseString(attr.getFirstElement()) :
			((StringDataset) DatasetUtils.cast(attr.getValue(), Dataset.STRING)).getData();
	}

	/**
	 * Parse elements of data node as string array
	 * @param n
	 * @return string array or null if not a data node
	 */
	public static String[] parseStringArray(Node n) {
		if (n == null || !(n instanceof DataNode))
			return null;

		StringDataset id = (StringDataset) DatasetUtils.cast(((DataNode) n).getDataset().getSlice(), Dataset.STRING);
		return id.getData();
	}

	/**
	 * Parse elements of data node as string array
	 * @param n
	 * @param length
	 * @return string array
	 * @throws IllegalArgumentException if node exists and is not of required length 
	 */
	public static String[] parseStringArray(Node n, int length) {
		String[] array = parseStringArray(n);
		if (array != null && array.length != length) {
			throw new IllegalArgumentException("Data node does not have array of required length");
		}
		return array;
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
	 * Parse elements of attribute as integer array
	 * @param attr
	 * @return integer array or null if attribute does not exist
	 */
	public static int[] parseIntArray(Attribute attr) {
		if (attr == null)
			return null;

		int[] array;
		if (attr.isString()) {
			String[] str = attr.getSize() == 1 ? parseString(attr.getFirstElement()) :
				((StringDataset) DatasetUtils.cast(attr.getValue(), Dataset.STRING)).getData();
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
	 * Parse elements of data node as integer array
	 * @param n
	 * @param length
	 * @return integer array
	 * @throws IllegalArgumentException if node exists and is not of required length 
	 */
	public static int[] parseIntArray(Node n, int length) {
		int[] array = parseIntArray(n);
		if (array != null && array.length != length) {
			throw new IllegalArgumentException("Data node does not have array of required length");
		}
		return array;
	}

	/**
	 * Parse elements of data node as integer array
	 * @param n
	 * @return integer array or null if not a data node
	 */
	public static int[] parseIntArray(Node n) {
		if (n == null || !(n instanceof DataNode))
			return null;

		IntegerDataset id = (IntegerDataset) DatasetUtils.cast(((DataNode) n).getDataset().getSlice(), Dataset.INT32);
		return id.getData();
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
	 * Parse elements of attribute as double array
	 * @param attr
	 * @return double array or null if attribute does not exist
	 */
	public static double[] parseDoubleArray(Attribute attr) {
		if (attr == null)
			return null;

		double[] array;
		if (attr.isString()) {
			String[] str = attr.getSize() == 1 ? parseString(attr.getFirstElement()) :
				((StringDataset) DatasetUtils.cast(attr.getValue(), Dataset.STRING)).getData();
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

	/**
	 * Parse elements of data node as integer array
	 * @param n
	 * @param length
	 * @return integer array
	 * @throws IllegalArgumentException if node exists and is not of required length 
	 */
	public static double[] parseDoubleArray(Node n, int length) {
		double[] array = parseDoubleArray(n);
		if (array != null && array.length != length) {
			throw new IllegalArgumentException("Data node does not have array of required length");
		}
		return array;
	}

	/**
	 * Parse elements of data node as double array
	 * @param n
	 * @return double array or null if not a data node
	 */
	public static double[] parseDoubleArray(Node n) {
		if (n == null || !(n instanceof DataNode))
			return null;

		DoubleDataset dd = (DoubleDataset) DatasetUtils.cast(((DataNode) n).getDataset().getSlice(), Dataset.FLOAT64);
		return dd.getData();
	}

	private static String[] parseString(String s) {
		s = s.trim();
		if (s.startsWith("[")) { // strip opening and closing brackets
			s = s.substring(1, s.length() - 1);
		}
	
		return s.split("[:,]");
	}

	private static double convertIfNecessary(Unit<? extends Quantity> unit, String attr, double value) {
		Unit<? extends Quantity> u = parseUnit(attr);
		if (u != null && !u.equals(unit)) {
			return u.getConverterTo(unit).convert(value);
		}
		return value;
	}

	private static void convertIfNecessary(Unit<? extends Quantity> unit, String attr, double[] values) {
		Unit<? extends Quantity> u = parseUnit(attr);
		if (u != null && !u.equals(unit)) {
			UnitConverter c = u.getConverterTo(unit);
			for (int i = 0, imax = values.length; i < imax; i++) {
				values[i] = c.convert(values[i]);
			}
		}
	}

	private static Unit<? extends Quantity> parseUnit(String attr) {
		if ("deg".equals(attr)) {
			return NonSI.DEGREE_ANGLE;
		}
		return attr != null ? Unit.valueOf(attr) : null;
	}
}
