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

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Vector3d;


/**
 * UnitCell class as specified by lengths and angles of cell
 */
public class UnitCell extends LatticeCell {
	private double[] lengths;
	private double[] angles;

	/**
	 * @param lengths
	 * @param angles (in degrees)
	 */
	public UnitCell(double[] lengths, double[] angles) {
		super();
		this.setLengths(lengths);
		this.setAngles(angles);
		double alpha = Math.toRadians(angles[0]);
		double beta  = Math.toRadians(angles[1]);
		double gamma = Math.toRadians(angles[2]);

		a = new Vector3d(lengths[0], 0, 0);

		b = new Vector3d(lengths[1] * Math.cos(gamma), lengths[1] * Math.sin(gamma), 0);

		double sinBetacosA = (Math.cos(alpha) - Math.cos(beta) * Math.cos(gamma)) / Math.sin(gamma);

		double sinA = sinBetacosA / Math.sin(beta);
		sinA = 1.0 - sinA * sinA;
		if (sinA < 0.0)
			sinA = 0.0; // for numerical errors

		c = new Vector3d(lengths[2] * Math.cos(beta), lengths[2] * sinBetacosA,
				lengths[2] * Math.sin(beta) * Math.sqrt(sinA));
	}

	/**
	 * @param oa
	 * @param ob
	 * @param oc
	 */
	public UnitCell(double[] oa, double[] ob, double[] oc) {
		super();
		a = new Vector3d(oa[0], oa[1], oa[2]);
		b = new Vector3d(ob[0], ob[1], ob[2]);
		c = new Vector3d(oc[0], oc[1], oc[2]);
		double[] temp = { a.length(), b.length(), c.length() };
		this.setLengths(temp);
	}

	/**
	 * @param lengths
	 */
	public void setLengths(double[] lengths) {
		this.lengths = lengths;
	}

	/**
	 * @return lengths of unit cell
	 */
	public double[] getLengths() {
		return lengths;
	}

	/**
	 * @param angles
	 */
	public void setAngles(double[] angles) {
		this.angles = angles;
	}

	/**
	 * @return angles of unit cell
	 */
	public double[] getAngles() {
		return angles;
	}
}
