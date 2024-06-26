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
import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.io.ILazyLoader;
import org.eclipse.january.io.IMetaLoader;
import org.eclipse.january.metadata.IMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class which can be extended when implementing IFileLoader
 */
public abstract class AbstractFileLoader implements IFileLoader, IMetaLoader {
	/** 
	 * Name prefix for an image dataset (should be followed by two digits, starting with 01)
	 */
	public static final String IMAGE_NAME_PREFIX = "image-";

	/** 
	 * Name format for an image dataset
	 */
	public static final String IMAGE_NAME_FORMAT = IMAGE_NAME_PREFIX + "%02d";

	/**
	 * Default name for first image dataset
	 */
	public static final String DEF_IMAGE_NAME = IMAGE_NAME_PREFIX + "01";

	/**
	 * Name for image stack
	 */
	public static final String STACK_NAME = "image-stack";

	/**
	 * String to separate full file path or file name from dataset name
	 */
	public static final String FILEPATH_DATASET_SEPARATOR = ":";

	protected String fileName = "";

	public void setFile(final String fileName) {
		this.fileName = fileName;
		clearMetadata();
	}

	protected boolean loadMetadata = true;
	protected IMetadata metadata;
	protected boolean loadLazily = false;

	protected abstract void clearMetadata();

	private static final Logger logger = LoggerFactory.getLogger(AbstractFileLoader.class);

	public abstract DataHolder loadFile() throws ScanFileHolderException ;

	@Override
	public IMetadata getMetadata() {
		return metadata;
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		return loadFile();
	}

	@Override
	public void setLoadMetadata(boolean willLoadMetadata) {
		loadMetadata = willLoadMetadata;
	}

	@Override
	public void setLoadAllLazily(boolean willLoadLazily) {
		loadLazily = willLoadLazily;
	}

	@Override
	public void loadMetadata(IMonitor mon) throws IOException {
		if (metadata != null)
			return;

		boolean oldMeta = loadMetadata;
		boolean oldLazily = loadLazily;
		loadMetadata = true;
		loadLazily = true;
		try {
			loadFile(mon);
		} catch (ScanFileHolderException e) {
			throw new IOException(e);
		}
		loadMetadata = oldMeta;
		loadLazily = oldLazily;
	}

	protected class LazyLoaderStub implements ILazyLoader {
		public static final long serialVersionUID = 5057544213374303912L;
		private IFileLoader loader;
		private String name;

		public LazyLoaderStub() {
			loader = null;
			name = null;
		}

		public LazyLoaderStub(IFileLoader loader) {
			this(loader, null);
		}

		/**
		 * @param loader
		 * @param name dataset name in data holder (can be null to signify first dataset)
		 */
		public LazyLoaderStub(IFileLoader loader, String name) {
			this.loader = loader;
			this.name = name;
			if (loader != null) {
				loader.setLoadAllLazily(false);
				loader.setLoadMetadata(false);
			}
		}

		public IFileLoader getLoader() {
			return loader;
		}

		@Override
		public IDataset getDataset(IMonitor mon, SliceND slice) throws IOException {
			if (loader == null) {
				return null;
			}
			IDataHolder holder = LoaderFactory.fetchData(fileName, loadMetadata);
			if (holder != null) {
				IDataset data = name == null ? holder.getDataset(0) : holder.getDataset(name);
				if (data != null) {
					logger.debug("Found cached {} in {}: {}", name, fileName, data);
					return DatasetUtils.convertToDataset(data).getSliceView(slice);
				}
			}

			try {
				IDataHolder nHolder = loader.loadFile(mon);
				if (holder != null) { // update old holder
					for (String dn : nHolder.getNames()) {
						holder.addDataset(dn, nHolder.getLazyDataset(dn));
					}
					holder.setMetadata(nHolder.getMetadata());
					if (holder.getLoaderClass() == null) {
						holder.setLoaderClass(loader.getClass());
					}
				} else {
					if (nHolder.getFilePath() == null) {
						nHolder.setFilePath(fileName);
					}
					if (nHolder.getLoaderClass() == null) {
						nHolder.setLoaderClass(loader.getClass());
					}
					LoaderFactory.cacheData(nHolder);
					holder = nHolder;
				}
			} catch (ScanFileHolderException e) {
				throw new IOException(e);
			}
			IDataset data = name == null ? holder.getDataset(0) : holder.getDataset(name);
			return data.getSliceView(slice);
		}

		@Override
		public boolean isFileReadable() {
			return new File(fileName).canRead();
		}
	}

	protected LazyDataset createLazyDataset(LazyLoaderStub l, String dName, Class<? extends Dataset> clazz, int... shape) {
		return new LazyDataset(l, dName, clazz == null ? DoubleDataset.class : clazz, shape);
	}

	protected LazyDataset createLazyDataset(IFileLoader loader, String dName, Class<? extends Dataset> clazz, int... shape) {
		return createLazyDataset(loader, dName, dName, clazz, shape);
	}

	protected LazyDataset createLazyDataset(IFileLoader loader, String dName, String dhName, Class<? extends Dataset> clazz, int... shape) {
		return new LazyDataset(new LazyLoaderStub(loader, dhName), dName, clazz == null ? DoubleDataset.class : clazz, shape);
	}


	/**
	 * @param mon
	 * @return false if cancelled
	 */
	protected static boolean monitorIncrement(IMonitor mon) {
		if (mon != null) {
			mon.worked(1);
			if (mon.isCancelled()) return false;
		}
		return true;
	}
	
	@Override
	public void setAsyncLoad(boolean treeOnTop) {
		throw new RuntimeException("Asynchronous loading is not supported!");
	}
	
	@Override
	public boolean isLoading() {
		throw new RuntimeException("Asynchronous loading is not supported!");
	}

}
