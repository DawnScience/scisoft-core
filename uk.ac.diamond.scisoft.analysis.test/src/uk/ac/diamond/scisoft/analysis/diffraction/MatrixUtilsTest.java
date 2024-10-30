/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.MatrixUtils;
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
		double deg = 30;
		double rad = Math.toRadians(deg);
		Matrix3d ma = MatrixUtils.createRotationMatrix(new Vector3d(1, 0, 0), deg);
		Matrix3d me = new Matrix3d();
		me.rotX(rad);
		MatrixUtils.isClose(me, ma, TOL, TOL);
		Matrix3d mb = MatrixUtils.createRotationMatrix(new Vector3d(0, 1, 0), deg);
		me.rotY(rad);
		MatrixUtils.isClose(me, mb, TOL, TOL);
		Matrix3d mc = MatrixUtils.createRotationMatrix(new Vector3d(0, 0, 1), deg);
		me.rotZ(rad);
		MatrixUtils.isClose(me, mc, TOL, TOL);

		Matrix3d mi16 = MatrixUtils.createI16KappaRotation(0, 0, 0, 0);
		me.setIdentity();
		MatrixUtils.isClose(me, mi16, TOL, TOL);

		System.err.println(MatrixUtils.createI16KappaRotation(10, 20, 30, 40));
	}

	@Test
	public void testEuler() {
		Matrix3d ma = MatrixUtils.createOrientationFromEulerZYZ(10, 20, 25);

		Vector3d va = new Vector3d(0, 0, 1);
		ma.transform(va);
		double ct = Math.cos(Math.toRadians(20));
		double st = Math.sqrt(1 - ct*ct);
		Vector3d vb = new Vector3d(st*Math.cos(Math.toRadians(25)), st*Math.sin(Math.toRadians(25)), ct);

		MatrixUtils.isClose(vb, va, TOL, TOL);

		double[] angles = MatrixUtils.calculateEulerZYZ(ma);
		assertArrayEquals(new double[] {10, 20, 25}, angles, TOL);

		ma = MatrixUtils.createOrientationFromEulerZYZ(190, 0, 0);
		angles = MatrixUtils.calculateEulerZYZ(ma);
		assertArrayEquals(new double[] {-170, 0, 0}, angles, TOL);

		ma = MatrixUtils.createOrientationFromEulerZYZ(10, 0, 25);
		angles = MatrixUtils.calculateEulerZYZ(ma);
		assertArrayEquals(new double[] {35, 0, 0}, angles, TOL);

		ma = MatrixUtils.createOrientationFromEulerZYZ(10, 180, 0);
		angles = MatrixUtils.calculateEulerZYZ(ma);
		assertArrayEquals(new double[] {10, 180, 0}, angles, TOL);

		ma = MatrixUtils.createOrientationFromEulerZYZ(0, 180, 10);
		angles = MatrixUtils.calculateEulerZYZ(ma);
		assertArrayEquals(new double[] {-10, 180, 0}, angles, TOL);

		ma = MatrixUtils.createOrientationFromEulerZYZ(10, 180, 25);
		angles = MatrixUtils.calculateEulerZYZ(ma);
		assertArrayEquals(new double[] {-15, 180, 0}, angles, TOL);

		ma = new Matrix3d(0.7157637730257163, -0.46905862078836913, -0.5173646987262505,
				0.5481161418476648, 0.836402232807894, 0.0, 0.43272498919061925,
				-0.2835759425940119, 0.8557650194486195);
		angles = MatrixUtils.calculateEulerZYZ(ma);
		assertArrayEquals(new double[] {-146.76213181875903, 31.15564612989802, 180.0}, angles, TOL);
	}

	@Test
	public void testYawPitchRoll() {
		Matrix3d m = new Matrix3d();
		m.setIdentity();
		MatrixUtils.createOrientationFromYawPitchRoll().epsilonEquals(m, TOL);

		double[] angles = {0.7, -1.5, 2.3};
		Matrix3d ypr = MatrixUtils.createOrientationFromYawPitchRoll(angles[0]);
		assertArrayEquals(new double[] {angles[0], 0, 0}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);
		ypr = MatrixUtils.createOrientationFromYawPitchRoll(0, angles[1]);
		assertArrayEquals(new double[] {0, angles[1], 0}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);
		ypr = MatrixUtils.createOrientationFromYawPitchRoll(0, 0, angles[2]);
		assertArrayEquals(new double[] {0, 0, angles[2]}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);
		ypr = MatrixUtils.createOrientationFromYawPitchRoll(angles[0], angles[1]);
		assertArrayEquals(new double[] {angles[0], angles[1], 0}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);
		ypr = MatrixUtils.createOrientationFromYawPitchRoll(angles[0], 0, angles[2]);
		assertArrayEquals(new double[] {angles[0], 0, angles[2]}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);
		ypr = MatrixUtils.createOrientationFromYawPitchRoll(angles[0], angles[1], angles[2]);
		assertArrayEquals(new double[] {angles[0], angles[1], angles[2]}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);

		// gimbal lock cases
		ypr = MatrixUtils.createOrientationFromYawPitchRoll(angles[0], Math.PI/2, angles[2]);
		assertArrayEquals(new double[] {angles[0] + angles[2], Math.PI/2, 0}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);
		ypr = MatrixUtils.createOrientationFromYawPitchRoll(angles[0], -Math.PI/2, angles[2]);
		assertArrayEquals(new double[] {angles[0] - angles[2], -Math.PI/2, 0}, MatrixUtils.calculateYawPitchRoll(ypr), TOL);
	}
}
