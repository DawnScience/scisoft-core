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

	private static final double ABS_TOL = 1e-12;

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
	public void testCheckRoot() {
		checkRoot(new Complex(-2), 1, 0, -4);
		checkRoot(new Complex(2), 1, 0, -4);
		checkRoot(new Complex(-0.3125, 0.46351240544347894), 3.2, 2, 1);
	}

	@Test
	public void testFindRoots() {
		checkComplex(new Complex[] {new Complex(-2), new Complex(2)}, Polynomial.findRoots(1, 0, -4));
		checkComplex(new Complex[] {new Complex(-1), new Complex(1)}, Polynomial.findRoots(1, 0, -1));
		checkComplex(new Complex[] {new Complex(-1), new Complex(-1)}, Polynomial.findRoots(1, 2, 1));
		checkComplex(new Complex[] {new Complex(0, 2), new Complex(0, -2)}, Polynomial.findRoots(1, 0, 4));
		checkComplex(new Complex[] {new Complex(-0.3125, 0.46351240544347894), new Complex(-0.3125, -0.46351240544347894)}, Polynomial.findRoots(3.2, 2, 1));

		double[] rhCoeffs = new double[] {
			-1.0021380351125523e+62, -1.3325683964593362e+63, -4.3736356299729265e+62, 1.5174319075477495e+62, 2.7616709058592895e+61, -5.7735973730761707e+60, -4.2781613377152188e+59, 7.2891988413828689e+58, -1.8014489076363173e+56
		};
		Complex[] rhRoots = new Complex[] {
				new Complex(-12.951379691972974, 0), new Complex(-0.45888486182687299, 0),
				new Complex(-0.1797091693878104, 8.1196199250557562e-09), new Complex(-0.1797091693878104, -8.1196199250557562e-09),
				new Complex(0.0025095961400314073, 0), new Complex(0.12677840909295585, 0),
				new Complex(0.17157045931736753, 4.7150695762880267e-09), new Complex(0.17157045931736753, -4.7150695762880267e-09),
		};
		for (Complex r : rhRoots)
			checkRoot(r, rhCoeffs);

		Complex[] roots = Polynomial.findRoots(rhCoeffs);
		for (Complex r : roots)
			checkRoot(r, rhCoeffs);

		checkComplex(rhRoots, roots, 1e-8);
	}

	private static void checkRoot(Complex root, double... coeffs) {
		double[] reals= new double[coeffs.length];
		double[] imags= new double[coeffs.length];
		Complex z = new Complex(1, 0);
		for (int i = coeffs.length - 1; i >= 0; i--) {
			double c = coeffs[i];
			reals[i] = c * z.getReal();
			imags[i] = c * z.getImaginary();
			z = z.multiply(root);
		}

		double tol = 1e-7 * findAbsMax(coeffs);
		Assert.assertEquals("R " + root + " @t " + tol, 0, sumC(reals), tol);
		Assert.assertEquals("I " + root, 0, sumC(imags), tol);
	}

	private static double sumC(double[] values) {
		double sum = 0;
		double c = 0;
		for (double v : values) {
			double t = v - c;
			double u = sum + t;
			c = (u - sum) - t;
			sum = u;
		}
		return sum;
	}

	private static double findAbsMax(double[] values) {
		double max = Double.NEGATIVE_INFINITY;
		for (double v : values) {
			max = Math.max(max, Math.abs(v));
		}
		return max;
	}

	private static void checkComplex(Complex[] expected, Complex[] actual) {
		checkComplex(expected, actual, ABS_TOL);
	}

	private static void checkComplex(Complex[] expected, Complex[] actual, double err) {
		Assert.assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			Complex e = expected[i];
			Complex a = actual[i];
			Assert.assertEquals("Real " + i, e.getReal(), a.getReal(), err);
			Assert.assertEquals("Imag " + i, e.getImaginary(), a.getImaginary(), err);
		}
	}
}
