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


import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

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
