/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import uk.ac.diamond.scisoft.analysis.metadata.IMetadata;


/**
 * This interface defines the implementation-independent and generic parts of a dataset
 */
public interface IDataset extends ILazyDataset {
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
	 * Change shape and size of dataset in-place
	 * @param newShape
	 */
	public void resize(int... newShape);

	@Override
	public IDataset squeeze();

	@Override
	public IDataset squeeze(boolean onlyFromEnd);

	/**
	 * @param switches - Should be null, or one boolean. By default it is false, if the boolean is true, will ignore NaNs and ignore infinities. 
	 * @return mean of all items in dataset as a double, array of doubles or a complex number
	 */
	public Object mean(boolean... switches);

	/**
	 * NOTE this does not return the minimum value if there are NaNs in the 
	 * dataset.
	 * 
	 * @param switches - Can be null, one boolean, or two booleans. By default, both are false. If the first boolean is true, will ignore NaNs and ignore infinities. Use the second boolean to ignore infinities separately.
	 * @return Minimum value
	 * @throws UnsupportedOperationException if comparisons are not valid
	 */
	public Number min(boolean... switches);

	/**
	 * NOTE this does not return the maximum value if there are NaNs in the 
	 * dataset.
	 * 
	 * @param switches - Can be null, one boolean, or two booleans. By default, both are false. If the first boolean is true, will ignore NaNs and ignore infinities. Use the second boolean to ignore infinities separately.
	 * @return Maximum value
	 * @throws UnsupportedOperationException if comparisons are not valid
	 */
	public Number max(boolean... switches);

	/**
	 * @return Position of minimum value
	 */
	public int[] minPos();

	/**
	 * @return Position of maximum value
	 */
	public int[] maxPos();

	/**
	 * Clone dataset, making new copy of data
	 * @return a (deep) copy of dataset
	 */
	@Override
	public IDataset clone();

	/**
	 * @return the metadata, may be null
	 */
	@Override
	public IMetadata getMetadata();

	@Override
	public IDataset getSliceView(int[] start, int[] stop, int[] step);

	@Override
	public IDataset getSliceView(Slice... slice);
}
