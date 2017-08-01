/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.export;


// Imports from java
import java.io.File;

// Imports from org.eclipse
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.PNGScaledSaver;
import uk.ac.diamond.scisoft.analysis.io.TIFFImageSaver;
import uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportImageModel.FileFormatEnum;


// The model for an Image exporting plug-in for the processing perspective
// Currently it will export either a 32-bit TIFF or a 16-bit PNG file. PNG saving may well result in data loss...
// It is intended for PUBLICATION saving only but this may be foolish to assume.

// @author Tim Snow

public class ExportImageOperation extends AbstractOperation<ExportImageModel, OperationData> implements IExportOperation {


	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.ExportImageOperation";
	}
	
	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// First, let's, check there's an output directory!
		String outputDirectory = model.getOutputDirectoryPath();
		
		if (outputDirectory == null || outputDirectory.isEmpty()) {
			throw new OperationException(this, new IllegalArgumentException("Output directory not set!"));
		}
		else {
			// Create a reference to the path
			File outputDirectoryReference = new File(outputDirectory);
			// Create the folder if it's not already there
			if (!outputDirectoryReference.exists()) outputDirectoryReference.mkdir();
		}
		
		// Now we need the filename, sans extension, which requires us to know the input's metadata
		SliceFromSeriesMetadata inputMetadata = getSliceSeriesMetadata(input);
		// Now we can put this through our custom method to find the filename
		String fileName = FileNameFromMetadata(inputMetadata);

		// Now let's find out the frame number we're working on and add the desired amount of padding
		int frameNumber = inputMetadata.getSliceInfo().getSliceNumber();
		String paddedFrameString = "";
		paddedFrameString = String.format("%0" + String.valueOf(model.getPaddingZeros()) + "d", frameNumber);

		// Make sure we create a place for the output path to be saved to
		String outputPath = "";
		
		// Let's create an object that will, eventually, save the data
		IFileSaver fileSaver;
		
		// With this in hand we can construct the full output path for the file and pass this, along with the filetype to the saving method
		if (model.getFileFormatEnum() == FileFormatEnum.TIFF) {
			outputPath = outputDirectory + File.separator + fileName + "_" + paddedFrameString + ".tiff";
			
			int nBits = 32;
			Dataset d = DatasetUtils.convertToDataset(input);
			if (d.getDType() == Dataset.FLOAT32 || d.getDType() == Dataset.FLOAT64) {
				nBits =33;
			}
			
			fileSaver = new TIFFImageSaver(outputPath, nBits, false);

		}
		else if (model.getFileFormatEnum() == FileFormatEnum.PNG) {
			outputPath = outputDirectory + File.separator + fileName + "_" + paddedFrameString + ".png";
			fileSaver = new PNGScaledSaver(outputPath);
		}
		else {
			throw new OperationException(this, new IllegalArgumentException("Error! No filetype picked!"));
		}

		// Now we'll create a place to hold the data to save
		DataHolder dataHolder = new DataHolder();

		// And stick the data in
		dataHolder.addDataset("", input);

		// Then we'll try to save the data, failing some form of error.
		try {
			fileSaver.saveFile(dataHolder);
		} catch (ScanFileHolderException error) {
			throw new OperationException(this, new IllegalArgumentException("Error saving files"));
		}
		
		// Now we're all done, let's create the return object container for DAWN
		OperationData toReturn = new OperationData();
		// Fill it
		toReturn.setData(input);
		// And the return it!
		return toReturn;
	}

	private static String FileNameFromMetadata(SliceFromSeriesMetadata inputMetadata) {
		// First up, find the filepath
		String pathToFile = inputMetadata.getSourceInfo().getFilePath();
		// Create an object for the file
		File inputFile = new File(pathToFile);
		// Get it's full name, sans directory path
		String completeFileName = inputFile.getName();
		// Find out what index in the string array the extension fullstop is
		int fileExtensionIndex = completeFileName.lastIndexOf(".");
		// Just return the part of the file string BEFORE the extension
		String fileName = fileExtensionIndex == -1 ? completeFileName : completeFileName.substring(0, fileExtensionIndex);

		// Return this to the caller
		return fileName;
	}
}