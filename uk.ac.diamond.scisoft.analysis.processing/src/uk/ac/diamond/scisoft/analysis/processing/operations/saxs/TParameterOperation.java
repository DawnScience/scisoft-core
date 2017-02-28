/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.MetadataFactory;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

// Imports from org.slf4j
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.processing.operations.expressions.ExpressionServiceHolder;

// Imports from other classes within this package
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.PorodFittingOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.KratkyFittingOperation;


// @author Tim Snow, adapted from original plug-in set by Tim Spain.


//
public class TParameterOperation extends AbstractOperation<TParameterModel, OperationData>{

	
	// Let's set up a logger first
	private static final Logger logger = LoggerFactory.getLogger(TParameterOperation.class);
	

	
	// First let's declare our process ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.TParameterOperation";
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
				logger.warn("There was an error creating the T Parameter expression engine", engineError.getMessage());
				throw new OperationException(this, engineError.getMessage());
			}
		}

		// Then extract out the relevant datasets
		Dataset xAxis = this.xAxisExtractor(inputDataset);
		Dataset yAxis = this.yAxisExtractor(inputDataset);
		
		// And regions of interest
		double[] porodROI = model.getPorodRange();
		double[] kratkyROI = model.getKratkyRange();
		
		// And the length of the data
		int dataLength = xAxis.getSize();
		
		// Then we'll create some homes for the Porod and Kratky fitting
		PorodFittingOperation porodFitting = new PorodFittingOperation();
		KratkyFittingOperation kratkyFitting = new KratkyFittingOperation();
		
		// Then do the fitting
		StraightLine porodFit = porodFitting.fitPorodData(xAxis, yAxis, porodROI, dataLength);
		Polynomial kratkyFit = kratkyFitting.fitKratkyData(xAxis, yAxis, kratkyROI, dataLength);
			
		// Extract out the Porod fitting parameters
		double porodGradient = porodFit.getParameterValue(0);
		double porodConstant = porodFit.getParameterValue(1);
		
		// Extract out the Kratky fitting parameters
		double kratkySquaredGradient = kratkyFit.getParameterValue(0);
		double kratkyGradient = kratkyFit.getParameterValue(1);
		double kratkyConstant = kratkyFit.getParameterValue(2);
		
		// And create some data to show on screen
		// For the Porod
		Dataset yDataPorod = DatasetFactory.zeros(dataLength);
		yDataPorod.setName("Porod plot");
		Dataset yFitPorod = DatasetFactory.zeros(dataLength);
		yFitPorod.setName("Fit");

		// and the Kratky
		Dataset yDataKratky = DatasetFactory.zeros(dataLength);
		yDataKratky.setName("Kratky plot");
		Dataset yFitKratky = DatasetFactory.zeros(dataLength);
		yFitKratky.setName("Fit");
		
		// Assuming there were nice numbers for the Porod fitting
		if (Double.isFinite(porodGradient) && Double.isFinite(porodConstant)) {
			for (int loopIter = 0; loopIter < dataLength; loopIter ++) {
				double dataVariable = Math.pow(xAxis.getDouble(loopIter), 4) * yAxis.getDouble(loopIter);
				double fitVariable = Math.pow(xAxis.getDouble(loopIter), 4) * Math.exp(porodGradient * (Math.log(xAxis.getDouble(loopIter))) + porodConstant);
				
				yDataPorod.set(dataVariable, loopIter);
				yFitPorod.set(fitVariable, loopIter);
			}
		}

		// Assuming there were nice numbers for the Kratky fitting
		if (Double.isFinite(kratkySquaredGradient) && Double.isFinite(kratkyGradient) && Double.isFinite(kratkyConstant)) {
			for (int loopIter = 0; loopIter < dataLength; loopIter ++) {
				double dataVariable = Math.pow(xAxis.getDouble(loopIter), 2) * yAxis.getDouble(loopIter);
				double fitVariable = (kratkySquaredGradient * Math.pow(xAxis.getDouble(loopIter), 2)) + (kratkyGradient * xAxis.getDouble(loopIter)) + kratkyConstant;

				yDataKratky.set(dataVariable, loopIter);
				yFitKratky.set(fitVariable, loopIter);
			}
		}

		// Finally, set the x axes for all the different plots, firstly by creating a home for the axis metadata
		AxesMetadata xAxisMetadata;

		// Then preparing it for receiving the necessary
		try {
			xAxisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException xAxisError) {
			logger.warn("There was an error creating the output x-axes for the T Parameter plugin", xAxisError.getMessage());
			throw new OperationException(this, xAxisError.getMessage());
		}

		// Then setting the x axis values
		xAxis.setName("q");
		xAxisMetadata.setAxis(0, xAxis);
		
		// And finally, inserting them into all the relevant datasets
		inputDataset.setMetadata(xAxisMetadata.clone());
		yDataPorod.setMetadata(xAxisMetadata.clone());
		yFitPorod.setMetadata(xAxisMetadata.clone());
		yDataKratky.setMetadata(xAxisMetadata.clone());
		yFitKratky.setMetadata(xAxisMetadata.clone());
		
		// Then a more relevant input dataset name
		inputDataset.setName("Scattered intensity");
		
		// Now that all the UI has been calculated and that we have all the required values, let's do the T-Parameter mathematics
		// High-q and low-q fitted integrals 
		double jPorod = porodConstant / porodROI[0];
		double jKratky = 1.00;
		// TODO DELETE ABOVE LINE RE-IMPLEMENT LINE BELOW
		//double jKratky = tP.getKratkyIntegral(); // Still need to work on this
		// Experimental integral
		double jExp;

		// First the lower q region
		int lowerKratkyIndex = DatasetUtils.findIndexGreaterThan(xAxis, kratkyROI[0]);
		// Then the higher q region
		int lowerPorodIndex = DatasetUtils.findIndexGreaterThan(xAxis, porodROI[0]);

		// Then get ready to take a slice of the input data
		Slice tParameterSlice = new Slice(lowerKratkyIndex, lowerPorodIndex);
		Dataset dataSlice = DatasetUtils.convertToDataset(inputDataset.getSlice(tParameterSlice));
		Dataset qSlice = xAxis.getSlice(tParameterSlice);
		
		// Integration
		// Is rectangle rule good for you?
		Dataset integrand = Maths.multiply(Maths.square(qSlice), dataSlice);
		Dataset indices = DatasetFactory.createRange(DoubleDataset.class, (double) lowerPorodIndex - lowerKratkyIndex);
		Dataset dq = Maths.derivative(indices, qSlice, 1);
		jExp = (double) Maths.multiply(integrand, dq).sum();
		// Add any bits between the pieces of the integral
		jExp = (xAxis.getDouble(lowerKratkyIndex) - kratkyROI[0]) * inputDataset.getDouble(lowerKratkyIndex) + jExp + (xAxis.getDouble(lowerPorodIndex) - kratkyROI[0]) * ((lowerKratkyIndex != inputDataset.getSize() - 1) ? inputDataset.getDouble(lowerKratkyIndex+1) : inputDataset.getDouble(lowerKratkyIndex));
		double j = jKratky + jExp + jPorod;
		
		double t = 4/(Math.PI * porodConstant) * j;
		System.out.println("T = " + t);

		Dataset tParameterDataset = DatasetFactory.createFromObject(new double[] {t});
		tParameterDataset.setName("Crystallite thickness");
		
		// With all this in hand, let's return the data!
		//OperationData toReturn = new OperationData(inputDataset);
		OperationData toReturn = new OperationData(inputDataset);
		
		toReturn.setAuxData(yDataPorod, yFitPorod, yDataKratky, yFitKratky, xAxis, tParameterDataset);
		// And then returning it
		return toReturn;
	}
	
	
	private Dataset xAxisExtractor(IDataset inputDataset) {
		// Next, we'll extract out the x axis (q) dataset from the input
		Dataset xAxis;
		// Just in case we don't have an x-axis (as we really need an x axis)
		try {
			xAxis = DatasetUtils.convertToDataset(inputDataset.getFirstMetadata(AxesMetadata.class).getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			logger.warn("There was an error loading the input x-axis for the T Parameter plugin", xAxisError.getMessage());
			throw new OperationException(this, xAxisError);
		}
		// Now return it
		return xAxis;
	}
	
	
	private Dataset yAxisExtractor(IDataset inputDataset) {
		// Yes, it's a waste but it's for consistency
		Dataset yAxis = DatasetUtils.convertToDataset(inputDataset);
		// Now return it
		return yAxis;	
	}
}