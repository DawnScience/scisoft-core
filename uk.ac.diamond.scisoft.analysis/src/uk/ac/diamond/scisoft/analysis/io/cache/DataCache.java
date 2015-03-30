/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.cache;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.io.IDataAnalysisObject;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;

/**
 * Class to encapsulate data caching for LoaderFactory
 */
public class DataCache {

	/**
	 * A caching mechanism using soft references. Soft references attempt to keep things
	 * in memory until the system is short on memory. Hashtable used because it is synchronized
	 * which should reduce chances of getting the wrong data for the key.
	 */
	private final Map<CacheKey, Reference<IDataAnalysisObject>> cache;
	
	public DataCache() {
		cache = new Hashtable<CacheKey, Reference<IDataAnalysisObject>>(89);
	}

	/**
	 * Used for synchronization.
	 */
	private final static Object LOCK = new Object();
	
	public void clear() {
		cache.clear();
	}
	
	public CacheKey createLoaderKey(String path, boolean loadMeta) {
		final CacheKey key = new CacheKey();
		key.setFilePath(path);
		key.setMetadata(loadMeta);
        return key;
	}

	/**
	 * May be null
	 * @param key
	 * @return the object referenced or null if it got garbaged or was not cached yet
	 */
	public Object getSoftReference(CacheKey key) {
		Object o = getReference(key);
		if (o != null) {
			return o;
		}
		if (key.hasMetadata()) { // wanted metadata but none there
			return null;
		}
		key.setMetadata(true); // try with unwanted metadata
		return getReference(key);
	}

	/**
	 * May be null
	 * @param key
	 * @return the object referenced or null if it got garbaged or was not cached yet
	 */
	public Object getSoftReferenceWithMetadata(CacheKey key) {
		Object o = getReference(key);
		if (o != null) return o;

		CacheKey k = findKeyWithMetadata(key);
		return k == null ? null : getReference(k);
	}


	private static final String NO_CACHING = "uk.ac.diamond.scisoft.analysis.io.nocaching";

	/**
	 * May be null
	 * @param key
	 * @return the object referenced or null if it got garbaged or was not cached yet
	 */
	private IDataAnalysisObject getReference(CacheKey key) {
		if (Boolean.getBoolean(NO_CACHING)) return null;
		synchronized (LOCK) {
			try {
		        final Reference<IDataAnalysisObject> ref = cache.get(key);
		        if (ref == null) return null;
		        IDataAnalysisObject got = ref.get();
		        return got;
			} catch (Throwable ne) {
				return null;
			}
		}
	}

	private CacheKey findKeyWithMetadata(CacheKey key) {
		if (Boolean.getBoolean(NO_CACHING)) return null;
		synchronized (LOCK) {
			for (CacheKey k : cache.keySet()) {
				if (k.isSameFile(key) && k.hasMetadata()) {
					return k;
				}
			}
			return null;
		}

	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return true if another value has been replaced.
	 */
	public boolean recordSoftReference(CacheKey key, IDataAnalysisObject value) {
		
		if (Boolean.getBoolean(NO_CACHING)) return false;
		synchronized (LOCK) {
			try {
				Reference<IDataAnalysisObject> ref = Boolean.getBoolean("uk.ac.diamond.scisoft.analysis.io.weakcaching")
						                           ? new WeakReference<IDataAnalysisObject>(value)
						                           : new SoftReference<IDataAnalysisObject>(value);
				return cache.put(key, ref)!=null;
			} catch (Throwable ne) {
				return false;
			}
		}
	}

	

	/**
	 * Store data into cache
     *
	 * @param holder
	 */
	public void cacheData(IDataHolder holder) {
		cacheData(holder, 0);
	}

	/**
	 * Store data into cache
	 * 
     *
	 * @param holder
	 * @param imageNumber
	 */
	public void cacheData(IDataHolder holder, int imageNumber) {
		final CacheKey key = new CacheKey();
		key.setFilePath(holder.getFilePath());
		key.setMetadata(holder.getMetadata() != null);
		key.setImageNumber(imageNumber);

		if (!recordSoftReference(key, holder))
			System.err.println("Loader factory failed to cache "+holder.getFilePath());
	}

	/**
	 * Fetch data from cache
     *
	 * @param path
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @return data or null if not in cache
	 */
	public IDataHolder fetchData(String path, boolean willLoadMetadata) {
		return fetchData(path, willLoadMetadata, 0);
	}

	/**
	 * Fetch data from cache
     *
	 * @param path
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @param imageNumber
	 * @return data or null if not in cache
	 */
	public IDataHolder fetchData(String path, boolean willLoadMetadata, int imageNumber) {
		final CacheKey key = new CacheKey();
		key.setFilePath(path);
		key.setMetadata(willLoadMetadata);
		key.setImageNumber(imageNumber);

		final Object cachedObject = getSoftReference(key);
		return cachedObject instanceof IDataHolder ? (IDataHolder) cachedObject : null;
	}

}
