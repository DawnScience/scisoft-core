/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.util.Date;

import org.eclipse.dawnsci.analysis.api.metadata.IExtendedMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;

/**
 * <b>Do not use</b> this where metadata can be accessible from Jython because the anonymous class adapter pattern
 * is generally not serializable (unless the host class is serializable and has a null constructor)
 */
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
		setFilePath(path);
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

	@Override
	public IMetadata clone() {
		return filesize < 0 ? new ExtendedMetadataAdapter() : new ExtendedMetadataAdapter(new File(path));
	}	
}
