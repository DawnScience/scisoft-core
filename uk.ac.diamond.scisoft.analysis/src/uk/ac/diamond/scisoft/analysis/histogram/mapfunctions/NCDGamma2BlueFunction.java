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
 * Specific NCD Blue channel mapping function
 */
public class NCDGamma2BlueFunction extends AbstractMapFunction {

	@Override
	public String getMapFunctionName() {
		return "NCD Gamma II Blue";
	}

	@Override
	public double mapFunction(double input) {
		if (input >= 0.690) return (input-0.690) / (1-0.690);
		if (input <= 0.192) return input/0.192;
		if (input <= 0.373) return 1 - (input - 0.192) / (0.373 - 0.192);
		if (input <= 0.506) return 0;
		if (input >= 0.624) return 0;
		if (input <= 0.569) return ((input-0.506)/(0.569-0.506)) * 0.322;
		if (input >= 0.569) return (1 - ((input-0.569)/(0.624-0.569))) * 0.322;
		return 0;
	}

}
