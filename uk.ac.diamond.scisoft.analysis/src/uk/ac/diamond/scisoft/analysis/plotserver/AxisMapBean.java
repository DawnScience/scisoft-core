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
import java.util.Arrays;

/**
 * Basic bean which stores information about how axes are mapped to different
 * dimensions of a dataset by IDs and names. The IDs refer to axis datasets
 * held by a data bean and those datasets are used for the independent
 * variables of a plot. The names direct the plot to use the corresponding axes.
 */
public class AxisMapBean implements Serializable {
	/**
	 * Default ID or name for x axis
	 */
	public static final String XAXIS = "X-Axis";
	/**
	 * Default ID or name for y axis
	 */
	public static final String YAXIS = "Y-Axis";
	/**
	 * Default ID or name for z axis
	 */
	public static final String ZAXIS = "Z-Axis";
	/**
	 * Default ID for secondary x axis
	 */
	public static final String XAXIS2 = "2nd X-Axis";

	private String[] axisID;
	private String[] axisNames;

	public AxisMapBean() {
	}

	/**
	 * @return Returns the axisIDs which refer to the axis datasets
	 */
	public String[] getAxisID() {
		return axisID;
	}

	/**
	 * @param axisID
	 *            The axisID strings to set that of axis datasets held in a data bean
	 */
	public void setAxisID(String[] axisID) {
		this.axisID = axisID;
	}

	/**
	 * @return axis names
	 */
	public String[] getAxisNames() {
		return axisNames;
	}

	/**
	 * Set the axes for the plotter to use by names
	 * @param axisNames
	 */
	public void setAxisNames(String[] axisNames) {
		this.axisNames = axisNames;
	}

	@Override
	public String toString() {
		return "Dataset IDs: " + Arrays.toString(axisID) + (axisNames == null ? "" : "; Axis names: " + Arrays.toString(axisNames));
	}
}
