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
 * Class to run over an integer dataset and return its items
 */
public class IntegerIterator extends IndexIterator {
	final private IntegerDataset indices;
	final private IndexIterator iter;
	final private int istep; // step over items
	final private int imax; // maximum index in array

	/**
	 * Constructor for an iterator over the items of an integer dataset
	 *
	 * @param index integer dataset
	 * @param length of entire data array
	 */
	public IntegerIterator(final Dataset index, final int length) {
		this(index, length, 1);
	}

	/**
	 * Constructor for an iterator over the items of an integer dataset
	 * 
	 * @param index integer dataset
	 * @param length of entire data array
	 * @param isize number of elements in an item
	 */
	public IntegerIterator(final Dataset index, final int length, final int isize) {
		indices = (IntegerDataset) (index instanceof IntegerDataset ? index : DatasetUtils.convertToDataset(index).cast(Dataset.INT32));
		iter = index.getIterator();
		istep = isize;
		imax = length*istep;
	}

	@Override
	public boolean hasNext() {
		while (iter.hasNext()) {
			index = istep*indices.getAbs(iter.index);
			if (index < 0)
				index += imax;
			return true;
		}
		return false;
	}

	@Override
	public int[] getPos() {
		return null;
	}

	@Override
	public void reset() {
		iter.reset();
	}
}
