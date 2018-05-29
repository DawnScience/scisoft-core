/*-
 * Copyright (c) 2015 Diamond Light Source Ltd.
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
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.utils.Faddeeva;

public class FanoGaussianTest {

	private static final double ABS_TOL = 1e-7;
//	private static final double REL_TOL = 1e-15;

	@Test
	public void testFunction() {
		AFunction f = new FanoGaussian();
		f.setParameterValues(23., 5., 4., 1.2, 6.);
		Assert.assertEquals(5, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {23., 5., 4., 1.2, 6.}, f.getParameterValues(), ABS_TOL);

		FunctionTestUtils.checkValues(f);

		double fr = 2 * Math.sqrt(Math.log(2)) / 1.2;
		double xi = fr*5./2.;
		double hr = 4.*fr*Faddeeva.erfcx(xi)/Math.sqrt(Math.PI);
		Assert.assertEquals(hr, f.val(23.), ABS_TOL);

		// check area
		Dataset x = DatasetFactory.createLinearSpace(DoubleDataset.class, -60+23, 60+23, 401);
		Dataset v = f.calculateValues(x);
		double s = ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1));
		Assert.assertEquals(4, s, 1.1e-1);
	}

	@Test
	public void testExtremes() {
		Dataset x = DatasetFactory.createLinearSpace(DoubleDataset.class, -20, 20, 401);

		FanoGaussian fg = new FanoGaussian();
		fg.setParameterValues(0., 5., 4., 1.2, 6000);
		Dataset pv = fg.calculateValues(x);

		Voigt v = new Voigt();
		v.setParameterValues(0., 5., 4., 1.2);
		Dataset vv = v.calculateValues(x);

		TestUtils.assertDatasetEquals(vv, pv, 1e-2, 1e-7);
	}

	@Test
	public void testFunctionDerivative() {
		AFunction f = new FanoGaussian();
		f.setParameterValues(23., 5., 4., 1.2, 6.);

		FunctionTestUtils.checkPartialDerivatives(f);
	}
}
