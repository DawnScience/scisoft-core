/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.junit.Assert;
import org.junit.Test;

public class DiffGaussianTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new DiffGaussian();
		f.setParameterValues(23., 2., 1.2);
		Assert.assertEquals(3, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {23., 2., 1.2}, f.getParameterValues(), ABS_TOL);

		FunctionTestUtils.checkValues(f);

		double a = 2;
		double b = Math.sqrt(1.2);
		Assert.assertEquals(0, f.val(23.), ABS_TOL);
		double gd = 2*a*b*Math.exp(-1);
		Assert.assertEquals(gd, f.val(23. - 1/b), ABS_TOL);
		Assert.assertEquals(-gd, f.val(23. + 1/b), ABS_TOL);

		Dataset x = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 46, 200);
		Dataset v = f.calculateValues(x);
		Assert.assertEquals(0, ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1)), ABS_TOL);

		Dataset xd = DatasetFactory.createFromObject(new double[] {23. - 1/b, 23, 23. + 1/b});
		DoubleDataset dx;
		dx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {gd, 0, -gd}, dx.getData(), ABS_TOL);
	}

	@Test
	public void testFunctionDerivative() {
		AFunction f = new DiffGaussian();
		f.setParameterValues(23., 2., 1.2);

		FunctionTestUtils.checkPartialDerivatives(f);
	}

	@Test
	public void testGetGaussian() {
		DiffGaussian f = new DiffGaussian();
		f.setParameterValues(23., 2., 0.012);
		AFunction g = f.getGaussian();

		Dataset x = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 46, 200);
		Dataset v = f.calculateValues(x).getSliceView(new Slice(-1));
		Dataset w = g.calculateValues(x);

		Dataset dw = Maths.difference(w, 1, 0);
		dw.imultiply(v.max(true).doubleValue() / dw.max(true).doubleValue());
		TestUtils.assertDatasetEquals(v, dw, 1e-2, 6e-3);
	}
}
