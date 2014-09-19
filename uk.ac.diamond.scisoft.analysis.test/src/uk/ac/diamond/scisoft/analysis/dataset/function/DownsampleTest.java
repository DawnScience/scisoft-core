/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;


import java.util.List;

import junit.framework.TestCase;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.junit.Test;

/**
 * Test down-sampling class
 */
public class DownsampleTest extends TestCase {
	Dataset d;

	@Override
	public void setUp() {
		d = DatasetFactory.createRange(24, Dataset.FLOAT32);
		d.setShape(new int[] {4, 6});
	}

	@Test
	public void testDownsamplePoint() {
		Downsample ds = new Downsample(DownsampleMode.POINT, new int[] {2, 3});

		List<? extends Dataset> dsets = ds.value(d);
		double[] answers = new double[] {0, 3, 12, 15};

		Dataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testDownsampleMean() {
		Downsample ds = new Downsample(DownsampleMode.MEAN, new int[] {2, 3});

		List<? extends Dataset> dsets = ds.value(d);
		double[] answers = new double[] {4, 7, 16, 19};

		Dataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testDownsampleMax() {
		Downsample ds = new Downsample(DownsampleMode.MAXIMUM, new int[] {2, 3});

		List<? extends Dataset> dsets = ds.value(d);
		double[] answers = new double[] {8, 11, 20, 23};

		Dataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testDownsampleMin() {
		Downsample ds = new Downsample(DownsampleMode.MINIMUM, new int[] {2, 3});

		List<? extends Dataset> dsets = ds.value(d);
		double[] answers = new double[] {0, 3, 12, 15};

		Dataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testBreak() {
		Downsample ds = new Downsample(DownsampleMode.MEAN, new int[] {5, 7, 2});

		List<? extends Dataset> dsets = ds.value(d);
		double[] answers = new double[] {11.5};

		Dataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}
}
