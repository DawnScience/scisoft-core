/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.dataset.roi;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.roi.IROI;

/**
 * Base class for general region of interest
 */
public class ROIBase implements IROI, Serializable {
	private static final long serialVersionUID = 3637155644794033370L;

	protected String name;
	protected double spt[]; // start or centre coordinates
	protected boolean plot;
	protected boolean fixed;

	protected transient RectangularROI bounds;

	public ROIBase() {
		spt = new double[2];
	}

	/**
	 * Copy constructor
	 * @param orig
	 */
	public ROIBase(ROIBase orig) {
		name = orig.name;
		spt = orig.spt.clone();
		plot = orig.plot;
		fixed = orig.fixed;
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
	public void setValue(int i, double value) {
		spt[i] = value;
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
	public double getValue(int i) {
		return spt[i];
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
		return new ROIBase(this);
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

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
		return String.format("%splot=%s, fixed=%s, ", name == null ? "" : String.format("name=%s, ", name),
				plot, fixed);
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

	/**
	 * @param a
	 * @param b
	 * @return true if a is within 1 ulp of, or greater than, b
	 */
	protected static boolean closelyEqOrGt(double a, double b) {
		double d = a - b;
		return d >= -Math.max(Math.ulp(a), Math.ulp(b));
	}

	/**
	 * @param a
	 * @param b
	 * @return true if a is within 1 ulp of b
	 */
	protected static boolean closelyEq(double a, double b) {
		double d = Math.abs(a - b);
		return d <= Math.max(Math.ulp(a), Math.ulp(b));
	}

	/**
	 * @param a
	 * @param b
	 * @return true if a is within 1 ulp of, or less than, b
	 */
	protected static boolean closelyEqOrLt(double a, double b) {
		double d = a - b;
		return d <= Math.max(Math.ulp(a), Math.ulp(b));
	}

	/**
	 * @param a
	 * @param b
	 * @return true if a is not within 1 ulp of b
	 */
	protected static boolean notClose(double a, double b) {
		double d = Math.abs(a - b);
		return d > Math.max(Math.ulp(a), Math.ulp(b));
	}

	/**
	 * Check if closely equal. Corresponding NaNs are consider equal.
	 * @param a
	 * @param b
	 * @return true if every element of a is with in 1 ulp of the corresponding element b
	 */
	protected static boolean closelyEq(double[] a, double[] b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}

		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i < a.length; i++) {
			double x = a[i], y = b[i];
			if (Double.isNaN(x)) {
				if (!Double.isNaN(y)) {
					return false;
				}
			} else {
				if (Double.isNaN(y)) {
					return false;
				}
				if (notClose(x, y)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	@Override
	public boolean isFixed() {
		return fixed;
	}
}
