/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;
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

	private static String filePath = "detectorproperties.png";
	static double wavelength = 1.4;
	static int[] ishape = new int[] { 800, 400 };
	static double[] pxdim = new double[] { 0.2, 0.3 };
	static double[] origin = new double[] { 80, 60, 234 };
	static double ttheta = Math.toRadians(11.5);

	private static DoubleDataset makeDiffImage() {
		DoubleDataset data = DatasetFactory.zeros(DoubleDataset.class, ishape[1], ishape[0]);
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
		String testScratchDirectoryName = IOTestUtils.setUpTestClass(DetectorPropertiesTest.class, true);
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
			fail("Cloned object not equal");
		}
	}

	@Test
	public void testCloning() {
		DetectorProperties det = new DetectorProperties(new Vector3d(50, 50, 300000), imageSizePix[0], imageSizePix[1],
				pixelSize, pixelSize, orientationMatrix);
		DetectorProperties newDetector = det.clone();
		if (!det.equals(newDetector)) {
			fail("Cloned object not equal");
		}
	}
	
	@Test
	public void testImageOrientation(){
		DetectorProperties det = new DetectorProperties(new Vector3d(50, 50, 300000), imageSizePix[0], imageSizePix[1],
				pixelSize, pixelSize, orientationMatrix);
		if(!orientationMatrix.equals(det.getOrientation()))
			fail("The orientation matrices are not equal");
	}

	@Test
	public void testBeamCentre() {
		DetectorProperties det = new DetectorProperties(detectorOrigin, imageSizePix[0], imageSizePix[1],
				pixelSize, pixelSize, orientationMatrix);

		double[] bc1 = det.intersectPreciseCoords(null, null, null);
		System.out.printf("Initial centre: %f, %f\n", bc1[0], bc1[1]);
		bc1[0] -= 75;
		bc1[1] += 120;
		det.setBeamCentreCoords(bc1);
		double[] bc2 = det.intersectPreciseCoords(null, null, null);
		assertEquals("X coord", bc1[0], bc2[0], 1e-7);
		assertEquals("Y coord", bc1[1], bc2[1], 1e-7);
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

	@Test
	public void testDetectorMethods() {
		DetectorProperties detector = new DetectorProperties(detectorOrigin, beamCentre, imageSizePix[0], imageSizePix[1], pixelSize,
				pixelSize, orientationMatrix);

		Vector3d p = new Vector3d();
		Vector3d c = new Vector3d();
		for (int x = 0; x < imageSizePix[0]; x++) {
			for (int y = 0; y < imageSizePix[1]; y++) {
				detector.pixelPosition(x, y, p);
				detector.pixelCoords(p, c);
				assertEquals(x, c.x, 1);
				assertEquals(y, c.y, 1);
			}
		}
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
		assertEquals("X rotation", answer, a.getM12(), 1e-7);
		a.rotY(angle);
		assertEquals("Y rotation", answer, a.getM20(), 1e-7);
		a.rotZ(angle);
		assertEquals("Z rotation", answer, a.getM01(), 1e-7);
	}

	@Test
	public void testEulerConversions() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100,100});
		double[] angle;

		angle = det.getNormalAnglesInDegrees();
		assertEquals("Yaw",   0, angle[0], 1e-7);
		assertEquals("Pitch", 0, angle[1], 1e-7);
		assertEquals("Roll",  0, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(30, 25, -35);
		angle = det.getNormalAnglesInDegrees();
		assertEquals("Yaw",   30, angle[0], 1e-7);
		assertEquals("Pitch", 25, angle[1], 1e-7);
		assertEquals("Roll",  -35, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(-95, -65, 103);
		angle = det.getNormalAnglesInDegrees();
		assertEquals("Yaw",   -95, angle[0], 1e-7);
		assertEquals("Pitch", -65, angle[1], 1e-7);
		assertEquals("Roll",  103, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(-95, 90, 103);
		angle = det.getNormalAnglesInDegrees();
		assertEquals("Yaw",   -95-103+360, angle[0], 1e-7);
		assertEquals("Pitch", 90, angle[1], 1e-7);
		assertEquals("Roll",  0, angle[2], 1e-7);

		det.setNormalAnglesInDegrees(-95, -90, 103);
		angle = det.getNormalAnglesInDegrees();
		assertEquals("Yaw",   -95+103, angle[0], 1e-7);
		assertEquals("Pitch", -90, angle[1], 1e-7);
		assertEquals("Roll",  0, angle[2], 1e-7);
	}

	@Test
	public void testEulerXYZ() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100,100});
		det.setOrientationEulerXYZ(Math.toRadians(0.4663), Math.toRadians(-44.01), Math.toRadians(-88.99));
		Matrix3d m = det.getOrientation();
		double[] row = new double[3];
		m.getRow(0, row);
		assertArrayEquals(new double[] {0.01268187, -0.99991112,  0.00411266}, row, 1e-4);
		m.getRow(1, row);
		assertArrayEquals(new double[] {0.71913747,  0.01197841,  0.69476458}, row, 1e-4);
		m.getRow(2, row);
		assertArrayEquals(new double[] {-0.69475209, -0.00585335,  0.71922547}, row, 1e-4);
	}

	@Test
	public void testEulerZYZ() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100,100});
		det.setOrientationEulerZYZ(Math.toRadians(10), Math.toRadians(20), Math.toRadians(25));
		Matrix3d m = det.getOrientation();
		Matrix3d me = MatrixUtils.createOrientationFromEulerZYZ(10, 20, 25);
		MatrixUtils.isClose(me, m, 1e-14, 1e-14);

		double[] angles = MatrixUtils.calculateFromOrientationEulerZYZ(m);
		assertArrayEquals(new double[] {10, 20, 25}, angles, 1e-4);
	}

	@Test
	public void testBeamCentreDistance() {
		DetectorProperties det = new DetectorProperties(new Vector3d(500, 550, 600), 1000, 1100, 1, 1, null);
		
		Vector3d o = det.getOrigin();
		assertEquals("Detector origin, x", 500, o.x, 1e-7);
		assertEquals("Detector origin, y", 550, o.y, 1e-7);
		assertEquals("Detector origin, z", 600, o.z, 1e-7);

		double[] c = det.intersectPreciseCoords(null, null, null);
		assertEquals("Beam centre, x", 500, c[0], 1e-7);
		assertEquals("Beam centre, y", 550, c[1], 1e-7);

		assertEquals("Beam centre distance", 600, det.getBeamCentreDistance(), 1e-7);
		assertEquals("Detector distance", 600, det.getDetectorDistance(), 1e-7);
		det.setBeamCentreDistance(700);
		assertEquals("Beam centre distance", 700, det.getBeamCentreDistance(), 1e-7);
		assertEquals("Detector distance", 700, det.getDetectorDistance(), 1e-7);

		det.setDetectorDistance(600);
		assertEquals("Beam centre distance", 600, det.getBeamCentreDistance(), 1e-7);
		assertEquals("Detector distance", 600, det.getDetectorDistance(), 1e-7);

		c = det.intersectPreciseCoords(null, null, null);
		det.setNormalAnglesInDegrees(30, 0, 0);
		assertEquals("Beam centre distance", 600, det.getBeamCentreDistance(), 1e-7);
		assertEquals("Detector distance", 600*Math.cos(Math.toRadians(30)), det.getDetectorDistance(), 1e-7);
		double[] d = det.intersectPreciseCoords(null, null, null);
		assertEquals("Beam centre, x", c[0], d[0], 1e-7);
		assertEquals("Beam centre, y", c[1], d[1], 1e-7);
	}

	@Test
	public void testDetectorOrientation() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100,100});
		Vector3d row = new Vector3d(-1, 0, 0);
		double roll = 0;

		// check yaw
		assertEquals("Normal to row", 90, Math.toDegrees(det.getNormal().angle(row)), 1e-7);
		double yaw = 30;
		det.setNormalAnglesInDegrees(yaw, 0, 0);
		Vector3d n = det.getNormal();
		assertEquals("Normal to row", 90+yaw, Math.toDegrees(n.angle(row)), 1e-7);
		assertEquals("Normal x", Math.sin(Math.toRadians(yaw)), n.x, 1e-7);
		assertEquals("Normal y", 0, n.y, 1e-7);
		assertEquals("Normal z", -Math.cos(Math.toRadians(yaw)), n.z, 1e-7);

		// check pitch
		double pitch = 30;
		det.setNormalAnglesInDegrees(0, pitch, 0);
		n = det.getNormal();
		assertEquals("Normal to row", 90, Math.toDegrees(n.angle(row)), 1e-7);
		assertEquals("Normal to beam", 180-pitch, Math.toDegrees(det.getBeamVector().angle(n)), 1e-7);
		assertEquals("Normal x", 0, n.x, 1e-7);
		assertEquals("Normal y", Math.sin(Math.toRadians(pitch)), n.y, 1e-7);
		assertEquals("Normal z", -Math.cos(Math.toRadians(pitch)), n.z, 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 0);
		assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		assertEquals("Image col angle", 90-roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		roll = 30;
		det.setNormalAnglesInDegrees(0, 0, roll);
		n = new Vector3d(det.getNormal());
		assertEquals("Normal", 180, Math.toDegrees(det.getBeamVector().angle(n)), 1e-7);
		assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		assertEquals("Image col angle", 90-roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		roll = 60;
		det.setNormalAnglesInDegrees(0, 0, roll);
		assertEquals("Normal", 0, Math.toDegrees(det.getNormal().angle(n)), 1e-7);
		assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		assertEquals("Image col angle", 90-roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		roll = 120;
		det.setNormalAnglesInDegrees(0, 0, roll);
		assertEquals("Normal", 0, Math.toDegrees(det.getNormal().angle(n)), 1e-7);
		assertEquals("Image row angle", roll, Math.toDegrees(det.getPixelRow().angle(row)), 1e-7);
		if (roll > 90) {
			roll = roll - 90;
		} else {
			roll = 90 - roll;
		}
		assertEquals("Image col angle", roll, Math.toDegrees(det.getPixelColumn().angle(row)), 1e-7);

		// test normal is same for any roll...
		Vector3d na = getNormal(30, 0, 0);
		Vector3d nb = getNormal(0, 30, 0);
		Vector3d nc = getNormal(30, 30, 0);

		getNormal(0, 0, 30);

		n = getNormal(30, 0, 30);
		assertTrue("Normals rolled", n.epsilonEquals(na, 1e-7));
		n = getNormal(0, 30, 30);
		assertTrue("Normals rolled", n.epsilonEquals(nb, 1e-7));
		n = getNormal(30, 30, 30);
		assertTrue("Normals rolled", n.epsilonEquals(nc, 1e-7));

		// check normal to row angle
		det.setNormalAnglesInDegrees(0, 0, 0);
		assertEquals("Normal to row", 90, Math.toDegrees(row.angle(det.getNormal())), 1e-7);
		det.setNormalAnglesInDegrees(30, 0, roll);
		nb = det.getNormal();
		assertEquals("Normals rolled", 0, Math.toDegrees(na.angle(nb)), 1e-7);

		// check sequence of normal angle changes through no-intersection cases and its effect on beam centre distance
		det.setNormalAnglesInDegrees(70, 0, 0);
		double dist = det.getBeamCentreDistance();
		double[] centre = det.intersectPreciseCoords(null, null, null);
		det.setNormalAnglesInDegrees(90, 0, 0);

		assertTrue("No intersection", Double.isInfinite(det.getBeamCentreDistance()));
		double[] centre2 = det.intersectPreciseCoords(null, null, null);
		assertTrue("No intersection", Double.isNaN(centre2[0]));
		assertTrue("No intersection", Double.isNaN(centre2[1]));

		det.setNormalAnglesInDegrees(70, 0, 0);
		assertEquals("Restored", dist, det.getBeamCentreDistance(), 1e-7);
		centre2 = det.intersectPreciseCoords(null, null, null);
		assertEquals("Restored", centre[0], centre2[0], 1e-7);
		assertEquals("Restored", centre[1], centre2[1], 1e-7);

		det.setNormalAnglesInDegrees(70, 5, 0);
		dist = det.getBeamCentreDistance();
		centre = det.intersectPreciseCoords(null, null, null);
		det.setNormalAnglesInDegrees(70, 0, 0);
		det.setNormalAnglesInDegrees(90, 0, 0);
		det.setNormalAnglesInDegrees(90, 5, 0);
//		det.setNormalAnglesInDegrees(70, 0, 0);
		det.setNormalAnglesInDegrees(70, 5, 0);
		centre2 = det.intersectPreciseCoords(null, null, null);
		assertEquals("Restored", dist, det.getBeamCentreDistance(), 1e-7);
		assertEquals("Restored", centre[0], centre2[0], 1e-7);
		assertEquals("Restored", centre[1], centre2[1], 1e-7);

		det.setNormalAnglesInDegrees(90, 0, 0);
		assertEquals("Yaw 90", 90, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(120, 0, 0);
		assertEquals("Yaw 120", 120, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(180, 0, 0);
		assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(185, 0, 0);
		assertEquals("Yaw -175", -175, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(-120, 0, 0);
		assertEquals("Yaw -120", -120, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(-180, 0, 0);
		assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(-185, 0, 0);
		assertEquals("Yaw 175", 175, det.getNormalAnglesInDegrees()[0], 1e-7);

		det.setNormalAnglesInDegrees(0, 85, 0);
		assertEquals("Pitch 85", 85, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, 90, 0);
		assertEquals("Pitch 90", 90, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, 95, 0);
		assertEquals("Pitch 85", 85, det.getNormalAnglesInDegrees()[1], 1e-7);
		assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);
		assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, -85, 0);
		assertEquals("Pitch -85", -85, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, -90, 0);
		assertEquals("Pitch -90", -90, det.getNormalAnglesInDegrees()[1], 1e-7);

		det.setNormalAnglesInDegrees(0, -95, 0);
		assertEquals("Pitch 85", -85, det.getNormalAnglesInDegrees()[1], 1e-7);
		assertEquals("Yaw 180", 180, det.getNormalAnglesInDegrees()[0], 1e-7);
		assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 90);
		assertEquals("Roll 90", 90, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 179);
		assertEquals("Roll 179", 179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 180);
		assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 181);
		assertEquals("Roll -179", -179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -90);
		assertEquals("Roll -90", -90, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -179);
		assertEquals("Roll -179", -179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -180);
		assertEquals("Roll 180", 180, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -181);
		assertEquals("Roll 179", 179, det.getNormalAnglesInDegrees()[2], 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -90);
		assertEquals("Roll -90", -90, det.getNormalAnglesInDegrees()[2], 1e-7);
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
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(100, 100);

		final Vector3d beam = det.getBeamVector();
		Vector3d major = findMajor(beam, det.getNormal());
		double angle = 0;

		assertEquals("Maj x", -1, major.x, 1e-7);
		assertEquals("Maj y", 0, major.y, 1e-7);
		assertEquals("Maj z", 0, major.z, 1e-7);

		angle = 30;
		det.setNormalAnglesInDegrees(angle, 0, 0);
		major = findMajor(beam, det.getNormal());
		assertEquals("Maj x", Math.cos(Math.toRadians(angle)), major.x, 1e-7);
		assertEquals("Maj y", 0, major.y, 1e-7);
		assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, angle, 0);
		major = findMajor(beam, det.getNormal());
		assertEquals("Maj x", 0, major.x, 1e-7);
		assertEquals("Maj y", Math.cos(Math.toRadians(angle)), major.y, 1e-7);
		assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, 0, angle);
		major = findMajor(beam, det.getNormal());
		assertEquals("Maj x", -1, major.x, 1e-7);
		assertEquals("Maj y", 0, major.y, 1e-7);
		assertEquals("Maj z", 0, major.z, 1e-7);

		det.setNormalAnglesInDegrees(-angle, 0, 0);
		major = findMajor(beam, det.getNormal());
		assertEquals("Maj x", -Math.cos(Math.toRadians(angle)), major.x, 1e-7);
		assertEquals("Maj y", 0, major.y, 1e-7);
		assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, -angle, 0);
		major = findMajor(beam, det.getNormal());
		assertEquals("Maj x", 0, major.x, 1e-7);
		assertEquals("Maj y", -Math.cos(Math.toRadians(angle)), major.y, 1e-7);
		assertEquals("Maj z", Math.sin(Math.toRadians(angle)), major.z, 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -angle);
		major = findMajor(beam, det.getNormal());
		assertEquals("Maj x", -1, major.x, 1e-7);
		assertEquals("Maj y", 0, major.y, 1e-7);
		assertEquals("Maj z", 0, major.z, 1e-7);
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
		assertEquals("M01", 1, c.getM01(), 1e-7);
		assertEquals("M10", -1, c.getM10(), 1e-7);
		assertEquals("M22", 1, c.getM22(), 1e-7);

		c.mul(a, b);
		assertEquals("M01", -1, c.getM01(), 1e-7);
		assertEquals("M10", 1, c.getM10(), 1e-7);
		assertEquals("M22", 1, c.getM22(), 1e-7);

		// this.mul(other) == this x mul
		a.mul(b);
		assertEquals("M01", a.getM01(), c.getM01(), 1e-7);
		assertEquals("M10", a.getM10(), c.getM10(), 1e-7);
		assertEquals("M22", a.getM22(), c.getM22(), 1e-7);
	}

	@Test
	public void testSolidAngle() {
		Vector3d a = new Vector3d(1, -1, 1);
		Vector3d b = new Vector3d(1, 1, 1);
		Vector3d c = new Vector3d(1, -1, -1);
		// half of a cube side with right angle corner at vertex a
		double s = DetectorProperties.calculatePlaneTriangleSolidAngle(a, b, c);
		assertEquals(4.*Math.PI/12, s, 1e-12);

		// check cos(alpha)^3 for a triangle in y-z that recedes
		double alpha = Math.toRadians(24.);
		double ca = Math.cos(alpha);
		double ta = Math.tan(alpha);
		double side = 1;
		double x, y;

		double factor = ca * ca * ca;

		for (x = 200; x < 2000; x += 50) {
			y = ta*x;
			a.set(x, y, -side);
			b.set(x, y, side);
			c.set(x, y + side, 0);
	
			s = DetectorProperties.calculatePlaneTriangleSolidAngle(a, b, c);
			double r = side/x;
			// ratio of solid angle subtended to that estimated by area by distance squared
			// tends to cos(alpha)^3
			assertEquals(factor, s/(r*r), factor*r/2.682);
		}
	}

	@Test
	public void testSolidAngle2() {
		double sx = 0.5;
		double dx = 1.0;
		double sy = 0.5;
		double dy = 2.0;
		double s = expectedRectangle(sx, dx, sy, dy);

		double s2 = DetectorProperties.calculatePixelSolidAngle(sx, sy, 1, dx, dy);
		assertEquals(s, s2, 1e-12);

		sx = -0.25;
		s = expectedRectangle(sx, dx, sy, dy);
		s2 = DetectorProperties.calculatePixelSolidAngle(sx, sy, 1, dx, dy);
		assertEquals(s, s2, 1e-5);

		sx = 0.5;
		sy = -0.5;
		s = expectedRectangle(sx, dx, sy, dy);
		s2 = DetectorProperties.calculatePixelSolidAngle(sx, sy, 1, dx, dy);
		assertEquals(s, s2, 1e-5);

		sx = -0.25;
		s = expectedRectangle(sx, dx, sy, dy);
		s2 = DetectorProperties.calculatePixelSolidAngle(sx, sy, 1, dx, dy);
		assertEquals(s, s2, 1e-5);

		double a = 1.;
		for (int i = 0; i < 16; i++) {
			checkExactSolidAngle(a, 0.5, 1);
			a *= 0.5;
		}
	}

	private static double expectedRectangle(double sx, double dx, double sy, double dy) {
		Vector3d a = new Vector3d(sx, sy, 1);
		Vector3d b = new Vector3d(sx+dx, sy, 1);
		Vector3d c = new Vector3d(sx+dx, sy+dy, 1);
		Vector3d d = new Vector3d(sx, sy+dy, 1);
		return DetectorProperties.calculatePlaneTriangleSolidAngle(a, b, c)
				+ DetectorProperties.calculatePlaneTriangleSolidAngle(c, d, a);
	}

	public static void checkExactSolidAngle(double a, double b, double c) {
		double alpha = a/c;
		double beta = b/c;
		double gamma = alpha*beta;
		double expected = Math.atan(gamma/Math.sqrt(1 + alpha*alpha + beta*beta));
		assertEquals(expected, DetectorProperties.calculateRectangleSolidAngle(a,  b, c), Math.abs(expected)/1024.);
	}

	private double calculateSolidAngleOld(DetectorProperties dp, int x, int y) {
		Vector3d a = dp.pixelPosition(x, y);
		Vector3d ab = dp.getPixelRow();
		Vector3d ac = dp.getPixelColumn();

		Vector3d b = new Vector3d();
		Vector3d c = new Vector3d();
		b.add(a, ab);
		c.add(a, ac);
		double s = DetectorProperties.calculatePlaneTriangleSolidAngle(a, b, c);

		a.add(b, ac);
		return s + DetectorProperties.calculatePlaneTriangleSolidAngle(b, a, c); // order is important
	}

	@Test
	public void testSolidAngleI07() {
		DetectorProperties dp = new DetectorProperties(898, 59.84, 11.22, 2069, 515, 0.055, 0.055);
		dp.setStartX(800);
		dp.setPx(400);
		dp.setStartY(200);
		dp.setPy(200);
		for (int j = 0; j < 200; j++) {
			for (int i = 0; i < 400; i++) {
				double sa = dp.calculateSolidAngle(i, j);
				double sb = calculateSolidAngleOld(dp, i, j);
				assertEquals(sb, sa, 1e-12);
			}
		}
	}

	@Test
	public void testPyFAIConventions() {
		// from pyFAI import detectors, azimuthalIntegrator
		// da = detectors.Detector(1,1)
		// ai = azimuthalIntegrator.AzimuthalIntegrator(dist=1, detector=da, wavelength=2.5e-3)
		// import math
		// ai.rot1 = math.radians(25.)
		// ai.rot2 = math.radians(34.) 
		// ai.rot3 = math.radians(15.)
		// ai.rotation_matrix()
		// # Out: 
		// # array([[ 0.8007888 , -0.00629717, -0.59891372],
		// #        [ 0.21457071,  0.93659154,  0.27704817],
		// #        [ 0.5591929 , -0.35036642,  0.75136321]])

		Matrix3d mPyFAI = new Matrix3d(new double[] {0.8007888, -0.00629717, -0.59891372,
				0.21457071, 0.93659154, 0.27704817,
				0.5591929, -0.35036642,  0.75136321});
		Matrix3d toPyFAI = new Matrix3d(new double[] {0,1,0, 1,0,0, 0,0,1,});
		Matrix3d m = DetectorProperties.inverseMatrixFromEulerAngles(25, -34, -15);

		m.mul(toPyFAI, m);
		m.mulTransposeRight(m, toPyFAI);
		m.transpose();
		System.out.println(m);
		assertTrue(mPyFAI.epsilonEquals(m, 1e-8));

		DetectorProperties d = DetectorProperties.getDefaultDetectorProperties(100,100);
		d.setNormalAnglesInDegrees(25, -34, -15);

		m = new Matrix3d(d.getOrientation());
		m.mulTransposeLeft(toPyFAI, m);
		m.mul(m, toPyFAI);
		System.out.println(m);
		assertTrue(mPyFAI.epsilonEquals(m, 1e-8));
	}
}
