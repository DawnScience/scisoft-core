/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.dawnsci.analysis.dataset.roi.json;

import java.util.Arrays;


/**
 * Sector ROI bean
 * @author wqk87977
 *
 */
public class SectorROIBean extends RingROIBean {

	public static final String SECTOR_TYPE = "SectorROI";

	private double[] angles;   // angles in radians

	private int symmetry; // symmetry

	private boolean combineSymmetry; // combine symmetry option for profile (where appropriate)

	public SectorROIBean(){
		type = SECTOR_TYPE;
	}

	/**
	 * Set the angles
	 * @param angles
	 */
	public void setAngles(double[] angles){
		this.angles = angles;
	}

	/**
	 * Set the symmetry
	 * @param symmetry
	 */
	public void setSymmetry(int symmetry){
		this.symmetry = symmetry;
	}

	/**
	 * Returns the angle
	 * @return angles
	 */
	public double[] getAngles(){
		return angles;
	}

	/**
	 * Returns the symmetry
	 * @return symmetry
	 */
	public int getSymmetry(){
		return symmetry;
	}

	public boolean isCombineSymmetry() {
		return combineSymmetry;
	}

	public void setCombineSymmetry(boolean combineSymmetry) {
		this.combineSymmetry = combineSymmetry;
	}

	@Override
	public String toString() {
		return String.format("{\"type\": \"%s\", \"name\": \"%s\", \"startPoint\": \"%s\", \"angles\": \"%s\", \"symmetry\": \"%s\", \"radii\": \"%s\", \"dpp\": \"%s\"}", 
				type, name, Arrays.toString(startPoint), Arrays.toString(angles), symmetry, Arrays.toString(radii), dpp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(angles);
		result = prime * result + (combineSymmetry ? 1231 : 1237);
		result = prime * result + symmetry;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectorROIBean other = (SectorROIBean) obj;
		if (!Arrays.equals(angles, other.angles))
			return false;
		if (combineSymmetry != other.combineSymmetry)
			return false;
		if (symmetry != other.symmetry)
			return false;
		return true;
	}

}
