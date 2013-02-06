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

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;

import java.io.File;
import java.util.Arrays;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.StringDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * Class to create dataset from a list of filenames.
 * 
 * The filenames can either represent a 1d or 2d scan. The dimensionality is indicated by a dimensions constructor argument
 * 
 * In the case of 2d scan 
 * 
 * filename for position x,y = filenames[x*dimensions[1] + y ]
 * 
 * 
 * The shape of the 'whole' dataset represented by the set of filenames is dimensions * shape of the first image
 * 
 * The type of the dataset is set to equal the type of the first image.
 */
public class ImageStackLoaderEx implements ILazyLoader {

	String[] imageFilenames;
	int[] shape;
	int dtype;
	int [] dimensions;
	private int[] data_shapes;
	AbstractDatasetRecord cache;
	String parent;
	private Class<? extends AbstractFileLoader> loaderClass;
	
	public int getDtype() {
		return dtype;
	}

	public ImageStackLoaderEx(StringDataset imageFilenames, String directory) throws Exception {
		this(imageFilenames.getShape(), imageFilenames.getData(), directory);
	}

	public ImageStackLoaderEx(int [] dimensions, String[] imageFilenames) throws Exception {
		this(dimensions, imageFilenames, null);
	}

	public ImageStackLoaderEx(int [] dimensions, String[] imageFilenames, String directory) throws Exception {
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
		java.util.Arrays.fill(location, 0);
		AbstractDataset dataSetFromFile = getDataSetFromFile(location, null);
		dtype = dataSetFromFile.getDtype(); 
		data_shapes = dataSetFromFile.getShape();
		shape = java.util.Arrays.copyOf(dimensions, dimensions.length + data_shapes.length);
		int offset = dimensions.length;
		for( int i=0;i<data_shapes.length;i++){
			shape[i+offset]=data_shapes[i];
		}
	}

	String getFilename(int [] pos){
		if (dimensions.length==1)
			return imageFilenames[pos[0]];
		if (dimensions.length==2)
			return imageFilenames[pos[0]*dimensions[1]+pos[1]];
		return null;
	}
	

	private AbstractDataset getDataSetFromFile(int[] location, IMonitor mon) throws ScanFileHolderException {
		if (cache == null || !java.util.Arrays.equals(location, cache.location)) {
			// load the file
			String filename = getFilename(location);
			if (parent != null) {
				File f = new File(filename);
				if (f.isAbsolute()) {
					filename = f.getName();
				}
				filename = new File(parent, filename).getAbsolutePath();
			}
			DataHolder data = null;
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

			AbstractDataset abstractDataset = data.getDataset(0);
			abstractDataset.setName(filename);

			cache = new AbstractDatasetRecord(abstractDataset, location);
		}
		return cache.dataset;
	}

	@Override
	public boolean isFileReadable() {
		return true;
	}

	@Override
	public AbstractDataset getDataset(IMonitor mon, int[] shape, int[] start, int[] stop, int[] step) throws ScanFileHolderException {
		
		if (step == null) {
			step = new int[shape.length];
			Arrays.fill(step, 1);
		}
		//check slice values are valid given shape of  whole dataset
		AbstractDataset.checkSlice(this.shape, start, stop, start, stop, step);

		final int[] newShape = AbstractDataset.checkSlice(shape, start, stop, start, stop, step);

		//dataset we will return
		AbstractDataset result = AbstractDataset.zeros(newShape, dtype);

		int [] resultStart = new int[newShape.length];
		java.util.Arrays.fill(resultStart, 0);

		int [] resultStep= new int[step.length];
		java.util.Arrays.fill(resultStep, 1);

		//location in result for current file
		int [] currentResultStart= new int[start.length];
		java.util.Arrays.fill(currentResultStart, 0);
		
		int [] currentResultStop= new int[start.length];
		java.util.Arrays.fill(currentResultStop, 1);
		for( int i=dimensions.length; i< start.length;i++){
			currentResultStop[i] = newShape[i];
		}

		//iterate over all files
		do {
			
			//get pointer into file set = start+file in iteration
			int [] fileLocation = java.util.Arrays.copyOfRange(start, 0, dimensions.length);
			for(int i=0; i< fileLocation.length;i++){
				fileLocation[i]+=currentResultStart[i];
			}

			AbstractDataset dataSetFromFile = getDataSetFromFile(fileLocation, mon);

			int[] fileImageStart = java.util.Arrays.copyOfRange(start, dimensions.length, start.length);
			int[] fileImageStop = java.util.Arrays.copyOfRange(stop, dimensions.length, start.length);
			int[] fileImageStep = java.util.Arrays.copyOfRange(step, dimensions.length, start.length);
			
			//extract the slice required
			AbstractDataset slice = dataSetFromFile.getSlice(fileImageStart, fileImageStop, fileImageStep);
			
			try{
				//add data from this file to the overall result
				result.setSlice(slice, currentResultStart, currentResultStop, resultStep);
			} catch( Exception e){
				throw new IllegalArgumentException("Error adding slice",e);
			}
		
		} while(addStep(currentResultStart, currentResultStop, resultStart, newShape, step, dimensions.length-1));
		
		return result;	
		
	}

	private boolean addStep( int [] currentStart, int [] currentStop, int start[], int [] stop, int [] step, int index){
		if( index < 0)
			return false;
		int newStart = currentStart[index] + step[index];
		if( newStart < stop[index])
		{
			currentStart[index] = newStart;
			currentStop[index] = newStart+1;
			return true;
		} 
		currentStart[index] = start[index];
		currentStop[index] = start[index]+1;
		return addStep( currentStart, currentStop, start, stop, step, index-1);
	}

	public int[] getShape() {
		return shape;
	}

}

class AbstractDatasetRecord{
	AbstractDataset dataset;
	int [] location;
	public AbstractDatasetRecord(AbstractDataset dataset, int[] location) {
		super();
		this.dataset = dataset;
		//need to make copy rather than take reference to prevent value being
		//changed under the covers
		this.location = java.util.Arrays.copyOf(location, location.length);
	}
	
}
