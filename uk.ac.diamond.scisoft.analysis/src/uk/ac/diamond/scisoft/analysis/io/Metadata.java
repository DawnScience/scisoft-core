/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

public class Metadata implements IMetaData {
	private static final long serialVersionUID = IMetaData.serialVersionUID;

	private Map<String, ? extends Serializable> metadata;
	private List<String> dataNames = new ArrayList<String>(1);
	private Map<String,int[]> shapes = new HashMap<String,int[]>(1);
	private Collection<Serializable> userObjects;

	public Metadata(Map<String, ? extends Serializable> metadata) {
		this.metadata = metadata;
	}

	void setMetadata(Map<String, ? extends Serializable> metadata) {
		this.metadata = metadata;
	}
	
	Map<String, ? extends Serializable> getInternalMetadata() {
		return metadata;
	}

	/**
	 * Set user objects
	 * @param objects
	 */
	void setUserObjects(Collection<Serializable> objects) {
		userObjects = objects;
	}

	/**
	 * Add name and shape of a dataset to metadata
	 * @param name
	 * @param shape (can be null or zero-length)
	 */
	void addDataInfo(String name, int... shape) {
		shapes.put(name, shape == null || shape.length == 0 ? null : shape);
	}

	@Override
	public Collection<String> getDataNames() {
		return Collections.unmodifiableCollection(dataNames);
	}

	@Override
	public Map<String, int[]> getDataShapes() {
		return Collections.unmodifiableMap(shapes);
	}

	@Override
	public Map<String, Integer> getDataSizes() {
		Map<String, Integer> sizes = new HashMap<String, Integer>(1);
		if (dataNames.size() > 0) {
			String name = dataNames.get(0);
			int[] shape = shapes.get(name);
			if (shape != null && shape.length > 1)
				sizes.put(name, shape[0] * shape[1]);
			return Collections.unmodifiableMap(sizes);
		}
		return null;
	}

	@Override
	public Serializable getMetaValue(String key) throws Exception {
		return metadata.get(key);
	}

	@Override
	public Collection<String> getMetaNames() throws Exception {
		return Collections.unmodifiableCollection(metadata.keySet());
	}

	@Override
	public Collection<Serializable> getUserObjects() {
		return userObjects;
	}

	@Override
	public IMetaData clone() {
		Metadata c = null;
		try {
			c = (Metadata) super.clone();
			HashMap<String, Serializable> md = new HashMap<String, Serializable>();
			c.metadata = md;
			ByteArrayOutputStream os = new ByteArrayOutputStream(512);
			for (String k : metadata.keySet()) {
				Serializable v = metadata.get(k);
				if (v != null) {
					SerializationUtils.serialize(v, os);
					Serializable nv = (Serializable) SerializationUtils.deserialize(os.toByteArray());
					os.reset();
					md.put(k, nv);
				} else {
					md.put(k, null);
				}
			}
			c.dataNames = new ArrayList<String>(dataNames);
			c.shapes = new HashMap<String, int[]>(1);
			for (String n : shapes.keySet()) {
				int[] s = shapes.get(n);
				c.shapes.put(n, s == null ? null : s.clone());
			}
		} catch (CloneNotSupportedException e) {
		}
		return c;
	}
}
