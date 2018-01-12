/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;

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
	 * @param tolerance
	 *            Not currently used.
	 */
	// TODO Add in the ability to use the tolerance parameter
	public LeastSquares(@SuppressWarnings("unused") double tolerance) {

	}

	private double alpha(int k, int l) {
		Dataset value;
		if (coords == null) {
			value = DatasetFactory.createFromObject(function.partialDeriv(params.get(k)));
		} else {
			value = DatasetUtils.convertToDataset(function.calculatePartialDerivativeValues(params.get(k), coords));
		}
		if (k == l) {
			value.imultiply(value);
		} else if (coords == null) {
			value.imultiply(function.partialDeriv(params.get(l)));
		} else {
			value.imultiply(function.calculatePartialDerivativeValues(params.get(l), coords));
		}
		if (weight != null) {
			value.imultiply(weight);
		}

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

	private double beta(int k, final boolean useResiduals) {
		// do a numerical differential for the time being.
		final double[] params = getParameterValues();
		final double v = params[k];

		double dv = (v == 0 ? 1 : v) * PERT;
		params[k] = v - dv;
		double chimin = useResiduals ? calculateResidual(params) : calculateFunction(params);
		params[k] = v + dv;
		double chimax = useResiduals ? calculateResidual(params) : calculateFunction(params);
		params[k] = v;
		setParameterValues(params);

		return -0.5 * (chimax - chimin) / (2 * PERT);
	}

	private Matrix evaluateChiSquared(final boolean useResiduals) {
		Matrix mat = new Matrix(n, 1);
		for (int i = 0; i < n; i++) {
			mat.set(i, 0, beta(i, useResiduals));
		}
		return mat;
	}

	private double[] solveDa(double lambda, final boolean useResiduals) {
		Matrix A = evaluateMatrix(lambda);
		Matrix B = evaluateChiSquared(useResiduals);

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
		optimize(0.001, true);
	}

	@Override
	void internalMinimax(boolean minimize) throws Exception {
		if (!minimize) {
			throw new IllegalArgumentException("Maximize not supported");
		}

		// choose a value for lambda
		optimize(0.001, false);
	}

	public void optimize(double lambda, final boolean useResiduals) {
		double[] pvalues = getParameterValues();
		// first calculate the quality of the fit.
		double eval = useResiduals ? calculateResidual(pvalues) : calculateFunction(pvalues);

		double[] testParams = new double[n];

		for (int j = 0; j < 1; j++) {

			// solve the least squares calculation
			double[] dParams = solveDa(lambda, useResiduals);

			// evaluate the new position
			for (int i = 0; i < n; i++) {
				testParams[i] = pvalues[i] + dParams[i];
			}

			double testEval = useResiduals ? calculateResidual(testParams) : calculateFunction(testParams);

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
