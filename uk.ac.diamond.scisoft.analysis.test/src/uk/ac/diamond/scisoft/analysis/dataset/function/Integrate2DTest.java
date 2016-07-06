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

import org.eclipse.january.dataset.CompoundDataset;
import org.eclipse.january.dataset.CompoundDoubleDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
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
		Dataset d = DatasetFactory.createFromObject(dd, 2, 3);
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
		Dataset d = DatasetFactory.createFromObject(2, CompoundDoubleDataset.class, dd, 2, 3);
		Integrate2D int2d = new Integrate2D();
		List<? extends Dataset> dsets = int2d.value(d);

		check1DArray(dsets.get(0), new double[] {6., 9., 24., 27.});
		check1DArray(d.sum(1), new double[] {6., 9., 24., 27.});

		check1DArray(dsets.get(1), new double[] {6., 8., 10., 12., 14., 16.});
		check1DArray(d.sum(0), new double[] {6., 8., 10., 12., 14., 16.});
	}

}
