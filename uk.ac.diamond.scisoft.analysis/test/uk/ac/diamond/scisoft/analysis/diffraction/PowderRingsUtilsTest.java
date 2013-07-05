/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import gda.analysis.io.ScanFileHolderException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.vecmath.Vector3d;

import org.jscience.physics.amount.Amount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.crystallography.HKL;
import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.PowderRingsUtils.FitFunction;
import uk.ac.diamond.scisoft.analysis.io.ADSCImageLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver;
import uk.ac.diamond.scisoft.analysis.roi.CircularFitROI;
import uk.ac.diamond.scisoft.analysis.roi.CircularROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalFitROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.PolylineROI;

public class PowderRingsUtilsTest {
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
	}

	// NIST Silicon SRM 640C as mentioned in IUCR International Tables vC 5.2.10
	static final double WAVELENGTH = 1.5405929; // in nm
	static final double LATTICE_PARAMETER = 0.5431195;  // in nm
	static final double[] CONE_ANGLES = {28.411, 47.300, 56.120};
//	static final double[] CONE_ANGLES = {28.411, 47.300, 56.120, 69.126, 76.372, 88.025,
//		94.947, 106.701, 114.084, 127.534, 136.880, 158.603}; // in degrees
	UnitCell siliconCell;
	MillerSpace mSpace;
	private ArrayList<HKL> spacings;

	@Before
	public void setUpSilicon() {
		siliconCell = new UnitCell(LATTICE_PARAMETER);
		spacings = new ArrayList<HKL>();
		for (double c : CONE_ANGLES) {
			spacings.add(new HKL(Amount.valueOf(0.5 * WAVELENGTH / Math.sin(0.5 * Math.toRadians(c)), NonSI.ANGSTROM)));
		}

		PowderRingsUtils.seed = 1237L; // set seed for evolution strategy fitting
//		mSpace = new MillerSpace(siliconCell, null);
	}

	@Test
	public void findEllipse() {
		AbstractDataset image = null;
		try {
			image = new ADSCImageLoader(TestFileFolder + "ADSCImageTest/Si_200_1_0001.img").loadFile().getDataset(0);
		} catch (Exception e) {
			Assert.fail("Could not open image");
		}

		CircularROI roi = new CircularROI(631, 1528.6, 1533.9);
		PolylineROI points = PowderRingsUtils.findPOIsNearCircle(null, image, null, roi);
		System.err.println(points);
		System.err.println(new CircularFitROI(points));
		System.err.println(new EllipticalFitROI(points));

		System.err.println(PowderRingsUtils.fitAndTrimOutliers(null, points, true));
		System.err.println(PowderRingsUtils.fitAndTrimOutliers(null, points, false));
	}

	private static final int N_W = 128;
	private static final int N_D = 128;
	private static final int N_T = 64;
	private static final int N_TD = 32;

	@Ignore
	@Test
	public void createRMSDatasets() {
		double pixel = 0.25; // in mm
		double distance = 153.0; // in mm
		DetectorProperties det = new DetectorProperties(new Vector3d(0, 0, distance), 10, 10, pixel, pixel, 0, 0, 0);

		double wavelength = WAVELENGTH * 1e-7; // in mm
	
		List<EllipticalROI> ells = new ArrayList<EllipticalROI>();
		List<Double> list = new ArrayList<Double>();
		for (HKL d : spacings) {
			double s = d.getD().doubleValue(SI.MILLIMETRE);
			IROI r = DSpacing.conicFromDSpacing(det, wavelength, s);
			if (r instanceof EllipticalROI) {
				ells.add((EllipticalROI) r);
				list.add(s);
			}
//			double xi = 0.5 * WAVELENGTH / s;
//			double om = 0.5 - xi*xi;
//			double r = distance * xi * Math.sqrt(0.5 + om) / (pixel * om);
//			ells.add(new EllipticalROI(r, 0, 0));
		}

		save("/tmp/rms4.npy", calcFit4Values(det, wavelength, distance, list, ells));
		save("/tmp/rms5.npy", calcFit5Values(det, wavelength, distance, list, ells));
	}

	private DoubleDataset calcFit4Values(DetectorProperties det, double wavelength, double distance, List<Double> list, List<EllipticalROI> ells) {
		FitFunction f = PowderRingsUtils.createQFitFunction4(ells, det, wavelength, false);
		f.setSpacings(list);
		double[] init = new double[7];
		DoubleDataset rms = new DoubleDataset(N_W, N_D, N_D, N_TD);
		int t = 0;
		init[2] = 0;
		init[5] = 0;
		init[6] = 0;
		for (int i = 0; i < N_W; i++) {
			init[0] = wavelength + (i - N_W*0.5)*1e-7*0.01; // 0.01A
			for (int j = 0; j < N_D; j++) {
				init[1] = (j - N_D*0.5)*0.01; // 0.01mm
				for (int l = 0; l < N_D; l++) {
					init[3] = distance + (l - N_D * 0.5) * 0.01; // 0.01mm
					for (int m = 0; m < N_TD; m++) {
						init[4] = (m - N_TD * 0.5) / 20; // 1/20 degrees
						double x = f.value(init);
						rms.setAbs(t++, x);
					}
				}
			}
		}
		return rms;
	}

	private DoubleDataset calcFit5Values(DetectorProperties det, double wavelength, double distance, List<Double> list, List<EllipticalROI> ells) {
		FitFunction f = PowderRingsUtils.createQFitFunction5(ells, det, wavelength, false);
		f.setSpacings(list);
		double[] init = new double[4];
		DoubleDataset rms = new DoubleDataset(N_W, N_D, N_D);
		int t = 0;
		init[1] = 0;
		for (int i = 0; i < N_W; i++) {
			init[0] = wavelength + (i - N_W*0.5)*1e-7*0.01; // 0.01A
			for (int k = 0; k < N_D; k++) {
				init[2] = (k - N_D * 0.5) * 0.01; // 0.01mm
				for (int l = 0; l < N_D; l++) {
					init[3] = distance + (l - N_D * 0.5) * 0.01; // 0.01mm
					double x = f.value(init);
					rms.setAbs(t++, x);
				}
			}
		}
		return rms;
	}

	private void save(String file, DoubleDataset rms) {
		DataHolder dh = new DataHolder();
		dh.addDataset("RMS", rms);

		try {
			new NumPyFileSaver(file).saveFile(dh);
		} catch (ScanFileHolderException e) {
			System.err.println("Problem saving file: " + file);
		}
	}

	@Test
	public void calibrateDetector() {
		double pixel = 0.25; // in mm
		double distance = 153.0; // in mm
		DetectorProperties det = new DetectorProperties(new Vector3d(0, 0, distance*0.99), 3000, 3000, pixel, pixel, null);

		DiffractionCrystalEnvironment env = new DiffractionCrystalEnvironment(WAVELENGTH*1.01);

		Random rnd = new Random(12345);
		for (int i = 0; i < 15; i+= 5) {
			System.err.println("Angle " + i);
			det.setNormalAnglesInDegrees(i, 0, 0);
			List<EllipticalROI> ells = new ArrayList<EllipticalROI>();
			for (HKL d : spacings) {
				EllipticalROI e = (EllipticalROI) DSpacing.conicFromDSpacing(det, WAVELENGTH,
						d.getD().doubleValue(NonSI.ANGSTROM));
				double r = e.getSemiAxis(0) + rnd.nextDouble() * 3;
				if (e.isCircular()) {
					e.setSemiAxis(0, r);
					e.setSemiAxis(1, r);
				} else {
					e.setSemiAxis(0, r);
					e.setSemiAxis(1, e.getSemiAxis(0) + rnd.nextDouble() * 3);
				}
				ells.add(e);
			}

			QSpace q;
			DetectorProperties nDet;
//			q = PowderRingsUtils.fitEllipsesToQSpace(null, det, env, ells, spacings, true);
//			nDet = q.getDetectorProperties();
//
//			Assert.assertEquals("Distance", distance, nDet.getDetectorDistance(), 5*(i+1));
//			Assert.assertEquals("Tilt", det.getTiltAngle(), nDet.getTiltAngle(), 6e-2*(i+1));
//			Assert.assertEquals("Wavelength", WAVELENGTH, q.getWavelength(), 2e-2);

			q = PowderRingsUtils.fitAllEllipsesToQSpace(null, det, env, ells, spacings, false);
			nDet = q.getDetectorProperties();

			Assert.assertEquals("Distance", distance, nDet.getDetectorDistance(), 5*(i+1));
			Assert.assertEquals("Tilt", det.getTiltAngle(), nDet.getTiltAngle(), 6e-2*(i+1));
			Assert.assertEquals("Wavelength", WAVELENGTH, q.getWavelength(), 3e-2);
			System.err.println("D = " + nDet.getDetectorDistance() + "; ta = " + Math.toDegrees(nDet.getTiltAngle()) + "; wl = " + q.getWavelength());
		}
	}

	// TODO write test for tilted rings
	@Test
	public void testTiltedRings() {
// init:		[9.794999999999996E-8, 186.51868837271343, 121.46464172000353, 150.65107569010982, 0.2, 0.0, 11.99999999999816]
		DetectorProperties dp = new DetectorProperties();
		dp.setOrigin(new Vector3d(186.51868837271343, 121.46464172000353, 150.65107569010982));
		dp.setHPxSize(0.103);
		dp.setVPxSize(0.103);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(9.794999999999996E-8);

		
		//		[1525.866292368052, 1535.3190810845122] Semi-axes [475.0046320040548, 474.8853872774939] Angle 22.3292 
//		[1524.5669639423957, 1536.8213139272307] Semi-axes [830.5962918636154, 830.3877362787756] Angle 4.79202 
//		[1523.524352510107, 1538.0700246613837] Semi-axes [1018.2738171527302, 1018.1290503505653] Angle 323.464 
//		[1521.6263675530188, 1540.6108471150792] Semi-axes [1332.4590170670806, 1332.1025894139686] Angle 60.4132 
//		[1520.3632139909628, 1542.7081427044725] Semi-axes [1532.4427340516588, 1532.1822066227478] Angle 15.8618 
//		[1517.0665240854303, 1547.1493099055606] Semi-axes [1905.2796564064454, 1904.4857261909885] Angle 10.5470
//		EllipticalROI e = new EllipticalROI(maj, min, ang, px, py);
	}
	
	@Test
	public void testFitFunction() {
		double wavelength = WAVELENGTH * 1e-7; // in mm
	
		List<EllipticalROI> ells = new ArrayList<EllipticalROI>();
		List<Double> list = new ArrayList<Double>();

		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(300, 300);
		det.setNormalAnglesInDegrees(21, 0, 30);
		for (HKL d : spacings) {
			double dspacing = d.getD().doubleValue(SI.MILLIMETRE); 
			list.add(dspacing);
			ells.add((EllipticalROI) DSpacing.conicFromDSpacing(det, wavelength, dspacing));
		}

		FitFunction f;
		double[] init;

		// test functions

		det.setNormalAnglesInDegrees(0, 0, 0);
		ells.clear();
		list.clear();
		for (HKL d : spacings) {
			double dspacing = d.getD().doubleValue(SI.MILLIMETRE); 
			list.add(dspacing);
			ells.add((EllipticalROI) DSpacing.conicFromDSpacing(det, wavelength, dspacing));
		}

		f = PowderRingsUtils.createQFitFunction4(ells, det, wavelength, false);
		init = f.getInit();
		f.setSpacings(list);
		Assert.assertEquals("", 0, f.value(init), 1e-2);

		f = PowderRingsUtils.createQFitFunction4(ells, det, wavelength, true);
		init = f.getInit();
		f.setSpacings(list);
		Assert.assertEquals("", 0, f.value(init), 1e-2);

		f = PowderRingsUtils.createQFitFunction5(ells, det, wavelength, false);
		init = f.getInit();
		f.setSpacings(list);
		Assert.assertEquals("", 0, f.value(init), 1e-2);

		f = PowderRingsUtils.createQFitFunction5(ells, det, wavelength, true);
		init = f.getInit();
		f.setSpacings(list);
		Assert.assertEquals("", 0, f.value(init), 1e-2);
	}
}
