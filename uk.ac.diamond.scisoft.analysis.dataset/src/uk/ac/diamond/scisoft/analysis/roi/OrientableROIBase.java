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

public class OrientableROIBase extends ROIBase implements IOrientableROI, Serializable {
	protected double ang;     // angle in radians
	protected double cang;
	protected double sang;

	/**
	 * @return Returns the angle in degrees
	 */
	@Override
	public double getAngleDegrees() {
		return Math.toDegrees(ang);
	}

	/**
	 * @param angle The angle in degrees to set
	 */
	@Override
	public void setAngleDegrees(double angle) {
		setAngle(Math.toRadians(angle));
	}

	private final static double TWOPI = 2.0 * Math.PI;
	/**
	 * Make sure angle lie in permitted ranges:
	 *  0 <= ang < 2*pi
	 */
	protected void checkAngle() {
		while (ang < 0) {
			ang += TWOPI;
		}
		while (ang >= TWOPI) {
			ang -= TWOPI;
		}
		cang = Math.cos(ang);
		sang = Math.sin(ang);
	}

	/**
	 * @return Returns the angle
	 */
	@Override
	public double getAngle() {
		return ang;
	}

	/**
	 * @param angle The major axis angle to set
	 */
	@Override
	public void setAngle(double angle) {
		ang = angle;
		checkAngle();
		bounds = null;
	}
}
