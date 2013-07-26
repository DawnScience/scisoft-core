/*-
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Basic bean which stores information about how axes are mapped to different
 * dimensions of a dataset
 */
public class AxisMapBean implements Serializable {
	/**
	 * Default ID for x axis
	 */
	public static final String XAXIS = "x-axis";
	/**
	 * Default ID for y axis
	 */
	public static final String YAXIS = "y-axis";
	/**
	 * Default ID for z axis
	 */
	public static final String ZAXIS = "z-axis";
	/**
	 * Default ID for secondary x axis
	 */
	public static final String XAXIS2 = "2nd x-axis";

	
	private String[] axisID;
    
	public AxisMapBean() {
	}
	
	/**
	 * @return Returns the axisID.
	 */
	public String[] getAxisID() {
		return axisID;
	}

	/**
	 * @param axisID
	 *            The axisID strings to set that correspond to each index of a dataset 
	 */
	public void setAxisID(String[] axisID) {
		this.axisID = axisID;
	}

	@Override
	public String toString() {
		return Arrays.toString(axisID);
	}
}
