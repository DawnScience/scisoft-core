/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.assertEquals;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class HistogramTest {

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
	public void testHistogram() {
		Histogram histo = new Histogram(2048);
		Dataset pd = histo.value(d).get(0);

		assertEquals(2048, pd.getSize());
		assertEquals(d.getSize(), pd.sum());
		assertEquals(1, pd.getInt(1));
		assertEquals(1, pd.getInt(512));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramWeights() {
		Histogram histo = new Histogram(2048);
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
	public void testHistogramUnequalEdgeSpans() {
		Histogram histo = new Histogram(DatasetFactory.createFromObject(new double[] {0, 200, 2000}));
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
	public void testHistogram2() {
		Histogram histo = new Histogram(1024);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(2, pd.getInt(1));
		assertEquals(2, pd.getInt(512));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram3() {
		Histogram histo = new Histogram(256);
		Dataset pd = histo.value(d).get(0);

		assertEquals(256, pd.getSize());
		assertEquals(8, pd.getInt(1));
		assertEquals(8, pd.getInt(128));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram4() {
		Histogram histo = new Histogram(205);
		Dataset pd = histo.value(d).get(0);

		assertEquals(205, pd.getSize());
		assertEquals(10, pd.getInt(1));
		assertEquals(10, pd.getInt(128));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram5() {
		Histogram histo = new Histogram(1024, 1.0, 1024.0);
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
	public void testHistogram6() {
		Histogram histo = new Histogram(1024, 2.0, 1024.0);
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
	public void testHistogram7() {
		Histogram histo = new Histogram(1024, 2.0, 1024.0, true);
		Dataset pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(1, pd.getInt(0));
		assertEquals(1, pd.getInt(512));
		assertEquals(1, pd.getInt(1023));

		histo = new Histogram(1024, 0, 0, true);
		pd = histo.value(d).get(0);

		assertEquals(1024, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram8() {
		Histogram histo = new Histogram(50);
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
	public void testHistogram9() {
		Histogram histo = new Histogram(DatasetFactory.createFromObject(new int[] {0, 1}));
		Dataset pd = histo.value(DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 2, 201)).get(0);
		assertEquals(1, pd.getSize());
		assertEquals(101, pd.getInt(0));

		histo = new Histogram(DatasetFactory.createFromObject(new int[] {0, 1, 2}));
		pd = histo.value(DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 2, 201)).get(0);
		assertEquals(2, pd.getSize());
		assertEquals(100, pd.getInt(0));
		assertEquals(101, pd.getInt(1));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogram10() {
		int bins = 1023;
		Histogram histo = new Histogram(bins, 0, 100.0, true);

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
	public void testHistogram11() {
		int bins = 2048;
		double min = -0.9647109185159629;
		double max = 0.9989960485318626;
		Histogram histo = new Histogram(bins, min, max, true);

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
	public void testHistogram12() {
		int bins = 2048;
		double max = 0.9647109185159629;
		double min = -0.9989960485318626;
		Histogram histo = new Histogram(bins, min, max, true);

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
	public void testHistogramNoException() {
		int bins = 2048;
		double max = 0.9989960485318626;
		Histogram h = new Histogram(bins, max, max, true);
		Dataset pd = h.value(d).get(0);
		assertEquals(bins, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));

		h = new Histogram(bins, Math.nextDown(max), max, true);
		pd = h.value(d).get(0);
		assertEquals(bins, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));
	}

	/**
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testHistogramException() {
		int bins = 2048;
		double max = 0.9989960485318626;
		Histogram h = new Histogram(bins, Math.nextUp(max), max, true);
		Dataset pd = h.value(d).get(0);
		assertEquals(bins, pd.getSize());
		assertEquals(d.getSize(), pd.getInt(0));
	}

	/**
	 * 
	 */
	@Test
	public void testHistogramSpeed() {
		long start = 0;

		Histogram h = new Histogram(50);
		Dataset d = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 100, 500000);

		Dataset a  = null;
		a = h.value(d).get(0);

		start = -System.nanoTime();
		for (int i = 0; i < 4; i++) {
			h = new Histogram(50);
			a = h.value(d).get(0);
		}
		start += System.nanoTime();

		System.out.printf("H = %s, %sms\n", a.sum().toString(), start*1e-6);
	}
}
