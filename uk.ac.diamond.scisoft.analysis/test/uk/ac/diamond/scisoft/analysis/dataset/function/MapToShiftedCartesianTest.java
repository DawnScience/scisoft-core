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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

/**
 *
 */
public class MapToShiftedCartesianTest extends TestCase {
	AbstractDataset d = AbstractDataset.zeros(new int[] {10, 20}, AbstractDataset.FLOAT64);

	/**
	 */
	@Override
	public void setUp() {
		d.fill(1.);
	}

	/**
	 * 
	 */
	@Test
	public void testMapToShiftedCartesian() {
		MapToShiftedCartesian mp = new MapToShiftedCartesian(5.3,7.6);
		AbstractDataset pd = mp.value(d).get(0);

		Sum s = new Sum();
		List<Number> dsets = s.value(pd);
		double answer = (10.-5.3)*(20. - 7.6);
		assertEquals(answer, dsets.get(0).doubleValue(), answer*1e-4); // within 0.01% accuracy
	}

}
