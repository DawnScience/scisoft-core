/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.IMetaData;
import uk.ac.diamond.scisoft.analysis.metadata.MetadataType;

/**
 * Common base for both lazy and normal dataset implementations
 */
public abstract class LazyDatasetBase implements ILazyDataset, Serializable {

	protected static final Logger logger = LoggerFactory.getLogger(LazyDatasetBase.class);

	protected String name = "";

	/**
	 * The shape or dimensions of the dataset
	 */
	protected int[] shape;

	protected Map<Class<? extends MetadataType>, List<MetadataType>> metadata = null;

	/**
	 * @return type of dataset item
	 */
	abstract public int getDtype();

	@Override
	public Class<?> elementClass() {
		return AbstractDataset.elementClass(getDtype());
	}

	@Override
	public LazyDatasetBase clone() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
	
		LazyDatasetBase other = (LazyDatasetBase) obj;
		if (!Arrays.equals(shape, other.shape)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = getDtype() * 17 + getElementsPerItem();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		return hash;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int[] getShape() {
		return shape.clone();
	}

	@Override
	public int getRank() {
		return shape.length;
	}

	@Override
	public ILazyDataset squeeze() {
		return squeeze(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MetadataType> void setMetadata(T metadata) {
		if (metadata == null)
			return;

		List<T> ml = null;
		try {
			ml = (List<T>) getMetadata(metadata.getClass());
		} catch (Exception e) {
		}

		if (ml == null) {
			addMetadata(metadata);
		} else {
			ml.clear();
			ml.add(metadata);
		}
	}

	@Override
	public IMetaData getMetadata() {
		List<IMetaData> ml = null;
		try {
			ml = getMetadata(IMetaData.class);
		} catch (Exception e) {
		}

		return ml == null ? null : ml.get(0);
	}

	@Override
	public <T extends MetadataType> void addMetadata(T metadata) {
		if (metadata == null)
			return;

		if (this.metadata == null) {
			this.metadata = new HashMap<Class<? extends MetadataType>, List<MetadataType>>();
		}

		Class<? extends MetadataType> clazz = findMetadataTypeSubInterfaces(metadata.getClass());
		if (!this.metadata.containsKey(clazz)) {
			this.metadata.put(clazz, new ArrayList<MetadataType>());
		}
		this.metadata.get(clazz).add(metadata);
	}

	/**
	 * Dig down to interface (or class) that directly extends (or implements) MetadataType
	 * @param clazz
	 * @return sub-interface
	 */
	@SuppressWarnings("unchecked")
	static Class<? extends MetadataType> findMetadataTypeSubInterfaces(Class<? extends MetadataType> clazz) {
		for (Class<?> c : clazz.getInterfaces()) {
			if (c.equals(MetadataType.class))
				return clazz;
			if (MetadataType.class.isAssignableFrom(c)) {
				return findMetadataTypeSubInterfaces((Class<? extends MetadataType>) c);
			}
		}
		assert false; // should not be able to get here!!!
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MetadataType> List<T> getMetadata(Class<T> clazz) throws Exception {
		if (metadata == null)
			return null;

		if (clazz == null) {
			List<T> all = new ArrayList<T>();
			for (Class<? extends MetadataType> c : metadata.keySet()) {
				all.addAll((Collection<? extends T>) metadata.get(c));
			}
			return all;
		}

		return (List<T>) metadata.get(findMetadataTypeSubInterfaces(clazz));
	}
}
