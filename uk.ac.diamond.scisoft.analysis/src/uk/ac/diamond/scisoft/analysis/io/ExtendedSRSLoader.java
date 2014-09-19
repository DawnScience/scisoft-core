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
import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.io.SliceObject;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;

/**
 * This class loads an SRS data file and also images from a Dectris Pilatus detector
 * <p>
 * <b>Note</b>: the metadata from this loader is left as strings
 */
public class ExtendedSRSLoader extends SRSLoader {

	private static final String PILATUS_DIR = "pilatus100k"; // sub-directory for 100k images
	private static final String PATH_DATASET = "path"; // path dataset name
	private static final String PILATUS_TEMPLATE = "pilatus100k_path_template"; // metadata key for template format
																				// string
	private static final String DATA_NAME = "Pilatus";

	public ExtendedSRSLoader(String filename) {
		super(filename);
	}

	private void appendPilatusData(DataHolder currentDataHolder, IMonitor mon) {

		ImageStackLoader loader = null;

		// now we need to try to load in the the pilatus data
		if (currentDataHolder.contains(PATH_DATASET)) {
			Dataset paths = currentDataHolder.getDataset(PATH_DATASET);
			String template = textMetadata.get(PILATUS_TEMPLATE);
			if (template == null) {
				// bodged format v1
				loader = getImageStack(PILATUS_DIR + "/test%d.tif", PILATUS_DIR + "/p%d.tif", paths, mon);
			} else {
				// bodged format v2
				loader = getImageStack(template, null, paths, mon);
			}
		}

		if (loader != null) {
			LazyDataset lazyDataset = new LazyDataset(DATA_NAME, loader.getDtype(), loader.getShape(), loader);
			currentDataHolder.addDataset(lazyDataset.getName(), lazyDataset);
			datasetNames.add(lazyDataset.getName());
			dataShapes.put(lazyDataset.getName(), lazyDataset.getShape());
		}
	}

	private ImageStackLoader getImageStack(String format, String format2, Dataset paths, IMonitor mon) {
		ArrayList<String> files = new ArrayList<String>();
		final File dir = new File(fileName).getParentFile();

		// Only works with 1D set which is likely ok, we are a very specific format here.
		for (int i = 0; i < paths.getSize(); ++i) {
			int n = paths.getInt(i);
			File iFile = new File(dir, String.format(format, n));
			if (!iFile.exists() && format2 != null) iFile = new File(dir, String.format(format2, n));
			if (!iFile.exists()) continue;
			files.add(iFile.getAbsolutePath());
		}
		
		// fix to http://jira.diamond.ac.uk/browse/DAWNSCI-439
		// We fudge loading if the names of the tifs were not matched.
		if (files.isEmpty() && format2==null) {
			try {
				final String subDir = format.substring(0, format.lastIndexOf('/'));
				final File imageDir = new File(dir, subDir);
				if (imageDir.exists() && imageDir.list()!=null) {
					final File[] fa = imageDir.listFiles();
					for (int i = 0; i < fa.length; i++) {
						final File f = fa[i];
						if (f.getName().endsWith(".tif")) {
						    files.add(f.getAbsolutePath());
						}
					}
				}
			} catch (Exception e) {
				logger.warn("Could not create ImageStackLoader, not populating Pilatus image stack");
				return null;
			}
		}

		try {
			return new ImageStackLoader(files, mon);
		} catch (Exception e) {
			logger.warn("Could not create ImageStackLoader, not populating Pilatus image stack");
			return null;
		}
	}

	@Override
	public DataHolder loadFile(IMonitor mon) throws ScanFileHolderException {
		// load all the standard data in
		DataHolder data = super.loadFile(mon);

		appendPilatusData(data, mon);
		if (loadMetadata) {
			createMetadata();
			data.setMetadata(getMetadata());
		}
		return data;
	}

	@Override
	public void loadMetadata(IMonitor mon) throws Exception {
		super.loadMetadata(mon);

		// Cannot do this if decorator, this means that the I16 data folder would parse all
		// the ascii files in the whole directory!!

		if (textMetadata.containsKey(PILATUS_TEMPLATE)) {

			if (!datasetNames.contains(PATH_DATASET))
				return;
			/**
			 * IMPORTANT DO NOT PARSE WHOLE FILE HERE! It will break the decorators!
			 */
			datasetNames.add(DATA_NAME);
			createMetadata();
		}
	}

	/**
	 * Slices the stack of images
	 */
	protected Dataset slice(SliceObject bean, IMonitor mon) throws Exception {

		/**
		 * Not ideal have to parse SRS file once for each slice. The LoaderFactory caches slices which helps a little.
		 */
		this.fileName = bean.getPath();
		final DataHolder dh = loadFile(mon);
		ILazyDataset imageStack = dh.getLazyDataset(DATA_NAME);
		// ImageStackLoader does load the Dataset at this point
		return (Dataset) imageStack.getSlice(bean.getSliceStart(), bean.getSliceStop(), bean.getSliceStep());
	}
}
