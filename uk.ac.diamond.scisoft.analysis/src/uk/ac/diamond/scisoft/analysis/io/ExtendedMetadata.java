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
import java.io.Serializable;
import java.util.Date;

import org.eclipse.dawnsci.analysis.api.metadata.IExtendedMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.Metadata;

public class ExtendedMetadata extends Metadata implements IExtendedMetadata {
	private static final long serialVersionUID = IMetadata.serialVersionUID;
	private String creator = null;
	private String filename = null;
	private String owner = null;
	private long filesize = -1;
	private String path;
	private Date lastModified = null;
	private Date date;

	/**
	 * This constructor will take a reference to a file and populate some of the metadata. This
	 * should be used in conjunction with populating the rest of the metadata
	 */
	public ExtendedMetadata(File f) {
		super();
		setFile(f);
	}

	void setFile(File f) {
		if (f != null) {
			filesize = f.length();
			filename = f.getName();
			lastModified = new Date(f.lastModified());
			path = f.getAbsolutePath();
			setFilePath(path);
		}
	}

	void setCreationDate(Date date) {
		this.date = date;
	}

	@Override
	public Date getCreation() {
		return date;
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
		Serializable scanCmd = null;
		try {
			scanCmd = getMetaValue("cmd");
			if (scanCmd == null)
				scanCmd = getMetaValue("command");
			if (scanCmd == null)
				scanCmd = getMetaValue("scancommand");
		} catch (Exception e) {
		}
		return scanCmd == null ? null : scanCmd.toString();
	}

	@Override
	public IMetadata clone() {
		ExtendedMetadata c = (ExtendedMetadata) super.clone();
		if (filesize < 0 && path != null) {
			c.setFile(new File(path));
		}
		c.date = date;
		return c;
	}	
}
