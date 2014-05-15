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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;

public class IntegerDatasetTest {

	@Test
	public void testConstructor() {
		int[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		IntegerDataset a = new IntegerDataset(da, null);

		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, a.getElementLongAbs(it.index));
		}

		IntegerDataset b = new IntegerDataset(da, 3, 4);

		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, b.getElementLongAbs(it.index));
		}

		// test hashes
		a.hashCode();
		b.hashCode();
	}

	@Test
	public void testStats() {
		AbstractDataset a = AbstractDataset.arange(12, Dataset.INT32);
		assertEquals(11., a.max().doubleValue(), 1e-6);
		assertEquals(0., a.min().doubleValue(), 1e-6);
		assertEquals(5.5, ((Number) a.mean()).doubleValue(), 1e-6);
		assertEquals(3.6055512754639891, a.stdDeviation().doubleValue(), 1e-6);
		assertEquals(13., a.variance().doubleValue(), 1e-6);
	}
	
	@Test
	public void testPosition() {
		double[] da = { 0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1 };
		DoubleDataset a = new DoubleDataset(da);
		
		assertEquals(6,a.maxPos()[0]);
		assertEquals(0,a.minPos()[0]);
		
		AbstractDataset b = AbstractDataset.zeros(new int[]{100,200}, Dataset.INT32 );
		
		b.set(100, new int[]{50,100});
		b.set(-100, new int[]{51,101});
		
		assertEquals(50,b.maxPos()[0]);
		assertEquals(100,b.maxPos()[1]);
		assertEquals(51,b.minPos()[0]);
		assertEquals(101,b.minPos()[1]);
		
	}

}
