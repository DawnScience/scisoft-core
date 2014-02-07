/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.persistence.bean.roi;

import java.util.Arrays;

public class EllipticalROIBean extends ROIBean {

	public static final String TYPE = "EllipticalROI";
	private double[] semiAxes; // semi-axes
	private double angle; // angles in radians

	public EllipticalROIBean() {
		this.type = TYPE;
	}

	/**
	 * @return Returns reference to the semi-axes
	 */
	public double[] getSemiAxes() {
		return semiAxes;
	}

	/**
	 * Set semi-axis values
	 * @param semiAxis
	 */
	public void setSemiAxes(double[] semiAxis) {
		this.semiAxes = semiAxis;
	}

	/**
	 * @return Returns the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @param angle The major axis angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * @return true if ellipse is circular (i.e. its axes have the same length)
	 */
	public boolean isCircular() {
		return semiAxes[0] == semiAxes[1];
	}

	/**
	 * @return Returns the angle in degrees
	 */
	public double getAngleDegrees() {
		return Math.toDegrees(angle);
	}

	@Override
	public String toString() {
		if (isCircular()) {
			return super.toString() + String.format("point=%s, radius=%g, angle=%g", Arrays.toString(getStartPoint()), semiAxes[0], getAngleDegrees());
		}
		return super.toString() + String.format("point=%s, semiaxes=%s, angle=%g", Arrays.toString(getStartPoint()), Arrays.toString(semiAxes), getAngleDegrees());
	}
}
