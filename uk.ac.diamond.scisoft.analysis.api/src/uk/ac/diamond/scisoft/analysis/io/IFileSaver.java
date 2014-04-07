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

/**
 * This interface specifies the methods required to allow the ScanFileHolder to save data to a file of some
 * description
 */
public interface IFileSaver {

	/**
	 * This function is called when the ScanFileHolder needs to save data from a particular source.
	 * It can also be called on by itself
	 * 
	 * @param holder
	 *            The object storing all the data to be saved
	 * @throws ScanFileHolderException
	 */
	void saveFile(IDataHolder holder) throws ScanFileHolderException;
}
