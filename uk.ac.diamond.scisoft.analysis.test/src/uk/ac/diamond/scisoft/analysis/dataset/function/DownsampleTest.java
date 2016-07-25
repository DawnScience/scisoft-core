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

import java.util.List;

import org.eclipse.dawnsci.analysis.api.downsample.DownsampleMode;
import org.eclipse.dawnsci.analysis.dataset.function.Downsample;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IndexIterator;
import org.junit.Before;
import org.junit.Test;

/**
 * Test down-sampling class
 */
public class DownsampleTest {
	Dataset d;

	@Before
	public void setUp() {
		d = DatasetFactory.createRange(24, Dataset.FLOAT32);
		d.setShape(new int[] {4, 6});
	}

	@Test
	public void testDownsamplePoint() {
		Downsample ds = new Downsample(DownsampleMode.POINT, new int[] {2, 3});

		List<Dataset> dsets = ds.value(d);
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

		List<Dataset> dsets = ds.value(d);
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

		List<Dataset> dsets = ds.value(d);
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

		List<Dataset> dsets = ds.value(d);
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

		List<Dataset> dsets = ds.value(d);
		double[] answers = new double[] {11.5};

		Dataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}
}
