/*
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

package uk.ac.diamond.scisoft.analysis.fitting;


import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.NonSquareMatrixException;
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
		} catch (Exception e) {
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
