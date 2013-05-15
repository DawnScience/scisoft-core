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

package uk.ac.diamond.scisoft.analysis.io;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

public interface IDataHolder {

	/**
	 * @return an object implementing IMetaData
	 */
	public IMetaData getMetadata();

	/**
	 * This does not retrieve lazy datasets.
	 * @param index
	 * @return Generic dataset with given index in holder
	 */
	public IDataset getDataset(int index);

	/**
	 * This does not retrieve lazy datasets.
	 * @param name
	 * @return Generic dataset with given name (first one if name not unique)
	 */
	public IDataset getDataset(String name);

	/**
	 * This pulls out the dataset which could be lazy, maintaining its laziness.
	 * @param index
	 * @return Generic dataset with given index in holder
	 */
	public ILazyDataset getLazyDataset(int index);

	/**
	 * This pulls out the dataset which could be lazy, maintaining its laziness.
	 * @param name
	 * @return Generic dataset with given name (first one if name not unique)
	 */
	public ILazyDataset getLazyDataset(String name);

	/**
	 * @param name
	 * @return true if data holder contains name 
	 * @see java.util.List#contains(Object)
	 */
	public boolean contains(String name);

	/**
	 * @return Array of dataset names
	 */
	public String[] getNames();

	/**
	 * @param index
	 * @return Dataset name at given index
	 */
	public String getName(int index);

	/**
	 * @return Number of datasets
	 */
	public int size();

	/**
	 * @return Number of unique dataset names
	 */
	public int namesSize();

}