/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;

/**
 * Base class for general region of interest
 */
public class ROIBase implements Serializable {
	protected double spt[]; // start or centre coordinates
	protected boolean plot;

	public ROIBase() {
	}

	/**
	 * @param point The start (or centre) point to set
	 */
	public void setPoint(double point[]) {
		spt = point;
	}

	/**
	 * @param point The start (or centre) point to set
	 */
	public void setPoint(int point[]) {
		spt[0] = point[0];
		spt[1] = point[1];
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setPoint(int x, int y) {
		spt[0] = x;
		spt[1] = y;
	}

	/**
	 * @param x 
	 * @param y 
	 */
	public void setPoint(double x, double y) {
		spt[0] = x;
		spt[1] = y;
	}

	/**
	 * @return Returns the start (or centre) point
	 */
	public double[] getPoint() {
		return spt;
	}

	/**
	 * @return Returns the start (or centre) point
	 */
	public int[] getIntPoint() {
		return new int[] { (int) spt[0], (int) spt[1] };
	}

	/**
	 * Add an offset to start (or centre) point
	 * 
	 * @param pt
	 */
	public void addPoint(int[] pt) {
		spt[0] += pt[0];
		spt[1] += pt[1];
	}

	/**
	 * @return a copy
	 */
	public ROIBase copy() {
		ROIBase c = new ROIBase();
		c.spt = spt.clone();
		c.plot = plot;
		return c;
	}

	/**
	 * To account for a down-sampling of the dataset, change ROI
	 * @param subFactor
	 */
	public void downsample(double subFactor) {
		spt[0] /= subFactor;
		spt[1] /= subFactor;
	}

	/**
	 * @param require set true if plot required 
	 */
	public void setPlot(boolean require) {
		plot = require;
	}

	/**
	 * @return true if plot is enabled
	 */
	public boolean isPlot() {
		return plot;
	}
}
