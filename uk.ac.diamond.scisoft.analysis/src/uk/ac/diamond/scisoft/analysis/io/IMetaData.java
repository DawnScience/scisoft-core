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

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Collection;
import java.util.Map;

/**
 * This interface describes the minimal metadata information that should be 
 * associated with an AbstractDataset or DataHolder. It is intended that
 * this interface will be implemented  in an object that will then be 
 * associated with the DataHolder or Dataset using setMetaData(IMetaData)
 */
public interface IMetaData extends Cloneable{

	/**
	 * Returns a collection of the data set names or null if not implemented.
	 * 
	 * @return collection
	 */
	public Collection<String> getDataNames();

	/**
	 * Can be implemented to return information about the sizes of the data.
	 * 
	 * @return map of sizes
	 */
	public Map<String, Integer> getDataSizes();

	/**
	 * Can be implemented to return information about the sizes of the data.
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
	public String getMetaValue(String key) throws Exception;

	public Collection<String> getMetaNames() throws Exception;

	/**
	 * May be implemented to provide custom meta data in the form of a list of objects
	 * 
	 * @return objects
	 */
	public Collection<Object> getUserObjects();

	/**
	 * Copy of object
	 * @return copy
	 */
	public IMetaData clone();

}
