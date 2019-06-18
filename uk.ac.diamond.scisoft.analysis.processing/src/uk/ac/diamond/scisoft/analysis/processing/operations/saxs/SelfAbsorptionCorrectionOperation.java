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
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;

// Imports from org.slf4j
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.SelfAbsorptionCorrectionModel.GeometryType;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;


// @author Tim Snow

//Implements the self-absorption equation given in https://arxiv.org/pdf/1306.0637.pdf (Equation 29)

@Atomic
public class SelfAbsorptionCorrectionOperation extends AbstractOperation<SelfAbsorptionCorrectionModel, OperationData>{


	// First let's declare our process ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.SelfAbsorptionCorrectionOperation";
	}


	// Then set up a logger
	private static final Logger logger = LoggerFactory.getLogger(SelfAbsorptionCorrectionOperation.class);


	// Now, how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}


	// Now for some local variables...
	protected volatile Dataset twoThetaArray;
	protected IDiffractionMetadata metadata;


	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// First let's get ahold of all the metadata that's available for remapping
		IDiffractionMetadata md = getFirstDiffractionMetadata(inputDataset);
		
		// First, is there any passed?
		if (md == null) throw new OperationException(this, "No detector geometry information!");
		
		// If there is, is it meaningful? 
		if (metadata == null) {
			metadata = md;
			twoThetaArray = null;
		} else {
			boolean dee = metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment());
			boolean dpe = metadata.getDetector2DProperties().equals(md.getDetector2DProperties());
			
			if (!dpe || !dee) {
				metadata = md;
				twoThetaArray = null;
			}
		}
		
		// Is there a twoThetaArray cache? If not we'll need some values...
		if (twoThetaArray == null) {
			twoThetaArray = PixelIntegrationUtils.generate2ThetaArrayRadians(this.metadata);
		}
		
		// First let's obtain all the necessary information for our calculations from the source NeXus file
		SliceFromSeriesMetadata sliceMetadata = getSliceSeriesMetadata(inputDataset);
		String filePath = sliceMetadata.getFilePath();
		
		Dataset transmissionFactor = null;
		
		switch (model.getCorrectionType()) {
			case INTENSITIES:	try {
									Dataset i0 = DatasetUtils.convertToDataset(sliceMetadata.getMatchingSlice(ProcessingUtils.getDataset(this, filePath, model.getI0Path())));
									Dataset iT = DatasetUtils.convertToDataset(sliceMetadata.getMatchingSlice(ProcessingUtils.getDataset(this, filePath, model.getItPath())));
									transmissionFactor = ErrorPropagationUtils.divideWithUncertainty(iT, i0);
								} catch (Exception e) {
										throw new OperationException(this, e);
								}
								
								break;
								
			case TRANSMISSION:	try {
									transmissionFactor = DatasetUtils.convertToDataset(sliceMetadata.getMatchingSlice(ProcessingUtils.getDataset(this, filePath, model.getTransmissionPath())));
								} catch (Exception e) {
										throw new OperationException(this, e);
								}
								break;
								
			default: logger.error("Performing analysis using the input format requested is not implemented");
		}
		
		// Get out input dataset in a useful format
		Dataset inputData = DatasetUtils.cast(DoubleDataset.class, DatasetUtils.convertToDataset(inputDataset));
		
		// Now we'll perform the requested operation to the data
		Dataset outputDataset = null;
		
		if (model.getGeometry() == GeometryType.PLATE) {
			Dataset correctionFactor = plateCorrection(inputData.getShape(), DatasetUtils.cast(DoubleDataset.class, transmissionFactor));
			outputDataset = ErrorPropagationUtils.multiplyWithUncertainty(inputData, correctionFactor);
		} else {
			logger.error("The geometry selected does not have the required mathematics implemented");
		}
		
		copyMetadata(inputDataset, outputDataset);
		
		// Or just return what we started with in case of error
		return new OperationData(outputDataset);
	}


	private Dataset plateCorrection(int[] datasetShape, Dataset transmissionFactor) {
		Dataset correctionFactor = DatasetFactory.zeros(datasetShape);
		IndexIterator loopIterator = correctionFactor.getIterator();
		double transFac = transmissionFactor.getDouble();
		
		while (loopIterator.hasNext()) {
			int index = loopIterator.index;
			double currentAngle = Math.abs(this.twoThetaArray.getElementDoubleAbs(index));
			
			if (currentAngle == 0.00 || currentAngle == 180.00) {
				correctionFactor.setObjectAbs(index, 1.00);
			} else if (currentAngle == 90.00 ) {
				correctionFactor.setObjectAbs(index, Double.POSITIVE_INFINITY);
			} else {
				double inverseCosTwoTheta = 1 / Math.cos(Math.toRadians(currentAngle));
				double fractionNumerator = 1 - Math.pow(transFac,  (inverseCosTwoTheta - 1));
				double fractionDenominator = Math.log(transFac) - inverseCosTwoTheta * Math.log(transFac);
				double correctionValue = fractionNumerator / fractionDenominator;
				correctionFactor.setObjectAbs(index, correctionValue);
			}
		}
		
		return correctionFactor;
	}
}