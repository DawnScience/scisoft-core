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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.NonSquareMatrixException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A ND Gaussian function
 * 
 * The parameters are mean peak position coordinates (N), volume (1), diagonal elements of covariance matrix (N)
 * and normalized upper triangle elements of covariance matrix (N*(N-1)/2). The last set of parameters
 * are normalized by the diagonal elements.
 */
public class GaussianND extends AFunction {
	private static String cname = "GaussianND";
	private int rank;
	private double[] pos = null;

	/**
	 * Setup the logging facilities
	 */
	private static transient final Logger logger = LoggerFactory.getLogger(GaussianND.class);

	public GaussianND() {
		super(new double[]{0,0,0});
		name = cname;
	}

	public GaussianND(IParameter[] params) {
		super(params);
		name = cname;
	}

	/**
	 * Constructor which takes the N + 1 + N + N(N-1)/2 properties required, which are volume, elements of the mean vector,
	 * diagonal elements and upper triangle elements of the covariance matrix (as this is symmetric)
	 * @param params
	 */
	public GaussianND(double... params) {
		super(params);
		int nparams = params.length;

		// check if correct number of parameters given
		int guess = -1;
		for (rank = 0; guess < nparams; ) {
			guess = 1 + (rank * (rank + 3)) / 2;
		}
		if (guess != nparams) {
			logger.error("Given number of parameters {} is not equal to {}", nparams, guess);
			throw new IllegalArgumentException("Given number of parameters " + nparams + " is not equal to" + guess);
		}

		// check triangle values
		int n = 2*rank + 1;
		for (int i = 0; i < nparams; i++) {
			double tri = getParameter(n).getValue();
			if (Math.abs(tri) > 1) {
				logger.error("Parameter {} ({}) is outside valid range [-1,1]", i, tri);
				throw new IllegalArgumentException("Parameter " + i + " (" + tri + ") is outside valid range [-1,1]");
			}
		}
		pos = new double[rank];
	}

	/**
	 * Create a multi-dimensional Gaussian function
	 * @param maxVol maximum "volume"
	 * @param minPeakPosition array containing minimum peak position
	 * @param maxPeakPosition array containing maximum peak position
	 * @param maxSigma maximum magnitude for any element in covariance matrix
	 */
	public GaussianND(double maxVol, double[] minPeakPosition, double[] maxPeakPosition, double maxSigma) {
		super(1 + (minPeakPosition.length*(minPeakPosition.length+3))/2);

		rank = minPeakPosition.length;

		if (maxPeakPosition.length != rank) {
			logger.error("Two vectors are not of same length");
			throw new IllegalArgumentException("Two vectors are not of same length");
		}

		int n = 0;
		for (int i = 0; i < rank; i++) {
			getParameter(n).setLowerLimit(minPeakPosition[i]);
			getParameter(n).setUpperLimit(maxPeakPosition[i]);
			getParameter(n).setValue((minPeakPosition[i] + maxPeakPosition[i]) / 2.0);
			n++;
		}

		getParameter(n).setLowerLimit(0);
		getParameter(n).setUpperLimit(maxVol);
		getParameter(n).setValue(maxVol / 2.0);
		n++;

		double sigmasq = maxSigma * maxSigma;
		for (int i = 0; i < rank; i++) {
			getParameter(n).setLowerLimit(0);
			getParameter(n).setUpperLimit(sigmasq);
			getParameter(n).setValue(sigmasq/100.);
			n++;
		}
		for (int i = 0; i < rank; i++) {
			for (int j = i + 1; j < rank; j++) {
				getParameter(n).setLowerLimit(-1);
				getParameter(n).setUpperLimit(1);
				getParameter(n).setValue(0);
				n++;
			}
		}

		pos = new double[rank];
	}

	/**
	 * Setting peak position
	 * @param pos
	 */
	public void setPeakPosition(double[] pos) {
		if (pos.length != rank) {
			logger.error("Peak position vector has wrong length");
			throw new IllegalArgumentException("Peak position vector has wrong length");
		}
		for (int i = 0; i < rank; i++) {
			getParameter(i).setValue(pos[i]);
		}
	}

	/**
	 * Setting volume of Gaussian (the integrated value)
	 * @param volume
	 */
	public void setVolume(double volume) {
		getParameter(rank).setValue(volume);
	}

	/**
	 * @return maximum value
	 */
	public double getPeakValue() {
		if (areParametersDirty())
			calcCachedParameters();
		return norm;
	}

	private Array2DRowRealMatrix invcov; // inverse of covariance matrix
	private double norm;
	private void calcCachedParameters() {
		int n = 0;
		for (int i = 0; i < rank; i++) {
			pos[i] = getParameterValue(n);
			n++;
		}
//		logger.info("New pos at {}", pos);
		norm = getParameterValue(n);
		n++;
		Array2DRowRealMatrix covar = (Array2DRowRealMatrix) MatrixUtils.createRealMatrix(rank, rank);
		for (int i = 0; i < rank; i++) {
			covar.setEntry(i, i, getParameterValue(n));
			n++;
		}
		for (int i = 0; i < rank; i++) {
			double diagi = Math.sqrt(covar.getEntry(i, i));
			for (int j = i + 1; j < rank; j++) {
				double diag = Math.sqrt(covar.getEntry(j, j))*diagi;
				double el = diag*getParameterValue(n);
				covar.setEntry(i, j, el);
				covar.setEntry(j, i, el);
				n++;
			}
		}
//		logger.info("New cov {}", covar);
		LUDecomposition decomp = null;
		try {
			decomp = new LUDecompositionImpl(covar);
		} catch (NonSquareMatrixException e) {
			logger.error("Non-square covariance matrix");
			throw new IllegalArgumentException("Non-square covariance matrix");
		}

		invcov = (Array2DRowRealMatrix) decomp.getSolver().getInverse();
//		logger.info("Inverse covariance matrix is {}", invcov);
		norm /= Math.sqrt(Math.pow(2.*Math.PI, rank) * decomp.getDeterminant());
//		logger.info("Normalization factor is {}", norm);
		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

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

}
