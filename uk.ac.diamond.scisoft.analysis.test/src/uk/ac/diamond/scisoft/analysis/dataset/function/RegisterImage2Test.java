/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.junit.Assert;
import org.junit.Test;

public class RegisterImage2Test {
	private static final int MAX = 6;
	private static final double X_DELTA = 2.4;
	private static final double Y_DELTA = 0.4;
	private static final int X_SIZE = 366;
	private static final int Y_SIZE = 256;
	
	private static final int START = 3; // start divider
	private static final int MARGIN = 10; // margin for ROI
	private static final double OBJECT_X = 42.3;
	private static final double OBJECT_Y = 27.5;

	@Test
	public void testRegisterSynthetic() {
		testRegisterSynthetic(null, 0.09);
	}

	@Test
	public void testRegisterSyntheticWithROI() {
		double[] start = new double[] {X_SIZE/START - OBJECT_X - MARGIN, Y_SIZE/START - OBJECT_Y - MARGIN};
		double[] end = start.clone();
		end[0] += 2*OBJECT_X + MAX*X_DELTA + MARGIN;
		end[1] += 2*OBJECT_Y + MAX*Y_DELTA + MARGIN;
		RectangularROI roi = new RectangularROI(start, end);
		System.err.println(roi);
		testRegisterSynthetic(roi, 1.39);
	}

	private void testRegisterSynthetic(IRectangularROI roi, double delta) {
		double[][] shifts = generateShifts(MAX);
		System.err.println(Arrays.deepToString(shifts));
		IDataset[] images = createTestImages(shifts, Y_SIZE, X_SIZE);

		RegisterImage2 reg = new RegisterImage2();
		DoubleDataset filter = DatasetFactory.ones(DoubleDataset.class, 9, 9);
		filter.imultiply(1./ ((Number) filter.sum()).doubleValue());
		reg.setFilter(filter);
		reg.setRectangle(roi);
		List<? extends IDataset> coords = reg.value(images);
		for (int i = 0; i < MAX; i++) {
			Assert.assertArrayEquals(shifts[i], (double[]) DatasetUtils.convertToDataset(coords.get(2*i)).getBuffer(), delta);
		}
	}

	public double[][] generateShifts(int number) {
		double[][] shifts = new double[number][2];
		for (int n = 0; n < number; n++) {
			shifts[n][0] = n*Y_DELTA;
			shifts[n][1] = n*X_DELTA;
		}
		return shifts;
	}

	public IDataset[] createTestImages(double[][] shifts, int height, int width) {
		int number = shifts.length;
		IDataset[] images = new IDataset[number];
		double[] start = new double[2];
		for (int n = 0; n < number; n++) {
			start[0] = height/START + shifts[n][0];
			start[1] = width/START + shifts[n][1];
			images[n] = createImage(height, width, start);
		}
		return images;
	}

	/**
	 * Create height field
	 * @param height
	 * @param width
	 * @param start
	 * @return image
	 */
	public IDataset createImage(int height, int width, double[] start) {
		DoubleDataset image = DatasetFactory.zeros(height, width);
		for (int i = 0; i < height; i++) {
			double y = i - start[0];
			for (int j = 0; j < width; j++) {
				double z = getZ(j - start[1], y);
				if (z != 0) {
					image.setItem(z, i, j);
				}
			}
		}
		return image;
	}

	/**
	 * Truncated pyramid
	 * @param x
	 * @param y
	 * @return z
	 */
	private double getZ(double x, double y) {
		final double m = OBJECT_Y/OBJECT_X;
		final double zmax = 0.5;
		double ax = Math.abs(x);
		double ay = Math.abs(y);
		if (ax > OBJECT_X || ay > OBJECT_Y) {
			return 0;
		}
		double yl = m*ax;
		double z;
		if (y < yl && y > -yl) { // within E-W sides
			z = 1 - ax/OBJECT_X;
		} else { // within N-S sides
			z = 1 - ay/OBJECT_Y;
		}
		if (z > zmax) {
			z = zmax;
		}
		return z;
	}
}
