/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.image;

import java.util.List;

import org.apache.commons.math3.special.Erf;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IndexIterator;
import org.junit.Assert;
import org.junit.Test;

public class ImageUtilsTest {

	private static final double mobrt = -Math.sqrt(0.5);

	/**
	 * Returns CDF of unit Gaussian centred on zero
	 * @param x
	 * @return CDF
	 */
	private static double cdfGaussian(double x) {
		return 0.5*Erf.erfc(mobrt*x);
	}

	private void distributePeak(Dataset data, double[] centre, double width, double amplitude) {
		int rank = data.getRank();
		int[] start = new int[rank];
		int[] stop = new int[rank];
		int window = (int) Math.ceil(6*width); // 3 sigma 
		for (int i = 0; i < rank; i++) {
			start[i] = (int) (Math.floor(centre[i]) - window/2);
			stop[i] = start[i] + window;
		}

		double f = 1./width;
		IndexIterator it = data.getSliceIterator(start, stop, null);
		int[] pos = it.getPos();
		while (it.hasNext()) {
			
			double v = 1;
			for (int j = 0; j < rank; j++) {
				double x = f * (pos[j] - centre[j]);
				v *= cdfGaussian(x + f) - cdfGaussian(x);
			}
			data.setObjectAbs(it.index, v*amplitude + data.getElementDoubleAbs(it.index));
		}
	}

	private static final double REL_TOL = 1e-2;

	@Test
	public void testDistributePeak() {
		final int size = 128;
		final double amp = 5;

		// 1D
		double[] centre = new double[] {32.3};
		int[] pos = new int[] {(int) centre[0]};
		Dataset data = DatasetFactory.zeros(size);

		distributePeak(data, centre, 5, amp);
		double sum = ((Number) data.sum()).doubleValue();
		Assert.assertEquals(amp, sum, REL_TOL*amp);
		Assert.assertEquals(0.398, data.getDouble(pos), 0.001);

		data.fill(0);
		distributePeak(data, centre, 0.5, amp);
		sum = ((Number) data.sum()).doubleValue();
		Assert.assertEquals(amp, sum, REL_TOL*amp);
		Assert.assertEquals(3.225, data.getDouble(pos), 0.001);

		// 2D
		centre = new double[] {32.3, 72.7};
		pos = new int[] {(int) centre[0], (int) centre[1]};

		data = DatasetFactory.zeros(size, size);

		distributePeak(data, centre, 5, amp);
		sum = ((Number) data.sum()).doubleValue();
		Assert.assertEquals(amp, sum, REL_TOL*amp);
		Assert.assertEquals(0.032, data.getDouble(pos), 0.001);

		data.fill(0);
		distributePeak(data, centre, 0.5, amp);
		sum = ((Number) data.sum()).doubleValue();
		Assert.assertEquals(amp, sum, REL_TOL*amp);
		Assert.assertEquals(2.080, data.getDouble(pos), 0.001);
	}

	@Test
	public void testSinglePeak1D() {
		final int size = 32;
		final int window = 3;
		final double amp = 5;
		double[] centre = new double[] {12.3};

		Dataset data = DatasetFactory.zeros(size);

		distributePeak(data, centre, 0.5, amp);
		List<Dataset> result = ImageUtils.findWindowedPeaks(data, window, 0.1*amp, 1.1*amp);
		Assert.assertEquals(3, result.size());

		Dataset sum = result.get(0);
		Assert.assertEquals(1, sum.getSize());
		Assert.assertArrayEquals(new int[] {1}, sum.getShapeRef());
		Assert.assertEquals(amp, sum.getDouble(0), REL_TOL*amp);
		Dataset coords = result.get(1);
		Assert.assertArrayEquals(new int[] {1, 1}, coords.getShapeRef());
		Assert.assertEquals(centre[0], coords.getDouble(0, 0), REL_TOL*centre[0]);
		Dataset fraction = result.get(2);
		Assert.assertEquals(3.225/amp, fraction.getDouble(0), 0.01);
	}

	@Test
	public void testSinglePeak2D() {
		final int size = 128;
		final int window = 3;
		final double amp = 5;
		double[] centre = new double[] {32.3, 72.7};

		Dataset data = DatasetFactory.zeros(size, size);

		distributePeak(data, centre, 0.5, amp);
		List<Dataset> result = ImageUtils.findWindowedPeaks(data, window, 0.1*amp, 1.1*amp);
		Assert.assertEquals(3, result.size());

		Dataset sum = result.get(0);
		Assert.assertEquals(1, sum.getSize());
		Assert.assertArrayEquals(new int[] {1}, sum.getShapeRef());
		Assert.assertEquals(amp, sum.getDouble(0), REL_TOL*amp);
		Dataset coords = result.get(1);
		Assert.assertArrayEquals(new int[] {1, 2}, coords.getShapeRef());
		Assert.assertEquals(centre[0], coords.getDouble(0, 0), REL_TOL*centre[0]);
		Assert.assertEquals(centre[1], coords.getDouble(0, 1), REL_TOL*centre[1]);
		Dataset fraction = result.get(2);
		Assert.assertEquals(2.080/amp, fraction.getDouble(0), 0.01);
	}
}
