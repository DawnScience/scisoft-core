/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting;


import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.NonSquareMatrixException;
import org.apache.commons.math.optimization.OptimizationException;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

/**
 * Class to fit an ND Gaussian
 */
public class Gaussian {

	private DoubleDataset dataset; // target for objective function
	private double[] weights;
	private double[] startPoint;

	public Gaussian() {
		
	}

	
	/**
	 * @param dataset The dataset to set.
	 */
	public void setDataset(DoubleDataset dataset) {
		this.dataset = dataset;
	}


	/**
	 * @return Returns the dataset.
	 */
	public DoubleDataset getDataset() {
		return dataset;
	}

	public void optimise() {
		LevenbergMarquardtOptimizer lmopt = new LevenbergMarquardtOptimizer();
		ObjectiveFunction f = new ObjectiveFunction(2);

		try {
			lmopt.optimize(f, dataset.getData(), weights, startPoint);
		} catch (OptimizationException e) {
			e.printStackTrace();
		} catch (FunctionEvaluationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * class for objective function i.e. Gaussian
	 */
	private static class ObjectiveFunction implements DifferentiableMultivariateVectorialFunction {
		int rank;
		public ObjectiveFunction(int ndims) {
			rank = ndims;
		}

		@Override
		public double[] value(double[] parameters) throws FunctionEvaluationException, IllegalArgumentException {
			Array2DRowRealMatrix covar = (Array2DRowRealMatrix) MatrixUtils.createRealMatrix(rank, rank);
			int n = rank;
			double norm = parameters[n];
			n++;
			for (int i = 0; i < rank; i++) {
				covar.setEntry(i, i, parameters[n]);
				n++;
			}
			for (int i = 0; i < rank; i++) {
				double diagi = Math.sqrt(covar.getEntry(i, i));
				for (int j = i + 1; j < rank; j++) {
					double diag = Math.sqrt(covar.getEntry(j, j))*diagi;
					double el = diag*parameters[n];
					covar.setEntry(i, j, el);
					covar.setEntry(j, i, el);
					n++;
				}
			}
			LUDecomposition decomp = null;
			try {
				decomp = new LUDecompositionImpl(covar);
			} catch (NonSquareMatrixException e) {
//				logger.error("Non-square covariance matrix");
				throw new IllegalArgumentException("Non-square covariance matrix");
			}

			norm /= Math.sqrt(Math.pow(2.*Math.PI, rank) * decomp.getDeterminant());

			Array2DRowRealMatrix invcov; // inverse of covariance matrix
			invcov = (Array2DRowRealMatrix) decomp.getSolver().getInverse();

			double residual = 0;

			double[] r = null;
			residual += gaussian(r, parameters, norm, invcov);
			return new double[] {residual};
		}

		private static double gaussian(double[] values, double[] pos, double norm, Array2DRowRealMatrix invcov) {
			double[] v = values.clone();
			for (int i = 0; i < v.length; i++)
				v[i] -= pos[i];

			double[] u = invcov.operate(v);
			double arg = 0;
			for (int i = 0; i < v.length; i++)
				arg += u[i] * v[i];

			double ex = Math.exp(-0.5 * arg);
			return norm * ex;
		}

		@Override
		public MultivariateMatrixFunction jacobian() {
			return null;
		}
		
		
	}
}