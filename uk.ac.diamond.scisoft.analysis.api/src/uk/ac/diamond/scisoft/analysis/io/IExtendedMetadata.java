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


import java.util.Date;

/**
 * This is an interface that has been designed to add information about a file in addition to
 * information which has been implemented in IMetaData.
 * <p>
 * It is intended that implementations of this interface will be through an adapter
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
