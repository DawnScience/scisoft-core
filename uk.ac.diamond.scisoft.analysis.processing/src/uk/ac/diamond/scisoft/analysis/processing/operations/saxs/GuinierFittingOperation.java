/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;
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
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.MetadataType;

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.processing.operations.expressions.ExpressionServiceHolder;


// @author Tim Snow


// The operation to take a region of reduced SAXS data, obtain a Guinier plot and fit, as well as
// information that, ultimately, provides a radius of gyration

@PlotAdditionalData(onInput = false, dataName = "Fitted line from ln(I) vs q^2 plot")
public class GuinierFittingOperation extends AbstractOperation<GuinierFittingModel, OperationData>{

	// First let's declare our process ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.GuinierFittingOperation";
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
		double[] guinierROI = model.getGuinierRange();
		
		// Create some placeholders
		int startIndex = 0;
		int endIndex = 0;
		
		// Assuming that we've been given some values
		if (guinierROI == null) {
			startIndex = 0;
			endIndex = inputDataset.getSize();
		} // Go and find them!
		else {
			// Just to make sure the indexing is right, lowest number first
			if (guinierROI[0] < guinierROI[1]) {
				startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, guinierROI[0]);
				endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, guinierROI[1]);	
			} // Or we handle for this
			else {
				startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, guinierROI[1]);
				endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xAxis, guinierROI[0]);
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
		String xExpressionString = "dnp:power(xaxis, 2)";
		String yExpressionString = "dnp:log(data)";

		// Do the processing
		Dataset processedXSlice = evaluateData(xExpressionString);
		Dataset processedYSlice = evaluateData(yExpressionString);
		
		// Set the names
		processedXSlice.setName("q^2");
		processedYSlice.setName("log(I)");

		// Set up a place to place the fitting parameters
		StraightLine guinierFit = new StraightLine();
		
		// Try to do the fitting on the new processed slices
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {processedXSlice}, processedYSlice, guinierFit);
//			Fitter.llsqFit(new Dataset[] {processedXSlice}, processedYSlice, guinierFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing linear fit in GuinierFittingOperation(): " + fittingError.toString());
		}
		
		// Extract out the fitting parameters
		double gradient = guinierFit.getParameterValue(0);
		double constant = guinierFit.getParameterValue(1);

		// Do some simple calculations
		double I0 = Math.exp(constant);
		double Rg = Math.sqrt(-3.0 * gradient);
		
		// Perform a quick sanity check
		if (guinierROI != null && Rg * guinierROI[1] > 1.5  && Rg * guinierROI[1] < 0.5) {
			Rg = Double.NaN;
		}
		
		// Just for the user's sanity, create the line of best fit as well
		Dataset fittedYSlice = null;
		
		// Load in the processed x axis to recreate the fitted line
		expressionEngine.addLoadedVariable("xaxis", processedXSlice);

		// Assuming there were nice numbers, regenerate from the x-axis
		if (Double.isFinite(gradient) && Double.isFinite(constant)) {
			yExpressionString = "xaxis * " + gradient + " + " + constant;
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
		Dataset gradientDataset = DatasetFactory.createFromObject(gradient, 1);
		gradientDataset.setName("Gradient of ln(I) vs q^2 plot fit");

		// Creating a home for the intercept data
		Dataset constantDataset = DatasetFactory.createFromObject(constant, 1);
		constantDataset.setName("Intercept of ln(I) vs q^2 plot fit");

		// Creating a home for the I0 data
		Dataset iZeroDataset = DatasetFactory.createFromObject(I0, 1);
		iZeroDataset.setName("Forward scatter (I_0) from ln(I) vs q^2 fit");

		// Creating a home for the Rg data
		Dataset rgDataset = DatasetFactory.createFromObject(Rg, 1);
		rgDataset.setName("Radius of gyration (R_g) from ln(I) vs q^2 fit");
		
		// Creating a home for the fit data
		Dataset fitDataset = DatasetFactory.createFromObject(fittedYSlice, fittedYSlice.getShape());
		fitDataset.setName("Fitted line from ln(I) vs q^2 plot");
		fitDataset.setMetadata(fitAxisMetadata);
		// Before creating the OperationData object to save everything in
		
		// Now create an operation data object but also displaying the values of interest
		OperationDataForDisplay returnDataWithDisplay = new OperationDataForDisplay();
		// And a log for the user
		OperationLog log = new OperationLog();
		
		// Then some content for the log window
		log.append("Guinier fit, the linear fit of log(I) against q^2");
		log.append("where a linear fit is a y = mx + c fit\n");
		log.append("Fitting parameters are as follows:\n");
		log.append("Gradient (m) = %E", gradientDataset.getDouble());
		log.append("Constant (c) = %E\n", constantDataset.getDouble());
		log.append("From these parameters the following are deduced:\n");
		log.append("The forward scatter (I0) is %E", iZeroDataset.getDouble());
		log.append("The Rg value is %E", rgDataset.getDouble());		
		// The output data to display
		List<IDataset> displayData = new ArrayList<>();
		displayData.add(fittedYSlice);
		displayData.add(processedYSlice);
		
		// Then set up the operation data object, getting it ready to return everything
		returnDataWithDisplay.setShowSeparately(true);
		returnDataWithDisplay.setLog(log);
		returnDataWithDisplay.setData(inputDataset);
		returnDataWithDisplay.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		returnDataWithDisplay.setAuxData(gradientDataset, constantDataset, fitDataset, iZeroDataset, rgDataset, processedYSlice);

		// Then return it
		return returnDataWithDisplay;
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