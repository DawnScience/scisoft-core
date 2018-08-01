/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.eclipse.january.asserts.TestUtils.assertDatasetEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.january.dataset.CompoundDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Random;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class HistogramNDTest {

	DoubleDataset d = null;

	/**
	 */
	@Before
	public void setUp() {
		d = DatasetFactory.createRange(DoubleDataset.class, 1.0, 2048.0, 1.0);
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND() {
		HistogramND histo = new HistogramND(2048);
		Dataset pd = histo.value(d).get(0);

		assertEquals(2048, pd.getSize());
		assertEquals(1, pd.getInt(1));
		assertEquals(1, pd.getInt(512));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramNDWeights() {
		HistogramND histo = new HistogramND(2048);
		DoubleDataset w = DatasetFactory.zeros(d);
		w.fill(0.25);
		histo.setWeights(w);
		Dataset pd = histo.value(d).get(0);

		assertEquals(2048, pd.getSize());
		assertEquals(w.sum(true), pd.sum());
		assertEquals(0.25, pd.getDouble(1), 1e-8);
		assertEquals(0.25, pd.getDouble(512), 1e-8);
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramNDUnequalEdgeSpans() {
		HistogramND histo = new HistogramND(DatasetFactory.createFromObject(new double[] {0, 200, 2000}));
		Dataset pd = histo.value(d).get(0);

		assertEquals(2, pd.getSize());
		assertEquals(2000, pd.sum());
		assertEquals(199, pd.getInt(0));
		assertEquals(1801, pd.getInt(1));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND2() {
		HistogramND histo = new HistogramND(1024);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(2, pd.getInt(1));
		assertEquals(2, pd.getInt(512));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND3() {
		HistogramND histo = new HistogramND(256);
		Dataset pd = histo.value(d).get(0);

		assertEquals(256, pd.getSize());
		assertEquals(8, pd.getInt(1));
		assertEquals(8, pd.getInt(128));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND4() {
		HistogramND histo = new HistogramND(205);
		Dataset pd = histo.value(d).get(0);

		assertEquals(205, pd.getSize());
		assertEquals(10, pd.getInt(1));
		assertEquals(10, pd.getInt(128));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND5() {
		HistogramND histo = new HistogramND(1024);
		histo.setBinMinima(1.0);
		histo.setBinMaxima(1024.0);
		histo.setIgnoreOutliers(false);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(1, pd.getInt(1));
		assertEquals(1, pd.getInt(512));
		assertEquals(1024, pd.getInt(1023));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND6() {
		HistogramND histo = new HistogramND(1024);
		histo.setBinMinima(2.0);
		histo.setBinMaxima(1024.0);
		histo.setIgnoreOutliers(false);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(2, pd.getInt(0));
		assertEquals(1, pd.getInt(512));
		assertEquals(1024, pd.getInt(1023));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND7() {
		HistogramND histo = new HistogramND(1024);
		histo.setBinMinima(2.0);
		histo.setBinMaxima(1024.0);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(1, pd.getInt(0));
		assertEquals(1, pd.getInt(512));
		assertEquals(1, pd.getInt(1023));

		histo = new HistogramND(1024);
		histo.setBinMinima(0);
		histo.setBinMaxima(0);
		pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND8() {
		HistogramND histo = new HistogramND(50);
		Dataset pd = histo.value(DatasetFactory.createLinearSpace(IntegerDataset.class, 0, 100, 101)).get(0);

		assertEquals(50, pd.getSize());
		assertEquals(2, pd.getInt(0));
		assertEquals(2, pd.getInt(25));
		assertEquals(3, pd.getInt(49));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND9() {
		HistogramND histo = new HistogramND(DatasetFactory.createFromObject(new int[] {0, 1}));
		Dataset pd = histo.value(DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 2, 201)).get(0);
		assertEquals(1, pd.getSize());
		assertEquals(101, pd.getInt(0));

		histo = new HistogramND(DatasetFactory.createFromObject(new int[] {0, 1, 2}));
		pd = histo.value(DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 2, 201)).get(0);
		assertEquals(2, pd.getSize());
		assertEquals(100, pd.getInt(0));
		assertEquals(101, pd.getInt(1));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND10() {
		int bins = 1023;
		HistogramND histo = new HistogramND(bins);
		histo.setBinMinima(0);
		histo.setBinMaxima(100);

		double[] edgeValues = new double [] {0, Double.MIN_VALUE, Double.MIN_NORMAL, Math.nextDown(100.0), 100, Math.nextUp(100.0)};

		Dataset pd = histo.value(DatasetFactory.createFromObject(edgeValues)).get(0);

		assertEquals(bins, pd.getSize());
		assertEquals(3, pd.getInt(0));
		assertEquals(2, pd.getInt(bins - 1)); // == are counted at top
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND11() {
		int bins = 2048;
		double min = -0.9647109185159629;
		double max = 0.9989960485318626;
		HistogramND histo = new HistogramND(bins);
		histo.setBinMinima(min);
		histo.setBinMaxima(max);

		double[] edgeValues = new double [] {Math.nextDown(min), min, Math.nextUp(min),
				Math.nextDown(max), max, Math.nextUp(max), 0.9989960485318625};

		Dataset pd = histo.value(DatasetFactory.createFromObject(edgeValues)).get(0);

		assertEquals(bins, pd.getSize());
		assertEquals(2, pd.getInt(0));
		assertEquals(3, pd.getInt(bins - 1));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramND12() {
		int bins = 2048;
		double max = 0.9647109185159629;
		double min = -0.9989960485318626;
		HistogramND histo = new HistogramND(bins);
		histo.setBinMinima(min);
		histo.setBinMaxima(max);

		double[] edgeValues = new double [] {-0.9989960485318625, Math.nextDown(min), min, Math.nextUp(min),
				Math.nextDown(max), max, Math.nextUp(max)};

		Dataset pd = histo.value(DatasetFactory.createFromObject(edgeValues)).get(0);

		assertEquals(bins, pd.getSize());
		assertEquals(3, pd.getInt(0));
		assertEquals(2, pd.getInt(bins - 1));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramNDNoException() {
		int bins = 2048;
		double max = 0.9989960485318626;
		HistogramND h = new HistogramND(bins);
		h.setBinMinima(max);
		h.setBinMaxima(max);
		Dataset pd = h.value(d).get(0);
		assertEquals(bins, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));

		h = new HistogramND(bins);
		h.setBinMinima(Math.nextDown(max));
		h.setBinMaxima(max);
		pd = h.value(d).get(0);
		assertEquals(bins, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));
	}

	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testHistogramNDException() {
		int bins = 2048;
		double max = 0.9989960485318626;
		HistogramND h = new HistogramND(bins);
		h.setBinMinima(Math.nextUp(max));
		h.setBinMaxima(max);
		Dataset pd = h.value(d).get(0);
		assertEquals(bins, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramNDSpeed() {
		long start = 0;

		HistogramND h = new HistogramND(50);
		Dataset d = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 100, 500000);

		Dataset a  = null;
		a = h.value(d).get(0);

		start = -System.nanoTime();
		for (int i = 0; i < 4; i++) {
			h = new HistogramND(50);
			a = h.value(d).get(0);
		}
		start += System.nanoTime();

		System.out.printf("H = %s, %sms\n", a.sum().toString(), start*1e-6);
	}

	@Test
	public void testHistogram2D() {
		Random.seed(1238129l);
		DoubleDataset x = Random.randn(1024).imultiply(50);
		DoubleDataset y = Random.randn(1024);
		CompoundDataset xy = DatasetUtils.createCompoundDataset(x, y);

		HistogramND hnd = new HistogramND(32, 10);
		List<Dataset> r = hnd.value(xy);

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {32, 10}, h.getShapeRef());
		assertArrayEquals(new int[] {32 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {10 + 1}, q.getShapeRef());

		assertEquals(x.min(true).doubleValue(), p.getDouble(), 1e-8);
		assertEquals(x.max(true).doubleValue(), p.getDouble(-1), 1e-8);
		assertEquals(y.min(true).doubleValue(), q.getDouble(), 1e-8);
		assertEquals(y.max(true).doubleValue(), q.getDouble(-1), 1e-8);

		assertEquals(x.getSize(), h.sum(true));
		assertEquals(32 / 2, h.sum(1, true).argMax(true));
		assertEquals(10 / 2 - 1, h.sum(0, true).argMax(true));
	}

	@Test
	public void testHistogram2DEdges() {
		HistogramND hnd = new HistogramND(2);
		Dataset nx = DatasetFactory.createFromObject(new int[] {0, 1, 2, 1});
		Dataset ny = DatasetFactory.createFromObject(new int[] {2, 3, 1, 3});
		List<Dataset> r = hnd.value(DatasetUtils.createCompoundDataset(nx, ny));

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {2, 2}, h.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, q.getShapeRef());

		assertEquals(nx.min(true).doubleValue(), p.getDouble(), 1e-8);
		assertEquals(nx.max(true).doubleValue(), p.getDouble(-1), 1e-8);
		assertEquals(ny.min(true).doubleValue(), q.getDouble(), 1e-8);
		assertEquals(ny.max(true).doubleValue(), q.getDouble(-1), 1e-8);

		assertEquals(nx.getSize(), h.sum(true));
		assertDatasetEquals(DatasetFactory.createFromObject(new int[] {0, 1, 1, 2}).reshape(2, 2), h);
	}

	@Test
	public void testHistogram2DUnequalEdgeSpans() {
		HistogramND hnd = new HistogramND(2);
		hnd.setBinEdges(DatasetFactory.createFromObject(new double[] {0, 1, 3}),
				DatasetFactory.createFromObject(new double[] {1, 3, 4}));
		Dataset nx = DatasetFactory.createFromObject(new int[] {0, 1, 2, 1});
		Dataset ny = DatasetFactory.createFromObject(new int[] {2, 3, 1, 3});
		List<Dataset> r = hnd.value(DatasetUtils.createCompoundDataset(nx, ny));

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {2, 2}, h.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, q.getShapeRef());

		assertEquals(nx.getSize(), h.sum(true));
		assertDatasetEquals(DatasetFactory.createFromObject(new int[] {1, 0, 1, 2}).reshape(2, 2), h);
	}

	@Test
	public void testHistogram2DWeights() {
		Random.seed(1238129l);
		DoubleDataset x = Random.randn(1024).imultiply(50);
		DoubleDataset y = Random.randn(1024);
		CompoundDataset xy = DatasetUtils.createCompoundDataset(x, y);
		DoubleDataset w = DatasetFactory.zeros(x);
		w.fill(0.25);

		HistogramND hnd = new HistogramND(32, 10);
		hnd.setWeights(w);
		List<Dataset> r = hnd.value(xy);

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {32, 10}, h.getShapeRef());
		assertArrayEquals(new int[] {32 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {10 + 1}, q.getShapeRef());

		assertEquals(x.min(true).doubleValue(), p.getDouble(), 1e-8);
		assertEquals(x.max(true).doubleValue(), p.getDouble(-1), 1e-8);
		assertEquals(y.min(true).doubleValue(), q.getDouble(), 1e-8);
		assertEquals(y.max(true).doubleValue(), q.getDouble(-1), 1e-8);

		assertEquals(w.sum(true), h.sum(true));
		assertEquals(32 / 2, h.sum(1, true).argMax(true));
		assertEquals(10 / 2 - 1, h.sum(0, true).argMax(true));
	}
}
