/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpansionTest {

	@Test
	public void testExpansion() {
		IntegerDataset d = new IntegerDataset(2,2);
		d.set(8920,2,0);
		d.set(8853,2,1);
		try {
			d.get(2,2);
			fail("access to uninitialised values allowed");
		} catch (ArrayIndexOutOfBoundsException e) {
			// expected
		}
		assertArrayEquals("shape incorrect", new int[] {3,2}, d.getShape());
		assertEquals("array length incorrect", 6, d.getData().length);
		assertArrayEquals("array incorrect", new int[] {0,0,0,0,8920,8853}, d.getData());
	}
}