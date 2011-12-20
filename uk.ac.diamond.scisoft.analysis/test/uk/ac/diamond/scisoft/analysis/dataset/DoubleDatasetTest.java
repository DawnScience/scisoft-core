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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

public class DoubleDatasetTest {

	@Test
	public void testConstructor() {
		double[] da = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		DoubleDataset a = new DoubleDataset(da);

		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, a.getElementDoubleAbs(it.index), 1e-5*i);
		}

		DoubleDataset b = new DoubleDataset(da, 3, 4);

		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, b.getElementDoubleAbs(it.index), 1e-5*i);
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
			assertEquals(i, a.getElementDoubleAbs(it.index), 1e-15*i);
		}

		double[][] db = { {0, 1, 2}, {3, 4, 5} };
		DoubleDataset b = DoubleDataset.createFromObject(db);
		assertEquals(2, b.getRank());
		assertEquals(6, b.getSize());
		assertEquals(2, b.getShape()[0]);
		assertEquals(3, b.getShape()[1]);
		it = b.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(i, b.getElementDoubleAbs(it.index), 1e-15*i);
		}

		double[][] dc = { {0, 1, 2, 3}, {4, 5, 6} };
		DoubleDataset c = DoubleDataset.createFromObject(dc);
		assertEquals(2, c.getRank());
		assertEquals(8, c.getSize());
		assertEquals(2, c.getShape()[0]);
		assertEquals(4, c.getShape()[1]);
		it = c.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			if (i < 7)
				assertEquals(i, c.getElementDoubleAbs(it.index), 1e-15*i);
			else
				assertEquals(0, c.getElementDoubleAbs(it.index), 1e-15);
		}

		double[][] dd = { {0, 1, 2}, {4, 5, 6, 7} };
		DoubleDataset d = DoubleDataset.createFromObject(dd);
		assertEquals(2, d.getRank());
		assertEquals(8, d.getSize());
		assertEquals(2, d.getShape()[0]);
		assertEquals(4, d.getShape()[1]);
		it = d.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			if (i != 3)
				assertEquals(i, d.getElementDoubleAbs(it.index), 1e-15*i);
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

		a.setShape(3,1,4);
		AbstractDataset b = a.sum(0);
		assertEquals(2, b.getRank());
		assertArrayEquals(new int[] {1, 4}, b.getShape());
		assertEquals(12., b.getDouble(0,0), 1e-6);
		assertEquals(15., b.getDouble(0,1), 1e-6);
		assertEquals(18., b.getDouble(0,2), 1e-6);
		assertEquals(21., b.getDouble(0,3), 1e-6);

		b = a.sum(1);
		assertEquals(2, b.getRank());
		assertArrayEquals(new int[] {3, 4}, b.getShape());
		assertEquals(a.squeeze(), b);

		a.setShape(3,1,4);
		b = a.sum(2);
		assertEquals(2, b.getRank());
		assertArrayEquals(new int[] {3, 1}, b.getShape());
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
			assertEquals(2.*i, r.getElementDoubleAbs(it.index), 1e-5*i);
		}
	}

	private final static int WARMUP = 3;
	private final static int REPEAT = 7;

	class TimeStats {
		double min, max, med;
		public TimeStats(List<Double> times) {
			Collections.sort(times);
			int len = times.size();
			min = times.get(0)*1e-6;
			max = times.get(len - 1)*1e-6;
			med = times.get(len/2)*1e-6;
		}

		@Override
		public String toString() {
			return String.format("(%.2f, %.2f, %.2f ms)", min, max, med);
		}

		public double getValue() {
			return min;
		}
	}

	@Test
	@Ignore("This test is for benchmarking only")
	public void testExtending() {
		List<Double> times = new ArrayList<Double>();

		int end = 100000;

		times.clear();
		for (int j = 0; j < REPEAT; j++) {
			final double time = timeList(100, end);
			times.add(time);
		}
		final TimeStats otime = new TimeStats(times);

		times.clear();
		for (int j = 0; j < REPEAT; j++) {
			final double time = timeDataset(100, end);
			times.add(time);
		}
		final TimeStats ntime = new TimeStats(times);

		System.out.printf("Time taken by extend for %s: %s; %s (%.1f%%)\n", end, otime, ntime, 100.*(otime.getValue() - ntime.getValue())/otime.getValue());
	}

	private double timeDataset(int n, int end) {
		for (int i = 0; i < WARMUP; i++) {
			extendDataset(end);
		}
		long start = -System.nanoTime();
		for (int i = 0; i < n; i++) {
			extendDataset(end);
		}
		start += System.nanoTime();

		return ((double) start)/n;
	}

	private void extendDataset(int end) {
		DoubleDataset result = DoubleDataset.arange(100);
		for (int i = 0; i < end; i++) {
			result.setItem((double) i, i);
		}
	}

	private double timeList(int n, int end) {
		for (int i = 0; i < WARMUP; i++) {
			extendList(end);
		}
		long start = -System.nanoTime();
		for (int i = 0; i < n; i++) {
			extendList(end);
		}
		start += System.nanoTime();

		return ((double) start)/n;
	}

	private void extendList(int end) {
		List<Double> result = new ArrayList<Double>(100);
		for (int i = 0; i < end; i++) {
			result.add((double) i);
		}
	}

}
