/*
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.histogram.mapfunctions;

/**
 * General linear map function with offset
 * 
 * This will scale the input by a specified scaling factor and adds an
 * offset to it, optional it is possible to get the absolute value 
 */
public class LinearMapFunction extends AbstractMapFunction {
	private String functionName;
	private double scaleFactor;
	private double offset;
	private boolean useAbsolute;

	/**
	 * Linear: x
	 */
	public LinearMapFunction()
	{
		this("x", 1.0, 0.0, false);
	}

	/**
	 * Scaled linear: scaleFactor * x
	 * @param functionName
	 * @param scaleFactor
	 * @param useAbs
	 */
	public LinearMapFunction(String functionName,
										double scaleFactor,
										boolean useAbs)
	{
		this(functionName, scaleFactor, 0.0, useAbs);
	}

	/**
	 * Scaled linear: scaleFactor * x + offset
	 *
	 * @param functionName name of the function
	 * @param scaleFactor scaling factor
	 * @param offset offset
	 * @param useAbs use absolute value?
	 */
	public LinearMapFunction(String functionName,
										double scaleFactor,
										double offset,
										boolean useAbs)
	{
		this.functionName = functionName;
		this.scaleFactor = scaleFactor;
		this.offset = offset;
		this.useAbsolute = useAbs;
	}

	@Override
	public String getMapFunctionName() {
		return functionName;
	}

	@Override
	public double mapFunction(double input) {
		double returnValue = input * scaleFactor + offset;
		if (useAbsolute)
			returnValue = Math.abs(returnValue);
		return returnValue;
	}
}
