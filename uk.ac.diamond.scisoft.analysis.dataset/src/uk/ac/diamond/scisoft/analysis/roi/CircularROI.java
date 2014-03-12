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

import java.io.Serializable;
import java.util.Arrays;

/**
 * A circular region of interest
 */
public class CircularROI extends ROIBase implements IParametricROI, Serializable {
	private double rad;

	/**
	 * Create a unit circle centred on origin
	 */
	public CircularROI() {
		this(1, 0, 0);
	}

	/**
	 * Create circle of given radius centred on origin
	 * @param radius
	 */
	public CircularROI(double radius) {
		this(radius, 0, 0);
	}

	/**
	 * Create circle of given radius with specified centre
	 * @param radius
	 * @param ptx centre point x value
	 * @param pty centre point y value
	 */
	public CircularROI(double radius, double ptx, double pty) {
		spt = new double[] { ptx, pty };
		rad = Math.abs(radius);
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		rad /= subFactor;
		bounds = null;
	}

	@Override
	public CircularROI copy() {
		CircularROI c = new CircularROI(rad, spt[0], spt[1]);
		c.name = name;
		c.plot = plot;
		return c;
	}

	/**
	 * @return radius
	 */
	public double getRadius() {
		return rad;
	}

	/**
	 * Set radius
	 * @param radius
	 */
	public void setRadius(double radius) {
		rad = radius;
		bounds = null;
	}

	/**
	 * Get point on circle at given angle
	 * @param angle in radians
	 * @return point 
	 */
	@Override
	public double[] getPoint(double angle) {
		return new double[] { spt[0] + rad*Math.cos(angle), 
				spt[1] + rad*Math.sin(angle) };
	}

	/**
	 * Get point on circle at given angle
	 * @param angle in degrees
	 * @return point 
	 */
	public double[] getPointDegrees(double angle) {
		return getPoint(Math.toRadians(angle));
	}

	/**
	 * Get centre point
	 * @return x and y coordinates in double array
	 */
	public double[] getCentre() {
		return getPoint();
	}

	/**
	 * Set centre point
	 * @param centre
	 */
	public void setCentre(double... centre) {
		setPoint(centre);
	}

	@Override
	public RectangularROI getBounds() {
		if (bounds == null) {
			bounds = new RectangularROI();
			bounds.setPoint(spt[0] - rad, spt[1] - rad);
			bounds.setLengths(2 * rad, 2 * rad);
		}
		return bounds;
	}

	@Override
	public boolean containsPoint(double x, double y) {
		return Math.hypot(x - spt[0], y - spt[1]) <= rad;
	}

	@Override
	public boolean isNearOutline(double x, double y, double distance) {
		double r = Math.hypot(x - spt[0], y - spt[1]);

		return Math.abs(r - rad) <= distance;
	}

	@Override
	public String toString() {
		return super.toString() + String.format("point=%s, radius=%g", Arrays.toString(spt), rad);
	}
	
	/**
	 * Calculate values for angle at which circle will intersect vertical line of given x
	 * @param x
	 * @return possible angles
	 */
	@Override
	public double[] getVerticalIntersectionParameters(double x) {

		x -= spt[0];
		if (x < -rad || x > rad) {
			return null;
		}

		if (x == -rad || x == rad) { // touching case
			return new double[]{x < 0 ? Math.PI : 0};
		}
		
		double ang = Math.acos(x/rad);
		
		return new double[] {ang, (Math.PI*2)-ang};
	}

	/**
	 * Calculate values for angle at which circle will intersect horizontal line of given y
	 * @param y
	 * @return possible angles
	 */
	@Override
	public double[] getHorizontalIntersectionParameters(double y) {

		y -= spt[1];
		if (y < -rad || y > rad) {
			return null;
		}

		if (y == -rad || y == rad) { // touching case
			return new double[]{y < 0 ? Math.PI : 0};
		}
		
		double ang = Math.acos(y/rad);
		
		return new double[] {ang, (Math.PI*2)-ang};
	}
}
