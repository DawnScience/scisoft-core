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
 * Geographical Red channel mapping
 */
public class GeoRedMapFunction extends AbstractMapFunction {

	private static final double region1 = 1.0/7.0;
	private static final double region2 = 2.0/5.0;
	private static final double region3 = 3.0/4.0;
	@Override
	public String getMapFunctionName() {
		return "GeoRed";
	}

	@Override
	public double mapFunction(double input) {
		if (input < region1)
			return 0;
		if (input >= region1 && input < region2)
			return (input-region1) * 4;
		else if (input >= region2 && input < region3)
			return 1.0 - 0.5 * (input-region2);
		else if (input >= region3)
			return 1.0 - 0.5 * (region3-region2) + 3.75 * (input-region3) * (input-region3);
		return 1;
	}

}
