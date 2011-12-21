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
 * Class to run over contiguous datasets
 */
public class ContiguousIterator extends IndexIterator {
	final private int imax; // maximum index in array
	final private int istep; // step over items

	/**
	 * Constructor for an iterator over the items of a contiguous dataset that are
	 * within the dimensions
	 *
	 * @param length of entire data array
	 */
	public ContiguousIterator(final int length) {
		this(length, 1);
	}

	/**
	 * Constructor for an iterator over the items of a contiguous dataset that are
	 * within the dimensions
	 *
	 * @param length of entire data array
	 * @param isize number of elements in an item
	 */
	public ContiguousIterator(final int length, final int isize) {
		istep = isize;
		index = -istep;
		imax = length*isize;
	}

	@Override
	public boolean hasNext() {
		index += istep;
		return index < imax;
	}

	@Override
	public int[] getPos() {
		return null;
	}
}
