/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Parameter;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;

// Importing the logger!
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@author Tim Snow


public class UsaxsZeroThetaOperation extends AbstractOperation<EmptyModel, OperationData> {
	
	
	// First, set up a logger
	private static final Logger logger = LoggerFactory.getLogger(UsaxsZeroThetaOperation.class);
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsZeroThetaOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) {
		
		// First, we'll extract out the x axis (q) dataset from the input and create a home for it's return
		AxesMetadata xAxisMetadata;
		Dataset xAxis;
		
		// Just in case we don't have an x-axis (as we could do with an x axis)
		try {
			xAxisMetadata = input.getFirstMetadata(AxesMetadata.class);
			xAxis = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		// Extract out the y axis (intensity) from the input
		Dataset yAxis = DatasetUtils.convertToDataset(input);
		
		// Set up a place to place the fitting parameters and set some first guesses
		Gaussian gaussianFit = new Gaussian();
		gaussianFit.setParameter(0, new Parameter(xAxis.getDouble(yAxis.maxPos(null)[0])));
		gaussianFit.setParameter(1, new Parameter(0.1));
		gaussianFit.setParameter(2, new Parameter(yAxis.sum(0, null).getDouble()));
		
		// Try to do the fitting on the new processed slices
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {xAxis}, yAxis, gaussianFit);
		} catch (Exception fittingError) {
			logger.error("Exception performing linear fit in USAXS Zero Theta Operation: " + fittingError.toString());
		}
		
		// Oversample the x axis and create the matching y axis
		Dataset fittingAxis = DatasetFactory.createLinearSpace(DoubleDataset.class, xAxis.min(null).doubleValue(), xAxis.max(null).doubleValue(), input.getSize()*100);
		Dataset fittedIntensities = gaussianFit.calculateValues(fittingAxis);
		
		// Find the peak location
		Double peakLocation = fittingAxis.getDouble(fittedIntensities.maxPos(null)[0]);
		
		// Subtract this location from all values along the current x axis
		xAxis = Maths.subtract(xAxis, peakLocation);
		
		// Set this, corrected, dataset to the x axis
		xAxisMetadata.setAxis(0, xAxis);
		input.setMetadata(xAxisMetadata);
		
		// Return it!
		return new OperationData(input);
	}
}
