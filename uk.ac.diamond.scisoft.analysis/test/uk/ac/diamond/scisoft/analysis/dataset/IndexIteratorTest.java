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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 *
 */
public class IndexIteratorTest {

	/**
	 * 
	 */
	@Test
	public void testIterations() {
		int size, type;

		size = 1024;
		type = AbstractDataset.FLOAT64;
		testIterationsND(size, type);

		type = AbstractDataset.COMPLEX128;
		testIterationsND(size, type);
	}

	private void testIterationsND(int size, int type) {
		AbstractDataset ta;


		System.out.println("Size: " + size);

		// 0D or scalar
		ta = AbstractDataset.zeros(new int[] {}, type);
		testDataset(ta);

		// 1D
		ta = AbstractDataset.arange(0, size, 1, type);
		testDataset(ta);

		// 2D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, size / 16);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);

		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / 32, 32);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);

		// 3D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, 8, size / (16 * 8));
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);

		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / (16 * 8), 16, 8);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testDataset(ta);

	}

	private void testDataset(AbstractDataset ta) {
		IndexIterator iter = ta.getIterator();
		double[] data = (double[]) ta.getBuffer();

		for (int i = 0; iter.hasNext(); i++) {
			assertEquals(i, data[iter.index], 1e-5*i);
		}

		iter = ta.getIterator(true);
		int[] pos = iter.getPos();
		for (int i = 0; iter.hasNext(); i++) {
			assertEquals(i, ta.getDouble(pos), 1e-5*i);
		}
	}

	private void testExpandedDataset(AbstractDataset ta, int ipos, int index) {
		IndexIterator iter = ta.getIterator(true);
		double[] data = (double[]) ta.getBuffer();
		int[] pos = iter.getPos();

		for (int i = 0; iter.hasNext(); i++) {
			if (pos[ipos] == index) {
				assertEquals("Position " + Arrays.toString(pos), 0, data[iter.index], 0);
				i--;
			} else {
				assertEquals("Position " + Arrays.toString(pos), i, data[iter.index], 1e-5*i);
			}
		}
	}

	/**
	 * 
	 */
	@Test
	public void testExpandedIterations() {
		int size, type;

		size = 1024;
		type = AbstractDataset.FLOAT64;
		testExpandedIterationsND(size, type);

		type = AbstractDataset.COMPLEX128;
		testExpandedIterationsND(size, type);
	}

	private void testExpandedIterationsND(int size, int type) {
		AbstractDataset ta;

		System.out.println("Size: " + size);

		// 1D
		ta = AbstractDataset.arange(0, size, 1, type);
		ta.set(0, size);
		System.out.println(" New size: " + ta.getSize());
		testExpandedDataset(ta, 0, size);

		// 2D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, size / 16);
		for (int k = 0, kmax = ta.getShape()[1]; k < kmax; k++)
			ta.set(0, 16, k);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testExpandedDataset(ta, 0, 16);

		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / 32, 32);
		for (int k = 0, kmax = ta.getShape()[0]; k < kmax; k++)
			ta.set(0, k, 32);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testExpandedDataset(ta, 1, 32);

		// 3D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, 8, size / (16 * 8));
		for (int k = 0, kmax = ta.getShape()[1]; k < kmax; k++)
			for (int l = 0, lmax = ta.getShape()[2]; l < lmax; l++)
				ta.set(0, 16, k, l);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testExpandedDataset(ta, 0, 16);

		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / (16 * 8), 16, 8);
		for (int k = 0, kmax = ta.getShape()[0]; k < kmax; k++)
			for (int l = 0, lmax = ta.getShape()[1]; l < lmax; l++)
				ta.set(0, k, l, 8);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testExpandedDataset(ta, 2, 8);
	}

	private AbstractDataset oldSlice(AbstractDataset t, SliceIterator siter) {
		int[] shape = siter.getSliceShape();
		int rank = shape.length;
		int[] lstart = siter.getStart();
		int[] lstep = siter.getStep();
		AbstractDataset result = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);

		// set up the vectors needed to do this
		int relative[] = new int[rank];
		int absolute[] = new int[rank];

		for (int i = 0; i < rank; i++) {
			relative[i] = lstart[i];
			absolute[i] = 0;
		}

		// now perform the loop
		while (true) {
			// write the value from the relative position of this dataset
			// to the actual position in the final vector.
			result.set(t.getDouble(relative), absolute);

			// now move the count on one position
			int j = rank - 1;
			for (; j >= 0; j--) {
				relative[j] += lstep[j];
				absolute[j]++;
				if (absolute[j] >= shape[j]) {
					relative[j] = lstart[j];
					absolute[j] = 0;
				} else {
					break;
				}
			}
			if (j == -1)
				break;
		}
		return result;
	}

	@SuppressWarnings({ "null" })
	private void testSlicedDataset(AbstractDataset t, int start, int startaxis, int step, int stepaxis) {
		int rank = t.getRank();
		int[] steps = new int[rank];
		int[] starts = new int[rank];

		Arrays.fill(steps, 1);
		while (stepaxis > rank) {
			stepaxis -= rank;
		}
		if (stepaxis < 0)
			stepaxis += rank;

		steps[stepaxis] = step;

		//Arrays.fill(starts, 1);
		while (startaxis > rank) {
			startaxis -= rank;
		}
		if (startaxis < 0)
			startaxis += rank;

		starts[startaxis] = start;

		int nloop = 7;
		long stime;
		List<Long> elapsed = new ArrayList<Long>();

		AbstractDataset sliced = null;
		SliceIterator siter = (SliceIterator) t.getSliceIterator(starts, null, steps);

		elapsed.clear();
		for (int i = 0; i < nloop; i++) {
			stime = System.nanoTime();
			sliced = oldSlice(t, siter);
			elapsed.add(System.nanoTime() - stime);
		}
		Collections.sort(elapsed);
//		System.out.println("  Sliced shape: " + Arrays.toString(sliced.getShape()));
//		System.out.println(String.format("    old  %5.2fus", elapsed.get(0)*1e-3));

		double[] sdata = (double[]) sliced.getBuffer();

		AbstractDataset nsliced = null;

		elapsed.clear();
		for (int i = 0; i < nloop; i++) {
			stime = System.nanoTime();
			nsliced = t.getSlice(starts, null, steps);
			elapsed.add(System.nanoTime() - stime);
		}
		Collections.sort(elapsed);
//		System.out.println(String.format("    iter %5.2fus", elapsed.get(0)*1e-3));

		double[] ndata = (double[]) nsliced.getBuffer();
		IndexIterator iter = nsliced.getIterator();
		for (int i = 0; i < sdata.length && iter.hasNext(); i++) {
			assertEquals(sdata[i], ndata[iter.index], 1e-5*sdata[i]);
		}
	}

	/**
	 * 
	 */
	@Test
	public void testSliceIteration() {
		int size, type;

		size = 60;
		type = AbstractDataset.FLOAT64;
		testSliceIterationND(size, type);

		type = AbstractDataset.COMPLEX128;
		testSliceIterationND(size, type);
	}

	private void testSliceIterationND(int size, int type) {
		AbstractDataset ta;

		System.out.println(" Size: " + size);

		// 1D
		ta = AbstractDataset.arange(0, size, 1, type);
		testSlicedDataset(ta, 0, 0, 3, 0);
		testSlicedDataset(ta, 0, 0, 62, 0);
		testSlicedDataset(ta, 23, 0, 3, 0);
		testSlicedDataset(ta, 23, 0, 62, 0);

		// 2D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / 15, 15);
//		ta.reshape(15, size / 15);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testSlicedDataset(ta, 0, 0, 3, 0);
		testSlicedDataset(ta, 0, 0, 3, 1);
		testSlicedDataset(ta, 2, 0, 3, 0);
		testSlicedDataset(ta, 2, 0, 3, 1);
		testSlicedDataset(ta, 3, 1, 3, 0);
		testSlicedDataset(ta, 3, 1, 3, 1);

		testSlicedDataset(ta, 0, 0, 4, 0);
		testSlicedDataset(ta, 0, 0, 4, 1);
		testSlicedDataset(ta, 2, 0, 4, 0);
		testSlicedDataset(ta, 2, 0, 4, 1);
		testSlicedDataset(ta, 3, 1, 4, 0);
		testSlicedDataset(ta, 3, 1, 4, 1);

		testSlicedDataset(ta, 0, 0, -1, 0);
		testSlicedDataset(ta, 0, 0, -1, 1);
		testSlicedDataset(ta, 2, 0, -1, 0);
		testSlicedDataset(ta, 2, 0, -1, 1);
		testSlicedDataset(ta, 3, 1, -1, 0);
		testSlicedDataset(ta, 3, 1, -1, 1);

		testSlicedDataset(ta, 0, 0, -2, 0);
		testSlicedDataset(ta, 0, 0, -2, 1);
		testSlicedDataset(ta, 2, 0, -2, 0);
		testSlicedDataset(ta, 2, 0, -2, 1);
		testSlicedDataset(ta, 3, 1, -2, 0);
		testSlicedDataset(ta, 3, 1, -2, 1);

		testSlicedDataset(ta, 0, 0, -3, 0);
		testSlicedDataset(ta, 0, 0, -3, 1);
		testSlicedDataset(ta, 2, 0, -3, 0);
		testSlicedDataset(ta, 2, 0, -3, 1);
		testSlicedDataset(ta, 3, 1, -3, 0);
		testSlicedDataset(ta, 3, 1, -3, 1);

		// 3D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / 10, 2, 5);
//		ta.reshape(5, size / 10, 2);
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));

		testSlicedDataset(ta, 0, 0, 3, 0);
		testSlicedDataset(ta, 0, 0, 3, 1);
		testSlicedDataset(ta, 0, 0, 3, 2);
		testSlicedDataset(ta, 3, 0, 3, 0);
		testSlicedDataset(ta, 3, 0, 3, 1);
		testSlicedDataset(ta, 3, 0, 3, 2);
		testSlicedDataset(ta, 1, 1, 3, 0);
		testSlicedDataset(ta, 1, 1, 3, 1);
		testSlicedDataset(ta, 1, 1, 3, 2);
		testSlicedDataset(ta, 2, 2, 3, 0);
		testSlicedDataset(ta, 2, 2, 3, 1);
		testSlicedDataset(ta, 2, 2, 3, 2);

		testSlicedDataset(ta, 0, 0, 4, 0);
		testSlicedDataset(ta, 0, 0, 4, 1);
		testSlicedDataset(ta, 0, 0, 4, 2);
		testSlicedDataset(ta, 3, 0, 4, 0);
		testSlicedDataset(ta, 3, 0, 4, 1);
		testSlicedDataset(ta, 3, 0, 4, 2);
		testSlicedDataset(ta, 1, 1, 4, 0);
		testSlicedDataset(ta, 1, 1, 4, 1);
		testSlicedDataset(ta, 1, 1, 4, 2);
		testSlicedDataset(ta, 2, 2, 4, 0);
		testSlicedDataset(ta, 2, 2, 4, 1);
		testSlicedDataset(ta, 2, 2, 4, 2);

		testSlicedDataset(ta, 0, 0, -1, 0);
		testSlicedDataset(ta, 0, 0, -1, 1);
		testSlicedDataset(ta, 0, 0, -1, 2);
		testSlicedDataset(ta, 3, 0, -1, 0);
		testSlicedDataset(ta, 3, 0, -1, 1);
		testSlicedDataset(ta, 3, 0, -1, 2);
		testSlicedDataset(ta, 1, 1, -1, 0);
		testSlicedDataset(ta, 1, 1, -1, 1);
		testSlicedDataset(ta, 1, 1, -1, 2);
		testSlicedDataset(ta, 2, 2, -1, 0);
		testSlicedDataset(ta, 2, 2, -1, 1);
		testSlicedDataset(ta, 2, 2, -1, 2);

		testSlicedDataset(ta, 0, 0, -2, 0);
		testSlicedDataset(ta, 0, 0, -2, 1);
		testSlicedDataset(ta, 0, 0, -2, 2);
		testSlicedDataset(ta, 3, 0, -2, 0);
		testSlicedDataset(ta, 3, 0, -2, 1);
		testSlicedDataset(ta, 3, 0, -2, 2);
		testSlicedDataset(ta, 1, 1, -2, 0);
		testSlicedDataset(ta, 1, 1, -2, 1);
		testSlicedDataset(ta, 1, 1, -2, 2);
		testSlicedDataset(ta, 2, 2, -2, 0);
		testSlicedDataset(ta, 2, 2, -2, 1);
		testSlicedDataset(ta, 2, 2, -2, 2);

		testSlicedDataset(ta, 0, 0, -3, 0);
		testSlicedDataset(ta, 0, 0, -3, 1);
		testSlicedDataset(ta, 0, 0, -3, 2);
		testSlicedDataset(ta, 3, 0, -3, 0);
		testSlicedDataset(ta, 3, 0, -3, 1);
		testSlicedDataset(ta, 3, 0, -3, 2);
		testSlicedDataset(ta, 1, 1, -3, 0);
		testSlicedDataset(ta, 1, 1, -3, 1);
		testSlicedDataset(ta, 1, 1, -3, 2);
		testSlicedDataset(ta, 2, 2, -3, 0);
		testSlicedDataset(ta, 2, 2, -3, 1);
		testSlicedDataset(ta, 2, 2, -3, 2);
	}

	private int get1DIndex(int[] pos, int[] shape) {
		int rank = shape.length;
		int index = pos[0];

		for (int i = 1; i < rank; i++) {
			index = (index * shape[i]) + pos[i];
		}
		return index;
	}

	private int[] getNDPosition(int n, int[] shape) {
		int rank = shape.length;
		int[] output = new int[rank];

		int inValue = n;
		for (int i = rank - 1; i > 0; i--) {
			output[i] = inValue % shape[i];
			inValue /= shape[i];
		}
		output[0] = inValue;

		return output;
	}

	private void testSlicedExpandedDataset(AbstractDataset t, int[] oshape, int ipos, int index, int start, int startaxis, int step, int stepaxis) {
		int rank = t.getRank();
		int[] steps = new int[rank];
		int[] starts = new int[rank];


		Arrays.fill(steps, 1);
		while (stepaxis > rank) {
			stepaxis -= rank;
		}
		if (stepaxis < 0)
			stepaxis += rank;

		steps[stepaxis] = step;

		//Arrays.fill(starts, 1);
		while (startaxis > rank) {
			startaxis -= rank;
		}
		if (startaxis < 0)
			startaxis += rank;

		starts[startaxis] = start;

		SliceIterator iter = (SliceIterator) t.getSliceIterator(starts.clone(), null, steps);

		int[] pos = iter.getPos();
		int[] sshape = iter.getSliceShape();

//		System.out.println("Shape: original " + Arrays.toString(oshape) + "; new " + Arrays.toString(t.getShape()) + "; true " + Arrays.toString(t.getTrueShape()));
//		System.out.println("Step " + Arrays.toString(iter.getStep()) + "; slice " + Arrays.toString(sshape) + "; gaps " + Arrays.toString(iter.getGap()));

		sshape = getSliceShape(oshape, starts, steps);
//		System.out.println("Shape: adj slice " + Arrays.toString(sshape));
		double[] data = (double[]) t.getBuffer();
//		int off = get1DIndex(starts, oshape);
		for (int i = 0; iter.hasNext(); i++) {
			int[] spos; // position within slice
			int j;
			if (pos[ipos] == index) {
				assertEquals("Position " + Arrays.toString(pos), 0, data[iter.index], 0);
				i--;
			} else {
				spos = getNDPosition(i, sshape);
//				System.out.println("spos " + Arrays.toString(spos));
				spos[stepaxis] *= step; // can do this as only one non-unit step
				if (spos[stepaxis] >= oshape[stepaxis]) {
					spos[stepaxis] = 0;
					if (stepaxis > 0)
						spos[stepaxis-1]++;
				}
				spos[startaxis] += start; // also only one non-zero start
				int s = startaxis;
				int d = spos[s] - oshape[s];
				while (d >= 0 && s > 0) { // carry
					spos[s] = d;
					if (s == startaxis)
						spos[s] += start;
					if (s-1 == stepaxis) {
						spos[s-1] += step;
					} else {
						spos[s-1]++;
					}
					s--;
					d = spos[s] - oshape[s];
				}
				j = get1DIndex(spos, oshape);
//				System.out.println(" Index " + iter.index + ", " + i + ", " + j + "; pos " +  Arrays.toString(pos) + "; " + Arrays.toString(spos) + "; data " + data[iter.index]);
				assertEquals("Position " + Arrays.toString(pos), j, data[iter.index], 1e-5*i);
			}
		}
	}

	private int[] getSliceShape(int[] shape, int[] start, int[] step) {
		int rank = shape.length;
		int[] sshape = new int[rank];

		for (int i = 0; i < rank; i++) {
			if (start[i] < 0) {
				start[i] += shape[i];
			}
			if (start[i] < 0) {
				start[i] = step[i] > 0 ? 0 : -1;
			}
			if (start[i] > shape[i]) {
				start[i] = step[i] > 0 ? shape[i] : shape[i] - 1;
			}
			int stop = step[i] > 0 ? shape[i] : -1;
			if (step[i] > 0) {
				sshape[i] = (stop - start[i] - 1) / step[i] + 1;
			} else {
				sshape[i] = (stop - start[i] + 1) / step[i] + 1;
			}
		}
		return sshape;
	}

	/**
	 * 
	 */
	@Test
	public void testExpandedSliceIterations() {
		int size, type;

		size = 1024;
		type = AbstractDataset.FLOAT64;
		testExpandedSliceIterationND(size, type);

		type = AbstractDataset.COMPLEX128;
		testExpandedSliceIterationND(size, type);
	}

	private void testExpandedSliceIterationND(int size, int type) {
		AbstractDataset ta;
		int[] oshape;
		System.out.println("Size: " + size);

		// 1D
		ta = AbstractDataset.arange(0, size, 1, type);
		oshape = ta.getShape().clone();
		ta.set(0, size);
		System.out.println(" New size: " + ta.getSize());

		testSlicedExpandedDataset(ta, oshape, 0, size, 0, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, size, 0, 0, size+1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, size, 23, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, size, 23, 0, size+1, 0);

		// 2D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, size / 16);
		oshape = ta.getShape().clone();
		for (int k = 0, kmax = ta.getShape()[1]; k < kmax; k++)
			ta.set(0, 16, k);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, 3, 1);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, 4, 1);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, -1, 1);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, -2, 1);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 1, -3, 1);

		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / 32, 32);
		oshape = ta.getShape().clone();
		for (int k = 0, kmax = ta.getShape()[0]; k < kmax; k++)
			ta.set(0, k, 32);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, 3, 1);

		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, 4, 1);

		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, -1, 1);

		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, -2, 1);

		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 0, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 2, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 1, 32, 3, 1, -3, 1);

		// 3D
		ta = AbstractDataset.arange(0, size, 1, type).reshape(16, 8, size / (16 * 8));
		oshape = ta.getShape().clone();
		for (int k = 0, kmax = ta.getShape()[1]; k < kmax; k++)
			for (int l = 0, lmax = ta.getShape()[2]; l < lmax; l++)
				ta.set(0, 16, k, l);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 3, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, 3, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, 3, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, 3, 2);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, 4, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, 4, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, 4, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, 4, 2);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -1, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -1, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -1, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -1, 2);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -2, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -2, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -2, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -2, 2);

		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 0, 0, -3, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 3, 0, -3, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 1, 1, -3, 2);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 0, 16, 2, 2, -3, 2);

		//
		ta = AbstractDataset.arange(0, size, 1, type).reshape(size / (16 * 8), 16, 8);
		oshape = ta.getShape().clone();
		for (int k = 0, kmax = ta.getShape()[0]; k < kmax; k++)
			for (int l = 0, lmax = ta.getShape()[1]; l < lmax; l++)
				ta.set(0, k, l, 8);
		System.out.println(" New size: " + ta.getSize());
		System.out.println(" Shape: " + Arrays.toString(ta.getShape()));
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, 3, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, 3, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, 3, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, 3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, 3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, 3, 2);

		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, 4, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, 4, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, 4, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, 4, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, 4, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, 4, 2);

		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -1, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -1, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -1, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -1, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -1, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -1, 2);

		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -2, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -2, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -2, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -2, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -2, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -2, 2);

		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 0, 0, -3, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 3, 0, -3, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 1, 1, -3, 2);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -3, 0);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -3, 1);
		testSlicedExpandedDataset(ta, oshape, 2, 8, 2, 2, -3, 2);
	}
}
