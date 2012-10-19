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
 * Abstract class for a general mapping function from one double to another one or a byte
 */
public abstract class AbstractMapFunction {

	/**
	 * Clipped version of mapFunction
	 * @param input original value to map
	 * @return output double
	 */
	final public double clippedMapToDouble(double input) {
		double value = mapFunction(input);
		if (value < 0.0) return 0.0;
		else if (value > 1.0) return 1.0;
		return value;
	}

	/**
	 * @param input original value to map
	 * @return byte (C-style usage) 0..255 but due to stupid Java signed bytes will be 
	 *              mapped to -128..127 in Java we have to use short
	 */
	final public short mapToByte(double input) {
		return (short)(255*clippedMapToDouble(input));
	}

	/**
	 * Get the name of the function so it can be included in GUI components
	 * @return the function name
	 */
	abstract public String getMapFunctionName();

	
	/**
	 * Converts an input value to an output value.
	 * 
	 * @param input the input value (0 to 1)
	 * 
	 * @return the output value (0 to 1)
	 */
	abstract public double mapFunction(double input);
}
