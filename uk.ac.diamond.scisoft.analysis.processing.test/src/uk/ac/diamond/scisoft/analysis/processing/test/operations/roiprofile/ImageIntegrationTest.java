/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operations.roiprofile;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.SliceND;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.ImageIntegration;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.ImageIntegrationModel;

public class ImageIntegrationTest {
	
	// Numbers that will be used in the test
	static final int LENGTH = 10;
	static final Direction DIRECTIONX = Direction.X;	
	static final Direction DIRECTIONY = Direction.Y;	
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("Image Integration ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.imageIntegration", new ImageIntegration().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.TWO, new ImageIntegration().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.ONE, new ImageIntegration().getOutputRank());
	}
		
	// Second test will actually probe the addition with uncertainties
	@Test
	public void testProcess() {
		// First a relevant operation object will be created
		ImageIntegration sumInDimensionOperation = new ImageIntegration();
	
		// Now some data needs to be made
		IDataset testData = DatasetFactory.ones(LENGTH, LENGTH);
		
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata(new SliceInformation(new SliceND(new int[]{10, 10}),
						new SliceND(new int[]{10, 10}),
						new SliceND(new int[]{10, 10}),
						new int[] {0, 1},  1, 0));
				
		IDataset testErrors = Maths.multiply(DatasetFactory.ones(LENGTH, LENGTH), 0.1);

		testData.setErrors(testErrors);
		testData.addMetadata(sliceSeriesMetadata);

	
		// Now we tell the SumInDimensionModel what is happening
		ImageIntegrationModel sumInDimensionModel = new ImageIntegrationModel();
		sumInDimensionModel.setDirection(DIRECTIONX);
		sumInDimensionOperation.setModel(sumInDimensionModel);
		
		// Now some result data needs to be created
		IDataset resultData = Maths.multiply(DatasetFactory.ones(LENGTH), LENGTH);
		IDataset resultErrors = Maths.multiply(DatasetFactory.ones(LENGTH), 1.0);
		resultData.setErrors(resultErrors);
		
		// We shall then create a monitor to satisfy the operation arguments
		IMonitor monitor = new IMonitor.Stub();
		// And then run the method
		OperationData returnedResult = sumInDimensionOperation.execute(testData, monitor);
		
		// Before checking the result
		TestUtils.assertDatasetEquals((Dataset) resultData, (Dataset) returnedResult.getData());
		TestUtils.assertDatasetEquals((Dataset) resultData.getErrors(), (Dataset) returnedResult.getData().getErrors());

		// Now we change the direction
		sumInDimensionModel.setDirection(DIRECTIONY);
		sumInDimensionOperation.setModel(sumInDimensionModel);
		
		OperationData returnedResult2 = sumInDimensionOperation.execute(testData, monitor);
		
		// Before checking the result
		TestUtils.assertDatasetEquals((Dataset) resultData, (Dataset) returnedResult2.getData());
		TestUtils.assertDatasetEquals((Dataset) resultData.getErrors(), (Dataset) returnedResult2.getData().getErrors());
		
		// New we can test the get Average functionality
		ImageIntegrationModel sumInDimensionModel2 = new ImageIntegrationModel();
		sumInDimensionModel2.setDirection(DIRECTIONX);
		sumInDimensionModel2.setDoAverage(true);
		sumInDimensionOperation.setModel(sumInDimensionModel2);
		
		// Now some result data needs to be created
		IDataset resultDataAv = Maths.multiply(DatasetFactory.ones(LENGTH), LENGTH/LENGTH);
		IDataset resultErrorsAv = Maths.multiply(DatasetFactory.ones(LENGTH), 0.1);
		resultDataAv.setErrors(resultErrorsAv);
		
		// And then run the method
		OperationData returnedResultAv = sumInDimensionOperation.execute(testData, monitor);
		
		// Before checking the result
		TestUtils.assertDatasetEquals((Dataset) resultDataAv, (Dataset) returnedResultAv.getData());
		TestUtils.assertDatasetEquals((Dataset) resultDataAv.getErrors(), (Dataset) returnedResultAv.getData().getErrors());

		// Now we change the direction
		sumInDimensionModel2.setDirection(DIRECTIONY);
		sumInDimensionModel2.setDoAverage(true);
		sumInDimensionOperation.setModel(sumInDimensionModel2);
		
		OperationData returnedResultAv2 = sumInDimensionOperation.execute(testData, monitor);
		
		// Before checking the result
		TestUtils.assertDatasetEquals((Dataset) resultDataAv, (Dataset) returnedResultAv2.getData());
		TestUtils.assertDatasetEquals((Dataset) resultDataAv.getErrors(), (Dataset) returnedResultAv2.getData().getErrors());
	}
}
