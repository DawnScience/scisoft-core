/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operations.reflectivityandsxrd;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.GeometricCorrectionModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.SpillOverCorrectionOperation;

public class SpillOverCorrectionOperationTest {
	
	// Numbers that will be used in the test
	static final double START = 0.;
	static final double STOP = 10.;
	static final int LENGTH = 10;
	static final double TOADD = 10.;	
	static final double TOADDUNCERTAINTY = 0.1;
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("Spill Over Correction Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.SpillOverCorrectionOperation", new SpillOverCorrectionOperation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.TWO, new SpillOverCorrectionOperation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.TWO, new SpillOverCorrectionOperation().getOutputRank());
	}
		
	// Test when the angle is below that required for spill over the data should not change
	@Test
	public void testExecuteA() throws MetadataException {
		// First a relevant operation object will be created
		SpillOverCorrectionOperation spillOverCorrectionOperation = new SpillOverCorrectionOperation();
		
		// Now some data needs to be made
		IDataset testData = DatasetFactory.ones(LENGTH, LENGTH);
		IDataset testErrors = DatasetFactory.ones(LENGTH, LENGTH);
		testData.setErrors(testErrors);
		
		Dataset sliceAxis = DatasetFactory.createFromObject(1);
		
		AxesMetadata ssmParentAxesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		
		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ILazyDataset ssmParent = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*2);
		ssmParent.setMetadata(ssmParentAxesMetadata);
		SourceInformation si = new SourceInformation(null, null, ssmParent);
		SliceFromSeriesMetadata ssm2 = new SliceFromSeriesMetadata(si);
		testData.setMetadata(ssm2);

		// Now we tell the ScalarUncertaintyModel what is happening, that we want to shift the x-axis by 10\pm0.1
		GeometricCorrectionModel spillOverCorrectionModel = new GeometricCorrectionModel();
		spillOverCorrectionModel.setSampleSize(100);
		spillOverCorrectionModel.setSlitHeight(0.1);
		spillOverCorrectionOperation.setModel(spillOverCorrectionModel);
		
		IMonitor monitor = new IMonitor.Stub();
		// And then run the method
		OperationData returnedResult = spillOverCorrectionOperation.execute(testData, monitor);
		
		TestUtils.assertDatasetEquals((Dataset) testData, (Dataset) returnedResult.getData());
	}
	
	// Test when the angle is below that required for spill over the data should not change
	@Test
	public void testExecuteB() throws MetadataException {
		// First a relevant operation object will be created
		SpillOverCorrectionOperation spillOverCorrectionOperation = new SpillOverCorrectionOperation();
		
		// Now some data needs to be made
		IDataset testData = DatasetFactory.ones(LENGTH, LENGTH);
		IDataset testErrors = DatasetFactory.ones(LENGTH, LENGTH);
		testData.setErrors(testErrors);
		
		Dataset sliceAxis = DatasetFactory.createFromObject(0.008);
		
		AxesMetadata ssmParentAxesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		
		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ILazyDataset ssmParent = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*2);
		ssmParent.setMetadata(ssmParentAxesMetadata);
		SourceInformation si = new SourceInformation("test", "test", ssmParent);
		SliceFromSeriesMetadata ssm2 = new SliceFromSeriesMetadata(si);
		testData.setMetadata(ssm2);

		// Now we tell the ScalarUncertaintyModel what is happening, that we want to shift the x-axis by 10\pm0.1
		GeometricCorrectionModel spillOverCorrectionModel = new GeometricCorrectionModel();
		spillOverCorrectionModel.setSampleSize(100);
		spillOverCorrectionModel.setSlitHeight(0.1);
		spillOverCorrectionOperation.setModel(spillOverCorrectionModel);
		
		IMonitor monitor = new IMonitor.Stub();
		// And then run the method
		OperationData returnedResult = spillOverCorrectionOperation.execute(testData, monitor);
		
		TestUtils.assertDatasetEquals((Dataset) Maths.multiply(testData, 7.1619736327979), (Dataset) returnedResult.getData());
	}
}
