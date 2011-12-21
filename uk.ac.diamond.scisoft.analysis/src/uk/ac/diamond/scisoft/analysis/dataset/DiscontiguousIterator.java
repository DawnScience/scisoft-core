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
