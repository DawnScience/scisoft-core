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

package gda.analysis.functions;

import org.python.core.PyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the standard output for any fitting function, it wraps the
 * output parameters to allow for easy interrogation in a script of other
 * application
 */
public class FunctionOutput {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FunctionOutput.class);

	Parameter[] params = null;

	IFunction function = null;

	double chiSquared = 0;

	double areaUnderFit = 0;

	/**
	 * @return Returns the areaUnderFit.
	 */
	public double getAreaUnderFit() {
		return areaUnderFit;
	}

	/**
	 * @param areaUnderFit
	 *            The areaUnderFit to set.
	 */
	public void setAreaUnderFit(double areaUnderFit) {
		this.areaUnderFit = areaUnderFit;
	}

	/**
	 * @return Returns the chiSquared.
	 */
	public double getChiSquared() {
		return chiSquared;
	}

	/**
	 * @param chiSquared
	 *            The chiSquared to set.
	 */
	public void setChiSquared(double chiSquared) {
		this.chiSquared = chiSquared;
	}

	/**
	 * @return Returns the function.
	 */
	public IFunction getFunction() {
		return function;
	}

	/**
	 * Constructor which takes a function as an input. All the
	 * parameter information is stripped from this, and left in a read only form
	 * for the caller.
	 * 
	 * @param Result
	 *            The final composite function after the optimisation has been
	 *            completed
	 */
	public FunctionOutput(IFunction Result) {

		// make a clone of the Composite Function, keeping the parameter data
		params = Result.getParameters();

		// also copy reference to the function
		function = Result;

	}

	/**
	 * Jython overloaded function to allow for data to be obtained as a jython
	 * container
	 * 
	 * @param value
	 *            The number of the point to be interrogated
	 * @return the object containing true
	 */
	public Object __contains__(Integer value) {
		if ((value < 0) || (value >= params.length)) {
			logger.error(
					"The value {} is not within the Result objects bounds",
					value);
			return false;
		}
		return true;
	}

	/**
	 * Jython overloaded function to allow for data to be obtained as a jython
	 * container
	 * 
	 * @param value
	 *            The number of the point to be interrogated
	 * @return the object which is the result
	 */
	public Object __getitem__(Integer value) {
		if ((value < 0) || (value >= params.length)) {
			logger.error(
					"The value {} is not within the Result objects bounds",
					value);
			throw new PyException();
		}
		return params[value];
	}

	/**
	 * Not implemented, as this class is read only
	 * 
	 * @param value
	 * @return null;
	 */
	public Object __delitem__(@SuppressWarnings("unused") Integer value) {
		return null;
	}

	/**
	 * Not implemented as this is a read only class
	 * 
	 * @param value
	 * @param newValue
	 */
	public void __setitem__(@SuppressWarnings("unused") Integer value, @SuppressWarnings("unused") Double newValue) {
	}

	/**
	 * Gets the number of objects in the class
	 * 
	 * @return An object integer containing the number of elements.
	 */
	public Object __len__() {
		return params.length;
	}

	/**
	 * Function that displays all the parameters to a string
	 * 
	 * @return the string containing all the parameter information
	 */
	public String disp() {

		String output = "";
		for (Parameter param : params) {
			output += param.getValue() + "(" + param.getLowerLimit() + ","
					+ param.getUpperLimit() + ")\n";
		}

		return output;

	}

	/**
	 * Called by Java to get the result.
	 * @param i
	 * @return parameter value.
	 */
	public double getParameterValue(int i) {
		return params[i].getValue();
	}

}
