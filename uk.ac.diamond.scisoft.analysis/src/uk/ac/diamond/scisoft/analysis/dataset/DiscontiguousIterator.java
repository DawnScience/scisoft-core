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


/**
 * Class to run over non-contiguous or fragmented datasets
 */
public class DiscontiguousIterator extends IndexIterator {
	final private int[] shape;
	final private int endrank; // last dimensions index
	final private int[] gaps; // gaps in dataset
	final private int imax; // maximum index in array
	final private int istep; // step over items
	final private int[] pos; // position in dataset

	/**
	 * Constructor for an iterator over the elements of a dataset that are
	 * within the dimensions
	 *
	 * @param shape
	 * @param dataShape
	 * @param length of entire data array
	 */
	public DiscontiguousIterator(final int[] shape, final int[] dataShape, final int length) {
		this(shape, dataShape, length, 1);
	}

	/**
	 * Constructor for an iterator over the elements of a dataset that are
	 * within the dimensions
	 *
	 * @param shape
	 * @param dataShape
	 * @param length of entire data array
	 * @param isize number of elements in an item
	 */
	public DiscontiguousIterator(final int[] shape, final int[] dataShape, final int length, final int isize) {
		this.shape = shape;
		endrank = shape.length - 1;
		istep = isize;
		pos = new int[endrank + 1];
		pos[endrank] = -1;
		index = -isize;
		imax = length;

		gaps = new int[endrank + 1];
		int chunk = isize;
		for (int i = endrank; i >= 0; i--) {
			gaps[i] = (dataShape[i] - shape[i])*chunk;
			chunk *= dataShape[i];
		}
	}

	@Override
	public boolean hasNext() {
		// now move on one position
		int j = endrank;
		for (; j >= 0; j--) {
			pos[j]++;
			if (pos[j] >= shape[j]) {
				pos[j] = 0;
				index += gaps[j];
			} else {
				break;
			}
		}
		if (j == -1) {
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
}
