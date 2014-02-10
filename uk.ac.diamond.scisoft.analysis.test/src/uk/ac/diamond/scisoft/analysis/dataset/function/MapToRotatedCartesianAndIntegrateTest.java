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

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;

/**
 *
 */
public class MapToRotatedCartesianAndIntegrateTest extends TestCase {
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
	public void testMapToRotatedCartesianAndIntegrate() {
		MapToRotatedCartesianAndIntegrate mp = new MapToRotatedCartesianAndIntegrate(100,70,50,30,45.);
		AbstractDataset pd = mp.value(d).get(0);

		double answer = 50.*30;
		assertEquals(answer, ((Number) pd.sum()).doubleValue(), answer*1e-4); // within 0.01% accuracy
	}

	/**
	 * 
	 */
	@Test
	public void testMapToRotatedCartesianAndIntegrateMasked() {
		MapToRotatedCartesianAndIntegrate mp = new MapToRotatedCartesianAndIntegrate(100,70,50,30,45.);
		AbstractDataset m = new BooleanDataset(d.getShape());
		m.fill(true);
		mp.setMask(m);
		AbstractDataset pd = mp.value(d).get(0);

		double answer = 50.*30;
		assertEquals(answer, ((Number) pd.sum()).doubleValue(), answer*1e-4); // within 0.01% accuracy

		m.fill(false);
		mp.setMask(m);
		pd = mp.value(d).get(0);

		assertEquals(0, ((Number) pd.sum()).doubleValue(), 1e-4); // within 0.01% accuracy

		m.fill(true);
		m.setSlice(false, new int[] {0, 108}, null, null);
		pd = mp.value(d).get(0);

		answer = 0.5*50.*30;
		assertEquals(answer, ((Number) pd.sum()).doubleValue(), answer*3e-2); // within 3% accuracy
	}
}
