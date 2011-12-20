/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.Arrays;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.MaxIterationsExceededException;
import org.apache.commons.math.analysis.solvers.BrentSolver;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.Random;

public class EllipseFitterTest {

	@Test
	public void testOrthoDist() {

		AngleDerivativeFunction angleDerivative = new AngleDerivativeFunction();
		BrentSolver solver = new BrentSolver(BrentSolver.DEFAULT_ABSOLUTE_ACCURACY);
		double a = 10.2;
		double b = 3.1;
		final double twopi = 2*Math.PI;
		double alpha = twopi*10./360; // 0 to 2 pi
		angleDerivative.setRadii(a, b);
		angleDerivative.setAngle(alpha);

//		final double ca = 0;
//		final double cb = b-0.5;
		final double Xc = -5.; // Math.cos(alpha)*ca + Math.sin(alpha)*cb;
		final double Yc = 5.5; //Math.sin(alpha)*ca + Math.cos(alpha)*cb;

		angleDerivative.setCoordinate(Xc, Yc);
		try {
			// find quadrant to use
			double p = Math.atan2(Yc, Xc);
			if (p < 0)
				p += twopi;
			p -= alpha;
			final double end;
			final double halfpi = 0.5*Math.PI;
			p /= halfpi;
			end = Math.ceil(p)*halfpi;
			final double angle = solver.solve(BrentSolver.DEFAULT_MAXIMUM_ITERATIONS, angleDerivative, end-halfpi, end);
//			final double cos = Math.cos(angle);
//			final double sin = Math.sin(angle);

			Assert.assertEquals("Angle found is not close enough", 1.930, angle, 0.001);
//			double dx = a*cos + Xc;
//			double dy = b*sin + Yc;
//
//			System.out.println("Bracket angle = " + Math.ceil(p));
//			System.out.println("Delta angle = " + 180.*angle/Math.PI);
//			System.out.println(dx + ", " + dy);
		} catch (MaxIterationsExceededException e) {
			// TODO
			System.err.println(e);
		} catch (FunctionEvaluationException e) {
			// TODO
			System.err.println(e);
		}

	}

	@Test
	public void testQuickEllipse() {
		double[] original = new double[] { 344.2, 243.2, Math.PI*0.23, 0.2, -12.3};

		DoubleDataset theta;
//		theta = new DoubleDataset(new double[] {0.1, 0.2, 0.25, 0.33, 0.35, 0.37, 0.43, });
		Random.seed(1277);
		theta = Random.rand(0, 2*Math.PI, 10);

		AbstractDataset[] coords = EllipseFitter.generateCoordinates(theta, original);

		AbstractDataset x;
		AbstractDataset y;
//		x = new DoubleDataset(new double[] {242.34, 188.08, 300.04, 188.90, 300.97, 103.80, 157.67, 141.81, 302.64, 266.58});
//		y = new DoubleDataset(new double[] {-262.478, 147.192, -107.673, 136.293, -118.735, 217.387, 166.996, 192.521, -55.201, 17.826});
		x = Maths.add(coords[0], Random.randn(0.0, 10.2, theta.getShape()));
		y = Maths.add(coords[1], Random.randn(0.0, 10.2, theta.getShape()));
		System.err.println(x);
		System.err.println(y);

		EllipseFitter fitter = new EllipseFitter();

		fitter.algebraicFit(x, y);
		double[] result = fitter.getParameters();

		double[] tols = new double[] {8e-2, 5e-2, 1e-1, 6, -2e-1};
		for (int i = 0; i < original.length; i++) {
			Assert.assertEquals("Algebraic fit: " + i, original[i], result[i], original[i]*tols[i]);
		}
		System.err.println(Arrays.toString(original));
		System.err.println(Arrays.toString(result));

		fitter.geometricFit(x, y, result);
		result = fitter.getParameters();
		System.err.println(Arrays.toString(result));
		for (int i = 0; i < original.length; i++) {
			Assert.assertEquals("Geometric fit: " + i, original[i], result[i], original[i]*tols[i]);
		}
	}

}

