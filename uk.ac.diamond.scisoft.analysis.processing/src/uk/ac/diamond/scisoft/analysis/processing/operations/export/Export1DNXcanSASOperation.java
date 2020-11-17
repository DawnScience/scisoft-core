/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


// Package declaration


package uk.ac.diamond.scisoft.analysis.processing.operations.export;


// Imports from Java
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.nexus.NexusFile;
// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;

// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;


//The operation file for exporting a NXcanSAS file 


//@author Tim Snow


//Let's save this file.
public class Export1DNXcanSASOperation extends AbstractOperation<Export1DNXcanSASModel, OperationData> implements IExportOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.Export1DNXcanSASOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// Get the frame's metadata so that we know the filename etc.
		SliceFromSeriesMetadata sliceSeriesMetadata = getSliceSeriesMetadata(input);
		int frameNumber = sliceSeriesMetadata.getSliceInfo().getSliceNumber();
		
		// Get the file's name
		String fileName = sliceSeriesMetadata.getFilePath();
		String folderPath = fileName.substring(0, fileName.lastIndexOf(File.separator));
		fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf("."));
		String filePath = "";
		
		// Create a save path
		if (model.getOutputDirectoryPath() != "") {
			filePath = model.getOutputDirectoryPath() + File.separator + fileName + "_" + String.format("%0" + String.valueOf(model.getPaddingZeros()) + "d", frameNumber) + "_NXcanSAS.nxs";	
		}
		else {
			filePath = folderPath + File.separator + fileName + "_" + String.format("%0" + String.valueOf(model.getPaddingZeros()) + "d", frameNumber) + "_NXcanSAS.nxs";	
		}
		
		// Get the data so that it's a nice array for outputting
		IDataset dataForOutput = input;

		// Get the axis data so that it's a nice array for outputting
		IDataset axesForOutput = (IDataset) input.getFirstMetadata(AxesMetadata.class).getAxes()[0];
		axesForOutput = Maths.multiply(axesForOutput, 10);
		
		// Get the error data so that it's a nice array for outputting
		IDataset errorForOutput = input.getErrors();

		// The SASdata class SDS classes of I & Q (I_err too!) with attributes for each SDS class e.g. I_axes = Q, Q_indicies 0.
		GroupNode nxData = TreeFactory.createGroupNode(0);
		nxData.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, "I"));
		nxData.addAttribute(TreeFactory.createAttribute("I_axes", "Q"));
		nxData.addAttribute(TreeFactory.createAttribute("Q_indices", Long.valueOf(0)));
		
		// Then we add the data
		DataNode sasDataNode = TreeFactory.createDataNode(1);
		sasDataNode.setDataset(dataForOutput);
		sasDataNode.addAttribute(TreeFactory.createAttribute(NexusConstants.UNITS, "arbitrary"));
		//nxData.addDataNode("I", NexusTreeUtils.createDataNode("I", dataForOutput, "1/cm"));
		nxData.addDataNode("Q", NexusTreeUtils.createDataNode("Q", axesForOutput, "1/nm"));
		
		// And any error values, if present
		if (errorForOutput != null) {
			sasDataNode.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_UNCERTAINTIES, "Idev"));
			//nxData.addAttribute(TreeFactory.createAttribute("I/uncertainties", "Idev"));
			nxData.addDataNode("Idev", NexusTreeUtils.createDataNode("Idev", dataForOutput, "arbitrary"));
		}
		
		nxData.addDataNode("I", sasDataNode);

		try {
			// Create the file and get ready to write
			NexusFile nexusFileReference = new NexusFileHDF5(filePath);
			nexusFileReference.createAndOpenToWrite();

			// Create the NXcanSAS file header
			NXcanSASHeader(nexusFileReference, fileName, input.getName());
			
			// Write in the experimental data
			nexusFileReference.addNode("/sasentry/sasdata", nxData);
		
			// Then, finally, close the file
			nexusFileReference.close();
		} catch (NexusException nexusFileError) {
			System.out.println("This error occured when attempting to open the NeXus file: " + nexusFileError.getMessage());
		}
		
		// For now, let's just create a return class, fill it
		OperationData output = new OperationData();
		output.setData(input);
		
		// And return the input data
		return output;
	}
	
	
	private void NXcanSASHeader (NexusFile nexusFileReference, String fileName, String fileTitle) throws NexusException {
		// First up, let's get the time
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		
		// So the NxcanSAS file format requires us to output Q & I minimum with Idev as well for preference.
		// We need a SASroot, SASentry and SASdata class structure with lowercase names (entry and data)

		// As a courtesy the root level has the file_name and file_time (acquisition time) given as attributes (Time format: YYYY-MM-DDTHH:MM:SS+0000 (timezone))
		GroupNode nxRoot = TreeFactory.createGroupNode(0);
		nxRoot.addAttribute(TreeFactory.createAttribute(NexusConstants.NXCLASS, NexusConstants.ROOT));
		nxRoot.addAttribute(TreeFactory.createAttribute("canSAS_class", "SASroot"));
		nxRoot.addAttribute(TreeFactory.createAttribute("default", "sasentry"));
		nxRoot.addAttribute(TreeFactory.createAttribute("file_name", fileName));
		nxRoot.addAttribute(TreeFactory.createAttribute("file_time", dateFormatter.format(currentDate)));
		nxRoot.addAttribute(TreeFactory.createAttribute("producer", "DAWN"));

		// The SASentry class contains a SASdata class with a version attribute (NXcanSAS version e.g. 1.0)
		GroupNode nxEntry = TreeFactory.createGroupNode(0);
		nxEntry.addAttribute(TreeFactory.createAttribute(NexusConstants.NXCLASS, NexusConstants.ENTRY));
		nxEntry.addAttribute(TreeFactory.createAttribute("canSAS_class", "SASentry"));
		nxEntry.addAttribute(TreeFactory.createAttribute("default", "sasdata"));
		nxEntry.addAttribute(TreeFactory.createAttribute("version", "1.0"));
		// These next three require fields with values not attributes
		DataNode definitionDataNote = TreeFactory.createDataNode(1);
		definitionDataNote.setDataset(DatasetFactory.createFromObject("NXcanSAS"));
		nxEntry.addDataNode("definition", definitionDataNote);
		DataNode runDataNote = TreeFactory.createDataNode(1);
		runDataNote.setDataset(DatasetFactory.createFromObject(fileName));
		nxEntry.addDataNode("run", runDataNote);
		DataNode titleDataNote = TreeFactory.createDataNode(1);
		titleDataNote.setDataset(DatasetFactory.createFromObject(fileName));
		nxEntry.addDataNode("title", titleDataNote);
		
		// The SASdata class SDS classes of I & Q (I_err too!) with attributes for each SDS class e.g. I_axes = Q, Q_indicies 0.
		GroupNode nxData = TreeFactory.createGroupNode(0);
		nxData.addAttribute(TreeFactory.createAttribute(NexusConstants.NXCLASS, NexusConstants.DATA));
		nxData.addAttribute(TreeFactory.createAttribute("canSAS_class", "SASdata"));
		
		// Then write this header
		nexusFileReference.addNode("/", nxRoot);
		nexusFileReference.addNode("/sasentry", nxEntry);
		nexusFileReference.addNode("/sasentry/sasdata", nxData);
	}
} 