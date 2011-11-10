/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;

/**
 *
 */
public class PositionIteratorTest {
	/**
	 * 
	 */
	@Test
	public void testIterations() {
		int size, type;

		size = 1024;
		type = AbstractDataset.FLOAT64;
		testIterationND(size, type);

		type = AbstractDataset.COMPLEX128;
		testIterationND(size, type);
	}

	private void testIterationND(int size, int type) {
		AbstractDataset ta;

		System.out.println("Size: " + size);

		// 1D
		ta = AbstractDataset.arange(0, size, 1, type);
		testDataset(ta);

		// 2D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, size / 16);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetAxes(ta, new int[] {0});
		testDatasetAxes(ta, new int[] {1});

		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / 32, 32);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetAxes(ta, new int[] {0});
		testDatasetAxes(ta, new int[] {1});

		// 3D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, 8, size / (16 * 8));
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetAxes(ta, new int[] {0});
		testDatasetAxes(ta, new int[] {2});
		testDatasetAxes(ta, new int[] {0,1});
		testDatasetAxes(ta, new int[] {0,2});

		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / (16 * 8), 16, 8);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetAxes(ta, new int[] {0});
		testDatasetAxes(ta, new int[] {2});
		testDatasetAxes(ta, new int[] {0,1});
		testDatasetAxes(ta, new int[] {0,2});

	}

	private void testDataset(AbstractDataset ta) {
		PositionIterator iter = ta.getPositionIterator();
		int[] pos = iter.getPos();

		for (int i = 0; iter.hasNext(); i++) {
			assertEquals(i, ta.getDouble(pos), 1e-5*i);
		}
	}

	private void testDatasetAxes(AbstractDataset ta, int[] axes) {
		PositionIterator iter = ta.getPositionIterator(axes);
		int[] pos = iter.getPos();
		int[] shape = ta.shape;
		int endrank = shape.length - 1;
		int[] tpos = new int[shape.length];

		int[] step = new int[shape.length];
		Arrays.fill(step, 1);
		for (int i = 0; i < axes.length; i++) {
			step[axes[i]] = 0;
		}

		for (; iter.hasNext();) {
//			System.out.println("        " + Arrays.toString(pos));
			for (int j = 0; j <= endrank; j++) {
				assertEquals("  Axes: " + Arrays.toString(axes) + "; shape: " + Arrays.toString(shape) + "; dim " + j,
						tpos[j], pos[j], 1e-5*tpos[j]);
			}

			int j = endrank;
			for (; j >= 0; j--) {
				if (step[j] > 0) {
					tpos[j] += step[j];
					if (tpos[j] >= shape[j]) {
						tpos[j] = 0;
					} else {
						break;
					}
				}
			}

		}
	}

}
