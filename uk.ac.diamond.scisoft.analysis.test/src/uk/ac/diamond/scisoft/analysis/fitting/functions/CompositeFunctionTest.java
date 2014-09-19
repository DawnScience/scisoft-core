/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
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

		Assert.assertArrayEquals(new double[] {23., -10., 1.2, -5.2, 4.2, -7.5}, cf.getParameterValues(), ABS_TOL);
		double x = -23. - 10. - 1.2 - 5.2 - 4.2 - 7.5;
		Assert.assertEquals(x, cf.val(-1), ABS_TOL);
		Assert.assertEquals(x, cf.calculateValues(DatasetFactory.createRange(-2., 2., 1, Dataset.INT16)).getDouble(1), ABS_TOL);

		DoubleDataset xd = new DoubleDataset(new double[] {-1, 0, 2});
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

		DoubleDataset[] coords = new DoubleDataset[] {DoubleDataset.createRange(15, 30, 0.25)};
		DoubleDataset weight = null;
		CoordinatesIterator it = cf.getIterator(coords);
		DoubleDataset current = new DoubleDataset(it.getShape());
		DoubleDataset data = Random.randn(it.getShape());
		cf.fillWithValues(current, it);
		double rd = data.residual(current, weight, false);
		double rf = cf.residual(true, data, weight, coords);
		Assert.assertEquals(rd, rf, 1e-9);
	}
}
