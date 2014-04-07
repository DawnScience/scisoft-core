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

import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * This interface specifies the methods required to allow the ScanFileHolder to load in data from a file of some
 * description
 */
public interface IFileLoader {

	/**
	 * This function is called when the ScanFileHolder needs to load data from a particular source.
	 * It can also be called on by itself
	 * 
	 * @return This returned object is all the data which has been loaded returned in a small object package.
	 * @throws ScanFileHolderException
	 */
	IDataHolder loadFile() throws ScanFileHolderException;

	/**
	 * This function is called when the ScanFileHolder needs to load data from a particular source
	 * It can also be called on by itself
	 * 
	 * @param mon - may be null
	 * @return This returned object is all the data which has been loaded returned in a small object package.
	 * @throws ScanFileHolderException
	 */
	IDataHolder loadFile(IMonitor mon) throws ScanFileHolderException;

	/**
	 * Set the loader to load metadata as well as data (all loaders default to this behaviour)
	 * 
	 * @param willLoadMetadata
	 */
	void setLoadMetadata(boolean willLoadMetadata);
}
