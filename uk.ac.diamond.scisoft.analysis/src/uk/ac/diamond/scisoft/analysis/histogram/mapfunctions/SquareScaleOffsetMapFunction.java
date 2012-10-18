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
 * Map function that returns the square of the scaled and offset input
 */
public class SquareScaleOffsetMapFunction extends AbstractMapFunction {
	private String functionName;
	private double scaleFactor;
	private double offset;
	
	/**
	 * Constructor for a square scale offset map function
	 *
	 * (scaleFactor*x + offset)^2
	 * @param functionName name of the function
	 * @param scaleFactor scaling factor
	 * @param offset offset factor
	 */
	public SquareScaleOffsetMapFunction(String functionName, double scaleFactor,
										double offset)
	{
		this.functionName = functionName;
		this.scaleFactor = scaleFactor;
		this.offset = offset;
	}
	
	@Override
	public String getMapFunctionName() {
		return functionName;
	}

	@Override
	public double mapFunction(double input) {
		double returnValue = input * scaleFactor + offset;
		returnValue *= returnValue;
		return returnValue;
	}
}
