/*
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

package uk.ac.diamond.scisoft.analysis.coords;

/**
 * Class to hold and utilise a rotation
 */
public class RotatedCoords {
	double cang, sang;

	/**
	 * @param angle in degrees
	 */
	public RotatedCoords(double angle) {
		cang = Math.cos(Math.toRadians(angle));
		sang = Math.sin(Math.toRadians(angle));
	}

	/**
	 * @param angle
	 * @param isDegrees 
	 */
	public RotatedCoords(double angle, boolean isDegrees) {
		if (isDegrees) {
			cang = Math.cos(Math.toRadians(angle));
			sang = Math.sin(Math.toRadians(angle));
		} else {
			cang = Math.cos(angle);
			sang = Math.sin(angle);
		}
	}

	/**
	 * @param rx 
	 * @param ry 
	 * @return array with original Cartesian coordinates
	 */
	public double[] transformToOriginal(double rx, double ry) {
		double[] car = { rx * cang - ry * sang, rx * sang + ry * cang };
		return car;
	}

	/**
	 * @param ox 
	 * @param oy 
	 * @return array with rotated Cartesian coordinates
	 */
	public double[] transformToRotated(double ox, double oy) {
		double[] car = { ox * cang + oy * sang, -ox * sang + oy * cang };
		return car;
	}
}
