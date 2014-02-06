/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.persistence.bean.roi;

import java.util.Arrays;


/**
 * Sector ROI bean
 * @author wqk87977
 *
 */
public class SectorROIBean extends ROIBean{

	private double[] angles;   // angles in radians

	private int symmetry; // symmetry

	private double[] radii; // radii

	private double dpp; // Sampling rate used for profile calculations in dots per pixel

	private boolean clippingCompensation; // compensate for clipping
	private boolean averageArea;
	private boolean combineSymmetry; // combine symmetry option for profile (where appropriate)

	public SectorROIBean(){
		type = "SectorROI";
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
	 * Set the radii
	 * @param radii
	 */
	public void setRadii(double[] radii){
		this.radii = radii;
	}

	/**
	 * Set sampling rate used in profile calculations  
	 * 
	 * @param dpp
	 *			sampling rate in dots per pixel; 
	 */
	public void setDpp(double dpp) {
		this.dpp = dpp;
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

	/**
	 * Returns the radii
	 * @return radii
	 */
	public double[] getRadii(){
		return radii;
	}

	/**
	 * Return sampling rate used in profile calculations
	 * 
	 * @return
	 * 			sampling rate in dots per pixel
	 */
	public double getDpp() {
		return dpp;
	}

	public boolean isClippingCompensation() {
		return clippingCompensation;
	}

	public void setClippingCompensation(boolean clippingCompensation) {
		this.clippingCompensation = clippingCompensation;
	}

	public boolean isAverageArea() {
		return averageArea;
	}

	public void setAverageArea(boolean averageArea) {
		this.averageArea = averageArea;
	}

	public boolean isCombineSymmetry() {
		return combineSymmetry;
	}

	public void setCombineSymmetry(boolean combineSymmetry) {
		this.combineSymmetry = combineSymmetry;
	}

	@Override
	public String toString(){
		return String.format("{\"type\": \"%s\", \"name\": \"%s\", \"startPoint\": \"%s\", \"angles\": \"%s\", \"symmetry\": \"%s\", \"radii\": \"%s\", \"dpp\": \"%s\"}", 
				type, name, Arrays.toString(startPoint), Arrays.toString(angles), symmetry, Arrays.toString(radii), dpp);
	}
}
