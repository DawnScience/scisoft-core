/*-
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

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Test;

public class BicubicInterpolatorTest {

	private static final double DELTA = 1e-12;

	@Test
	public void testBicubicInterpolate() {
		double[] p = new double[] {
			0, 0, 0, 0,
			0, 1, 1, 0,
			0, 1, 1, 0,
			0, 0, 0, 0,
		};

		BicubicInterpolator bicube = new BicubicInterpolator(1, 2);

		bicube.calculateParameters(p);

		assertEquals(1.265, bicube.bicubicInterpolate(0.5, 0.5), 0.001);
	}

	@Test
	public void testGenerateSurroundingPoints() {

		DoubleDataset ds = DatasetFactory.createRange(9);
		ds.setShape(3, 3);
		int[] shape = ds.getShapeRef();

		BicubicInterpolator bicube = new BicubicInterpolator(20, 20);
		double[] val = bicube.generateSurroundingPoints(0, 0, ds, shape);

		assertEquals(0.0, val[0*4+0], DELTA);
		assertEquals(0.0, val[1*4+1], DELTA);
		assertEquals(4.0, val[2*4+2], DELTA);
		assertEquals(8.0, val[3*4+3], DELTA);

		val = bicube.generateSurroundingPoints(1, 1, ds, shape);

		assertEquals(0.0, val[0*4+0], DELTA);
		assertEquals(4.0, val[1*4+1], DELTA);
		assertEquals(8.0, val[2*4+2], DELTA);
		assertEquals(8.0, val[3*4+3], DELTA);
	}

	@Test
	public void testValue() {
		DoubleDataset ds = DatasetFactory.createRange(9);
		ds.setShape(3, 3);

		BicubicInterpolator bicube = new BicubicInterpolator(5, 5);
		List<? extends Dataset> ds2 = bicube.value(ds);

		assertEquals(8.288, ds2.get(0).peakToPeak().doubleValue(), DELTA);
	}

	@Test
	public void testValues() {
		DoubleDataset ds = DatasetFactory.createRange(24);
		ds.setShape(6, 4);

		BicubicInterpolator bicube = new BicubicInterpolator(15, 10);
		Dataset ds2 = bicube.value(ds).get(0);

		assertEquals(0.0, ds2.getDouble(0,0), DELTA);
		assertEquals(1.64, ds2.getDouble(1,1), DELTA);
		assertEquals(3.92, ds2.getDouble(2,2), DELTA);

		assertEquals(2., ds2.getDouble(0,5), DELTA);
		assertEquals(2.448, ds2.getDouble(0,6), DELTA);
		assertEquals(2.864, ds2.getDouble(0,7), DELTA);
		assertEquals(3.064, ds2.getDouble(0,8), DELTA);
		assertEquals(3.048, ds2.getDouble(0,9), DELTA);

		assertEquals(8.0, ds2.getDouble(5,0), DELTA);
		assertEquals(16.0, ds2.getDouble(10,0), DELTA);
		assertEquals(17.792, ds2.getDouble(11,0), DELTA);
		assertEquals(19.456, ds2.getDouble(12,0), DELTA);

		assertEquals(1.64, bicube.interpolate(1, 1, ds), DELTA);
		assertEquals(3.064, bicube.interpolate(0, 8, ds), DELTA);
		assertEquals(16.0, bicube.interpolate(10, 0, ds), DELTA);
		assertEquals(17.792, bicube.interpolate(11, 0, ds), DELTA);

		bicube = new BicubicInterpolator(12, 8);
		ds2 = bicube.value(ds).get(0);
		assertEquals(ds2.getDouble(5,0), bicube.interpolate(5, 0, ds), DELTA);

		bicube = new BicubicInterpolator(18, 12);
		ds2 = bicube.value(ds).get(0);
		assertEquals(ds2.getDouble(5,0), bicube.interpolate(5, 0, ds), DELTA);
	}
}
