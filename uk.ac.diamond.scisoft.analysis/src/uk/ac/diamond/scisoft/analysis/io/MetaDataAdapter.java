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

import org.eclipse.january.MetadataException;
import org.eclipse.january.metadata.IMetadata;

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
		initialize(filePath);
	}

	public void initialize(String filePath) {
		this.filePath=filePath;
	}

	@Override
	public void initialize(Map<String, ? extends Serializable> metadata) {
		// TODO Auto-generated method stub
		
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
	public Serializable getMetaValue(String key) throws MetadataException {
		return null;
	}

	@Override
	public Collection<String> getMetaNames() throws MetadataException {
		return null;
	}

	@Override
	public IMetadata clone() {
		MetaDataAdapter ret = new MetaDataAdapter();
		ret.setFilePath(getFilePath());
		ret.adapterUserObjects = adapterUserObjects;
		ret.adapterDataNames = adapterDataNames;
		return ret;
	}
	
	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void addDataInfo(String n, int... shape) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addNames(Collection<String> names) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setMetadata(Map<String, ? extends Serializable> map) {
		// TODO Auto-generated method stub
	}
}
