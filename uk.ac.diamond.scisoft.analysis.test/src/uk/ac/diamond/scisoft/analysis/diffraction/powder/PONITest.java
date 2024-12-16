/*-
 * Copyright 2024 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public class PONITest {
	private void checkPyFAICalc(double[] expected, double... angles) {
		Matrix3d ePF = PONIDiffractionMetadataDescriptor.createOrientationFromEulerPyFAI(angles);
		assertArrayEquals(expected != null ? expected : angles, PONIDiffractionMetadataDescriptor.calculateEulerPyFAI(ePF),
				1e-12);
	}

	private void checkPyFAICalc(double... angles) {
		checkPyFAICalc(null, angles);
	}

	@Test
	public void testEulerPyFAICalc() {
		checkPyFAICalc(0.3, 1.5, -2.4);
		checkPyFAICalc(0.47, 0.28, 0.51);

		// degenerate cases
		checkPyFAICalc(new double[] { 0.3 + 2.4, Math.PI / 2, 0 }, 0.3, Math.PI / 2, -2.4);
		checkPyFAICalc(new double[] { 0.3 - 2.4, -Math.PI / 2, 0 }, 0.3, -Math.PI / 2, -2.4);
	}

	private void checkPONICalc(double[] expected, double... angles) {
		Matrix3d ePF = PONIDiffractionMetadataDescriptor.createOrientationFromEulerPONI(angles);
		assertArrayEquals(expected != null ? expected : angles, PONIDiffractionMetadataDescriptor.calculateEulerPONI(ePF),
				1e-12);
	}

	private void checkPONICalc(double... angles) {
		checkPyFAICalc(null, angles);
	}

	@Test
	public void testEulerPONICalc() {
		checkPONICalc(0.3, 1.5, -2.4);
		checkPONICalc(0.47, 0.28, 0.51);

		// degenerate cases
		checkPONICalc(new double[] { 0.3 + 2.4, Math.PI / 2, 0 }, 0.3, Math.PI / 2, -2.4);
		checkPONICalc(new double[] { 0.3 - 2.4, -Math.PI / 2, 0 }, 0.3, -Math.PI / 2, -2.4);
	}

	/**
	 * Get point of normal incidence
	 * 
	 * @param angles
	 * @return
	 */
	private static Vector3d getPONI(double... angles) {
		double pixelSize = 1e-4;
		double[] centre = {200, 100};
		PONIDiffractionMetadataDescriptor poni = new PONIDiffractionMetadataDescriptor(centre[1] * pixelSize, centre[0] * pixelSize,
				0.5, angles[0], angles[1], angles[2], 1e-9, pixelSize, pixelSize, 256, 512);

		return poni.getPixelPosition(centre[0], centre[1]);
	}

	@Test
	public void testPONI() {
		Vector3d v = getPONI(0, 0, 0);
		Vector3d expected = new Vector3d(0, 0, 0.5);
		assertTrue(v + " should match " + expected, v.epsilonEquals(expected, 1e-12));

		double angle1 = 0.6;
		v = getPONI(angle1, 0, 0);
		expected.set(0, 0.5 * Math.sin(angle1), 0.5 * Math.cos(angle1));
		assertTrue(v + " should match " + expected, v.epsilonEquals(expected, 1e-12));

		double angle2 = -1.3;
		v = getPONI(0, angle2, 0);
		expected.set(-0.5 * Math.sin(angle2), 0, 0.5 * Math.cos(angle2));
		assertTrue(v + " should match " + expected, v.epsilonEquals(expected, 1e-12));

		double angle3 = 0.2;
		v = getPONI(0, 0, angle3);
		expected.set(0, 0, 0.5);
		assertTrue(v + " should match " + expected, v.epsilonEquals(expected, 1e-12));

		v = getPONI(angle1, 0, angle3);
		expected.set(-0.5 * Math.sin(angle1) * Math.sin(angle3), 0.5 * Math.sin(angle1) * Math.cos(angle3),
				0.5 * Math.cos(angle1));
		assertTrue(v + " should match " + expected, v.epsilonEquals(expected, 1e-12));

		v = getPONI(angle1, angle2, 0);
		expected.set(-0.5 * Math.cos(angle1) * Math.sin(angle2), 0.5 * Math.sin(angle1),
				0.5 * Math.cos(angle1) * Math.cos(angle2));
		assertTrue(v + " should match " + expected, v.epsilonEquals(expected, 1e-12));
	}

	private void checkDPPONI(DetectorProperties dp, DiffractionCrystalEnvironment env, double... angles) {
		dp.setNormalAnglesInDegrees(angles[0], angles[1], angles[2]);
		double[] coords = dp.pixelPreciseCoords(dp.getClosestPoint());
		PONIDiffractionMetadataDescriptor poni = new PONIDiffractionMetadataDescriptor(
				new DiffractionMetadata("blah", dp, env));
		Vector3d v = poni.getPixelPosition(coords[0], dp.getPy() - coords[1]);
		v.normalize();
		Vector3d normal = new Vector3d(dp.getNormal());
		normal.negate();
		PONIDiffractionMetadataDescriptor.DLS2PONI.transform(normal);
		assertTrue(v + " should match " + normal, normal.epsilonEquals(v, 1e-5));
	}

	@Test
	public void testDetectorPropertiesPONI() {
		double pixelSize = 0.1; // 0.1mm
		int height = 256;
		DiffractionCrystalEnvironment env = new DiffractionCrystalEnvironment(10.);
		DetectorProperties dp = new DetectorProperties(500, 200*pixelSize, (height-100)*pixelSize, height, 512, pixelSize, pixelSize);
		System.out.println(Arrays.toString(dp.getBeamCentreCoords()) + " => " + dp.getBeamCentrePosition());
		System.out.println("PONI: " + dp.getClosestPoint() + " => "
				+ Arrays.toString(dp.pixelPreciseCoords(dp.getClosestPoint())));

		checkDPPONI(dp, env, 0, 0, 0);

		double angle1 = 0.6;
		checkDPPONI(dp, env, angle1, 0, 0);

		double angle2 = -1.3;
		checkDPPONI(dp, env, 0, angle2, 0);

		checkDPPONI(dp, env, angle1, angle2, 0);

		double angle3 = 0.2;
		checkDPPONI(dp, env, 0, 0, angle3);

		checkDPPONI(dp, env, angle1, 0, angle3);

		checkDPPONI(dp, env, 0, angle2, angle3);

		checkDPPONI(dp, env, angle1, angle2, angle3);
	}

	/*
	 * 	
	 */
	@Test
	public void testCalib() {
		DiffractionCrystalEnvironment env = new DiffractionCrystalEnvironment(0.7301779019464133);
		double pixelSize = 0.172;
		int height = 1679;
		DetectorProperties dp = new DetectorProperties(398.82235525738247, 0, 0, height, 1475, pixelSize, pixelSize);
		dp.setOrientationEulerZYZ(Math.toRadians(-146.76213182), Math.toRadians(31.15564613), Math.PI);
		dp.setBeamCentreCoords(-235.72041018881208, 1509.5721759068924);
		PONIDiffractionMetadataDescriptor poni = new PONIDiffractionMetadataDescriptor(
				new DiffractionMetadata("blah", dp, env));

		System.out.println(poni.getStringDescription());
		Vector3d p = poni.getPixelPosition(767.65400473984, height - 852.0348193971477);
		Vector3d normal = new Vector3d(dp.getNormal());
		normal.negate();
		PONIDiffractionMetadataDescriptor.DLS2PONI.transform(normal);
		double d = 0.3412982206033781;
		normal.scale(d);
		
		System.out.println("poni = " + p + ";  n = " + normal);
		assertTrue(p.epsilonEquals(normal, 1e-10));
	}
}
