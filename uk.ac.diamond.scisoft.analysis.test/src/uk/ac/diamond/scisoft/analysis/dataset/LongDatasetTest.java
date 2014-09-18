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

import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.LongDataset;
import org.junit.Test;

public class LongDatasetTest {

	@Test
	public void testConstructor() {
		long[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		LongDataset a = new LongDataset(da);

		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, a.getElementLongAbs(it.index));
		}

		LongDataset b = new LongDataset(da, 3, 4);

		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, b.getElementLongAbs(it.index));
		}

		// test hashes
		a.hashCode();
		b.hashCode();
	}

	@Test
	public void testGetter() {
		long[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		LongDataset a = new LongDataset(da);
		int l = da.length;
		for (int i = 0; i < l; i++) {
			assertEquals(i, a.getLong(i));
		}
		
		for (int i = 0; i < l; i++) {
			int r = l - 1 - i;
			assertEquals(r, a.getLong(-(i + 1)));
		}

		Dataset sv = a.getSliceView(new Slice(2,7));
		Dataset sc = a.getSlice(new Slice(2,7));
		l = sc.getSize();
		for (int i = 0; i < l; i++) {
			long r = sc.getLong(-(i + 1));
			assertEquals(r, sv.getLong(-(i + 1)));
		}
	}

	@Test
	public void testStats() {
		Dataset a = DatasetFactory.createRange(12, Dataset.INT64);
		assertEquals(11., a.max().doubleValue(), 1e-6);
		assertEquals(0., a.min().doubleValue(), 1e-6);
		assertEquals(5.5, ((Number) a.mean()).doubleValue(), 1e-6);
		assertEquals(3.6055512754639891, a.stdDeviation().doubleValue(), 1e-6);
		assertEquals(13., a.variance().doubleValue(), 1e-6);
	}

}
