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

import uk.ac.diamond.scisoft.analysis.diffraction.MatrixUtils;


/**
 * Reciprocal cell that is dual to a unit cell.
 * This is defined in the crystallographers' way.
 */
public class ReciprocalCell extends UnitCell {
	/**
	 * Orthogonalization convention is the choice of how the coordinate
	 * frame is aligned with the reciprocal unit cell
	 */
	public enum Ortho_Convention {
		/**
		 * Default of using the laboratory coordinate frame
		 */
		DEFAULT,
		/**
		 * X=a*, Y in plane of a* x b*, Z=a* x b*
		 * given in Busing & Levy, Acta Cryst 22, pp457-64 (1967)
		 */
		BUSING_LEVY,
		/**
		 * Used by the Protein Data Bank (www.rscb.org)
		 */
		PDB,
	}

	private Matrix3d bMatrix; // orthogonalization matrix

	/**
	 * create a reciprocal cell from a unit cell with default orthogonalization convention
	 * @param l unit cell
	 */
	public ReciprocalCell(UnitCell l) {
		this(l, Ortho_Convention.DEFAULT);
	}

	/**
	 * create a reciprocal cell from a unit cell with given orthogonalization convention
	 * @param l unit cell
	 * @param convention
	 */
	public ReciprocalCell(UnitCell l, Ortho_Convention convention) {
		a = new Vector3d(); // a*
		a.cross(l.b, l.c);
		double volumeInv = 1. / a.dot(l.a);
		a.scale(volumeInv);
		MatrixUtils.santise(a);

		b = new Vector3d(); // b*
		b.cross(l.c, l.a);
		b.scale(volumeInv);
		MatrixUtils.santise(b);

		c = new Vector3d(); // c*
		c.cross(l.a, l.b);
		c.scale(volumeInv);
		MatrixUtils.santise(c);

		calculateAll();

		if (convention == Ortho_Convention.DEFAULT) {
			bMatrix = new Matrix3d();
			bMatrix.setColumn(0, a);
			bMatrix.setColumn(1, b);
			bMatrix.setColumn(2, c);
		} else {
			double lc = l.getLength(2);

			if (convention == Ortho_Convention.PDB) {
				double cb = Math.cos(Math.toRadians(l.getAngle(1)));
				double cc = Math.cos(Math.toRadians(l.getAngle(2)));
				double sb = sin(cb);
				double sc = sin(cc);
				double la = l.getLength(0);
				double lb = l.getLength(1);
				double cas = Math.cos(Math.toRadians(getAngle(0)));
				double sas = sin(cas);

				bMatrix = new Matrix3d(new double[] {la, lb*cc, lc*cb, 0, lb*sc, -lc*sb*cas, 0, 0, lc*sb*sas});
				bMatrix.invert();
			}  else {
				double ca = Math.cos(Math.toRadians(l.getAngle(0)));
				double cbs = Math.cos(Math.toRadians(getAngle(1)));
				double ccs = Math.cos(Math.toRadians(getAngle(2)));
				double las = getLength(0);
				double lbs = getLength(1);
				double lcs = getLength(2);

				bMatrix = new Matrix3d(new double[] {las, lbs*ccs, lcs*cbs, 0, lbs*sin(ccs), -lcs*sin(cbs)*ca,
						0, 0, 1/lc});
			}
		}
	}

	private static double sin(double cos) {
		double ss = 1 - cos*cos;
		return ss >= 0 ? Math.sqrt(ss) : 0;
	}

	/**
	 * @return orthogonalization matrix according to the chosen convention
	 */
	public Matrix3d orthogonalization() {
		return bMatrix;
	}
}
