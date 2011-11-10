/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;


import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.function.Downsample;
import uk.ac.diamond.scisoft.analysis.dataset.function.DownsampleMode;

/**
 * Test down-sampling class
 */
public class DownsampleTest extends TestCase {
	AbstractDataset d;

	@Override
	public void setUp() {
		d = AbstractDataset.arange(24, AbstractDataset.FLOAT32);
		d.setShape(new int[] {4, 6});
	}

	@Test
	public void testDownsamplePoint() {
		Downsample ds = new Downsample(DownsampleMode.POINT, new int[] {2, 3});

		List<AbstractDataset> dsets = ds.value(d);
		double[] answers = new double[] {0, 3, 12, 15};

		AbstractDataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testDownsampleMean() {
		Downsample ds = new Downsample(DownsampleMode.MEAN, new int[] {2, 3});

		List<AbstractDataset> dsets = ds.value(d);
		double[] answers = new double[] {4, 7, 16, 19};

		AbstractDataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testDownsampleMax() {
		Downsample ds = new Downsample(DownsampleMode.MAXIMUM, new int[] {2, 3});

		List<AbstractDataset> dsets = ds.value(d);
		double[] answers = new double[] {8, 11, 20, 23};

		AbstractDataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testDownsampleMin() {
		Downsample ds = new Downsample(DownsampleMode.MINIMUM, new int[] {2, 3});

		List<AbstractDataset> dsets = ds.value(d);
		double[] answers = new double[] {0, 3, 12, 15};

		AbstractDataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}

	@Test
	public void testBreak() {
		Downsample ds = new Downsample(DownsampleMode.MEAN, new int[] {5, 7, 2});

		List<AbstractDataset> dsets = ds.value(d);
		double[] answers = new double[] {11.5};

		AbstractDataset a = dsets.get(0);
		IndexIterator it = a.getIterator();
		for (int i = 0; it.hasNext(); i++) {
			assertEquals(answers[i], a.getElementDoubleAbs(it.index), 1e-6);
		}
	}
}
