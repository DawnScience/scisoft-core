/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntegerIteratorTest {
	AbstractDataset a, b;

	@Before
	public void setUpClass() {
		a = new DoubleDataset(new double[] { 0, 1, 3, 5, -7, -9 });
		b = new DoubleDataset(new double[] { 0.01, 1.2, 2.9, 5, -7.1, -9 });
	}

	@Test
	public void testEqualTo() {
		AbstractDataset c = a.clone().reshape(2, 3);
		IntegerDataset s = new IntegerDataset(new int[] {0, 3, 4}, null);

		List<Integer> inds = new ArrayList<Integer>();

		IntegerIterator iter = new IntegerIterator(s, c.size);
		while (iter.hasNext())
			inds.add((int) c.getElementLongAbs(iter.index));

		checkDatasets((IntegerDataset) AbstractDataset.createFromList(inds),
				new IntegerDataset(new int[] {0,5,-7}, null));
	}

	public void checkDatasets(IntegerDataset calc, IntegerDataset expected) {
		IndexIterator at = calc.getIterator(true);
		IndexIterator bt = expected.getIterator();
		final int is = calc.getElementsPerItem();

		while (at.hasNext() && bt.hasNext()) {
			for (int j = 0; j < is; j++) {
				Assert.assertEquals("Value does not match at " + Arrays.toString(at.getPos()) + "; " + j +
						": ", expected.getAbs(at.index + j), calc.getAbs(bt.index + j));
			}
		}
	}
}
