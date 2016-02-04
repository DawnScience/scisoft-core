/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class PseudoVoigtTest {

	private static final double ABS_TOL = 1e-7;
	private static final double REL_TOL = 1e-15;

	@Test
	public void testFunction() {
		PseudoVoigt f = new PseudoVoigt();
		Assert.assertEquals(5, f.getNoOfParameters());
		f.setParameterValues(23., 2., 1.2, 2.3, 0.6);
		Assert.assertArrayEquals(new double[] {23., 2., 1.2, 2.3, 0.6}, f.getParameterValues(), ABS_TOL);

		double l = 2 * 0.6 * Math.PI / 2;
		double g = 2.3 * (1 - 0.6) * Math.sqrt(Math.PI / Math.log(2.)) / 2;
		double h = 1.2 / (g + l);

		Assert.assertEquals(h, f.val(23.), ABS_TOL);
		Assert.assertEquals(h, f.getHeight(), ABS_TOL);

		double dx = f.getFWHM() / 2.;
		Assert.assertEquals(0.5 * h, f.val(23. - dx), 1e-4);
		Assert.assertEquals(0.5 * h, f.val(23. + dx), 1e-4);

		Dataset x = DatasetUtils.linSpace(-20+23, 20+23, 401, Dataset.FLOAT64);
		Dataset v = f.calculateValues(x);
		double s = ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1));
		Assert.assertEquals(1.2, s, 1e-1);
	}

	@Test
	public void testExtremes() {
		Dataset x = DatasetUtils.linSpace(-20+23, 20+23, 401, Dataset.FLOAT64);

		PseudoVoigt pv = new PseudoVoigt();
		pv.setParameterValues(23., 2., 1.2, 2.3, 1);
		Dataset pl = pv.calculateValues(x);

		pv.setParameterValues(23., 2., 1.2, 2.3, 0);
		Dataset pg = pv.calculateValues(x);

		Lorentzian lf = new Lorentzian();
		lf.setParameterValues(23., 2., 1.2);
		Dataset l = lf.calculateValues(x);
		TestUtils.assertDatasetEquals(l, pl, REL_TOL, ABS_TOL);

		Gaussian gf = new Gaussian();
		gf.setParameterValues(23., 2.3, 1.2);
		Dataset g = gf.calculateValues(x);
		TestUtils.assertDatasetEquals(g, pg, REL_TOL, ABS_TOL);
	}
}
