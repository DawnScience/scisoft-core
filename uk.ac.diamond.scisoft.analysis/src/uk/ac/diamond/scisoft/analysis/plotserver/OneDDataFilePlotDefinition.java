/*
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
import java.util.Map;

public class OneDDataFilePlotDefinition implements Serializable{
	public String url; 
	public String x_axis; 
	public String[] y_axes;
	
	public Map<String, String> yAxesMap;	

	public OneDDataFilePlotDefinition(String url, String xAxis, String[] yAxes) {
		super();
		this.url = url;
		x_axis = xAxis;
		y_axes = yAxes;
	}
	
	
	public Map<String, String> getyAxesMap() {
		return yAxesMap;
	}

	/**
	 * Key - name of y_axis being plotted. To match a value in y_axes
	 * Entry - name of the y axis to use if the default axis is not to be used
	 */
	public void setyAxesMap(Map<String, String> yAxesMap) {
		this.yAxesMap = yAxesMap;
	}
	
	
}
