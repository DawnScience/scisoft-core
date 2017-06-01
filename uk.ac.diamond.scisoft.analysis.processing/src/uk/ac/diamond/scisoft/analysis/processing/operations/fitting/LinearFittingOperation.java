/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.fitting;


// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.MetadataFactory;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.PlotAdditionalData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;


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
	private Dataset fittedYaxis;
	
	
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

		// Get out the start and end values of the fitting range
		double[] fittingROI = model.getFittingRange();
		
		// Perform the fitting
		StraightLine linearFit = this.fitLinearData(xAxis, yAxis, fittingROI, inputDataset.getSize());
		
		// Extract out the fitting parameters
		double xGradient = linearFit.getParameterValue(0);
		double constant = linearFit.getParameterValue(1);

		this.fittedYaxis = DatasetFactory.zeros(yAxis.getSize());
		
		// Assuming there were nice numbers, regenerate from the x-axis
		if (Double.isFinite(xGradient) && Double.isFinite(constant)) {
			for (int loopIter = 0; loopIter < yAxis.getSize(); loopIter ++) {
				// y = (m * x) + c
				double fitVariable = (xGradient * xAxis.getDouble(loopIter)) + constant;
				this.fittedYaxis.set(fitVariable, loopIter);
			}	
		}
		
		// Just to see what's going on
		AxesMetadata xAxisMetadata;

		// We'll create the xAxis used in the regression for plotting
		try {
			xAxisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			xAxisMetadata.setAxis(0, xAxis);
		} catch (MetadataException xAxisError) {
			throw new OperationException(this, xAxisError.getMessage());
		}

		// Filling the fit dataset with the processed x axis
		this.fittedYaxis.setName("Linear Fit");
		this.fittedYaxis.setMetadata(xAxisMetadata);
		
		// Creating a home for the gradient data
		Dataset gradientDataset = DatasetFactory.createFromObject(xGradient, 1);
		gradientDataset.setName("m term from y = mx + c fit");

		// Creating a home for the intercept data
		Dataset constantDataset = DatasetFactory.createFromObject(constant, 1);
		constantDataset.setName("c term from y = mx + c fit");

		// Before creating the OperationData object to save everything in
		OperationData toReturn = new OperationData(inputDataset);
		// And all the other variables
		toReturn.setAuxData(this.fittedYaxis, gradientDataset, constantDataset);
		
		// And then returning it		
		return toReturn;
	}
	
	
	// A method to fit data, within limits, and calculate the linear fit returning the fitting parameters for plotting later
	public StraightLine fitLinearData(Dataset xAxis, Dataset yAxis, double[] fittingROI, int dataLength) {
		// Create some placeholders
		int startIndex = 0;
		int endIndex = 0;
		
		// Assuming that we've been given some values
		if (fittingROI == null) {
			startIndex = 0;
			endIndex = dataLength;
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
		Dataset xSlice = xAxis.getSlice(regionOfInterest);
		Dataset ySlice = yAxis.getSlice(regionOfInterest);
		
		// Set up a place to place the fitting parameters
		StraightLine linearFit = new StraightLine();
		
		// Try to do the fitting on the new processed slices
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {xSlice}, ySlice, linearFit);
//			Fitter.llsqFit(new Dataset[] {xSlice}, ySlice, linearFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing linear fit in LinearFittingOperation(): " + fittingError.toString());
		}

		// Then return it
		return linearFit;
	}
}