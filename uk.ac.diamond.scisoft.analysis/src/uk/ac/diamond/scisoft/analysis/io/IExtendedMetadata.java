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


import java.util.Date;

/**
 * This is an interface that has been designed to add information about a file in addition to
 * information which has been implemented in IMetaData.
 * <p>
 * It is intended that implementations of this interface will be through an adapter
 * (see {@link ExtendedMetadataAdapter})
 */
public interface IExtendedMetadata extends IMetaData {

	/**
	 * This should be the timestamp of when the experiment or measurement took place which should
	 * be recorded in the header of the file, if applicable
	 * 
	 * @return a date object to represent when the data was created
	 */
	public Date getCreation();

	/**
	 * @return a date object that indicated when the data was last modified
	 */
	public Date getLastModified();

	/**
	 * @return a string representing the user who created the file
	 */
	public String getCreator();

	/**
	 * @return a string containing the filename
	 */
	public String getFileName();

	/**
	 * @return the owner of the file
	 */
	public String getFileOwner();

	/**
	 * @return a long representing the size of the file in bytes
	 */
	public long getFileSize();

	/**
	 * @return the full path string of the file
	 */
	public String getFullPath();

	/**
	 * @return The scan command as a string that was used to generate the data. This can be null as not always
	 *         applicable
	 */
	public String getScanCommand();

}
