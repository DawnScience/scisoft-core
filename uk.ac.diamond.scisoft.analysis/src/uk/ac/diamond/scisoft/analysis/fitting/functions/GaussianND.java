/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.DoubleDataset;
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
	private static final long serialVersionUID = 512262312319470425L;

	private static String NAME = "GaussianND";
	private static String DESC = "An N-dimensional Gaussian function."
			+ "\nThe parameters are mean peak position coordinates (N), volume (1),"
			+ "\ndiagonal elements of covariance matrix (N) and normalized upper triangle"
			+ "\nelements of covariance matrix N*(N-1)/2. The last set of parameters are"
			+ "\nnormalized by the diagonal elements.";
	private transient int rank;
	private transient double[] pos = null;

	/**
	 * Setup the logging facilities
	 */
	private static transient final Logger logger = LoggerFactory.getLogger(GaussianND.class);

	/**
	 * 1D Gaussian
	 */
	public GaussianND() {
		super(new double[]{0,0,0});
	}

	public GaussianND(IParameter... params) {
		super(params);
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
		for (rank = 0; guess < nparams; rank++) {
			guess = 1 + (rank * (rank + 3)) / 2;
		}
		rank = rank - 1;
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
		IParameter p;
		for (int i = 0; i < rank; i++) {
			p = getParameter(n++);
			p.setValue((minPeakPosition[i] + maxPeakPosition[i]) / 2.0);
			p.setLowerLimit(minPeakPosition[i]);
			p.setUpperLimit(maxPeakPosition[i]);
		}

		p = getParameter(n++);
		p.setValue(maxVol / 2.0);
		p.setLowerLimit(0);
		p.setUpperLimit(maxVol);

		double sigmasq = maxSigma * maxSigma;
		for (int i = 0; i < rank; i++) {
			p = getParameter(n++);
			p.setValue(sigmasq/100.);
			p.setLowerLimit(0);
			p.setUpperLimit(sigmasq);
		}
		for (int i = 0; i < rank; i++) {
			for (int j = i + 1; j < rank; j++) {
				p = getParameter(n++);
				p.setValue(0);
				p.setLowerLimit(-1);
				p.setUpperLimit(1);
			}
		}
	}

	@Override
	public int getNoOfParameters() {
		return parameters == null ? -1 : parameters.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC);
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
		setDirty(true);
	}

	/**
	 * Set the minimum and maximum volume of the Gaussian
	 * @param minVol
	 * @param maxVol
	 */
	public void setVolumeLimits(double minVol, double maxVol) {
		getParameter(rank).setLowerLimit(minVol);
		getParameter(rank).setUpperLimit(maxVol);
	}
	
	/**
	 * Set the minimum and maximum variance
	 * @param minS
	 * @param maxS
	 */
	public void setSigmaLimits(double minS, double maxS) {
		int n = rank + 1;
		IParameter p;
		for (int i = 0; i < rank; i++) {
			p = getParameter(n++);
			p.setLowerLimit(minS);
			p.setUpperLimit(maxS);
		}
	}

	/**
	 * @return maximum value
	 */
	public double getPeakValue() {
		if (isDirty())
			calcCachedParameters();
		return norm;
	}

	/**
	 * Setting volume of Gaussian (the integrated value)
	 * @param volume
	 */
	public void setVolume(double volume) {
		getParameter(rank).setValue(volume);
		setDirty(true);
	}
	
	private transient Array2DRowRealMatrix invcov; // inverse of covariance matrix
	private transient double norm;

	private void calcCachedParameters() {
		if (pos == null) {
			pos = new double[rank];
		}
		int n = 0;
		for (int i = 0; i < rank; i++) {
			pos[i] = getParameterValue(n);
			n++;
		}
//		logger.info("New pos at {}", pos);
		norm = getParameterValue(n);
		n++;
		if (rank == 0)
			return;
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
		LUDecomposition decomp = new LUDecomposition(covar, 1e-100);
		invcov = (Array2DRowRealMatrix) decomp.getSolver().getInverse();
		norm /= Math.sqrt(Math.pow(2.*Math.PI, rank) * decomp.getDeterminant());

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double[] v = values.clone();
		for (int i = 0; i < v.length; i++)
			v[i] -= pos[i];

		double[] u = invcov.operate(v);
		double arg = 0;
		for (int i = 0; i < v.length; i++)
			arg += u[i] * v[i];

		return norm * Math.exp(-0.5 * arg);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty())
			calcCachedParameters();

		it.reset();
		double[] coords = it.getCoordinates();
		int j = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			double[] v = coords.clone();
			for (int i = 0; i < v.length; i++) {
				v[i] -= pos.length > 0 ? pos[i] : 0;
			}
			if (invcov == null)
				return;
			double[] u = invcov.operate(v);
			double arg = 0;
			for (int i = 0; i < v.length; i++)
				arg += u[i] * v[i];

			buffer[j++] = norm * Math.exp(-0.5 * arg);
		}
	}
}
