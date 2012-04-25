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

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.fitting.EllipseFitter;

/**
 * An elliptical region of interest
 */
public class EllipticalROI extends ROIBase {
	private double saxis[]; // semi-axes
	private double ang; // angles in radians

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
	}

	/**
	 * Fit an ellipse to given polygon
	 * @param polygon
	 * @return ellipse ROI
	 */
	public static EllipticalROI fitEllipse(PolygonalROI polygon) {
		int n = polygon.getSides();
		double[] x = new double[n];
		double[] y = new double[n];
		int i = 0;
		for (ROIBase r : polygon) {
			x[i] = r.getPointX();
			y[i] = r.getPointY();
			i++;
		}

		DoubleDataset dx = new DoubleDataset(x);
		DoubleDataset dy = new DoubleDataset(y);
		EllipseFitter f = new EllipseFitter();
		f.geometricFit(dx, dy, null);
		double[] p = f.getParameters();
		
		return new EllipticalROI(p[0], p[1], p[2], p[3], p[4]);
	}

	/**
	 * @return Returns the semi-axes
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
	}

	/**
	 * @return Returns the angle in degrees
	 */
	public double getAngleDegrees() {
		return Math.toDegrees(ang);
	}

	/**
	 * @param angle The angle in degrees to set
	 */
	public void setAngleDegrees(double angle) {
		ang = Math.toRadians(angle);
		checkAngle();
	}

	private final static double TWOPI = 2.0 * Math.PI;
	/**
	 * Make sure angle lie in permitted ranges:
	 *  0 <= ang <= 2*pi
	 */
	private void checkAngle() {
		while (ang < 0) {
			ang += TWOPI;
		}
		while (ang > TWOPI) {
			ang -= TWOPI;
		}
	}

	/**
	 * @return Returns the angle
	 */
	public double getAngle() {
		return ang;
	}

	/**
	 * @param angle The major axis angle to set
	 */
	public void setAngle(double angle) {
		ang = angle;
	}

	/**
	 * @return true if ellipse is circular (i.e. its axes have the same length)
	 */
	public boolean isCircular() {
		return saxis[0] == saxis[1];
	}
}
