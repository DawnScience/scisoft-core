/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.MatrixUtils;
import org.junit.Test;

public class TwoCircleDetectorTest {

	private static final double RADIUS = 1000;
	private static final double ROT_5 = Math.toDegrees(Math.atan(5/RADIUS));
	private static final double ROT_7 = Math.toDegrees(Math.atan(7/RADIUS));

	@Test
	public void testDetectorNormal() {
		DetectorProperties dp = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);

		TwoCircleDetector dt = new TwoCircleDetector();

		// set detector normal to beam and fast axis horizontal
		dt.setDetector(new Vector3d(0, 0, RADIUS), new Vector3d(0, 0, -1), new Vector3d(-1, 0, 0));

		dt.updateDetectorProperties(dp, 0, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, 0, ROT_5);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 5}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, 0, -ROT_5);
		assertArrayEquals(new double[] {0, -5}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, ROT_5, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, -ROT_5, 0);
		assertArrayEquals(new double[] {-5, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, ROT_5, -ROT_7);
		assertArrayEquals(new double[] {5, -7}, dp.getBeamCentreCoords(), 2e-4); // approximately

		// set detector normal to beam and fast axis vertical down
		dt.setDetector(new Vector3d(0, 0, RADIUS), new Vector3d(0, 0, -1), new Vector3d(0, -1, 0));
		
		dt.updateDetectorProperties(dp, 0, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, 0, ROT_5);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, ROT_5, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, -5}, dp.getBeamCentreCoords(), 1e-8);

		// set detector normal to beam and fast axis horizontal
		// and vary everything one-by-one
		dt.setDetector(new Vector3d(0, 0, RADIUS), new Vector3d(0, 0, -1), new Vector3d(-1, 0, 0));
		double[] bc;

		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] == 0);

		dt.gammaOff += 0.95;
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.gammaOff -= 0.95;

		dt.deltaOff += 0.95;
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] > 0);
		dt.deltaOff -= 0.95;

		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] == 0);

		dt.updateDetectorProperties(dp, 0, 1);
		double[] obc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(obc));
		dt.updateDetectorProperties(dp, 0, 1);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == obc[0] && bc[1] == obc[1]);

		dt.detectorPos.x += 0.95;
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.detectorPos.x -= 0.95;

		dt.detectorPos.y += 0.95;
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] > 0);
		dt.detectorPos.y -= 0.95;

		dt.detectorPos.z += 0.95;
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] == 0);
		dt.detectorPos.z -= 0.95;

		dt.detectorPos.x += 0.95;
		dt.detectorOri.m10 = 0.05;
		dt.detectorOri.normalize();
		System.err.println(dt.detectorOri);
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] < 0);
		dt.detectorOri.setIdentity();

		dt.detectorOri.m20 = 0.05;
		dt.detectorOri.normalize();
		System.err.println(dt.detectorOri);
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.detectorOri.setIdentity();

		dt.detectorOri.m12 = 0.05;
		dt.detectorOri.normalize();
		System.err.println(dt.detectorOri);
		dt.updateDetectorProperties(dp, 0, 0);
		bc = dp.getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.detectorOri.setIdentity();
	}

	@Test
	public void testBeam() {
		TwoCircleDetector dt = new TwoCircleDetector();

		dt.setGamma(0);
		dt.setDetector(new Vector3d(0, 0, RADIUS), TwoCircleDetector.createDirection(180, 0), 90);

		DetectorProperties dp = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);
		Vector3d p;
		double[] bc;

		dt.updateDetectorProperties(dp, 0, 0);
		p = dp.getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS, p.z, 1e-10);

		dt.updateDetectorProperties(dp, 5, 0);
		p = dp.getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		dt.updateDetectorProperties(dp, 0, 5);
		p = dp.getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		dt.updateDetectorProperties(dp, 0, 5);
		bc = dp.getBeamCentreCoords();
		assertEquals(-RADIUS*Math.tan(Math.toRadians(5)), bc[0], 1e-10);
		assertEquals(0, bc[1], 1e-10);

		dt.updateDetectorProperties(dp, 5, 0);
		bc = dp.getBeamCentreCoords();
		assertEquals(0, bc[0], 1e-10);
		assertEquals(RADIUS*Math.tan(Math.toRadians(5)), bc[1], 1e-10);

		dt.setDetector(new Vector3d(0, 0, RADIUS), TwoCircleDetector.createDirection(180, 0), 0);
		dt.updateDetectorProperties(dp, 0, 0);
		p = dp.getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS, p.z, 1e-10);

		dt.updateDetectorProperties(dp, 5, 0);
		p = dp.getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		dt.updateDetectorProperties(dp, 0, 5);
		p = dp.getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		dt.updateDetectorProperties(dp, 0, 5);
		bc = dp.getBeamCentreCoords();
		assertEquals(0, bc[0], 1e-10);
		assertEquals(RADIUS*Math.tan(Math.toRadians(5)), bc[1], 1e-10);

		dt.updateDetectorProperties(dp, 5, 0);
		bc = dp.getBeamCentreCoords();
		assertEquals(RADIUS*Math.tan(Math.toRadians(5)), bc[0], 1e-10);
		assertEquals(0, bc[1], 1e-10);
	}

	private static final double ANGLE = Math.toRadians(20);

	@Test
	public void testComputeTransform() {
		double ct = 0.5 * Math.sqrt(3);
		checkTransform(new Vector3d(0, 0, -1), new Vector3d(-1, 0, 0));
		checkTransform(new Vector3d(0, 0, -1), new Vector3d(-ct, -0.5, 0));
		checkTransform(new Vector3d(0, 0, -1), new Vector3d(0.5, -ct, 0));

		checkTransform(new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)), new Vector3d(-1, 0, 0));
		checkTransform(new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)), new Vector3d(-ct, -0.5, 0));
		checkTransform(new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)), new Vector3d(0, -1, 0));
		checkTransform(new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)), new Vector3d(0.5, -ct, 0));
		checkTransform(new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)), new Vector3d(0, 1, 0));
	}

	private void checkTransform(Vector3d ni, Vector3d fa) {
		Matrix3d ori = MatrixUtils.computeOrientation(ni, fa);
		System.err.printf("%s", ori);
		Vector3d no = new Vector3d();
		Vector3d vn = new Vector3d(0, 0, -1);
		ori.transform(vn, no);
		assertTrue(no.epsilonEquals(ni, 1e-8));

		// check fast and slow axis are transformed to be perpendicular to input normal
		Vector3d va = new Vector3d(-1, 0, 0);
		ori.transform(va, no);
		System.err.println("fast: " + no + "; " + va.dot(no));
		assertEquals(0, Math.abs(no.dot(ni)), 1e-8);
//		assertEquals(1, fa.dot(no), 1e-8);
		assertTrue(fa.dot(no) > 0);

		va.set(0, -1, 0);
		ori.transform(va, no);
		System.err.println("slow: " + no + "; " + va.dot(no));
		assertEquals(0, Math.abs(no.dot(ni)), 1e-8);
	}

	@Test
	public void testDetectorOblique() {
		DetectorProperties dp = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);

		TwoCircleDetector dt = new TwoCircleDetector();

		// set detector oblique to beam and fast axis horizontal
		dt.setDetector(new Vector3d(0, 0, RADIUS), new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)),
				new Vector3d(-1, 0, 0));

		dt.updateDetectorProperties(dp, 0, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, 0, ROT_5);
		System.err.println(dp);
		System.err.println(dt);
		checkIntBeamCentreCoords(dp, 0, 5);

		dt.updateDetectorProperties(dp, 0, -ROT_5);
		checkIntBeamCentreCoords(dp, 0, -5);

		dt.updateDetectorProperties(dp, ROT_5, 0);
		System.err.println(dp);
		System.err.println(dt);
		checkIntBeamCentreCoords(dp, 5, 0);

		dt.updateDetectorProperties(dp, -ROT_5, 0);
		checkIntBeamCentreCoords(dp, -5, 0);

		dt.updateDetectorProperties(dp, ROT_5, -ROT_7);
		checkIntBeamCentreCoords(dp, 5, -7);

		dt.setDetector(new Vector3d(0, 0, RADIUS), new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)),	90);

		dt.updateDetectorProperties(dp, 0, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, 0, ROT_5);
		System.err.println(dp);
		System.err.println(dt);
		checkIntBeamCentreCoords(dp, 0, 5);

		dt.updateDetectorProperties(dp, 0, -ROT_5);
		checkIntBeamCentreCoords(dp, 0, -5);

		dt.updateDetectorProperties(dp, ROT_5, 0);
		System.err.println(dp);
		System.err.println(dt);
		checkIntBeamCentreCoords(dp, 5, 0);

		dt.updateDetectorProperties(dp, -ROT_5, 0);
		checkIntBeamCentreCoords(dp, -5, 0);

		dt.updateDetectorProperties(dp, ROT_5, -ROT_7);
		checkIntBeamCentreCoords(dp, 5, -7);

		// set detector normal to beam and fast axis vertical down
		dt.setDetector(new Vector3d(0, 0, RADIUS), new Vector3d(0, 0, -1), new Vector3d(0, -1, 0));

		dt.updateDetectorProperties(dp, 0, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, 0, ROT_5);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, ROT_5, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, -5}, dp.getBeamCentreCoords(), 1e-8);

		dt.setDetector(new Vector3d(0, 0, RADIUS), new Vector3d(0, 0, -1), -90);
		
		dt.updateDetectorProperties(dp, 0, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, 0, ROT_5);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, dp.getBeamCentreCoords(), 1e-8);

		dt.updateDetectorProperties(dp, ROT_5, 0);
		System.err.println(dp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, -5}, dp.getBeamCentreCoords(), 1e-8);
	}

	private void checkIntBeamCentreCoords(DetectorProperties dp, double... coords) {
		double[] nc = dp.getBeamCentreCoords();
		for (int i = 0; i < nc.length; i++) {
			nc[i] = Math.round(nc[i]);
		}
		assertArrayEquals(coords, nc, 1e-8);
	}

	@Test
	public void testParameters() {
		TwoCircleDetector two = new TwoCircleDetector();

		double[][] parameters = new double[][] {
				new double[] {5, 0, 20, 30, 100, 35},
				new double[] {2, 12, 20, 30, 100, -17},
		};

		for (double[] p : parameters) {
			TwoCircleDetector.setupTwoCircle(two, p);
			double[] np = TwoCircleDetector.getTwoCircleParameters(two, p.length);
			assertArrayEquals(p, np, 1e-8);
		}
	}

	@Test
	public void testMatrix() {
		Matrix3d m = new Matrix3d();
		m.set(new AxisAngle4d(1, 0, 0, Math.PI/3));
		System.out.println(m);

		m.set(new AxisAngle4d(0, 1, 0, Math.PI/3));
		System.out.println(m);

		m.set(new AxisAngle4d(0, 0, 1, Math.PI/3));
		System.out.println(m);
	}
}
