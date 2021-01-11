/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.test.operations.twod;


// Imports from org.junit
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;


// Imports from org.eclipse
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;


// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DetectorEfficiencyCalculationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.DetectorEfficiencyCalculationOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.EnterDiffractionCalibrationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.EnterDiffractionCalibrationOperation;


// @author Tim Snow


public class DetectorEfficiencyCalculationTest {
	
	
	@Test
	public void testProcess() throws DatasetException {
		IMonitor monitor = new IMonitor.Stub();
		Dataset testData = DatasetFactory.ones(10, 10);
		Dataset sliceAxis = DatasetFactory.createFromObject(1);
		AxesMetadata ssmParentAxesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ILazyDataset ssmParent = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 10, 10);
		ssmParent.setMetadata(ssmParentAxesMetadata);
		SourceInformation si = new SourceInformation("test", "test", ssmParent);
		SliceFromSeriesMetadata ssm2 = new SliceFromSeriesMetadata(si);
		testData.setMetadata(ssm2);
		
		DetectorEfficiencyCalculationModel processingOperationModel = new DetectorEfficiencyCalculationModel();
		DetectorEfficiencyCalculationOperation processingOperation = new DetectorEfficiencyCalculationOperation();

		processingOperationModel.setBeamEnergy(12.4);
		processingOperationModel.setManualEnergyValueUsed(true);
		processingOperationModel.setSensorComposition("Si");
		processingOperationModel.setSensorDensity(2.3290);
		processingOperationModel.setSensorThickness(0.01);
		processingOperation.setModel(processingOperationModel);

		EnterDiffractionCalibrationModel calibrationModel = new EnterDiffractionCalibrationModel();
		EnterDiffractionCalibrationOperation calibrationOperation = new EnterDiffractionCalibrationOperation();

		calibrationModel.setBeamCentreX(5.00);
		calibrationModel.setBeamCentreY(5.00);
		calibrationModel.setDetectorDistance(100.00);
		calibrationModel.setEnergy(12.4);
		calibrationModel.setPitch(0.00);
		calibrationModel.setPixelSize(1);
		calibrationModel.setRoll(0.00);
		calibrationModel.setYaw(0.00);
		calibrationOperation.setModel(calibrationModel);
		
		OperationData resultData = calibrationOperation.execute(testData, monitor);
		resultData = processingOperation.execute(resultData.getData(), monitor);
		Dataset resultDataset = (Dataset) resultData.getData();

		assertEquals(0.99837, resultDataset.getDouble(0, 0), 0.00001);
		assertEquals(0.99917, resultDataset.getDouble(5, 0), 0.00001);
		assertEquals(0.99837, resultDataset.getDouble(9, 9), 0.00001);
	}
}