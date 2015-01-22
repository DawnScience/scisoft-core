/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

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

	private Dataset getFullStack() throws Exception {
		
    	IDataHolder      data = LoaderFactory.getData(loaderClass, imageFilenames.get(0), true, new IMonitor.Stub());
    	int size = data.getDataset(0).getSize();

    	Dataset result = DatasetFactory.zeros(shape, dtype);
     	Object          buffer = result.getBuffer();
		// this assumes that all files have images of the same shape and type
		int image = 0;
        for (String path : imageFilenames) {
        	final IDataHolder      d = LoaderFactory.getData(loaderClass, path, true, new IMonitor.Stub());
        	final Dataset i = DatasetUtils.convertToDataset(d.getDataset(0));
        	System.arraycopy(i.getBuffer(), 0, buffer, image*size, size);
        	++image;
		}
        
        return result;
	}

	@Override
	public Dataset getDataset(IMonitor mon, SliceND slice) throws Exception {
		
		if (slice.isAll()) return getFullStack();// Might cause out of memory!
		                                                     // But this allows expressions of the stack to work if the stack fit in memory.

		int[] lstart = slice.getStart();
		int[] lstop  = slice.getStop();
		int[] lstep  = slice.getStep();
		int[] newShape = slice.getShape();

		Dataset result = DatasetFactory.zeros(newShape, dtype);

		IDataHolder data = null;
		// FIXME this seems to be designed for three dimensions only
		int[] imageStart = new int[] {lstart[1], lstart[2]};
		int[] imageStop = new int[] {lstop[1], lstop[2]};
		int[] imageStep = new int[] {lstep[1], lstep[2]};
		int[] resultStart = new int[3];
		int[] resultStop = newShape.clone();
		int[] resultStep = new int[] {1,1,1};
		int n = lstart[0];
		do {
			// load the file
			if (loaderClass != null) {
				try {
					data = LoaderFactory.getData(loaderClass, imageFilenames.get(n), true, mon);
				} catch (Exception e) {
					// do nothing and try with all registered loaders
				}
			}
			if (data == null) {
				try {
					data = LoaderFactory.getData(imageFilenames.get(n), mon);
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

			ILazyDataset sliced = data.getLazyDataset(0).getSliceView(imageStart, imageStop, imageStep);
			resultStop[0] = resultStart[0] + 1;
			result.setSlice(sliced, resultStart, resultStop, resultStep);
			resultStart[0]++;
			n += lstep[0];
		} while (resultStart[0] < newShape[0]);

		IMetadata meta = LoaderFactory.getLockedMetaData();
		if (meta!=null) {
			 result.setMetadata(meta); // Locked overrides all
		} else {
		    meta = result.getMetadata()==null ? data.getMetadata() : null;
			if (meta!=null) result.setMetadata(meta);
		}

		return result;	
	}

	public int[] getShape() {
		return shape;
	}

}
