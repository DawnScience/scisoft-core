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
 *
 */
public class SpecialExposureFunction extends AbstractMapFunction {

	private char mode;
	private double minThreshold;
	private double maxThreshold;
	private String functionName;
	
	public SpecialExposureFunction(String functionName,
								   double minThreshold,
								   double maxThreshold,
								   char mode)
	{
		this.functionName = functionName;
		this.minThreshold = minThreshold;
		this.maxThreshold = maxThreshold;
		this.mode = mode;
	}
	
	public void setThresholds(double minThreshold,
							  double maxThresHold)
	{
		this.minThreshold = minThreshold;
		this.maxThreshold = maxThresHold;
	}
	
	@Override
	public String getMapFunctionName() {
		// TODO Auto-generated method stub
		return functionName;
	}

	@Override
	public double mapFunction(double input) {
		double returnValue = 0;
		switch (mode) {
			case 'r':
			{
				if (input < minThreshold)
					returnValue = 0;
				else
					returnValue = input;
			}
			break;
			case 'g':
			{
				if (input < minThreshold ||
					input > maxThreshold)
					returnValue = 0;
				else
					returnValue = input;
			}
			break;
			case 'b':
			{
				returnValue = input;
				if (input < minThreshold)
					returnValue =  (minThreshold - input) / minThreshold;
				if (input > maxThreshold)
					returnValue = 0;
			}
			break;
		}
		return returnValue;
	}

}
