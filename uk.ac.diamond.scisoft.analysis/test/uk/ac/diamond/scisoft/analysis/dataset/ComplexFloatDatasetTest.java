/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ComplexFloatDataset;

public class ComplexFloatDatasetTest {
	@Test
	public void testConstructor() {
		float[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		ComplexFloatDataset a = new ComplexFloatDataset(da);

		assertEquals(AbstractDataset.COMPLEX64, a.getDtype());
		assertEquals(2, a.getElementsPerItem());
		assertEquals(8, a.getItemsize());

		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i*2, a.getElementDoubleAbs(it.index), 1e-5*i);
		}

		ComplexFloatDataset b = new ComplexFloatDataset(da, 3, 2);

		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i*2, b.getElementDoubleAbs(it.index), 1e-5*i);
		}

		AbstractDataset aa = Maths.abs(a);
		assertEquals(AbstractDataset.FLOAT32, aa.getDtype());
		assertEquals(1, aa.getElementsPerItem());
		assertEquals(4, aa.getItemsize());		
	}

	@Test
	public void testTake() {
		AbstractDataset a = AbstractDataset.arange(12, AbstractDataset.COMPLEX64);
		AbstractDataset t;
		System.out.println(a);

		t = a.take(new int[] {0, 2, 4}, 0);
		System.out.println(t);

		a.setShape(new int[] {3,4});
		System.out.println(a);

		t = a.take(new int[] {0}, 0);
		System.out.println(t);

		t = a.take(new int[] {1}, 0);
		System.out.println(t);

		t = a.take(new int[] {2}, 0);
		System.out.println(t);

		t = a.take(new int[] {0}, 1);
		System.out.println(t);

		t = a.take(new int[] {1}, 1);
		System.out.println(t);

		t = a.take(new int[] {2}, 1);
		System.out.println(t);

		t = a.take(new int[] {3}, 1);
		System.out.println(t);

	}

}
