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
		int[] shape = ta.shape;
		int rank = shape.length;
		int[] step = new int[rank];
		Arrays.fill(step, 1);
		int[] start = new int[rank];
		int[] stop = shape.clone();
		testDatasetAxes(ta, axes, start , stop, step);
	}

	private void testDatasetAxes(AbstractDataset ta, int[] axes, int[] start, int[] stop, int[] step) {
		int[] shape = ta.shape;
		PositionIterator iter = new PositionIterator(shape, start, stop, step, axes);
		int[] pos = iter.getPos();
		int endrank = shape.length - 1;
		int[] tpos = start;

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
					if (tpos[j] >= stop[j]) {
						tpos[j] = start[j];
					} else {
						break;
					}
				}
			}

		}
	}

}
