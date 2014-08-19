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
 * Class to run over an iterator and visits positions where items in selection dataset are true 
 */
public class BooleanIterator extends IndexIterator {
	final private BooleanDataset b;
	final private IndexIterator iterb;
	final private IndexIterator iterd;
	final private boolean v;
	final private int[] pos; // position in dataset

	/**
	 * Constructor for an iterator over the items of a boolean dataset that are
	 * true
	 *
	 * @param iter dataset iterator
	 * @param selection boolean dataset
	 */
	public BooleanIterator(final IndexIterator iter, final Dataset selection) {
		this(iter, selection, true);
	}

	/**
	 * Constructor for an iterator over the items of a boolean dataset that match
	 * given value
	 *
	 * @param iter dataset iterator
	 * @param selection boolean dataset
	 * @param value
	 */
	public BooleanIterator(final IndexIterator iter, final Dataset selection, boolean value) {
		b = (BooleanDataset) (selection instanceof BooleanDataset ? selection : DatasetUtils.convertToDataset(selection).cast(Dataset.BOOL));

		iterb = selection.getIterator();
		iterd = iter;
		pos = iterd.getPos();
		v = value;
	}

	@Override
	public boolean hasNext() {
		while (iterb.hasNext() && iterd.hasNext()) {
			if (b.getAbs(iterb.index) == v) {
				index = iterd.index;
				return true;
			}
		}
		return false;
	}

	@Override
	public int[] getPos() {
		return pos;
	}

	@Override
	public void reset() {
		iterb.reset();
		iterd.reset();
	}
}
