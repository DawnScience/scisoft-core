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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ObjectDatasetTest {

	@Test
	public void testConstructor() {
		Object[] da = { "0", (byte) 1, (short) 2, (int) 3, (float) 4, (double) 5, "6", "7", "8", "9", "10", "11" };
		ObjectDataset a = new ObjectDataset(da, null);

		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, Double.parseDouble(a.getStringAbs(it.index)), 1e-5*i);
		}

		ObjectDataset b = new ObjectDataset(da, 3, 4);

		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, Double.parseDouble(b.getStringAbs(it.index)), 1e-5*i);
		}
	}

}
