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

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;


/**
 * Function interface used for fitting
 */
public interface IFunction {

	/**
	 * Get the name of the function
	 * 
	 * @return The function's name
	 */
	public String getName();

	/**
	 * Set the name parameter
	 * 
	 * @param newName
	 *            The function's new name
	 */
	public void setName(String newName);

	/**
	 * Get the description of the function
	 * 
	 * @return The description of the function
	 */
	public String getDescription();

	/**
	 * Set the description of the function
	 * 
	 * @param newDescription
	 *            The function's new description
	 */
	public void setDescription(String newDescription);

	/**
	 * The key method of the object which returns the evaluation at particular values
	 * 
	 * @param values
	 *            The function arguments
	 * @return A double which is the result of the evaluation.
	 */
	public double val(double... values);

	/**
	 * Get the parameter which is at the index specified
	 * 
	 * @param index
	 *            The position in the array to be obtained
	 * @return The parameter
	 */
	public IParameter getParameter(int index);

	/**
	 * Get the parameter name at a particular index in the function
	 * 
	 * @param index
	 *            The index to retrieve
	 * @return The parameter's name
	 */
	public String getParameterName(int index);

	/**
	 * Set a parameter name of the function
	 * 
	 * @param name
	 *           A string array with the parameter name
	 * @param index
	 *           The index of the parameter name
	 */
	public void setParameterName(String name, int index);

	/**
	 * Get all the parameters in the array
	 * 
	 * @return A copy of all the parameters
	 */
	public IParameter[] getParameters();

	/**
	 * Get the length of the parameter array
	 * 
	 * @return the length as an integer
	 */
	public int getNoOfParameters();

	/**
	 * Gets the number of functions in the (composite) function.
	 * 
	 * @return An integer which is the number of functions
	 */
	public int getNoOfFunctions();

	/**
	 * Get a particular function from the composite function
	 * 
	 * @param index
	 *            The index of the object to retrieve
	 * @return A link to the function itself
	 */
	public IFunction getFunction(int index);

	/**
	 * Get the parameter at a particular index in the function
	 * 
	 * @param index
	 *            The index to retrieve
	 * @return A pointer to the parameter
	 */
	public double getParameterValue(int index);

	/**
	 * Get a double array of all the parameters values
	 * 
	 * @return The double array of all the parameter values
	 */
	public double[] getParameterValues();

	/**
	 * Set a parameter at given index
	 *
	 * @param index
	 * @param parameter
	 */
	public void setParameter(int index, IParameter parameter);

	/**
	 * Set all of the parameter values for the object.
	 * 
	 * @param params
	 *            an array of doubles which needs to be equal in length to the number of parameters in the array.
	 */
	public void setParameterValues(double... params);

	/**
	 * Method which calculates the partial derivative
	 * 
	 * @param index
	 * @param values
	 * @return the derivative at the point specified with respect to the parameter specified by its index.
	 */
	public double partialDeriv(int index, double... values);

	/**
	 * Make a dataset from the function
	 * 
	 * The function can be evaluated in one of two possible modes. In general, the function has
	 * <tt>m</tt> independent variables and the output dataset has <tt>n</tt> dimensions.
	 * The simplest mode has the restriction <tt>m = n</tt> and all <tt>n</tt> input datasets must
	 * be 1D and the function is evaluated on a nD hypergrid.
	 * 
	 * The general mode requires <tt>m</tt> nD datasets.
	 * 
	 * @param values
	 *            The values at which to evaluate the function
	 * @return The dataset of the whole function
	 */
	public IDataset makeDataset(IDataset... values);

	/**
	 * Method to evaluate the sum of the deviations of the dataset from the function
	 * when that function is evaluated at the given values and parameters. The allValues flag
	 * dictates whether to use all the values or just a sampled subset
	 * 
	 * @param allValues Boolean specifying whether to use sampling or not, currently not implemented so use true
	 * @param data A dataset containing the values for the data to be evaluated
	 * @param values a dataset containing the coordinates of the data points
	 * @return residual
	 */
	public double residual(boolean allValues, IDataset data, IDataset... values);

	/**
	 * Set a monitor to check progress
	 * @param monitor
	 */
	public void setMonitor(IMonitor monitor);

	/**
	 * Get monitor
	 * @return monitor, maybe be null
	 */
	public IMonitor getMonitor();
}
