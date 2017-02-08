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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterImageTest {
	private final static Logger logger = LoggerFactory.getLogger(RegisterImage.class);
	private static final int MAX = 6;
	private static final double X_DELTA = 2.4;
	private static final double Y_DELTA = 0.4;
	private static final int X_SIZE = 366;
	private static final int Y_SIZE = 256;
	
	private static final int START = 3; // start divder
	private static final int MARGIN = 5; // margin for ROI
	private static final double OBJECT_X = 42.3;
	private static final double OBJECT_Y = 27.5;

	@Test
	public void testRegisterSynthetic() {
		try {
			throw new UnsupportedOperationException("Test logger");
		} catch (UnsupportedOperationException e) {
			logger.error("Start {} end", START, e);
		}
		testRegisterSynthetic(null);
	}

	@Test
	public void testRegisterSyntheticWithROI() {
		double[] start = new double[] {X_SIZE/START - OBJECT_X - MARGIN, Y_SIZE/START - OBJECT_Y - MARGIN};
		double[] end = start.clone();
		end[0] += 2*OBJECT_X + MAX*X_DELTA + MARGIN;
		end[1] += 2*OBJECT_Y + MAX*Y_DELTA + MARGIN;
		RectangularROI roi = new RectangularROI(start, end);
		System.err.println(roi);
		testRegisterSynthetic(roi);
	}

	private void testRegisterSynthetic(IRectangularROI roi) {
		double[][] shifts = generateShifts(MAX);
		System.err.println(Arrays.deepToString(shifts));
		IDataset[] images = createTestImages(shifts, Y_SIZE, X_SIZE);
		IDataset anchor = images[0];

		RegisterImage reg = new RegisterImage();
		reg.setReference(anchor);
		reg.setRectangle(roi);
		List<? extends IDataset> coords = reg.value(images);
		for (int i = 0; i < MAX; i++) {
			Assert.assertArrayEquals(shifts[i], (double[]) DatasetUtils.convertToDataset(coords.get(2*i)).getBuffer(), 0.21);
		}
	}

	private double[][] generateShifts(int number) {
		double[][] shifts = new double[number][2];
		for (int n = 0; n < number; n++) {
			shifts[n][0] = n*Y_DELTA;
			shifts[n][1] = n*X_DELTA;
		}
		return shifts;
	}

	private IDataset[] createTestImages(double[][] shifts, int height, int width) {
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
		if (Math.abs(x) > OBJECT_X || Math.abs(y) > OBJECT_Y) {
			return 0;
		}
		double ax = Math.abs(x);
		double yl = m*ax;
		double z;
		if (y < yl && y > -yl) { // within E-W sides
			z = 1 - ax/OBJECT_X;
		} else {
		double ay = Math.abs(y); // within N-S sides
			z = 1 - ay/OBJECT_Y;
		}
		if (z > zmax) {
			z = zmax;
		}
		return z;
	}
}
