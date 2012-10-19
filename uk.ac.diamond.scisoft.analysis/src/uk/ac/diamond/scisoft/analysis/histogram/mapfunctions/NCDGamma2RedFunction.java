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
 * Specific NCD Red channel mapping function
 */
public class NCDGamma2RedFunction extends AbstractMapFunction {

	@Override
	public String getMapFunctionName() {
		return "NCD Gamma II Red";
	}

	@Override
	public double mapFunction(double input) {
		if (input >= 0.753) return 1;
		if (input <= 0.188) return 0;
		if (input <= 0.251) return 0.316 * (input - 0.188) / (0.251 - 0.188);
		if (input <= 0.306) return 0.316;
		if (input <= 0.431) return 0.316 + (1 - 0.319) * (input - 0.306) / (0.431 - 0.306);
		if (input >= 0.682) return 0.639 + (1 - 0.639) * (input - 0.682) / (0.753 - 0.682);
		if (input >= 0.635) return 1 - (1 - 0.639) * (input - 0.635) / (0.682 - 0.635);
		return 1;					
	}

}
