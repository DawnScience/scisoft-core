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

package uk.ac.diamond.scisoft.analysis.io;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * This interface describes the minimal metadata information that should be 
 * associated with an AbstractDataset or DataHolder. It is intended that
 * this interface will be implemented in an object that will then be 
 * associated with a DataHolder or dataset using setMetaData(IMetaData)
 */
public interface IMetaData extends Cloneable, Serializable {
	/**
	 * Update this when there are any serious changes to API
	 */
	static final long serialVersionUID = 8640458661665962384L;

	/**
	 * Returns a collection of dataset names or null if not implemented
	 * 
	 * @return collection
	 */
	public Collection<String> getDataNames();

	/**
	 * Can be implemented to return sizes of datasets
	 * 
	 * @return map of sizes
	 */
	public Map<String, Integer> getDataSizes();

	/**
	 * Can be implemented to return shapes of dataset
	 * 
	 * @return map of sizes
	 */
	public Map<String, int[]> getDataShapes();

	/**
	 * Returns string value or null if not implemented
	 * 
	 * @param key
	 * @return value
	 */
	public Serializable getMetaValue(String key) throws Exception;

	/**
	 * Returns a collection of metadata names
	 * @return collection
	 * @throws Exception
	 */
	public Collection<String> getMetaNames() throws Exception;

	/**
	 * May be implemented to provide custom metadata in the form of a collection of objects
	 * 
	 * @return collection
	 */
	public Collection<Object> getUserObjects();

	/**
	 * Copy of metadata
	 * @return copy
	 */
	public IMetaData clone();

}
