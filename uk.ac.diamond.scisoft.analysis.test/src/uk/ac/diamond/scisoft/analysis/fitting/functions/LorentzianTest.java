/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.junit.Assert;
import org.junit.Test;

public class LorentzianTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		IFunction f = new Lorentzian();
		Assert.assertEquals(3, f.getNoOfParameters());
		f.setParameterValues(23., 2., 1.2);
		Assert.assertArrayEquals(new double[] {23., 2., 1.2}, f.getParameterValues(), ABS_TOL);

		double h = 1.2 / Math.PI;
		Assert.assertEquals(h, f.val(23.), ABS_TOL);

		Assert.assertEquals(0.5 * h, f.val(23. - 1), ABS_TOL);
		Assert.assertEquals(0.5 * h, f.val(23. + 1), ABS_TOL);

		// test that equals(f2) works even if f2 is still "dirty" 
		IFunction f2 = new Lorentzian(new double[] {23., 2., 1.2});
		Assert.assertTrue(f.equals(f2));
		
		Dataset x = DatasetUtils.linSpace(-100+23, 100+23, 201, Dataset.FLOAT64);
		Dataset v = DatasetUtils.convertToDataset(f.calculateValues(x));
		double s = ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1));
		Assert.assertEquals(1.2, s, 1e-2);

		// test that calculateValues(dataset) gives same as f.val(double)
		Assert.assertEquals(v.getDouble(0), f.val(x.getDouble(0)), ABS_TOL);
	}
}
