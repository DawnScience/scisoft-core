/*-
 * Copyright 2013 Diamond Light Source Ltd.
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
 * A function that is an operation on other functions
 */
public interface IOperator extends IFunction {

	/**
	 * Test if operator is extendible
	 * @returntrue, if it can be extended
	 */
	public boolean isExtendible();

	/**
	 * Get the number of functions in the (composite) function.
	 * 
	 * @return An integer which is the number of functions
	 */
	public int getNoOfFunctions();

	/**
	 * @return number of required functions, can return -1 for unlimited number
	 */
	public int getRequiredFunctions();

	/**
	 * Get function at given index
	 * 
	 * @param index
	 * @return function
	 */
	public IFunction getFunction(int index);

	/**
	 * Get all function
	 * 
	 * @return functions
	 */
	public IFunction[] getFunctions();

	/**
	 * Add function to operator
	 * @param function
	 */
	public void addFunction(IFunction function);

	/**
	 * Removes a function at given index from operator
	 * @param index
	 */
	public void removeFunction(int index);

	/**
	 * Set function at given index
	 * @param index
	 * @param function
	 */
	public void setFunction(int index, IFunction function);
}
