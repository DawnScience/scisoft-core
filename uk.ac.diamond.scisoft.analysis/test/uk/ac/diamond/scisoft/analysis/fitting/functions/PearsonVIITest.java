/*-
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

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

		AbstractDataset x = DatasetUtils.linSpace(-50+23, 50+23, 200, AbstractDataset.FLOAT64);
		AbstractDataset v = DatasetUtils.convertToAbstractDataset(f.makeDataset(x));
		Assert.assertEquals(1.2, ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1)), 1e-4);
	}

	@Test
	public void testExtremes() {
		AbstractDataset x = DatasetUtils.linSpace(-20+23, 20+23, 401, AbstractDataset.FLOAT64);

		PearsonVII pv = new PearsonVII();
		pv.getParameter(3).setUpperLimit(Double.MAX_VALUE);
		pv.setParameterValues(23., 2., 1.2, 1);
		AbstractDataset pl = DatasetUtils.convertToAbstractDataset(pv.makeDataset(x));

		double power = 500000;
		pv.setParameterValues(23., 2., 1.2, power);
		AbstractDataset pg = DatasetUtils.convertToAbstractDataset(pv.makeDataset(x));

		Lorentzian lf = new Lorentzian();
		lf.setParameterValues(23., 2., 1.2);
		AbstractDataset l = DatasetUtils.convertToAbstractDataset(lf.makeDataset(x));
		checkDatasets(pl, l, ABS_TOL);

		Gaussian gf = new Gaussian();
		double width = pv.getFWHM()*Math.sqrt(2 * Math.log(2.)/( (2*power - 3) * (Math.pow(2, 1/power) - 1)));
		gf.setParameterValues(23., width, 1.2);
		AbstractDataset g = DatasetUtils.convertToAbstractDataset(gf.makeDataset(x));
		checkDatasets(pg, g, 1e-6);
	}

	private void checkDatasets(AbstractDataset a, AbstractDataset b, double tol) {
		IndexIterator it = a.getIterator();
		while (it.hasNext()) {
			Assert.assertEquals(a.getElementDoubleAbs(it.index), b.getElementDoubleAbs(it.index), tol);
		}
	}
}
