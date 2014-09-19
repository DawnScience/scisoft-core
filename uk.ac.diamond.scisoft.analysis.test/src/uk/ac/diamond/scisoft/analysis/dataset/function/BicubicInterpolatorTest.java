/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Test;

public class BicubicInterpolatorTest {

	@Test
	public void testBicubicInterpolate() {
		double[][] p = new double[4][4];
		p[0][0] = 0.0; p[0][1] = 0.0; p[0][2] = 0.0; p[0][3] = 0.0;
		p[1][0] = 0.0; p[1][1] = 1.0; p[1][2] = 1.0; p[1][3] = 0.0;
		p[2][0] = 0.0; p[2][1] = 1.0; p[2][2] = 1.0; p[2][3] = 0.0;
		p[3][0] = 0.0; p[3][1] = 0.0; p[3][2] = 0.0; p[3][3] = 0.0;

		BicubicInterpolator bicube = new BicubicInterpolator(new int[] {1,2});

		bicube.calculateParameters(p);

		assertEquals(1.265, bicube.bicubicInterpolate(0.5, 0.5), 0.001);
	}

	@Test
	public void testGenerateSurroundingPoints() {

		DoubleDataset ds = DoubleDataset.createRange(0.0, 9.0, 1.0);
		ds = (DoubleDataset) ds.reshape(3, 3);
		int[] shape = ds.getShapeRef();

		BicubicInterpolator bicube = new BicubicInterpolator(new int[] { 20, 20 });
		double[][] val = bicube.generateSurroundingPoints(0, 0, ds, shape);

		assertEquals(0.0, val[0][0], 0.1);
		assertEquals(0.0, val[1][1], 0.1);
		assertEquals(4.0, val[2][2], 0.1);
		assertEquals(8.0, val[3][3], 0.1);

		val = bicube.generateSurroundingPoints(1, 1, ds, shape);

		assertEquals(0.0, val[0][0], 0.1);
		assertEquals(4.0, val[1][1], 0.1);
		assertEquals(8.0, val[2][2], 0.1);
		assertEquals(8.0, val[3][3], 0.1);
	}

	@Test
	public void testValue() {
		DoubleDataset ds = DoubleDataset.createRange(0.0, 9.0, 1.0);
		ds = (DoubleDataset) ds.reshape(3, 3);

		BicubicInterpolator bicube = new BicubicInterpolator(new int[] { 5, 5 });
		List<? extends Dataset> ds2 = bicube.value(ds);

		ds2.get(0).peakToPeak();
	}
}
