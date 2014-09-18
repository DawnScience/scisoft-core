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

import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Assert;
import org.junit.Test;

public class QuadraticTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new Quadratic();
		Assert.assertEquals(3, f.getNoOfParameters());
		f.setParameterValues(23., -10., 1.2);
		Assert.assertArrayEquals(new double[] {23., -10., 1.2}, f.getParameterValues(), ABS_TOL);
		Assert.assertEquals(23. + 10. + 1.2, f.val(-1), ABS_TOL);

		DoubleDataset xd = new DoubleDataset(new double[] {-1, 0, 2});
		DoubleDataset dx;
		dx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {23. + 10. + 1.2, 1.2, 23.*4 - 10.*2 + 1.2}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(0), xd);
		Assert.assertArrayEquals(new double[] {1, 0, 4}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(1), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 2}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(2), xd);
		Assert.assertArrayEquals(new double[] {1, 1, 1}, dx.getData(), ABS_TOL);

		Assert.assertEquals(1,  f.partialDeriv(f.getParameter(0), -1), ABS_TOL);
		Assert.assertEquals(-1, f.partialDeriv(f.getParameter(1), -1), ABS_TOL);
		Assert.assertEquals(1,  f.partialDeriv(f.getParameter(2), -1), ABS_TOL);
	}
}
