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

	private String name = "";

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
	 * Function that gets the name of the parameter
	 * 
	 * @return The Name of the parameter
	 */
	@Override
	public String getName() {
		return name;
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
	 * Function sets the name of the parameter
	 * 
	 * @param name
	 *            The new String name of the parameter
	 */
	@Override
	public void setName(String name) {
		this.name = name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (fixed ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(lowerLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(upperLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (fixed != other.fixed)
			return false;
		if (Double.doubleToLongBits(lowerLimit) != Double.doubleToLongBits(other.lowerLimit))
			return false;
		if (Double.doubleToLongBits(upperLimit) != Double.doubleToLongBits(other.upperLimit))
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
}
