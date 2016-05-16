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

import org.eclipse.dawnsci.analysis.asserts.TestUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.junit.BeforeClass;
import org.junit.Test;


@SuppressWarnings("unused")
public class Interpolation2DTest {

	private static Dataset x, y, xy;
	private static Dataset testX = DatasetFactory.createFromObject(new double[]{1.33, 2.65, 55.05, 90.09}, Dataset.FLOAT64);
	private static Dataset testY = DatasetFactory.createFromObject(new double[]{-36.789, 43.0652, 0.00006, 18.34}, Dataset.FLOAT64);
	private static Dataset testXY = testFunction(testX, testY);
	
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
	public void testBicubicInterpolation() {
		Dataset test_results = Interpolation2D.bicubicInterpolation(x, y, xy, testX, testY);
		//System.out.println("test_results: " + Arrays.toString((((DoubleDataset)test_results).getData())));
		TestUtils.assertDatasetEquals(testXY, test_results, 0.01, 0.0);
	}

	@Test
	public void testPiecewiseBicubicSplineInterpolation() {
		Dataset test_results = Interpolation2D.piecewiseBicubicSplineInterpolation(x, y, xy, testX, testY);
		//System.out.println("test_results: " + Arrays.toString((((DoubleDataset)test_results).getData())));
		TestUtils.assertDatasetEquals(testXY, test_results, 0.02, 0.0);
	}

	public static double testFunction(double x, double y) {
		return x*x + 3*x*y - y*y; 
	}
	
	public static Dataset testFunction(Dataset datax, Dataset datay) {
		// x*x + 3*x*y - y*y;
		return Maths.multiply(datax, datax).iadd(Maths.multiply(datax, datay).imultiply(3.0)).isubtract(Maths.multiply(datay, datay)); 
	}
}
