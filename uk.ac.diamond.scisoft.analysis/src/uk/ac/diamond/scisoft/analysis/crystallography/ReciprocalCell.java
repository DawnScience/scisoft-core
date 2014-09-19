/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;


/**
 * Reciprocal cell that is dual to a unit cell
 */
public class ReciprocalCell extends UnitCell {
	/**
	 * create a reciprocal cell from a unit cell
	 */
	public ReciprocalCell(UnitCell l) {
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

		calculateAll();
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
