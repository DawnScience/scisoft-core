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

import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.gda.monitor.IMonitor;

public class ImageStackLoader implements ILazyLoader {

	List<String> imageFilenames;
	int[] shape;
	int dtype;
	private Class<? extends AbstractFileLoader> loaderClass;
	
	
	public int getDtype() {
		return dtype;
	}

	public ImageStackLoader(List<String> imageFilenames, IMonitor mon) throws Exception {
		this.imageFilenames = imageFilenames;
		// load the first image to get the shape of the whole thing
		int stack = imageFilenames.size();
		DataHolder dh = LoaderFactory.getData(imageFilenames.get(0), mon);
		if (dh == null)
			throw new ScanFileHolderException("Unable to load " + imageFilenames.get(0));
		loaderClass = dh.getLoaderClass();

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
			DataHolder data = null;
			if (loaderClass != null) {
				try {
					data = LoaderFactory.getData(loaderClass, imageFilenames.get(i), true, mon);
				} catch (Exception e) {
					// do nothing and try with all registered loaders
				}
			}
			if (data == null) {
				try {
					data = LoaderFactory.getData(imageFilenames.get(i), mon);
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
