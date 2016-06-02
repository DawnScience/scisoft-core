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
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.metadata.MaskMetadataImpl;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NXbeam;
import org.eclipse.dawnsci.nexus.NXcontainer;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXdetector;
import org.eclipse.dawnsci.nexus.NXinstrument;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXsample;
import org.eclipse.dawnsci.nexus.NXshape;
import org.eclipse.dawnsci.nexus.NXslit;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusUtils;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamData;
import uk.ac.diamond.scisoft.xpdf.XPDFBeamTrace;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentCylinder;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentGeometry;
import uk.ac.diamond.scisoft.xpdf.XPDFComponentPlate;
import uk.ac.diamond.scisoft.xpdf.XPDFDetector;
import uk.ac.diamond.scisoft.xpdf.XPDFMetadataImpl;
import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;
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
				String nexusFilePath = ssm.getFilePath();
				NexusFile nexusFile = NexusFileHDF5.openNexusFileReadOnly(nexusFilePath);
				tree = NexusUtils.loadNexusTree(nexusFile);
			} catch (Exception e1) {
				throw new OperationException(this, e1);
			}
			
			if (tree == null) throw new OperationException(this, "Error constructing file tree for " + ssm.getFilePath());

			// sample details
			if (model.isReadSampleInfo()) {
				readAndAddSampleInfo(xpdfMeta, tree, ssm.getParent());
				readDataParameters(xpdfMeta, tree, ssm.getParent());
			}

			// container details and container data
			if (model.isReadContainerInfo() || model.isReadContainerData()) {
				readAndAddContainerInfo(xpdfMeta, tree, ssm.getParent());
			}

			// empty beam data
			if (model.isReadBeamData()) {
				readAndAddBeamData(xpdfMeta, tree, ssm.getParent());
			}
			
			// beam information (size, wavelength)
			if (model.isReadBeamInfo()) {
				readAndAddBeamInfo(xpdfMeta, tree, ssm.getParent());
			}
			
			// detector physical details
			if (model.isReadDetectorInfo()) {
				readAndAddDetector(xpdfMeta, tree, ssm.getParent());
			}
			
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
		XPDFBeamTrace sampleIntegration = traceFromTree(tree, false);
		xpdfMeta.setSampleTrace(sampleIntegration);
	}
	
	private XPDFBeamTrace traceFromTree(Tree tree, boolean addData) {
		// Get the first NXdata from the tree
		NXdata data = getFirstSomething(tree, "NXdata");
		double countTime = data.getDouble("count_time");
		
		XPDFBeamTrace componentIntegration = new XPDFBeamTrace();
		componentIntegration.setAxisAngle(true);
		componentIntegration.setCountingTime(countTime);
		componentIntegration.setMonitorRelativeFlux(1.0); // FIXME: No value yet in the NeXus file, default to 1

		// Get the data from the tree, and add it to the BeamTrace
		if (addData) {
			ILazyDataset iLazyDataset = data.getDataNode("data").getDataset();
			Dataset dataset = DatasetUtils.sliceAndConvertLazyDataset(iLazyDataset);
			componentIntegration.setTrace(dataset);
		}
		
		return componentIntegration;
	}
	
	/**
	 * Gets the container metadata.
	 * <p>
	 * Using the inside_of_file_name attribute in the container data, the
	 * container XPDFTargetComponent and XPDFBeamTrace are added to the XPDFMetadata. 
	 * @param xpdfMeta
	 * 				metadata object to add the container to
	 * @param tree
	 * 			the tree containing the location of the container file
	 * @param parent
	 * 				the dataset of the sample
	 */
	private void readAndAddContainerInfo(XPDFMetadataImpl xpdfMeta, Tree tree,
			ILazyDataset parent) {
		
		GroupNode containerFileNameNode = getFirstSomething(tree, "NXcontainer");
		String containerFileName = containerFileNameNode.getDataNode("inside_of_file_name").getString();//getAttribute("inside_of_file_name").getFirstElement();
		
		// Now open the relevant file, and get the tree
		NexusFile containerFile;
		Tree containerTree;
		try {
			containerFile = NexusFileHDF5.openNexusFileReadOnly(containerFileName);
			containerTree = NexusUtils.loadNexusTree(containerFile);
		} catch (Exception e1) {
			throw new OperationException(this, e1);
		}
		if (tree == null) throw new OperationException(this, "Error constructing container file tree from " + containerFileName);

		// Component details (material, density &c.) of the container
		NXsample nxampleContainer = getNXsampleFromTree(null, containerTree, null);
		XPDFTargetComponent containerCompo = new XPDFTargetComponent(nxampleContainer, null);
		containerCompo.setSample(false);
		
		// Shape of the container
		NXcontainer container = nxampleContainer.getChild("container", NXcontainer.class);
		NXshape shape = container.getChild("shape", NXshape.class);
		XPDFComponentGeometry geom = null;
		
		// TODO: should this be moved to a factory class?
		String shapeName = shape.getShapeScalar();
		if ("nxcylinder".equalsIgnoreCase(shapeName)) {
			geom = new XPDFComponentCylinder();
			geom.setStreamality(true, true);
			Dataset cylinderParameters = DatasetUtils.convertToDataset(shape.getSize());
			double[] radii = new double[2];
			radii[0] = cylinderParameters.getDouble(0, 0);
			radii[1] = cylinderParameters.getDouble(1, 0);
			geom.setDistances(radii[0], radii[1]);
		} else if ("nxbox".equalsIgnoreCase(shapeName)) {
			geom = new XPDFComponentPlate();
		}
		
		containerCompo.getForm().setGeom(geom);
		xpdfMeta.addContainer(containerCompo);
		
		// Container data
		XPDFBeamTrace containerTrace = traceFromTree(containerTree, true);
		xpdfMeta.setContainerTrace(containerCompo, containerTrace);		

		try {
			containerFile.close();
		} catch (NexusException nE) {
			// Can't close the file? Oh well, never mind
		}
	}

	/**
	 * Adds the empty beam data.
	 * <p>
	 * By recursing through all the containers, following the 
	 * 'inside_of_file_name' values, this method gets the data associated with
	 * the outermost NeXus file. This is added to the XPDF metadata as the
	 * empty beam data.
	 * @param xpdfMeta
	 * 				metadata object to add the data to
	 * @param tree
	 * 				the Nexus tree of the sample data
	 * @param parent
	 * 				sample data
	 */
	private void readAndAddBeamData(XPDFMetadataImpl xpdfMeta, Tree tree,
			ILazyDataset parent) {

		NexusFile beamFile, componentFile = null;
		Tree componentTree = tree, beamTree;
		while(true){
			GroupNode containerFileNameNode = getFirstSomething(componentTree, "NXcontainer");
			if (containerFileNameNode == null) {
				// No NXcontainer found. This must be the outermost container, the I15-1 beam
				beamTree = componentTree;
				beamFile = componentFile;
				break;
			}
			String componentFileName = containerFileNameNode.getDataNode("inside_of_file_name").getString();

			// Now open the relevant file, and get the tree
			try {
				componentFile = NexusFileHDF5.openNexusFileReadOnly(componentFileName);
				componentTree = NexusUtils.loadNexusTree(componentFile);
			} catch (Exception e1) {
				throw new OperationException(this, e1);
			}
			try{
				if (componentFile != null) componentFile.close();
			} catch (Exception e1) {
				throw new OperationException(this, e1);
			}
			if (componentTree == null) throw new OperationException(this, "Error constructing container file tree from " + componentFileName);

		}

		// Empty beam data
		XPDFBeamTrace containerTrace = traceFromTree(beamTree, true);
		
		xpdfMeta.setEmptyTrace(containerTrace);
		
		try {
			if (beamFile != null) beamFile.close();
		} catch (NexusException nE) {
			// Can't close the file? Oh well, never mind
		}
	}
	
	private void readAndAddBeamInfo(XPDFMetadataImpl xpdfMeta, Tree tree, ILazyDataset parent) {
		// Beam wavelength from the beam
		NXbeam beamNode = getFirstSomething(tree, "NXbeam");
		
		XPDFBeamData beam = new XPDFBeamData();
		beam.setBeamWavelength(beamNode.getIncident_wavelengthScalar());

		// beam size from the slits
		// Get the beam_defining aperture in the experimental hutch, as part of the instrument
		NXslit definer = (NXslit) tree.getGroupNode().getGroupNode("entry1").getGroupNode("instrument").getGroupNode("experimental_hutch").getGroupNode("beam_defining_aperture");
		beam.setBeamWidth((double) definer.getX_gapScalar());
		beam.setBeamHeight((double) definer.getY_gapScalar());
		
		xpdfMeta.setBeamData(beam);
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

	// Add the detector information to the XPDF metadata
	private void readAndAddDetector(XPDFMetadataImpl xpdfMeta, Tree tree, ILazyDataset parent) {
		// get the node describing the detector. Hard coded, following the I15-1 Nexus spec
		GroupNode rootNode = tree.getGroupNode(),
				entryNode = rootNode.getGroupNode("entry1"),
				instrumentNode = entryNode.getGroupNode("instrument"),
				hutchNode = instrumentNode.getGroupNode("experimental_hutch");
		GroupNode tectNode = hutchNode.getGroupNode("detector_1");
		NXdetector nxtect = (NXdetector) tectNode;
		// simple values
		XPDFDetector xpdftect = new XPDFDetector();
		// substance
		double density = nxtect.getDouble("sensor_density");
		xpdftect.setSubstance(new XPDFSubstance(nxtect.getSensor_materialScalar(), nxtect.getSensor_materialScalar(), density, 1.0));
		// thickness
		xpdftect.setThickness(nxtect.getSensor_thicknessScalar());
		// Solid angle. TODO: calculate the true solid angle
		double solidAngle;
			solidAngle = xpdftect.getSolidAngle();
			// failure is assigning zero, not throwing an Exception
			if (solidAngle == 0.0) solidAngle = 0.1;

		xpdftect.setSolidAngle(solidAngle);
		
		xpdfMeta.setDetector(xpdftect);
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
		if (nodeMap.size() == 0) return null;
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
