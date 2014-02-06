/*-
 * Copyright 2011 Diamond Light Source Ltd. Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DoubleDatasetTest {

	@Test
	public void testConstructor() {
		double[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		DoubleDataset a = new DoubleDataset(da);

		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, a.getElementDoubleAbs(it.index), 1e-5 * i);
		}

		DoubleDataset b = new DoubleDataset(da, 3, 4);

		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, b.getElementDoubleAbs(it.index), 1e-5 * i);
		}

		DoubleDataset c = new DoubleDataset(a.getSliceView(new int[] {1}, null, new int[] {2}));
		it = c.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(2*i+1, c.getElementDoubleAbs(it.index), 1e-5 * i);
		}

		// test hashes
		a.hashCode();
		b.hashCode();
		c.hashCode();
	}

	@Test
	public void testGetter() {
		double[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		DoubleDataset a = new DoubleDataset(da);
		int l = da.length;
		for (int i = 0; i < l; i++) {
			assertEquals(i, a.getDouble(i), 1e-5 * i);
		}

		for (int i = 0; i < l; i++) {
			int r = l - 1 - i;
			assertEquals(r, a.getDouble(-(i + 1)), 1e-5 * r);
		}

		AbstractDataset sv = a.getSliceView(new Slice(2, 7));
		AbstractDataset sc = a.getSlice(new Slice(2, 7));
		l = sc.getSize();
		for (int i = 0; i < l; i++) {
			double r = sc.getDouble(-(i + 1));
			assertEquals(r, sv.getDouble(-(i + 1)), 1e-5 * r);
		}
	}

	@Test
	public void testCreators() {
		double dz = 0.5;
		DoubleDataset z = DoubleDataset.createFromObject(dz);
		assertEquals(0, z.getRank());
		assertEquals(1, z.getSize());
		assertEquals(dz, z.getElementDoubleAbs(0), 1e-14);

		double[] da = { 0, 1, 2, 3, 4, 5 };
		DoubleDataset a = DoubleDataset.createFromObject(da);
		assertEquals(1, a.getRank());
		assertEquals(6, a.getSize());
		assertEquals(6, a.getShape()[0]);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, a.getElementDoubleAbs(it.index), 1e-15 * i);
		}

		double[][] db = { { 0, 1, 2 }, { 3, 4, 5 } };
		DoubleDataset b = DoubleDataset.createFromObject(db);
		assertEquals(2, b.getRank());
		assertEquals(6, b.getSize());
		assertEquals(2, b.getShape()[0]);
		assertEquals(3, b.getShape()[1]);
		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, b.getElementDoubleAbs(it.index), 1e-15 * i);
		}

		double[][] dc = { { 0, 1, 2, 3 }, { 4, 5, 6 } };
		DoubleDataset c = DoubleDataset.createFromObject(dc);
		assertEquals(2, c.getRank());
		assertEquals(8, c.getSize());
		assertEquals(2, c.getShape()[0]);
		assertEquals(4, c.getShape()[1]);
		it = c.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			if (i < 7)
				assertEquals(i, c.getElementDoubleAbs(it.index), 1e-15 * i);
			else
				assertEquals(0, c.getElementDoubleAbs(it.index), 1e-15);
		}

		double[][] dd = { { 0, 1, 2 }, { 4, 5, 6, 7 } };
		DoubleDataset d = DoubleDataset.createFromObject(dd);
		assertEquals(2, d.getRank());
		assertEquals(8, d.getSize());
		assertEquals(2, d.getShape()[0]);
		assertEquals(4, d.getShape()[1]);
		it = d.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			if (i != 3)
				assertEquals(i, d.getElementDoubleAbs(it.index), 1e-15 * i);
			else
				assertEquals(0, d.getElementDoubleAbs(it.index), 1e-15);
		}
	}

	@Test
	public void testStats() {
		AbstractDataset a = AbstractDataset.arange(12, AbstractDataset.FLOAT64);
		assertEquals(11., a.max().doubleValue(), 1e-6);
		assertEquals(0., a.min().doubleValue(), 1e-6);
		assertEquals(5.5, ((Number) a.mean()).doubleValue(), 1e-6);
		assertEquals(3.6055512754639891, a.stdDeviation().doubleValue(), 1e-6);
		assertEquals(13., a.variance().doubleValue(), 1e-6);

		a.setShape(3, 1, 4);
		AbstractDataset b = a.sum(0);
		assertEquals(2, b.getRank());
		assertArrayEquals(new int[] { 1, 4 }, b.getShape());
		assertEquals(12., b.getDouble(0, 0), 1e-6);
		assertEquals(15., b.getDouble(0, 1), 1e-6);
		assertEquals(18., b.getDouble(0, 2), 1e-6);
		assertEquals(21., b.getDouble(0, 3), 1e-6);

		b = a.sum(1);
		assertEquals(2, b.getRank());
		assertArrayEquals(new int[] { 3, 4 }, b.getShape());
		assertEquals(a.squeeze(), b);

		a.setShape(3, 1, 4);
		b = a.sum(2);
		assertEquals(2, b.getRank());
		assertArrayEquals(new int[] { 3, 1 }, b.getShape());
		assertEquals(6., b.getDouble(0), 1e-6);
		assertEquals(22., b.getDouble(1), 1e-6);
		assertEquals(38., b.getDouble(2), 1e-6);
	}

	@Test
	public void testMaths() {
		double[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		DoubleDataset a = new DoubleDataset(da);

		AbstractDataset r = Maths.add(a, a);
		IndexIterator it = r.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(2. * i, r.getElementDoubleAbs(it.index), 1e-5 * i);
		}
	}

	@Test
	public void testPosition() {
		double[] da = { 0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1 };
		DoubleDataset a = new DoubleDataset(da);

		assertEquals(6, a.maxPos()[0]);
		assertEquals(0, a.minPos()[0]);

		AbstractDataset b = AbstractDataset.zeros(new int[] { 100, 200 }, AbstractDataset.FLOAT64);

		b.set(100.00, new int[] { 50, 100 });
		b.set(-100.00, new int[] { 51, 101 });

		assertEquals(50, b.maxPos()[0]);
		assertEquals(100, b.maxPos()[1]);
		assertEquals(51, b.minPos()[0]);
		assertEquals(101, b.minPos()[1]);

		b.set(Double.NaN, new int[] { 52, 53 });

		assertEquals(52, b.maxPos()[0]);
		assertEquals(53, b.maxPos()[1]);

		assertEquals(50, b.maxPos(true)[0]);
		assertEquals(100, b.maxPos(true)[1]);

		AbstractDataset c = AbstractDataset.zeros(new int[] { 100, 200 }, AbstractDataset.FLOAT64);
		c.set(100.00, new int[] { 99, 50 });
		c.set(99.99, new int[] { 50, 50 });
		assertEquals(99, c.maxPos()[0]);
		assertEquals(50, c.maxPos()[1]);

		c.set(101, new int[] { 0, 0 });
		assertEquals(0, c.maxPos()[0]);
		assertEquals(0, c.maxPos()[1]);
	}
}
