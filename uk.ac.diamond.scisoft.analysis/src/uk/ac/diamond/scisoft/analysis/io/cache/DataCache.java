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

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;

/**
 * Class to encapsulate data caching for LoaderFactory
 */
public class DataCache<T> {


	private static final String NO_CACHING = "uk.ac.diamond.scisoft.analysis.io.nocaching";
	/**
	 * A caching mechanism using soft references. Soft references attempt to keep things
	 * in memory until the system is short on memory. Hashtable used because it is synchronized
	 * which should reduce chances of getting the wrong data for the key.
	 */
	private final Map<CacheKey, Reference<T>> cache;
	
	public DataCache() {
		cache = new Hashtable<CacheKey, Reference<T>>(89);
	}

	/**
	 * Used for synchronization.
	 */
	private final static Object LOCK = new Object();
	
	public void clear() {
		cache.clear();
	}
	
	public CacheKey createCacheKey(String path, boolean loadMeta) {
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
	public T getSoftReference(CacheKey key) {
		T o = getReference(key);
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
	public T getSoftReferenceWithMetadata(CacheKey key) {
		T o = getReference(key);
		if (o != null) return o;

		CacheKey k = findKeyWithMetadata(key);
		return k == null ? null : getReference(k);
	}


	/**
	 * May be null
	 * @param key
	 * @return the object referenced or null if it got garbaged or was not cached yet
	 */
	private T getReference(CacheKey key) {
		if (Boolean.getBoolean(NO_CACHING)) return null;
		synchronized (LOCK) {
			try {
		        final Reference<T> ref = cache.get(key);
		        if (ref == null) return null;
		        T got = ref.get();
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
	 * @return true if value has been stored
	 */
	public boolean recordSoftReference(CacheKey key, T value) {

		if (Boolean.getBoolean(NO_CACHING)) return false;
		synchronized (LOCK) {
			try {
				Reference<T> ref = Boolean.getBoolean("uk.ac.diamond.scisoft.analysis.io.weakcaching")
						         ? new WeakReference<T>(value)
						         : new SoftReference<T>(value);
				cache.put(key, ref);
				return true;
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
	public void cacheData(T holder) {
		cacheData(holder, 0);
	}

	/**
	 * Store data into cache
	 * 
     *
	 * @param holder
	 * @param imageNumber
	 */
	public void cacheData(T holder, int imageNumber) {
		final CacheKey key = new CacheKey();
		if (holder instanceof IDataHolder) {
			IDataHolder h = (IDataHolder)holder;
			key.setFilePath(h.getFilePath());
			key.setMetadata(h.getMetadata() != null);
		}
		key.setImageNumber(imageNumber);

		if (!recordSoftReference(key, holder))
			System.err.println("Loader factory failed to cache "+key);
	}

	/**
	 * Fetch data from cache
     *
	 * @param path
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @return data or null if not in cache
	 */
	public T fetchData(String path, boolean willLoadMetadata) {
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
	public T fetchData(String path, boolean willLoadMetadata, int imageNumber) {
		final CacheKey key = new CacheKey();
		key.setFilePath(path);
		key.setMetadata(willLoadMetadata);
		key.setImageNumber(imageNumber);

		final T cachedObject = getSoftReference(key);
		return cachedObject;
	}

}
