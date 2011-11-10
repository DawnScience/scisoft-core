/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.plotserver;

import java.io.Serializable;

/**
 * Basic bean which stores information about how axes are mapped to different
 * dimensions of a dataset
 */
public class AxisMapBean implements Serializable {

	/**
	 * Direct mapping to a 1 dimensional dataset, i.e. if the position of the
	 * point of interest is at (x,y,z) the value of the x axis will be
	 * axisData(x). In other words, the axes mapping can separated into independent
	 * mappings for each axis.
	 */
	public static final Integer DIRECT = 0;
	
	/**
	 * Full mapping to an n-dimensional dataset. This is for systems where there
	 * may be a nonlinear/complex relationship between axes and so if the point
	 * of interest is at (x,y,z) the value for the x axis will be
	 * axisData(x,y,z). Here the axes mapping can not be separated into individual
	 * axis mappings.
	 */
	public static final Integer FULL = 1;

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
    private Integer mapMode;
    
	/**
	 * Constructor of an AxisMapBean
	 * @param mode
	 */
    
	public AxisMapBean(Integer mode)
	{
		mapMode = mode;
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

	/**
	 * Get the mapping mode of the AxisMap
	 * @return the mapping mode
	 */
	public Integer getMapMode()
	{
		return mapMode;
	}
	
}
