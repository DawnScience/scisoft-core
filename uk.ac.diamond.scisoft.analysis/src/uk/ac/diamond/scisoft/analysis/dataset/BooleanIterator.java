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
 * Class to run over an iterator and visits positions where items in selection dataset are true 
 */
public class BooleanIterator extends IndexIterator {
	final private BooleanDataset b;
	final private IndexIterator iterb;
	final private IndexIterator iterd;
	final private int[] pos; // position in dataset

	/**
	 * Constructor for an iterator over the items of a boolean dataset that are
	 * true
	 *
	 * @param iter dataset
	 * @param selection boolean dataset
	 */
	public BooleanIterator(final IndexIterator iter, final BooleanDataset selection) {
		b = selection;

		iterb = selection.getIterator();
		iterd = iter;
		pos = iterd.getPos();
	}

	@Override
	public boolean hasNext() {
		while (iterb.hasNext() && iterd.hasNext()) {
			if (b.getAbs(iterb.index)) {
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
}
