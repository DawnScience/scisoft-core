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

import java.io.Serializable;

/**
 * This interface defines the implementation-independent and generic parts of a dataset
 */
public interface IDataset extends ILazyDataset {
	

	/**
	 * Boolean
	 */
	public static final int BOOL = 0;
	/**
	 * Signed 8-bit integer
	 */
	public static final int INT8 = 1;
	/**
	 * Signed 16-bit integer
	 */
	public static final int INT16 = 2;
	/**
	 * Signed 32-bit integer
	 */
	public static final int INT32 = 3;
	/**
	 * Integer (same as signed 32-bit integer)
	 */
	public static final int INT = INT32;
	/**
	 * Signed 64-bit integer
	 */
	public static final int INT64 = 4;
	/**
	 * 32-bit floating point
	 */
	public static final int FLOAT32 = 5;
	/**
	 * 64-bit floating point
	 */
	public static final int FLOAT64 = 6;
	/**
	 * Floating point (same as 64-bit floating point)
	 */
	public static final int FLOAT = FLOAT64;
	/**
	 * 64-bit complex floating point (real and imaginary parts are 32-bit floats)
	 */
	public static final int COMPLEX64 = 7;
	/**
	 * 128-bit complex floating point (real and imaginary parts are 64-bit floats)
	 */
	public static final int COMPLEX128 = 8;
	/**
	 * Complex floating point (same as 64-bit floating point)
	 */
	public static final int COMPLEX = COMPLEX128;

	/**
	 * String
	 */
	public static final int STRING = 9;

	/**
	 * Object
	 */
	public static final int OBJECT = 10;

	public static final int ARRAYMUL = 100;

	/**
	 * Array of signed 8-bit integers
	 */
	public static final int ARRAYINT8 = ARRAYMUL * INT8;
	/**
	 * Array of signed 16-bit integers
	 */
	public static final int ARRAYINT16 = ARRAYMUL * INT16;
	/**
	 * Array of three signed 16-bit integers for RGB values
	 */
	public static final int RGB = ARRAYINT16 + 3;
	/**
	 * Array of signed 32-bit integers
	 */
	public static final int ARRAYINT32 = ARRAYMUL * INT32;
	/**
	 * Array of signed 64-bit integers
	 */
	public static final int ARRAYINT64 = ARRAYMUL * INT64;
	/**
	 * Array of 32-bit floating points
	 */
	public static final int ARRAYFLOAT32 = ARRAYMUL * FLOAT32;
	/**
	 * Array of 64-bit floating points
	 */
	public static final int ARRAYFLOAT64 = ARRAYMUL * FLOAT64;

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
	 * Change shape and size of dataset in-place
	 * 
	 * @param newShape
	 */
	public void resize(int... newShape);

	/**
	 * 
	 * @return mean of all items in dataset as a double, array of doubles or a complex number
	 */
	public Object mean();

	/**
	 * NOTE this does not return the minimum value if there are NaNs in the 
	 * dataset.
	 * 
	 * @return Minimum value
	 * @throws UnsupportedOperationException if comparisons not valid
	 */
	public Number min();

	/**
	 * NOTE this does not return the maximum value if there are NaNs in the 
	 * dataset.
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

	/**
	 * Checks if the ILazyDataset is compatible throwing IllegalArgumentException if
	 * it is not.
	 * 
	 * @param g
	 */
	public void checkCompatibility(final ILazyDataset g) throws IllegalArgumentException;

	/**
	 * 
	 * @return the data type of the IDataset.
	 */
	public int getDtype();
	
	/**
	 * Get the underlying object buffer used by the dataset.
	 * May be used as a safe way to test for datasets with unpopulated data.
	 * 
	 * @return s
	 */
	public Serializable getBuffer();

	
}
