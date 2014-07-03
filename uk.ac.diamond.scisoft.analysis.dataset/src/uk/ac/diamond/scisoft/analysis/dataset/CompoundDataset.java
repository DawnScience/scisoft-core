/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

public interface CompoundDataset extends Dataset {

	@Override
	public CompoundDataset reshape(int... shape);

	@Override
	public CompoundDataset sort(Integer axis);

	/**
	 * Get an item as a double array
	 * @param darray double array must be allocated and have sufficient length
	 * @param pos
	 */
	public void getDoubleArray(double[] darray, int... pos);

	/**
	 * Get an item as a double array
	 * @param index
	 * @param darray double array must be allocated and have sufficient length
	 */
	public void getDoubleArrayAbs(int index, double[] darray);

	/**
	 * Get chosen elements from each item as a dataset
	 * @param element
	 * @return dataset of chosen elements
	 */
	public Dataset getElements(int element);

	/**
	 * Set values of chosen elements from each item according to source dataset
	 * @param source
	 * @param element
	 */
	public void setElements(Dataset source, int element);

	/**
	 * Copy chosen elements from each item to another dataset
	 * @param destination
	 * @param element
	 */
	public void copyElements(Dataset destination, int element);

	/**
	 * Get a non-compound dataset version
	 * @param shareData if true, share data otherwise copy it
	 * @return non-compound dataset
	 */
	public Dataset asNonCompoundDataset(final boolean shareData);

	/**
	 * Calculate maximum values of elements over all items in dataset
	 * @return double array of element-wise maxima
	 */
	public double[] maxItem();

	/**
	 * Calculate minimum values of elements over all items in dataset
	 * @return double array of element-wise minima
	 */
	public double[] minItem();

	@Override
	public CompoundDataset getError();

	/**
	 * @param i
	 * @return item in given position
	 */
	public byte[] getByteArray(final int i);

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public byte[] getByteArray(final int i, final int j);

	/**
	 * @param pos
	 * @return item in given position
	 */
	public byte[] getByteArray(final int... pos);

	/**
	 * @param i
	 * @return item in given position
	 */
	public short[] getShortArray(final int i);

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public short[] getShortArray(final int i, final int j);

	/**
	 * @param pos
	 * @return item in given position
	 */
	public short[] getShortArray(final int... pos);

	/**
	 * @param i
	 * @return item in given position
	 */
	public int[] getIntArray(final int i);

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public int[] getIntArray(final int i, final int j);

	/**
	 * @param pos
	 * @return item in given position
	 */
	public int[] getIntArray(final int... pos);

	/**
	 * @param i
	 * @return item in given position
	 */
	public long[] getLongArray(final int i);

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public long[] getLongArray(final int i, final int j);

	/**
	 * @param pos
	 * @return item in given position
	 */
	public long[] getLongArray(final int... pos);

	/**
	 * @param i
	 * @return item in given position
	 */
	public float[] getFloatArray(final int i);

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public float[] getFloatArray(final int i, final int j);

	/**
	 * @param pos
	 * @return item in given position
	 */
	public float[] getFloatArray(final int... pos);

	/**
	 * @param i
	 * @return item in given position
	 */
	public double[] getDoubleArray(final int i);

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public double[] getDoubleArray(final int i, final int j);

	/**
	 * @param pos
	 * @return item in given position
	 */
	public double[] getDoubleArray(final int... pos);
}