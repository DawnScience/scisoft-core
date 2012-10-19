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
 * A custom piece-wise linear mapping function
 */
public class CustomAPieceWiseLinearMapFunction extends AbstractMapFunction {
	@Override
	public String getMapFunctionName() {
		return "4x;1;-2x+1.84;12.5x-11.5";
	}

	@Override
	public double mapFunction(double input) {
		double returnValue;
		if (input < 0.25) {
			returnValue = 4.0 * input;
		} else if (input < 0.42) {
			returnValue = 1.0;
		} else if (input < 0.92) {
			returnValue = -2.0 * input + 1.84;
		} else {
			returnValue = 12.5 * input - 11.5;
		}
		return returnValue;
	}
}
