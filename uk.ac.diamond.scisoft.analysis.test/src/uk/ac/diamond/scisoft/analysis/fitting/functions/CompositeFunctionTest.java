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
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Random;

public class CompositeFunctionTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		CompositeFunction cf = new CompositeFunction();

		AFunction fa = new Cubic();
		fa.setParameterValues(23., -10., 1.2, -5.2);

		AFunction fb = new StraightLine();
		fb.setParameterValues(4.2, -7.5);

		cf.addFunction(fa);
		cf.addFunction(fb);

		Assert.assertEquals(6, cf.getNoOfParameters());

		Assert.assertArrayEquals(new double[] {23., -10., 1.2, -5.2, 4.2, -7.5}, cf.getParameterValues(), ABS_TOL);
		double x = -23. - 10. - 1.2 - 5.2 - 4.2 - 7.5;
		Assert.assertEquals(x, cf.val(-1), ABS_TOL);
		Assert.assertEquals(x, cf.calculateValues(AbstractDataset.arange(-2., 2., 1, Dataset.INT16)).getDouble(1), ABS_TOL);

		DoubleDataset xd = new DoubleDataset(new double[] {-1, 0, 2});
		DoubleDataset dx;

		dx = cf.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {-23. - 10. - 1.2 - 5.2 - 4.2 - 7.5, -5.2 - 7.5,
				23.*8 - 10.*4 + 1.2*2 - 5.2 + 4.2*2 - 7.5}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(0), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 8}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(1), xd);
		Assert.assertArrayEquals(new double[] {1, 0, 4}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(2), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 2}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(3), xd);
		Assert.assertArrayEquals(new double[] {1, 1, 1}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(4), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 2}, dx.getData(), ABS_TOL);

		dx = cf.calculatePartialDerivativeValues(cf.getParameter(5), xd);
		Assert.assertArrayEquals(new double[] {1, 1, 1}, dx.getData(), ABS_TOL);

		DoubleDataset[] coords = new DoubleDataset[] {DoubleDataset.arange(15, 30, 0.25)};
		DoubleDataset weight = null;
		CoordinatesIterator it = cf.getIterator(coords);
		DoubleDataset current = new DoubleDataset(it.getShape());
		DoubleDataset data = Random.randn(it.getShape());
		cf.fillWithValues(current, it);
		double rd = data.residual(current, weight, false);
		double rf = cf.residual(true, data, weight, coords);
		Assert.assertEquals(rd, rf, 1e-9);
	}
}
