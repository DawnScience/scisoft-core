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

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static Class which allows for the monteCarlo functionality to be called to optimize simple functions
 */
public class MonteCarlo implements IOptimizer {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(MonteCarlo.class);

	private double qualityFactor = 0.0;

	/**
	 * Constructor which takes the quality of the fit as an input.
	 * 
	 * @param quality
	 *            A generic quality term, try 0.1 to start then increase the number for a quicker time, and decrease it
	 *            for more accuracy
	 */
	public MonteCarlo(double quality) {
		qualityFactor = quality;
	}

	@Override
	public void Optimize(DataSet[] coords, DataSet Objective, IFunction function) {

		// get the parameters
		double[] params = function.getParameterValues();

		// find out the first Value
		double minval = function.residual(true, Objective, coords);

		// set up all the random values
		Random rand = new Random();

		double Delta = 1.0;

		logger.debug("Accurasy reached is :{}", Delta);

		int FailCount = 0;
		// now loop until the required accuracy
		while (Delta > qualityFactor) {


			if (FailCount > params.length * 20) {
				Delta = Delta / 1.2;
				logger.info("Accurasy reached is :" + Delta);
				FailCount = 0;
				// Function.Display(XAxis, Objective);
			}
			
			if (FailCount < 0) {
				FailCount = 0;
			}
			
			// pick a position at random
			int pos = Math.abs(rand.nextInt()) % params.length;
		
			double oldValue = function.getParameter(pos).getValue(); 
			
			// calculate a maximum displacement, based on the delta value
			double deltaValue = ((rand.nextDouble() - 0.5) * Delta);
			
			double displacementValue = (function.getParameter(pos).getUpperLimit() 
					- function.getParameter(pos).getLowerLimit()) * deltaValue;
			
	
			// now grab a random point
			double newRandomParameterValue = (rand.nextDouble() * (function.getParameter(pos).getUpperLimit() 
					- function.getParameter(pos).getLowerLimit()))+function.getParameter(pos).getLowerLimit();
			
			// compare this with the old position
			
			if(Math.abs(oldValue-newRandomParameterValue) > displacementValue) {
				// the displacement is too large
				if(newRandomParameterValue > oldValue) {
					newRandomParameterValue = oldValue+displacementValue;
				} else {
					newRandomParameterValue = oldValue-displacementValue;
				}
			}
						
			params[pos] = newRandomParameterValue;

			function.setParameterValues(params);
			double testval = function.residual(true, Objective, coords);
			
			if (testval < minval) {
				minval = testval;
				FailCount -= 15;
				logger.debug("minval is {} with a failcount of {}", minval, FailCount);

			} else {
				
				//TODO this should really count for the proper statistical distribution of accepting higher values.

				// reset back to the old/better value
				params[pos] = oldValue;
				function.setParameterValues(params);
				FailCount++;
				logger.debug("minval is {} with a failcount of {}", minval, FailCount);
			}

		}

		function.setParameterValues(params);
		params = function.getParameterValues();
	}

}
