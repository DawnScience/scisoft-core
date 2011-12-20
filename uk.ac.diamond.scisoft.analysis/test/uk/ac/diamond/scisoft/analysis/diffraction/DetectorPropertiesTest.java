/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import gda.util.TestUtils;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
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
	static DetectorProperties det;
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
}
