/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

	@Override
	public String toString(){
		return String.format("{\"type\": \"%s\", \"name\": \"%s\", \"startPoint\": \"%s\", \"angles\": \"%s\", \"symmetry\": \"%s\", \"radii\": \"%s\", \"dpp\": \"%s\"}", 
				type, name, Arrays.toString(startPoint), Arrays.toString(angles), symmetry, Arrays.toString(radii), dpp);
	}
}
