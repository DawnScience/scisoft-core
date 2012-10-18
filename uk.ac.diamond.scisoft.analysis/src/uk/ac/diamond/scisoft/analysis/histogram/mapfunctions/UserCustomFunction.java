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
public class UserCustomFunction extends AbstractMapFunction {

	double[] mapTable;
	String funcname;
	
	public UserCustomFunction(String funcname, int tableSize) {
		mapTable = new double[tableSize];
		this.funcname = funcname;
	}
	@Override
	public String getMapFunctionName() {
		return funcname;
	}

	public void setValues(double[] values) {
		mapTable = values.clone();
	}
	
	public void setValue(int pos, double value) {
		if (pos >= 0 && pos < mapTable.length)
			mapTable[pos] = value;
	}
	
	@Override
	public double mapFunction(double input) {
		int lowMapEntry = (int)(input * mapTable.length);
		int upMapEntry = (int)Math.ceil(input * mapTable.length);
		
		if (upMapEntry > mapTable.length-1)
			upMapEntry = mapTable.length-1;
		
		double a = input * mapTable.length - lowMapEntry;
		// linear interpolation
		return mapTable[lowMapEntry] * (1.0 - a) +  a * mapTable[upMapEntry];
	}

}
