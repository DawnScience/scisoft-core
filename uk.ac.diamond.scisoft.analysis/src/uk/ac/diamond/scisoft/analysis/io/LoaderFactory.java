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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.io.IMetaLoader;
import org.eclipse.january.metadata.IMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.cache.CacheKey;
import uk.ac.diamond.scisoft.analysis.io.cache.DataCache;
import uk.ac.diamond.scisoft.analysis.utils.FileUtils;

/**
 * A class which gives a single point of entry to loading data files
 * into the system.
 * 
 * In order to work with the factory a loader must have:
 * 1. a no argument constructor
 * 2. a setFile(...) method with a string path argument
 * 
 * *OR*
 * 
 * A constructor with a string argument.
 * 
 * In order to work well the loader should implement:
 * 
 * 1. IMetaLoader - this interface marks it possible to extract dataset names and other meta
 *    data without reading all the file data into memory.
 *    
 * 2. IDataSetLoader to load a single data set without loading the rest of the file.
 * 
 * see LoaderFactoryExtensions which boots up the extensions from reading the extension points.
 * 
 * This class is going to be Deprecated please use ILoaderService where possible.
 * See org.eclipse.dawnsci.plotting.examples.Examples which receives OSGi services, including ILoaderService. 
 * 
 */
public class LoaderFactory {

	/**
	 * Logger for detailing any non-fatal problems (sorry but there are some)
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoaderFactory.class);

	/**
	 * DO NOT USE this constructor. It is used by OSGi for reflecting/instantiating the object
	 * Ideally use ILoaderService available from OSGi but the static methods are still supported
	 * on LoaderFactory.
	 */
	@Deprecated
	public LoaderFactory() {
		
	}

	private static final Map<String, CachedListIterator<Class<? extends IFileLoader>>> LOADERS;
	private static final Map<String, Class<? extends InputStream>>     UNZIPPERS;
	private static final DataCache<IDataHolder> dataCache;
	private static final Set<String> IGNORE_EXTS;

	/**
	 * 
	 * Loaders can be registered at run time using registerLoader(...)
	 * 
	 * There is no need for an extension point now and no dependency on eclipse.
	 * Instead an OSGI service contributing the loaders is looked for.
	 * 
	 * To change a loader programmatically (not advised)
	 * 
	 * 1. LoaderFactory.getSupportedExtensions();
	 * 2. LoaderFactory.clearLoader("h5");
	 * 3. LoaderFactory.registerLoader("h5", MyH5ClassThatIsBetter.class);
	 * 
	 */
	static {
		
		LOADERS   = new HashMap<String, CachedListIterator<Class<? extends IFileLoader>>>(19);
		UNZIPPERS = new HashMap<String, Class<? extends InputStream>>(3);
		dataCache = new DataCache<IDataHolder>();
		IGNORE_EXTS   = new HashSet<String>(3);
		
		registerLoaderWithException("npy", NumPyFileLoader.class);
		registerLoaderWithException("img", ADSCImageLoader.class);
		registerLoaderWithException("osc", RAxisImageLoader.class);
		registerLoaderWithException("cbf", CBFLoader.class);
		registerLoaderWithException("img", CrysalisLoader.class);
		registerLoaderWithException("tif", PixiumLoader.class);
		registerLoaderWithException("jpeg", JPEGLoader.class);
		registerLoaderWithException("jpg", JPEGLoader.class);
		registerLoaderWithException("gif", GIFLoader.class);
		registerLoaderWithException("mccd", MARLoader.class);
		registerLoaderWithException("mar3450", MAR345Loader.class);
		registerLoaderWithException("pck3450", MAR345Loader.class);
		registerLoaderWithException("mrc", MRCImageStackLoader.class);

		// There is some disagreement about the proper nexus/hdf5
		// file extension at different facilities
		registerLoaderWithException("nxs", NexusHDF5Loader.class);
		registerLoaderWithException("nexus", NexusHDF5Loader.class);
		registerLoaderWithException("h5", HDF5Loader.class);
		registerLoaderWithException("hdf", HDF5Loader.class);
		registerLoaderWithException("hdf5", HDF5Loader.class);
		registerLoaderWithException("hd5", HDF5Loader.class);
		registerLoaderWithException("mat", HDF5Loader.class);
		registerLoaderWithException("nc", HDF5Loader.class);

		registerLoaderWithException("tif", PilatusTiffLoader.class);
		registerLoaderWithException("png", PNGLoader.class);
		registerLoaderWithException("raw", RawBinaryLoader.class);
		registerLoaderWithException("raw", DatLoader.class);
		registerLoaderWithException("srs", ExtendedSRSLoader.class);
		registerLoaderWithException("srs", SRSLoader.class);
		registerLoaderWithException("dat", DatLoader.class);
		registerLoaderWithException("xy", DatLoader.class);
		registerLoaderWithException("xye", DatLoader.class);
		registerLoaderWithException("dat", ExtendedSRSLoader.class);
		registerLoaderWithException("dat", ExtendedSRSLoader2.class);
		registerLoaderWithException("dat", SRSLoader.class);
		registerLoaderWithException("dat", UViewDatLoader.class);
		registerLoaderWithException("dat", ColumnTextLoader.class);
		registerLoaderWithException("csv", CSVLoader.class);
		registerLoaderWithException("rgb", RGBTextLoader.class);
		registerLoaderWithException("txt", DatLoader.class);
		registerLoaderWithException("txt", SRSLoader.class);
		registerLoaderWithException("mca", MCALoader.class);
		registerLoaderWithException("mca", DatLoader.class);
		registerLoaderWithException("mca", SRSLoader.class);
		registerLoaderWithException("tif", TIFFImageLoader.class);
		registerLoaderWithException("tiff", TIFFImageLoader.class);
		registerLoaderWithException("zip", XMapLoader.class);
		registerLoaderWithException("edf", PilatusEdfLoader.class);
		registerLoaderWithException("pgm", PgmLoader.class);
		registerLoaderWithException("pnm", JavaImageLoader.class);
		registerLoaderWithException("ppm", JavaImageLoader.class);
		registerLoaderWithException("pbm", JavaImageLoader.class);
		registerLoaderWithException("f2d", Fit2DLoader.class);
		registerLoaderWithException("msk", Fit2DMaskLoader.class);
		registerLoaderWithException("mib", MerlinLoader.class);
		registerLoaderWithException("bmp", BitmapLoader.class);
		registerLoaderWithException("spe", SpeLoader.class);
		registerLoaderWithException("xmso", XMSOLoader.class);
		registerLoaderWithException("alba", AlbaLinkFileLoader.class);
		registerLoaderWithException("dawn", DAWNLinkLoader.class);
		registerLoaderWithException("jcpds", JCPDSLoader.class);
		registerLoaderWithException("xrmc", XRMCLoader.class);
		registerLoaderWithException("xdi", XDILoader.class);

		registerUnzip("gz", GZIPInputStream.class);
		registerUnzip("zip", ZipInputStream.class);
		registerUnzip("bz2", CBZip2InputStream.class);

		// Ignore these extensions for loading dataset or metadata
		// but can be overridden by the registered loaders
		IGNORE_EXTS.add("py");
		IGNORE_EXTS.add("exe");
	}

	/**
	 * This method is used to define the supported extensions that the LoaderFactory
	 * already knows about.
	 * 
	 * NOTE that is can be called from Jython. It is probably not used in the GDA/SDA 
	 * code based that much but external code like Jython can 
	 * 
	 * @return collection of extensions.
	 */
	public static Collection<String> getSupportedExtensions() {
		return LOADERS.keySet();
	}


	/**
	 * Call to load any file type into memory. By default loads all data sets, therefore
	 * could take a **long time**.
	 * 
	 * In addition to find out if a given file loads with a particular loader - it actually 
	 * LOADS it. 
	 * 
	 * Therefore it can take a while to run depending on how quickly the loader
	 * fails. Also if there are many loaders called in turn, much memory could be consumed and
	 * discarded. For this reason the registration process requires a file extension and tries 
	 * all the loaders for a given extension if the extension is registered already. 
	 * Otherwise it tries all loaders - in no particular order.
	 * 
	 * @param path
	 * @return DataHolder
	 * @throws Exception
	 */
	public static IDataHolder getData(final String path) throws Exception {
		return getData(path, true, new IMonitor.Stub() {
			
			@Override
			public void worked(int amount) {
				// Deliberately empty
			}
			
			@Override
			public boolean isCancelled() {
				return false;
			}
		});
	}
	
	/**
	 * Call to load any file type into memory. By default loads all data sets, therefore
	 * could take a **long time**.
	 * 
	 * In addition to find out if a given file loads with a particular loader - it actually 
	 * LOADS it. 
	 * 
	 * Therefore it can take a while to run depending on how quickly the loader
	 * fails. Also if there are many loaders called in turn, much memory could be consumed and
	 * discarded. For this reason the registration process requires a file extension and tries 
	 * all the loaders for a given extension if the extension is registered already. 
	 * Otherwise it tries all loaders - in no particular order.
	 * 
	 * @param path
	 * @param mon
	 * @return DataHolder
	 * @throws Exception
	 */
	public static IDataHolder getData(final String path, final IMonitor mon) throws Exception {
		return getData(path, true, mon);
	}

	/**
	 * Call to load any file type into memory. By default loads all data sets, therefore
	 * could take a **long time**.
	 * 
	 * In addition to find out if a given file loads with a particular loader - it actually 
	 * LOADS it. 
	 * 
	 * Therefore it can take a while to run depending on how quickly the loader
	 * fails. Also if there are many loaders called in turn, much memory could be consumed and
	 * discarded. For this reason the registration process requires a file extension and tries 
	 * all the loaders for a given extension if the extension is registered already. 
	 * Otherwise it tries all loaders - in no particular order.
	 * 
	 * @param path to file
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @param mon
	 * @return DataHolder
	 * @throws Exception
	 */
	public static IDataHolder getData(final String path, final boolean willLoadMetadata, final IMonitor mon) throws Exception {
		return getData(path, willLoadMetadata, false, false, mon);
	}

	/**
	 * Call to load any file type into memory. By default loads all data sets, therefore
	 * could take a **long time**.
	 * 
	 * In addition to find out if a given file loads with a particular loader - it actually 
	 * LOADS it. 
	 * 
	 * Therefore it can take a while to run depending on how quickly the loader
	 * fails. Also if there are many loaders called in turn, much memory could be consumed and
	 * discarded. For this reason the registration process requires a file extension and tries 
	 * all the loaders for a given extension if the extension is registered already. 
	 * Otherwise it tries all loaders - in no particular order.
	 * 
	 *   *synchronized* is REQUIRED because multiple threads load data simultaneously and without
	 *   a synchronized you can get data loaded twice which is SLOW.
	 * 
	 * @param path to file
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @param loadImageStacks if true, find and load images in the same directory as a stack
	 * @param mon
	 * @return DataHolder
	 * @throws Exception
	 */
	public static IDataHolder getData(final String   path, 
									  final boolean  willLoadMetadata, 
									  final boolean  loadImageStacks, 
									  final IMonitor mon) throws Exception {
		return getData(path, willLoadMetadata, loadImageStacks, false, mon);
	}

	/**
	 * Call to load any file type into memory. By default loads all data sets, therefore
	 * could take a **long time**.
	 * 
	 * In addition to find out if a given file loads with a particular loader - it actually 
	 * LOADS it. 
	 * 
	 * Therefore it can take a while to run depending on how quickly the loader
	 * fails. Also if there are many loaders called in turn, much memory could be consumed and
	 * discarded. For this reason the registration process requires a file extension and tries 
	 * all the loaders for a given extension if the extension is registered already. 
	 * Otherwise it tries all loaders - in no particular order.
	 * 
	 *   *synchronized* is REQUIRED because multiple threads load data simultaneously and without
	 *   a synchronized you can get data loaded twice which is SLOW.
	 * 
	 * @param path to file
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @param loadImageStacks if true, find and load images in the same directory as a stack
	 * @param lazily if true, <b>all</b> datasets in the data holder will be lazy otherwise the holder
	 * may contain non-lazy datasets
	 * @param mon
	 * @return DataHolder
	 * @throws Exception
	 */
	public static /*THIS IS REQUIRED:*/ synchronized  IDataHolder getData(String path,
																		  final boolean willLoadMetadata, 
																		  final boolean loadImageStacks, 
																		  final boolean lazily, 
																		  final IMonitor mon) throws Exception {

		if (path.toLowerCase().startsWith("http")) {
			throw new Exception("Data from URL not yet supported!");
		}
		path = Utils.translateDLSFilePath(path);
		final File file = new File(path);
		if (!file.exists()) {
			new File(file.getParent()).list();
			if (!file.exists()) throw new FileNotFoundException(path);
		}
		if (file.isFile()) {
			return getFileData(path, willLoadMetadata, loadImageStacks, lazily, mon);
		} else if (file.isDirectory()) {
			final IDataHolder holder = new DataHolder();
			final Map<String, ILazyDataset> stack = getImageStack(file, holder, mon, LOADERS.keySet());
			if (stack!=null) for (String name : stack.keySet()) holder.addDataset(name, stack.get(name));
			return holder;
		}
		throw new Exception(path + " is not valid!");
	}

	private static IDataHolder getFileData(final String path, final boolean willLoadMetadata,
			final boolean loadImageStacks, final boolean lazily, final IMonitor mon) throws Exception {

		// IMPORTANT: DO NOT USE loadImageStacks in Key. 
		// Instead when loadImageStacks=true, we add the stack to the already
		// cached data. So reducing the cache size.
		final CacheKey key = dataCache.createCacheKey(path, willLoadMetadata);
		// END IMPORTANT

		final Object cachedObject = dataCache.getSoftReference(key);
		IDataHolder holder = null;
		if (cachedObject!=null && cachedObject instanceof IDataHolder) holder = (IDataHolder)cachedObject;

		if (holder==null) { // try and load it
			final Iterator<Class<? extends IFileLoader>> it = getIterator(path);
			if (it == null) return null;
	
			// Currently this method simply cycles through all loaders.
			// When it finds one which does not give an exception on loading it
			// returns the data from this loader.
			while (it.hasNext()) {
				final Class<? extends IFileLoader> clazz = it.next();
				IFileLoader loader = null;
				try {
					loader = getLoader(clazz, path);
				} catch (Throwable t) {
					// do nothing
				}
				if (loader == null) {
					continue;
				}
				loader.setLoadMetadata(willLoadMetadata);
				loader.setLoadAllLazily(lazily);
				try {
					// NOTE Assumes loader fails quickly and nicely
					// if given the wrong file. If a loader does not
					// do this it should not be registered with LoaderFactory
					holder = loader.loadFile(mon);
					holder.setLoaderClass(clazz);
					holder.setFilePath(path);

					if (!lazily) {
						key.setMetadata(holder.getMetadata()!=null);
						boolean cached = dataCache.recordSoftReference(key, holder);
						if (!cached) System.err.println("Loader factory failed to cache "+path);
					}
					break;
					
				} catch (OutOfMemoryError ome) {
					logger.error("There was not enough memory to load {}", path);
					throw new ScanFileHolderException("Out of memory in loader factory", ome);
				} catch (Throwable ne) {
					logger.trace("Loader {} caused {}", loader, ne);
					continue;
				}
			}
		}
		
		// For images, we can put another item in the data holder
		// which represents the stack of other images in the same directory.
		try {
			if (loadImageStacks && holder!=null) {

				if (holder.size()==1 && holder.getLazyDataset(0).getRank()==2 && !isH5(path)) {
					final Map<String,ILazyDataset> stack = getImageStack(path, holder, mon);
					if (stack!=null) for (String name : stack.keySet()) holder.addDataset(name, stack.get(name));
				}

			}
		} catch (Throwable ne) { // It is not a fatal error to fail to load an image stack.
			logger.error("Cannot load image stack!", ne);
		}
		return holder;
	}

	
	/**
	 * Call to load file into memory with specific loader class
	 * 
	 *   *synchronized* is REQUIRED because multiple threads load data simultaneously and without
	 *   a synchronized you can get data loaded twice which is SLOW.
     *
	 * @param clazz loader class
	 * @param path to file
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @param mon
	 * @return data holder (can be null)
	 * @throws ScanFileHolderException
	 */
	public static /*THIS IS REQUIRED:*/ synchronized IDataHolder getData(Class<? extends IFileLoader> clazz, 
						                         String path, 
			                                     boolean willLoadMetadata, 
			                                     IMonitor mon) throws Exception {
		path = Utils.translateDLSFilePath(path);
		if (!(new File(path)).exists()) throw new FileNotFoundException(path);

		// IMPORTANT: DO NOT USE loadImageStacks in Key. 
		// Instead when loadImageStacks=true, we add the stack to the already
		// cached data. So reducing the cache size.
		final CacheKey key = dataCache.createCacheKey(path, willLoadMetadata);
		// END IMPORTANT

		final Object cachedObject = dataCache.getSoftReference(key);
		IDataHolder holder = null;
		if (cachedObject!=null && cachedObject instanceof IDataHolder) holder = (IDataHolder)cachedObject;
		if (holder!=null) return holder;

		IFileLoader loader;
		try {
			loader = getLoader(clazz, path);
		} catch (Exception e) {
			logger.error("Cannot create loader", e);
			throw new ScanFileHolderException("Cannot create loader", e);
		}
		if (loader == null) {
			logger.error("Cannot create loader");
			throw new ScanFileHolderException("Cannot create loader");
		}

		loader.setLoadMetadata(willLoadMetadata);
		try {
			holder = loader.loadFile(mon);
			holder.setLoaderClass(clazz);
			holder.setFilePath(path);
			
			key.setMetadata(holder.getMetadata()!=null);
			boolean cached = dataCache.recordSoftReference(key, holder);
			if (!cached) System.err.println("Loader factory failed to cache " + path);
			return holder;
			
		} catch (OutOfMemoryError ome) {
			logger.error("There was not enough memory to load {}", path);
			throw new ScanFileHolderException("Out of memory in loader factory", ome);
		} catch (Throwable ne) {
			logger.trace("Loader {} caused {}", loader, ne);
			throw new ScanFileHolderException("Loader error", ne);
		}
	}

	/**
	 * Store data into cache
     *
	 * @param holder
	 */
	public static void cacheData(IDataHolder holder) {
		dataCache.cacheData(holder);
	}

	/**
	 * Store data into cache
	 * 
     *
	 * @param holder
	 * @param imageNumber
	 */
	public static void cacheData(IDataHolder holder, int imageNumber) {
		dataCache.cacheData(holder, imageNumber);
	}

	/**
	 * Fetch data from cache
     *
	 * @param path
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @return data or null if not in cache
	 */
	public static IDataHolder fetchData(String path, boolean willLoadMetadata) {
		path = Utils.translateDLSFilePath(path);
		return dataCache.fetchData(path, willLoadMetadata);
	}

	/**
	 * Fetch data from cache
     *
	 * @param path
	 * @param willLoadMetadata dictates whether metadata is not loaded (if possible)
	 * @param imageNumber
	 * @return data or null if not in cache
	 */
	public static IDataHolder fetchData(String path, boolean willLoadMetadata, int imageNumber) {
		path = Utils.translateDLSFilePath(path);
		return dataCache.fetchData(path, willLoadMetadata, imageNumber);
	}

	private static String stackExpression = "(.+)_?(\\d+)";
	
	public static String getStackExpression() {
		return stackExpression;
	}


	public static void setStackExpression(String stackExpression) {
		LoaderFactory.stackExpression = stackExpression;
	}


	/**
	 * This method can be used to load an image stack of other images in the same directory.
	 * 
	 * @param filePath - to one of the images in the stack.
	 * @param holder
	 * @param mon
	 * @return and image stack for 
	 * @throws Exception
	 */
	public static final Map<String,ILazyDataset> getImageStack(String filePath, IDataHolder holder, IMonitor mon) throws Exception {
		
		if (filePath==null) return null;

		filePath = Utils.translateDLSFilePath(filePath);
		final File   file  = new File(filePath);
		final String ext   = FileUtils.getFileExtension(file.getName());
		final File   dir   = file.getParentFile();

		return getImageStack(dir, holder, mon, ext);
	}
	
	private static final Map<String,ILazyDataset> getImageStack(final File dir, IDataHolder holder, IMonitor mon, String... extensions) throws Exception {
        return getImageStack(dir, holder, mon, Arrays.asList(extensions));
	}
	
	private static final Map<String,ILazyDataset> getImageStack(final File dir, IDataHolder holder, IMonitor mon, Collection<String> extensions) throws Exception {

		
		final Map<String, List<String>> imageFilenames = new TreeMap<String, List<String>>();
		imageFilenames.put("Image Stack", new ArrayList<String>(31));
		
		String patternPrefix = getStackExpression();
		
		if (dir.isDirectory()) { // Which it should be...
			
			final List<String> files = Arrays.asList(dir.list());
			Collections.sort(files, new SortNatural<String>(true));
			
			for (String fName : files) {
				
				final String ext   = FileUtils.getFileExtension(fName);
				if (extensions.contains(ext)) {
					
					final File f = new File(dir,fName);
					String name  = "Image Stack";
					
					// Name will be something like 35873_M3S15_1_0001.cbf
					// A string '35873_M3S15_1_' followed by a 4-digit number, followed by the file extension.
					Pattern pattern = Pattern.compile(patternPrefix+"\\."+ext);
					Matcher matcher = pattern.matcher(fName);
					if (matcher.matches()) {
						name = matcher.group(1);
						if (!imageFilenames.containsKey(name)) {
							imageFilenames.put(name, new ArrayList<String>(31));
						}
					}
					imageFilenames.get(name).add(f.getAbsolutePath());
				}
			}
		}
		
		if (imageFilenames.size() > 0) {
			
			Map<String,ILazyDataset> ret = new TreeMap<String,ILazyDataset>();
			for (String name : imageFilenames.keySet()) {
				final List<String> files = imageFilenames.get(name);
				
				if (files==null || files.size()<2) continue;
	 			ImageStackLoader loader = new ImageStackLoader(files, holder, mon);
				ILazyDataset lazyDataset = loader.createLazyDataset(name);
				ret.put(name, lazyDataset);
			}
			
			if (ret.size()>0) return ret;
		}
		return null;
	}


	/**
	 * Call to load any file type into memory. If a loader implements IMetaLoader will
	 * use this fast method to avoid loading the entire file into memory. If the loader
	 * does not implement IMetaLoader it will return null. Then you should use getData(...) 
	 * to load the entire file.
	 * 
	 * 
	 * @param path
	 * @param mon
	 * @return IMetadata
	 * @throws Exception
	 */
	public static synchronized IMetadata getMetadata(String path, final IMonitor mon) throws Exception {

		path = Utils.translateDLSFilePath(path);
		if (!(new File(path)).exists()) throw new FileNotFoundException(path);
		final CacheKey key = dataCache.createCacheKey(path, true);
		
		// Look for other data with the meta data
		IDataHolder cachedObject = dataCache.getSoftReferenceWithMetadata(key);
		if (cachedObject!=null) {
			IMetadata meta = cachedObject.getMetadata();
			if (meta!=null) return meta;
			logger.warn("Cached object is not a metadata object or contain one");
		}
		
		// Look for cached metadataonly record
		key.setMetadataOnly(true);
		cachedObject = dataCache.getSoftReferenceWithMetadata(key);
		if (cachedObject!=null) {
			IMetadata meta = cachedObject.getMetadata();
			if (meta!=null) return meta;
			logger.warn("Cached object is not a metadata object or contain one");
		}
		
		final Iterator<Class<? extends IFileLoader>> it = getIterator(path);
		if (it == null) return null;

		// Currently this method simply cycles through all loaders.
		// When it finds one which does not give an exception on loading, it
		// returns the data from this loader.
		while (it.hasNext()) {
			final Class<? extends IFileLoader> clazz = it.next();
			final IFileLoader loader = getLoader(clazz, path);
			if (!IMetaLoader.class.isInstance(loader)) continue;

			try {
				// NOTE Assumes loader fails quickly and nicely
				// if given the wrong file. If a loader does not
				// do this, it should not be registered with LoaderFactory
				((IMetaLoader) loader).loadMetadata(mon);
				IMetadata meta = ((IMetaLoader) loader).getMetadata();
				key.setMetadataOnly(true); // We are definitely recording only metadata with this step.
				dataCache.recordSoftReference(key, new DataHolder(meta));
				return meta;
			} catch (Throwable ne) {
				//logger.trace("Cannot load nexus meta data", ne);
				logger.trace("Loader {} caused {}", loader, ne);
				continue;
			}
		}

		return null;
	}

	/**
	 * Loads a single dataset by loading the whole data holder and asking for the dataset
	 * by name. Loaders should load things properly to ILazyDatasets and then this method
	 * will take from the data holder the set by name. This uses caching of the data holder
	 * if the data has been previously loaded into a DataHolder.
	 * 
	 * NOTE LazyDatasets will be loaded into memory by this method. To avoid this use:
	 * <code>
	 * IDataHolder holder = LoaderFactory.getData(...)
	 * ILazyDataset lz    = holder.getLazyDataset(...)
	 * <code>
	 * 
	 * Now the ILazyDataset is available rather than loading all into memory.
	 * If you use this method all the data of the dataset will be loaded to memory.
	 * 
	 * @param path
	 * @param mon
	 * @return IDataset
	 * @throws Exception
	 */
	public static IDataset getDataSet(final String path, final String name, final IMonitor mon) throws Exception {

		// Makes the cache the DataHolder
        final IDataHolder holder = getData(path, true, mon);
        if (holder == null) return null;
        IDataset data = holder.getDataset(name);

        if (data == null) { // We try to load the data from the ILazyDataset
        	final ILazyDataset lz = holder.getLazyDataset(name);
        	if (lz == null) return null;
        	data = lz.getSlice();
        	holder.addDataset(name, data); // We just loaded the data, cache it
        }
        return data;
	}

	/**
	 * Returns true if a given file is an IMetadata and able to load metadata without the data
	 * 
	 * @param path
	 * @return true if can load metadata without all data being loaded.
	 */
	public static synchronized boolean isMetaLoader(final String path) throws Exception {

		return isInstanceSupported(path, IMetaLoader.class);
	}

	private static boolean isInstanceSupported(String path, Class<?> interfaceClass) throws Exception {

		final String extension = FileUtils.getFileExtension(path).toLowerCase();

		if (LOADERS.containsKey(extension)) {
			final CachedListIterator<Class<? extends IFileLoader>> loaders = LOADERS.get(extension);
			loaders.reset();

			while (loaders.hasNext()) {
				Class<? extends IFileLoader> clazz = loaders.next();
				final IFileLoader loader = getLoader(clazz, path);
				if (interfaceClass.isInstance(loader)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets an AbstractFileLoader for the given class and file path.
	 * 
	 * @param clazz
	 * @param path
	 * @return AbstractFileLoader
	 * @throws Exception
	 */
	public static IFileLoader getLoader(Class<? extends IFileLoader> clazz, final String path) throws Exception {

		IFileLoader loader;
		try {
			final Constructor<?> singleString = clazz.getConstructor(String.class);
			loader = (AbstractFileLoader) singleString.newInstance(path);
		} catch (NoSuchMethodException e) {
			loader = clazz.getConstructor().newInstance();

			final Method setFile = loader.getClass().getMethod("setFile", String.class);
			setFile.invoke(loader, path);
		} catch (NoClassDefFoundError ne) { // CBF Loader does this on win64
			loader = null;
		} catch (UnsatisfiedLinkError ule) {// CBF Loader does this on win64, the first time
			loader = null;
		}

		return loader;
	}

	/**
	 * Get class that can load files of given extension
	 * 
	 * @param extension
	 * @return loader class
	 */
	public static synchronized Class<? extends IFileLoader> getLoaderClass(String extension) {
		CachedListIterator<Class<? extends IFileLoader>> list = LOADERS.get(extension);
		if (list == null) {
			return null;
		}
		list.reset();
		return list.next();
	}

	/**
	 * Set registered extension to given loader class for testing
	 * @param extension
	 * @param loaderClass can be null to use preset order
	 */
	static void setTestingForLoader(String extension, Class<? extends IFileLoader> loaderClass) {
		CachedListIterator<Class<? extends IFileLoader>> list = LOADERS.get(extension);
		if (list == null) {
			if (loaderClass == null) {
				logger.error("Can not register a null loader class when extension is not known");
			} else {
				try {
					registerLoader(extension, loaderClass);
					list = LOADERS.get(extension);
				} catch (Exception e) {
					logger.error("Could not register loader to new extension list", e);
				}
			}
		}
		if (list != null) {
			list.setLast(loaderClass);
		}
	}

	private static Iterator<Class<? extends IFileLoader>> getIterator(String path) throws IllegalAccessException {

		if ((new File(path).isDirectory()))
			throw new IllegalAccessException("Cannot load directories with LoaderFactory!");

		final String extension = FileUtils.getFileExtension(path).toLowerCase();
		Iterator<Class<? extends IFileLoader>> it = null;
		CachedListIterator<Class<? extends IFileLoader>> list = null;

		if (LOADERS.containsKey(extension)) {
			it = list = LOADERS.get(extension);
			list.reset();
		} else if (!IGNORE_EXTS.contains(extension)) {
			// We may have a zipped file type that we support
			final File file = new File(path);
			final String regEx = ".+\\." + getLoaderExpression() + "\\." + getZipExpression();

			final Matcher m = Pattern.compile(regEx).matcher(file.getName());
			if (m.matches()) {
				final String realExt = m.group(1);
				if (LOADERS.keySet().contains(realExt)) {
					list = new CachedListIterator<Class<? extends IFileLoader>>();
					list.add(CompressedLoader.class);
					return list;
				}
			}

			final Set<Class<? extends IFileLoader>> all = new HashSet<Class<? extends IFileLoader>>();
			for (String ext : LOADERS.keySet()) {
				list = LOADERS.get(ext);
				list.reset();
				while (list.hasNext()) {
					all.add(list.next());
				}
			}
			it = all.iterator();
		}
		return it;
	}

	public static void registerUnzip(final String extension, final Class<? extends InputStream> input) {
		UNZIPPERS.put(extension, input);
	}

	/**
	 * Could cache this but it will be fast
	 */
	protected static String getZipExpression() {
		return getExpression(UNZIPPERS.keySet().iterator());
	}

	/**
	 * Could cache this but it will be fast
	 */
	protected static String getLoaderExpression() {
		return getExpression(LOADERS.keySet().iterator());
	}

	/**
	 * Could cache this but it will be fast
	 */
	private static String getExpression(final Iterator<String> it) {
		final StringBuilder buf = new StringBuilder();
		buf.append("(");
		while (it.hasNext()) {

			buf.append(it.next());
			if (it.hasNext())
				buf.append("|");
		}
		buf.append(")");
		return buf.toString();
	}

	/**
	 * Throws an exception if the loader is not ready to be used with LoaderFactory.
	 * Otherwise adds the class to the list of loaders.
	 * 
	 * NOTE that duplicates are allowed and the LoaderFactory simply tries loaders until
	 * one works. If loaders do not fail fast on invalid files then this approach does not work.
	 * 
	 * This has been tested by adding a test for each file type using the loader factory. This
	 * coverage could be extended by adding more example files and attempting to load them
	 * with the factory. However as long as each file type is passed through LoaderFactory and
	 * checks are made in the test to ensure that the loader is working, there is a good chance
	 * that it will find the right loader.
	 * 
	 * @param extension - lower case string
	 * @param loader
	 * @throws Exception
	 */
	public static void registerLoader(final String extension, final Class<? extends IFileLoader> loader) throws Exception {

		CachedListIterator<Class<? extends IFileLoader>> list = prepareRegistration(extension, loader);

		// Since not using set of loaders anymore must use contains to ensure
		// that a memory leak does not occur.
		if (!list.contains(loader)) list.add(loader);
	}

	/**
	 * Throws an exception if the loader is not ready to be used with LoaderFactory.
	 * Otherwise adds the class to the list of loaders at the position specified.
	 * 
	 * NOTE that duplicates are allowed and the LoaderFactory simply tries loaders until
	 * one works. If loaders do not fail fast on invalid files then this approach does not work.
	 * 
	 * This has been tested by adding a test for each file type using the loader factory. This
	 * coverage could be extended by adding more example files and attempting to load them
	 * with the factory. However as long as each file type is passed through LoaderFactory and
	 * checks are made in the test to ensure that the loader is working, there is a good chance
	 * that it will find the right loader.
	 * 
	 * @param extension - lower case string
	 * @param loader
	 * @throws Exception
	 */
	public static void registerLoader(final String extension, final Class<? extends IFileLoader> loader, final int position) throws Exception {

		CachedListIterator<Class<? extends IFileLoader>> list = prepareRegistration(extension, loader);
		// Since not using set of loaders anymore must use contains to ensure
		// that a memory leak does not occur.
		if (!list.contains(loader)) list.add(position, loader);
	}

	private static void registerLoaderWithException(String extension, Class<? extends IFileLoader> clazz) {
		try {
			registerLoader(extension, clazz);
		} catch (Throwable t) {
			logger.error("Could not register loader {}", clazz, t);
		}
	}

	private static CachedListIterator<Class<? extends IFileLoader>> prepareRegistration(String extension, Class<? extends IFileLoader> loader) throws Exception {
		try {
			loader.getConstructor(String.class);
		} catch (NoSuchMethodException e) {
			// TODO These messages are not quite correct
			if (loader.getMethod("setFile", String.class) == null)
				throw new Exception("Loaders must have method setFile(String path)");
			if (loader.getConstructor() == null)
				throw new Exception("Loaders must have a no argument constructor!");
		}

		CachedListIterator<Class<? extends IFileLoader>> list = LOADERS.get(extension);
		if (list == null) {
			list = new CachedListIterator<Class<? extends IFileLoader>>();
			LOADERS.put(extension, list);
		}
		return list;
	}

	/**
	 * Caches the last thing returned by iterator so that once it is reset, the
	 * iteration returns the cached value first
	 *
	 * @param <T>
	 */
	private static class CachedListIterator<T> implements Iterator<T> {
		private List<T> list;
		private T last; // last thing returned by iterator
		private Iterator<T> it;

		public CachedListIterator() {
			last = null;
			list = new LinkedList<>();
			reset();
		}

		/**
		 * @param e element must not be null
		 * @return true if list contains element
		 */
		public boolean contains(T e) {
			if (e == null) {
				throw new IllegalArgumentException("Null value not allowed");
			}
			return list.contains(e);
		}

		/**
		 * Add element to list
		 * @param e element must not be null
		 * @return true if was added
		 */
		public boolean add(T e) {
			if (e == null) {
				throw new IllegalArgumentException("Null value not allowed");
			}
			return list.add(e);
		}

		/**
		 * Add element to list in given position
		 * @param position
		 * @param e element must not be null
		 */
		public void add(int position, T e) {
			if (e == null) {
				throw new IllegalArgumentException("Null value not allowed");
			}
			list.add(position, e);
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			last = it.next();
			return last;
		}

		/**
		 * Set last item used
		 * @param last
		 */
		public void setLast(T last) {
			this.last = last;
		}

		/**
		 * Must call before re-use
		 */
		public void reset() {
			T cached = list.size() > 1 ? last : null;

			if (cached != null) {
				LinkedList<T> nl = new LinkedList<>(list);
				if (nl.remove(cached)) { // in case cached item has been removed
					nl.addFirst(cached);
				}
				it = nl.iterator();
			} else {
				it = list.iterator();
			}
		}
	}

	/**
	 * Call to clear all the loaders registered for a given extension.
	 * 
	 * @param extension
	 */
	public static void clearLoader(final String extension) {
		LOADERS.remove(extension);
	}

	protected static Class<? extends InputStream> getZipStream(final String extension) {
		return UNZIPPERS.get(extension);
	}

	private static IMetadata lockedMetaData;

	/**
	 * DO NOT MAKE Public. Use ILoaderService instead.
	 * @return metaData
	 */
	static IMetadata getLockedMetaData() {
		return lockedMetaData;
	}

	/**
	 * @Internal do not use. Use ILoaderService.getLockedDiffractionMetaData()
	 */
	public static void setLockedMetaData(IMetadata lockedMetaData) {
		LoaderFactory.lockedMetaData = lockedMetaData;
		if (lockedMetaData==null) clear();
	}

	private final static List<String> HDF5_EXT;
	static {
		List<String> tmp = new ArrayList<String>(7);
		tmp.add("h5");
		tmp.add("nxs");
		tmp.add("hd5");
		tmp.add("hdf5");
		tmp.add("hdf");
		tmp.add("nexus");
		HDF5_EXT = Collections.unmodifiableList(tmp);
	}	

	private static boolean isH5(final String filePath) {
		if (filePath == null) { return false; }
		final String ext = FileUtils.getFileExtension(filePath);
		if (ext == null) { return false; }
		return HDF5_EXT.contains(ext.toLowerCase());
	}
	
	
	/**
	 * This method may be called to ensure that the soft reference cache of data is
	 * empty. It is required from the unit tests which attempt to measure memory
	 * leaks, which otherwise would measure the "leak" of the soft reference cache.
	 */
	public static void clear() {
		dataCache.clear();
	}
	
	public static void clear(String filePath) {
		filePath = Utils.translateDLSFilePath(filePath);
		dataCache.clear(filePath);
	}

}
