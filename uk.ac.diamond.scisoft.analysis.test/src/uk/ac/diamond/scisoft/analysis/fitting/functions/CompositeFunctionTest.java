/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Random;
import org.eclipse.january.dataset.ShortDataset;
import org.junit.Assert;
import org.junit.Test;

public class CompositeFunctionTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		CompositeFunction cf = new CompositeFunction();

		AFunction fa = new Cubic();
		fa.setParameterValues(23., -10., 1.2, -5.2);

		AFunction fb = new StraightLine();
		fb.setParameterValues(4.2, -7.5);

		cf.addFunction(fa);
		cf.addFunction(fb);

		Assert.assertEquals(6, cf.getNoOfParameters());

		FunctionTestUtils.checkValues(cf);

		Assert.assertArrayEquals(new double[] {23., -10., 1.2, -5.2, 4.2, -7.5}, cf.getParameterValues(), ABS_TOL);
		double x = -23. - 10. - 1.2 - 5.2 - 4.2 - 7.5;
		Assert.assertEquals(x, cf.val(-1), ABS_TOL);
		Assert.assertEquals(x, cf.calculateValues(DatasetFactory.createRange(ShortDataset.class, -2., 2., 1)).getDouble(1), ABS_TOL);

		Dataset xd = DatasetFactory.createFromObject(new double[] {-1, 0, 2});
		DoubleDataset dx;

		dx = cf.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {-23. - 10. - 1.2 - 5.2 - 4.2 - 7.5, -5.2 - 7.5,
				23.*8 - 10.*4 + 1.2*2 - 5.2 + 4.2*2 - 7.5}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(0), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 8}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(1), xd);
		Assert.assertArrayEquals(new double[] {1, 0, 4}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(2), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 2}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(3), xd);
		Assert.assertArrayEquals(new double[] {1, 1, 1}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(4), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 2}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(5), xd);
		Assert.assertArrayEquals(new double[] {1, 1, 1}, dx.getData(), ABS_TOL);

		DoubleDataset[] coords = new DoubleDataset[] {DatasetFactory.createRange(DoubleDataset.class, 15, 30, 0.25)};
		DoubleDataset weight = null;
		CoordinatesIterator it = CoordinatesIterator.createIterator(null, coords);
		DoubleDataset current = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		DoubleDataset data = Random.randn(it.getShape());
		cf.fillWithValues(current, it);
		double rd = data.residual(current, weight, false);
		double rf = cf.residual(true, data, weight, coords);
		Assert.assertEquals(rd, rf, 1e-9);
	}

	@Test
	public void testFunctionDerivative() {
		CompositeFunction f = new CompositeFunction();

		AFunction fa = new Cubic();
		fa.setParameterValues(23., -10., 1.2, -5.2);

		AFunction fb = new StraightLine();
		fb.setParameterValues(4.2, -7.5);

		f.addFunction(fa);
		f.addFunction(fb);

		FunctionTestUtils.checkPartialDerivatives(f);
	}
}
