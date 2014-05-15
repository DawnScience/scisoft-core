/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.function.Downsample;
import uk.ac.diamond.scisoft.analysis.dataset.function.DownsampleMode;
import uk.ac.diamond.scisoft.analysis.io.AbstractFileLoader;
import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.RawBinaryLoader;
import uk.ac.diamond.scisoft.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

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
	 * @return AbstractDataset
	 */
	public static IDataset loadImage(String filename, boolean createThumbnail, boolean loadMetaData) {
		return loadImage(filename, createThumbnail, loadMetaData, null);
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
	 * @return AbstractDataset
	 */
	public static IDataset loadImage(String filename, boolean createThumbnail, boolean loadMetaData, IMonitor monitor) {
		
		IDataHolder scan = null;
		if (!filename.toLowerCase().endsWith(".raw")) {
//			long start = -System.nanoTime();
			try {
				scan = LoaderFactory.getData(filename, loadMetaData, monitor);
			} catch (Exception e) {
				logger.error("Cannot load "+filename, e);
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
					step = Math.max(1, (width > height ? width : height)/ DOWNSAMPLE_SIZE_IN_PIXELS);
					int[] stepping = new int[] {step, step};
					Downsample down = new Downsample(DownsampleMode.POINT, stepping);
					AbstractDataset ds_downsampled = down.value(ds).get(0);
					ds_downsampled.setName(new String(path));
					return ds_downsampled;
				}
				return ds;
			}
		}
		AbstractDataset ds_null = AbstractDataset.zeros(new int[] {DOWNSAMPLE_SIZE_IN_PIXELS, DOWNSAMPLE_SIZE_IN_PIXELS}, Dataset.BOOL);
		ds_null.setName("Invalid Image");
		return ds_null;
	}
}
