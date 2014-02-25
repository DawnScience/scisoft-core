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

import java.util.Arrays;

import uk.ac.diamond.scisoft.analysis.coords.RotatedCoords;

/**
 * A hyperbolic region of interest with the start point as the focus. In the rotated frame,
 * it can be represented as (x+l/e)^2 / a^2 - y^2 / b^2 = 1, where l = b^2/a and e = sqrt(1 + b^2/a^2)
 */
public class HyperbolicROI extends OrientableROIBase {
	private double l;   // semi-latus rectum
	private double e;   // eccentricity

	/**
	 * No argument constructor need for serialization
	 */
	public HyperbolicROI() {
		this(1, Math.sqrt(2), 0, 0);
	}

	/**
	 * Create a hyperbolic ROI
	 * @param semi semi-latus rectum (half length of chord parallel to directrix, passing through focus)
	 * @param eccentricity measure of non-circularity (>1)
	 * @param ptx centre point x value
	 * @param pty centre point y value
	 */
	public HyperbolicROI(double semi, double eccentricity, double ptx, double pty) {
		this(semi, eccentricity, 0, ptx, pty);
	}

	/**
	 * Create a hyperbolic ROI
	 * @param semi semi-latus rectum (half length of chord parallel to directrix, passing through focus)
	 * @param eccentricity measure of non-circularity (>1)
	 * @param angle major axis angle
	 * @param ptx centre point x value
	 * @param pty centre point y value
	 */
	public HyperbolicROI(double semi, double eccentricity, double angle, double ptx, double pty) {
		spt = new double[] { ptx, pty };
		l = semi;
		e = eccentricity;
		ang = angle;
		checkAngle();
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		l /= subFactor;
	}

	@Override
	public HyperbolicROI copy() {
		HyperbolicROI c = new HyperbolicROI(l, ang, spt[0], spt[1]);
		c.name = name;
		c.plot = plot;
		return c;
	}

	/**
	 * @return Returns semi-latus rectum
	 */
	public double getSemilatusRectum() {
		return l;
	}

	/**
	 * Set semi-latus rectum
	 * @param semi
	 */
	public void setSemilatusRectum(double semi) {
		l = semi;
		bounds = null;
	}

	/**
	 * @return eccentricity
	 */
	public double getEccentricity() {
		return e;
	}

	/**
	 * Set eccentricity
	 * @param eccentricity
	 */
	public void setEccentricity(double eccentricity) {
		e = eccentricity;
		bounds = null;
	}

	/**
	 * Get point on hyperbola at given angle
	 * @param angle in radians
	 * @return point 
	 */
	public double[] getPoint(double angle) {
		double[] pt = getRelativePoint(angle);
		pt[0] += spt[0];
		pt[1] += spt[1];
		return pt;
	}

	/**
	 * Get point on hyperbola at given angle relative to focus
	 * @param angle in radians
	 * @return point 
	 */
	public double[] getRelativePoint(double angle) {
		if (src == null)
			src = new RotatedCoords(ang, false);

		double cb = Math.cos(angle);
		double denom = 1 - e * cb;
		// NB when this is negative, the point is on other nappe of double cone
		if (denom == 0) {
			double[] pt = src.transformToOriginal(1, 0);
			if (pt[0] != 0)
				pt[0] *= Double.POSITIVE_INFINITY;
			if (pt[1] != 0)
				pt[1] *= Double.POSITIVE_INFINITY;
			return pt;
		}
		double sb = Math.sin(angle);
		double r = l / denom;

		return src.transformToOriginal(r * cb, r * sb);
	}

	/**
	 * @param d
	 * @return start angle of positive branch at distance from focus (end = 2pi - start)
	 */
	public double getStartAngle(double d) {
		return Math.acos((1 - l/d)/e);
	}

	private transient RotatedCoords src = null;

	/**
	 * Get point on hyperbolic at given angle
	 * @param angle in degrees
	 * @return point 
	 */
	public double[] getPointDegrees(double angle) {
		return getPoint(Math.toRadians(angle));
	}

	/**
	 * Get distance from focus to point on hyperbolic at given angle
	 * @param angle in radians
	 * @return distance
	 */
	public double getDistance(double angle) {
		double[] p = getRelativePoint(angle);
		return Math.hypot(p[0], p[1]);
	}

	/**
	 * <emph>Important:</emph> this returns null as a hyperbolic is unbounded
	 */
	@Override
	public RectangularROI getBounds() {
		return null;
	}

	/**
	 * Determine if point is on or inside hyperbolic
	 * @param x
	 * @param y
	 * @return true if hyperbolic contains point
	 */
	@Override
	public boolean containsPoint(double x, double y) {
		return false;
	}

	@Override
	public boolean isNearOutline(double x, double y, double distance) {
		x -= spt[0];
		y -= spt[1];

		if (src == null)
			src = new RotatedCoords(ang, false);

		double[] pt = src.transformToRotated(x, y);
		return Math.abs(pt[0] * pt[0] - 2 * l * pt[1]) <= distance; // FIXME
	}

	@Override
	public String toString() {
		return super.toString() + String.format("point=%s, focal=%g, angle=%g", Arrays.toString(spt), l, getAngleDegrees());
	}
}
