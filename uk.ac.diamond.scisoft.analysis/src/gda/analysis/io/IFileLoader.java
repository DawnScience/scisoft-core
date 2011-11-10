/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.io;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.gda.monitor.IMonitor;

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
	DataHolder loadFile() throws ScanFileHolderException;

	/**
	 * This function is called when the ScanFileHolder needs to load data from a particular source
	 * It can also be called on by itself
	 * 
	 * @param mon - may be null
	 * @return This returned object is all the data which has been loaded returned in a small object package.
	 * @throws ScanFileHolderException
	 */
	DataHolder loadFile(IMonitor mon) throws ScanFileHolderException;

	/**
	 * Set the loader to load metadata as well as data (all loaders default to this behaviour)
	 * 
	 * @param willLoadMetadata
	 */
	void setLoadMetadata(boolean willLoadMetadata);
}
