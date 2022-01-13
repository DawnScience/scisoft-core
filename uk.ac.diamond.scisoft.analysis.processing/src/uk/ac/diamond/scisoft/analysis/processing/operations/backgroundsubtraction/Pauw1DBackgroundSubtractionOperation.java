/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;


import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

//import org.apache.commons.lang.ArrayUtils;

// Imports from org.eclipse
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;


//More information and the equation for this background subtraction routine can be found in:
//Everything SAXS: small-angle scattering pattern collection and correction
//B. R. Pauw, Journal of Physics: Condensed Matter, 2013, 25, 383201. 
//DOI: 10.1088/0953-8984/25/38/383201


//@author Tim Snow


//The model for a DAWN processing plugin to perform background subtraction on a scattered diffraction pattern
public class Pauw1DBackgroundSubtractionOperation extends AbstractOperation<Pauw1DBackgroundSubtractionModel, OperationData> {


	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.Pauw1DBackgroundSubtractionOperation";
	}


	// Before we start, let's make sure we know how many dimensions of data are going in...
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
	public OperationData process(IDataset sampleDataset, IMonitor monitor) throws OperationException {

		// Get metadata about the file/frame passed to this process
		SliceFromSeriesMetadata sliceSeriesMetadata = getSliceSeriesMetadata(sampleDataset);
		
		// Get the file/frame path and then from there extract out the thickness, I_0 and I_t values as floats.
		String sampleFilePath = sliceSeriesMetadata.getFilePath();
		double sampleThickness = (ProcessingUtils.getDataset(this, sampleFilePath, model.getThicknessPath())).getDouble(0);
		double sampleIZero = (ProcessingUtils.getDataset(this, sampleFilePath, model.getIZeroPath())).getDouble(0);
		double sampleITransmission = (ProcessingUtils.getDataset(this, sampleFilePath, model.getITransmissionPath())).getDouble(0);

		// Get the background file name and then from there extract out the thickness, I_0 and I_t values as floats.
		String backgroundFilePath = model.getBackgroundFilePath();
		double backgroundThickness = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getThicknessPath())).getDouble(0);
		double backgroundIZero = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getIZeroPath())).getDouble(0);
		double backgroundITransmission = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getITransmissionPath())).getDouble(0);

		// Get the absolute scan file name and then from there extract out the reduced data
		IDataset reducedAbsoluteScanDataset = ProcessingUtils.getDataset(this, model.getAbsoluteScanFilepath(), model.getReducedBackgroundData());

		// Get the reduced background file name and then from there extract out the reduced data
		IDataset reducedBackgroundDataset = ProcessingUtils.getDataset(this, model.getReducedBackgroundFilePath(), model.getReducedBackgroundData());

		// Take the IDatasets we've been given and make them a bit more workable
		Dataset absoluteScanData = DatasetUtils.convertToDataset(reducedAbsoluteScanDataset);
		Dataset backgroundData = DatasetUtils.convertToDataset(reducedBackgroundDataset);
		Dataset sampleData = DatasetUtils.convertToDataset(sampleDataset);

		// Now we can calculate the required absorption coefficients
		// TODO Serious thinking about whether this is good - should we be halving the background abs coeff as it passes through two walls?
		double backgroundLinearAbsorptionCoefficient = backgroundITransmission / backgroundIZero;
		double sampleLinearAbsorptionCoefficient = sampleITransmission / sampleIZero;
		
		// The equation we're going to solve takes the form:
		// TODO Fill this in!
		
		// Sort out all the factors for the equation
		double sampleFractionFactor = 1 / Math.exp(-((2 * backgroundLinearAbsorptionCoefficient * backgroundThickness) + (sampleLinearAbsorptionCoefficient * sampleThickness)));
		double backgroundFractionFactor = 1 / Math.exp(-(2 * backgroundLinearAbsorptionCoefficient * backgroundThickness));
		double equationPrefactor = 1 / sampleThickness;
		
		// Now cast into float datasets  so we can go through using the error propogation utilities
		Dataset sampleFractionFactorData = DatasetFactory.createFromObject(DoubleDataset.class, sampleFractionFactor);
		Dataset backgroundFractionFactorData = DatasetFactory.createFromObject(DoubleDataset.class, backgroundFractionFactor);
		Dataset equationPrefactorData = DatasetFactory.createFromObject(DoubleDataset.class, equationPrefactor);

//		Dataset hermanOrientationDataset = DatasetFactory.zeros(datasetSize);
//		hermanOrientationDataset.set(hermanOrientationFactor, 0);

		// Solve the first term of the equation inside the brackets
		Dataset sampleOverAbsoluteScatterData = ErrorPropagationUtils.divideWithUncertainty(sampleData, absoluteScanData);
		Dataset firstEquationTerm = ErrorPropagationUtils.multiplyWithUncertainty(sampleOverAbsoluteScatterData, sampleFractionFactorData);
		//sampleOverAbsoluteScatterData = null;
		
		// Solve the second term of the equation inside the brackets
		Dataset backgroundOverAbsoluteScatterData = ErrorPropagationUtils.divideWithUncertainty(backgroundData, absoluteScanData);
		Dataset secondEquationTerm = ErrorPropagationUtils.multiplyWithUncertainty(backgroundOverAbsoluteScatterData, backgroundFractionFactorData);
		//backgroundOverAbsoluteScatterData = null;

		// Solve the equation
		Dataset equationTermInsideBrackets = ErrorPropagationUtils.subtractWithUncertainty(firstEquationTerm, secondEquationTerm);
		Dataset sampleScatterProbabilityData = ErrorPropagationUtils.multiplyWithUncertainty(equationTermInsideBrackets, equationPrefactorData);
		//equationTermInsideBrackets = null;

		// Apply it to the diffraction image
		Dataset backgroundSubtractedData = ErrorPropagationUtils.multiplyWithUncertainty(sampleData, sampleScatterProbabilityData);
		//sampleScatterProbabilityData = null;
		
		// Finally, we can create the operation data object that will hold this
		OperationData toReturn = new OperationData();
		// Fill it
		toReturn.setData(backgroundSubtractedData);
		//backgroundSubtractedData = null;
		
		// And then return it		
		return toReturn;	
	}
}
