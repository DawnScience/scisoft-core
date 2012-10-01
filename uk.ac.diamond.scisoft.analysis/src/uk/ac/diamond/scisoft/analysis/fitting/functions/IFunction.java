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

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;


/**
 * IFunction interface
 */
public interface IFunction {

	/**
	 * Function that gets the name of the function
	 * 
	 * @return The name of the function
	 */
	public String getName();

	/**
	 * Sets the name parameter
	 * 
	 * @param newName
	 *            The functions new name
	 */
	public void setName(String newName);

	/**
	 * Function that gets the description of the function
	 * 
	 * @return The description of the function
	 */
	public String getDescription();

	/**
	 * Sets the description parameter
	 * 
	 * @param newDescription
	 *            The functions new description
	 */
	public void setDescription(String newDescription);

	/**
	 * The key function of the object which returns the evaluation at particular values
	 * 
	 * @param values
	 *            The function arguments
	 * @return A double which is the result of the evaluation.
	 */
	public double val(double... values);

	/**
	 * Gets the parameter which is at the index specified
	 * 
	 * @param index
	 *            The position in the array to be obtained
	 * @return A link to the parameter
	 */
	public IParameter getParameter(int index);

	/**
	 * Function that gets the parameter name at a particular index in the function
	 * 
	 * @param index
	 *            The index to retrieve
	 * @return A pointer to the name parameter
	 */
	public String getParameterName(int index);

	/**
	 * Function that sets the parameter names  of the function
	 * 
	 * @param name
	 *           A string array with the parameter name
	 * @param index
	 *           The index of the parameter name
	 */
	public void setParameterName(String name, int index);

	/**
	 * Gets all the parameters in the list
	 * 
	 * @return A copy of all the parameters
	 */
	public IParameter[] getParameters();

	/**
	 * Function that gets the length of the parameter array
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
	 * Function which gets a particular function from the composite function
	 * 
	 * @param index
	 *            The index of the object to retrieve
	 * @return A link to the function itself
	 */
	public IFunction getFunction(int index);

	/**
	 * Function that gets the parameter at a particular index in the function
	 * 
	 * @param index
	 *            The index to retrieve
	 * @return A pointer to the parameter
	 */
	public double getParameterValue(int index);

	/**
	 * Function that gets a double array of all the parameters values
	 * 
	 * @return The double array of all the parameter values
	 */
	public double[] getParameterValues();

	/**
	 * Function that sets all of the parameter values for the object.
	 * 
	 * @param params
	 *            an array of doubles which needs to be equal in length to the number of parameters in the array.
	 */
	public void setParameterValues(double... params);

	/**
	 * Function which calculates the partial derivative
	 * 
	 * @param Parameter
	 * @param position
	 * @return the derivative at the point specified with respect to the parameter specified.
	 */
	public double partialDeriv(int Parameter, double... position);

	/**
	 * Function that makes a dataset from the Function
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
	public DoubleDataset makeDataset(IDataset... values);

	/**
	 * Method to evaluate the sum of the deviations of the dataset from the function
	 * when that function is evaluated at the given values and parameters. The allValues flag
	 * dictates whether to use all the values or just a sampled subset
	 * 
	 * @param data A dataset containing the values for the data to be evauluated
	 * @param allValues Boolean specifying wheter to use sampling or not, curtrently not implemented so use true
	 * @param values a dataset containing the coordinates of the data points
	 * @return residual
	 */
	public double residual(boolean allValues, IDataset data, IDataset... values);
}
