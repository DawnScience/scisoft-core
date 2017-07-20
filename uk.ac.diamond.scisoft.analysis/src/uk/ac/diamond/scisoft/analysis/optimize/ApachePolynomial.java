/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;

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
		final int size = x.getSize();
		if (x.getRank() != 1 && y.getRank() != 1) {
			throw new IllegalArgumentException("Input datasets must have rank of 1");
		}
		if (y.getSize() != size) {
			throw new IllegalArgumentException("Input dataset sizes must match");
		}

		Collection<WeightedObservedPoint> points = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			points.add(new WeightedObservedPoint(1, x.getDouble(i), y.getDouble(i)));
		}

		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(guess.length - 1);
		fitter.withStartPoint(guess);
		return fitter.fit(points);
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
		final int size = x.getSize();
		if (x.getRank() != 1 && y.getRank() != 1) {
			throw new IllegalArgumentException("Input datasets must have rank of 1");
		}
		if (y.getSize() != size) {
			throw new IllegalArgumentException("Input dataset sizes must match");
		}
		if (size <= windowSize) {
			throw new IllegalArgumentException("Input dataset size must be greater than window size");
		}
		if (size < 2) {
			throw new IllegalArgumentException("Input dataset size must be greater than 2");
		}

		Collection<WeightedObservedPoint> points = new ArrayList<>(size);
		// integer divide window size so window is symmetric around point
		final int window = windowSize/2;
		final double dx = x.getDouble(1) - x.getDouble(0); // change in x for edge extrapolation
		final int end = size - 1;
		Dataset result = DatasetFactory.zeros(size);
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(polyOrder);
		double[] guess = new double[polyOrder+1];
		Arrays.fill(guess, 1);

		for (int i = 0; i < size; i++) {
			points.clear();
			for (int j = -window + i; j < window+i; j++) {
				WeightedObservedPoint pt;

				// Deal with edge cases:
				// In both cases extend x edge by dx required for window size
				// Pad y with first or last value
				if (j < 0) {
					pt = new WeightedObservedPoint(1, x.getDouble(0) + dx*j, y.getDouble(0));
				} else if (j > end) {
					pt = new WeightedObservedPoint(1, x.getDouble(end) + dx*(j - end), y.getDouble(end));
				} else {
					pt = new WeightedObservedPoint(1, x.getDouble(j), y.getDouble(j));
				}
				points.add(pt);
			}

			fitter.withStartPoint(guess);
			PolynomialFunction fitted = new PolynomialFunction(fitter.fit(points));
			result.set(fitted.value(x.getDouble(i)), i);
		}

		return result;
	}
}
