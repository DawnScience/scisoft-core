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

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;


/**
 * Reciprocal cell that is dual to a lattice cell
 */
public class ReciprocalCell extends LatticeCell {
	/**
	 * create a reciprocal cell from a unit cell
	 */
	public ReciprocalCell(LatticeCell l) {
		super();

		a = new Vector3d(); // a*
		a.cross(l.b, l.c);
		double volumeInv = 1. / a.dot(l.a);
		a.scale(volumeInv);

		b = new Vector3d(); // b*
		b.cross(l.c, l.a);
		b.scale(volumeInv);

		c = new Vector3d(); // c*
		c.cross(l.a, l.b);
		c.scale(volumeInv);
	}

	/**
	 * @return orthogonalization matrix
	 */
	public Matrix3d orthogonalization() {
		Matrix3d B = new Matrix3d();
		B.setColumn(0, a);
		B.setColumn(1, b);
		B.setColumn(2, c);
		return B;
	}
}
