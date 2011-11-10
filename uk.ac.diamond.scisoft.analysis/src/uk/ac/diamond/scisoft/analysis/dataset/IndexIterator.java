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
 * <p>Class to provide iteration through whole data array that backs a dataset</p>
 * <p>Instantiate an iterator and use it in a while loop:
 * <pre>
 *  DoubleDataset ds = (DoubleDataset) AbstractDataset.linSpace(0,10,0.25, AbstractDataset.FLOAT64);
 *  IndexIterator iter = ds.getIterator();
 *  double[] data = ds.getData();
 *  
 *  while (iter.hasNext()) {
 *      data[iter.index] = 1.2;
 *  }
 * </pre>
 * 
 */
public abstract class IndexIterator {
	/**
	 * Index in array
	 */
	public int index;

	/**
	 * @return true if there is another iteration
	 */
	abstract public boolean hasNext();

	/**
	 * @return position indices (nb this is reference not a copy so avoid changing)
	 */
	abstract public int[] getPos();
}
