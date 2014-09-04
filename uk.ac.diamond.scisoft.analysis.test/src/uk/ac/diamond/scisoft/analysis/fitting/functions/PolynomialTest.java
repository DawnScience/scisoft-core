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

import org.apache.commons.math3.complex.Complex;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

public class PolynomialTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new Polynomial(3);
		Assert.assertEquals(4, f.getNoOfParameters());
		f.setParameterValues(23., -10., 1.2, -5.2);
		Assert.assertArrayEquals(new double[] {23., -10., 1.2, -5.2}, f.getParameterValues(), ABS_TOL);
		Assert.assertEquals(-23. - 10. - 1.2 - 5.2, f.val(-1), ABS_TOL);

		DoubleDataset xd = new DoubleDataset(new double[] {-1, 0, 2});
		DoubleDataset dx;
		dx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {-23. - 10. - 1.2 - 5.2, -5.2, 23.*8 - 10.*4 + 1.2*2 - 5.2}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(0), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 8}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(1), xd);
		Assert.assertArrayEquals(new double[] {1, 0, 4}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(2), xd);
		Assert.assertArrayEquals(new double[] {-1, 0, 2}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(3), xd);
		Assert.assertArrayEquals(new double[] {1, 1, 1}, dx.getData(), ABS_TOL);
	}

	@Test
	public void testFindRoots() {
		checkComplex(new Complex[] {new Complex(-2), new Complex(2)}, Polynomial.findRoots(new double[] {1, 0, -4}));
		checkComplex(new Complex[] {new Complex(-1), new Complex(1)}, Polynomial.findRoots(new double[] {1, 0, -1}));
		checkComplex(new Complex[] {new Complex(-1), new Complex(-1)}, Polynomial.findRoots(new double[] {1, 2, 1}));
		checkComplex(new Complex[] {new Complex(0, 1), new Complex(0, -1)}, Polynomial.findRoots(new double[] {1, 0, 1}));
		checkComplex(new Complex[] {new Complex(-0.3125, 0.46351240544347894), new Complex(-0.3125, -0.46351240544347894)}, Polynomial.findRoots(new double[] {3.2, 2, 1}));
	}

	private static void checkComplex(Complex[] expected, Complex[] actual) {
		Assert.assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			Complex e = expected[i];
			Complex a = actual[i];
			Assert.assertEquals("Real " + i, e.getReal(), a.getReal(), ABS_TOL);
			Assert.assertEquals("Imag " + i, e.getImaginary(), a.getImaginary(), ABS_TOL);
		}
	}
}
