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
	 * @param pos
	 * @return Item in given position as an object
	 */
	public Object getObject(final int... pos);

	/**
	 * @param pos
	 * @return Item in given position as a string
	 */
	public String getString(final int... pos);

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
	public void set(final Object obj, final int... pos);

	/**
	 * NOTE this does not return the minimum value if there are NaNs in the 
	 * dataset. In that case you have to work out min and max for yourself.
	 * 
	 * @return Minimum value
	 * @throws UnsupportedOperationException if comparisons not valid
	 */
	public Number min();

	/**
	 * NOTE this does not return the maximum value if there are NaNs in the 
	 * dataset. In that case you have to work out min and max for yourself.
	 * 
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

	/**
	 * Clone dataset without making new copy of data
	 * @return a (shallow) copy of dataset
	 */
	@Override
	public IDataset clone();
}
