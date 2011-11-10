/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;


import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundDoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

/**
 *
 */
public class Integrate2DTest extends TestCase {

	private void check1DArray(final AbstractDataset a, final double[] d) {
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
				((AbstractCompoundDataset) a).getDoubleArray(ad, i);
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
		AbstractDataset d = new DoubleDataset(dd).reshape(2,3);
		Integrate2D int2d = new Integrate2D();
		List<AbstractDataset> dsets = int2d.value(d);

		check1DArray(dsets.get(0), new double[] {3., 12.});

		check1DArray(dsets.get(1), new double[] {3., 5., 7.});
	}


	/**
	 * 
	 */
	@Test
	public void testCompound() {
		double[] dd = {0., 1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11.};
		AbstractDataset d = new CompoundDoubleDataset(2, dd, new int[] {2,3});
		Integrate2D int2d = new Integrate2D();
		List<AbstractDataset> dsets = int2d.value(d);

		check1DArray(dsets.get(0), new double[] {6., 9., 24., 27.});

		check1DArray(dsets.get(1), new double[] {6., 8., 10., 12., 14., 16.});
	}

}
