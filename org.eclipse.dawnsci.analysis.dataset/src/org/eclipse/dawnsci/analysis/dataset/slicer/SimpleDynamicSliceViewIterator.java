/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.dataset.slicer;

import java.util.Arrays;

import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.IDynamicDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDynamicSliceViewIterator implements ISliceViewIterator {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleDynamicSliceViewIterator.class);
	
	private IDynamicDataset lazy;
	private IDynamicDataset key;
	private int dataSize;
	private int maxNumber;
	private int[] axes;
	private SourceInformation source;
	private boolean next = false;

	private boolean last = false;

	private int maxTimeout = 50000;
	private int waitTime = 1000;

	private DynamicSliceNDIterator iterator;

	public SimpleDynamicSliceViewIterator(IDynamicDataset lazy, IDynamicDataset key, int dataSize, int maxNumber) {
		
		this.lazy = lazy;
		this.key = key;
		this.dataSize = dataSize;
		this.maxNumber = maxNumber;
		this.axes = new int[dataSize];
		
		createInnerIterator();
		
		int lr = lazy.getRank();
		
		for (int i = 0; i < axes.length; i++) {
			axes[i] = lr - 1 -i;
		}
		
		Object ssm = lazy.getFirstMetadata(SliceFromSeriesMetadata.class);
		if (ssm != null && ssm instanceof SliceFromSeriesMetadata && ((SliceFromSeriesMetadata)ssm).getSourceInfo() != null) source = ((SliceFromSeriesMetadata)ssm).getSourceInfo();
		else logger.warn("Lazy dataset contains no source information");
	}

	private void createInnerIterator() {
		try {
			iterator = new DynamicSliceNDIterator(lazy.getShape(), key.getSlice(),lazy.getRank()-dataSize);
		} catch (DatasetException e) {
			throw new IllegalArgumentException("Could not build iterator from datasets",e);
		}
	}
	
	/**
	 * Timeout in Milliseconds
	 * 
	 * @param maxTimeout
	 */
	public void setMaxTimeout(int maxTimeout) {
		this.maxTimeout = maxTimeout;
		this.waitTime = maxTimeout/50;
	}
	
	public void updateShape() {
		try {
			lazy.refreshShape();
			int[] current = lazy.getShape();
			key.refreshShape();
			
			logger.info("Dynamic iterator setting max shape" + Arrays.toString(current));

			iterator.updateShape(current, key.getSlice());
		} catch (Exception e) {
			logger.error("Error refreshing axes",e);
		}
	}
	
	@Override
	public boolean hasNext() {
		
		boolean hasNext = next;
		double time = 0;
		
		if (maxNumber == iterator.getCount()) {
			last = true;
		}
		
		while (time < maxTimeout && !hasNext && !last) {
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			} finally {
				//apply updates in finally in case
				//interrupt breaks loop.
				updateShape();
				time += waitTime;
				hasNext = iterator.hasNext();
			}
		}
		
		if (time >= maxTimeout) {
			last = true;
			logger.error("Dynamic slice view iterator has timed-out");
		}
		
		return hasNext;
	}

	@Override
	public ILazyDataset next() {

		SliceND current = iterator.getCurrentSlice();
		current = SliceND.createSlice(lazy, current.getStart(), current.getStop(),current.getStep());
		
		ILazyDataset view = lazy.getSliceView(current);
		view.clearMetadata(SliceFromSeriesMetadata.class);
		
		int count = iterator.getCount();
		
		SliceInformation sl = new SliceInformation(current,
				current.clone(), new SliceND(lazy.getShape()),
				axes, last ? count : -1, count - 1);
		
		SliceFromSeriesMetadata m = new SliceFromSeriesMetadata(source, sl);
		
		view.setMetadata(m);
		
		next = iterator.hasNext();
		
		return view;
	}

	@Override
	public void reset() {
		iterator.reset();
		last = false;
		updateShape();
		next = iterator.hasNext();
	}

	/**
	 * Get the shape of the subsampled view
	 * 
	 * @return shape
	 */
	@Override
	public int[] getShape(){
		return lazy.getShape();
	}
}
