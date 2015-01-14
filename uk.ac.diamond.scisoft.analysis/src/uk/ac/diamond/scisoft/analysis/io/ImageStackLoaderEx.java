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
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;

/**
 * Class to create dataset from a list of filenames.
 * 
 * The filenames can either represent a 1d or 2d scan. The dimensionality is
 * indicated by a dimensions constructor argument
 * 
 * In the case of 2d scan 
 * 
 * filename for position x,y = filenames[x*dimensions[1] + y ]
 * 
 * 
 * The shape of the 'whole' dataset represented by the set of filenames is dimensions
 * shape of the first image
 * 
 * The type of the dataset is set to equal the type of the first image.
 */
public class ImageStackLoaderEx implements ILazyLoader {

	String[] imageFilenames;
	int[] shape;
	int dtype;
	int [] dimensions;
	private int[] data_shapes;
	DatasetRecord cache;
	String parent;
	private Class<? extends IFileLoader> loaderClass;
	
	public int getDtype() {
		return dtype;
	}

	public ImageStackLoaderEx(StringDataset imageFilenames, String directory) throws Exception {
		this(imageFilenames.getShape(), imageFilenames.getData(), directory);
	}

	public ImageStackLoaderEx(int[] dimensions, String[] imageFilenames) throws Exception {
		this(dimensions, imageFilenames, null);
	}

	public ImageStackLoaderEx(int[] dimensions, String[] imageFilenames, String directory) throws Exception {
		if( dimensions == null || dimensions.length<1 || dimensions.length>2)
			throw new IllegalArgumentException("dimensions invalid");
		int totalLength = AbstractDataset.calcSize(dimensions);
		if( imageFilenames == null || imageFilenames.length != totalLength)
			throw new IllegalArgumentException("imageFilenames.length != "+ totalLength);

		if (directory != null) {
			File file = new File(directory); 
			if (!file.isDirectory()) {
				throw new IllegalArgumentException("Given directory is not a directory");
			}
		}
		parent = directory;

		this.imageFilenames = imageFilenames;
		this.dimensions = dimensions;
		// load the first image to get the shape of the whole thing
		int [] location = new int[dimensions.length];
		Arrays.fill(location, 0);
		IDataset dataSetFromFile = getDataSetFromFile(location, null);
		dtype = dataSetFromFile instanceof Dataset ? ((Dataset) dataSetFromFile).getDtype() : 
			AbstractDataset.getDTypeFromClass(dataSetFromFile.elementClass());
		data_shapes = dataSetFromFile.getShape();
		shape = Arrays.copyOf(dimensions, dimensions.length + data_shapes.length);
		int offset = dimensions.length;
		for( int i=0;i<data_shapes.length;i++){
			shape[i+offset]=data_shapes[i];
		}
	}

	String getFilename(int[] pos) {
		if (dimensions.length == 1)
			return imageFilenames[pos[0]];
		if (dimensions.length == 2)
			return imageFilenames[pos[0] * dimensions[1] + pos[1]];
		return null;
	}

	private IDataset getDataSetFromFile(int[] location, IMonitor mon) throws ScanFileHolderException {
		if (cache == null || !Arrays.equals(location, cache.location)) {
			// load the file
			String filename = getFilename(location);
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
			} else if (loaderClass == null) {
				loaderClass = data.getLoaderClass();
			}

			IDataset abstractDataset = data.getDataset(0);
			abstractDataset.setName(filename);

			cache = new DatasetRecord(abstractDataset, location);
		}
		return cache.dataset;
	}
	
	/**
	 * 
	 * @param dlsPath
	 * @return String in windows format.
	 */
	private static String getLegalPath(String dlsPath) {
		if (dlsPath ==null) return null;
		
		String path = dlsPath;
		if (isWindowsOS() && path.startsWith("/dls/")) {
			path = "\\\\Data.diamond.ac.uk\\"+path.substring(5);
		}
		
        return path;
	}
	/**
	 * @return true if windows
	 */
	static public boolean isWindowsOS() {
		return (System.getProperty("os.name").indexOf("Windows") == 0);
	}

	@Override
	public boolean isFileReadable() {
		return true;
	}

	@Override
	public Dataset getDataset(IMonitor mon, SliceND slice) throws ScanFileHolderException {
		int[] lstart = slice.getStart();
		int[] lstop  = slice.getStop();
		int[] lstep  = slice.getStep();
		int[] newShape = slice.getShape();

		// dataset we will return
		Dataset result = DatasetFactory.zeros(newShape, dtype);

		int [] resultStart = new int[newShape.length];

		int [] resultStep= new int[lstep.length];
		Arrays.fill(resultStep, 1);

		// location in result for current file
		int [] currentResultStart= new int[lstart.length];
		
		int [] currentResultStop= new int[lstart.length];
		Arrays.fill(currentResultStop, 1);
		for( int i=dimensions.length; i< lstart.length;i++){
			currentResultStop[i] = newShape[i];
		}

		// iterate over all files
		do {
			
			// get pointer into file set = start+file in iteration
			int [] fileLocation = Arrays.copyOfRange(lstart, 0, dimensions.length);
			for(int i=0; i< fileLocation.length;i++){
				fileLocation[i]+=currentResultStart[i];
			}

			IDataset dataSetFromFile = getDataSetFromFile(fileLocation, mon);

			int[] fileImageStart = Arrays.copyOfRange(lstart, dimensions.length, lstart.length);
			int[] fileImageStop = Arrays.copyOfRange(lstop, dimensions.length, lstart.length);
			int[] fileImageStep = Arrays.copyOfRange(lstep, dimensions.length, lstart.length);
			
			// extract the slice required
			IDataset data = dataSetFromFile.getSlice(fileImageStart, fileImageStop, fileImageStep);
			
			try{
				// add data from this file to the overall result
				result.setSlice(data, currentResultStart, currentResultStop, resultStep);
			} catch( Exception e){
				throw new IllegalArgumentException("Error adding slice",e);
			}
		
		} while (addStep(currentResultStart, currentResultStop, resultStart, newShape, lstep, dimensions.length-1));
		
		return result;	
		
	}

	private boolean addStep(int[] currentStart, int[] currentStop, int start[], int[] stop, int[] step, int index) {
		if (index < 0)
			return false;
		int newStart = currentStart[index] + step[index];
		if (newStart < stop[index]) {
			currentStart[index] = newStart;
			currentStop[index] = newStart + 1;
			return true;
		}
		currentStart[index] = start[index];
		currentStop[index] = start[index]+1;
		return addStep(currentStart, currentStop, start, stop, step, index - 1);
	}

	public int[] getShape() {
		return shape;
	}

}

class DatasetRecord {
	IDataset dataset;
	int[] location;

	public DatasetRecord(IDataset dataset, int[] location) {
		super();
		this.dataset = dataset;
		// need to make copy rather than take reference to prevent value being
		// changed under the covers
		this.location = location.clone();
	}

}
