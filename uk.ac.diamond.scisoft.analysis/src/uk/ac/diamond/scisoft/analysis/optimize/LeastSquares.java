/*-
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.optimize;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import Jama.Matrix;

/**
 * Basic least squares optimiser, this currently works for polynomial
 * functions, and not really for more complex functions.
 */
public class LeastSquares extends AbstractOptimizer {

	/**
	 * Setup the logging facilities
	 */
//	private static final Logger logger = LoggerFactory.getLogger(LeastSquares.class);

	/**
	 * Base constructor
	 * 
	 * @param tolerence
	 *            Not currently used.
	 */
	// TODO Add in the ability to use the tolerance parameter
	public LeastSquares(@SuppressWarnings("unused") double tolerence) {

	}

	private double alpha(int k, int l) {
		AbstractDataset value = DatasetUtils.convertToAbstractDataset(function.calculatePartialDerivativeValues(params.get(k), coords[0]));
		if (k == l) {
			value.imultiply(value);
		} else {
			value.imultiply(function.calculatePartialDerivativeValues(params.get(l), coords[0]));
		}
		if (weight != null)
			value.imultiply(weight);

		return ((Number) value.sum()).doubleValue();
	}

	private double alphaPrime(int k, int l, double lambda) {
		double a = alpha(k, l);
		return k == l ? a  * (1 + lambda) : a;
	}

	private Matrix evaluateMatrix(double lambda) {
		Matrix mat = new Matrix(n, n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				mat.set(i, j, alphaPrime(i, j, lambda));
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				mat.set(i, j, mat.get(j, i));
			}
		}
		return mat;
	}

	private final static double PERT = 1e-4;

	private double beta(int k) {
		// do a numerical differential for the time being.
		final double[] params = getParameterValues();
		final double v = params[k];

		double dv = (v == 0 ? 1 : v) * PERT;
		params[k] = v - dv;
		double chimin = calculateResidual(params);
		params[k] = v + dv;
		double chimax = calculateResidual(params);
		params[k] = v;
		setParameterValues(params);

		return -0.5 * (chimax - chimin) / (2 * PERT);
	}

	private Matrix evaluateChiSquared() {
		Matrix mat = new Matrix(n, 1);
		for (int i = 0; i < n; i++) {
			mat.set(i, 0, beta(i));
		}
		return mat;
	}

	private double[] solveDa(double lambda) {
		Matrix A = evaluateMatrix(lambda);
		Matrix B = evaluateChiSquared();

		Matrix mat = A.inverse();
		mat = mat.times(B);
		double[] result = new double[n];
		for (int i = 0; i < result.length; i++) {
			result[i] = mat.get(i, 0);
		}

		return result;
	}

	@Override
	void internalOptimize() {
		// choose a value for lambda
		optimize(0.001);
	}

	public void optimize(double lambda) {
		double[] pvalues = getParameterValues();
		// first calculate the quality of the fit.
		double eval = calculateResidual(pvalues);

		double[] testParams = new double[n];

		for (int j = 0; j < 1; j++) {

			// solve the least squares calculation
			double[] dParams = solveDa(lambda);

			// evaluate the new position
			for (int i = 0; i < n; i++) {
				testParams[i] = pvalues[i] + dParams[i];
			}

			double testEval = calculateResidual(testParams);

			//logger.debug(testEval + "," + eval + "," + lambda + "["
			//		+ dParams[0] + "," + dParams[1] + "," + dParams[2] + "]");

			if (testEval >= eval) {
				lambda *= 10;
			} else {
				lambda /= 10;
				eval = testEval;
				for (int i = 0; i < n; i++) {
					pvalues[i] = testParams[i];
				}
			}
		}

		setParameterValues(pvalues);
	}
}
