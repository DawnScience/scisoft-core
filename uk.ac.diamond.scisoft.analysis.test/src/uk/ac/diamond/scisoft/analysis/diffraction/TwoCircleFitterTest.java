/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.MatrixUtils;
import org.junit.Before;
import org.junit.Test;

public class TwoCircleFitterTest {
	private int n;
	private double[] gamma;
	private double[] delta;
	private double[] x;
	private double[] y;

	@Before
	public void setUp() {
		int ng = 4;
		int nd = 3;
		n = ng * nd;
		gamma = new double[n];
		delta = new double[n];
		int k = 0;
		for (int i = 0; i < ng; i++) {
			for (int j = 0; j < nd; j++) {
				gamma[k] = i*5 - 2;
				delta[k] = j*5 - 2;
				k++;
			}
		}
		x = new double[n];
		y = new double[n];

		FittingUtils.seed = 12375L;
	}

	@Test
	public void testFitFunction() {

		DetectorProperties prop = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);
		DetectorProperties ndp = prop.clone();

		TwoCircleDetector dt = new TwoCircleDetector();

		/* 6-parameter fit function:
		 * detector pos, detector normal, detector fast axis angle from meridian
		 */
		double[] init = new double[] {0, 0, 1000, 180-9, -90, 0};
		TwoCircleDetector.setupTwoCircle(dt, init);

		// check for minima in many dimensions
		for (int np : new int[] {8, 10, 18}) {
			for (int i = 0; i < n; i++) {
				dt.updateDetectorProperties(ndp, gamma[i], delta[i]);
				double[] bc = ndp.getBeamCentreCoords();
				x[i] = bc[0];
				y[i] = bc[1];
			}

			TwoCircleFitter.TwoCircleFitFunction f = new TwoCircleFitter.TwoCircleFitFunction(prop, dt, np, gamma, delta, x, y);
			assertEquals(0, f.value(init), 1e-14);
	
			for (int i = 0; i < n; i++) {
				dt.updateDetectorProperties(ndp, gamma[i], delta[i]);
				double[] bc = ndp.getBeamCentreCoords();
				x[i] = bc[0] + 1;
				y[i] = bc[1] + 1;
			}
			f = new TwoCircleFitter.TwoCircleFitFunction(prop, dt, np, gamma, delta, x, y);
			assertEquals(2*n, f.value(init), 1e-14);
		}
	}

	private static double R_TOL = 1e-4;
	private static double A_TOL = 1e-8;

	@Test
	public void testFitter() {
		DetectorProperties prop = new DetectorProperties(100, 0, 0, 195, 487, 0.172, 0.172);
		DetectorProperties ndp = prop.clone();

		TwoCircleDetector dt = new TwoCircleDetector();
		/* 6-parameter fit function:
		 * detector pos, detector normal, detector fast axis angle from meridian
		 */
		double[] init = new double[] {
			0, 0, 535, 180-35, 0, 90};
		TwoCircleDetector.setupTwoCircle(dt, init);

		Random rnd = new Random(123457L);

		for (int np : new int[] {8, 10}) {
			for (int i = 0; i < n; i++) {
				dt.updateDetectorProperties(ndp, gamma[i], delta[i]);
				double[] bc = ndp.getBeamCentreCoords();
				x[i] = bc[0] + 0.007*rnd.nextGaussian();
				y[i] = bc[1] + 0.007*rnd.nextGaussian();
			}

			TwoCircleDetector fdt = TwoCircleFitter.fitDetector(prop, dt, np, gamma, delta, x, y);
			System.err.println(dt);
			System.err.println(fdt);
			assertTrue(dt.isClose(fdt, R_TOL, A_TOL));
	
			// now fix points and move detector
			checkDetectorFitter(fdt, np, prop, ndp,
					0, 0, 535, 180 - 35 + 1. / 32, 0, 90);
	
			checkDetectorFitter(fdt, np, prop, ndp,
					0, 0, 535, 180 - 35, 0, 90);
		}
	}

	private void checkDetectorFitter(TwoCircleDetector dt, int np, DetectorProperties prop, DetectorProperties ndp,
			double... p) {
		TwoCircleDetector.setupTwoCircle(dt, p);
		int n = gamma.length;
		for (int i = 0; i < n; i++) {
			dt.updateDetectorProperties(ndp, gamma[i], delta[i]);
			double[] bc = ndp.getBeamCentreCoords();
			x[i] = bc[0];
			y[i] = bc[1];
		}

		TwoCircleDetector fdt = TwoCircleFitter.fitDetector(prop, dt, np, gamma, delta, x, y);
		System.err.println(dt);
		System.err.println(fdt);
		assertTrue(dt.isClose(fdt, R_TOL, A_TOL));
	}

	@Test
	public void testI16() {
		TwoCircleDetector d = TwoCircleFitter.createI16Detector();
		DetectorProperties prop = new DetectorProperties(100, 0, 0, 195, 487, 0.172, 0.172);
		d.updateDetectorProperties(prop, 0, 0);
		Vector3d v = new Vector3d(0, -1, -1);
		v.normalize();
		assertTrue(MatrixUtils.isClose(v, prop.getNormal(), R_TOL, A_TOL));
	}
}
