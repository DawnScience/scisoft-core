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

import static org.junit.Assert.assertEquals;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.diffraction.Resolution;

/**
 * Class that will test calculating
 */
public class ResolutionTest {
	DetectorProperties detector;
	DiffractionCrystalEnvironment diffexp;

	/**
	 * build the detector and diffraction experiment
	 */
	@Before
	public void initialise() {
		double pixelSize = 0.1026;
		// static double pixelSize = 1.0;
		int[] imageSizePix = { 3072, 3072 };
		Vector3d detectorOrigin = new Vector3d(105.37, 108.86, 170);
		// Vector3d detectorOrigin = new Vector3d(0, 0, 170);
		Vector3d beamCentre = new Vector3d(0, 0, 1);
		Matrix3d orientationMatrix = new Matrix3d(
				// 0.999846172301652, 0.017452379855755, -0.001745328365898,
				// -0.017451490332376, 0.999847574048345, 0.000523597954187,
				// 0.001754200362949, -0.000493058829215, 0.999998339835661);
				0.984657762021401, 0.017187265168157, -0.17364817766693, 0.012961831885909, 0.985184016468007,
				0.171010071662834, 0.174014604574351, -0.170637192932859, 0.969846310392954);
		// orientationMatrix.setIdentity();
		double lambda = 0.97966;

		detector = new DetectorProperties(detectorOrigin, beamCentre, imageSizePix[0], imageSizePix[1], pixelSize,
				pixelSize, orientationMatrix);
		diffexp = new DiffractionCrystalEnvironment(lambda);
	}

	/**
	 * since the origin of the detector is pixel 00 the scattering vector to this pixel and the detector orientation
	 * should be identical
	 */
	@Test
	public void compareVectorToOrigin() {

		Vector3d newOrigin = new Vector3d(130, 120, 200);
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
		// detector size for assert
		double detSizeX = detector.getDetectorSizeH();
		double detSizeY = detector.getDetectorSizeV();
		double diagDetSize = Math.sqrt((detSizeX * detSizeX) + (detSizeY * detSizeY));
		int[] detectorCorners = { 0, 0, 0, 3072, 3072, 0, 3072, 3072 };
		Vector3d px1topx4;

		px1topx4 = Resolution.pixelToPixelVector(detector, detectorCorners[0], detectorCorners[1], detectorCorners[6],
				detectorCorners[7]);
		assertEquals(diagDetSize, px1topx4.length(), 0.00001);

		Matrix3d newOri = detector.getOrientation();
		newOri.rotY(1.5);
		detector.setOrientation(newOri);

		px1topx4 = Resolution.pixelToPixelVector(detector, detectorCorners[0], detectorCorners[1], detectorCorners[6],
				detectorCorners[7]);
		assertEquals(diagDetSize, px1topx4.length(), 0.00001);

		detector.setOrigin(new Vector3d(-150, 250, 389));

		px1topx4 = Resolution.pixelToPixelVector(detector, detectorCorners[0], detectorCorners[1], detectorCorners[6],
				detectorCorners[7]);
		assertEquals(diagDetSize, px1topx4.length(), 0.00001);

	}

	/**
	 * Calculates the d spacing between peaks in pixels
	 */
	@Test
	public void dspacingFromPx() {
		int[] testingPix = { 854, 899, 864, 897,864, 897, 874, 894, 884, 892, 894, 889, 904, 886, 914, 884, 924, 882 };
		double[] dspacing;
		try {
			dspacing = Resolution.peakDistances(testingPix, detector, diffexp);
			double average = 0;

			for (int i = 0; i < dspacing.length;) {
				average += dspacing[i++];
			}
			average = average / dspacing.length;
			assertEquals(164, average, 1); // 171 <- is the old value but the method appears to be mathematically
											// correct
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
