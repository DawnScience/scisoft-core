/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.IFindInTree;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.metadata.MaskMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NexusUtils;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;

public class XPDFReadMetadataOperation extends AbstractOperation<XPDFReadMetadataModel, OperationData> {

	@Override
	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {
		// Get the SliceFromSeriesMetadata
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		
		// Get the mask
		if (model.isReadMask())
			readAndAddMask(input, ssm.getFilePath(), ssm.getParent());
		
		// Get the detector calibration
		if (model.isReadDetectorCal())
			readAndAddDetectorCalibration(input, ssm.getFilePath(), ssm.getParent());
		
		if (model.isReadAnyXPDFMetadata()) {
		
			// Create the XPDF metadata object
			XPDFMetadataImpl xpdfMeta = new XPDFMetadataImpl();

			// Get the NeXus file tree 
//			IDataHolder dh;
			Tree tree;
			try {
//				dh = LoaderFactory.getData(ssm.getFilePath());
//				tree = dh.getTree();
				tree = NexusUtils.loadNexusTree(NexusFileHDF5.openNexusFileReadOnly(ssm.getFilePath()));
			} catch (Exception e1) {
				throw new OperationException(this, e1);
			}
			
			if (tree == null) throw new OperationException(this, "Error constructing file tree for " + ssm.getFilePath());

			// sample details
			if (model.isReadSampleInfo())
				readAndAddSampleInfo(xpdfMeta, tree, ssm.getParent());

			// beam details

			// TODO: container details
			// TODO: empty container data
			// TODO: empty beam data

			input.setMetadata(xpdfMeta);;
			
		}
		
		return new OperationData(input);
	}

	private void readAndAddSampleInfo(XPDFMetadataImpl xpdfMeta, Tree tree,
			ILazyDataset parent) {
		// Get the map of names to samples
		Map<String, NodeLink> nodeMap = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getSample(), true, null);

		if (nodeMap.size() < 1) throw new OperationException(this, "Sample information requested, but no NXsample data was found.");
		if (nodeMap.size() > 1) throw new OperationException(this, "Multiple NXsample data found. Giving up.");

		// Get the first (only) NXsample
		GroupNode sampleNode = (GroupNode) nodeMap.values().toArray(
				new NodeLink[nodeMap.size()])[0].getDestination();
		NXsample nxample = (NXsample) sampleNode;
		
//		NXsample nxample = (NXsample) nodeMap.values().toArray(new NodeLink[nodeMap.size()])[0].getDestination();
		XPDFTargetComponent sampleCompo = new XPDFTargetComponent(nxample, null);
		sampleCompo.setSample(true);
		
		xpdfMeta.setSampleData(sampleCompo);
	}

	// Get the mask
	private void readAndAddDetectorCalibration(IDataset input, String filePath,
			ILazyDataset parent) {
		// TODO: This method does not cope with multiple detectors
		IDiffractionMetadata diffraction = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(filePath, parent);
		if (diffraction != null)
			input.setMetadata(diffraction);
	}

	// Get the detector calibration
	private void readAndAddMask(IDataset input, String filePath, ILazyDataset parent) {
		// TODO: This method does not cope with multiple detectors
		IDataset firstMask = NexusDiffractionCalibrationReader.getDetectorPixelMaskFromNexus(filePath, parent);
		IDataset firstBooleanMask;
		if (firstMask != null) {
			firstBooleanMask = Comparisons.equalTo(firstMask, 0);
			input.setMetadata(new MaskMetadataImpl(firstBooleanMask));
		}
	}

	// find me a sample
	private static IFindInTree getSample() {
		return new IFindInTree() {

			@Override
			public boolean found(NodeLink node) {
				if (node.getDestination() instanceof GroupNode) {
					Attribute nxClass = ((GroupNode) node.getDestination()).getAttribute("NX_class");
					if (nxClass != null
							&& nxClass.getFirstElement() != null
							&& nxClass.getFirstElement().equals("NXsample"))
						return true;
				}
				return false;
			}

		};
	}
	
	
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFReadMetadataOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
