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
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.junit.Assert;
import org.junit.Test;

public class PearsonVIITest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		IFunction f = new PearsonVII();
		Assert.assertEquals(4, f.getNoOfParameters());
		f.setParameterValues(23., 2., 1.2, 2);
		Assert.assertArrayEquals(new double[] {23., 2., 1.2, 2}, f.getParameterValues(), ABS_TOL);

		double h = 1.2 * Math.sqrt(Math.sqrt(2) - 1) / (Math.PI / 2);
		Assert.assertEquals(h, f.val(23.), ABS_TOL);

		Assert.assertEquals(0.5 * h, f.val(23. - 1), ABS_TOL);
		Assert.assertEquals(0.5 * h, f.val(23. + 1), ABS_TOL);

		Dataset x = DatasetUtils.linSpace(-50+23, 50+23, 200, Dataset.FLOAT64);
		Dataset v = DatasetUtils.convertToDataset(f.calculateValues(x));
		Assert.assertEquals(1.2, ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1)), 1e-4);
	}

	@Test
	public void testExtremes() {
		Dataset x = DatasetUtils.linSpace(-20+23, 20+23, 401, Dataset.FLOAT64);

		PearsonVII pv = new PearsonVII();
		pv.getParameter(3).setUpperLimit(Double.MAX_VALUE);
		pv.setParameterValues(23., 2., 1.2, 1);
		Dataset pl = DatasetUtils.convertToDataset(pv.calculateValues(x));

		double power = 500000;
		pv.setParameterValues(23., 2., 1.2, power);
		Dataset pg = DatasetUtils.convertToDataset(pv.calculateValues(x));

		Lorentzian lf = new Lorentzian();
		lf.setParameterValues(23., 2., 1.2);
		Dataset l = DatasetUtils.convertToDataset(lf.calculateValues(x));
		checkDatasets(pl, l, ABS_TOL);

		Gaussian gf = new Gaussian();
		double width = pv.getFWHM()*Math.sqrt(2 * Math.log(2.)/( (2*power - 3) * (Math.pow(2, 1/power) - 1)));
		gf.setParameterValues(23., width, 1.2);
		Dataset g = DatasetUtils.convertToDataset(gf.calculateValues(x));
		checkDatasets(pg, g, 1e-6);
	}

	private void checkDatasets(Dataset a, Dataset b, double tol) {
		IndexIterator it = a.getIterator();
		while (it.hasNext()) {
			Assert.assertEquals(a.getElementDoubleAbs(it.index), b.getElementDoubleAbs(it.index), tol);
		}
	}
}
