/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * This service gives access to parts of the scisoft.analysis plugin
 * without having to import the concrete plugin uk.ac.diamond.scisoft.analysis.
 * So you don't end up with dependencies required by loaders when you do simple
 * dataset maniplulation for instance.
 * 
 * The service is donated in the uk.ac.diamond.scisoft.analysis.osgi plugin and
 * will be available - this plugin is started automatically in DAWN
 * based products.
 */
public interface IAnalysisService {

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
	 * 
	 * @param stop
	 * @param dtype
	 * @return IDataset
	 */
	public IDataset arange(final double stop, final int dtype);

	/**
	 * Create an arange without importing AbstractDataset.
	 * @param start
	 * @param stop
	 * @param step
	 * @param dtype
	 * @return IDataset
	 */
	public IDataset arange(final double start, final double stop, final double step, final int dtype);

	/**
	 * Create a dataset using a bunch of doubles.
	 * @param raw
	 * @param shape
	 * @return IDataset
	 */
	public IDataset createDoubleDataset(double[] raw, int... shape);

	/**
	 * Ensures that the dataset has been converted to an AbstractDataset (ie loaded data)
	 * @param set
	 * @return the set
	 */
	public IDataset convertToAbstractDataset(IDataset set);

	/**
	 * 
	 * @param sum
	 * @param i
	 * @return IDataset
	 */
	public IDataset sum(IDataset sum, int i);

	/**
	 * 
	 * @param sum
	 * @return transposed data set
	 */
	public IDataset transpose(IDataset sum);

	/**
	 * Take the mean along a given dimension.
	 * @param slice
	 * @param i
	 * @return mean
	 */
	public IDataset mean(IDataset slice, int i);

	public IDataset max(IDataset slice, int i);

	public IDataset min(IDataset slice, int i);

	public IDataset median(IDataset slice, int i);

	public IDataset mode(IDataset slice, int i);
}
