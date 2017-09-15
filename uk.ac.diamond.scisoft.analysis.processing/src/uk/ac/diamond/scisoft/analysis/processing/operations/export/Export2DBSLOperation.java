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
import java.util.Date;
import java.util.Calendar;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;


//The operation file for exporting a BSL folder 


//@author Tim Snow


//Let's save this file.
public class Export2DBSLOperation extends AbstractOperation<Export2DBSLModel, OperationData> implements IExportOperation {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.export.Export2DBSLOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// Get the frame's metadata so that we know the filename etc.
		SliceFromSeriesMetadata sliceSeriesMetadata = getSliceSeriesMetadata(input);
		String sampleName = input.getName();
		int frameNumber = sliceSeriesMetadata.getSliceInfo().getSliceFromInput()[1].getStart();

		// Get the file's name
		String filePath = sliceSeriesMetadata.getFilePath();
		//String fileName = sliceSeriesMetadata.getFilePath();
		String folderPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.lastIndexOf("."));
		String bslFolderPath = "";
		
		// Create a save path
		if (model.getOutputDirectoryPath() != "") {
			bslFolderPath = model.getOutputDirectoryPath() + File.separator + fileName;
		}
		else {
			bslFolderPath = folderPath + File.separator + fileName;	
		}
		
		// Get the data so that it's a nice array for outputting
		IDataset scalersData = (ProcessingUtils.getDataset(this, filePath, model.getScalersFilePath())).squeeze();
		IDataset countTimes = (ProcessingUtils.getDataset(this, filePath, model.getCountTimeFilePath())).squeeze();
		IDataset waitTimes = (ProcessingUtils.getDataset(this, filePath, model.getWaitTimeFilePath())).squeeze();

		// Then generate the different file names that will be generated for the BSL directory
		String[] bslFileNames = BSLFileNames(fileName);
		
		boolean multiFrame = (1 >= 0) && (1 < scalersData.getShape().length);
		
		if (multiFrame == true) {
			// Output the BSL header file
			BSLHeaderWriter(bslFolderPath, bslFileNames, sampleName, input.getShape(), sliceSeriesMetadata.getTotalSlices(), scalersData.getShape()[1]);
			// Output the detector images
			BSLDetectorImageWriter(bslFolderPath, bslFileNames, input, input.getShape());
			// Output the scalers, focusing on the scaler values for this frame
			BSLScalersWriter(bslFolderPath, bslFileNames, scalersData.getSlice(new Slice(frameNumber, frameNumber + 1)));
			// Output the secondary detector information (blank for now)
			BSLNullWriter(bslFolderPath, bslFileNames);
			// Output the scan times, focusing on the time values for this frame
			BSLTimesWriter(bslFolderPath, bslFileNames, countTimes.getFloat(frameNumber), waitTimes.getFloat(frameNumber));
		}
		else {
			// Output the BSL header file
			BSLHeaderWriter(bslFolderPath, bslFileNames, sampleName, input.getShape(), sliceSeriesMetadata.getTotalSlices(), scalersData.getShape()[0]);
			// Output the detector images
			BSLDetectorImageWriter(bslFolderPath, bslFileNames, input, input.getShape());
			// Output the scalers, focusing on the scaler values for this frame
			BSLScalersWriter(bslFolderPath, bslFileNames, scalersData);
			// Output the secondary detector information (blank for now)
			BSLNullWriter(bslFolderPath, bslFileNames);
			// Output the scan times, focusing on the time values for this frame
			BSLTimesWriter(bslFolderPath, bslFileNames, countTimes.getFloat(), waitTimes.getFloat());
		}
		
		// Return the data as it arrived
		OperationData output = new OperationData();
		output.setData(input);
				
		// And return the input data
		return output;
	}
	
		
	protected String[] BSLFileNames (String fileName) {
		// First we need the current date
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		
		// Now we can format the month as required for the BSL standard
		// Months are zero based
		String month = Integer.toHexString(cal.get(Calendar.MONTH) + 1);
		// then get the day
		String day = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
		// and the last 3 digits of the run number
		String runNumber = fileName.substring(fileName.length() - 2);

		// Now we can crete a placeholder for the file titles
		String[] bslFileNames = new String[5];
		
		// And 'string' them all together
		// 000 is the header
		bslFileNames[0] = "A" + runNumber + "000." + month + day;
		// 001 is the primary scattering data
		bslFileNames[1] = "A" + runNumber + "001." + month + day;
		// 002 is the scalers data
		bslFileNames[2] = "A" + runNumber + "002." + month + day;
		// 003 is the secondary scattering data
		bslFileNames[3] = "A" + runNumber + "003." + month + day;
		// 004 is the scan times
		bslFileNames[4] = "A" + runNumber + "004." + month + day;

		// Return the file names
		return bslFileNames;
	}
	
	protected void BSLHeaderWriter (String folderPath, String[] fileNames, String sampleName, int [] detectorDimensions, int numberOfFrames, int scalersLength) {
		// First, where's the data going?
		new File(folderPath).mkdirs();
		// Create the path for the header file
		String filePath = folderPath + File.separator + fileNames[0];
		if (new File(filePath).exists() == false) {
			// Get a formatted timestamp
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDate = new Date();
			// Format all the variables required as fixed length strings
			String xLength = String.format("%1$"+8+ "s", detectorDimensions[1]);
			String yLength = String.format("%1$"+8+ "s", detectorDimensions[0]);
			String numberOfFramesString = String.format("%1$"+8+ "s", numberOfFrames);
			String scalersLengthString = String.format("%1$"+8+ "s", scalersLength);
			String zero = String.format("%1$"+8+ "s", 0);
			String one = String.format("%1$"+8+ "s", 1);
			String two = String.format("%1$"+8+ "s", 2);
			String midsection = one + zero + zero + zero + zero + zero;
			// There's a few variables in here just for the BSL format, such as zero and one...
			// Create the strings for outputting
			String fileHeader = "Created by DAWN on " + dateFormatter.format(currentDate) + "\n" + sampleName + "\n";
			String detectorLine = xLength + yLength + numberOfFramesString + midsection + one + "\n" + fileNames[1] + "\n";
			String scalersLine = scalersLengthString + numberOfFramesString + one + midsection + one + "\n" + fileNames[2] + "\n";
			String secondaryDetectorLine = zero + zero + zero + zero + zero + zero + zero + zero + zero + one + "\n" + fileNames[3] + "\n";
			String timesLine = two + numberOfFramesString + one + midsection + zero + "\n" + fileNames[4] + "\n";
			
			// Create a fileWriter
			BufferedWriter fileWriter = null;
			
			try {
				// Set up the reference to the file and write the header information
				fileWriter = new BufferedWriter(new FileWriter(filePath));
				fileWriter.write(fileHeader + detectorLine + scalersLine + secondaryDetectorLine + timesLine);
			} catch (IOException fileError) {
				// Print out if we have any errors
				System.out.println("There was a problem creating the BSL header file: " + fileError.toString());
			} finally {
				try {
					// Close the file regardless
					fileWriter.close();
				} catch (IOException fileError) {
					// Unless there's an error!
					System.out.println("There was a problem closing the BSL header file: " + fileError.toString());
				}
			}
		}
	}
	
	
	protected void BSLDetectorImageWriter (String folderPath, String[] fileNames, IDataset dataForOutput, int[] dataShape) {
		// First, where's the data going?
		String filePath = folderPath + File.separator + fileNames[1];
		// Get the size of the image
		int[] dataSize = dataForOutput.getShape();
		// Calculate how many bytes of data that is and create a byte array, for output
		byte[] bytesToWrite = new byte[(dataSize[0] * dataSize[1] * 4)];
		
		// Looping through each pixel
		for (int yLoopIter = 0; yLoopIter < dataSize[0]; yLoopIter ++) {
			for (int xLoopIter = 0; xLoopIter < dataSize[1]; xLoopIter ++) {
				int loopIter = ((yLoopIter * dataSize[1]) + xLoopIter) * 4;
				// Extract out the value as bytes
				byte[] tempBytes = ByteBuffer.allocate(4).putFloat(dataForOutput.getFloat(yLoopIter, xLoopIter)).array();
				// And place these in the byte array
				bytesToWrite[loopIter] = tempBytes[0];
				bytesToWrite[loopIter + 1] = tempBytes[1];
				bytesToWrite[loopIter + 2] = tempBytes[2];
				bytesToWrite[loopIter + 3] = tempBytes[3];
			}
		}

		// Create a fileWriter
		DataOutputStream fileWriter = null;
		
		try {
			// Set up the reference to the file and save the detector information as 32-bit real
			fileWriter = new DataOutputStream(new FileOutputStream(filePath, true));
			fileWriter.write(bytesToWrite);							
		} catch (IOException fileError) {
			// Print out if we have any errors
			System.out.println("There was a problem creating the BSL detector image file: " + fileError.toString());
		} finally {
			try {
				// Close the file regardless
				fileWriter.close();
			} catch (IOException fileError) {
				// Unless there's an error!
				System.out.println("There was a problem closing the BSL detector image file: " + fileError.toString());
			}
		}
	}
	

	protected void BSLScalersWriter (String folderPath, String[] fileNames, IDataset scalerValues) {
		// First, where's the data going?
		String filePath = folderPath + File.separator + fileNames[2];

		// Find out how many scalers there are
		int rank = scalerValues.getRank();
		
		while (rank > 1) {
			scalerValues.squeeze();
			rank = scalerValues.getRank();
		}
		int scalerLength = scalerValues.getSize();

		// Calculate how many bytes of data that is and create a byte array, for output
		byte[] bytesToWrite = new byte[(scalerLength * 4)];
		
		// Looping through each value
		for (int scalerLoopIter = 0; scalerLoopIter < scalerLength; scalerLoopIter ++) {
			// Extract out the value as bytes
			byte[] tempBytes = ByteBuffer.allocate(4).putFloat(scalerValues.getFloat(scalerLoopIter)).array();
			// And place these in the byte array
			bytesToWrite[0] = tempBytes[0];
			bytesToWrite[1] = tempBytes[1];
			bytesToWrite[2] = tempBytes[2];
			bytesToWrite[3] = tempBytes[3];
		}

		// Create a fileWriter
		DataOutputStream fileWriter = null;
		
		try {
			// Set up the reference to the file and save the scalers information as 32-bit real
			fileWriter = new DataOutputStream(new FileOutputStream(filePath, true));
			fileWriter.write(bytesToWrite);							
		} catch (IOException fileError) {
			// Print out if we have any errors
			System.out.println("There was a problem creating the BSL detector image file: " + fileError.toString());
		} finally {
			try {
				// Close the file regardless
				fileWriter.close();
			} catch (IOException fileError) {
				// Unless there's an error!
				System.out.println("There was a problem closing the BSL detector image file: " + fileError.toString());
			}
		}
	}

	
	protected void BSLNullWriter (String folderPath, String[] fileNames) {
		// First, where's the data going?
		String filePath = folderPath + File.separator + fileNames[3];

		// So, we need a file (so we can save the times) but that doesn't mean it needs any information...
		BufferedWriter fileWriter = null;

		try {
			// Set up the reference to the file and save nothing!
			fileWriter = new BufferedWriter(new FileWriter(filePath));
			fileWriter.write("");
		} catch (IOException fileError) {
			// Print out if we have any errors
			System.out.println("There was a problem creating the BSL detector image file: " + fileError.toString());
		} finally {
			try {
				// Close the file regardless
				fileWriter.close();
			} catch (IOException fileError) {
				// Unless there's an error!
				System.out.println("There was a problem closing the BSL detector image file: " + fileError.toString());
			}
		}
	}
	

	protected void BSLTimesWriter (String folderPath, String[] fileNames, float countTime, float waitTime) {
		// First, where's the data going?
		String filePath = folderPath + File.separator + fileNames[4];

		// Find out how many timepoints there are
		// Calculate how many bytes of data that is and create a byte array, for output
		byte[] bytesToWrite = new byte[8];
		
		// Extract out the count time as bytes
		byte[] tempBytes = ByteBuffer.allocate(4).putFloat(countTime).array();
		bytesToWrite[0] = tempBytes[0];
		bytesToWrite[1] = tempBytes[1];
		bytesToWrite[2] = tempBytes[2];
		bytesToWrite[3] = tempBytes[3];
		
		// Extract out the wait time as bytes
		tempBytes = ByteBuffer.allocate(4).putFloat(waitTime).array();
		bytesToWrite[4] = tempBytes[0];
		bytesToWrite[5] = tempBytes[1];
		bytesToWrite[6] = tempBytes[2];
		bytesToWrite[7] = tempBytes[3];
		
		// Create a fileWriter
		DataOutputStream fileWriter = null;
		
		try {
			// Set up the reference to the file and save the scalers information as 32-bit real
			fileWriter = new DataOutputStream(new FileOutputStream(filePath, true));
			fileWriter.write(bytesToWrite);							
		} catch (IOException fileError) {
			// Print out if we have any errors
			System.out.println("There was a problem creating the BSL detector image file: " + fileError.toString());
		} finally {
			try {
				// Close the file regardless
				fileWriter.close();
			} catch (IOException fileError) {
				// Unless there's an error!
				System.out.println("There was a problem closing the BSL detector image file: " + fileError.toString());
			}
		}
	}
}