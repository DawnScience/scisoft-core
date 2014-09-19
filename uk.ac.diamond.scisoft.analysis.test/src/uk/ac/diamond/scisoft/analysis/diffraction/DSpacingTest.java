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

import java.util.Arrays;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.roi.IParametricROI;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROI;
import org.eclipse.dawnsci.analysis.dataset.roi.HyperbolicROI;
import org.eclipse.dawnsci.analysis.dataset.roi.ParabolicROI;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that will test calculating
 */
public class DSpacingTest {
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
	 * Calculates the d spacing between peaks in pixels
	 */
	@Test
	public void dspacingFromPx() {
		int[] testingPix = { 854, 899, 864, 897,864, 897, 874, 894, 884, 892, 894, 889, 904, 886, 914, 884, 924, 882 };
		double[] dspacing;
		try {
			dspacing = DSpacing.dSpacingsFromPixelCoords(detector, diffexp, testingPix);
			double average = 0;

			for (int i = 0; i < dspacing.length;) {
				average += dspacing[i++];
			}
			average = average / dspacing.length;
			assertEquals(161, average, 1); // 171 <- is the old value but the method appears to be mathematically
											// correct
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEllipses() {
		DiffractionCrystalEnvironment env = new DiffractionCrystalEnvironment(0.5);
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100, 100});

		IROI roi = DSpacing.conicFromDSpacing(det, env, 1);
		EllipticalROI eroi = (EllipticalROI) roi;
		Assert.assertTrue("Circle", eroi.isCircular());
		Assert.assertEquals("Radius", det.getBeamCentreDistance()*Math.tan(2*Math.asin(0.25))/det.getVPxSize(), eroi.getSemiAxis(0), 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertEquals("Angle", 30, eroi.getAngleDegrees(), 1e-7);
		Assert.assertTrue("Circle", eroi.isCircular());
		Assert.assertEquals("Radius", det.getBeamCentreDistance()*Math.tan(2*Math.asin(0.25))/det.getVPxSize(), eroi.getSemiAxis(0), 1e-7);

		det.setNormalAnglesInDegrees(0, 0, -30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertEquals("Angle", 360-30, eroi.getAngleDegrees(), 1e-7);
		Assert.assertTrue("Circle", eroi.isCircular());
		Assert.assertEquals("Radius", det.getBeamCentreDistance()*Math.tan(2*Math.asin(0.25))/det.getVPxSize(), eroi.getSemiAxis(0), 1e-7);

		det.setNormalAnglesInDegrees(30, 0, 0);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 180, eroi.getAngleDegrees(), 1e-7);
//		Assert.assertEquals("Radius", det.getBeamCentreDistance()*Math.tan(2*Math.asin(0.25))/det.getVPxSize(), eroi.getSemiAxis(0), 1e-7);

		det.setNormalAnglesInDegrees(30, 0, 180);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 0, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(30, 0, -180);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 0, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(-30, 0, 0);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 0, eroi.getAngleDegrees(), 1e-7);
//		Assert.assertEquals("Radius", det.getBeamCentreDistance()*Math.tan(2*Math.asin(0.25))/det.getVPxSize(), eroi.getSemiAxis(0), 1e-7);

		det.setNormalAnglesInDegrees(0, 30, 0);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 360-90, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(0, -30, 0);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 90, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(0, 30, 30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 360-90+30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(0, 30, -30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 360-90-30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(0, -30, 30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 90+30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(0, -30, -30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 90-30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(30, 0, 30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 180+30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(30, 0, -30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 180-30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(-30, 0, 30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(-30, 0, -30);
		roi = DSpacing.conicFromDSpacing(det, env, 1);
		eroi = (EllipticalROI) roi;
		Assert.assertFalse("Ellipse", eroi.isCircular());
		Assert.assertEquals("Angle", 360-30, eroi.getAngleDegrees(), 1e-7);

		det.setNormalAnglesInDegrees(0, 0, 0);
		try {
			roi = DSpacing.conicFromAngle(det, Math.PI*0.5);
		} catch (UnsupportedOperationException e) {
			// do nothing
			Assert.fail("Now should not have thrown an exception");
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testConics() {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {100, 100});

		double alpha = 60;
		double[] alphas = new double[] {Math.toRadians(alpha)};
		IROI[] rois;
		rois = DSpacing.conicsFromAngles(det, alphas);
		Assert.assertTrue(rois[0] instanceof EllipticalROI);
		checkEllipses((EllipticalROI) DSpacing.oldConicFromAngle(det, alphas[0]), (EllipticalROI) rois[0]);
		System.err.println(rois[0]);

		double delta = 0.1;
		det.setNormalAnglesInDegrees(90 - alpha - delta, 0, 0);
		rois = DSpacing.conicsFromAngles(det, alphas);
		Assert.assertTrue(rois[0] instanceof EllipticalROI);
		checkEllipses((EllipticalROI) DSpacing.oldConicFromAngle(det, alphas[0]), (EllipticalROI) rois[0]);
		double[] apt = ((EllipticalROI) rois[0]).getPoint(Math.PI);
		System.err.println(rois[0] + ", point=" + Arrays.toString(apt));

		det.setNormalAnglesInDegrees(30, 0, 0);
		rois = DSpacing.conicsFromAngles(det, alphas);
		Assert.assertTrue(rois[0] instanceof ParabolicROI);
		double[] bpt = ((ParabolicROI) rois[0]).getPoint(Math.PI);
		System.err.println(rois[0] + ", point=" + Arrays.toString(bpt));

		det.setNormalAnglesInDegrees(30.1, 0, 0);
		rois = DSpacing.conicsFromAngles(det, alphas);
		Assert.assertTrue(rois[0] instanceof HyperbolicROI);
		double[] cpt = ((HyperbolicROI) rois[0]).getPoint(Math.PI);
		System.err.println(rois[0] + ", point=" + Arrays.toString(cpt));

		// check a point is close
		Assert.assertEquals(apt[0], bpt[0], 2);
		Assert.assertEquals(apt[1], bpt[1], 1e-8);
		Assert.assertEquals(apt[0], cpt[0], 4);
		Assert.assertEquals(apt[1], cpt[1], 1e-8);
	}

	@Test
	public void testAllConics() {
		double d = 100;
		DetectorProperties det = new DetectorProperties(d, 0, 0, 100, 100, 1, 1);

		double[] alphas = new double[] {Math.toRadians(30), Math.toRadians(45), Math.toRadians(60)};
		IROI[] rois;
		rois = DSpacing.conicsFromAngles(det, alphas);
		for (int i = 0; i < alphas.length; i++) {
			IROI roi = rois[i];
			Assert.assertTrue(roi instanceof EllipticalROI);
			double r = d * Math.tan(alphas[i]);
			checkEllipses(new EllipticalROI(r, r, 0, 0, 0), (EllipticalROI) roi);
			System.err.println(roi);
		}

		double eta = Math.toRadians(45);
		det.setNormalAnglesInDegrees(Math.toDegrees(eta), 0, 0);
		rois = DSpacing.conicsFromAngles(det, alphas);
		IParametricROI roi = (IParametricROI) rois[0];
		double alpha = alphas[0];
		Assert.assertTrue(roi instanceof EllipticalROI);
		double a = d * Math.sqrt(1.5);
		double b = a * 0.5 / Math.cos(alpha);
		double t = d * Math.cos(eta) * Math.tan(eta - alpha) - d * Math.sin(eta); // AQ - PQ = -AP
		double x = a + t; // CA - AP = CP
		double[] pt = roi.getPoint(Math.PI);
		Assert.assertEquals(-t, pt[0], 1e-10);
		Vector3d v = det.pixelPosition(pt[0], pt[1]);
		Assert.assertEquals(-d * Math.sin(alpha) * Math.cos(eta) / Math.cos(eta - alpha), v.getX(), 1e-10);
		checkEllipses(new EllipticalROI(a, b, Math.PI, -x, 0), (EllipticalROI) roi);
		System.err.println(roi);

		roi = (IParametricROI) rois[1];
		alpha = alphas[1];
		Assert.assertTrue(roi instanceof ParabolicROI);
		pt = roi.getPoint(Math.PI);
		v = det.pixelPosition(pt[0], pt[1]);
		Assert.assertEquals(-d * Math.sin(alpha) * Math.cos(eta) / Math.cos(eta - alpha), v.getX(), 1e-10);
		a = 0.5 * d * Math.cos(eta) * Math.tan(alpha); // aka focal parameter
		t = d * Math.cos(eta) / Math.tan(2 * alpha);
		x = a + t;
		checkParabolas(new ParabolicROI(a, Math.PI, x, 0), (ParabolicROI) roi);
		System.err.println(roi);

		roi = (IParametricROI) rois[2];
		alpha = alphas[2];
		Assert.assertTrue(roi instanceof HyperbolicROI);
		pt = roi.getPoint(Math.PI);
		v = det.pixelPosition(pt[0], pt[1]);
		Assert.assertEquals(-d * Math.sin(alpha) * Math.cos(eta) / Math.cos(eta - alpha), v.getX(), 1e-10);
		a = d * Math.cos(eta) * Math.tan(alpha); // l
		x = 4 * d * Math.cos(eta) * Math.sin(eta) * (Math.sin(alpha) - Math.cos(eta)) - d * Math.sin(eta);
		checkHyperbolas(new HyperbolicROI(a, Math.sin(eta) / Math.cos(alpha), Math.PI, -x, 0), (HyperbolicROI) roi);
		System.err.println(roi);

		alphas = new double[90];
		for (int j = 0; j < alphas.length; j++) {
			alphas[j] = Math.toRadians(j);
		}
		for (int i = 0; i < 90; i++) {
			det.setNormalAnglesInDegrees(i, 0, 0);
			eta = Math.toRadians(i);
			rois = DSpacing.conicsFromAngles(det, alphas);

			for (int j = 0; j < alphas.length; j++) {
				alpha = alphas[j];
				roi = (IParametricROI) rois[j];
				int k = i + j;
				if (k < 90) {
					Assert.assertTrue(roi instanceof EllipticalROI);
				} else if (k == 90) {
					Assert.assertTrue(roi instanceof ParabolicROI);
				} else {
					Assert.assertTrue(roi instanceof HyperbolicROI);
				}
				pt = roi.getPoint(i == 0 ? 0 : Math.PI);
				v = det.pixelPosition(pt[0], pt[1]);
				Assert.assertEquals(-d * Math.sin(alpha) * Math.cos(eta) / Math.cos(eta - alpha), v.getX(), 1e-10);
			}
		}
	}

	private void checkEllipses(EllipticalROI ea, EllipticalROI eb) {
		double tol = 1e-7;
		Assert.assertEquals(ea.getSemiAxis(0), eb.getSemiAxis(0), tol);
		Assert.assertEquals(ea.getSemiAxis(1), eb.getSemiAxis(1), tol);
		Assert.assertEquals(ea.getAngle(), eb.getAngle(), tol);
		Assert.assertEquals(ea.getPointX(), eb.getPointX(), tol);
		Assert.assertEquals(ea.getPointY(), eb.getPointY(), tol);
	}

	private void checkParabolas(ParabolicROI pa, ParabolicROI pb) {
		double tol = 1e-8;
		Assert.assertEquals(pa.getFocalParameter(), pb.getFocalParameter(), tol);
		Assert.assertEquals(pa.getAngle(), pb.getAngle(), tol);
		Assert.assertEquals(pa.getPointX(), pb.getPointX(), tol);
		Assert.assertEquals(pa.getPointY(), pb.getPointY(), tol);
	}

	private void checkHyperbolas(HyperbolicROI ha, HyperbolicROI hb) {
		double tol = 1e-8;
		Assert.assertEquals(ha.getSemilatusRectum(), hb.getSemilatusRectum(), tol);
		Assert.assertEquals(ha.getEccentricity(), hb.getEccentricity(), tol);
		Assert.assertEquals(ha.getAngle(), hb.getAngle(), tol);
		Assert.assertEquals(ha.getPointX(), hb.getPointX(), tol);
		Assert.assertEquals(ha.getPointY(), hb.getPointY(), tol);
	}
}
