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
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.gda.monitor.IMonitor;

public class ImageStackLoader implements ILazyLoader {

	List<String> imageFilenames;
	int[] shape;
	int dtype;
	
	
	public int getDtype() {
		return dtype;
	}

	public ImageStackLoader(List<String> imageFilenames) throws Exception {
		this.imageFilenames = imageFilenames;
		// load the first image to get the shape of the whole thing
		int stack = imageFilenames.size();
		DataHolder dh = LoaderFactory.getData(imageFilenames.get(0), null);
		if ( dh == null)
			throw new ScanFileHolderException("Unable to load " + imageFilenames.get(0));
		AbstractDataset data = dh.getDataset(0);
		dtype = data.getDtype(); 
		int[] data_shape = data.getShape();
		
		shape = new int[data_shape.length + 1];
		shape[0] = stack;
		for( int i=0;i<data_shape.length;i++){
			shape[i+1]=data_shape[i];
		}
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
		int[] newShape = AbstractDataset.checkSlice(shape, start, stop, start, stop, step);

		AbstractDataset result = AbstractDataset.zeros(newShape, dtype);

		for (int i = start[0], j = 0; i < stop[0]; i += step[0], j++) {
			
			// load the file
			DataHolder data;
			try {
				data = LoaderFactory.getData(imageFilenames.get(i), mon);
			} catch (Exception e) {
				throw new ScanFileHolderException("Cannot load image in image stack",e);
			}
			
			AbstractDataset abstractDataset = data.getDataset(0);
			
			int[] imageStart = new int[] {start[1],start[2]};
			int[] imageStop = new int[] {stop[1],stop[2]};
			int[] imageStep = new int[] {step[1],step[2]};
			
			AbstractDataset slice = abstractDataset.getSlice(imageStart, imageStop, imageStep);
			
			int[] resultStart = new int[] {j,0,0};
			int[] resultStop = new int[] {j+1,newShape[1], newShape[2]};
			int[] resultStep = new int[] {1,1,1};
			result.setSlice(slice, resultStart, resultStop, resultStep);
		}
		
		return result;	
		
	}

	public int[] getShape() {
		return shape;
	}

}
