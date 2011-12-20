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

import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.gda.monitor.IMonitor;

public interface IDataSetLoader {

	/**
	 * Used to load a specific data set by name without loading
	 * the other data sets in the file. If this is not possible for
	 * a given file format, the loader will return null.
	 * 
	 * @param path
	 * @param name
	 * @param mon
	 * @return the set loaded
	 */
	public AbstractDataset loadSet(String path, String name, IMonitor mon) throws Exception;
	
	
	/**
	 * Used to load a specific data set by name without loading
	 * the other data sets in the file. If this is not possible for
	 * a given file format, the loader will return null.
	 * 
	 * @param path
	 * @param names
	 * @param mon
	 * @return the set loaded
	 */
	public Map<String,ILazyDataset> loadSets(String path, List<String> names, IMonitor mon) throws Exception;

}
