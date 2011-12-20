/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
public interface IMetaData extends Cloneable {

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
