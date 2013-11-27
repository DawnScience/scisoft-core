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

/**
 * Parameter interface used for manipulation of fitting parameters
 */
public interface IParameter {

	/**
	 * Get the name of the parameter
	 * 
	 * @return The Name of the parameter
	 */
	public String getName();

	/**
	 * Get the value of the parameter
	 * 
	 * @return The Value of the parameter
	 */
	public double getValue();

	/**
	 * Get the Upper limit of the Parameter
	 * 
	 * @return The upper limit
	 */
	public double getUpperLimit();

	/**
	 * Get the lower limit of the parameter
	 * 
	 * @return The lower limit
	 */
	public double getLowerLimit();

	/**
	 * Check whether the parameter is set as fixed or not
	 * 
	 * @return Boolean for use in logical statements
	 */
	public boolean isFixed();

	/**
	 * Set the parameter as fixed or not, the default is false
	 * 
	 * @param b
	 *            The new boolean value for the fixed variable
	 */
	public void setFixed(boolean b);

	/**
	 * Set both limits of the parameter. For when both limits are being
	 * changed at the same time and you wish to avoid the logic testing
	 * when setting the limits separately.
	 * 
	 * @param newLowerLimit
	 * @param newUpperLimit
	 */
	public void setLimits(double newLowerLimit, double newUpperLimit);

	/**
	 * Set the lower limit of the parameter, the default is Double.MIN_VALUE
	 * 
	 * @param lowerLimit
	 *            The new double value which is the lower limit
	 */
	public void setLowerLimit(double lowerLimit);

	/**
	 * Set the upper limit of the parameter, the default is Double.MAX_VALUE
	 * 
	 * @param upperLimit
	 *            The new double value which is the upper limit
	 */
	public void setUpperLimit(double upperLimit);

	
	/**
	 * Set the name of the parameter
	 * 
	 * @param name
	 *            The new String name of the parameter
	 */
	public void setName(String name);

	/**
	 * Set the value of the parameter
	 * 
	 * @param value
	 *            The new double Value of the parameter
	 */
	public void setValue(double value);

}
