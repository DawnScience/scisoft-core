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
 * Cubic map function, takes the cubic of an input value
 */
public class CubicMapFunction extends AbstractMapFunction {
	@Override
	public String getMapFunctionName() {
		return "x^3";
	}

	@Override
	public double mapFunction(double input) {
		return input*input*input;
	}
}
