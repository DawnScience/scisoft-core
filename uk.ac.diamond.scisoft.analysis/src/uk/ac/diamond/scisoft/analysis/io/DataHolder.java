/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IMetadataProvider;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;
import org.eclipse.january.metadata.MetadataType;

/**
 * This class is to marshal all the data for the purpose of loading from or saving to a file
 * directly or via a ScanFileHolder.
 * <p>
 * This is designed to take in any dataset obeying the IDataset interface but output an
 * object that is a subclass of Dataset - the dataset will be converted if necessary.
 * 
 * This implementation does not permit duplicated names.
 */
public class DataHolder implements IMetadataProvider, IDataHolder, Serializable {
	private static final long serialVersionUID = 699494887322875262L;

	private static final DeprecationLogger logger = DeprecationLogger.getLogger(DataHolder.class);

	/**
	 * List containing all the name and data pairs (to be) loaded.
	 */
	private LinkedHashMap<String, ILazyDataset> nameDataMappings;

	/**
	 * List containing metadata
	 */
	private IMetadata metadata;

	/**
	 * Loader class string
	 */
	private String loaderClass;
	
	/**
	 * The path to the original file loaded (if any)
	 */
	private String filePath;

	private Tree tree;

	/**
	 * This must create the three objects which will be put into the ScanFileHolder
	 */
	public DataHolder() {
		this(new Metadata());
	}

	/**
	 * Used to hold metadata
	 * @param meta
	 */
	public DataHolder(IMetadata meta) {
		nameDataMappings = new LinkedHashMap<String, ILazyDataset>();
		metadata = meta;
	}

	/**
	 * The current data as a map of lazy datasets.
	 * @return map of lazy datasets with keys from their corresponding names
	 */
	@Override
	public Map<String, ILazyDataset> toLazyMap() {
		return new LinkedHashMap<String, ILazyDataset>(nameDataMappings);
	}

	/**
	 * Does not clone the meta data.
	 * @return shallow copy of DataHolder
	 */
	@Override
	public IDataHolder clone() {
		DataHolder ret = new DataHolder();
		ret.nameDataMappings.putAll(nameDataMappings);
		ret.metadata    = metadata;
		ret.filePath    = filePath;
		ret.loaderClass = loaderClass;
		ret.tree = tree;
		return ret;
	}

	/**
	 * Adds an entry to the holder
	 * 
	 * Replaces any datasets of the same name already existing.
	 *  
	 * @param name
	 *            the name of the dataset which is to be added
	 * @param dataset
	 *            the actual data of the dataset
	 * @return true if this replaces an existing entry
	 */
	@Override
	public boolean addDataset(String name, ILazyDataset dataset) {
		boolean ret = nameDataMappings.containsKey(name);
		nameDataMappings.put(name, dataset);
		return ret;
	}

	/**
	 * Adds an entry with metadata
	 * 
	 * Replaces any datasets of the same name already existing.
	 * 
	 * @param name
	 *            the name of the dataset which is to be added
	 * @param dataset
	 *            the actual data of the dataset.
	 * @param metadata
	 *            the metadata that is associated with the dataset
	 * @return true if this replaces an existing entry
	 */
	public boolean addDataset(String name, ILazyDataset dataset, IMetadata metadata) {
		boolean ret = addDataset(name, dataset);
		this.metadata = metadata;
		return ret;
	}
	
	/**
	 * Add a ImetaData object
	 * @param metadata which is an object implementing IMetadata
	 */
	@Override
	public void setMetadata(IMetadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * @return an object implementing IMetadata
	 */
	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends MetadataType> List<T> getMetadata(Class<T> clazz) throws MetadataException {
		if (IMetadata.class.isAssignableFrom(clazz)) {
			List<T> result = new ArrayList<>();
			result.add((T) getMetadata());
			return result;
		}
		throw new MetadataException("getMetadata(clazz) does not currently support anything other than IMetadata");
		// If it should only support this, simply return null here, otherwise implement the method fully
	}
	
	@Override
	public <T extends MetadataType> T getFirstMetadata(Class<T> clazz) {
		try {
			List<T> ml = getMetadata(clazz);
			if (ml == null) return null;
			return ml.isEmpty() ? null : ml.get(0);
		} catch (Exception e) {
			logger.error("Get metadata failed!",e);
		}

		return null;
	}

	/**
	 * @return List of (potential lazy) datasets
	 */
	@Override
	public List<ILazyDataset> getList() {
		return new ArrayList<ILazyDataset>(nameDataMappings.values());
	}

	/**
	 * This pulls out the dataset which could be lazy, maintaining its laziness.
	 * @param index
	 * @return dataset with given index in holder or null if it does not exist
	 */
	@Override
	public ILazyDataset getLazyDataset(int index) {
		final String key = getName(index);
		if (key == null ) return null;
 		return nameDataMappings.get(key);
	}

	/**
	 * This pulls out the dataset which could be lazy, maintaining its laziness.
	 * @param name
	 * @return dataset with given name or null if it does not exist
	 */
	@Override
	public ILazyDataset getLazyDataset(String name) {
		return nameDataMappings.get(name);
	}

	/**
	 * Set a generic dataset with given name
	 * @param name
	 * @param dataset
	 */
	public void setDataset(String name, ILazyDataset dataset) {
		nameDataMappings.put(name, dataset);
	}

	/**
	 * This does not retrieve lazy datasets.
	 * @param index
	 * @return Generic dataset with given index in holder or null if it is lazy or does not exist
	 */
	@Override
	public Dataset getDataset(int index) {
		ILazyDataset lazy = getLazyDataset(index);
		return lazy instanceof IDataset ? DatasetUtils.convertToDataset((IDataset) lazy) : null;
	}

	/**
	 * This does not retrieve lazy datasets.
	 * @param name
	 * @return Generic dataset with given name or null if it is lazy or does not exist
	 */
	@Override
	public Dataset getDataset(String name) {
		ILazyDataset lazy = getLazyDataset(name);
		return lazy instanceof IDataset ? DatasetUtils.convertToDataset((IDataset) lazy) : null;
	}

	/**
	 * @param name
	 * @return true if data holder contains name 
	 * @see java.util.List#contains(Object)
	 */
	@Override
	public boolean contains(String name) {
		return nameDataMappings.containsKey(name);
	}

	/**
	 * @param name
	 * @return index of dataset with given name
	 * @see java.util.List#indexOf(Object)
	 */
	public int indexOf(String name) {
		List<String> keys = new ArrayList<String>(nameDataMappings.keySet());
		return keys.indexOf(name);
	}

	/**
	 * @return Array of dataset names
	 */
	@Override
	public String[] getNames() {
		return nameDataMappings.keySet().toArray(new String[nameDataMappings.size()]);
	}

	/**
	 * @param index
	 * @return Dataset name at given index
	 */
	@Override
	public String getName(final int index) {
		if (index < 0 || index >= size()) {
			return null;
		}
		List<String> keys = new ArrayList<String>(nameDataMappings.keySet());
		return keys.get(index);
	}

	/**
	 * @return Number of datasets
	 */
	@Override
	public int size() {
		return nameDataMappings.size();
	}

	/**
	 * @return Number of unique dataset names
	 */
	@Deprecated(since="Dawn 2.3")
	@Override
	public int namesSize() {
		logger.deprecatedMethod("namesSize()", null, "size()");
		return size();
	}

	/**
	 * Clear list of names and datasets
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		nameDataMappings.clear();
		metadata = null;
		tree = null;
	}

	/**
	 * Remove name and dataset at index
	 * @param index
	 * @see java.util.List#remove(int)
	 */
	public void remove(int index) {
		final String key = getName(index);
		if (key != null)
			nameDataMappings.remove(key);
	}

	/**
	 * @return class of file loader or null if class is not found by class loader
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends IFileLoader> getLoaderClass() {
		if (loaderClass == null)
			return null;

		try {
			return (Class<? extends AbstractFileLoader>) Class.forName(loaderClass);
		} catch (ClassNotFoundException e) {
			logger.error("No class found for {}", loaderClass, e);
		}
		return null;
	}

	@Override
	public void setLoaderClass(Class<? extends IFileLoader> clazz) {
		loaderClass = clazz.getName();
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
	public void setTree(Tree tree) {
		this.tree = tree;
	}

	@Override
	public Tree getTree() {
		return tree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((loaderClass == null) ? 0 : loaderClass.hashCode());
		result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
		result = prime * result + ((nameDataMappings == null) ? 0 : nameDataMappings.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataHolder other = (DataHolder) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (loaderClass == null) {
			if (other.loaderClass != null)
				return false;
		} else if (!loaderClass.equals(other.loaderClass))
			return false;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		if (nameDataMappings == null) {
			if (other.nameDataMappings != null)
				return false;
		} else if (!nameDataMappings.equals(other.nameDataMappings))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nameDataMappings.toString();
	}
	
	@Override
	public Map<String, int[]> getDatasetShapes() {
		Map<String, int[]> output = new LinkedHashMap<>();
		
		for (Entry<String, ILazyDataset> e : nameDataMappings.entrySet()) {
			output.put(e.getKey(), e.getValue().getShape());
		}
		
		return output;
	}
}
