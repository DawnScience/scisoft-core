/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


import java.text.MessageFormat;

import javax.measure.IncommensurableException;
import javax.measure.UnconvertibleException;
import javax.measure.UnitConverter;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.unit.UnitUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.UnitMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.uom.NonSI;
// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;


// @author Tim Snow


public class QErrorsOperation extends AbstractOperation<QErrorsModel, OperationData>{
	
	
	// First, set up a logger and some constants
	private static final Logger logger = LoggerFactory.getLogger(QErrorsOperation.class);
	public static final String NEXUS_ENERGY_ERROR_PATH = "/instrument/monochromator/energy_error";
	public static final String NEXUS_SAMPLE_THICKNESS_PATH = "/sample/thickness";
	public static final double HC = 12398.4193;
	protected IDiffractionMetadata metadata;

	
	// Then let's declare our process ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.QErrorsOperation";
	}
	
	
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
	
	
	// Now let's define the main process
	@Override
	public OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		// First a few things need to be declared
		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		String currentNexusEntry = "/" + ssm.getDatasetName().split("/")[1];
		String filePath = ssm.getFilePath();
		Tree nexusTree;
		
		try {
			nexusTree = LoaderFactory.getData(filePath).getTree();
		} catch (Exception treeException) {
			throw new OperationException(this, "No NeXus tree found!\n" + treeException);
		}
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(input);
		if (md == null) throw new OperationException(this, "No detector geometry information!");
		// N.B. getDetectorDistance() returns a value in mm
		double sampleToDetectorDistance = md.getOriginalDetector2DProperties().getDetectorDistance();
		double beamWavelengthInA = md.getDiffractionCrystalEnvironment().getWavelength();

		// Let's set up some variables!
		Dataset inputDataset = DatasetUtils.convertToDataset(input);
		UnitConverter axisConverter = null;
		AxesMetadata xAxisMetadata = null;
		UnitMetadata unitMetadata = null;
		Dataset workingAxis = null;
		Dataset inputAxis = null;
		
		// Now let's get the primary axis, which will also need a judicious trimming...
		try {
			xAxisMetadata = input.getFirstMetadata(AxesMetadata.class);
			inputAxis = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
			unitMetadata = inputAxis.getFirstMetadata(UnitMetadata.class);
			workingAxis = inputAxis.clone();
		} catch (DatasetException metadataError) {
			throw new OperationException(this, "Appropriate metdata not found!\n" + metadataError);
		}
		
		if (!unitMetadata.getUnit().equals(NonSI.ANGSTROM.inverse())) {
			try {
				axisConverter = unitMetadata.getUnit().getConverterToAny(NonSI.ANGSTROM.inverse());
			} catch (UnconvertibleException | IncommensurableException e) {
				String error = MessageFormat.format("Could not convert axis's ({}) unit to Angstrom", unitMetadata.getUnit());
				logger.error(error, e);
				throw new OperationException(this, error);
			}
			
			for (int index = 0; index < inputAxis.getSize(); index ++) {
				double convertedAxisValue = axisConverter.convert(inputAxis.getDouble(index));
				workingAxis.set(convertedAxisValue, index);
			}
		}
		
		// Home for the overall errors found in this plugin
		Dataset overallErrors = DatasetFactory.zeros(DoubleDataset.class, inputAxis.getSize());
		
		if (model.getCalculateDataBinningErrors()) {
			overallErrors = Maths.add(overallErrors, CalculateBinningErrorsSquared(workingAxis));
		}
		
		if (model.getCalculatePolychromaticityErrors()) {
			String nexusEnergyErrorPath = currentNexusEntry + NEXUS_ENERGY_ERROR_PATH;
			double beamEnergyErrorInEv = NexusTreeUtils.getDataset(nexusEnergyErrorPath, nexusTree, NonSI.ELECTRON_VOLT).getDouble();
			double beamWavelengthErrorInA = beamEnergyErrorInEv / HC;
			
			if (beamEnergyErrorInEv == 0.00 || Double.isNaN(beamEnergyErrorInEv)) throw new OperationException(this, "No beam energy error information!");
			overallErrors = Maths.add(overallErrors, CalculatePolychromaticityErrorsSquared(workingAxis, beamWavelengthInA, beamWavelengthErrorInA));
		}
		
		if (model.getCalculateSampleThicknessErrors()) {
			String nexusSampleThicknessPath = currentNexusEntry + NEXUS_SAMPLE_THICKNESS_PATH;
			double sampleThickness = NexusTreeUtils.getDataset(nexusSampleThicknessPath, nexusTree, UnitUtils.MILLIMETRE).getDouble();
			
			if (sampleThickness == 0.00 || Double.isNaN(sampleThickness)) throw new OperationException(this, "No sample thickness provided!");
			overallErrors = Maths.add(overallErrors, CalculateSampleThicknessErrorsSquared(workingAxis, sampleToDetectorDistance, beamWavelengthInA, sampleThickness));
		}
		
		// Square root all the errors for our working axis units
		Dataset axisErrors = Maths.sqrt(overallErrors);
		
		// Cast the errors if required
		if (axisConverter != null) {
			for (int index = 0; index < axisErrors.getSize(); index ++) {
				double convertedErrorValue = axisConverter.convert(axisErrors.getDouble(index));
				axisErrors.set(convertedErrorValue, index);
			}
		}
		
		inputAxis.setErrors(axisErrors);
		AxesMetadata outputAxisMetadata;
		
		try {
			outputAxisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			outputAxisMetadata.setAxis(0, inputAxis);
			inputDataset.setMetadata(outputAxisMetadata);
		} catch (MetadataException e1) {
			logger.error("Could not set axis metadata", e1);
		}
		
		return new OperationData(inputDataset);
	}
	
	
	private Dataset CalculateBinningErrorsSquared(Dataset inputAxis) {
		Dataset outputErrorDataset = DatasetFactory.zeros(DoubleDataset.class, inputAxis.getSize());
		
		for (int index = 1; index < inputAxis.getSize(); index ++) {
			double indexAxisValue = inputAxis.getDouble(index);
			double difference = indexAxisValue - inputAxis.getDouble(index - 1);
			double errorValue = (difference / indexAxisValue) * difference;
			
			double errorValueSquared = errorValue * errorValue;
			outputErrorDataset.set(errorValueSquared, index);
		}
		
		outputErrorDataset.set(outputErrorDataset.getObject(1), 0);
		return outputErrorDataset;
	}
	
	
	private Dataset CalculatePolychromaticityErrorsSquared(Dataset inputAxis, double beamWavelengthInA, double beamWavelengthErrorInA) {
		Dataset outputErrorDataset = DatasetFactory.zeros(DoubleDataset.class, inputAxis.getSize());
		double preFactor = ((4 * Math.PI) / (beamWavelengthInA - beamWavelengthErrorInA)) - ((4 * Math.PI) / (beamWavelengthInA + beamWavelengthErrorInA));
		double thetaFactor = beamWavelengthInA / (4 * Math.PI);
		
		for (int index = 0; index < inputAxis.getSize(); index ++) {
			double indexAxisValue = inputAxis.getDouble(index);
			double theta = Math.asin(indexAxisValue * thetaFactor);
			double errorValue = preFactor * Math.sin(theta);
			
			double errorValueSquared = errorValue * errorValue;
			outputErrorDataset.set(errorValueSquared, index);
		}
		
		return outputErrorDataset;
	}
	
	
	private Dataset CalculateSampleThicknessErrorsSquared(Dataset inputAxis, double sampleToDetectorDistance, double beamWavelengthInA, double sampleThickness) {
		Dataset outputErrorDataset = DatasetFactory.zeros(DoubleDataset.class, inputAxis.getSize());
		double thetaFactor = beamWavelengthInA / (4 * Math.PI);
		double qFactor = 1.00 / thetaFactor;
		double halfSampleThickness = sampleThickness / 2;
		
		for (int index = 0; index < inputAxis.getSize(); index ++) {
			double indexAxisValue = inputAxis.getDouble(index);
			double theta = Math.asin(indexAxisValue * thetaFactor);
			
			double scatterDistance = sampleToDetectorDistance * Math.tan(2 * theta);
			double thetaOne = 0.5 * Math.atan(scatterDistance / (sampleToDetectorDistance - halfSampleThickness));
			double thetaTwo = 0.5 * Math.atan(scatterDistance / (sampleToDetectorDistance + halfSampleThickness));
			
			double deltaQ = qFactor * (Math.sin(thetaOne) - Math.sin(thetaTwo));
			double errorValue = (deltaQ / indexAxisValue) * deltaQ;
			
			double errorValueSquared = errorValue * errorValue;
			outputErrorDataset.set(errorValueSquared, index);
		}
		
		return outputErrorDataset;
	}
}