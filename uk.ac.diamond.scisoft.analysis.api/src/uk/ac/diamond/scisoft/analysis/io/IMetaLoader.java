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

import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * Interface used to mark a loader as being available to load and return
 * metadata without loading the entire file into memory.
 */
public interface IMetaLoader {

	/**
	 * Loads the meta data from the file but not all the data.
	 * This can be read in more cheaply than the entire data.
	 * @param mon
	 */
	public void loadMetaData(IMonitor mon) throws Exception ;
	
    /**
     * Returns an object containing some data about the data file
     * to be read in. 
      * @return IMetaData
     */
	public IMetaData getMetaData();
}
