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
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

public class PseudoVoigtTest {

	private static final double ABS_TOL = 1e-7;

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

		double dx = ((IPeak) f).getFWHM() / 2.;
		Assert.assertEquals(0.5 * h, f.val(23. - dx), 1e-4);
		Assert.assertEquals(0.5 * h, f.val(23. + dx), 1e-4);

		AbstractDataset x = DatasetUtils.linSpace(-20+23, 20+23, 401, Dataset.FLOAT64);
		AbstractDataset v = DatasetUtils.convertToAbstractDataset(f.calculateValues(x));
		double s = ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1));
		Assert.assertEquals(1.2, s, 1e-1);
	}

	@Test
	public void testExtremes() {
		AbstractDataset x = DatasetUtils.linSpace(-20+23, 20+23, 401, Dataset.FLOAT64);

		PseudoVoigt pv = new PseudoVoigt();
		pv.setParameterValues(23., 2., 1.2, 2.3, 1);
		AbstractDataset pl = DatasetUtils.convertToAbstractDataset(pv.calculateValues(x));

		pv.setParameterValues(23., 2., 1.2, 2.3, 0);
		AbstractDataset pg = DatasetUtils.convertToAbstractDataset(pv.calculateValues(x));

		Lorentzian lf = new Lorentzian();
		lf.setParameterValues(23., 2., 1.2);
		AbstractDataset l = DatasetUtils.convertToAbstractDataset(lf.calculateValues(x));
		checkDatasets(pl, l);

		Gaussian gf = new Gaussian();
		gf.setParameterValues(23., 2.3, 1.2);
		AbstractDataset g = DatasetUtils.convertToAbstractDataset(gf.calculateValues(x));
		checkDatasets(pg, g);
	}

	private void checkDatasets(AbstractDataset a, AbstractDataset b) {
		IndexIterator it = a.getIterator();
		while (it.hasNext()) {
			Assert.assertEquals(a.getElementDoubleAbs(it.index), b.getElementDoubleAbs(it.index), ABS_TOL);
		}
	}
}
