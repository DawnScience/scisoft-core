/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

/**
 * An elliptical region of interest with the start point as the centre
 */
public class EllipticalROI extends OrientableROIBase implements IParametricROI {
	private double[] saxis; // semi-axes

	/**
	 * No argument constructor need for serialization
	 */
	public EllipticalROI() {
		this(1, 1, 0, 0, 0);
	}

	/**
	 * Create a circular ROI
	 * @param croi
	 */
	public EllipticalROI(CircularROI croi) {
		this(croi.getRadius(), croi.getRadius(), 0, croi.getPointX()	, croi.getPointY());
	}

	/**
	 * Create a circular ROI
	 * @param radius
	 * @param ptx centre point x value
	 * @param pty centre point y value
	 */
	public EllipticalROI(double radius, double ptx, double pty) {
		this(radius, radius, 0, ptx, pty);
	}

	/**
	 * Create an elliptical ROI
	 * @param major semi-axis
	 * @param minor semi-axis
	 * @param angle major axis angle
	 * @param ptx centre point x value
	 * @param pty centre point y value
	 */
	public EllipticalROI(double major, double minor, double angle, double ptx, double pty) {
		spt = new double[] { ptx, pty };
		saxis = new double[] { major, minor };
		ang = angle;
		checkAngle();
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		saxis[0] /= subFactor;
		saxis[1] /= subFactor;
	}

	@Override
	public EllipticalROI copy() {
		EllipticalROI c = new EllipticalROI(saxis[0], saxis[1], ang, spt[0], spt[1]);
		c.name = name;
		c.plot = plot;
		return c;
	}

	/**
	 * @return Returns reference to the semi-axes
	 */
	public double[] getSemiAxes() {
		return saxis;
	}

	/**
	 * @param index (should be 0 or 1 for major or minor axis)
	 * @return Returns the semi-axis value
	 */
	public double getSemiAxis(int index) {
		if (index < 0 || index > 1)
			throw new IllegalArgumentException("Index should be 0 or 1");
		return saxis[index];
	}

	/**
	 * Set semi-axis values
	 * @param semiaxis
	 */
	public void setSemiAxes(double[] semiaxis) {
		if (saxis.length < 2)
			throw new IllegalArgumentException("Need at least two semi-axis values");
		saxis[0] = semiaxis[0];
		saxis[1] = semiaxis[1];
		bounds = null;
	}

	/**
	 * Set semi-axis values
	 * @param semiaxis
	 */
	public void setSemiaxes(double[] semiaxis) {
		setSemiAxes(semiaxis);
	}

	/**
	 * Set semi-axis value
	 * @param index (should be 0 or 1 for major or minor axis)
	 * @param semiaxis
	 */
	public void setSemiAxis(int index, double semiaxis) {
		if (index < 0 || index > 1)
			throw new IllegalArgumentException("Index should be 0 or 1");
		saxis[index] = semiaxis;
		bounds = null;
	}

	/**
	 * For Jython
	 * @param angle The angle in degrees to set
	 */
	public void setAngledegrees(double angle) {
		setAngleDegrees(angle);
	}

	/**
	 * @return true if ellipse is circular (i.e. its axes have the same length)
	 */
	public boolean isCircular() {
		return saxis[0] == saxis[1];
	}

	/**
	 * @return aspect ratio, i.e. ratio of major to minor axes
	 */
	public double getAspectRatio() {
		return saxis[0] / saxis[1];
	}

	/**
	 * Get point on ellipse at given angle
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
	 * Get point on ellipse at given angle relative to centre
	 * @param angle in radians
	 * @return point 
	 */
	public double[] getRelativePoint(double angle) {
		double cb = Math.cos(angle);
		double sb = Math.sin(angle);

		return new double[] { saxis[0] * cang * cb - saxis[1] * sang * sb,
				saxis[0] * sang * cb + saxis[1] * cang * sb };
	}

	/**
	 * Get point on ellipse at given angle
	 * @param angle in degrees
	 * @return point 
	 */
	public double[] getPointDegrees(double angle) {
		return getPoint(Math.toRadians(angle));
	}

	/**
	 * Get distance from centre to point on ellipse at given angle
	 * @param angle in radians
	 * @return distance
	 */
	public double getDistance(double angle) {
		double[] p = getRelativePoint(angle);
		return Math.hypot(p[0], p[1]);
	}

	@Override
	public RectangularROI getBounds() {
		if (bounds == null) {
			// angles which produce stationary points in x and y
			double[] angles = new double[] { Math.atan2(-saxis[1] * sang, saxis[0] * cang),
					Math.atan2(saxis[1] * cang, saxis[0] * sang) };

			double[] max = getRelativePoint(angles[0]);
			double[] min = max.clone();

			ROIUtils.updateMaxMin(max, min, getRelativePoint(angles[0] + Math.PI));

			ROIUtils.updateMaxMin(max, min, getRelativePoint(angles[1]));

			ROIUtils.updateMaxMin(max, min, getRelativePoint(angles[1] + Math.PI));

			bounds = new RectangularROI();
			bounds.setLengths(max[0] - min[0], max[1] - min[1]);
			bounds.setPoint(spt[0] + min[0], spt[1] + min[1]);
		}
		return bounds;
	}

	protected double getAngleRelative(double x, double y) {
		return Math.atan2(saxis[0]*(cang*y - sang*x), saxis[1]*(cang*x + sang*y));
	}

	@Override
	public boolean containsPoint(double x, double y) {
		x -= spt[0];
		y -= spt[1];
		double a = getAngleRelative(x, y);
		return Math.hypot(x, y) <= getDistance(a);
	}

	@Override
	public boolean isNearOutline(double x, double y, double distance) {
		x -= spt[0];
		y -= spt[1];
		double a = getAngleRelative(x, y);
		return Math.abs(getDistance(a) - Math.hypot(x, y)) <= distance;
	}

	/**
	 * Determine if ellipse is within an axis-aligned rectangle
	 * @param rect
	 * @return true if ellipse lies wholly within rectangle
	 */
	public boolean isContainedBy(RectangularROI rect) {
		double as = saxis[0]*saxis[0];
		double bs = saxis[1]*saxis[1];
		double dx = Math.sqrt(as*cang*cang + bs*sang*sang);
		double dy = Math.sqrt(as*sang*sang + bs*cang*cang);
		double ang = Math.abs(rect.getAngle());
		double[] a = rect.getPointRef();
		double[] b = rect.getEndPoint();
		if (ang == 0 || ang == Math.PI) {
			// do nothing
		} else if (ang == 0.5*Math.PI || ang == 1.5*Math.PI) {
			double t = dx;
			dx = dy;
			dy = t;
		} else {
			throw new UnsupportedOperationException("Non-axis-aligned rectangles are not implemented yet");
		}

		double x = a[0] - spt[0];
		if (x > -dx)
			return false;
		x = b[0] - spt[0];
		if (x < dx)
			return false;

		double y = a[1] - spt[1];
		if (y > -dy)
			return false;
		y = b[1] - spt[1];
		if (y < dy)
			return false;

		return true;
	}

	@Override
	public String toString() {
		if (isCircular()) {
			return super.toString() + String.format("point=%s, radius=%g, angle=%g", Arrays.toString(spt), saxis[0], getAngleDegrees());
		}
		return super.toString() + String.format("point=%s, semiaxes=%s, angle=%g", Arrays.toString(spt), Arrays.toString(saxis), getAngleDegrees());
	}
}
