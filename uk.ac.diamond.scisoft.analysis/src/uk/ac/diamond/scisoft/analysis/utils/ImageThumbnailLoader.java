/*
 * Copyright (c) 2012-2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.downsample.DownsampleMode;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.function.Downsample;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.RawBinaryLoader;
import uk.ac.diamond.scisoft.analysis.plotserver.DatasetWithAxisInformation;

/**
 *
 */
public class ImageThumbnailLoader {
	private static final Logger logger = LoggerFactory.getLogger(ImageThumbnailLoader.class);
	
	
	/**
	 * see {@link LoaderFactory} getData(...) method
	 * 
	 * This implementation which does not loop over
	 * loaders and cause out of memory error depending on how badly the loader is 
	 * coded.
	 * 
	 * @param filename
	 * @param createThumbnail
	 * @return Dataset
	 */
	public static IDataset loadImage(String filename, boolean createThumbnail, boolean loadMetadata) {
		return loadImage(filename, null, createThumbnail, loadMetadata, null);
	}

	/**
	 * see {@link LoaderFactory} getData(...) method
	 * 
	 * This implementation which does not loop over
	 * loaders and cause out of memory error depending on how badly the loader is 
	 * coded.
	 * 
	 * @param filename
	 * @param additionalInfo
	 * @param createThumbnail
	 * @param loadMetadata
	 * @return IDataset
	 */
	public static IDataset loadImage(String filename, Object additionalInfo, boolean createThumbnail, boolean loadMetadata) {
		return loadImage(filename, additionalInfo, createThumbnail, loadMetadata, null);
	}

	/**
	 * see {@link LoaderFactory} getData(...) method
	 * 
	 * This implementation which does not loop over
	 * loaders and cause out of memory error depending on how badly the loader is 
	 * coded.
	 * 
	 * @param filename
	 * @param createThumbnail
	 * @param loadMetadata
	 * @param monitor
	 * @return IDataset
	 */
	public static IDataset loadImage(String filename, boolean createThumbnail, boolean loadMetadata, IMonitor monitor) {
		return loadImage(filename, null, createThumbnail, loadMetadata, monitor);
	}

	/**
	 * see {@link LoaderFactory} getData(...) method
	 * 
	 * This implementation which does not loop over
	 * loaders and cause out of memory error depending on how badly the loader is 
	 * coded.
	 * 
	 * @param filename
	 * @param additionalInfo
	 * @param createThumbnail
	 * @return IDataset
	 */
	public static IDataset loadImage(String filename, Object additionalInfo, boolean createThumbnail, boolean loadMetadata, IMonitor monitor) {
		
		IDataHolder scan = null;
		if (!filename.toLowerCase().endsWith(".raw")) {
//			long start = -System.nanoTime();
			try {
				scan = LoaderFactory.getData(filename, loadMetadata, monitor);
			} catch (Exception e) {
				logger.debug("Cannot load "+filename + ". Will try to load the dataset directly instead.");
				try {
					// try loading the additional Info
					if (additionalInfo instanceof DatasetWithAxisInformation) {
						DatasetWithAxisInformation data = (DatasetWithAxisInformation)additionalInfo;
						return data.getData();
					}
				} catch (Exception ex) {
					logger.error("Could not load data with additionalinfo: ", ex);
				}
			}
//			start += System.nanoTime();
//			logger.info("Loading {} took {}ms", filename, start/1000000);
		} else {
			RawBinaryLoader rwBinLoader = new RawBinaryLoader(filename);
			try {
				scan = rwBinLoader.loadFile();
			} catch (ScanFileHolderException e) {
				logger.error("Cannot load "+filename, e);
			}
		}

		return getSingle(filename, createThumbnail, scan);
	}

	private static final int DOWNSAMPLE_SIZE_IN_PIXELS = 96;

	/**
	 * Utility method for extracting the image Dataset from a
	 * DataHolder, optionally scaling the data to a thumbnail
	 * 
	 * @param path
	 * @param createThumbnail
	 * @param scan
	 * @return single data set
	 */
	public static IDataset getSingle(final String     path,
			                                final boolean    createThumbnail,
			                                final IDataHolder scan) {
		if (scan != null && scan.size() > 0) {
			IDataset ds = scan.getDataset(0);
			return getSingleFromDataset(path, createThumbnail, ds);
		}
		Dataset ds_null = DatasetFactory.zeros(new int[] {DOWNSAMPLE_SIZE_IN_PIXELS, DOWNSAMPLE_SIZE_IN_PIXELS}, Dataset.BOOL);
		ds_null.setName("Invalid Image");
		return ds_null;
	}

	/**
	 * Utility method for scaling a dataset to a thumbnail
	 * 
	 * @param path
	 * @param ds
	 * @return single data set
	 */
	public static IDataset getThumbnail(String path, IDataset ds) {
		return getSingleFromDataset(path, true, ds);
	}

	/**
	 * Utility method for scaling a dataset to a thumbnail
	 * 
	 * @param path
	 * @param ds
	 * @return single data set
	 */
	public static IDataset getSingleFromDataset(String path, boolean createThumbnail, IDataset ds) {
		if (ds != null) {
			if (ds.getRank() == 2) { // 2D datasets only!!!
				int width = ds.getShape()[1];
				int height = ds.getShape()[0];
				String name = new File(path).getName();
				String dname = ds.getName();
				if (dname == null || dname.length() == 0 || dname.contains(AbstractFileLoader.IMAGE_NAME_PREFIX)) {
					ds.setName(name);
				} else {
					ds.setName(name + AbstractFileLoader.FILEPATH_DATASET_SEPARATOR + ds.getName());
				}
				if (createThumbnail) {
					int step;
					step = Math.max(1, (width > height ? width : height) / DOWNSAMPLE_SIZE_IN_PIXELS);
					int[] stepping = new int[] { step, step };
					Downsample down = new Downsample(DownsampleMode.POINT, stepping);
					Dataset ds_downsampled = down.value(ds).get(0);
					ds_downsampled.setName(new String(path));
					return ds_downsampled;
				}
				return ds;
			}
		}
		Dataset ds_null = DatasetFactory.zeros(new int[] {DOWNSAMPLE_SIZE_IN_PIXELS, DOWNSAMPLE_SIZE_IN_PIXELS}, Dataset.BOOL);
		ds_null.setName("Invalid Image");
		return ds_null;
	}
}
