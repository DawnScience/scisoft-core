/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;


// Imports from org.eclipse
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

// Imports from uk.ac.diamond
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
		String originalAbsoluteFilePath = (ProcessingUtils.getDataset(this, absoluteFilePath, model.getOriginalFilePath())).getString();
		double absoluteScanTime = (ProcessingUtils.getDataset(this, originalAbsoluteFilePath, model.getScanTimePath())).getDouble(0);

		// Get the background file name and then from there extract out the thickness, I_0 and I_t values as floats.
		String backgroundFilePath = model.getBackgroundScanFilePath();
		String originalBackgroundFilePath = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getOriginalFilePath())).getString();
		double backgroundThickness = (ProcessingUtils.getDataset(this, originalBackgroundFilePath, model.getThicknessPath())).getDouble(0);
		double backgroundIZero = (ProcessingUtils.getDataset(this, originalBackgroundFilePath, model.getIZeroPath())).getDouble(0);
		double backgroundITransmission = (ProcessingUtils.getDataset(this, originalBackgroundFilePath, model.getITransmissionPath())).getDouble(0);
		double backgroundScanTime = (ProcessingUtils.getDataset(this, originalBackgroundFilePath, model.getScanTimePath())).getDouble(0);

		// Get the file/frame path and then from there extract out the thickness, I_0 and I_t values as floats.
		String sampleFilePath = getSliceSeriesMetadata(sampleDataset).getFilePath();
		double sampleThickness = (ProcessingUtils.getDataset(this, sampleFilePath, model.getThicknessPath())).getDouble(0);
		double sampleIZero = (ProcessingUtils.getDataset(this, sampleFilePath, model.getIZeroPath())).getDouble(0);
		double sampleITransmission = (ProcessingUtils.getDataset(this, sampleFilePath, model.getITransmissionPath())).getDouble(0);
		double sampleScanTime = (ProcessingUtils.getDataset(this, sampleFilePath, model.getScanTimePath())).getDouble(0);

		// All scan times should match the sample, we shall assume that scaling is LINEAR here, so let's make up some factors
		double absoluteIntensityCorrector = sampleScanTime / absoluteScanTime;
		double backgroundIntensityCorrector = sampleScanTime / backgroundScanTime;

		// Get the datasets from the disk
		IDataset absoluteScanDataset = ProcessingUtils.getDataset(this, absoluteFilePath, model.getProcessedDataPath());
		IDataset backgroundDataset = ProcessingUtils.getDataset(this, backgroundFilePath, model.getProcessedDataPath());

		// Get the error datasets from the disk
		IDataset sampleErrorset = sampleDataset.getError();
		IDataset absoluteErrorset = ProcessingUtils.getDataset(this, absoluteFilePath, model.getProcessedErrorPath());
		IDataset backgroundErrorset = ProcessingUtils.getDataset(this, backgroundFilePath, model.getProcessedErrorPath());
		
		// Now we can calculate the required absorption coefficients
		// TODO Serious thinking about whether this is good - should we be halving the background abs coeff as it passes through two walls?
		double backgroundLinearAbsorptionCoefficient = 0.98;//backgroundITransmission / backgroundIZero;
		double sampleLinearAbsorptionCoefficient = 0.74;//sampleITransmission / sampleIZero;
		
		// The equation we're going to solve takes the form:
		// TODO Fill this in!
		
		// Sort out all the factors for the equation
		double sampleFractionFactor = Math.exp(-((2 * backgroundLinearAbsorptionCoefficient * backgroundThickness) + (sampleLinearAbsorptionCoefficient * sampleThickness)));
		double backgroundFractionFactor = Math.exp(-(2 * backgroundLinearAbsorptionCoefficient * backgroundThickness));
		double equationPrefactor = 1 / sampleThickness;
	
		// Find the size for the loopIters
		int[] detectorShape = absoluteScanDataset.getShape();
		
		for (int loopIterOne = 0; loopIterOne < detectorShape[2]; loopIterOne ++) {
			for (int loopIterTwo = 0; loopIterTwo < detectorShape[3]; loopIterTwo ++) {
				// First the detector pixel intensities
				double firstTerm = sampleDataset.getDouble(loopIterOne, loopIterTwo) / (absoluteScanDataset.getDouble(0, 0, loopIterOne, loopIterTwo) * sampleFractionFactor * absoluteIntensityCorrector);
				double secondTerm = (backgroundDataset.getDouble(0, 0, loopIterOne, loopIterTwo) * backgroundIntensityCorrector) / (absoluteScanDataset.getDouble(0, 0, loopIterOne, loopIterTwo) * backgroundFractionFactor * absoluteIntensityCorrector);
				double equationSolution = sampleDataset.getDouble(loopIterOne, loopIterTwo) * equationPrefactor * (firstTerm - secondTerm);
				sampleDataset.set(equationSolution, loopIterOne, loopIterTwo);
				
				// Then the errors
				firstTerm = sampleErrorset.getDouble(loopIterOne, loopIterTwo) / (absoluteErrorset.getDouble(0, 0, loopIterOne, loopIterTwo) * sampleFractionFactor * absoluteIntensityCorrector);
				secondTerm = (backgroundErrorset.getDouble(0, 0, loopIterOne, loopIterTwo) * backgroundIntensityCorrector) / (absoluteErrorset.getDouble(0, 0, loopIterOne, loopIterTwo) * backgroundFractionFactor * absoluteIntensityCorrector);
				equationSolution = sampleErrorset.getDouble(loopIterOne, loopIterTwo) * equationPrefactor * (firstTerm - secondTerm);
				sampleErrorset.set(equationSolution, loopIterOne, loopIterTwo);
			}
		}
			
		// Stick the errors back in
		sampleDataset.setError(sampleErrorset);
		
		// Finally, we can create the operation data object that will hold this
		OperationData toReturn = new OperationData();
		// Fill it
		toReturn.setData(sampleDataset);
		// toReturn.setData(backgroundSubtractedData);
		//backgroundSubtractedData = null;
		
		// And then return it		
		return toReturn;	
	}
}