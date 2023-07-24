/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Collection;
import java.util.Map;

import javax.vecmath.Matrix3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.IFindInTree;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Read DiffractionMetaData from Nexus file written by DAWN/GDA
 */
public class NexusDiffractionCalibrationReader {

	private static final Logger logger = LoggerFactory.getLogger(NexusDiffractionCalibrationReader.class);

	public static IDiffractionMetadata getDiffractionMetadataFromNexus(final String filePath, final ILazyDataset parent) throws DatasetException {
		return getDiffractionMetadataFromNexus(filePath, parent, null);
	}

	/**
	 * Read detector properties objects from the Nexus tree of the file specified by path.
	 * 
	 * @param path
	 * @param parent
	 * @param positions
	 * @return properties
	 * @throws NexusException 
	 */
	public static DetectorProperties[] getDetectors(String path, ILazyDataset parent, int... positions) throws NexusException {
		Tree tree = null;
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(path);
			tree = dh.getTree();
		} catch (Exception e1) {
			return null;
		}

		if (tree == null) return null;

		Map<String, NodeLink> dnl = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder(parent), true, null);

		if (dnl.size() != 1) return null;

		String key = dnl.keySet().iterator().next();

		return NexusTreeUtils.parseDetector("/" + key, tree, positions);
	}

	public static IDiffractionMetadata getDiffractionMetadataFromNexus(final String filePath, final ILazyDataset parent, String datasetName) throws DatasetException {

		Tree tree = null;
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(filePath);
			tree = dh.getTree();
		} catch (Exception e1) {
			return null;
		}
		
		if (tree == null) return null;

		// Search for all NXdetectors related to parent. If parent is null, then this results in finding all NXdetectors
		Map<String, NodeLink> dnl = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder(parent), false, null);
		
		if (parent != null && dnl.size() != 1) {
			logger.warn("Lazy datasets provided but NXdetector not found by equality test");
		}
		
		// If the above did not find exactly one NXdetector, try searching again, this time keying from the provided dataset name
		if (dnl.size() != 1 && datasetName != null){
			String s = stripDataName(datasetName);
			dnl = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder(s), true, null);
		}
		
		if (datasetName != null && dnl.size() != 1) {
			logger.warn("Dataset name provided but NXdetector not found by test");
		}
		
		// If the above did not find exactly one NX detector, try again, without filtering, and only get the first found
		if (dnl.size() != 1 && parent != null) dnl = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder((ILazyDataset)null), true, null);
		
		// If the above did not find exactly one NX detector, fail
		if (dnl.size() != 1) return null;
		
		String key = dnl.keySet().iterator().next();
		
		DetectorProperties[] d;
		try {
			d = NexusTreeUtils.parseDetector("/" + key, tree, 0);
		} catch (NexusException e) {
			throw new DatasetException(e);
		}
		
		if (d == null || d.length == 0) return trySAXS(dnl.get(key), tree, filePath);
		
		DetectorProperties dp = d[0];
		
		DiffractionCrystalEnvironment dce = tryBeam(tree);
		if (dce == null) dce = tryMonochromator(tree);
		if (dce == null) return null;
		
		return new DiffractionMetadata(filePath, dp, dce);
	}
	
	private static String stripDataName(String dataName) {
		String[] split = dataName.split("/");
		if (split == null || split.length < 3) return null;
		return split[split.length-2];
	}
	
	private static IDiffractionMetadata trySAXS(NodeLink detectorLink, Tree tree, String path) throws DatasetException {
		DetectorProperties saxs = null;
		try {
			saxs = NexusTreeUtils.parseSaxsDetector(detectorLink);
		} catch (Exception e) {
			//TODO warn
		}
		if (saxs == null) return null;
		legacySupport(saxs,detectorLink);
		DiffractionCrystalEnvironment dce = tryBeam(tree);
		if (dce == null) dce = tryMonochromator(tree);
		if (dce == null) return null;
		
		return new DiffractionMetadata(path, saxs, dce);
	}
	
	private static DiffractionCrystalEnvironment tryBeam(Tree tree) {
		NodeLink nl = findWavelengthNode(tree, NexusConstants.BEAM);
		if (nl == null) return null;
		DiffractionCrystalEnvironment dce = new DiffractionCrystalEnvironment();
		NexusTreeUtils.parseBeam(tree, nl, dce);
		
		return dce.getWavelength() == 0 ? null : dce;
	}
	
	private static DiffractionCrystalEnvironment tryMonochromator(Tree tree) {
		NodeLink nl = findWavelengthNode(tree, NexusConstants.MONOCHROMATOR);
		if (nl == null) return null;
		DiffractionCrystalEnvironment dce = new DiffractionCrystalEnvironment();
		NexusTreeUtils.parseMonochromator(tree, nl, dce);
		
		return dce.getWavelength() == 0 ? null : dce;
	}
	
	private static NodeLink findWavelengthNode(Tree tree, final String nxClass) {
		IFindInTree findbeam = new IFindInTree() {
			
			@Override
			public boolean found(NodeLink node) {
				Node source = node.getDestination();

				Attribute attribute = source.getAttribute(NexusConstants.NXCLASS);
				if (attribute == null) return false;
				String el = attribute.getFirstElement();
				if (el == null) return false;
				if (el.equals(nxClass)) return true;

				return false;
			}
		};
		
		Map<String, NodeLink> dnl1 = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), findbeam, true, null);
		
		if (dnl1.size() != 1) return null;
		
		String key = dnl1.keySet().iterator().next();
		
		return dnl1.get(key);
	}
	
	private static DetectorProperties legacySupport(DetectorProperties dp, NodeLink nl) throws DatasetException {
		
		Node n = nl.getDestination();
		if (n instanceof GroupNode) {
			GroupNode gn = (GroupNode)n;
			if (gn.containsDataNode("detector_orientation")) {
				IDataset slice = gn.getDataNode("detector_orientation").getDataset().getSlice();
				DoubleDataset dd = DatasetUtils.cast(DoubleDataset.class, slice);
				double[] bcc = dp.getBeamCentreCoords();
				dp.setBeamCentreCoords(new double[]{0,0});
				dp.setOrientation(new Matrix3d(dd.getData()));
				dp.setBeamCentreCoords(bcc);
			}
		}
		
		return dp;
		
	}
	
	private static IFindInTree getFinder(final ILazyDataset parent) {
		return new IFindInTree() {

			@Override
			public boolean found(NodeLink node) {

				Node dest = node.getDestination();

				if (dest instanceof GroupNode) {
					GroupNode dgn = (GroupNode)dest;
					Attribute attribute = dgn.getAttribute("NX_class");
					if (attribute == null) return false;
					String el = attribute.getFirstElement();
					if (el == null) return false;

					if (el.equals("NXdetector")){
						
						if (parent == null) return true;
						
						Collection<String> names = dgn.getNames();
						for (String name : names) {
							if (dgn.containsDataNode(name)) {
								if (dgn.getDataNode(name).getDataset().equals(parent)) return true;
							}
						}
						
						if (dest instanceof DataNode){
							return ((DataNode)dest).getDataset().equals(parent);
						}
					}
				}

				return false;

			}
		};
	}
	
	private static IFindInTree getFinder(final String name) {
		return new IFindInTree() {

			@Override
			public boolean found(NodeLink node) {

				Node dest = node.getDestination();

				if (dest instanceof GroupNode) {
					GroupNode dgn = (GroupNode)dest;
					Attribute attribute = dgn.getAttribute("NX_class");
					if (attribute == null) return false;
					String el = attribute.getFirstElement();
					if (el == null) return false;

					if (el.equals("NXdetector")){
						return node.getName().equals(name);
					}
				}

				return false;

			}
		};
	}
	
	public static IDataset getDetectorPixelMaskFromNexus(final String filePath, final ILazyDataset parent) throws Exception {
		Tree tree = null;
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(filePath);
			tree = dh.getTree();
		} catch (Exception e1) {
			return null;
		}

		Map<String, NodeLink> dnl = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder(parent), true, null);
		
		if (dnl.size() != 1 && parent != null) dnl = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getFinder((ILazyDataset)null), true, null);
		
		if (dnl.size() != 1) return null;
		
		String key = dnl.keySet().iterator().next();
		NodeLink nl = dnl.get(key);
		Node n = nl.getDestination();
		
		if (n instanceof GroupNode) {
			GroupNode g = (GroupNode)n;
			if (g.containsDataNode("pixel_mask")) {
				return g.getDataNode("pixel_mask").getDataset().getSlice();
			}
		}
		
		return null;
	}
}
