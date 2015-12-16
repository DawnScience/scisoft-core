/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import java.util.Arrays;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialFitter;
import org.apache.commons.math3.optim.nonlinear.vector.jacobian.LevenbergMarquardtOptimizer;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;

/**
 * Class to contain methods to fit univariate polynomials
 */
public class ApachePolynomial {

	/**
	 * Fit a polynomial to given x and y data
	 * @param x
	 * @param y
	 * @param order order or highest degree of terms in polynomial
	 * @return coefficients of fitted polynomial (given in increasing degree of the terms)
	 */
	public static double[] polynomialFit(Dataset x, Dataset y, int order) {
		double[] guess = new double[order + 1];
		Arrays.fill(guess, 1);
		return polynomialFit(x, y, guess);
	}

	/**
	 * Fit a polynomial to given x and y data
	 * @param x
	 * @param y
	 * @param guess initial values for coefficients (given in increasing degree of the terms)
	 * @return coefficients of fitted polynomial
	 */
	public static double[] polynomialFit(Dataset x, Dataset y, double... guess) {
		PolynomialFitter fitter = new PolynomialFitter(new LevenbergMarquardtOptimizer());

		for (int i = 0; i < y.getSize(); i++) {
			fitter.addObservedPoint(1,x.getDouble(i),y.getDouble(i));
		}

		return fitter.fit(guess);
	}

	/**
	 * Calculate a polynomial filtered dataset using a specified window size and polynomial order.
	 * 
	 * @param x The abscissa (generally energy/wavelength etc)
	 * @param y The ordinate to be smoothed
	 * @param windowSize The size of window used in the smoothing
	 * @param polyOrder The order of the polynomial fitted to the window
	 * @return result The smoothed data set
	 * @throws Exception 
	 */
	public static Dataset getPolynomialSmoothed(final Dataset x, final Dataset y,
			int windowSize, int polyOrder) throws Exception {
		// Could probably do with more sanity check on relative size of
		// window vs polynomial but doesn't seem to trip up
		// So we'll see how it goes...

		// integer divide window size so window is symmetric around point
		int window = windowSize/2;

		PolynomialFitter fitter = new PolynomialFitter(new LevenbergMarquardtOptimizer());
		double dx = x.getDouble(1) - x.getDouble(0); // change in x for edge extrapolation
		int xs =  x.getSize();
		Dataset result = DatasetFactory.zeros(y);
		double[] guess = new double[polyOrder+1];

		for (int idx = 0; idx < xs; idx++) {
			fitter.clearObservations();
			
			// Deal with edge cases:
			// In both cases extend x edge by dx required for window size
			// Pad y with first or last value
			for (int idw = -window; idw < window+1; idw++) {
				if (idx+idw < 0) {
					fitter.addObservedPoint(1,x.getDouble(0)+(dx*(idx+idw)), y.getDouble(0));
				} else if ((idx + idw) > (xs-1)) {
					fitter.addObservedPoint(1,x.getDouble(xs-1) + dx*(idx + idw -(xs-1)), y.getDouble(xs-1));
				} else {
					fitter.addObservedPoint(1,x.getDouble(idx+idw), y.getDouble(idx+idw));
				}
			}

			PolynomialFunction fitted = new PolynomialFunction(fitter.fit(guess));
			result.set(fitted.value(x.getDouble(idx)), idx);
		}

		return result;
	}
}
