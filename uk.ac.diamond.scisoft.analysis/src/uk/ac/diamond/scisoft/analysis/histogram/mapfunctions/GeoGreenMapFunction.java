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
 * Geographical Green channel mapping
 */
public class GeoGreenMapFunction extends AbstractMapFunction {

	private static final double region1 = 1.0/7.0;
	private static final double region2 = 1.0/5.0;
	private static final double region3 = 1.0/3.0;
	private static final double region4 = 2.0/5.0;
	private static final double region5 = 2.0/3.0;
	private static final double region6 = 5.0/6.0;
	
	@Override
	public String getMapFunctionName() {
		return "GeoGreen";
	}

	@Override
	public double mapFunction(double input) {
		if (input < region1)
			return 0;
		else if (input >= region1 && input < region2) 
			return 3.75 * Math.sqrt(input - region1);
		else if (input >= region2 && input < region3)
			return 0.9;
		else if (input >= region3 && input < region4)
			return 0.9 + (input-region3);
		else if (input >= region4 && input < region5)
			return 0.9742  - 1.25 * (input-region4);
		else if (input >= region5 && input < region6)
			return 0.9742 - 1.25 * (region5-region4) - 0.75 * Math.sqrt(input - region5);
		else
			return 0.9742 - 1.25 * (region5-region4) - 0.75 * Math.sqrt(region6 - region5) + 28 * (input-region6) * (input-region6);
	}

}
