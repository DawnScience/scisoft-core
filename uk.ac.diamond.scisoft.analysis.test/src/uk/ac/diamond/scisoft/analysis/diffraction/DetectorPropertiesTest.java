/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.assertEquals;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.PNGSaver;

/**
 *
 */
public class DetectorPropertiesTest {

	static int[] detectorCorners = { 0, 0, 0, 3072, 3072, 0, 3072, 3072 };
	static double pixelSize = 0.1026;
	// static double pixelSize = 1.0;
	static int[] imageSizePix = { 3072, 3072 };
	static Vector3d detectorOrigin = new Vector3d(157.44, 157.44, 329.8);
	static Vector3d beamCentre = new Vector3d(0, 0, 329.8);
	static Matrix3d orientationMatrix = new Matrix3d(0.984657762021401, 0.017187265168157, -0.17364817766693,
			0.012961831885909, 0.985184016468007, 0.171010071662834, 0.174014604574351, -0.170637192932859,
			0.969846310392954);
	static double lambda = 1.001;

	private static String testScratchDirectoryName;
	private static String filePath = "detectorproperties.png";
	static double wavelength = 1.4;
	static int[] ishape = new int[] { 800, 400 };
	static double[] pxdim = new double[] { 0.2, 0.3 };
	static double[] origin = new double[] { 80, 60, 234 };
	static double ttheta = Math.toRadians(11.5);

	private static DoubleDataset makeDiffImage() {
		DoubleDataset data = new DoubleDataset(ishape[1], ishape[0]);
		double rho = origin[2] * Math.tan(ttheta);

		double dphi = 45.;
		Matrix3d orientn = new Matrix3d();
		orientn.setIdentity();
		DetectorProperties detprops = new DetectorProperties(new Vector3d(origin), ishape[1], ishape[0], pxdim[1],
				pxdim[0], orientn);
		DiffractionCrystalEnvironment diffexp = new DiffractionCrystalEnvironment(wavelength);
		QSpace qspace = new QSpace(detprops, diffexp);
		for (double phi = 0; phi < 360.; phi += dphi) {
			int x = (int) Math.floor((origin[0] + rho * Math.cos(Math.toRadians(phi))) / pxdim[0]);
			int y = (int) Math.floor((origin[1] + rho * Math.sin(Math.toRadians(phi))) / pxdim[1]);

			data.set(phi + 30, y, x);
			System.out.println("Q: " + qspace.qFromPixelPosition(x, y) + "; value = " + (phi + 30));
		}

		return data;
	}

	/**
	 * Creates an empty directory for use by test code.
	 * 
	 * @throws Exception
	 *             if the test fails
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(DetectorPropertiesTest.class
				.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
		DataHolder dh = new DataHolder();
		DoubleDataset data = makeDiffImage();
		dh.addDataset("testing data", data);
		new PNGSaver(testScratchDirectoryName + filePath).saveFile(dh);
	}

	/**
	 * test the setters
	 */
	@Test
	public void testSetters() {
		DetectorProperties det = new DetectorProperties();
		det.setHPxSize(pixelSize);
		det.setVPxSize(pixelSize);
		det.setPx(imageSizePix[0]);
		det.setPy(imageSizePix[1]);
		det.setOrientation(orientationMatrix);
		det.setOrigin(new Vector3d(50, 50, 300000));
	}

	@Test
	public void testCloningWithSetters() {
		DetectorProperties det = new DetectorProperties();
		det.setHPxSize(pixelSize);
		det.setVPxSize(pixelSize);
		det.setPx(imageSizePix[0]);
		det.setPy(imageSizePix[1]);
		det.setOrientation(orientationMatrix);
		det.setOrigin(new Vector3d(50, 50, 300000));
		det.setBeamVector(new Vector3d(0, 0, 1));

		DetectorProperties newDetector = det.clone();
		if (!det.equals(newDetector)) {
			Assert.fail("Cloned object not equal");
		}
	}

	@Test
	public void testCloning() {
		DetectorProperties det = new DetectorProperties(new Vector3d(50, 50, 300000), imageSizePix[0], imageSizePix[1],
				pixelSize, pixelSize, orientationMatrix);
		DetectorProperties newDetector = det.clone();
		if (!det.equals(newDetector)) {
			Assert.fail("Cloned object not equal");
		}
	}
	
	@Test
	public void testImageOrientation(){
		DetectorProperties det = new DetectorProperties(new Vector3d(50, 50, 300000), imageSizePix[0], imageSizePix[1],
				pixelSize, pixelSize, orientationMatrix);
		if(!orientationMatrix.equals(det.getOrientation()))
			Assert.fail("The orientation matrices are not equal");
	}

	@Test
	public void testBeamCentre() {
		DetectorProperties det = new DetectorProperties(detectorOrigin, imageSizePix[0], imageSizePix[1],
				pixelSize, pixelSize, orientationMatrix);

		double[] bc1 = det.getBeamCentreCoords();
		System.out.printf("Initial centre: %f, %f\n", bc1[0], bc1[1]);
		bc1[0] -= 75;
		bc1[1] += 120;
		det.setBeamCentreCoords(bc1);
		double[] bc2 = det.getBeamCentreCoords();
		Assert.assertEquals("X coord", bc1[0], bc2[0], 1e-7);
		Assert.assertEquals("Y coord", bc1[1], bc2[1], 1e-7);
	}

	@Test
	public void testDetectorOrigin() {
		DetectorProperties detector = new DetectorProperties(detectorOrigin, beamCentre, imageSizePix[0], imageSizePix[1], pixelSize,
				pixelSize, orientationMatrix);
		Vector3d newOrigin;

		newOrigin = new Vector3d(130, 120, 200);
		detector.setOrigin(newOrigin);
		assertEquals(detector.pixelPosition(0, 0), newOrigin);

		newOrigin = new Vector3d(-130, 120, 200);
		detector.setOrigin(newOrigin);
		assertEquals(detector.pixelPosition(0, 0), newOrigin);

		newOrigin = new Vector3d(130, -120, 200);
		detector.setOrigin(newOrigin);
		assertEquals(detector.pixelPosition(0, 0), newOrigin);

		newOrigin = new Vector3d(130, 120, -200);
		detector.setOrigin(newOrigin);
		assertEquals(detector.pixelPosition(0, 0), newOrigin);

		newOrigin = new Vector3d(-130, -120, 200);
		detector.setOrigin(newOrigin);
		assertEquals(detector.pixelPosition(0, 0), newOrigin);

		newOrigin = new Vector3d(130, -120, -200);
		detector.setOrigin(newOrigin);
		assertEquals(detector.pixelPosition(0, 0), newOrigin);

		newOrigin = new Vector3d(-130, -120, -200);
		detector.setOrigin(newOrigin);
		assertEquals(detector.pixelPosition(0, 0), newOrigin);
	}

	/**
	 * As a general check test the size of the detector a various orientations
	 */
	@Test
	public void testDetectorSize() {
	DetectorProperties detector = new DetectorProperties(detectorOrigin, beamCentre, imageSizePix[0], imageSizePix[1], pixelSize,
			pixelSize, orientationMatrix);
		// detector size for assert
		double detSizeX = detector.getDetectorSizeH();
		double detSizeY = detector.getDetectorSizeV();
		double diagDetSize = Math.sqrt((detSizeX * detSizeX) + (detSizeY * detSizeY));
		int[] detectorCorners = { 0, 0, 0, 3072, 3072, 0, 3072, 3072 };
		Vector3d px1topx4 = new Vector3d();

		px1topx4.sub(detector.pixelPosition(detectorCorners[0], detectorCorners[1]),
				detector.pixelPosition(detectorCorners[6], detectorCorners[7]));
		assertEquals(diagDetSize, px1topx4.length(), 0.00001);

		Matrix3d newOri = detector.getOrientation();
		newOri.rotY(1.5);
		detector.setOrientation(newOri);

		px1topx4.sub(detector.pixelPosition(detectorCorners[0], detectorCorners[1]),
				detector.pixelPosition(detectorCorners[6], detectorCorners[7]));
		assertEquals(diagDetSize, px1topx4.length(), 0.00001);

		detector.setOrigin(new Vector3d(-150, 250, 389));

		px1topx4.sub(detector.pixelPosition(detectorCorners[0], detectorCorners[1]),
				detector.pixelPosition(detectorCorners[6], detectorCorners[7]));
		assertEquals(diagDetSize, px1topx4.length(), 0.00001);

	}

	/**
	 * Check that vecmath has active transformation
	 */
	@Test
	public void testMatrixConvention() {
		Matrix3d a = new Matrix3d();
		double angle = Math.PI/6.;
		double answer = -Math.sin(angle);
		a.rotX(angle);
		Assert.assertEquals("X rotation", answer, a.getM12(), 1e-7);
		a.rotY(angle);
		Assert.assertEquals("Y rotation", answer, a.getM20(), 1e-7);
		a.rotZ(angle);
		Assert.assertEquals("Z rotation", answer, a.getM01(), 1e-7);
	}

	@Test
	public void testEulerConversions() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100,100});
		double[] angle;

		angle = det.getNormalAnglesInDegrees();
		Assert.assertEquals("Yaw",   0, angle[0], 1e-7);
		Assert.assertEquals("Pitch", 0, angle[1], 1e-7);
		Assert.assertEquals("Roll",  0, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(30, 25, -35);
		angle = det.getNormalAnglesInDegrees();
		Assert.assertEquals("Yaw",   30, angle[0], 1e-7);
		Assert.assertEquals("Pitch", 25, angle[1], 1e-7);
		Assert.assertEquals("Roll",  -35, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(-95, -65, 103);
		angle = det.getNormalAnglesInDegrees();
		Assert.assertEquals("Yaw",   -95, angle[0], 1e-7);
		Assert.assertEquals("Pitch", -65, angle[1], 1e-7);
		Assert.assertEquals("Roll",  103, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(-95, 90, 103);
		angle = det.getNormalAnglesInDegrees();
		Assert.assertEquals("Yaw",   -95-103+360, angle[0], 1e-7);
		Assert.assertEquals("Pitch", 90, angle[1], 1e-7);
		Assert.assertEquals("Roll",  0, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(-95, -90, 103);
		angle = det.getNormalAnglesInDegrees();
		Assert.assertEquals("Yaw",   -95+103, angle[0], 1e-7);
		Assert.assertEquals("Pitch", -90, angle[1], 1e-7);
		Assert.assertEquals("Roll",  0, angle[2], 1e-7);
	}

	@Test
	public void testBeamCentreDistance() {
		DetectorProperties det = new DetectorProperties(new Vector3d(500, 550, 600), 1000, 1100, 1, 1, null);
		
		Vector3d o = det.getOrigin();
		Assert.assertEquals("Detector origin, x", 500, o.x, 1e-7);
		Assert.assertEquals("Detector origin, y", 550, o.y, 1e-7);
		Assert.assertEquals("Detector origin, z", 600, o.z, 1e-7);

		double[] c = det.getBeamCentreCoords();
		Assert.assertEquals("Beam centre, x", 500, c[0], 1e-7);
		Assert.assertEquals("Beam centre, y", 550, c[1], 1e-7);

		Assert.assertEquals("Beam centre distance", 600, det.getBeamCentreDistance(), 1e-7);
		Assert.assertEquals("Detector distance", 600, det.getDetectorDistance(), 1e-7);
		det.setBeamCentreDistance(700);
		Assert.assertEquals("Beam centre distance", 700, det.getBeamCentreDistance(), 1e-7);
		Assert.assertEquals("Detector distance", 700, det.getDetectorDistance(), 1e-7);

		det.setDetectorDistance(600);
		Assert.assertEquals("Beam centre distance", 600, det.getBeamCentreDistance(), 1e-7);
		Assert.assertEquals("Detector distance", 600, det.getDetectorDistance(), 1e-7);

		c = det.getBeamCentreCoords();
		det.setNormalAnglesInDegrees(30, 0, 0);
		Assert.assertEquals("Beam centre distance", 600, det.getBeamCentreDistance(), 1e-7);
		Assert.assertEquals("Detector distance", 600*Math.cos(Math.toRadians(30)), det.getDetectorDistance(), 1e-7);
		double[] d = det.getBeamCentreCoords();
		Assert.assertEquals("Beam centre, x", c[0], d[0], 1e-7);
		Assert.assertEquals("Beam centre, y", c[1], d[1], 1e-7);
	}

	@Test
	public void testDetectorOrientation() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100,100});
		Vector3d row = new Vector3d(-1, 0, 0);
		double roll = 0;

		// check yaw
		Assert.assertEquals("Normal to row", 90, Math.toDegrees(det.getNormal().angle(row)), 1e-7);
		double yaw = 30;
		det.setNormalAnglesInDegrees(yaw, 0, 0);
		Vector3d n = det.getNormal();
		Assert.assertEquals("Normal to row", 90+yaw, Math.toDegrees(n.angle(row)), 1e-7);
		Assert.assertEquals("Normal x", Math.sin(Math.toRadians(yaw)), n.x, 1e-7);
		Assert.assertEquals("Normal y", 0, n.y, 1e-7);
		Assert.assertEquals("Normal z", -Math.cos(Math.toRadians(yaw)), n.z, 1e-7);

		// check pitch
		double pitch = 30;
		det.setNormalAnglesInDegrees(0, pitch, 0);
		n = det.getNormal();
		Assert.assertEquals("Normal to row", 90, Math.toDegrees(n.angle(row)), 1e-7);
		Assert.assertEquals("Normal to beam", 180-pitch, Math.toDegrees(det.getBeamVector().angle(n)), 1e-7);
		Assert.assertEquals("Normal x", 0, n.x, 1e-7);
		Assert.assertEquals("Normal y", Math.sin(Math.toRadians(pitch)), n.y, 1e-7);
		Assert.assertEquals("Normal z", -Math.cos(Math.toRadians(pitch)), n.z, 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 0);
		Assert.assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		Assert.assertEquals("Image col angle", 90-roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		roll = 30;
		det.setNormalAnglesInDegrees(0, 0, roll);
		n = new Vector3d(det.getNormal());
		Assert.assertEquals("Normal", 180, Math.toDegrees(det.getBeamVector().angle(n)), 1e-7);
		Assert.assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		Assert.assertEquals("Image col angle", 90-roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		roll = 60;
		det.setNormalAnglesInDegrees(0, 0, roll);
		Assert.assertEquals("Normal", 0, Math.toDegrees(det.getNormal().angle(n)), 1e-7);
		Assert.assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		Assert.assertEquals("Image col angle", 90-roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		roll = 120;
		det.setNormalAnglesInDegrees(0, 0, roll);
		Assert.assertEquals("Normal", 0, Math.toDegrees(det.getNormal().angle(n)), 1e-7);
		Assert.assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		if (roll > 90) {
			roll = roll - 90;
		} else {
			roll = 90 - roll;
		}
		Assert.assertEquals("Image col angle", roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		// test normal is same for any roll...
		Vector3d na = getNormal(30, 0, 0);
		Vector3d nb = getNormal(0, 30, 0);
		Vector3d nc = getNormal(30, 30, 0);

		getNormal(0, 0, 30);

		n = getNormal(30, 0, 30);
		Assert.assertTrue("Normals rolled", n.epsilonEquals(na, 1e-7));
		n = getNormal(0, 30, 30);
		Assert.assertTrue("Normals rolled", n.epsilonEquals(nb, 1e-7));
		n = getNormal(30, 30, 30);
		Assert.assertTrue("Normals rolled", n.epsilonEquals(nc, 1e-7));

		// check normal to row angle
		det.setNormalAnglesInDegrees(0, 0, 0);
		Assert.assertEquals("Normal to row", 90, Math.toDegrees(row.angle(det.getNormal())), 1e-7);
		det.setNormalAnglesInDegrees(30, 0, roll);
		nb = det.getNormal();
		Assert.assertEquals("Normals rolled", 0, Math.toDegrees(na.angle(nb)), 1e-7);

		// check sequence of normal angle changes through no-intersection cases and its effect on beam centre distance
		det.setNormalAnglesInDegrees(70, 0, 0);
		double dist = det.getBeamCentreDistance();
		double[] centre = det.getBeamCentreCoords();
		det.setNormalAnglesInDegrees(90, 0, 0);

		Assert.assertTrue("No intersection", Double.isInfinite(det.getBeamCentreDistance()));
		double[] centre2 = det.getBeamCentreCoords();
		Assert.assertTrue("No intersection", Double.isNaN(centre2[0]));
		Assert.assertTrue("No intersection", Double.isNaN(centre2[1]));

		det.setNormalAnglesInDegrees(70, 0, 0);
		Assert.assertEquals("Restored", dist, det.getBeamCentreDistance(), 1e-7);
		centre2 = det.getBeamCentreCoords();
		Assert.assertEquals("Restored", centre[0], centre2[0], 1e-7);
		Assert.assertEquals("Restored", centre[1], centre2[1], 1e-7);

		det.setNormalAnglesInDegrees(70, 5, 0);
		dist = det.getBeamCentreDistance();
		centre = det.getBeamCentreCoords();
		det.setNormalAnglesInDegrees(70, 0, 0);
		det.setNormalAnglesInDegrees(90, 0, 0);
		det.setNormalAnglesInDegrees(90, 5, 0);
//		det.setNormalAnglesInDegrees(70, 0, 0);
		det.setNormalAnglesInDegrees(70, 5, 0);
		centre2 = det.getBeamCentreCoords();
		Assert.assertEquals("Restored", dist, det.getBeamCentreDistance(), 1e-7);
		Assert.assertEquals("Restored", centre[0], centre2[0], 1e-7);
		Assert.assertEquals("Restored", centre[1], centre2[1], 1e-7);

		det.setNormalAnglesInDegrees(90, 0, 0);
		Assert.assertEquals("Yaw 90", 90, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(120, 0, 0);
		Assert.assertEquals("Yaw 120", 120, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(180, 0, 0);
		Assert.assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(185, 0, 0);
		Assert.assertEquals("Yaw -175", -175, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(-120, 0, 0);
		Assert.assertEquals("Yaw -120", -120, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(-180, 0, 0);
		Assert.assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(-185, 0, 0);
		Assert.assertEquals("Yaw 175", 175, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(0, 85, 0);
		Assert.assertEquals("Pitch 85", 85, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, 90, 0);
		Assert.assertEquals("Pitch 90", 90, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, 95, 0);
		Assert.assertEquals("Pitch 85", 85, det.getNormalAnglesInDegrees()[1], 1e-7);
		Assert.assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);
		Assert.assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, -85, 0);
		Assert.assertEquals("Pitch -85", -85, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, -90, 0);
		Assert.assertEquals("Pitch -90", -90, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, -95, 0);
		Assert.assertEquals("Pitch 85", -85, det.getNormalAnglesInDegrees()[1], 1e-7);
		Assert.assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);
		Assert.assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 90);
		Assert.assertEquals("Roll 90", 90, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 179);
		Assert.assertEquals("Roll 179", 179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 180);
		Assert.assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 181);
		Assert.assertEquals("Roll -179", -179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -90);
		Assert.assertEquals("Roll -90", -90, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -179);
		Assert.assertEquals("Roll -179", -179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -180);
		Assert.assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -181);
		Assert.assertEquals("Roll 179", 179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -90);
		Assert.assertEquals("Roll -90", -90, det.getNormalAnglesInDegrees()[2], 1e-7);
	}

	private Vector3d getNormal(double... angles) {
		Matrix3d ta;
		Vector3d va;

		ta = DetectorProperties.inverseMatrixFromEulerAngles(angles[0], angles[1], angles[2]);
		va = new Vector3d(0, 0, -1);
		ta.transform(va);
//		System.err.println(Arrays.toString(angles) + ": " + va);
		return va;
	}

	public Vector3d findMajor(final Vector3d beam, final Vector3d normal) {
		Vector3d u = new Vector3d();
		Vector3d v = new Vector3d(-1, 0, 0);

		u.cross(normal, beam);
		if (u.length() != 0) {
			v.cross(u, normal);
			v.normalize();
		}
		return v;
	}

	@Test
	public void testMajorAxis() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100, 100});

		final Vector3d beam = det.getBeamVector();
		Vector3d major = findMajor(beam, det.getNormal());
		double angle = 0;

		Assert.assertEquals("Maj x", -1, major.x, 1e-7);
		Assert.assertEquals("Maj y", 0, major.y, 1e-7);
		Assert.assertEquals("Maj z", 0, major.z, 1e-7);

		angle = 30;
		det.setNormalAnglesInDegrees(angle, 0, 0);
		major = findMajor(beam, det.getNormal());
		Assert.assertEquals("Maj x", Math.cos(Math.toRadians(angle)), major.x, 1e-7);
		Assert.assertEquals("Maj y", 0, major.y, 1e-7);
		Assert.assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, angle, 0);
		major = findMajor(beam, det.getNormal());
		Assert.assertEquals("Maj x", 0, major.x, 1e-7);
		Assert.assertEquals("Maj y", Math.cos(Math.toRadians(angle)), major.y, 1e-7);
		Assert.assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, 0, angle);
		major = findMajor(beam, det.getNormal());
		Assert.assertEquals("Maj x", -1, major.x, 1e-7);
		Assert.assertEquals("Maj y", 0, major.y, 1e-7);
		Assert.assertEquals("Maj z", 0, major.z, 1e-7);

		det.setNormalAnglesInDegrees(-angle, 0, 0);
		major = findMajor(beam, det.getNormal());
		Assert.assertEquals("Maj x", -Math.cos(Math.toRadians(angle)), major.x, 1e-7);
		Assert.assertEquals("Maj y", 0, major.y, 1e-7);
		Assert.assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, -angle, 0);
		major = findMajor(beam, det.getNormal());
		Assert.assertEquals("Maj x", 0, major.x, 1e-7);
		Assert.assertEquals("Maj y", -Math.cos(Math.toRadians(angle)), major.y, 1e-7);
		Assert.assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -angle);
		major = findMajor(beam, det.getNormal());
		Assert.assertEquals("Maj x", -1, major.x, 1e-7);
		Assert.assertEquals("Maj y", 0, major.y, 1e-7);
		Assert.assertEquals("Maj z", 0, major.z, 1e-7);
	}


	@Test
	public void testMatrixMultiply() {
		Matrix3d a = new Matrix3d();
		a.setM01(1);
		a.setM10(1);
		a.setM22(1);

		Matrix3d b = new Matrix3d();
		b.setM00(1);
		b.setM11(-1);
		b.setM22(1);

		Matrix3d c = new Matrix3d();
		c.mul(b, a);
		Assert.assertEquals("M01", 1, c.getM01(), 1e-7);
		Assert.assertEquals("M10", -1, c.getM10(), 1e-7);
		Assert.assertEquals("M22", 1, c.getM22(), 1e-7);

		c.mul(a, b);
		Assert.assertEquals("M01", -1, c.getM01(), 1e-7);
		Assert.assertEquals("M10", 1, c.getM10(), 1e-7);
		Assert.assertEquals("M22", 1, c.getM22(), 1e-7);

		// this.mul(other) == this x mul
		a.mul(b);
		Assert.assertEquals("M01", a.getM01(), c.getM01(), 1e-7);
		Assert.assertEquals("M10", a.getM10(), c.getM10(), 1e-7);
		Assert.assertEquals("M22", a.getM22(), c.getM22(), 1e-7);
	}
}
