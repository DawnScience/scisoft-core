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

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.junit.Test;

public class TwoCircleDetectorTest {

	private static final double RADIUS = 1000;
	private static final double ROT_5 = Math.toDegrees(Math.atan(5/RADIUS));
	private static final double ROT_7 = Math.toDegrees(Math.atan(7/RADIUS));

	@Test
	public void testDetectorNormal() {
		DetectorProperties dp = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);
		DetectorProperties ndp;

		TwoCircleDetector dt = new TwoCircleDetector();

		// set detector normal to beam and fast axis horizontal
		dt.setDetector(new Vector3d(RADIUS, 0, RADIUS), new Vector3d(0, 0, -1), new Vector3d(-1, 0, 0));

		ndp = dt.getDetectorProperties(dp, 0, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, 0, ROT_5);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 5}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, 0, -ROT_5);
		assertArrayEquals(new double[] {0, -5}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, ROT_5, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, -ROT_5, 0);
		assertArrayEquals(new double[] {-5, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, ROT_5, -ROT_7);
		assertArrayEquals(new double[] {5, -7}, ndp.getBeamCentreCoords(), 2e-4); // approximately

		// set detector normal to beam and fast axis vertical down
		dt.setDetector(new Vector3d(1000, 0, 1000), new Vector3d(0, 0, -1), new Vector3d(0, -1, 0));
		
		ndp = dt.getDetectorProperties(dp, 0, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, 0, ROT_5);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, ROT_5, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, -5}, ndp.getBeamCentreCoords(), 1e-8);

		// set detector normal to beam and fast axis horizontal
		// and vary everything one-by-one
		dt.setDetector(new Vector3d(1000, 0, 1000), new Vector3d(0, 0, -1), new Vector3d(-1, 0, 0));
		double[] bc;

		dt.beamPos.x += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] < 0 && bc[1] == 0);
		dt.beamPos.x -= 0.95;

		dt.beamPos.y += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] < 0);
		dt.beamPos.y -= 0.95;

		dt.beamPos.z += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] == 0);
		dt.beamPos.z -= 0.95;

		dt.beamDir.x = 0.05;
		dt.beamDir.normalize();
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] < 0 && bc[1] == 0);
		dt.beamDir.x = 0.0;
		dt.beamDir.normalize();

		dt.beamDir.y = 0.05;
		dt.beamDir.normalize();
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] < 0);
		dt.beamDir.y = 0.0;
		dt.beamDir.normalize();

		dt.gammaOff += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.gammaOff -= 0.95;

		dt.deltaOff += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] > 0);
		dt.deltaOff -= 0.95;

		dt.deltaPos.x += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.deltaPos.x -= 0.95;

		dt.deltaPos.y += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] > 0);
		dt.deltaPos.y -= 0.95;

		dt.deltaPos.z += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] == 0);
		dt.deltaPos.z -= 0.95;

		double[] obc = dt.getDetectorProperties(dp, 0, 1).getBeamCentreCoords();
		System.err.println(Arrays.toString(obc));
		dt.deltaDir.y = 0.05;
		dt.deltaDir.normalize();
		bc = dt.getDetectorProperties(dp, 0, 1).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > obc[0] && bc[1] < obc[1]);
		dt.deltaDir.y = 0.0;
		dt.deltaDir.normalize();

		dt.deltaDir.z = 0.05;
		dt.deltaDir.normalize();
		bc = dt.getDetectorProperties(dp, 0, 1).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > obc[0] && bc[1] > obc[1]);
		dt.deltaDir.z = 0.0;
		dt.deltaDir.normalize();

		dt.detectorPos.x += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.detectorPos.x -= 0.95;

		dt.detectorPos.y += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] > 0);
		dt.detectorPos.y -= 0.95;

		dt.detectorPos.z += 0.95;
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] == 0 && bc[1] == 0);
		dt.detectorPos.z -= 0.95;

		dt.detectorPos.x += 0.95;
		dt.detectorOri.m10 = 0.05;
		dt.detectorOri.normalize();
		System.err.println(dt.detectorOri);
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] < 0);
		dt.detectorOri.setIdentity();

		dt.detectorOri.m20 = 0.05;
		dt.detectorOri.normalize();
		System.err.println(dt.detectorOri);
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.detectorOri.setIdentity();

		dt.detectorOri.m12 = 0.05;
		dt.detectorOri.normalize();
		System.err.println(dt.detectorOri);
		bc = dt.getDetectorProperties(dp, 0, 0).getBeamCentreCoords();
		System.err.println(Arrays.toString(bc));
		assertTrue(bc[0] > 0 && bc[1] == 0);
		dt.detectorOri.setIdentity();
	}

	@Test
	public void testBeam() {
		TwoCircleDetector dt = new TwoCircleDetector();

		dt.setBeam(new Vector3d(0, 0, 0), TwoCircleFitter.createDirection(0, 0));
		dt.setGamma(0);
		dt.setDelta(new Vector3d(-RADIUS, 0, 0), TwoCircleFitter.createDirection(90, 180), 0);
		dt.setDetector(new Vector3d(RADIUS, 0, RADIUS), TwoCircleFitter.createDirection(180, 0), 90);

		DetectorProperties dp = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);
		Vector3d p;
		double[] bc;

		p = dt.getDetectorProperties(dp, 0, 0).getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS, p.z, 1e-10);

		p = dt.getDetectorProperties(dp, 5, 0).getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		p = dt.getDetectorProperties(dp, 0, 5).getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		bc = dt.getDetectorProperties(dp, 0, 5).getBeamCentreCoords();
		assertEquals(-RADIUS*Math.tan(Math.toRadians(5)), bc[0], 1e-10);
		assertEquals(0, bc[1], 1e-10);

		bc = dt.getDetectorProperties(dp, 5, 0).getBeamCentreCoords();
		assertEquals(0, bc[0], 1e-10);
		assertEquals(RADIUS*Math.tan(Math.toRadians(5)), bc[1], 1e-10);

		dt.setDetector(new Vector3d(RADIUS, 0, RADIUS), TwoCircleFitter.createDirection(180, 0), 0);
		p = dt.getDetectorProperties(dp, 0, 0).getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS, p.z, 1e-10);

		p = dt.getDetectorProperties(dp, 5, 0).getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		p = dt.getDetectorProperties(dp, 0, 5).getBeamCentrePosition();
		assertEquals(0, p.x, 1e-10);
		assertEquals(0, p.y, 1e-10);
		assertEquals(RADIUS/Math.cos(Math.toRadians(5)), p.z, 1e-10);

		bc = dt.getDetectorProperties(dp, 0, 5).getBeamCentreCoords();
		assertEquals(0, bc[0], 1e-10);
		assertEquals(RADIUS*Math.tan(Math.toRadians(5)), bc[1], 1e-10);

		bc = dt.getDetectorProperties(dp, 5, 0).getBeamCentreCoords();
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
		DetectorProperties ndp;

		TwoCircleDetector dt = new TwoCircleDetector();

		// set detector oblique to beam and fast axis horizontal
		dt.setDetector(new Vector3d(1000, 0, 1000), new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)),
				new Vector3d(-1, 0, 0));

		ndp = dt.getDetectorProperties(dp, 0, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, 0, ROT_5);
		System.err.println(ndp);
		System.err.println(dt);
		checkIntBeamCentreCoords(ndp, 0, 5);

		ndp = dt.getDetectorProperties(dp, 0, -ROT_5);
		checkIntBeamCentreCoords(ndp, 0, -5);

		ndp = dt.getDetectorProperties(dp, ROT_5, 0);
		System.err.println(ndp);
		System.err.println(dt);
		checkIntBeamCentreCoords(ndp, 5, 0);

		ndp = dt.getDetectorProperties(dp, -ROT_5, 0);
		checkIntBeamCentreCoords(ndp, -5, 0);

		ndp = dt.getDetectorProperties(dp, ROT_5, -ROT_7);
		checkIntBeamCentreCoords(ndp, 5, -7);

		dt.setDetector(new Vector3d(1000, 0, 1000), new Vector3d(0, -Math.sin(ANGLE), -Math.cos(ANGLE)),
				0);

		ndp = dt.getDetectorProperties(dp, 0, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, 0, ROT_5);
		System.err.println(ndp);
		System.err.println(dt);
		checkIntBeamCentreCoords(ndp, 0, 5);

		ndp = dt.getDetectorProperties(dp, 0, -ROT_5);
		checkIntBeamCentreCoords(ndp, 0, -5);

		ndp = dt.getDetectorProperties(dp, ROT_5, 0);
		System.err.println(ndp);
		System.err.println(dt);
		checkIntBeamCentreCoords(ndp, 5, 0);

		ndp = dt.getDetectorProperties(dp, -ROT_5, 0);
		checkIntBeamCentreCoords(ndp, -5, 0);

		ndp = dt.getDetectorProperties(dp, ROT_5, -ROT_7);
		checkIntBeamCentreCoords(ndp, 5, -7);

		// set detector normal to beam and fast axis vertical down
		dt.setDetector(new Vector3d(1000, 0, 1000), new Vector3d(0, 0, -1), new Vector3d(0, -1, 0));
		
		ndp = dt.getDetectorProperties(dp, 0, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, 0, ROT_5);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, ROT_5, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, -5}, ndp.getBeamCentreCoords(), 1e-8);

		dt.setDetector(new Vector3d(1000, 0, 1000), new Vector3d(0, 0, -1), -90);
		
		ndp = dt.getDetectorProperties(dp, 0, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, 0, ROT_5);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {5, 0}, ndp.getBeamCentreCoords(), 1e-8);

		ndp = dt.getDetectorProperties(dp, ROT_5, 0);
		System.err.println(ndp);
		System.err.println(dt);
		assertArrayEquals(new double[] {0, -5}, ndp.getBeamCentreCoords(), 1e-8);
	}

	private void checkIntBeamCentreCoords(DetectorProperties dp, double... coords) {
		double[] nc = dp.getBeamCentreCoords();
		for (int i = 0; i < nc.length; i++) {
			nc[i] = Math.round(nc[i]);
		}
		assertArrayEquals(coords, nc, 1e-8);
	}
}
