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
 * 
 * Usual definition of 2D polar coordinates with respect to 2D Cartesian coordinate system
 *
 */
public class SectorCoords {
	double x, y;
	double r, p;

	/**
	 * @param ca
	 * @param cb
	 * @param isCartesian
	 */
	public SectorCoords(double ca, double cb, boolean isCartesian) {
		if (isCartesian) {
			x = ca;
			y = cb;
			r = Math.hypot(x, y);
			p = Math.atan2(y, x);
			if (p < 0) p += 2*Math.PI;
		} else {
			r = ca;
			p = Math.toRadians(cb);
			x = r*Math.cos(p);
			y = r*Math.sin(p);
		}
	}

	/**
	 * @param spt 
	 * @param pt
	 */
	public SectorCoords(int[] spt, int[] pt) {
		int px = pt[0] - spt[0];
		int py = pt[1] - spt[1];
		r = Math.hypot(px, py);
		p = Math.atan2(py, px);
		if (p < 0) p += 2*Math.PI;
	}

	/**
	 * @param spt 
	 * @param pt
	 */
	public SectorCoords(double[] spt, int[] pt) {
		double px = pt[0] - spt[0];
		double py = pt[1] - spt[1];
		r = Math.hypot(px, py);
		p = Math.atan2(py, px);
		if (p < 0) p += 2*Math.PI;
	}

	/**
	 * @param ca
	 * @param cb
	 * @param isCartesian
	 * @param isDegrees 
	 */
	public SectorCoords(double ca, double cb, boolean isCartesian, boolean isDegrees) {
		if (isCartesian) {
			x = ca;
			y = cb;
			r = Math.hypot(x, y);
			p = Math.atan2(y, x);
			if (p < 0) p += 2*Math.PI;
		} else {
			r = ca;
			if (isDegrees) {
				p = Math.toRadians(cb);
			} else {
				p = cb;
			}
			x = r*Math.cos(p);
			y = r*Math.sin(p);
		}
	}

	/**
	 * 
	 * @return array with Cartesian coordinates
	 */
	public double[] getCartesian() {
		double[] car = { x, y };
		return car;
	}

	/**
	 * 
	 * @return array with Polar coordinates (in degrees)
	 */
	public double[] getPolar() {
		double[] pol = { r, Math.toDegrees(p) };
		return pol;
	}

	/**
	 * 
	 * @return array with Polar coordinates (in radians)
	 */
	public double[] getPolarRadians() {
		double[] pol = { r, p };
		return pol;
	}

	/**
	 * Add an amount to the azimuthal (polar) angle in degrees
	 * 
	 * @param dp change in phi
	 */
	public void addPhi(double dp) {
		p += Math.toRadians(dp);
		x = r*Math.cos(p);
		y = r*Math.sin(p);
	}
}
