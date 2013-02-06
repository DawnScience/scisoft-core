/*
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

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.Arrays;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.linear.ArrayRealVector;
import org.apache.commons.math.optimization.VectorialPointValuePair;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.LinearAlgebra;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/**
 * Fit a circle whose geometric parameters are
 *  radius, centre coordinates
 */
public class CircleFitter {
	/**
	 * Setup the logging facilities
	 */
	private static transient final Logger logger = LoggerFactory.getLogger(CircleFitter.class);

	private double[] parameters;
	private final static int PARAMETERS = 3;

	/**
	 * This function returns the coordinates (interleaved) for the points specified by the
	 * geometric parameters and an array of angles
	 */
	class CircleCoordinatesFunction implements DifferentiableMultivariateVectorialFunction {
		private AbstractDataset X;
		private AbstractDataset Y;
		private DoubleDataset v;
		private double[][] j;
		private int n; // number of points
		private int m; // number of equations
		private double[] ca;
		private double[] sa;

		public CircleCoordinatesFunction(AbstractDataset x, AbstractDataset y) {
			setPoints(x, y);
		}

		/**
		 * Set points used in fit
		 * @param x
		 * @param y
		 */
		public void setPoints(AbstractDataset x, AbstractDataset y) {
			X = x;
			Y = y;
			n = X.getSize();
			m = 2*n;
			v = new DoubleDataset(m);
			j = new double[m][PARAMETERS+n];
			for (int i = 0; i < m; i++) {
				j[i][1] = 1;
				i++;
				j[i][2] = 1;
			}
			ca = new double[n];
			sa = new double[n];
		}

		/**
		 * @return array of interleaved coordinates
		 */
		public double[] getTarget() {
			double[] target = new double[m];
			final IndexIterator itx = X.getIterator();
			final IndexIterator ity = Y.getIterator();
			int i = 0;
			while (itx.hasNext() && ity.hasNext()) {
				target[i++] = X.getElementDoubleAbs(itx.index);
				target[i++] = Y.getElementDoubleAbs(ity.index);
			}
			return target;
		}

		/**
		 * @return default weights of 1
		 */
		public double[] getWeight() {
			double[] weight = new double[m];
			Arrays.fill(weight, 1.0);
			return weight;
		}

		/**
		 * Calculate angles of closest points on ellipse to targets
		 * @param initParameters geometric parameters
		 * @return array of all initial parameters
		 */
		public double[] calcAllInitValues(double[] initParameters) {
			double[] init = new double[n+PARAMETERS];
			for (int i = 0; i < initParameters.length; i++) {
				init[i] = initParameters[i];
			}
			final double x = initParameters[1];
			final double y = initParameters[2];

			// work out the angle values for the closest points on circle
			final IndexIterator itx = X.getIterator();
			final IndexIterator ity = Y.getIterator();
			int i = PARAMETERS;
			while (itx.hasNext() && ity.hasNext()) {
				final double Xc = X.getElementDoubleAbs(itx.index) - x;
				final double Yc = Y.getElementDoubleAbs(ity.index) - y;

				init[i++] = Math.atan2(Yc, Xc);
			}
			return init;
		}

		@Override
		public double[] value(double[] p) throws FunctionEvaluationException, IllegalArgumentException {
			final double[] values = v.getData();
			final double r = p[0];
			final double x = p[1];
			final double y = p[2];

			for (int i = 0; i < n; i++) {
				final double t = p[i+PARAMETERS];
				final double cost = Math.cos(t);
				final double sint = Math.sin(t);
				ca[i] = cost;
				sa[i] = sint;
				final int ti = 2*i;
				values[ti] = x + r*cost;
				values[ti+1] = y + r*sint;
			}

			return values;
		}

		private void calculateJacobian(double[] p) {
			final double r = p[0];

			for (int i = 0; i < n; i++) {
				final double ct = ca[i];
				final double st = sa[i];

				final int ti = 2*i;
				final int tj = ti + 1;
				j[ti][0] = ct;
				j[ti][PARAMETERS+i] = - r*st;

				j[tj][0] = st;
				j[tj][PARAMETERS+i] = r*ct;
			}
		}

		@Override
		public MultivariateMatrixFunction jacobian() {
			return new MultivariateMatrixFunction() {
				@Override
				public double[][] value(double[] p) throws FunctionEvaluationException, IllegalArgumentException {
					calculateJacobian(p);
					return j;
				}
			};
		}
	}

	public CircleFitter() {
		parameters = new double[PARAMETERS];
	}

	public double[] getParameters() {
		return parameters;
	}

	/**
	 * Fit points given by x, y datasets to a circle. If no initial parameters are given, then
	 * an algebraic fit is performed then a non-linear least squares fitting routine is used to
	 * provide the best geometric fit.
	 * @param x
	 * @param y
	 * @param init parameters (can be null)
	 */
	public void geometricFit(AbstractDataset x, AbstractDataset y, double[] init) {

		if (x.getSize() < PARAMETERS || y.getSize() < PARAMETERS) {
			throw new IllegalArgumentException("Need " + PARAMETERS + " or more points");
		}

		if (init == null)
			init = quickfit(x, y);
		else if (init.length < PARAMETERS)
			throw new IllegalArgumentException("Need " + PARAMETERS + " parameters");

		CircleCoordinatesFunction f = new CircleCoordinatesFunction(x, y);
		LevenbergMarquardtOptimizer opt = new LevenbergMarquardtOptimizer();

		try {
			VectorialPointValuePair result = opt.optimize(f, f.getTarget(), f.getWeight(), f.calcAllInitValues(init));

			double[] point = result.getPointRef(); 
			for (int i = 0; i < PARAMETERS; i++)
				parameters[i] = point[i];

			logger.info("Circle fit: rms = {}, x^2 = {}", opt.getRMS(), opt.getChiSquare());
		} catch (FunctionEvaluationException e) {
			// cannot happen
		} catch (IllegalArgumentException e) {
			// should not happen!
		} catch (ConvergenceException e) {
			throw new IllegalArgumentException("Problem with optimizer converging");
		}
	}

	/**
	 * Fit points given by x, y datasets to an ellipse. 
	 * @param x
	 * @param y
	 */
	public void algebraicFit(AbstractDataset x, AbstractDataset y) {
		if (x.getSize() < PARAMETERS || y.getSize() < PARAMETERS) {
			throw new IllegalArgumentException("Need " + PARAMETERS + " or more points");
		}

		double[] quick = quickfit(x, y);

		for (int i = 0; i < PARAMETERS; i++)
			parameters[i] = quick[i];
	}

	/**
	 * least-squares using algebraic cost function
	 * <p>
	 * This uses the Pratt method as mentioned in "Error analysis for circle fitting algorithms"
	 * by A. Al-Sharadqah and N. Chernov, Electronic Journal of Statistics, v3, pp886-991 (2009)
	 * <p>
	 * @param x
	 * @param y
	 * @return geometric parameters
	 */
	private static double[] quickfit(AbstractDataset x, AbstractDataset y) {
		final AbstractDataset z = Maths.square(x).iadd(Maths.square(y));

		Matrix S = new Matrix(4, 4);
		S.set(0, 0, LinearAlgebra.dotProduct(z, z).getDouble());
		S.set(0, 1, LinearAlgebra.dotProduct(z, x).getDouble());
		S.set(0, 2, LinearAlgebra.dotProduct(z, y).getDouble());
		S.set(0, 3, ((Number) z.sum()).doubleValue());

		S.set(1, 0, S.get(0, 1));
		S.set(1, 1, LinearAlgebra.dotProduct(x, x).getDouble());
		S.set(1, 2, LinearAlgebra.dotProduct(x, y).getDouble());
		S.set(1, 3, ((Number) x.sum()).doubleValue());

		S.set(2, 0, S.get(0, 2));
		S.set(2, 1, S.get(1, 2));
		S.set(2, 2, LinearAlgebra.dotProduct(y, y).getDouble());
		S.set(2, 3, ((Number) y.sum()).doubleValue());

		S.set(3, 0, S.get(0, 3));
		S.set(3, 1, S.get(1, 3));
		S.set(3, 2, S.get(2, 3));
		S.set(3, 3, x.getSize());

		Matrix C = new Matrix(new double[] {0,0,0,-2, 0,1,0,0, 0,0,1,0, -2,0,0,0}, 4);
		Matrix M = S.solve(C);
//		System.err.println("M " + Arrays.toString(M.getRowPackedCopy()));
		EigenvalueDecomposition decomp = M.eig();
		double[] ev = decomp.getRealEigenvalues();
//		System.err.println("Eigenvalues: " + Arrays.toString(ev));

		// find minimal positive eigenvalue
//		double emin = Double.POSITIVE_INFINITY;
//		for (double vi : ev) {
//			if (vi > 0 && vi < emin)
//				emin = vi;
//		}
		int i = 0;
		for (; i < 4; i++) {
//			if (ev[i] == emin)
//				break;
			if (ev[i] > 0)
				break;
		}

		double[][] mv = decomp.getV().getArray();
//		System.err.println("V " + Arrays.toString(decomp.getV().getRowPackedCopy()));
		ArrayRealVector v = new ArrayRealVector(new double[] {mv[0][i], mv[1][i], mv[2][i], mv[3][i]});

		ev = v.getDataRef();
		final double ca = ev[0];
		final double cd = ev[1];
		final double ce = ev[2];
		final double cf = ev[3];
		final double disc = cd*cd + ce*ce - 4.*ca*cf;
//		System.err.println(String.format("Algebraic: %g, %g, %g, %g (%g)\n", ca, cd, ce, cf, disc));
		if (disc < 0) {
			throw new IllegalArgumentException("Solution is not a circle");
		}

		double[] qparameters = new double[PARAMETERS];
		double f = 0.5/ca;
		qparameters[0] = f*Math.sqrt(disc);
		qparameters[1] = -f*cd;
		qparameters[2] = -f*ce;

		return qparameters;
	}

	/**
	 * Compute coordinates from an array of angles
	 * @param angles
	 * @param geometricParameters
	 * @return x and y datasets
	 */
	public static AbstractDataset[] generateCoordinates(AbstractDataset angles, final double[] geometricParameters) {
		if (geometricParameters.length != PARAMETERS)
			throw new IllegalArgumentException("Need " + PARAMETERS + " parameters");

		AbstractDataset[] coords = new AbstractDataset[2];

		DoubleDataset x = new DoubleDataset(angles.getShape());
		DoubleDataset y = new DoubleDataset(angles.getShape());
		coords[0] = x;
		coords[1] = y;

		final double r = geometricParameters[0];
		final double cx = geometricParameters[1];
		final double cy = geometricParameters[2];
		final IndexIterator it = angles.getIterator();

		int i = 0;
		while (it.hasNext()) {
			final double t = angles.getElementDoubleAbs(it.index);
			x.setAbs(i, cx + r*Math.cos(t));
			y.setAbs(i, cy + r*Math.sin(t));
			i++;
		}
		return coords;
	}

}

