/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Random;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegisterData1DTest {
	private static final int MAX = 6;
	private static final double X_DELTA = 2.4;
	private static final int X_SIZE = 366;
	
	private static final int START = 3; // start divider
	private static final int MARGIN = 10; // margin for ROI
	private static final double OBJECT_X = 42.3;

	@BeforeClass
	public static void setUp() {
		Random.seed(123571L);
	}

	@Test
	public void testRegisterSynthetic() {
		testRegisterSynthetic(0.17, 0);
		testRegisterSynthetic(163, 0.05); // 10% noise
		testRegisterSynthetic(151, 0.1); // 20% noise
	}

	@Test
	public void testRegisterSyntheticWithROI() {
		testRegisterSyntheticWithROI(0.06, 0);
		testRegisterSyntheticWithROI(72, 0.05); // 10% noise
		testRegisterSyntheticWithROI(72, 0.1); // 20% noise
	}

	void testRegisterSynthetic(double delta, double noise) {
		testRegisterSynthetic(null, delta, noise);
	}

	void testRegisterSyntheticWithROI(double delta, double noise) {
		double[] start = new double[] {X_SIZE/START - OBJECT_X - MARGIN, 0};
		double[] end = start.clone();
		end[0] += 2*OBJECT_X + MAX*X_DELTA + MARGIN;
		end[1] = 1; // to get around auto-adjustment of start and angle
		RectangularROI roi = new RectangularROI(start, end);
		System.err.println(roi);
		testRegisterSynthetic(roi, delta, noise);
	}

	DatasetToDatasetFunction createFunction(IDataset[] data, IRectangularROI roi) {
		RegisterData1D reg = new RegisterData1D();
		DoubleDataset filter = DatasetFactory.ones(DoubleDataset.class, 5);
		IDataset anchor = data[0];
		filter.imultiply(1./ ((Number) filter.sum()).doubleValue());
		reg.setFilter(filter);
		reg.setReference(anchor);
		reg.setRectangle(roi);
		return reg;
	}

	void testRegisterSynthetic(IRectangularROI roi, double delta, double noise) {
		double[] shifts = generateShifts(MAX);
		System.err.println(Arrays.toString(shifts));
		IDataset[] data = createTestData(shifts, X_SIZE, noise);

		DatasetToDatasetFunction function = createFunction(data, roi);
		List<? extends IDataset> coords = function.value(data);
		assertNotNull(coords);
		for (int i = 0; i < MAX; i++) {
			double v = coords.get(2*i).getDouble();
			System.err.println(i + " -> " + v + " cf " + shifts[i]);
		}
		for (int i = 0; i < MAX; i++) {
			double v = coords.get(2*i).getDouble();
			Assert.assertEquals(shifts[i], v, delta);
		}
	}

	public double[] generateShifts(int number) {
		double[] shifts = new double[number];
		for (int n = 0; n < number; n++) {
			shifts[n] = n*X_DELTA;
		}
		return shifts;
	}

	public IDataset[] createTestData(double[] shifts, int width, double noise) {
		int number = shifts.length;
		IDataset[] data = new IDataset[number];
		for (int n = 0; n < number; n++) {
			double start = width/START + shifts[n];
			data[n] = createData(width, start, noise);
		}
		return data;
	}

	/**
	 * Create height field
	 * @param width
	 * @param start
	 * @return image
	 */
	public IDataset createData(int width, double start, double noise) {
		DoubleDataset data = DatasetFactory.zeros(width);
		for (int i = 0; i < width; i++) {
			double y = i - start;
			double z = getZ(y);
			if (z != 0) {
				data.setItem(z, i);
			}
		}
		if (noise != 0) {
			DoubleDataset ndata = Random.randn(width).imultiply(noise);
			data.iadd(ndata);
		}
		return data;
	}

	/**
	 * Truncated triangle
	 * @param x
	 * @return z
	 */
	private double getZ(double x) {
		final double zmax = 0.5;
		double ax = Math.abs(x);
		if (ax > OBJECT_X) {
			return 0;
		}
		double z = 1 - ax/OBJECT_X;
		if (z > zmax) {
			z = zmax;
		}
		return z;
	}
}
