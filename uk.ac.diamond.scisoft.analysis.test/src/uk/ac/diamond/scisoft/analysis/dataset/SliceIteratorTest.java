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

/**
 *
 */
public class SliceIteratorTest {
	/**
	 * 
	 */
	@Test
	public void testIterations() {
		int size, type;

		size = 1024;
		type = Dataset.FLOAT64;
		testIterationsND(size, type);

		type = Dataset.COMPLEX128;
		testIterationsND(size, type);
	}

	private void testIterationsND(int size, int type) {
		Dataset ta;

		System.out.println("Size: " + size);

		// 0D
		ta = DatasetFactory.zeros(new int[] {}, type);
		testDataset(ta);
		testDatasetSteps(ta, new int[] {});
		testDatasetAxes(ta, new boolean[] {});

		// 1D
		ta = DatasetFactory.createRange(0, size, 1, type);
		testDataset(ta);
		testDatasetSteps(ta, new int[] {2});
		testDatasetSteps(ta, new int[] {-3});
		testDatasetAxes(ta, new boolean[] {true});

		// 2D
		ta.setShape(16, size / 16);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetSteps(ta, new int[] {1, 2});
		testDatasetSteps(ta, new int[] {3, 1});
		testDatasetAxes(ta, new boolean[] {true, true});
		testDatasetAxes(ta, new boolean[] {true, false});
		testDatasetAxes(ta, new boolean[] {false, true});

		ta.setShape(size / 32, 32);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetSteps(ta, new int[] {1, 2});
		testDatasetSteps(ta, new int[] {3, 1});
		testDatasetAxes(ta, new boolean[] {true, true});
		testDatasetAxes(ta, new boolean[] {true, false});
		testDatasetAxes(ta, new boolean[] {false, true});

		// 3D
		ta.setShape(16, 8, size / (16 * 8));
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetSteps(ta, new int[] {1, 1, 3});
		testDatasetSteps(ta, new int[] {1, 2, 1});
		testDatasetSteps(ta, new int[] {3, 1, 1});
		testDatasetSteps(ta, new int[] {3, 1, 2});
		testDatasetSteps(ta, new int[] {3, -1, 2});
		testDatasetAxes(ta, new boolean[] {true, true, true});
		testDatasetAxes(ta, new boolean[] {true, true, false});
		testDatasetAxes(ta, new boolean[] {true, false, true});
		testDatasetAxes(ta, new boolean[] {true, false, false});
		testDatasetAxes(ta, new boolean[] {false, true, true});
		testDatasetAxes(ta, new boolean[] {false, true, false});
		testDatasetAxes(ta, new boolean[] {false, false, true});
		testDatasetAxes(ta, new boolean[] {false, false, false});

		ta.setShape(size / (16 * 8), 16, 8);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);
		testDatasetSteps(ta, new int[] {1, 1, 3});
		testDatasetSteps(ta, new int[] {1, 2, 1});
		testDatasetSteps(ta, new int[] {3, 1, 1});
		testDatasetSteps(ta, new int[] {3, 1, 2});
		testDatasetSteps(ta, new int[] {3, -1, 2});
		testDatasetAxes(ta, new boolean[] {true, true, true});
		testDatasetAxes(ta, new boolean[] {true, true, false});
		testDatasetAxes(ta, new boolean[] {true, false, true});
		testDatasetAxes(ta, new boolean[] {true, false, false});
		testDatasetAxes(ta, new boolean[] {false, true, true});
		testDatasetAxes(ta, new boolean[] {false, true, false});
		testDatasetAxes(ta, new boolean[] {false, false, true});
		testDatasetAxes(ta, new boolean[] {false, false, false});

	}

	private void testDataset(Dataset ta) {
		SliceIterator iter = (SliceIterator) ta.getSliceIterator(null, null, null);
		int[] pos = iter.getPos();

		for (int i = 0; iter.hasNext(); i++) {
			assertEquals(i, ta.getDouble(pos), 1e-5*i);
		}
	}

	private void testDatasetSteps(Dataset ta, int[] step) {
		SliceIterator iter = (SliceIterator) ta.getSliceIterator(null, null, step);
		int[] pos = iter.getPos();
		int[] shape = ta.getShapeRef();
		int endrank = shape.length - 1;
		int[] tpos = new int[shape.length];

		for (int j = 0; j <= endrank; j++) {
			if (step[j] < 0)
				tpos[j] = shape[j] - 1;
		}

		while (iter.hasNext()) {
//			System.out.println("        " + Arrays.toString(pos));
			for (int j = 0; j <= endrank; j++) {
				assertEquals("  step: " + Arrays.toString(step) + "; shape: " + Arrays.toString(shape) + "; dim " + j,
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
				} else {
					tpos[j] += step[j];
					if (tpos[j] < 0) {
						tpos[j] = shape[j] - 1;
					} else {
						break;
					}
				}
			}

		}
	}

	private void testDatasetAxes(Dataset ta, boolean[] axes) {
		SliceIterator iter = ta.getSliceIteratorFromAxes(null, axes);
		int[] pos = iter.getPos();
		int[] shape = ta.getShapeRef();
		int endrank = shape.length - 1;
		int[] tpos = new int[shape.length];

		while (iter.hasNext()) {
//			System.out.println("        " + Arrays.toString(pos));
			for (int j = 0; j <= endrank; j++) {
				assertEquals("  axes: " + Arrays.toString(axes) + "; shape: " + Arrays.toString(shape) + "; dim " + j,
						tpos[j], pos[j], 1e-5*tpos[j]);
			}

			int j = endrank;
			for (; j >= 0; j--) {
				if (axes[j]) {
					tpos[j]++;
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
