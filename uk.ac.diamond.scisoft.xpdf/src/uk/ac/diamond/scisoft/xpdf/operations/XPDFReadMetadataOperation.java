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
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NexusUtils;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamTrace;
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
			if (model.isReadSampleInfo()) {
				readAndAddSampleInfo(xpdfMeta, tree, ssm.getParent());
				readDataParameters(xpdfMeta, tree, ssm.getParent());
			}
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
		NXsample nxample = getNXsampleFromTree(xpdfMeta, tree, parent);
		XPDFTargetComponent sampleCompo = new XPDFTargetComponent(nxample, null);
		sampleCompo.setSample(true);
		
		xpdfMeta.setSampleData(sampleCompo);
	}

	private void readDataParameters(XPDFMetadataImpl xpdfMeta, Tree tree, ILazyDataset parent) {
		// Get the first NXdata from the tree
		NXdata data = getFirstSomething(tree, "NXdata");
		double countTime = data.getDouble("count_time");
		
		XPDFBeamTrace sampleIntegration = new XPDFBeamTrace();
		sampleIntegration.setAxisAngle(true);
		sampleIntegration.setCountingTime(countTime);
		sampleIntegration.setMonitorRelativeFlux(1.0); // FIXME: No value yet in the NeXus file, default to 1
		
		xpdfMeta.setSampleTrace(sampleIntegration);
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

	private NXsample getNXsampleFromTree(XPDFMetadataImpl xpdfMeta, Tree tree, ILazyDataset parent) {
		Map<String, NodeLink> nodeMap = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getSample(), true, null);

		if (nodeMap.size() < 1) throw new OperationException(this, "Sample information requested, but no NXsample data was found.");
		if (nodeMap.size() > 1) throw new OperationException(this, "Multiple NXsample data found. Giving up.");

		// Get the first (only) NXsample
		GroupNode sampleNode = (GroupNode) nodeMap.values().toArray(
				new NodeLink[nodeMap.size()])[0].getDestination();
		NXsample nxample = (NXsample) sampleNode;

		return nxample;
	}
	
	// find me a sample
	private static IFindInTree getSample() {
		return getSomething("NXsample");
	}
	
	// find me anything
	private static IFindInTree getSomething(String NXclass) {
		
		return new IFindInTree() {

			@Override
			public boolean found(NodeLink node) {
				if (node.getDestination() instanceof GroupNode) {
					Attribute nxClass = ((GroupNode) node.getDestination()).getAttribute("NX_class");
					if (nxClass != null
							&& nxClass.getFirstElement() != null
							&& nxClass.getFirstElement().equals(NXclass))
						return true;
				}
				return false;
			}
		};
	}
	
	private <T extends NXobject> T getFirstSomething(Tree tree, String NXclass) {

		Map<String, NodeLink> nodeMap = TreeUtils.treeBreadthFirstSearch(tree.getGroupNode(), getSomething(NXclass), true, null);
		GroupNode node = (GroupNode) nodeMap.values().toArray(
				new NodeLink[nodeMap.size()])[0].getDestination();
		// The cast has already been checked, since NXobject is a derived class of GroupNode
		@SuppressWarnings("unchecked")
		T tt = (T) node;
		
		return tt;
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
