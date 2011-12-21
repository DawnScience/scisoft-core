/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
