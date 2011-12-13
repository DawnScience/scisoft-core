/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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

import java.io.File;
import java.util.Date;

public class ExtendedMetadataAdapter extends MetaDataAdapter implements IExtendedMetadata {
	private String creator = null;
	private String filename = null;
	private String owner = null;
	private long filesize = -1;
	private String path;
	private Date lastModified = null;

	public ExtendedMetadataAdapter() {

	}

	/**
	 * This constructor will take a reference to a file and populate some of the metadata. This
	 * should be used in conjunction with populating the rest of the metadata
	 */
	public ExtendedMetadataAdapter(File f) {
		filesize = f.length();
		filename = f.getName();
		lastModified = new Date(f.lastModified());
		path = f.getAbsolutePath();
	}

	@Override
	public Date getCreation() {
		return null;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

	@Override
	public String getCreator() {
		return creator;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public String getFileOwner() {
		return owner;
	}

	@Override
	public long getFileSize() {
		return filesize;
	}

	@Override
	public String getFullPath() {
		return path;
	}

	@Override
	public String getScanCommand() {
		return null;
	}
}
