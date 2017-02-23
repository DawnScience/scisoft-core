/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.MetadataType;
// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.PlotAdditionalData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.MetadataException;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.KratkyFittingModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.expressions.ExpressionServiceHolder;


// @author Tim Snow


// The operation to take a region of reduced SAXS data, obtain a Guinier plot and fit, as well as
// information that, ultimately, provides information about the shape of the molecule

@PlotAdditionalData(onInput = false, dataName = "Fitted line from ln(I) vs q^2 plot")
public class KratkyFittingOperation extends AbstractOperation<KratkyFittingModel, OperationData>{

	// First let's declare our process ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.KratkyFittingOperation";
	}

	
	// In order to do our mathematics, we shall instantiate an expression and regression engine
	private IExpressionEngine expressionEngine;

	
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

		// First up, let's check that our expression engine is set up properly
		if (expressionEngine == null) {
			try {
				IExpressionService service = ExpressionServiceHolder.getExpressionService();
				expressionEngine = service.getExpressionEngine();
			} catch (Exception engineError) {
				// If not, we'll raise an error
				throw new OperationException(this, engineError.getMessage());
			}
		}
		
		// Next, we'll extract out the x axis (q) dataset from the input
		Dataset xAxis;
		// Just in case we don't have an x-axis (as we really need an x axis)
		try {
			xAxis = DatasetUtils.convertToDataset(inputDataset.getFirstMetadata(AxesMetadata.class).getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		// Extract out the y axis (intensity) from the input
		Dataset yAxis = DatasetUtils.convertToDataset(inputDataset);

		// Get out the start and end values of the Guinier range
		double[] kratkyROI = model.getKratkyRange();
		
		// Create some placeholders
		int startIndex = 0;
		int endIndex = 0;
		
		// Assuming that we've been given some values
		if (kratkyROI == null) {
			startIndex = 0;
			endIndex = inputDataset.getSize();
		} // Go and find them!
		else {
			// Just to make sure the indexing is right, lowest number first
			if (kratkyROI[0] < kratkyROI[1]) {
				startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, kratkyROI[0]);
				endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, kratkyROI[1]);	
			} // Or we handle for this
			else {
				startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, kratkyROI[1]);
				endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, kratkyROI[0]);
			}
		}
		
		// Next up, we'll slice the datasets down to the size of interest
		Slice regionOfInterest = new Slice(startIndex, endIndex, 1);
		Dataset xSlice = xAxis.getSlice(regionOfInterest);
		Dataset ySlice = yAxis.getSlice(regionOfInterest);
		
		// Then add these slices to the expression engine
		expressionEngine.addLoadedVariable("xaxis", xSlice);
		expressionEngine.addLoadedVariable("data", ySlice);
		
		// The hard-coded variables for the Guinier Fitting
		String yExpressionString = "dnp:power(xaxis, 2) * data";

		// Do the processing
		Dataset processedXSlice = xSlice;
		Dataset processedYSlice = evaluateData(yExpressionString);
		
		// Set the names
		processedXSlice.setName("q");
		processedYSlice.setName("I * q^2");

		// Set up a place to place the fitting parameters
		//StraightLine kratkyFit = new StraightLine();
		Polynomial kratkyFit = new Polynomial(2);
		
		// Try to do the fitting on the new processed slices
		try {
			//Fitter.llsqFit(new Dataset[] {processedXSlice}, processedYSlice, kratkyFit);
			Fitter.polyFit(new Dataset[] {processedXSlice}, processedYSlice, 1e-15, kratkyFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing linear fit in KratkyFittingOperation(): " + fittingError.toString());
		}
		
		// Extract out the fitting parameters
		double xSquaredGradient = kratkyFit.getParameterValue(0);
		double xGradient = kratkyFit.getParameterValue(1);
		double intercept = kratkyFit.getParameterValue(2);

		// Just for the user's sanity, create the line of best fit as well
		Dataset fittedYSlice = null;
		
		// Load in the processed x axis to recreate the fitted line
		expressionEngine.addLoadedVariable("xaxis", processedXSlice);

		// Assuming there were nice numbers, regenerate from the x-axis
		if (Double.isFinite(xGradient) && Double.isFinite(intercept)) {
			yExpressionString = "(" + xSquaredGradient + " * xaxis * xaxis) + (xaxis * " + xGradient + ") + " + intercept;
			fittedYSlice = evaluateData(yExpressionString);
		}
		else {
			// If the values from the fit are bad, create a null dataset of the length of the x axis
			yExpressionString = "xaxis * 0";
			fittedYSlice = evaluateData(yExpressionString);
		}
		
		// Just to see what's going on
		AxesMetadata xAxisMetadata;

		// We'll create the xAxis used in the regression for plotting
		try {
			xAxisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException xAxisError) {
			throw new OperationException(this, xAxisError.getMessage());
		}

		// Filling the object with the processed x axis slice
		xAxisMetadata.setAxis(0, processedXSlice);
		MetadataType fitAxisMetadata = xAxisMetadata.clone();
		
		// And then placing this in the processedYSlice
		processedYSlice.setMetadata(xAxisMetadata);
	
		// Creating a home for the gradient data
		Dataset gradientDataset = DatasetFactory.createFromObject(xGradient, 1);
		gradientDataset.setName("x term from I * q^2 vs q fit");

		// Creating a home for the intercept data
		Dataset interceptDataset = DatasetFactory.createFromObject(intercept, 1);
		interceptDataset.setName("c term from I * q^2 vs q fit");

		// Creating a home for the intercept data
		Dataset xSquaredGradientDataset = DatasetFactory.createFromObject(xSquaredGradient, 1);
		xSquaredGradientDataset.setName("x^2 term from I * q^2 vs q fit");

		// Creating a home for the fit data
		Dataset fitDataset = DatasetFactory.createFromObject(fittedYSlice, fittedYSlice.getShape());
		fitDataset.setName("Fitted line from ln(I) vs q^2 plot");
		fitDataset.setMetadata(fitAxisMetadata);

		// Before creating the OperationData object to save everything in
		OperationData toReturn = new OperationData();
		// Filling it with data
		toReturn.setData(processedYSlice);
		// And all the other variables
		toReturn.setAuxData(gradientDataset, interceptDataset, fitDataset, xSquaredGradientDataset);
		
		// And then returning it		
		return toReturn;
	}
	
	
	// A method to evaluate input data against a given expression, for 1D data only.
	protected Dataset evaluateData(String expression) throws OperationException {
		// First up, somewhere for the outputs to go
		Dataset output = null;
		Object outObject = null;
		
		// Next, try to set the expression
		try {
			expressionEngine.createExpression(expression);
		} catch (Exception expressionError) {
			throw new OperationException(this, expressionError.getMessage());
		}
		
		// Try to evaluate the input with the expression given
		try {
			outObject = expressionEngine.evaluate();
		} catch (Exception evalutationError) {
			throw new OperationException(this, evalutationError.getMessage());
		}
		
		// Finally, check if the outObject is the kind of data we're expecting and set it if it is
		if (outObject instanceof Dataset && ((Dataset)outObject).getRank() == 1) {
			output = (Dataset) outObject;
		} else {
			throw new OperationException(this, "The evaluated output was not as expected");
		}

		// Now, return it 
		return output;
	}
}