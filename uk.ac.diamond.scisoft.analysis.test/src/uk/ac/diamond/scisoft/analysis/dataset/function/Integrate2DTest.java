/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.CompoundDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.CompoundDoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.RGBDataset;
import org.junit.Test;

/**
 *
 */
public class Integrate2DTest {

	private void check1DArray(final Dataset a, final double[] d) {
		assertEquals(1, a.getRank());
		final int size = a.getSize();
		final int is = a.getElementsPerItem();
		assertEquals(d.length, is*size);

		if (is == 1) {
			for (int i = 0; i < size; i++) {
				assertEquals(d[i], a.getDouble(i), 1e-8);
			}
		} else {
			int n = 0;
			double[] ad = new double[is];
			for (int i = 0; i < size; i++) {
				((CompoundDataset) a).getDoubleArray(ad, i);
				for (int j = 0; j < is; j++) {
					assertEquals(d[n++], ad[j], 1e-8);
				}
			}
		}
	}

	/**
	 * 
	 */
	@Test
	public void testSimple() {
		double[] dd = {0., 1., 2., 3., 4., 5.};
		Dataset d = new DoubleDataset(dd).reshape(2,3);
		Integrate2D int2d = new Integrate2D();
		List<? extends Dataset> dsets = int2d.value(d);

		check1DArray(dsets.get(0), new double[] {3., 12.});
		check1DArray(d.sum(1), new double[] {3., 12.});

		check1DArray(dsets.get(1), new double[] {3., 5., 7.});
		check1DArray(d.sum(0), new double[] {3., 5., 7.});
	}


	/**
	 * 
	 */
	@Test
	public void testCompound() {
		double[] dd = {0., 1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11.};
		Dataset d = new CompoundDoubleDataset(2, dd, new int[] {2,3});
		Integrate2D int2d = new Integrate2D();
		List<? extends Dataset> dsets = int2d.value(d);

		check1DArray(dsets.get(0), new double[] {6., 9., 24., 27.});
		check1DArray(d.sum(1), new double[] {6., 9., 24., 27.});

		check1DArray(dsets.get(1), new double[] {6., 8., 10., 12., 14., 16.});
		check1DArray(d.sum(0), new double[] {6., 8., 10., 12., 14., 16.});
	}

	@Test
	public void testRGBDatasetIntegration() {
		
		// Create a 2-row, 3-column dataset, where each item in the dataset has 3 elements.
		RGBDataset ds = new RGBDataset(
			new int[] { 1,  2,  3,  4,  5,  6},
			new int[] {10, 11, 12, 13, 14, 15},
			new int[] {20, 21, 22, 23, 24, 25},
			2, 3);
		
		// The dataset looks like this:
		// 
		// red          green      blue
		//  1  2  3     10 11 12   20 21 22
		//  4  5  6     13 14 15   23 24 25
		
		assertEquals(6, ds.getSize());
		assertEquals(3, ds.getElementsPerItem());
		
		assertArrayEquals(new short[] { 1,  2,  3,  4,  5,  6}, ds.getRedView().getData());
		assertArrayEquals(new short[] {10, 11, 12, 13, 14, 15}, ds.getGreenView().getData());
		assertArrayEquals(new short[] {20, 21, 22, 23, 24, 25}, ds.getBlueView().getData());
		
		// Do the integration
		List<Dataset> calculated = new Integrate2D().value(ds);
		
		// We get back two datasets: sum of each row, which should have 2 items...
		RGBDataset rowSums = (RGBDataset) calculated.get(0);
		assertEquals(2, rowSums.getSize());
		assertEquals(3, rowSums.getElementsPerItem());
		
		// ...and sum of each column, which should have 3 items
		RGBDataset columnSums = (RGBDataset) calculated.get(1);
		assertEquals(3, columnSums.getSize());
		assertEquals(3, columnSums.getElementsPerItem());
		
		assertArrayEquals(new int[] {1+2+3, 10+11+12, 20+21+22}, rowSums.getIntArray(0));
		assertArrayEquals(new int[] {4+5+6, 13+14+15, 23+24+25}, rowSums.getIntArray(1));
		
		assertArrayEquals(new int[] {1+4, 10+13, 20+23}, columnSums.getIntArray(0));
		assertArrayEquals(new int[] {2+5, 11+14, 21+24}, columnSums.getIntArray(1));
		assertArrayEquals(new int[] {3+6, 12+15, 22+25}, columnSums.getIntArray(2));
	}

}
