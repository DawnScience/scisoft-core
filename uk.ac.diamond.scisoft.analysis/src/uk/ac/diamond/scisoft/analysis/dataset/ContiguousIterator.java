/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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
