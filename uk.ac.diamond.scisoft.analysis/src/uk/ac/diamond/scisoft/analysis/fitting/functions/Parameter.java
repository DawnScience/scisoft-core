/*-
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
		this.value = value;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public double getUpperLimit() {
		return this.upperLimit;
	}

	@Override
	public double getLowerLimit() {
		return this.lowerLimit;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}

	@Override
	public void setFixed(boolean b) {
		this.fixed = b;
	}

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

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setValue(double value) {
		if (value > upperLimit) {
			this.value = upperLimit;
			return;
		}
		if ( value < lowerLimit) {
			this.value = lowerLimit;
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
