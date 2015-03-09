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
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.SliceNDIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;

/**
 * Class to create dataset from a dataset of filenames.
 * 
 * The shape of the 'whole' dataset represented by the set of filenames appended with
 * the shape of the first image.
 * 
 * The type of the dataset is set to equal the type of the first image.
 */
public class ImageStackLoader implements ILazyLoader {

	private StringDataset filenames;
	private int[] fShape; // filename shape
	private int[] iShape; // image shape
	private int[] shape;
	private int dtype;
	private String parent;
	private Class<? extends IFileLoader> loaderClass;
	private boolean onlyOne;
	
	public int getDtype() {
		return dtype;
	}

	public ImageStackLoader(List<String> imageFilenames, IMonitor mon) throws Exception {
		this(imageFilenames, LoaderFactory.getData(imageFilenames.get(0), mon), mon);
	}

	@SuppressWarnings("unused")
	public ImageStackLoader(List<String> imageFilenames, IDataHolder dh, IMonitor mon) throws Exception {
		this((StringDataset) DatasetFactory.createFromList(imageFilenames), dh, null);
	}

	public ImageStackLoader(int[] dimensions, String[] imageFilenames, String directory) throws Exception {
		this(new StringDataset(imageFilenames, dimensions), null, directory);
	}

	public ImageStackLoader(int[] dimensions, String[] imageFilenames) throws Exception {
		this(dimensions, imageFilenames, null);
	}

	public ImageStackLoader(StringDataset imageFilenames, String directory) throws Exception {
		this(imageFilenames, null, directory);
	}

	public ImageStackLoader(StringDataset imageFilenames, IDataHolder dh, String directory) throws Exception {
		if (directory != null) {
			File file = new File(directory); 
			if (!file.isDirectory()) {
				throw new IllegalArgumentException("Given directory is not a directory");
			}
		}
		parent = directory;

		filenames = imageFilenames;
		fShape = imageFilenames.getShapeRef();
		int fRank = fShape.length;
		// load the first image to get the shape of the whole thing
		IDataset dataSetFromFile;
		if (dh == null || dh.getNames().length == 0) {
			dataSetFromFile = getDataSetFromFile(new int[fRank], null);
		} else {
			dataSetFromFile = dh.getDataset(0);
			loaderClass = dh.getLoaderClass();
		}
		onlyOne = imageFilenames.getSize() == 1;
		dtype = AbstractDataset.getDType(dataSetFromFile);
		iShape = dataSetFromFile.getShape();
		shape = Arrays.copyOf(fShape, fRank + iShape.length);
		for (int i = 0; i < iShape.length; i++) {
			shape[i + fRank] = iShape[i];
		}
	}

	private IDataset getDataSetFromFile(int[] location, IMonitor mon) throws ScanFileHolderException {
		// load the file
		String filename = filenames.get(location);
		filename = getLegalPath(filename);
		if (parent != null) {
			File f = new File(filename);
			if (f.isAbsolute()) {
				filename = f.getName();
			}
			filename = new File(parent, filename).getAbsolutePath();
		}
		IDataHolder data = null;
		if (loaderClass != null) {
			try {
				data = LoaderFactory.getData(loaderClass, filename, true, mon);
			} catch (Exception e) {
				// do nothing and try with all registered loaders
			}
		}
		if (data == null) {
			try {
				data = LoaderFactory.getData(filename, mon);
			} catch (Exception e) {
				throw new ScanFileHolderException("Cannot load image in image stack", e);
			}
			if (data == null) {
				throw new ScanFileHolderException("Cannot load image in image stack");
			}
		}
		if (loaderClass == null) {
			loaderClass = data.getLoaderClass();
		}

		IDataset dataset = data.getDataset(0);
		dataset.setName(filename);

		return dataset;
	}
	
	/**
	 * 
	 * @param dlsPath
	 * @return String in windows format.
	 */
	private static String getLegalPath(String dlsPath) {
		if (dlsPath ==null)
			return null;

		String path;
		if (isWindowsOS() && dlsPath.startsWith("/dls/")) {
			path = "\\\\Data.diamond.ac.uk\\" + dlsPath.substring(5);
		} else {
			path = dlsPath;
		}
        return path;
	}

	/**
	 * @return true if windows
	 */
	static public boolean isWindowsOS() {
		return System.getProperty("os.name").startsWith("Windows");
	}

	@Override
	public boolean isFileReadable() {
		return true;
	}

	@Override
	public Dataset getDataset(IMonitor mon, SliceND slice) throws ScanFileHolderException {
		int[] newShape = slice.getShape();

		if (AbstractDataset.calcSize(newShape) == 0)
			return DatasetFactory.zeros(newShape, dtype);

		int iRank = iShape.length;
		int nRank = newShape.length;
		int[] missing = new int[iRank];
		int start = nRank - iRank;
		for (int i = 0; i < iRank; i++) {
			missing[i] = start + i;
		}
		SliceNDIterator it = new SliceNDIterator(slice, missing);
		Dataset result = onlyOne || AbstractDataset.calcSize(it.getShape()) == 1 ? null : DatasetFactory.zeros(newShape, dtype);

		int[] pos = it.getUsedPos();
		SliceND iSlice = it.getOmittedSlice();
		int[] iShape = iSlice.getShape();
		SliceND dSlice = it.getOutputSlice();
		while (it.hasNext()) {
			IDataset image = getDataSetFromFile(pos, mon).getSliceView(iSlice);

			image.setShape(iShape);
			if (result == null) {
				result = DatasetUtils.convertToDataset(image);
				result.setShape(newShape);
				break;
			}
			result.setSlice(image, dSlice);
		}

		return result;
	}

	public int[] getShape() {
		return shape;
	}
}
