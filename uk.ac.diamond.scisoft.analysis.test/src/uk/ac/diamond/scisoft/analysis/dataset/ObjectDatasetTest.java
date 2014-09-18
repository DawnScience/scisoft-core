/*-
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

import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.ObjectDataset;
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

		ObjectDataset c = new ObjectDataset(a.getSliceView(new int[] {1}, null, new int[] {2}));
		it = c.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(2*i+1, Double.parseDouble(c.getStringAbs(it.index)), 1e-5 * i);
		}

		// test hashes
		a.hashCode();
		b.hashCode();
		c.hashCode();
	}
}
