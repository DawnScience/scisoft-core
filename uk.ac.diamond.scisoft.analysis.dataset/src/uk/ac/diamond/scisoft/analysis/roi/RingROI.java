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

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.coords.SectorCoords;

/**
 * Class for ring region of interest
 */
public class RingROI extends ROIBase implements Serializable {
	protected double rad[]; // radii

	protected boolean clippingCompensation; // compensate for clipping
	private boolean averageArea = true;
	protected double dpp; // Sampling rate used for profile calculations in dots per pixel

	/**
	 * 
	 */
	public RingROI() {
		this(30., 120.);
	}

	/**
	 * Create an annulus centred on origin
	 * @param sr 
	 * @param er
	 */
	public RingROI(double sr, double er) {
		this(0, 0, sr, er);
	}

	/**
	 * Create an annulus
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 */
	public RingROI(double ptx, double pty, double sr, double er) {
		this(ptx, pty, sr, er, 1.0, true);
	}

	/**
	 * @param ptx
	 * @param pty
	 * @param sr
	 * @param er
	 * @param dpp
	 * @param clip 
	 */
	public RingROI(double ptx, double pty, double sr, double er, double dpp, boolean clip) {
		spt = new double[] {ptx, pty};
		rad = new double[] {sr, er};
		this.dpp = dpp;
		clippingCompensation = clip;
		checkRadii();
	}

	/**
	 * @return Returns reference to the radii
	 */
	public double[] getRadii() {
		return rad;
	}

	/**
	 * @param index 
	 * @return Returns the radius
	 */
	public double getRadius(int index) {
		return rad[index];
	}

	/**
	 * @return Returns the radii
	 */
	public int[] getIntRadii() {
		return new int[] { (int) rad[0], (int) rad[1] };
	}

	/**
	 * @param index 
	 * @return Returns the radius
	 */
	public int getIntRadius(int index) {
		return (int) rad[index];
	}

	/**
	 * @param radius The radii to set
	 */
	public void setRadii(double radius[]) {
		setRadii(radius[0], radius[1]);
	}

	/**
	 * @param startRadius 
	 * @param endRadius 
	 */
	public void setRadii(double startRadius, double endRadius) {
		rad[0] = startRadius;
		rad[1] = endRadius;
		checkRadii();
		bounds = null;
	}

	/**
	 * Add an offset to radii
	 * @param radius 
	 */
	public void addRadii(double radius) {
		if (rad[0] + radius < 0)
			radius = -rad[0];
		if (rad[1] + radius < 0)
			radius = -rad[1];
		rad[0] += radius;
		rad[1] += radius;
		bounds = null;
	}

	/**
	 * Add an offset to a radius
	 * @param index 
	 * @param radius 
	 */
	public void addRadius(int index, double radius) {
		rad[index] += radius;
		checkRadii();
		bounds = null;
	}

	/**
	 * Make sure radii lie in permitted range:
	 *  0 <= rad0, rad1
	 *  rad0 <= rad1
	 */
	private void checkRadii() {
		if (rad[0] < 0)
			rad[0] = 0;
		if (rad[1] < 0)
			rad[1] = 0;
		if (rad[0] > rad[1]) {
			rad[0] = rad[1];
		}
	}

	/**
	 * @param clippingCompensation The clippingCompensation to set.
	 */
	public void setClippingCompensation(boolean clippingCompensation) {
		this.clippingCompensation = clippingCompensation;
	}

	/**
	 * @return Returns the clippingCompensation.
	 */
	public boolean isClippingCompensation() {
		return clippingCompensation;
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
	 * Whether the pixel average value shall be calculated instead of integrating
	 * @return averageArea
	 */
	public boolean isAverageArea() {
		return averageArea;
	}

	/**
	 * Set true to not strictly integrate but average over the pixels
	 * @param averageArea
	 */
	public void setAverageArea(boolean averageArea) {
		this.averageArea = averageArea;
	}

	@Override
	public boolean isNearOutline(double x, double y, double distance) {
		x -= spt[0];
		y -= spt[1];

		double[] pol = SectorCoords.convertFromCartesianToPolarRadians(x, y);
		double r = pol[0];
		return Math.abs(r - rad[0]) <= distance || Math.abs(r - rad[1]) <= distance;
	}

	@Override
	public RectangularROI getBounds() {
		if (bounds == null) {
			bounds = new RectangularROI();
			bounds.setPoint(spt[0] - rad[1], spt[1] - rad[1]);
			bounds.setLengths(2*rad[1], 2*rad[1]);
		}
		return bounds;
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		rad[0] /= subFactor;
		rad[1] /= subFactor;
		bounds = null;
	}

	@Override
	public RingROI copy() {
		RingROI c = new RingROI(spt[0], spt[1], rad[0], rad[1], dpp, clippingCompensation);
		c.setAverageArea(averageArea);
		c.name = name;
		c.plot = plot;
		return c;
	}

	@Override
	public Map<String, Double> getROIInfos() {
		Map<String, Double> roiInfos = new LinkedHashMap<String, Double>(4);
		roiInfos.put("X Centre", getPointX());
		roiInfos.put("Y Centre", getPointY());
		roiInfos.put("Inner Radius", getRadii()[0]);
		roiInfos.put("Outer Radius", getRadii()[1]);
		return roiInfos;
	}

	@Override
	public String toString() {
		return super.toString() + String.format("Centre %s Radii %s", Arrays.toString(spt), Arrays.toString(rad));
	}
}
