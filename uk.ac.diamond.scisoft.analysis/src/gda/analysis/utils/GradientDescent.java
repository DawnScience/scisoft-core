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

/**
 * GradientDescent Class
 */
public class GradientDescent implements IOptimizer {

	/**
	 * Setup the logging facilities
	 */
//	private static final Logger logger = LoggerFactory.getLogger(GradientDescent.class);

	double qualityFactor = 0.0;

	/**
	 * @param quality
	 */
	public GradientDescent(double quality) {
		qualityFactor = quality;
	}

	@Override
	public void Optimize(DataSet[] coords, DataSet Objective,
			IFunction function) {
		// get the parameters
		double[] params = function.getParameterValues();

		// find out the first Value
		double minval = function.residual(true, Objective, coords);

		// set up all the random values
		// Random rand = new Random();

		// int FailCount = 0;

		double[] pvals = new double[params.length];
		// double[] original = new double[params.length];
		double[] deriv = new double[params.length];

		for (int i = 0; i < pvals.length; i++) {
			pvals[i] = params[i];
		}

		System.out.println(minval);

		double Distance = 0.01;

		// now loop until the required accuracy
		while (Distance > qualityFactor) {

			// now calculate the derivative

			for (int i = 0; i < pvals.length; i++) {
				pvals[i] = function.getParameterValue(i);
			}

			double delta = 0.0000001;
			// generate the differential
			for (int i = 0; i < pvals.length; i++) {

				pvals[i] = pvals[i] - delta;

				// get a value
				function.setParameterValues(pvals);
				double start = function.residual(true, Objective, coords);

				pvals[i] = pvals[i] + 2 * delta;

				// get a value
				function.setParameterValues(pvals);
				double end = function.residual(true, Objective, coords);

				pvals[i] = pvals[i] - delta;

				deriv[i] = (end - start) / (2 * delta);

			}

			// now the derivative has been found, move along that direction
			// by a bit
			double[] tmpvals = new double[params.length];

			for (int i = 0; i < pvals.length; i++) {
				tmpvals[i] = pvals[i] - (deriv[i] * Distance);
			}

			function.setParameterValues(tmpvals);
			double newval = function.residual(true, Objective, coords);

			if (newval < minval) {
				Distance *= 1.1;
				minval = newval;
			} else {
				Distance *= 0.5;
				function.setParameterValues(pvals);
			}

		}

		// make sure the optimised positions are set
		// minval = func.evaluate(pvals);

	}

}
