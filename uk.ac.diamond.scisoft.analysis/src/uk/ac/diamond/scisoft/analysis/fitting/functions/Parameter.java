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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class which wraps a single parameter for a function and allows for its change
 */
public class Parameter implements Serializable, IParameter {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(Parameter.class);

	private double value = 0.0;

	private double upperLimit = Double.MAX_VALUE;

	private double lowerLimit = -(Double.MAX_VALUE);

	private boolean fixed = false;

	/**
	 * Basic Constructor, does not initialise anything
	 */
	public Parameter() {
	}

	/**
	 * This constructor clones another Parameter
	 * 
	 * @param p
	 *            The parameter to be cloned
	 */
	public Parameter(IParameter p) {
		this.value = p.getValue();
		this.upperLimit = p.getUpperLimit();
		this.lowerLimit = p.getLowerLimit();
		this.fixed = p.isFixed();
	}

	/**
	 * Constructor that sets the value, but leaves everything else set as initialised
	 * 
	 * @param value
	 *            Value that the parameter is set to
	 */
	public Parameter(double value) {
		// this.name = name;
		this.value = value;
	}

	/**
	 * Constructor that sets up the value along with the max and min parameters
	 * 
	 * @param value
	 *            Value of the parameter
	 * @param lowerLimit
	 *            Lower limit the parameter is restricted to
	 * @param upperLimit
	 *            Upper limit the parameter is restricted to
	 */
	public Parameter(double value, double lowerLimit, double upperLimit) {
		// this.name = name;
		this.value = value;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	/**
	 * Function that gets the value of the parameter
	 * 
	 * @return The Value of the parameter
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * Function that gets the Upper limit of the Parameter
	 * 
	 * @return The upper limit
	 */
	@Override
	public double getUpperLimit() {
		return this.upperLimit;
	}

	/**
	 * Function that gets the lower limit of the parameter
	 * 
	 * @return The lower limit
	 */
	@Override
	public double getLowerLimit() {
		return this.lowerLimit;
	}

	/**
	 * Function that returns whether the parameter is set as fixed or not
	 * 
	 * @return Boolean for use in logical statements
	 */
	@Override
	public boolean isFixed() {
		return fixed;
	}

	/**
	 * Sets whether the parameter is fixed or not, the default is false
	 * 
	 * @param b
	 *            The new boolean value for the fixed variable
	 */
	@Override
	public void setFixed(boolean b) {
		this.fixed = b;
	}

	/**
	 * Sets both limits of the parameter. For when both limits are being changed at the same time and you wish to avoid
	 * the logic testing when setting the limits separately.
	 * 
	 * @param newLowerLimit
	 * @param newUpperLimit
	 */
	@Override
	public void setLimits(double newLowerLimit, double newUpperLimit) {
		
		if (newLowerLimit > newUpperLimit) {
			logger.warn("Cannot set limits: You are trying to set the lower bound to greater than the upper limit");
			return;
		}

		if (newLowerLimit < lowerLimit) {
			logger.warn("Parameter value {} is lower than this new lower bound {} - Adjusting value to equal new lower bound value ", value, newLowerLimit);
			value = lowerLimit;
		}
		
		if (newUpperLimit > upperLimit) {
			logger.warn("Parameter value {} is higher than this new upper bound {} - Adjusting value to equal new upper bound value ",value, newUpperLimit);
			value = upperLimit;
		}

		this.lowerLimit = newLowerLimit;
		this.upperLimit = newUpperLimit;
	}

	/**
	 * Function sets the lower limit of the parameter, the default is Double.MIN_VALUE;
	 * 
	 * @param lowerLimit
	 *            The new double value which is the lower limit
	 */
	@Override
	public void setLowerLimit(double lowerLimit) {
		if (lowerLimit > upperLimit) {
			logger.warn("Cannot set lower limit: You are trying to set the lower bound to greater than the upper limit");
			return;
		}

		if (value < lowerLimit) {
			logger.warn("Parameter value {} is lower than this new lower bound {} - Adjusting value to equal new lower bound value ", value, lowerLimit);
			value = lowerLimit;
		}
		this.lowerLimit = lowerLimit;
	}

	/**
	 * Function sets the upper limit of the parameter, the default is Double.MAX_VALUE;
	 * 
	 * @param upperLimit
	 *            The new double value which is the upper limit
	 */
	@Override
	public void setUpperLimit(double upperLimit) {
		if (upperLimit < lowerLimit) {
			logger.warn("Cannot set upper limit: You are trying to set the upper bound to lower than the lower limit");
			return;
		}

		if (value > upperLimit) {
			logger.warn("Parameter value is higher than this new upper bound - Adjusting value to equal new upper bound value ");
			value = upperLimit;
		}

		this.upperLimit = upperLimit;
	}

	/**
	 * Function sets the value of the parameter
	 * 
	 * @param value
	 *            The new double Value of the parameter
	 */
	@Override
	public void setValue(double value) {
		if (value > upperLimit || value < lowerLimit) {
			return;
		}
		if (fixed) {
			return;
		}
		this.value = value;
	}

	@Override
	public String toString() {
		return "Parameter is " + String.valueOf(value);
	}
}
