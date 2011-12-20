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

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;

import java.util.Arrays;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.gda.monitor.IMonitor;

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
	
	
	public int getDtype() {
		return dtype;
	}

	public ImageStackLoaderEx(int [] dimensions, String[] imageFilenames) throws Exception {
		if( dimensions == null || dimensions.length<1 || dimensions.length>2)
			throw new IllegalArgumentException("dimensions invalid");
		int totalLength = AbstractDataset.calcSize(dimensions);
		if( imageFilenames == null || imageFilenames.length != totalLength)
			throw new IllegalArgumentException("imageFilenames.length != "+ totalLength);

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
			DataHolder data = null;
			try {
				data = LoaderFactory.getData(filename, mon);
			} catch (Exception e) {
				throw new ScanFileHolderException("Cannot load image in image stack", e);
			}

			if (data == null) {
				throw new ScanFileHolderException("Cannot load image in image stack");
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
