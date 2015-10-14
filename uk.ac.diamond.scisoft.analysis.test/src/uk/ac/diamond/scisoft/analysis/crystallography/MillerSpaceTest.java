/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.junit.Test;

public class MillerSpaceTest {

	@Test
	public void testMillerSpace() {
		double a = 5.658;
		double l = 1.5498026;
		UnitCell uc = new UnitCell(a);

		MillerSpace ms = new MillerSpace(uc, null);

		Vector3d k = new Vector3d(0, 0, 2*Math.PI/l);
		Vector3d q;
		double t;
		double qmod;

		q = ms.q(1, 0, 0); // in crystal frame
		System.err.println("qc : " + q);
		t = theta(a, l, 1, 0, 0);
		Matrix3d r = new Matrix3d();
		r.rotY(t);
		r.transform(q); // rotate to lab frame
		System.err.println("ql : " + q);

		// check 
		ms.setRotation(r);
		q = ms.q(1, 0, 0); // in crystal frame
		System.err.println("ql : " + q);

		qmod = q.length();
		q.add(k);
		System.err.println("kf : " + q);
		System.err.println(q.length()/k.length());
		q.normalize();
		System.err.println("Reflection in " + q + "; cos(2t) = " + Math.cos(2*t));
		System.err.println("t = " + t + " " + Math.asin(qmod*l/(4*Math.PI)) + " cf " + 0.5*Math.acos(q.z));
	}

	/**
	 * 
	 * @param a cube side
	 * @param l wavelength
	 * @param hkl Miller indices
	 * @return theta (half scattering angle) in radians
	 */
	private double theta(double a, double l, double... hkl) {
		if (hkl.length == 1) {
			double h = hkl[0];
			hkl = new double[] {h, h, h};
		}
		double st = 0.5*l*Math.sqrt(hkl[0]*hkl[0] + hkl[1]*hkl[1] + hkl[2]*hkl[2])/a;
		if (st > 1) {
			throw new IllegalArgumentException("Reflection too large");
		}
		return Math.asin(st);
	}
}
