/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;

/**
 * <b>Do not use</b> this where metadata can be accessible from Jython because the anonymous class adapter pattern
 * is generally not serializable (unless the host class is serializable and has a null constructor)
 */
public class MetaDataAdapter implements IMetadata {

	private String filePath;
	protected Collection<String> adapterDataNames;
	protected Collection<Serializable> adapterUserObjects;

	public MetaDataAdapter() {
	}
	public MetaDataAdapter(String filePath) {
		this.filePath=filePath;
	}

	public MetaDataAdapter(Collection<String> names) {
		this.adapterDataNames = names;
	}

	public MetaDataAdapter(Collection<String> names, final Collection<Serializable> userObjects) {
		this.adapterDataNames = names;
		this.adapterUserObjects = userObjects;
	}

	@Override
	public Collection<String> getDataNames() {
		return adapterDataNames;
	}

	@Override
	public Collection<Serializable> getUserObjects() {
		return adapterUserObjects;
	}

	@Override
	public Map<String, Integer> getDataSizes() {
		return null;
	}

	@Override
	public Map<String, int[]> getDataShapes() {
		return null;
	}

	@Override
	public Serializable getMetaValue(String key) throws Exception {
		return null;
	}

	@Override
	public Collection<String> getMetaNames() throws Exception {
		return null;
	}

	@Override
	public IMetadata clone() {
		
		MetaDataAdapter ret = null;
		if (adapterUserObjects == null) {
			if (adapterDataNames == null) {
				ret = new MetaDataAdapter();
			} else {
			    ret = new MetaDataAdapter(adapterDataNames);
			}
		} else {
		    ret = new MetaDataAdapter(adapterDataNames, adapterUserObjects);
		}
		ret.setFilePath(getFilePath());
		return ret;
	}
	
	@Override
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
