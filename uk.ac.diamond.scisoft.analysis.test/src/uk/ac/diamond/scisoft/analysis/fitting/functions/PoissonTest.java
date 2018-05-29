/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.apache.commons.math3.special.Gamma;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Assert;
import org.junit.Test;

public class PoissonTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new Poisson();
		f.setParameterValues(23., 1.2);
		Assert.assertEquals(2, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {23., 1.2}, f.getParameterValues(), ABS_TOL);

		FunctionTestUtils.checkValues(f);

		double lh = Math.log(1.2)  + 23. * Math.log(23) - 23 - Gamma.logGamma(23. + 1);
		Assert.assertEquals(lh, Math.log(f.val(23.)), ABS_TOL);

		Dataset x = DatasetFactory.createRange(46);
		Dataset v = f.calculateValues(x);
		Assert.assertEquals(1.2, ((Number) v.sum()).doubleValue(), ABS_TOL*1e3);
	}

	@Test
	public void testFunctionDerivative() {
		AFunction f = new Poisson();
		f.setParameterValues(23., 1.2);

		FunctionTestUtils.checkPartialDerivatives(f);
	}
}
