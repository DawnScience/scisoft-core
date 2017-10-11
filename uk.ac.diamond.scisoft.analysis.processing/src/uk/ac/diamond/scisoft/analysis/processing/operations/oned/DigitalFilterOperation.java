/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.oned;


import java.util.List;
import java.util.ArrayList;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.oned.DigitalFilterModel.FeatureFilter;


public class DigitalFilterOperation extends AbstractOperation<DigitalFilterModel, OperationData> {

	// First we set the ID of the plug-in
	@Override
	public String getId() {

		return "uk.ac.diamond.scisoft.analysis.processing.operations.DigitalFilterOperation";
	}
	
	
	// Then set up a logger
	private static final Logger logger = LoggerFactory.getLogger(DigitalFilterOperation.class);
	
	
	// Then the required dimensionality of the input data
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}
	
	
	// Then the output data dimensionality
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	
	// Then we declare the process itself
	protected OperationData process(IDataset input, IMonitor monitor) {
		Dataset output = (Dataset) input;
		
		// If widths have been given, do the filtering
		if (model.getFirstFilterWidth() != 0.00 && model.getSecondFilterWidth() != 0.00) {
			output = digitalFilterBackground(DatasetUtils.convertToDataset(input), xAxisExtractor(input), model.getFirstFilterWidth(), model.getSecondFilterWidth());
		}
		
		// Put the metadata back!
		copyMetadata(input, output);
		
		// Return the result
		return new OperationData(output);
	}
	
	
	// Where all the work happens, made public out to make this available to other classes
	public Dataset digitalFilterBackground(Dataset yDataset, Dataset xDataset, double firstWidth, double secondWidth) {
		// Set up some bits for the method
		LinearInterpolator interpolator = new LinearInterpolator();
		double[] yData = (double[]) DatasetUtils.createJavaArray(yDataset);
		double[] xData = (double[]) DatasetUtils.createJavaArray(xDataset);
		
		double startValue = xDataset.min(true).doubleValue();
		double endValue = startValue + firstWidth;
		int firstWidthInt = DatasetUtils.findIndexGreaterThanOrEqualTo(xDataset, endValue);
		endValue = startValue + secondWidth;
		int secondWidthInt = DatasetUtils.findIndexGreaterThanOrEqualTo(xDataset, endValue);
		
		// Run the first round of filtering
		double[] firstFilterData = forwardDigitalFilter(firstWidthInt, firstWidthInt, yData);
		double[][] firstFilter = arrayChecker(yData, firstFilterData, xData);
		
		// Interpolate to re-create the original array dimensions
		PolynomialSplineFunction firstInterpolationResult = interpolator.interpolate(firstFilter[1], firstFilter[0]);
		double[] interpolatedFirstFilterY = dataRecreator(firstInterpolationResult, xData);
		
		// Run the second round of filtering
		double[] secondFilterData = backwardDigitalFilter(secondWidthInt, secondWidthInt, yData);
		double[][] secondFilter = arrayChecker(yData, secondFilterData, xData);

		// Interpolate to re-create the original array dimensions
		PolynomialSplineFunction secondInterpolationResult = interpolator.interpolate(secondFilter[1], secondFilter[0]);
		double[] interpolatedSecondFilterY = dataRecreator(secondInterpolationResult, xData);

		// Depending on whether we're interested in the narrow or broad features...
		if (model.getFilterType() == FeatureFilter.NARROW) {
			// Subtract off the standard background
			double[] forwardFeatures = narrowFeatures(yData, interpolatedFirstFilterY);
			double[] backwardFeatures = narrowFeatures(yData, interpolatedSecondFilterY);

			// Now combine the positive results of the narrow features to give the overall features forwards and backwards along the array
			double[] returnData = combinedNarrowFeatures(forwardFeatures, backwardFeatures);
			
			// Find the minimum dataset value and add this to the dataset, this is for logarithmic plotting primarily
			returnData = addMinimumValue(returnData);
			
			// Return the result
			return DatasetFactory.createFromObject(returnData);
		} else if (model.getFilterType() == FeatureFilter.BROAD) {
			// Now combine the positive results of the broad features to give the overall features forwards and backwards along the array
			double[] returnData = combinedBroadFeatures(interpolatedFirstFilterY, interpolatedSecondFilterY);
			
			// Find the minimum dataset value and add this to the dataset, this is for logarithmic plotting primarily
			returnData = addMinimumValue(returnData);
			
			// Return the result
			return DatasetFactory.createFromObject(returnData);
		} else {
			// If there's an error with the picker, we'll handle this too
			return DatasetFactory.createFromObject(-1);
		}
	}
	
	
	public static Dataset xAxisExtractor(IDataset inputDataset) {
		// Next, we'll extract out the x axis (q) dataset from the input
		Dataset xAxis;
		
		// Just in case we don't have an x-axis (as we really need an x axis)
		try {
			// Try to get one from the metadata
			xAxis = DatasetUtils.convertToDataset(inputDataset.getFirstMetadata(AxesMetadata.class).getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			// If there's no axis data, I guess we'll have to make some then!
			int dataLength = inputDataset.squeeze().getSize();
			xAxis = DatasetFactory.createRange(Dataset.class, dataLength);
		}
		// Now return it
		return xAxis;
	}
	
	
	private double[] backwardDigitalFilter(int width, int gap, double[] yData) {
		// Find out some information about our dataset
		int dataSize = yData.length;
		double[] correctedDataset = new double[dataSize];
		
		// Some seed values for the for loop
		int startValue = -(dataSize );
		int endValue = -(((4 * width) + (2 * gap)) + 1);
		int loopIncrement = 1;
		
		// Loop through the data starting at the end
		for(int loopIter = startValue; loopIter < endValue; loopIter += loopIncrement) {
			// Find the limits of the pre-peak area
			int preStop = Math.abs(loopIter);
			int preStart = preStop - width;

			// Work out the peak limits
			int peakStop = preStart - gap;
			int peakStart = peakStop - ((width * 2) + 1);

			// Find the limits of the post-peak area
			int postStop = peakStart - gap;
			int postStart = postStop - width;

			// Work out the intensity location we're finding
			int correctionLocation = peakStop - (width + 1);

			// Integrate the pre-, post- and peak values
			double prePeakIntegral = arraySummator(yData, preStart, preStop);
			double peakIntegral = arraySummator(yData, peakStart, peakStop);
			double postPeakIntegral = arraySummator(yData, postStart, postStop);

			// Use these to work out the corrected intensity
			double intensity = peakIntegral - (prePeakIntegral + postPeakIntegral);

			// Set the datapoint with it's new value
			correctedDataset[correctionLocation] = intensity;
		}
		// After traversing the dataset, return it!
		return correctedDataset;
	}
	
	
	private double[] forwardDigitalFilter(int width, int gap, double[] yData) {
		// Find out some information about our dataset
		int dataSize = yData.length;
		double[] correctedDataset = new double[dataSize];
		
		// Some seed values for the for loop
		int startValue = 0;
		int endValue = (dataSize - ((4 * width) + (2 * gap)) - 1);
		int loopIncrement = 1;
		
		// Loop through the data starting at the end
		for(int loopIter = startValue; loopIter < endValue; loopIter += loopIncrement) {
			// Find the limits of the pre-peak area
			int preStart = loopIter;
			int preStop = preStart + width;

			// Work out the peak limits
			int peakStart = preStop + gap;
			int peakStop = peakStart + ((width * 2) + 1);

			// Find the limits of the post-peak area
			int postStart = peakStop + gap;
			int postStop = postStart + width;

			// Work out the intensity location we're finding
			int correctionLocation = peakStart + (width + 1);

			// Integrate the pre-, post- and peak values
			double prePeakIntegral = arraySummator(yData, preStart, preStop);
			double peakIntegral = arraySummator(yData, peakStart, peakStop);
			double postPeakIntegral = arraySummator(yData, postStart, postStop);

			// Use these to work out the corrected intensity
			double intensity = peakIntegral - (prePeakIntegral + postPeakIntegral);

			// Set the datapoint with it's new value
			correctedDataset[correctionLocation] = intensity;
		}
		// After traversing the dataset, return it!
		return correctedDataset;
	}
	
	
	private double arraySummator(double[] array, int startIndex, int endIndex) {
		// There doesn't seem to be a ready way to do this so...
		// Set up sum
		double sum = 0.00;
		
		// Loop over relevant elements
		for (int arrayIndex = startIndex; arrayIndex < endIndex; arrayIndex ++) {
			sum += array[arrayIndex];
		}
		// Return sum
		return sum;
	}
	
	
	private double[][] arrayChecker(double [] primaryArray, double [] comparitorArray, double [] secondaryArray) {
		// First get the length of the input data
		int dataSize = primaryArray.length;
		
		// Then create some lists as they are easy to add to
		List<Double> primaryList = new ArrayList<>();
		List<Double> secondaryList = new ArrayList<>();
		
		// Find all the elements that meet our requirements and add them to the list
		for (int loopIter = 0; loopIter < dataSize; loopIter ++) {
			if (primaryArray[loopIter] > comparitorArray[loopIter]) {
				primaryList.add(primaryArray[loopIter]);
				secondaryList.add(secondaryArray[loopIter]);
			}
		}
		
		// Find out the list length and prepare the returnArray
		int listLength = primaryList.size();
		double[][] returnArray = new double[2][listLength];
		
		// Because this isn't easy in Java, loop through the list to convert to a normal array
		for (int loopIter = 0; loopIter < listLength; loopIter ++) {
			returnArray[0][loopIter] = primaryList.get(loopIter); 
			returnArray[1][loopIter] = secondaryList.get(loopIter);
		}
		// Return the array
		return returnArray;
	}
	
	
	private double[] dataRecreator(PolynomialSplineFunction interpolationResult, double[] xData) {
		// Get the datalength and setup the return array
		int dataSize = xData.length;
		double[] returnData = new double[dataSize];
		
		// Use the interpolator to find all the corresponding y values
		for (int loopIter = 0; loopIter < dataSize; loopIter ++) {
			try {
				// If the number required is within the interpolation limits, great!
				returnData[loopIter] = interpolationResult.value(xData[loopIter]);
			} catch (Exception e) {
				// If not, we'll give it a value of zero
				logger.info("Creating datapoint outside of interpolation zone");
				returnData[loopIter] = 0;
			}
		}
		// Return it
		return returnData;
	}
	
	
	private double[] narrowFeatures(double[] originalData, double[] filteredData) {
		// Get the datalength and setup the return array
		int dataSize = originalData.length;
		double[] returnData = new double[dataSize];
		
		// Looping over the dataset
		for (int loopIter = 0; loopIter < dataSize; loopIter ++) {
			// If we're looking for the narrow features, subtract the filtered result from the original data 
			returnData[loopIter] = originalData[loopIter] - filteredData[loopIter];
		}
		/// Then return it!
		return returnData;
	}
	
	
	private double[] combinedNarrowFeatures(double[] firstDataset, double[] secondDataset) {
		// Get the datalength and setup the return array
		int dataSize = firstDataset.length;
		double[] returnData = new double[dataSize];
		
		// Looping
		for (int loopIter = 0; loopIter < dataSize; loopIter ++) {
			// Returning the maximum value to best represent the peaks found after the two sweeps over the dataset
			returnData[loopIter] = Math.max(firstDataset[loopIter], secondDataset[loopIter]);
		}
		// Then returning it
		return returnData;
	}
	
	
	private double[] combinedBroadFeatures(double[] firstDataset, double[] secondDataset) {
		// Get the datalength and setup the return array
		int dataSize = firstDataset.length;
		double[] returnData = new double[dataSize];
		
		// Looping
		for (int loopIter = 0; loopIter < dataSize; loopIter ++) {
			// Returning the maximum value to best represent the peaks found after the two sweeps over the dataset
			returnData[loopIter] = Math.min(firstDataset[loopIter], secondDataset[loopIter]);
		}
		// Then returning it
		return returnData;
	}
	
	
	private double[] addMinimumValue(double[] dataset) {
		// Get the datalength and setup the return array
		int dataSize = dataset.length;
		double minimumValue = 0.00;
		
		// Looping over the dataset
		for (int loopIter = 0; loopIter < dataSize; loopIter ++) {
			// Find the minimum, non-zero, number in the dataset
			if ((dataset[loopIter] != 0.00) && (Math.abs(dataset[loopIter]) < minimumValue)) {
				// Store it
				minimumValue = Math.abs(dataset[loopIter]);
			}
		}
		
		// Then add it to the dataset. Primarily for logarithmic plotting 
		for (int loopIter = 0; loopIter < dataSize; loopIter ++) {
			dataset[loopIter] += minimumValue;
		}
		// Finally, return it
		return dataset;
	}
}