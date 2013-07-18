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

package uk.ac.diamond.scisoft.analysis.roi;

/**
 * Base class for general region of interest
 */
public class ROIBase implements IROI {
	protected String name;
	protected double spt[]; // start or centre coordinates
	protected boolean plot;
	
	/**
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param point The start (or centre) point to set
	 */
	@Override
	public void setPoint(double[] point) {
		spt = point;
	}

	/**
	 * @param point The start (or centre) point to set
	 */
	@Override
	public void setPoint(int[] point) {
		spt[0] = point[0];
		spt[1] = point[1];
	}

	/**
	 * @param x
	 * @param y
	 */
	@Override
	public void setPoint(int x, int y) {
		spt[0] = x;
		spt[1] = y;
	}

	/**
	 * @param x 
	 * @param y 
	 */
	@Override
	public void setPoint(double x, double y) {
		spt[0] = x;
		spt[1] = y;
	}

	/**
	 * @return Returns reference to the start (or centre) point
	 */
	@Override
	public double[] getPointRef() {
		return spt;
	}

	/**
	 * @return Returns copy of the start (or centre) point
	 */
	@Override
	public double[] getPoint() {
		return spt.clone();
	}

	/**
	 * @return Returns the start (or centre) point's x value
	 */
	@Override
	public double getPointX() {
		return spt[0];
	}

	/**
	 * @return Returns the start (or centre) point's y value
	 */
	@Override
	public double getPointY() {
		return spt[1];
	}

	/**
	 * @return Returns the start (or centre) point
	 */
	@Override
	public int[] getIntPoint() {
		return new int[] { (int) spt[0], (int) spt[1] };
	}

	/**
	 * Add an offset to start (or centre) point
	 * 
	 * @param pt
	 */
	@Override
	public void addPoint(int[] pt) {
		spt[0] += pt[0];
		spt[1] += pt[1];
	}

	/**
	 * @return a copy
	 */
	@Override
	public ROIBase copy() {
		ROIBase c = new ROIBase();
		c.name = name;
		c.spt = spt.clone();
		c.plot = plot;
		return c;
	}

	/**
	 * To account for a down-sampling of the dataset, change ROI
	 * @param subFactor
	 */
	@Override
	public void downsample(double subFactor) {
		spt[0] /= subFactor;
		spt[1] /= subFactor;
	}

	/**
	 * @param require set true if plot required 
	 */
	@Override
	public void setPlot(boolean require) {
		plot = require;
	}

	/**
	 * @return true if plot is enabled
	 */
	@Override
	public boolean isPlot() {
		return plot;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ROIBase) {
			ROIBase that = (ROIBase) obj;
			if (spt == null || that.spt == null)
				return false;
			if (spt.length != that.spt.length)
				return false;

			for (int i = spt.length-1; i>=0; i--) {
				if (Double.doubleToLongBits(spt[i]) != Double.doubleToLongBits(that.spt[i]))
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (spt == null || spt.length == 0)
			return 0;
		int h = 0;
		for (int i = spt.length-1; i>=0; i--) {
			long l = Double.doubleToLongBits(spt[i]) + 31;
			h = (int) (h*17L + l);
		}
		return h;
	}

	@Override
	public String toString() {
		return name == null ? "" : String.format("Name %s ", name);
	}
}
