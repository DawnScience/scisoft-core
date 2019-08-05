/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.utils;

import org.eclipse.january.DatasetException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.Test;

//Now the testing class
public class ReflMergeUtilsTest {
	
	@Test
	public void testCorrectAttenuation() throws DatasetException {
		Dataset y1 = Maths.multiply(DatasetFactory.ones(10), 10);
		Dataset yE1 = DatasetFactory.ones(10);
		Dataset y2 = Maths.multiply(DatasetFactory.ones(10), 5);
		Dataset yE2 = Maths.multiply(DatasetFactory.ones(10), 0.5);
		
		y1.setErrors(yE1);
		y2.setErrors(yE2);
		
		Dataset x1 = DatasetFactory.createRange(DoubleDataset.class, 1, 11, 1);
		Dataset x2 = DatasetFactory.createRange(DoubleDataset.class, 8, 18, 1);
		
		y1 = ReflMergeUtils.setXAxis(y1, x1);
		y2 = ReflMergeUtils.setXAxis(y2, x2);
		
		Dataset[] inputData = new Dataset[2];
		inputData[0] = y1;
		inputData[1] = y2;
		
		Dataset expectedY1 = Maths.multiply(DatasetFactory.ones(9), 10);
		Dataset expectedYE1 = DatasetFactory.ones(9);
		Dataset expectedY2 = Maths.multiply(DatasetFactory.ones(9), 10);
		Dataset expectedYE2 = DatasetFactory.ones(9);
		
		expectedY1.setErrors(expectedYE1);
		expectedY2.setErrors(expectedYE2);
		
		Dataset expectedX1 = DatasetFactory.createRange(DoubleDataset.class, 2, 11, 1);
		Dataset expectedX2 = DatasetFactory.createRange(DoubleDataset.class, 8, 17, 1);
		
		expectedY1 = ReflMergeUtils.setXAxis(expectedY1, expectedX1);
		expectedY2 = ReflMergeUtils.setXAxis(expectedY2, expectedX2);
		
		Dataset[] outputData = ReflMergeUtils.correctAttenuation(inputData);
		
		TestUtils.assertDatasetEquals(expectedY1, outputData[0]);
		TestUtils.assertDatasetEquals(expectedY1.getErrors(), outputData[0].getErrors());
		TestUtils.assertDatasetEquals(expectedY2, outputData[1]);
		TestUtils.assertDatasetEquals(expectedY2.getErrors(), outputData[1].getErrors());
	}
	
	@Test
	public void testCorrectAttenuationSmallOverlap() throws DatasetException {
		Dataset y1 = Maths.multiply(DatasetFactory.ones(10), 10);
		Dataset yE1 = DatasetFactory.ones(10);
		Dataset y2 = Maths.multiply(DatasetFactory.ones(10), 5);
		Dataset yE2 = Maths.multiply(DatasetFactory.ones(10), 0.5);
		
		y1.setErrors(yE1);
		y2.setErrors(yE2);
		
		Dataset x1 = DatasetFactory.createRange(DoubleDataset.class, 1, 11, 1);
		Dataset x2 = DatasetFactory.createRange(DoubleDataset.class, 9, 19, 1);
		
		y1 = ReflMergeUtils.setXAxis(y1, x1);
		y2 = ReflMergeUtils.setXAxis(y2, x2);
		
		Dataset[] inputData = new Dataset[2];
		inputData[0] = y1;
		inputData[1] = y2;
		
		Dataset expectedY1 = Maths.multiply(DatasetFactory.ones(9), 10);
		Dataset expectedYE1 = DatasetFactory.ones(9);
		Dataset expectedY2 = Maths.multiply(DatasetFactory.ones(9), 10);
		Dataset expectedYE2 = DatasetFactory.ones(9);
		
		expectedY1.setErrors(expectedYE1);
		expectedY2.setErrors(expectedYE2);
		
		Dataset expectedX1 = DatasetFactory.createRange(DoubleDataset.class, 2, 11, 1);
		Dataset expectedX2 = DatasetFactory.createRange(DoubleDataset.class, 9, 18, 1);
		
		expectedY1 = ReflMergeUtils.setXAxis(expectedY1, expectedX1);
		expectedY2 = ReflMergeUtils.setXAxis(expectedY2, expectedX2);
		
		Dataset[] outputData = ReflMergeUtils.correctAttenuation(inputData);
		
		TestUtils.assertDatasetEquals(expectedY1, outputData[0]);
		TestUtils.assertDatasetEquals(expectedY1.getErrors(), outputData[0].getErrors());
		TestUtils.assertDatasetEquals(expectedY2, outputData[1]);
		TestUtils.assertDatasetEquals(expectedY2.getErrors(), outputData[1].getErrors());
	}
	
	@Test 
	public void testConcatenate() throws DatasetException {
		Dataset y1 = Maths.multiply(DatasetFactory.ones(4), 10);
		Dataset yE1 = DatasetFactory.ones(4);
		Dataset y2 = Maths.multiply(DatasetFactory.ones(4), 5);
		Dataset yE2 = Maths.multiply(DatasetFactory.ones(4), 0.5);
		y1.setErrors(yE1);
		y2.setErrors(yE2);
		
		Dataset x1 = DatasetFactory.createRange(DoubleDataset.class, 1, 5, 1);
		Dataset x2 = DatasetFactory.createRange(DoubleDataset.class, 4, 8, 1);
		Dataset xE1 = Maths.multiply(DatasetFactory.ones(4), 0.1);
		Dataset xE2 = Maths.multiply(DatasetFactory.ones(4), 0.1);
		x1.setErrors(xE1);
		x2.setErrors(xE2);
		
		y1 = ReflMergeUtils.setXAxis(y1, x1);
		y2 = ReflMergeUtils.setXAxis(y2, x2);
		
		Dataset[] inputData = new Dataset[2];
		inputData[0] = y1;
		inputData[1] = y2;
		
		double[] expectedX = {1., 2., 3., 4., 4., 5., 6., 7.};
		double[] expectedY = {10., 10., 10., 10., 5., 5., 5., 5.};
		double[] expectedYE = {1., 1., 1., 1., 0.5, 0.5, 0.5, 0.5};
		Dataset expectedXDataset = DatasetFactory.createFromObject(expectedX);
		Dataset expectedYDataset = DatasetFactory.createFromObject(expectedY);
		Dataset expectedYEDataset = DatasetFactory.createFromObject(expectedYE);
		Dataset expectedXEDataset = Maths.multiply(DatasetFactory.ones(8), 0.1);
		
		expectedXDataset.setErrors(expectedXEDataset);
		expectedYDataset = ReflMergeUtils.setXAxis(expectedYDataset, expectedXDataset);
		expectedYDataset.setErrors(expectedYEDataset);
		
		Dataset outputData = ReflMergeUtils.concatenate(inputData);
			
		TestUtils.assertDatasetEquals(expectedYDataset, outputData);
		TestUtils.assertDatasetEquals(expectedYDataset.getErrors(), outputData.getErrors());
		Dataset outputX = ReflMergeUtils.getXAxis(outputData);
		TestUtils.assertDatasetEquals(expectedXDataset.getErrors(), outputX.getErrors());
	}
	
	// Test using a x^-4 power law
	@Test
	public void testNormaliseTIR() throws DatasetException {
		double[] y = {1e9, 1e9, 1e9, 3.16e8, 1.29e8, 6.25e7, 3.37e7, 2.00e7};
		double[] yE = {1e7, 1e7, 1e7, 3.16e6, 1.29e6, 6.25e5, 3.37e5, 2.00e5};
		Dataset xDataset = DatasetFactory.createRange(DoubleDataset.class, 1, 9, 1);
		Dataset yDataset = DatasetFactory.createFromObject(y);
		Dataset yEDataset = DatasetFactory.createFromObject(yE);
		
		yDataset.setErrors(yEDataset);		
		yDataset = ReflMergeUtils.setXAxis(yDataset, xDataset);
		
		Dataset expectedY = ErrorPropagationUtils.divideWithUncertainty(yDataset, DatasetFactory.createFromObject(1e9));
		
		expectedY = ReflMergeUtils.setXAxis(expectedY, xDataset);
		
		Dataset outputData = ReflMergeUtils.normaliseTER(yDataset);
		
		TestUtils.assertDatasetEquals(expectedY, outputData);
		TestUtils.assertDatasetEquals(expectedY.getErrors(), outputData.getErrors());
	}
	
	@Test
	public void testRebinDatapoints() throws DatasetException {
		Dataset y = DatasetFactory.createRange(DoubleDataset.class, 1, 9, 1);
		Dataset yE = Maths.multiply(y, 0.1);
		y.setErrors(yE);
		double[] x = {1., 2., 3., 4., 4., 5., 6., 7.};
		Dataset xDataset = DatasetFactory.createFromObject(x);
		y = ReflMergeUtils.setXAxis(y, xDataset);
		
		double[] expectedY = {1., 2., 3., 4.5, 6., 7., 8.};
		Dataset expectedYDataset = DatasetFactory.createFromObject(expectedY);

		double[] expectedX = {1., 2., 3., 4., 5., 6., 7.};
		Dataset expectedXDataset = DatasetFactory.createFromObject(expectedX);
		expectedYDataset = ReflMergeUtils.setXAxis(expectedYDataset, expectedXDataset);
		
		Dataset outputData = ReflMergeUtils.rebinDatapoints(y);
		
		TestUtils.assertDatasetEquals(expectedYDataset, outputData);
	}
}