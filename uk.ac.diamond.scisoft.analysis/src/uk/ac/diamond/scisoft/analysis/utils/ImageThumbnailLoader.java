/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gda.analysis.io.ScanFileHolderException;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.function.Downsample;
import uk.ac.diamond.scisoft.analysis.dataset.function.DownsampleMode;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.RawBinaryLoader;

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
	public static AbstractDataset loadImage(String filename, boolean createThumbnail) {
		
		DataHolder scan = null;
		if (!filename.toLowerCase().endsWith(".raw")) {
//			long start = -System.nanoTime();
			try {
				scan = LoaderFactory.getData(filename, !createThumbnail, null);
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
	 * DataHolder, optionally scaling the data to a thumnail
	 * 
	 * @param path
	 * @param createThumbnail
	 * @param scan
	 * @return single data set
	 */
	public static AbstractDataset getSingle(final String     path,
			                                final boolean    createThumbnail,
			                                final DataHolder scan) {
		
		if (scan!=null && scan.size() > 0) {
			AbstractDataset ds = scan.getDataset(0);
			if (ds.getRank() == 2) { // 2D datasets only!!!
				int width = ds.getShape()[1];
				int height = ds.getShape()[0];
				if (ds.getName() == null || ds.getName().length() == 0) {
					File f = new File(path);
					ds.setName(f.getName());
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
		AbstractDataset ds_null = AbstractDataset.zeros(new int[] {DOWNSAMPLE_SIZE_IN_PIXELS, DOWNSAMPLE_SIZE_IN_PIXELS}, AbstractDataset.BOOL);
		ds_null.setName("Invalid Image");
		return ds_null;
	}

}
