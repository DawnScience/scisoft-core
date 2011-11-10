/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.utils;

import gda.analysis.DataSet;
import gda.analysis.functions.IFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Jama.Matrix;

/**
 * Basic least squares optimiser, this currently works for polynomial
 * functions, and not really for more complex functions.
 */
public class LeastSquares implements IOptimizer {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(LeastSquares.class);

	/**
	 * Base constructor
	 * 
	 * @param tolerence
	 *            Not currently used.
	 */
	// TODO Add in the ability to use the tolerance parameter
	public LeastSquares(@SuppressWarnings("unused") double tolerence) {

	}

	private double chiSquared(double[] params, DataSet xAxis, DataSet yAxis,
			DataSet sigma, IFunction Function) {

		double result = 0.0;

		double[] oldParams = Function.getParameterValues();

		Function.setParameterValues(params);

		for (int i = 0; i < xAxis.getSize(); i++) {
			result += Math
					.pow(((yAxis.get(i) - Function.val(xAxis.get(i))) / sigma
							.get(i)), 2);
		}

		Function.setParameterValues(oldParams);

		return result;
	}

	private double alpha(int k, int l, DataSet xAxis, DataSet sigma,
			IFunction func) {
		double sum = 0.0;
		for (int i = 0; i < xAxis.getDimensions()[0]; i++) {
			double value = 1.0 / Math.pow(sigma.get(i), 2);
			value *= func.partialDeriv(k, xAxis.get(i));
			value *= func.partialDeriv(l, xAxis.get(i));
			sum += value;
		}
		return sum;
	}

	private double alphaPrime(int k, int l, DataSet xAxis, DataSet sigma,
			IFunction func, double lambda) {
		if (k == l) {
			return (alpha(k, l, xAxis, sigma, func)) * (1 + lambda);
		}

		return (alpha(k, l, xAxis, sigma, func));

	}

	private Matrix evaluateMatrix(DataSet xAxis, DataSet sigma,
			IFunction func, double lambda) {
		int nparams = func.getNoOfParameters();
		Matrix mat = new Matrix(nparams, nparams);
		for (int i = 0; i < nparams; i++) {
			for (int j = 0; j < nparams; j++) {
				mat.set(i, j, alphaPrime(i, j, xAxis, sigma, func, lambda));
			}
		}
		return mat;
	}

	private double beta(int k, DataSet xAxis, DataSet yAxis, DataSet sigma,
			IFunction func) {
		// do a numerical differential for the time being.
		double[] params = func.getParameterValues();
		double offset = 0.001;
		params[k] -= offset;
		double chimin = chiSquared(params, xAxis, yAxis, sigma, func);
		params[k] += 2 * offset;
		double chimax = chiSquared(params, xAxis, yAxis, sigma, func);

		return -0.5 * (chimax - chimin) / (2 * offset);

	}

	private Matrix evaluateChiSquared(DataSet xAxis, DataSet yAxis,
			DataSet sigma, IFunction func) {
		int nparams = func.getNoOfParameters();
		Matrix mat = new Matrix(nparams, 1);
		for (int i = 0; i < nparams; i++) {
			mat.set(i, 0, beta(i, xAxis, yAxis, sigma, func));
		}
		return mat;
	}

	private double[] solveDa(DataSet xAxis, DataSet yAxis, DataSet sigma,
			IFunction func, double lambda) {
		Matrix A = evaluateMatrix(xAxis, sigma, func, lambda);
		Matrix B = evaluateChiSquared(xAxis, yAxis, sigma, func);

		Matrix mat = A.inverse();
		mat = mat.times(B);
		double[] result = new double[func.getNoOfParameters()];
		for (int i = 0; i < result.length; i++) {
			result[i] = mat.get(i, 0);
		}

		return result;
	}

	@Override
	public void Optimize(DataSet[] coords, DataSet yAxis, IFunction function) {

		logger.debug("Start of Optimization");

		// first set sigma, this is artificial at first but should be sorted
		// out
		// once the basics work.
		DataSet sigma = new DataSet(coords[0].getDimensions()[0]);
		sigma = DatasetMaths.add(sigma, 1);

		// get the parameters
		double[] params = function.getParameterValues();

		// first calculate the quality of the fit.
		double eval = chiSquared(params, coords[0], yAxis, sigma, function);

		// choose a value for lambda
		double lambda = 0.001;

		double[] testParams = new double[params.length];

		for (int j = 0; j < 1; j++) {

			// solve the least squares calculation
			double[] dParams = solveDa(coords[0], yAxis, sigma, function, lambda);

			// evaluate the new position
			for (int i = 0; i < params.length; i++) {
				testParams[i] = params[i] + dParams[i];
			}

			double testEval = chiSquared(testParams, coords[0], yAxis, sigma,
					function);

			//logger.debug(testEval + "," + eval + "," + lambda + "["
			//		+ dParams[0] + "," + dParams[1] + "," + dParams[2] + "]");

			if (testEval >= eval) {
				lambda *= 10;
			} else {
				lambda /= 10;
				eval = testEval;
				for (int i = 0; i < params.length; i++) {
					params[i] = testParams[i];
				}
			}

		}

		function.setParameterValues(params);

	}

}
