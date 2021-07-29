/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.fitting;


import java.util.ArrayList;
import java.util.List;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.PlotAdditionalData;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;


// @author Tim Snow


//A straightforward y = mx + c line fitter
@PlotAdditionalData(onInput = false, dataName = "Linear Fit")
public class LinearFittingOperation extends AbstractOperation<LinearFittingModel, OperationData>{


	// First let's declare our process ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.fitting.LinearFittingOperation";
	}

	// Then we'll create some placeholders for data to be stored in
	private Dataset xSlice;
	private Dataset ySlice;

	// Now, how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}


	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// Next, we'll extract out the x axis (q) dataset from the input
		Dataset xAxis;

		// Just in case we don't have an x-axis (as we could do with an x axis)
		try {
			xAxis = DatasetUtils.convertToDataset(inputDataset.getFirstMetadata(AxesMetadata.class).getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		// Extract out the y axis (intensity) from the input
		Dataset yAxis = DatasetUtils.convertToDataset(inputDataset);

		if (model.getTranspose()) {
			Dataset t = xAxis;
			xAxis = yAxis;
			yAxis = t.getSliceView();
			yAxis.clearMetadata(AxesMetadata.class);
			MetadataUtils.setAxes(this, yAxis, xAxis);
		}

		// Get out the start and end values of the fitting range
		double[] fittingROI = model.getFittingRange();
		
		// Perform the fitting
		StraightLine linearFit = fitLinearData(xAxis, yAxis, fittingROI);
		
		// Extract out the fitting parameters
		double xGradient = linearFit.getParameterValue(0);
		double constant = linearFit.getParameterValue(1);
		
		// Assuming there were nice numbers, regenerate from the x-axis
		Dataset fittedYaxis;
		if (Double.isFinite(xGradient) && Double.isFinite(constant)) {
			fittedYaxis = linearFit.calculateValues(xSlice);
		} else {
			fittedYaxis = DatasetFactory.zeros(ySlice);
		}

		// Filling the fit dataset with the processed x axis
		fittedYaxis.setName("Linear Fit");
		MetadataUtils.setAxes(fittedYaxis, xSlice);
		
		// Creating a home for the gradient data
		Dataset gradientDataset = DatasetFactory.createFromObject(xGradient, 1);
		gradientDataset.setName("m term from y = mx + c fit");
		
		// Creating a home for the intercept data
		Dataset constantDataset = DatasetFactory.createFromObject(constant, 1);
		constantDataset.setName("c term from y = mx + c fit");
		
		// Now create an operation data object but also displaying the values of interest
		OperationDataForDisplay returnDataWithDisplay = new OperationDataForDisplay();
		// And a log for the user
		OperationLog log = new OperationLog();
		
		// Then some content for the log window
		log.append("Linear fit equation: y = mx + c\n");
		log.append("Fitting parameters are as follows:\n");
		log.append("Gradient (m) = %E", gradientDataset.getDouble());
		log.append("Constant (c) = %E", constantDataset.getDouble());
		
		// The output data to display
		List<IDataset> displayData = new ArrayList<>();
		displayData.add(ySlice);
		displayData.add(fittedYaxis);
		
		// Then set up the operation data object, getting it ready to return everything
		returnDataWithDisplay.setShowSeparately(true);
		returnDataWithDisplay.setLog(log);
		returnDataWithDisplay.setData(yAxis);
		returnDataWithDisplay.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		returnDataWithDisplay.setAuxData(gradientDataset, constantDataset, fittedYaxis);
		
		// Then return it
		return returnDataWithDisplay;
	}
	
	
	// A method to fit data, within limits, and calculate the linear fit returning the fitting parameters for plotting later
	public StraightLine fitLinearData(Dataset xAxis, Dataset yAxis, double[] fittingROI) {
		// Create some placeholders
		int startIndex = 0;
		int endIndex = 0;
		
		// Assuming that we've been given some values
		if (fittingROI == null) {
			startIndex = 0;
			endIndex = xAxis.getSize();
		} // Go and find them!
		else {
			// Just to make sure the indexing is right, lowest number first
			if (fittingROI[0] < fittingROI[1]) {
				startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, fittingROI[0]);
				endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, fittingROI[1]);
			} // Or we handle for this
			else {
				startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, fittingROI[1]);
				endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, fittingROI[0]);
			}
		}
		
		// Next up, we'll slice the datasets down to the size of interest
		Slice regionOfInterest = new Slice(startIndex, endIndex, 1);
		xSlice = xAxis.getSlice(regionOfInterest);
		ySlice = yAxis.getSlice(regionOfInterest);
		
		// Set up a place to place the fitting parameters
		StraightLine linearFit = new StraightLine();
		
		// Try to do the fitting on the new processed slices
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {xSlice}, ySlice, linearFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing linear fit in LinearFittingOperation(): " + fittingError.toString());
		}
		
		// Then return it
		return linearFit;
	}
}