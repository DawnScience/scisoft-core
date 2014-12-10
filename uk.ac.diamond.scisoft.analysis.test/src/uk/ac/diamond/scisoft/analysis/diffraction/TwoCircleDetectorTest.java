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

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.junit.Test;

public class TwoCircleDetectorTest {

	private static final double ROT_5 = Math.toDegrees(Math.atan(5/1000.));
	private static final double ROT_7 = Math.toDegrees(Math.atan(7/1000.));

	@Test
	public void testDetectorNormal() {
		DetectorProperties dp = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);
		DetectorProperties ndp;

		TwoCircleDetector dt = new TwoCircleDetector();

		// set detector normal to beam and fast axis horizontal
		dt.setDetector(new Vector3d(1000, 0, 1000), new Vector3d(0, 0, -1), new Vector3d(-1, 0, 0));

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
		Matrix3d ori = TwoCircleDetector.computeOrientation(ni, fa);
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
