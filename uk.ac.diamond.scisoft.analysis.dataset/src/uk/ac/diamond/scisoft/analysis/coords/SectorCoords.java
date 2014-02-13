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
 * 
 * Usual definition of 2D polar coordinates with respect to 2D Cartesian coordinate system
 *
 */
public class SectorCoords {
	double x, y;
	double r, p;

	/**
	 * @param ca
	 * @param cb (in degrees, if not Cartesian)
	 * @param isCartesian
	 */
	public SectorCoords(double ca, double cb, boolean isCartesian) {
		if (isCartesian) {
			x = ca;
			y = cb;
			double[] pol = convertFromCartesianToPolarRadians(x, y);
			r = pol[0];
			p = pol[1];
		} else {
			r = ca;
			p = Math.toRadians(cb);
			double[] car = convertFromPolarRadians(r, p);
			x = car[0];
			y = car[1];
		}
	}

	/**
	 * @param spt 
	 * @param pt
	 */
	public SectorCoords(double[] spt, double[] pt) {
		this(pt[0] - spt[0], pt[1] - spt[1], true);
	}

	public static double[] convertFromPolarRadians(double r, double p) {
		return new double[] {r*Math.cos(p), r*Math.sin(p)};
	}

	public static double[] convertFromPolarDegrees(double r, double p) {
		return convertFromPolarRadians(r, Math.toRadians(p));
	}

	public static double[] convertFromCartesianToPolarRadians(double x, double y) {
		double r = Math.hypot(x, y);
		double p = Math.atan2(y, x);
		if (p < 0) p += 2*Math.PI;
		return new double[] {r, p};
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
