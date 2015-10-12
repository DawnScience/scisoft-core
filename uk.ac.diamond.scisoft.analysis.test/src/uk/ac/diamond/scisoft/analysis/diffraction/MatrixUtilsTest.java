/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.fail;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.junit.Test;

public class MatrixUtilsTest {

	private static final double TOL = 1e-14;

	@Test
	public void testClose() {
		MatrixUtils.isClose(new Vector3d(1, 0, 0), new Vector3d(1, 0, 0), TOL, TOL);
		try {
			MatrixUtils.isClose(new Vector3d(1, 0, 0), new Vector3d(1, 2e-4, 0), TOL, TOL);
			fail();
		} catch (AssertionError e) {
		}
	}

	@Test
	public void testMeridionalTangents() {
		MatrixUtils.isClose(new Vector3d(0, 0, -1), MatrixUtils.computeMeridionalTangent(new Vector3d(1, 0, 0)), TOL, TOL);
		MatrixUtils.isClose(new Vector3d(0, 0, -1), MatrixUtils.computeMeridionalTangent(new Vector3d(0, 1, 0)), TOL, TOL);
		MatrixUtils.isClose(new Vector3d(1, 0, 0), MatrixUtils.computeMeridionalTangent(new Vector3d(0, 0, 1)), TOL, TOL);

		Vector3d v = new Vector3d(1, 0, 1);
		v.normalize();
		Vector3d u = new Vector3d(1, 0, -1);
		u.normalize();
		MatrixUtils.isClose(u, MatrixUtils.computeMeridionalTangent(v), TOL, TOL);
	}

	@Test
	public void testTangents() {
		MatrixUtils.isClose(new Vector3d(0, 1, 0), MatrixUtils.computeTangent(new Vector3d(0, 0, 1), 90), TOL, TOL);

		Vector3d v = new Vector3d(1, 0, 1);
		v.normalize();
		Vector3d u = new Vector3d(0, 1, 0);
		u.normalize();
		MatrixUtils.isClose(u, MatrixUtils.computeTangent(v, 90), TOL, TOL);
	}

	@Test
	public void testRotations() {
		Matrix3d ma = MatrixUtils.createRotationMatrix(new Vector3d(1, 0, 0), 30);
		System.err.println(ma);
		Matrix3d mb = MatrixUtils.createRotationMatrix(new Vector3d(0, 1, 0), 30);
		System.err.println(mb);
		Matrix3d mc = MatrixUtils.createRotationMatrix(new Vector3d(0, 0, 1), 30);
		System.err.println(mc);
	}
}
