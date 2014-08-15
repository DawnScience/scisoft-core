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

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;

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

		Dataset x = DatasetUtils.linSpace(-100+23, 100+23, 201, Dataset.FLOAT64);
		Dataset v = DatasetUtils.convertToDataset(f.calculateValues(x));
		double s = ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1));
		Assert.assertEquals(1.2, s, 1e-2);
	}
}
