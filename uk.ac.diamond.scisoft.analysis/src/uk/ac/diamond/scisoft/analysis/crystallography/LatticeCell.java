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
