/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset;


import java.util.Arrays;

/**
 * Class to run over a slice of a dataset
 */
public class SliceIterator extends IndexIterator {
	final int[] shape;
	final int endrank; // last shape index
	final int[] gap; // gaps in dataset
	final int imax; // maximum index in array
	final int[] start;
	final int[] stop;
	final int[] step;
	final int[] sshape; // slice shape
	int[] pos; // position in dataset
	int istep; // step in last index

	public SliceIterator(final int[] dimensions, final int length, final int[] sshape) {
		this(dimensions, length, null, null, sshape, 1);
	}

	public SliceIterator(final int[] dimensions, final int length, final int[] start, final int[] sshape) {
		this(dimensions, length, start, null, sshape, 1);
	}

	public SliceIterator(final int[] dimensions, final int length, final int[] sshape, final int isize) {
		this(dimensions, length, null, null, sshape, isize);
	}

	public SliceIterator(final int[] dimensions, final int length, final int[] start, final int[] sshape, final int isize) {
		this(dimensions, length, start, null, sshape, isize);
	}

	public SliceIterator(final int[] dimensions, final int length, final int[] start, final int[] step, final int[] sshape) {
		this(dimensions, length, start, step, sshape, 1);
	}

	/**
	 * Constructor for an iterator over the elements of a sliced dataset
	 * 
	 * @param shape or dataShape
	 * @param length of entire data array
	 * @param start (if null then equivalent to the origin)
	 * @param step (cannot contain zeros, if null then equivalent to ones)
	 * @param sshape shape of new dataset, i.e. slice
	 * @param isize number of elements in an item
	 */
	public SliceIterator(final int[] shape, final int length, final int[] start, final int[] step, final int[] sshape, final int isize) {
		int rank = shape.length;
		endrank = rank - 1;
		this.shape = shape;
		this.start = (start == null) ? new int[rank] : start;
		this.sshape = sshape;
		if (step == null) {
			this.step = new int[rank];
			Arrays.fill(this.step, 1);
		} else {
			this.step = step;
		}

		if (rank == 0) {
			istep = isize;
			index = -isize;
			imax = length*isize;
			stop = new int[0];
			pos = new int[0];
			gap = null;
		} else {


		istep = this.step[endrank];
		pos = Arrays.copyOf(start, rank);
		pos[endrank] -= istep;
		istep *= isize;
		imax = length*isize;
		stop = new int[rank];

		// work out index of first position
		index = pos[0];
		for (int j = 1; j <= endrank; j++)
			index = index*shape[j] + pos[j];
		index *= isize;

		gap = new int[endrank+1];
		int chunk = isize;
		for (int i = endrank; i >= 0; i--) {
			stop[i] = this.start[i] + sshape[i]*this.step[i];

			if (this.step[i] < 0)
				stop[i]++; // adjust for -ve steps so later code has more succinct test

			if (i > 0) {
				gap[i] = (shape[i] * this.step[i - 1] - sshape[i] * this.step[i]) * chunk;
				chunk *= shape[i];
			}
		}
		}
	}

	@Override
	public boolean hasNext() {
		// now move on one position in slice
		int j = endrank;
		for (; j >= 0; j--) {
			pos[j] += step[j];

			if ((pos[j] >= stop[j]) == (step[j] > 0)) { // stop index has been adjusted in code for -ve steps
				pos[j] = start[j];
				index += gap[j];
			} else {
				break;
			}
		}
		if (j == -1 && endrank >= 0) {
			index = imax;
			return false;
		}

		index += istep;
		return index < imax;
	}

	@Override
	public int[] getPos() {
		return pos;
	}

	public int[] getStart() {
		return start;
	}

	public int[] getStop() {
		return stop;
	}

	public int[] getStep() {
		return step;
	}

	public int[] getGap() {
		return gap;
	}

	public int[] getShape() {
		return shape;
	}

	public int[] getSliceShape() {
		return sshape;
	}
}
