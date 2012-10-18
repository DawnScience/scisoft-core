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
 * Specific NCD Green channel mapping function
 */
public class NCDGamma2GreenFunction extends AbstractMapFunction {

	@Override
	public String getMapFunctionName() {
		return "NCD Gamma II Green";
	}

	@Override
	public double mapFunction(double input) {
		if (input >= 0.749) return 1;
		if (input <= 0.447) return 0;
		if (input <= 0.569) return 0.639 * (input - 0.447) / (0.569 - 0.447);
		if (input >= 0.690) return 0.639 + (1 - 0.639) * (input - 0.690) / (0.749 - 0.690); 
		return 0.639;
	}

}
