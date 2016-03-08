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

import org.junit.Assert;
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
		Matrix3d ma = MatrixUtils.createRotationFromEulerZYZ(10, 20, 25);

		Vector3d va = new Vector3d(0, 0, 1);
		ma.transform(va);
		double ct = Math.cos(Math.toRadians(20));
		double st = Math.sqrt(1 - ct*ct);
		Vector3d vb = new Vector3d(st*Math.cos(Math.toRadians(25)), st*Math.sin(Math.toRadians(25)), ct);

		MatrixUtils.isClose(vb, va, TOL, TOL);

		double[] angles = MatrixUtils.calculateFromRotationEulerZYZ(ma);
		Assert.assertArrayEquals(new double[] {10, 20, 25}, angles, TOL);

		ma = MatrixUtils.createRotationFromEulerZYZ(190, 0, 0);
		angles = MatrixUtils.calculateFromRotationEulerZYZ(ma);
		Assert.assertArrayEquals(new double[] {-170, 0, 0}, angles, TOL);

		ma = MatrixUtils.createRotationFromEulerZYZ(10, 0, 25);
		angles = MatrixUtils.calculateFromRotationEulerZYZ(ma);
		Assert.assertArrayEquals(new double[] {35, 0, 0}, angles, TOL);

		ma = MatrixUtils.createRotationFromEulerZYZ(10, 180, 0);
		angles = MatrixUtils.calculateFromRotationEulerZYZ(ma);
		Assert.assertArrayEquals(new double[] {10, 180, 0}, angles, TOL);

		ma = MatrixUtils.createRotationFromEulerZYZ(0, 180, 10);
		angles = MatrixUtils.calculateFromRotationEulerZYZ(ma);
		Assert.assertArrayEquals(new double[] {-10, 180, 0}, angles, TOL);

		ma = MatrixUtils.createRotationFromEulerZYZ(10, 180, 25);
		angles = MatrixUtils.calculateFromRotationEulerZYZ(ma);
		Assert.assertArrayEquals(new double[] {-15, 180, 0}, angles, TOL);
	}
}
