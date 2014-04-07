/*-
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

import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

public class ImageStackLoader implements ILazyLoader {

	private List<String> imageFilenames;
	private int[] shape;
	private int dtype;
	private Class<? extends IFileLoader> loaderClass;

	public int getDtype() {
		return dtype;
	}

	public ImageStackLoader(List<String> imageFilenames, IMonitor mon) throws Exception {
		this(imageFilenames, LoaderFactory.getData(imageFilenames.get(0), mon), mon);
	}

	@SuppressWarnings("unused")
	public ImageStackLoader(List<String> imageFilenames, IDataHolder dh, IMonitor mon) throws Exception {
		this.imageFilenames = imageFilenames;
		// load the first image to get the shape of the whole thing
		int stack = imageFilenames.size();
		loaderClass = dh.getLoaderClass();

		ILazyDataset data = dh.getLazyDataset(0);
		dtype = AbstractDataset.getDType(data);
		int[] dShape = data.getShape();

		shape = new int[dShape.length + 1];
		shape[0] = stack;
		for (int i = 0; i < dShape.length; i++) {
			shape[i + 1] = dShape[i];
		}
	}

	@Override
	public boolean isFileReadable() {
		return true;
	}

	private AbstractDataset getFullStack() throws Exception {
		
    	IDataHolder      data = LoaderFactory.getData(loaderClass, imageFilenames.get(0), true, new IMonitor.Stub());
    	int size = data.getDataset(0).getSize();

    	AbstractDataset result = AbstractDataset.zeros(shape, dtype);
     	Object          buffer = result.getBuffer();
		// this assumes that all files have images of the same shape and type
		int image = 0;
        for (String path : imageFilenames) {
        	final IDataHolder      d = LoaderFactory.getData(loaderClass, path, true, new IMonitor.Stub());
        	final AbstractDataset i = DatasetUtils.convertToAbstractDataset(d.getDataset(0));
        	System.arraycopy(i.getBuffer(), 0, buffer, image*size, size);
        	++image;
		}
        
        return result;
	}

	@Override
	public AbstractDataset getDataset(IMonitor mon, int[] shape, int[] start, int[] stop, int[] step) throws Exception {
		
		if (start==null && step==null) return getFullStack();// Might cause out of memory!
		                                                     // But this allows expressions of the stack to work if the stack fit in memory.

		int rank = shape.length;
		int[] lstart, lstop, lstep;

		if (step == null) {
			lstep = new int[rank];
			Arrays.fill(lstep, 1);
		} else {
			lstep = step;
		}

		if (start == null) {
			lstart = new int[rank];
		} else {
			lstart = start;
		}

		if (stop == null) {
			lstop = new int[rank];
		} else {
			lstop = stop;
		}
		int[] newShape = AbstractDataset.checkSlice(shape, start, stop, lstart, lstop, lstep);

		AbstractDataset result = AbstractDataset.zeros(newShape, dtype);

		IDataHolder data = null;
		// FIXME this seems to be designed for three dimensions only
		int[] imageStart = new int[] {lstart[1], lstart[2]};
		int[] imageStop = new int[] {lstop[1], lstop[2]};
		int[] imageStep = new int[] {lstep[1], lstep[2]};
		int[] resultStart = new int[3];
		int[] resultStop = newShape.clone();
		int[] resultStep = new int[] {1,1,1};
		for (int i = lstart[0], j = 0; i < lstop[0]; i += lstep[0], j++) {
			// load the file
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

			IDataset slice = data.getLazyDataset(0).getSlice(imageStart, imageStop, imageStep);
			resultStart[0] = j;
			resultStop[0] = j + 1;
			result.setSlice(slice, resultStart, resultStop, resultStep);
		}

		IMetaData meta = LoaderFactory.getLockedMetaData();
		if (meta!=null) {
			 result.setMetadata(meta); // Locked overrides all
		} else {
		    meta = data!=null && result.getMetadata()==null ? data.getMetadata() : null;
			if (meta!=null) result.setMetadata(meta);
		}
		return result;	
		
	}

	public int[] getShape() {
		return shape;
	}

}
