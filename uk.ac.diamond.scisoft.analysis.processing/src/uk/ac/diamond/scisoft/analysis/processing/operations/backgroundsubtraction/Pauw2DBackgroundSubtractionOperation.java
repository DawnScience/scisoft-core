/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;


import org.eclipse.january.DatasetException;

//import org.apache.commons.lang.ArrayUtils;

// Imports from org.eclipse
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.RunningAverage;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Slice;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.Pauw2DBackgroundSubtractionModel;


//More information and the equation for this background subtraction routine can be found in:
//Everything SAXS: small-angle scattering pattern collection and correction
//B. R. Pauw, Journal of Physics: Condensed Matter, 2013, 25, 383201. 
//DOI: 10.1088/0953-8984/25/38/383201


//@author Tim Snow


//The model for a DAWN processing plugin to perform background subtraction on a scattered diffraction pattern
public class Pauw2DBackgroundSubtractionOperation extends AbstractOperation<Pauw2DBackgroundSubtractionModel, OperationData> {


	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.Pauw2DBackgroundSubtractionOperation";
	}


	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}


	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset sampleDataset, IMonitor monitor) throws OperationException {

		// Get metadata about the file/frame passed to this process
		SliceFromSeriesMetadata sliceSeriesMetadata = getSliceSeriesMetadata(sampleDataset);
		
		// Get the absolute scan file name and then from there extract out the thickness, I_0 and I_t values as floats.
		String absoluteFilePath = model.getAbsoluteScanFilePath();
		// double absoluteThickness = (ProcessingUtils.getDataset(this, absoluteFilePath, model.getThicknessPath())).getDouble(0);
		// double absoluteIZero = (ProcessingUtils.getDataset(this, absoluteFilePath, model.getIZeroPath())).getDouble(0);
		// double absoluteITransmission = (ProcessingUtils.getDataset(this, absoluteFilePath, model.getITransmissionPath())).getDouble(0);
		double absoluteScanTime = (ProcessingUtils.getDataset(this, absoluteFilePath, model.getScanTimePath())).getDouble(0);

		// Get the background file name and then from there extract out the thickness, I_0 and I_t values as floats.
		String backgroundFilePath = model.getBackgroundScanFilePath();
		double backgroundThickness = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getThicknessPath())).getDouble(0);
		double backgroundIZero = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getIZeroPath())).getDouble(0);
		double backgroundITransmission = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getITransmissionPath())).getDouble(0);
		double backgroundScanTime = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getScanTimePath())).getDouble(0);

		// Get the file/frame path and then from there extract out the thickness, I_0 and I_t values as floats.
		String sampleFilePath = sliceSeriesMetadata.getFilePath();
		double sampleThickness = (ProcessingUtils.getDataset(this, sampleFilePath, model.getThicknessPath())).getDouble(0);
		double sampleIZero = (ProcessingUtils.getDataset(this, sampleFilePath, model.getIZeroPath())).getDouble(0);
		double sampleITransmission = (ProcessingUtils.getDataset(this, sampleFilePath, model.getITransmissionPath())).getDouble(0);
		double sampleScanTime = (ProcessingUtils.getDataset(this, sampleFilePath, model.getScanTimePath())).getDouble(0);

		// All scan times should match the sample, we shall assume that scaling is LINEAR here, so let's make up some factors
		double absoluteIntensityCorrector = sampleScanTime / absoluteScanTime;
		double backgroundIntensityCorrector = sampleScanTime / backgroundScanTime;

		// Then convert to datasets for the error propogated multiplication
		Dataset absoluteIntensityCorrectorData = DatasetFactory.createFromObject(Dataset.FLOAT64, absoluteIntensityCorrector);
		Dataset backgroundIntensityCorrectorData = DatasetFactory.createFromObject(Dataset.FLOAT64, backgroundIntensityCorrector);

		// Get the background images and average them, work out errors and time correct them, relative to the sample
//		ILazyDataset absoluteScanDataset = ProcessingUtils.getLazyDataset(this, absoluteFilePath, model.getDetectorDataPath());
		ILazyDataset absoluteScanDataset = ProcessingUtils.getLazyDataset(this, absoluteFilePath, model.getDetectorDataPath());
		ILazyDataset backgroundDataset = ProcessingUtils.getLazyDataset(this, backgroundFilePath, model.getDetectorDataPath());

		Dataset absoluteScanData = null;
		Dataset backgroundData = null;
		
		try {
			absoluteScanDataset = absoluteScanDataset.getSlice(new Slice(0,1), new Slice(0,1), null, null);
			absoluteScanData = DatasetUtils.sliceAndConvertLazyDataset(absoluteScanDataset);
			backgroundDataset = backgroundDataset.getSlice(new Slice(0,1), new Slice(0,1), null, null);
			backgroundData = DatasetUtils.sliceAndConvertLazyDataset(backgroundDataset);
		} catch (DatasetException e) {
			// TODO Auto-generated catch block
		}
		
		absoluteScanData = PoissonError(absoluteScanData);
		absoluteScanData = ErrorPropagationUtils.multiplyWithUncertainty(absoluteScanData, absoluteIntensityCorrectorData);
		absoluteScanDataset = null;

		backgroundData = ErrorPropagationUtils.multiplyWithUncertainty(backgroundData, backgroundIntensityCorrectorData);
		backgroundData = PoissonError(backgroundData);

		backgroundDataset = null;
		
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
		Dataset sampleFractionFactorData = DatasetFactory.createFromObject(Dataset.FLOAT64, sampleFractionFactor);
		Dataset backgroundFractionFactorData = DatasetFactory.createFromObject(Dataset.FLOAT64, backgroundFractionFactor);
		Dataset equationPrefactorData = DatasetFactory.createFromObject(Dataset.FLOAT64, equationPrefactor);

		// Solve the first term of the equation inside the brackets
		Dataset sampleOverAbsoluteScatterData = ErrorPropagationUtils.divideWithUncertainty(sampleData, absoluteScanData);
		Dataset firstEquationTerm = ErrorPropagationUtils.multiplyWithUncertainty(sampleOverAbsoluteScatterData, sampleFractionFactorData);
		sampleOverAbsoluteScatterData = null;
		
		// Solve the second term of the equation inside the brackets
		Dataset backgroundOverAbsoluteScatterData = ErrorPropagationUtils.divideWithUncertainty(backgroundData, absoluteScanData);
		Dataset secondEquationTerm = ErrorPropagationUtils.multiplyWithUncertainty(backgroundOverAbsoluteScatterData, backgroundFractionFactorData);
		backgroundOverAbsoluteScatterData = null;

		// Solve the equation
		Dataset equationTermInsideBrackets = ErrorPropagationUtils.subtractWithUncertainty(firstEquationTerm, secondEquationTerm);
		Dataset sampleScatterProbabilityData = ErrorPropagationUtils.multiplyWithUncertainty(equationTermInsideBrackets, equationPrefactorData);
		equationTermInsideBrackets = null;

		// Apply it to the diffraction image
		Dataset backgroundSubtractedData = ErrorPropagationUtils.multiplyWithUncertainty(sampleData, sampleScatterProbabilityData);
		sampleScatterProbabilityData = null;
		
		// Stick the metadata back in
		backgroundSubtractedData.setMetadata(sliceSeriesMetadata);
		
		// Finally, we can create the operation data object that will hold this
		OperationData toReturn = new OperationData();
		// Fill it
		toReturn.setData(backgroundSubtractedData);
		// toReturn.setData(backgroundSubtractedData);
		//backgroundSubtractedData = null;
		
		// And then return it		
		return toReturn;	
	}
	

	// To calculate poisson errors
	public Dataset PoissonError(Dataset inputDataset) {
		// Set up a place to hold the errors
		DoubleDataset errorDataset = DatasetFactory.zeros(DoubleDataset.class, inputDataset.getShape());

		// Set up something to iterate over the whole dataset
		IndexIterator iterator = inputDataset.getIterator();

		// Set up a value
		double piexlValue = 0;

		// Loop through dataset
		while (iterator.hasNext()) {
			piexlValue = inputDataset.getElementDoubleAbs(iterator.index);
			if (piexlValue < 0) continue;
			errorDataset.setAbs(iterator.index, Math.sqrt(piexlValue));
		}
		
		inputDataset.setError(errorDataset);

		return inputDataset;
	}
}
