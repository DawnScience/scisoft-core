/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation2D.BicubicInterpolationOutput;


@SuppressWarnings("unused")
public class Interpolation2DTest {

	private static Dataset x, y, xy;
	private static Dataset testX = DatasetFactory.createFromObject(Dataset.FLOAT64, new double[]{1.33, 2.65, 55.05, 90.09});
	private static Dataset testY = DatasetFactory.createFromObject(Dataset.FLOAT64, new double[]{-36.789, 43.0652, 0.00006, 18.34});
	private static Dataset testXY1D = testFunction1D(testX, testY);
	private static Dataset testXY2D = testFunction2D(testX, testY);
	
	@BeforeClass
	public static void initTest() {
		x = DatasetFactory.createRange(0.0, 100.0, 2.0, Dataset.FLOAT64);
		y = DatasetFactory.createRange(-50.0, 50.0, 1.0, Dataset.FLOAT64);
		xy = DatasetFactory.zeros(new int[]{x.getSize(),  y.getSize()}, Dataset.FLOAT64);
		for (int i = 0 ; i < x.getSize() ; i++) {
			for (int j = 0 ; j < y.getSize() ; j++) {
				double value = testFunction(x.getDouble(i), y.getDouble(j));
				xy.set(value, i, j);
			}
		}
		//System.out.println("testXY: " + Arrays.toString((((DoubleDataset)testXY).getData())));
	}
	
	@Test
	public void testBicubicInterpolation1D() {
		Dataset test_results = Interpolation2D.bicubicInterpolation(x, y, xy, testX, testY, BicubicInterpolationOutput.ONED);
		//System.out.println("test_results: " + Arrays.toString((((DoubleDataset)test_results).getData())));
		TestUtils.assertDatasetEquals(testXY1D, test_results, 0.01, 0.0);
	}

	@Test
	public void testPiecewiseBicubicSplineInterpolation1D() {
		Dataset test_results = Interpolation2D.piecewiseBicubicSplineInterpolation(x, y, xy, testX, testY, BicubicInterpolationOutput.ONED);
		//System.out.println("test_results: " + Arrays.toString((((DoubleDataset)test_results).getData())));
		TestUtils.assertDatasetEquals(testXY1D, test_results, 0.02, 0.0);
	}

	@Test
	public void testBicubicInterpolation2D() {
		Dataset test_results = Interpolation2D.bicubicInterpolation(x, y, xy, testX, testY, BicubicInterpolationOutput.TWOD);
		//System.out.println("test_results: " + Arrays.toString((((DoubleDataset)test_results).getData())));
		assertArrayEquals(new int[]{testX.getSize(),  testY.getSize()}, test_results.getShape());
		TestUtils.assertDatasetEquals(testXY2D, test_results, 0.03, 0.0);
	}

	@Test
	public void testPiecewiseBicubicSplineInterpolation2D() {
		Dataset test_results = Interpolation2D.piecewiseBicubicSplineInterpolation(x, y, xy, testX, testY, BicubicInterpolationOutput.TWOD);
		//System.out.println("test_results: " + Arrays.toString((((DoubleDataset)test_results).getData())));
		assertArrayEquals(new int[]{testX.getSize(),  testY.getSize()}, test_results.getShape());
		TestUtils.assertDatasetEquals(testXY2D, test_results, 0.02, 0.0);
	}

	public static double testFunction(double x, double y) {
		return x*x + 3*x*y - y*y; 
	}
	
	public static Dataset testFunction1D(Dataset datax, Dataset datay) {
		// x*x + 3*x*y - y*y;
		return Maths.multiply(datax, datax).iadd(Maths.multiply(datax, datay).imultiply(3.0)).isubtract(Maths.multiply(datay, datay)); 
	}
	
	public static Dataset testFunction2D(Dataset datax, Dataset datay) {
		// x*x + 3*x*y - y*y;
		Dataset rv = DatasetFactory.zeros(new int[]{datax.getSize(), datay.getSize()}, Dataset.FLOAT64);
	
		for (int i = 0 ; i < datax.getSize() ; i++) {
			for (int j = 0 ; j < datay.getSize() ; j++) {
				rv.set(testFunction(datax.getDouble(i), datay.getDouble(j)), i, j); 
			}
		}
		
		return rv;
	}
}
