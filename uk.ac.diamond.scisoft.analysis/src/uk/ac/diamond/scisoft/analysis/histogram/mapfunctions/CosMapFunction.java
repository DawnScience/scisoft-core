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
 * Cosine map function return a sin value from the input using some given frequency modulation and
 * optionally returning the absolute value
 */
public class CosMapFunction extends AbstractMapFunction {

	private String functionName;
	private double freqMod;
	private boolean useAbsolute;
	
	/**
	 * CosMapFunction constructor
	 * @param functionName function name
	 * @param frequency frequency modulator
	 * @param useAbs use absolute value
	 */
	public CosMapFunction(String functionName, 
						  double frequency, boolean useAbs)
	{
		this.functionName = functionName;
		this.freqMod = frequency;
		this.useAbsolute = useAbs;
	}
	
	@Override
	public String getMapFunctionName() {
		return functionName;
	}

	@Override
	public double mapFunction(double input) {
		double returnValue = Math.cos(input * freqMod);
		if (useAbsolute)
			returnValue = Math.abs(returnValue);
		return returnValue;	
	}
}
