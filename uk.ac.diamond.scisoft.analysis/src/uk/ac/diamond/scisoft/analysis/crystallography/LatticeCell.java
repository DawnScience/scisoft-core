/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Vector3d;

/**
 * Class to hold 3 basis vectors that define a lattice
 * <p>
 * Each basis vector is defined in Cartesian coordinates.
 *
 */
public class LatticeCell {
	protected Vector3d a, b, c;

	/**
	 * 
	 */
	public LatticeCell() {
	}

	/**
	 * Constructor for a lattice cell
	 * @param a
	 * @param b
	 * @param c
	 */
	public LatticeCell(Vector3d a, Vector3d b, Vector3d c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * @return lattice basis a
	 */
	public Vector3d getA() {
		return a;
	}

	/**
	 * @param a
	 */
	public void setA(Vector3d a) {
		this.a = a;
	}

	/**
	 * @return lattice basis b
	 */
	public Vector3d getB() {
		return b;
	}

	/**
	 * @param b
	 */
	public void setB(Vector3d b) {
		this.b = b;
	}

	/**
	 * @return lattice basis c
	 */
	public Vector3d getC() {
		return c;
	}

	/**
	 * @param c
	 */
	public void setC(Vector3d c) {
		this.c = c;
	}

	/**
	 * @return volume of lattice cell
	 */
	public double volume() {
		Vector3d t = new Vector3d();
		t.cross(a,b);
		return t.dot(c);
	}
}
