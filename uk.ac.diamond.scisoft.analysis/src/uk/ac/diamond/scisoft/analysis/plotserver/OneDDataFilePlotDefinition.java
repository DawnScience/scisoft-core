/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
