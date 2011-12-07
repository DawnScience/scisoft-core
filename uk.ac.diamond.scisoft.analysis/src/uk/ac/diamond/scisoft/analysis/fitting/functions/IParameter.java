/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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

public interface IParameter {

	/**
	 * Function that gets the value of the parameter
	 * 
	 * @return The Value of the parameter
	 */
	public double getValue();

	/**
	 * Function that gets the Upper limit of the Parameter
	 * 
	 * @return The upper limit
	 */
	public double getUpperLimit();

	/**
	 * Function that gets the lower limit of the parameter
	 * 
	 * @return The lower limit
	 */
	public double getLowerLimit();

	/**
	 * Function that returns whether the parameter is set as fixed or not
	 * 
	 * @return Boolean for use in logical statements
	 */
	public boolean isFixed();

	/**
	 * Sets whether the parameter is fixed or not, the default is false
	 * 
	 * @param b
	 *            The new boolean value for the fixed variable
	 */
	public void setFixed(boolean b);

	/**
	 * Sets both limits of the parameter. For when both limits are being changed at the same time and you wish to avoid
	 * the logic testing when setting the limits separately.
	 * 
	 * @param newLowerLimit
	 * @param newUpperLimit
	 */
	public void setLimits(double newLowerLimit, double newUpperLimit);

	/**
	 * Function sets the lower limit of the parameter, the default is Double.MIN_VALUE;
	 * 
	 * @param lowerLimit
	 *            The new double value which is the lower limit
	 */
	public void setLowerLimit(double lowerLimit);

	/**
	 * Function sets the upper limit of the parameter, the default is Double.MAX_VALUE;
	 * 
	 * @param upperLimit
	 *            The new double value which is the upper limit
	 */
	public void setUpperLimit(double upperLimit);

	/**
	 * Function sets the value of the parameter
	 * 
	 * @param value
	 *            The new double Value of the parameter
	 */
	public void setValue(double value);

}