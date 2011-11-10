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

import uk.ac.gda.monitor.IMonitor;

/**
 * Interface used to mark a Loader as being available to Load and return
 * meta data without loading the entire file into memory.
 * 
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
