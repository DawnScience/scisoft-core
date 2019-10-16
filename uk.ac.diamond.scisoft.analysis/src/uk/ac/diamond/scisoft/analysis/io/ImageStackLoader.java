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
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.LazyDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.SliceNDIterator;
import org.eclipse.january.dataset.StringDataset;
import org.eclipse.january.io.ILazyLoader;
import org.eclipse.january.metadata.IMetadata;

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
	private long[] mShape; // max total shape
	private int[] iShape; // image shape
	private int[] shape;
	private Class<? extends Dataset> clazz;
	private File parent = null;
	private Class<? extends IFileLoader> loaderClass;
	private boolean onlyOne;
	private String datasetName;
	
	public Class<? extends Dataset> getInterface() {
		return clazz;
	}

	public ImageStackLoader(List<String> imageFilenames, IMonitor mon) throws Exception {
		this(imageFilenames, LoaderFactory.getData(imageFilenames.get(0), mon), mon);
	}

	@SuppressWarnings("unused")
	public ImageStackLoader(List<String> imageFilenames, IDataHolder dh, IMonitor mon) throws Exception {
		this((StringDataset) DatasetFactory.createFromList(imageFilenames), dh, null);
	}

	public ImageStackLoader(int[] dimensions, String[] imageFilenames, String directory) throws Exception {
		this(DatasetFactory.createFromObject(StringDataset.class, imageFilenames, dimensions), null, directory);
	}

	public ImageStackLoader(int[] dimensions, String[] imageFilenames) throws Exception {
		this(dimensions, imageFilenames, null);
	}

	public ImageStackLoader(StringDataset imageFilenames, String directory) throws Exception {
		this(imageFilenames, null, directory);
	}

	public ImageStackLoader(StringDataset imageFilenames, IDataHolder dh, String directory) throws Exception {
		this(imageFilenames,dh,directory,null);
	}
	
	public ImageStackLoader(StringDataset imageFilenames, IDataHolder dh, String directory, String datasetName) throws Exception {
		if (directory != null) {
			File file = new File(directory); 
			if (file.isDirectory()) {
				parent = file;
			}
		}

		filenames = imageFilenames;
		fShape = imageFilenames.getShapeRef();
		this.datasetName = datasetName;
		int fRank = fShape.length;
		// load the first image to get the shape of the whole thing
		ILazyDataset dataSetFromFile;
		if (dh == null || dh.getNames().length == 0) {
			dataSetFromFile = getDatasetFromFile(new int[fRank], null);
		} else {
			dataSetFromFile = datasetName == null ? dh.getDataset(0) : dh.getDataset(datasetName);
			loaderClass = dh.getLoaderClass();
		}
		onlyOne = imageFilenames.getSize() == 1;
		clazz = InterfaceUtils.getInterfaceFromClass(dataSetFromFile.getElementsPerItem(), dataSetFromFile.getElementClass());
		iShape = dataSetFromFile.getShape();
		shape = Arrays.copyOf(fShape, fRank + iShape.length);
		for (int i = 0; i < iShape.length; i++) {
			shape[i + fRank] = iShape[i];
		}
	}

	private ILazyDataset getDatasetFromFile(int[] location, IMonitor mon) throws ScanFileHolderException {
		File f = new File(filenames.get(location));
		File nf = null;
		if (parent != null) { // try local directory first
			if (!f.isAbsolute()) { // try relative path first
				nf = getDLSWindowsPath(new File(parent, f.getPath()));
			}
			if (nf == null || !nf.exists()) {
				nf = getDLSWindowsPath(new File(parent, f.getName()));
				if (!nf.exists()) {
					nf = null;
				}
			}
		}

		if (nf == null) {
			nf = getDLSWindowsPath(f);
		}

		return loadDataset(nf.getAbsolutePath(), mon);
	}

	private ILazyDataset loadDataset(String filename, IMonitor mon) throws ScanFileHolderException {
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
		
		ILazyDataset dataset = null;
		if (datasetName != null) {
			try {
				dataset = data.getLazyDataset(datasetName);
			} catch (Exception e) {
				throw new ScanFileHolderException("Could no slice data", e);
			}
		} else {
			dataset = data.getDataset(0);
		}
		
		IMetadata meta = data.getMetadata();
		dataset.setMetadata(meta);
		return dataset;
	}

	private static final boolean isWindows = System.getProperty("os.name").startsWith("Windows");

	private static final String DLS_PREFIX = "/dls/";

	/**
	 * 
	 * @param f
	 * @return file in windows format.
	 */
	private static File getDLSWindowsPath(File f) {
		if (!isWindows || f == null) {
			return f;
		}

		String dlsPath = f.getPath();
		if (dlsPath.startsWith(DLS_PREFIX)) {
			return new File( "\\\\Data.diamond.ac.uk", dlsPath.substring(DLS_PREFIX.length()));
		}
		return f;
	}

	@Override
	public boolean isFileReadable() {
		return true;
	}

	@Override
	public Dataset getDataset(IMonitor mon, SliceND slice) throws IOException {
		int[] newShape = slice.getShape();

		if (ShapeUtils.calcSize(newShape) == 0)
			return DatasetFactory.zeros(clazz, newShape);

		int iRank = iShape.length;
		int nRank = newShape.length;
		int[] missing = new int[iRank];
		int start = nRank - iRank;
		for (int i = 0; i < iRank; i++) {
			missing[i] = start + i;
		}
		SliceNDIterator it = new SliceNDIterator(slice, missing);
		Dataset result = onlyOne || ShapeUtils.calcSize(it.getShape()) == 1 ? null : DatasetFactory.zeros(clazz, newShape);

		int[] pos = it.getUsedPos();
		SliceND iSlice = it.getOmittedSlice();
		int[] iShape = iSlice.getShape();
		SliceND dSlice = it.getOutputSlice();
		while (it.hasNext()) {
			IDataset image;
			try {
				image = getDatasetFromFile(pos, mon).getSlice(iSlice);
			} catch (Exception e) {
				throw new IOException(e);
			}

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

	/**
	 * @param maxShape original maximum shape for filenames dataset
	 */
	public void setMaxShape(long[] maxShape) {
		if (maxShape == null) {
			mShape = null;
			return;
		}
		if (maxShape.length != fShape.length) {
			throw new IllegalArgumentException("Maximum shape must be same rank as filename dataset");
		}
		int rank = shape.length;
		int mrank = maxShape.length;
		mShape = new long[rank];
		int i = 0;
		for (; i < mrank; i++) {
			mShape[i] = maxShape[i];
		}
		for (; i < rank; i++) {
			mShape[i] = shape[i];
		}
	}

	/**
	 * Remove dimensions of one from shape (and max shape)
	 */
	public void squeeze() {
		int rank = shape.length;
		int unitDims = 0;
		for (int i = 0; i < rank; i++) {
			if (shape[i] == 1) {
				unitDims++;
			}
		}

		if (unitDims == 0)
			return;

		int nrank = rank - unitDims;
		int[] nShape = new int[nrank];
		long[] nMaxShape = new long[nrank];

		int j = 0;
		for (int i = 0; i < rank; i++) {
			if (shape[i] > 1) {
				nShape[j] = shape[i];
				nMaxShape[j++] = mShape[i];
				if (j == nrank)
					break;
			}
		}
		shape = nShape;
		mShape = nMaxShape;
		filenames.squeeze();
	}

	/**
	 * @return maximum shape
	 */
	public long[] getMaxShape() {
		return mShape;
	}

	/**
	 * @return chunk shape
	 */
	public long[] getChunkShape() {
		// use each image as a chunk
		int rank = shape.length;
		long[] chunk = new long[rank];
		int i = 0;
		int ibeg = rank - iShape.length;
		for (; i < ibeg; i++) {
			chunk[i] = 1;
		}
		for (; i < rank; i++) {
			chunk[i] = iShape[i - ibeg];
		}

		return chunk;
	}

	/**
	 * Create lazy dataset from stack
	 * @param name
	 * @return lazy dataset
	 */
	public ILazyDataset createLazyDataset(String name) {
		return new LazyDataset(this, name, clazz, shape);
	}
}
