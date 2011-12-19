/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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
 * This interface defines the implementation-independent and generic parts of a dataset
 */
public interface IDataset extends ILazyDataset {
	/**
	 * @return Number of elements per item
	 */
	public int getElementsPerItem();

	/**
	 * @return Size of an item in dataset (in bytes)
	 */
	public int getItemsize();

	/**
	 * Set the name of the dataset
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * @param pos
	 * @return Item in given position as an object
	 */
	public Object getObject(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as a double
	 */
	public double getDouble(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as a long 
	 */
	public long getLong(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as a float
	 */
	public float getFloat(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as an int 
	 */
	public int getInt(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as a short
	 */
	public short getShort(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as a byte
	 */
	public byte getByte(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as a boolean
	 */
	public boolean getBoolean(final int... pos);

	/**
	 * Set the value given by object at given position  
	 * @param obj
	 * @param pos
	 */
	public void set(Object obj, final int... pos);

	/**
	 * @return Minimum value
	 * @throws UnsupportedOperationException if comparisons not valid
	 */
	public Number min();

	/**
	 * @return Maximum value
	 * @throws UnsupportedOperationException if comparisons not valid
	 */
	public Number max();

	/**
	 * @return Position of minimum value
	 */
	public int[] minPos();

	/**
	 * @return Position of maximum value
	 */
	public int[] maxPos();

}
