/*-
 * Copyright 2022 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Test;

public class CubicSplineTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testCubicSpline() {
		double[] x = {0, 1, 2, 3};
		double[] y = {0.13863122, 1.53725674, 0.79807632, 4.18036446};

		CubicSpline cs = new CubicSpline(x, y);

		double[] nx = {0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5};
		double[] ny = {0.13863122, 1.15476129, 1.53725674, 1.01889183,
				0.79807632, 2.02362839, 4.18036446, 6.33710054};

		DoubleDataset result = cs.calculateValues(DatasetFactory.createFromObject(nx));
		TestUtils.assertDatasetEquals(DatasetFactory.createFromObject(ny), result, ABS_TOL, ABS_TOL);
	}
}
