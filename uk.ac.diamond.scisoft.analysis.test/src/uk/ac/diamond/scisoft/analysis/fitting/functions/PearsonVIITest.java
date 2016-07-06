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
import org.junit.Assert;
import org.junit.Test;

public class PearsonVIITest {

	private static final double ABS_TOL = 1e-7;
	private static final double REL_TOL = 1e-15;

	@Test
	public void testFunction() {
		APeak f = new PearsonVII();
		Assert.assertEquals(4, f.getNoOfParameters());
		f.setParameterValues(23., 2., 1.2, 2);
		Assert.assertArrayEquals(new double[] {23., 2., 1.2, 2}, f.getParameterValues(), ABS_TOL);

		double h = 1.2 * Math.sqrt(Math.sqrt(2) - 1) / (Math.PI / 2);
		Assert.assertEquals(h, f.val(23.), ABS_TOL);
		Assert.assertEquals(h, f.getHeight(), ABS_TOL);

		Assert.assertEquals(0.5 * h, f.val(23. - 1), ABS_TOL);
		Assert.assertEquals(0.5 * h, f.val(23. + 1), ABS_TOL);

		Dataset x = DatasetFactory.createLinearSpace(-50+23, 50+23, 200, Dataset.FLOAT64);
		Dataset v = f.calculateValues(x);
		Assert.assertEquals(1.2, ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1)), 1e-4);
	}

	@Test
	public void testExtremes() {
		Dataset x = DatasetFactory.createLinearSpace(-20+23, 20+23, 401, Dataset.FLOAT64);

		PearsonVII pv = new PearsonVII();
		pv.getParameter(3).setUpperLimit(Double.MAX_VALUE);
		pv.setParameterValues(23., 2., 1.2, 1);
		Dataset pl = pv.calculateValues(x);

		double power = 500000;
		pv.setParameterValues(23., 2., 1.2, power);
		Dataset pg = pv.calculateValues(x);

		Lorentzian lf = new Lorentzian();
		lf.setParameterValues(23., 2., 1.2);
		Dataset l = lf.calculateValues(x);
		TestUtils.assertDatasetEquals(l, pl, REL_TOL, ABS_TOL);

		Gaussian gf = new Gaussian();
		double width = pv.getFWHM()*Math.sqrt(2 * Math.log(2.)/( (2*power - 3) * (Math.pow(2, 1/power) - 1)));
		gf.setParameterValues(23., width, 1.2);
		Dataset g = gf.calculateValues(x);
		TestUtils.assertDatasetEquals(g, pg, REL_TOL, 5*ABS_TOL);
	}
}
