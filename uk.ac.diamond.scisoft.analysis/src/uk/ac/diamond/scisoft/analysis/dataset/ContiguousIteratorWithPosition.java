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
 * Class to run over contiguous datasets and keep track of position. Note this is slower than ContiguousIterator
 */
public class ContiguousIteratorWithPosition extends IndexIterator {
	final private int[] shape;
	final private int endrank; // last dimensions index
	final private int imax; // maximum index in array
	final private int istep; // step over items
	final private int[] pos; // position in dataset

	/**
	 * Constructor for an iterator over the items of a dataset that are
	 * within the dimensions
	 *
	 * @param shape
	 * @param length of entire data array
	 */
	public ContiguousIteratorWithPosition(final int[] shape, final int length) {
		this(shape, length, 1);
	}

	/**
	 * Constructor for an iterator over the items of a dataset that are
	 * within the dimensions
	 *
	 * @param shape
	 * @param length of entire data array
	 * @param isize number of elements in an item
	 */
	public ContiguousIteratorWithPosition(final int[] shape, final int length, final int isize) {
		this.shape = shape;
		endrank = this.shape.length - 1;
		istep = isize;
		if (shape.length == 0) {
			pos = new int[0];
		} else {
			pos = new int[endrank + 1];
			pos[endrank] = -1;
		}
		index = -isize;
		imax = length;
	}

	@Override
	public boolean hasNext() {
		// now move on one position
		int j = endrank;
		for (; j >= 0; j--) {
			pos[j]++;
			if (pos[j] >= shape[j]) {
				pos[j] = 0;
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

	/**
	 * @return position indices (nb not a copy)
	 */
	@Override
	public int[] getPos() {
		return pos;
	}
}
