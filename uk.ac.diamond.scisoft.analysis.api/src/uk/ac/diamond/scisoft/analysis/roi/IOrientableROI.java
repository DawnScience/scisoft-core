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

/**
 * Represents an orientable region of interest with the start point being the centre of rotation
 */
public interface IOrientableROI extends IROI {
	@Override
	public IOrientableROI copy();

	/**
	 * @return angle, in degrees, from 0 to 90 is from x-axis to y-axis
	 */
	public double getAngleDegrees();

	/**
	 * Set angle, in degrees
	 * @param degrees from 0 to 90 is from x-axis to y-axis
	 */
	public void setAngleDegrees(double degrees);

	/**
	 * @return angle, in radians
	 */
	public double getAngle();

	/**
	 * Set angle, in radians, from 0 to pi/2 is from x-axis to y-axis
	 * @param angle from 0 to pi/2 is from x-axis to y-axis
	 */
	public void setAngle(double angle);
}
