/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operations;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.Maths;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.SumOperation;

public class SumOperationTest {
	@Test 
	public void testNoErrors() {
		SumOperation sumOperation = setup();
		
		Dataset fiveOnes = DatasetFactory.ones(5);
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		fiveOnes.addMetadata(sliceSeriesMetadata);
		
		IMonitor monitor = new IMonitor.Stub();
		OperationData returnedResult = sumOperation.execute(fiveOnes, monitor);
		Dataset expectedResult = Maths.multiply(DatasetFactory.ones(), 5.0);
		
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(returnedResult.getData()), fiveOnes);
		assertEquals(returnedResult.getData().getErrors(), null);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]), expectedResult);
		assertEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]).getErrors(), null);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]), expectedResult);
		assertEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]).getErrors(), null);
	}
	
	@Test 
	public void testNoErrorsSqrt() {
		SumOperation sumOperation = setup();
		
		Dataset fiveTwentyFives = Maths.multiply(DatasetFactory.ones(5), 25);
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		fiveTwentyFives.addMetadata(sliceSeriesMetadata);
		
		IMonitor monitor = new IMonitor.Stub();
		OperationData returnedResult = sumOperation.execute(fiveTwentyFives, monitor);
		Dataset expectedResult = Maths.multiply(DatasetFactory.ones(), 125.0);
		Dataset expectedSumOfSqrtResult = Maths.multiply(DatasetFactory.ones(), 25.0);
		
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(returnedResult.getData()), fiveTwentyFives);
		assertEquals(returnedResult.getData().getErrors(), null);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]), expectedResult);
		assertEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]).getErrors(), null);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]), expectedSumOfSqrtResult);
		assertEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]).getErrors(), null);
	}
	
	@Test 
	public void testErrors() {
		SumOperation sumOperation = setup();
		
		Dataset fiveOnes = DatasetFactory.ones(5);
		Dataset errors = Maths.multiply(DatasetFactory.ones(5), 0.1);
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		fiveOnes.addMetadata(sliceSeriesMetadata);
		fiveOnes.setErrors(errors);
		
		IMonitor monitor = new IMonitor.Stub();
		OperationData returnedResult = sumOperation.execute(fiveOnes, monitor);
		Dataset expectedResult = Maths.multiply(DatasetFactory.ones(), 5.0);
		Dataset expectedErrors = Maths.multiply(DatasetFactory.ones(), 0.223606797749979);
		Dataset expectedErrorsSumOfSqrt = Maths.multiply(DatasetFactory.ones(), 0.7071067811865476);
		
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(returnedResult.getData()), fiveOnes);
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(returnedResult.getData().getErrors()), errors);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]), expectedResult);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]).getErrors(), expectedErrors);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]), expectedResult);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]).getErrors(), expectedErrorsSumOfSqrt);
	}
	
	@Test 
	public void testErrorsSqrt() {
		SumOperation sumOperation = setup();
		
		Dataset fiveTwentyFives = Maths.multiply(DatasetFactory.ones(5), 25);
		Dataset errors = Maths.multiply(DatasetFactory.ones(5), 2.5);
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		fiveTwentyFives.addMetadata(sliceSeriesMetadata);
		fiveTwentyFives.setErrors(errors);
		
		IMonitor monitor = new IMonitor.Stub();
		OperationData returnedResult = sumOperation.execute(fiveTwentyFives, monitor);
		Dataset expectedResult = Maths.multiply(DatasetFactory.ones(), 125.0);
		Dataset expectedErrors = Maths.multiply(DatasetFactory.ones(), 5.5901699437494745);
		Dataset expectedSumOfSqrtResult = Maths.multiply(DatasetFactory.ones(), 25.0);
		Dataset expectedErrorsSumOfSqrt = Maths.multiply(DatasetFactory.ones(), 3.5355339059327378);
		
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(returnedResult.getData()), fiveTwentyFives);
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(returnedResult.getData().getErrors()), errors);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]), expectedResult);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[0]).getErrors(), expectedErrors);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]), expectedSumOfSqrtResult);
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(returnedResult.getAuxData()[1]).getErrors(), expectedErrorsSumOfSqrt);
	}
	
	private SumOperation setup() {
		SumOperation sumOperation = new SumOperation();
		EmptyModel emptyModel = new EmptyModel();
		sumOperation.setModel(emptyModel);
		return sumOperation;
	}
}