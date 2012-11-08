/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
	
}
