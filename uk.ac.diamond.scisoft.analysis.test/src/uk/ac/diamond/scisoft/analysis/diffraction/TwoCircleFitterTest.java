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

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.TwoCircleFitter.TwoCircleFitFunction;

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
				gamma[k] = i;
				delta[k] = j;
				k++;
			}
		}
		x = new double[n];
		y = new double[n];

	}

	@Test
	public void testFitFunction() {

		DetectorProperties prop = new DetectorProperties(100, 0, 0, 200, 400, 1, 1);
		DetectorProperties ndp = prop.clone();

		TwoCircleDetector dt = new TwoCircleDetector();

		/* 18-parameter fit function: beam pos (x,y,z), beam dir (t,p), gamma offset,
		 * delta pos, delta dir, delta offset,
		 * detector pos, detector normal, detector fast axis angle from horizontal
		 */
		double[] init = new double[] {0, 0, 0, 0, 0, -0.5,
				-1000, 0, 0, 90, 180, 0.5,
				1000, 0, 1000, 180-9, -90, 0};
		TwoCircleFitter.setupTwoCircle(dt, init);

		for (int i = 0; i < n; i++) {
			dt.getDetectorProperties(ndp, gamma[i], delta[i]);
			double[] bc = ndp.getBeamCentreCoords();
			x[i] = bc[0];
			y[i] = bc[1];
		}


		TwoCircleFitFunction f = new TwoCircleFitFunction(prop, dt, gamma, delta, x, y);
		assertEquals(0, f.value(init), 1e-14);

		for (int i = 0; i < n; i++) {
			dt.getDetectorProperties(ndp, gamma[i], delta[i]);
			double[] bc = ndp.getBeamCentreCoords();
			x[i] = bc[0] + 1;
			y[i] = bc[1] + 1;
		}
		f = new TwoCircleFitFunction(prop, dt, gamma, delta, x, y);
		assertEquals(2*n, f.value(init), 1e-14);
	}
}
