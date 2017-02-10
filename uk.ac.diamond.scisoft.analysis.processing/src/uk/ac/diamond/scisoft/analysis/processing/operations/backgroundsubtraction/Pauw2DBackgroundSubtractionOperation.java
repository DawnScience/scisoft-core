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
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;


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

		String beamlineBackgroundFilePath = model.getBeamlineBackgroundScanFilePath();
		double beamlineBackgroundIZero = (ProcessingUtils.getDataset(this, beamlineBackgroundFilePath, model.getIZeroPath())).getDouble(0);
		double beamlineBackgroundITransmission = (ProcessingUtils.getDataset(this, beamlineBackgroundFilePath, model.getITransmissionPath())).getDouble(0);
		// Not needed but left in case of need to debug
		// double beamlineBackgroundScanTime = (ProcessingUtils.getDataset(this, beamlineBackgroundFilePath, model.getScanTimePath())).getDouble(0);

		// Now calculate the absorption coefficient
		double iTransmissionCorrection = beamlineBackgroundIZero / beamlineBackgroundITransmission;
		beamlineBackgroundITransmission *= iTransmissionCorrection;
		// Not needed but left in case of need to debug
		// double beamlineBackgroundLinearAbsorptionCoefficient = (-Math.log(beamlineBackgroundITransmission / beamlineBackgroundIZero));
		
		// Get the background file name and then from there extract out the thickness, I_0 and I_t values and scan time.
		String backgroundFilePath = model.getBackgroundScanFilePath();
		double backgroundIZero = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getIZeroPath())).getDouble(0);
		double backgroundITransmission = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getITransmissionPath())).getDouble(0);
		double backgroundScanTime = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getScanTimePath())).getDouble(0);
		// Apparently it is not necessary to deduce this as it cancels out, however, it is left here in case
		//double backgroundThickness = (ProcessingUtils.getDataset(this, backgroundFilePath, model.getThicknessPath())).getDouble(0);

		// Now calculate the absorption coefficient
		backgroundITransmission *= iTransmissionCorrection;
		double backgroundLinearAbsorptionCoefficient = (-Math.log(backgroundITransmission / backgroundIZero)); // Ditto line 83/84 // backgroundThickness;
		
		// Then do the same for the sample file
		String sampleFilePath = getSliceSeriesMetadata(sampleDataset).getFilePath();
		double sampleThickness = (ProcessingUtils.getDataset(this, sampleFilePath, model.getThicknessPath())).getDouble(0);
		double sampleIZero = (ProcessingUtils.getDataset(this, sampleFilePath, model.getIZeroPath())).getDouble(0);
		double sampleITransmission = (ProcessingUtils.getDataset(this, sampleFilePath, model.getITransmissionPath())).getDouble(0);
		double sampleScanTime = (ProcessingUtils.getDataset(this, sampleFilePath, model.getScanTimePath())).getDouble(0);

		// Now calculate the absorption coefficient
		sampleITransmission *= iTransmissionCorrection;
		double sampleLinearAbsorptionCoefficient = (-Math.log(sampleITransmission / sampleIZero)) / sampleThickness;

		// The background scan time should match the sample, we're assuming that the scaling is LINEAR here.
		double backgroundIntensityCorrector = sampleScanTime / backgroundScanTime;
		// Not needed but left in case of need to debug
		// double beamlineBackgroundIntensityCorrector = sampleScanTime / beamlineBackgroundScanTime;

		// Get the background dataset from the disk
		IDataset backgroundDataset = ProcessingUtils.getDataset(this, backgroundFilePath, model.getDetectorDataPath());
		// Not needed but left in case of need to debug
		// IDataset beamlineBackgroundDataset = ProcessingUtils.getDataset(this, beamlineBackgroundFilePath, model.getDetectorDataPath());
		
		// The equation we're going to solve takes the form:
		// P_2 = (1/D_2) * ( (I_s) / (I_0 e^(-(2 * a_1 * D_1 + a_2 * D_2))) - ((I_b) / (I_0 * e^(-2 * a_1 * D_1)))) 
		
		// Calculate any known factors going in...
		double equationPrefactor = 1 / sampleThickness;
		double scatteredFactor = Math.exp(-sampleLinearAbsorptionCoefficient);
		double backgroundFactor = Math.exp(-backgroundLinearAbsorptionCoefficient);

		// Find the size for the loopIters		
		int[] detectorShape = sampleDataset.getShape();
		int detectorShapeLength = detectorShape.length;
		int detectorIndexX = detectorShapeLength - 2;
		int detectorIndexY = detectorShapeLength - 1;
		
		// Find a detector frame on the backgroundDataset
		int[] backgroundShape = backgroundDataset.getShape();
		int backgroundShapeLength = backgroundShape.length;
		int[] backgroundDetectorIndicies = new int[backgroundShapeLength];
				
		// Create a home for the subtracted data
		DoubleDataset resultDataset = DatasetFactory.zeros(detectorShape);
		
		for (int loopIterOne = 0; loopIterOne < detectorShape[detectorIndexX]; loopIterOne ++) {
			for (int loopIterTwo = 0; loopIterTwo < detectorShape[detectorIndexY]; loopIterTwo ++) {
				// Set up the backgroundDetector indices for later
				backgroundDetectorIndicies[backgroundShapeLength - 2] = loopIterOne;
				backgroundDetectorIndicies[backgroundShapeLength - 1] = loopIterTwo;
				
				// Perform the mathematics one fraction at a time
				double firstFraction = sampleDataset.getDouble(loopIterOne, loopIterTwo) / (sampleIZero * Math.exp(-scatteredFactor));
				double secondFraction = (backgroundDataset.getDouble(backgroundDetectorIndicies) * backgroundIntensityCorrector) / (backgroundIZero * Math.exp(-backgroundFactor));
				double sampleScatterProbability = equationPrefactor * (firstFraction - secondFraction);
				
				// Normalise the the background I0 value
				sampleScatterProbability *= beamlineBackgroundIZero;
				
				// Then place the final result into the result dataset
				resultDataset.set(sampleScatterProbability, loopIterOne, loopIterTwo);
			}
		}
		
		// Finally, we can create the operation data object that will hold this
		OperationData toReturn = new OperationData();

		// Fill it
		toReturn.setData(sampleDataset);
		toReturn.setData(resultDataset);
		
		// And then return it		
		return toReturn;	
	}
}