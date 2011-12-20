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
	public IntegerIterator(final IntegerDataset index, final int length) {
		this(index, length, 1);
	}

	/**
	 * Constructor for an iterator over the items of an integer dataset
	 * 
	 * @param index integer dataset
	 * @param length of entire data array
	 * @param isize number of elements in an item
	 */
	public IntegerIterator(final IntegerDataset index, final int length, final int isize) {
		indices = index;
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
}
