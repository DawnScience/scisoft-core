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

package uk.ac.diamond.scisoft.analysis.dataset.function;


import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

/**
 *
 */
public class MapToRotatedCartesianTest extends TestCase {
	AbstractDataset d = AbstractDataset.zeros(new int[] {500, 500}, AbstractDataset.FLOAT64);

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
	public void testMapToRotatedCartesian() {
		MapToRotatedCartesian mp = new MapToRotatedCartesian(100,70,50,30,45.);
		AbstractDataset pd = mp.value(d).get(0);
		
		Sum s = new Sum();
		List<Number> dsets = s.value(pd);
		double answer = 50.*30;
		assertEquals(answer, dsets.get(0).doubleValue(), answer*1e-4); // within 0.01% accuracy
	}

}
