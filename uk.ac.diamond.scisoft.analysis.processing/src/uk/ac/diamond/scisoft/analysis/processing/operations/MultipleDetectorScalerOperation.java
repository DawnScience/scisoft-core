/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

//Imports from org.eclipse.january
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.IMonitor;

// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.MultipleDetectorScalerModel;


//This processing plugin scales the intensity recorded on an uncalibrated detector when another, calibrated, detector 
//at a different sample-to-detector distance can be used as a reference point. This is achieved via the inverse square law

public class MultipleDetectorScalerOperation extends AbstractOperation<MultipleDetectorScalerModel, OperationData> {

	// Let's start with the required methods for the class, the ID;
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.MultipleDetectorScalerOperation";
	}

	// input,
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	// and output ranks.
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	// Now for the process method where the work happens...
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		// Let's get a hold of the input data as a dataset and any associated metadata
		Dataset inputData = DatasetUtils.convertToDataset(input);
		SliceFromSeriesMetadata metadata = getSliceSeriesMetadata(input);
		
		// Next get th// This processing plugin scales the intensity recorded on an uncalibrated detector when another, calibrated, detector 
		// at a different sample-to-detector distance can be used as a reference point. This is achieved via the inverse square lawe two detector distances from the file
		String filePath = metadata.getFilePath();
		IDataset detectorOneDistance = ProcessingUtils.getDataset(this, filePath, model.getDetectorOneDistanceDataset());
		IDataset detectorTwoDistance = ProcessingUtils.getDataset(this, filePath, model.getDetectorTwoDistanceDataset());

		// Cast these as doubles and do the inverse square law mathematics
		double distanceOne = detectorOneDistance.getDouble(0);
		double distanceTwo = detectorTwoDistance.getDouble(0);
		double correctionFactor = (distanceTwo * distanceTwo) / (distanceOne * distanceOne);
		Dataset correctionFactorDataset = DatasetFactory.createFromObject(correctionFactor);
		
		// Correct the input dataset
		inputData = ErrorPropagationUtils.multiplyWithUncertainty(inputData, correctionFactorDataset);
		
		// and export the result
		copyMetadata(input, inputData);
		OperationData toReturn = new OperationData(inputData);

		return toReturn;
	}
}
