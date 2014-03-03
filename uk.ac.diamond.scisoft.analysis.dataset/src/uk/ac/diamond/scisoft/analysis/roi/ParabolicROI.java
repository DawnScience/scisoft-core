/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

/**
 * A parabolic region of interest with the start point as the focus. In the rotated frame,
 * it can be represented as x-p = 4 a y^2 where p = 2 a
 */
public class ParabolicROI extends OrientableROIBase implements IParametricROI, Serializable {
	private double tp;   // twice focal parameter (or latus rectum) 

	/**
	 * No argument constructor need for serialization
	 */
	public ParabolicROI() {
		this(2, 0, 0, 0);
	}

	/**
	 * Create a parabolic ROI
	 * @param focal length
	 * @param ptx centre point x value
	 * @param pty centre point y value
	 */
	public ParabolicROI(double focal, double ptx, double pty) {
		this(focal, 0, ptx, pty);
	}

	/**
	 * Create a parabolic ROI
	 * @param focal length
	 * @param angle major axis angle
	 * @param ptx centre point x value
	 * @param pty centre point y value
	 */
	public ParabolicROI(double focal, double angle, double ptx, double pty) {
		spt = new double[] { ptx, pty };
		setFocalParameter(focal);
		ang = angle;
		checkAngle();
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		tp /= subFactor;
	}

	@Override
	public ParabolicROI copy() {
		ParabolicROI c = new ParabolicROI(0.5*tp, ang, spt[0], spt[1]);
		c.name = name;
		c.plot = plot;
		return c;
	}

	/**
	 * @return Returns focal parameter
	 */
	public double getFocalParameter() {
		return 0.5*tp;
	}

	/**
	 * Set focal parameter
	 * @param focal
	 */
	public void setFocalParameter(double focal) {
		tp = 2*focal;
		bounds = null;
	}

	/**
	 * Get point on parabola at given angle
	 * @param angle in radians
	 * @return point 
	 */
	@Override
	public double[] getPoint(double angle) {
		double[] pt = getRelativePoint(angle);
		pt[0] += spt[0];
		pt[1] += spt[1];
		return pt;
	}

	/**
	 * Get point on parabola at given angle relative to focus
	 * @param angle in radians
	 * @return point 
	 */
	public double[] getRelativePoint(double angle) {
		double cb = Math.cos(angle);
		if (cb == 1) {
			double[] pt = transformToOriginal(1, 0);
			if (pt[0] != 0)
				pt[0] *= Double.POSITIVE_INFINITY;
			if (pt[1] != 0)
				pt[1] *= Double.POSITIVE_INFINITY;
			return pt;
		}
		double sb = Math.sin(angle);
		double r = tp / (1 - cb);

		return transformToOriginal(r * cb, r * sb);
	}

	/**
	 * @param d
	 * @return start angle at distance from focus (end = 2pi - start)
	 */
	public double getStartAngle(double d) {
		return Math.acos(1 - tp/d);
	}

	/**
	 * Get point on parabola at given angle
	 * @param angle in degrees
	 * @return point 
	 */
	public double[] getPointDegrees(double angle) {
		return getPoint(Math.toRadians(angle));
	}

	/**
	 * Get distance from focus to point on parabola at given angle
	 * @param angle in radians
	 * @return distance
	 */
	public double getDistance(double angle) {
		double[] p = getRelativePoint(angle);
		return Math.hypot(p[0], p[1]);
	}

	/**
	 * <emph>Important:</emph> this returns null as a parabola is unbounded
	 */
	@Override
	public RectangularROI getBounds() {
		return null;
	}

	/**
	 * Determine if point is on or inside ellipse
	 * @param x
	 * @param y
	 * @return true if ellipse contains point
	 */
	@Override
	public boolean containsPoint(double x, double y) {
		return false;
	}

	@Override
	public boolean isNearOutline(double x, double y, double distance) {
		x -= spt[0];
		y -= spt[1];

		double[] pt = transformToRotated(x, y);
		return Math.abs(pt[0] * pt[0] - tp * pt[1]) <= distance;
	}

	@Override
	public String toString() {
		return super.toString() + String.format("point=%s, focal=%g, angle=%g", Arrays.toString(spt),
				getFocalParameter(), getAngleDegrees());
	}
}
