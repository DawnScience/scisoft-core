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

import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

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
	public IDataset loadSet(String path, String name, IMonitor mon) throws Exception;
	
	
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
