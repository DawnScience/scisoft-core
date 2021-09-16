/*-
 * Copyright (c) 2021 Diamond Light Source Ltd.
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
import org.junit.Assert;
import org.junit.Test;

public class PlanckTest {

	private static final double ABS_TOL = 1e-7;
	private static final double BIG_ABS_TOL = 1e5;
	private static final double TINY_ABS_TOL = 1e-20;

	@Test
	public void testFunction() {
		AFunction f = new Planck();
		f.setParameterValues(0., 3000.);
		Assert.assertEquals(2, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0., 3000.}, f.getParameterValues(), ABS_TOL);
		Assert.assertEquals(0., f.val(1), ABS_TOL);

		f.setParameterValues(1., 1.);
		Assert.assertArrayEquals(new double[] {1., 1.}, f.getParameterValues(), ABS_TOL);
		double c = 299792458.;
	    double h = 6.62607015e-34;
	    double kB = 1.380649e-23;
		Assert.assertEquals(2 * Math.PI * h * c * c / Math.expm1(h * c / kB), f.val(1), TINY_ABS_TOL);
		
		f.setParameterValues(1., 3000.);
		FunctionTestUtils.checkValues(f);

		Dataset xd = DatasetFactory.createFromObject(new double[] {0.0000006, 0.00000065, 0.0000007});
		DoubleDataset dx;
		dx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {1.62578454e+12, 2.01561809e+12, 2.35807196e+12}, dx.getData(), BIG_ABS_TOL);
		
	}
	
	@Test
	public void testFunctionDerivative() {
		AFunction f = new Planck();
		f.setParameterValues(1,3000);

		FunctionTestUtils.checkPartialDerivatives(f);
	}
}
