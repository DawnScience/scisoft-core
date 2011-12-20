/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
