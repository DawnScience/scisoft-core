/*-
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

import java.io.Serializable;

/**
 * Base class for general region of interest
 */
public class ROIBase implements IROI, Serializable {
	protected String name;
	protected double spt[]; // start or centre coordinates
	protected boolean plot;

	protected transient RectangularROI bounds;

	public ROIBase() {
		spt = new double[2];
	}

	protected void setDirty() {
		bounds = null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setPoint(double[] point) {
		spt = point;
		setDirty();
	}

	@Override
	public void setPoint(int[] point) {
		setPoint(point[0], point[1]);
	}

	@Override
	public void setPoint(int x, int y) {
		spt[0] = x;
		spt[1] = y;
		setDirty();
	}

	@Override
	public void setPoint(double x, double y) {
		spt[0] = x;
		spt[1] = y;
		setDirty();
	}

	@Override
	public double[] getPointRef() {
		return spt;
	}

	@Override
	public double[] getPoint() {
		return spt.clone();
	}

	@Override
	public double getPointX() {
		return spt[0];
	}

	@Override
	public double getPointY() {
		return spt[1];
	}

	@Override
	public int[] getIntPoint() {
		return new int[] { (int) spt[0], (int) spt[1] };
	}

	@Override
	public void addPoint(int[] pt) {
		spt[0] += pt[0];
		spt[1] += pt[1];
		setDirty();
	}

	@Override
	public void addPoint(double[] pt) {
		spt[0] += pt[0];
		spt[1] += pt[1];
		setDirty();
	}

	@Override
	public void addPoint(double x, double y) {
		spt[0] += x;
		spt[1] += y;
		setDirty();
	}

	@Override
	public ROIBase copy() {
		ROIBase c = new ROIBase();
		c.name = name;
		c.spt = spt.clone();
		c.plot = plot;
		return c;
	}

	@Override
	public void downsample(double subFactor) {
		spt[0] /= subFactor;
		spt[1] /= subFactor;
		setDirty();
	}

	@Override
	public void setPlot(boolean require) {
		plot = require;
	}

	@Override
	public boolean isPlot() {
		return plot;
	}

	@Override
	public RectangularROI getBounds() {
		if (bounds == null)
			bounds = new RectangularROI(spt[0], spt[1], 0, 0, 0);
		return bounds;
	}

	@Override
	public boolean containsPoint(double x, double y) {
		return getBounds().containsPoint(x, y);
	}

	public boolean containsPoint(double[] pt) {
		return containsPoint(pt[0], pt[1]);
	}

	@Override
	public boolean isNearOutline(double x, double y, double distance) {
		RectangularROI b = getBounds().copy();
		b.addPoint(-distance, -distance);
		b.addToLengths(2*distance, 2*distance);
		return b.containsPoint(x, y);
	}

	public boolean isNearOutline(double[] pt, double distance) {
		return isNearOutline(pt[0],  pt[1], distance);
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
		return name == null ? "" : String.format("name=%s, ", name);
	}

	/**
	 * @param y ordinate of line
	 * @return true if given horizontal line intersects ROI
	 */
	protected boolean intersectHorizontal(double y) {
		RectangularROI r = getBounds();
		y -= r.spt[1];
		return y >= 0 && y <= r.len[1];
	}

	@Override
	public double[] findHorizontalIntersections(double y) {
		return null;
	}
}
