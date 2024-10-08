/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.dataset.roi;

import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.impl.function.LineSample;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;

/**
 * Class with utils methods for slicing 3D datasets with ROIs
 */
public class ROISliceUtils {

	/**
	 * Method to find the index of matching the value closest to val in the dataset<br>
	 * 
	 * @param dataset
	 * @param val
	 * @return position
	 */
	public static int findPositionOfClosestValueInAxis(IDataset dataset, Double val) {
		return Maths.abs(Maths.subtract(dataset, val)).argMin();
	}

	/**
	 * Method to get x,y slices from a rectangular roi<br>
	 * 
	 * If length of roi is less than 1, slice of width 1 is returned<br>
	 * 
	 * @param roi
	 * @param step
	 * @return slices
	 */
	public static Slice[] getSlicesFromRectangularROI(IRectangularROI roi, int step) {

		Slice[] slices = new Slice[2];

		for (int i = 0; i < 2; i++) {
			slices[i] = new Slice(Math.max(0, (int) Math.floor(roi.getValue(i))), Math.max(1, (int) Math.ceil(roi.getValue(i) + roi.getLength(i))), step);
		}
		return slices;
	}

	/**
	 * Method to slice a dataset using an roi to define the sliced range <br>
	 * Order must be at least 2 ints long and defines the x,y slice dimensions<br>
	 * 
	 * @param lz
	 * @param slices
	 * @param order
	 * @param step
	 * @return slices
	 * @throws Exception 
	 */
	public static Dataset getDataset(ILazyDataset lz, IRectangularROI roi, Slice[] slices, int[] order, int step, IMonitor monitor) throws Exception {
		
		Slice[] roiSlice = getSlicesFromRectangularROI(roi, step);
		if (monitor != null && monitor.isCancelled()) return null;
		Slice[] sl = checkSlices(lz.getRank(), slices);
		
		sl[order[0]] = roiSlice[0];
		sl[order[1]] = roiSlice[1];
		if (monitor != null && monitor.isCancelled()) return null;
		return DatasetUtils.convertToDataset(lz.getSlice(monitor, sl));
		
	}
	
	/**
	 * Method to return the slice corresponding to the area selected by an x-axis range roi<br>
	 * 
	 * Only for ROIs on 1D plots.
	 * 
	 * @param roi
	 * @param axis
	 * @param step
	 * @return slice
	 */
	public static Slice getSliceFromRectangularXAxis1D(IRectangularROI roi, IDataset axis, int step) {
		
		double roiStart = roi.getPointX();

		int start = findPositionOfClosestValueInAxis(axis, roiStart);
		int end   = findPositionOfClosestValueInAxis(axis, roiStart + roi.getLength(0));
		
		if (start > end) {
			int tmp = start;
			start = end;
			end = tmp;
		}
		
		Slice xSlice = new Slice(start, end + 1, step);

		return xSlice;
	}

	/**
	 * Method to get trapezium integration corresponding to the area selected by an x-axis range roi<br>
	 * 
	 * Only for ROIs on 1D plots, along the dimension dim of the lazy dataset.<br>
	 * 
	 * @param lz
	 * @param axis
	 * @param roi
	 * @param slices
	 * @param dim
	 * @param step
	 * 
	 * @return dataset
	 * @throws Exception 
	 */
	public static Dataset getAxisDatasetTrapzSum(ILazyDataset lz, IDataset axis, IRectangularROI roi, Slice[] slices, int dim, int step, IMonitor monitor) throws Exception {
		Slice[] sl = checkSlices(lz.getRank(), slices);

		sl[dim] = getSliceFromRectangularXAxis1D(roi,axis, step);
		if (monitor != null && monitor.isCancelled()) return null;
		int start = sl[dim].getStart();
		int end = sl[dim].getEnd();

		Dataset dataBlock = DatasetUtils.convertToDataset(lz.getSlice(monitor, sl));
		if (monitor != null && monitor.isCancelled()) return null;
		sl = new Slice[lz.getRank()];
		
		sl[dim] = new Slice(0,1);

		Dataset datasetStart = dataBlock.getSlice(monitor, sl).cast(FloatDataset.class);
		Dataset result = DatasetFactory.zeros(datasetStart);
		Dataset datasetEnd = DatasetFactory.zeros(datasetStart);
		
		for (int i = 1; i < (end-start+1); i++) {
			if (monitor != null && monitor.isCancelled()) return null;
			sl[dim].setStart(i);
			sl[dim].setStop(i+1);
			datasetEnd = dataBlock.getSlice(monitor, sl).cast(FloatDataset.class);
			datasetStart.iadd(datasetEnd);
			datasetStart.idivide(2.0);
			double val = Math.abs(axis.getDouble(start+i)-axis.getDouble(start+i-1));
			datasetStart.imultiply(val);
			result.iadd(datasetStart);
			datasetStart = datasetEnd;
		}
		
		double roiStart = roi.getPointX();
		double roiLength = roi.getLength(0);
		
		result.setName("sum_" + Double.toString(roiStart) +"_" + Double.toString(roiStart + roiLength));
		
		return result.squeeze();
	}
	
	/**
	 * Method to get trapezium area corresponding to the region selected by an x-axis range roi (start-end)<br>
	 * 
	 * Only for ROIs on 1D plots, along the dimension dim of the lazy dataset.<br>
	 * 
	 * @param lz
	 * @param axis
	 * @param roi
	 * @param slices
	 * @param dim
	 * @param step
	 * @return dataset
	 */
	public static Dataset getTrapeziumArea(ILazyDataset lz, IDataset axis, IRectangularROI roi, Slice[] slices, int dim, int step, IMonitor monitor) throws Exception {
		Slice[] sl = checkSlices(lz.getRank(), slices);
		if (monitor != null && monitor.isCancelled()) return null;
		sl[dim] = getSliceFromRectangularXAxis1D(roi,axis, step);

		int start = sl[dim].getStart();
		int end = sl[dim].getEnd();
		
		sl[dim].setStop(start+1);
		if (monitor != null && monitor.isCancelled()) return null;
		Dataset dataStart = DatasetUtils.cast(FloatDataset.class,lz.getSlice(monitor, sl));
		sl[dim].setStart(end);
		sl[dim].setStop(end+1);
		if (monitor != null && monitor.isCancelled()) return null;
		dataStart.iadd(DatasetUtils.cast(FloatDataset.class,lz.getSlice(monitor, sl)));
		dataStart.idivide(2.0);
		if (monitor != null && monitor.isCancelled()) return null;
		dataStart.imultiply(Maths.abs(axis.getDouble(end)-axis.getDouble(start)));
		return dataStart.squeeze();
	}
	
	/**
	 * Method to trapezium integration minus linear baseline corresponding to the region selected by an x-axis range roi<br>
	 * 
	 * Only for ROIs on 1D plots, along the dimension dim of the lazy dataset.<br>
	 * 
	 * @param lz
	 * @param axis
	 * @param roi
	 * @param slices
	 * @param dim
	 * @param step
	 * @return dataset
	 * @throws Exception 
	 */
	public static Dataset getAxisDatasetTrapzSumBaselined(ILazyDataset lz, IDataset axis, IRectangularROI roi, Slice[] slices, int dim, int step,boolean baseline,IMonitor monitor) throws Exception {

		final Dataset output = getAxisDatasetTrapzSum(lz, axis, roi, slices, dim, step, monitor);
		
		if (baseline) {
			if (monitor != null && monitor.isCancelled()) return null;
			final Dataset datasetBasline = getTrapeziumArea(lz, axis, roi, slices, dim, step, monitor);
			output.isubtract(datasetBasline);
			
			if (output.getName() != null) {
				output.setName(output.getName() + "_baseline");
			}
		}

		return output;
	}
	
	/**
	 * Method to return the dataset corresponding to the area selected by an y-axis range roi<br>
	 * 
	 * Only for ROIs on 1D plots.
	 * 
	 * @param lz
	 * @param roi
	 * @param slices
	 * @param dim
	 * @return dataset
	 * @throws Exception 
	 */
	public static IDataset getYAxisDataset2D(ILazyDataset lz, RectangularROI roi, Slice[] slices, int dim, IMonitor monitor) throws Exception{
		
		Slice[] sl = checkSlices(lz.getRank(), slices);
		
		int roiStart = Math.max(0, (int) roi.getValue(1));
		
		Slice xSlice = new Slice(roiStart, roiStart + 1, 1);
		
		sl[dim] = xSlice;
		if (monitor != null && monitor.isCancelled()) return null;
		IDataset out = lz.getSlice(monitor, sl);
		
		return out.squeeze();
	}

	/**
	 * Method to return the dataset corresponding to the area selected by an y-axis range roi<br>
	 * 
	 * Only for ROIs on 1D plots.
	 * 
	 * @param lz
	 * @param roi
	 * @param slices
	 * @param dim
	 * @return dataset
	 * @throws Exception 
	 */
	public static Dataset getYAxisDataset2DAverage(ILazyDataset lz, RectangularROI roi, Slice[] slices, int dim, IMonitor monitor) throws Exception{
		return getAxisDataset2DAverage(lz, roi, slices, dim, monitor, false);

	}
	
	/**
	 * Method to return the dataset corresponding to the area selected by an x-axis range roi<br>
	 * 
	 * Only for ROIs on 1D plots.
	 * 
	 * @param lz
	 * @param roi
	 * @param slices
	 * @param dim
	 * @return dataset
	 * @throws Exception 
	 */
	public static Dataset getXAxisDataset2DAverage(ILazyDataset lz, RectangularROI roi, Slice[] slices, int dim, IMonitor monitor) throws Exception{
		return getAxisDataset2DAverage(lz, roi, slices, dim, monitor, true);

	}
	
	private static Dataset getAxisDataset2DAverage(ILazyDataset lz, RectangularROI roi, Slice[] slices, int dim, IMonitor monitor, boolean isX) throws Exception{
		
		int roiDim = isX ? 0 : 1;
		
		Slice[] sl = checkSlices(lz.getRank(), slices);
		
		int roiStart = Math.max(0, (int) roi.getValue(roiDim));
		int roiEnd = (int) Math.ceil(roiStart + roi.getLength(roiDim));
		
		Slice xSlice = new Slice(roiStart, roiEnd, 1);
		
		sl[dim] = xSlice;
		
		Dataset out = DatasetUtils.convertToDataset(lz.getSlice(monitor, sl));
		if (monitor != null && monitor.isCancelled()) return null;
		out = out.mean(dim);
		
		return out.squeeze();

	}
	
	/**
	 * Method to slice a dataset using an roi to define the sliced range <br>
	 * Order must be at least 2 ints long and defines the x,y slice dimensions<br>
	 * 
	 * @param lz
	 * @param slices
	 * @param order
	 * @param step
	 * @return slices
	 */
	public static IDataset getDataset(ILazyDataset lz, LinearROI roi, Slice[] slices, int[] order, int step, IMonitor monitor) throws Exception {

		int[] start = roi.getIntPoint();
		int[] end = roi.getIntEndPoint();

		LineSample ls = new LineSample(start[1], start[0], end[1], end[0], 1);
		
		int len = (int)Math.floor(roi.getLength()) +1;

		
		IDataset[] ds = new IDataset[len];

		Slice[] sl = checkSlices(lz.getRank(), slices);

		Slice xSlice = new Slice();
		Slice ySlice = new Slice();

		sl[order[1]] = xSlice;
		sl[order[0]] = ySlice;


		int[] points;

		
		
		int[] shape = new int[]{1,1};

		for (int i = 0; i < len; i++) {
			if (monitor != null && monitor.isCancelled()) return null;
			points = ls.getPoint(i);

			sl[order[0]].setStart(points[0]);
			sl[order[0]].setStop(points[0]+1);
			sl[order[1]].setStart(points[1]);
			sl[order[1]].setStop(points[1]+1);

			//ds[i] = lz.getSlice(sl).squeeze().getSlice();
			ds[i] = lz.getSlice(monitor, sl).squeeze();
			shape = ds[i].getShape();

			ds[i].setShape(new int[]{1,shape[0]});
		}

		return DatasetUtils.concatenate(ds, 0);
	}
	
	private static Slice[] checkSlices(int rank, Slice[] slices) {
		Slice[] sl = new Slice[rank];

		if (slices != null && rank == slices.length) {
			sl = slices.clone();
		} else if ( slices != null && rank > slices.length){
			sl = new Slice[rank];
			for (int i = 0 ; i < slices.length ; i++) {
				sl[i] = slices[i];
			}
		} else if ( slices != null && rank < slices.length){
			sl = new Slice[rank];
			for (int i = 0 ; i < rank ; i++) {
				sl[i] = slices[i];
			}
		}
		return sl;
	}

	/**
	 * Create Slice from given parameters
	 * @param start
	 * @param length
	 * @param max
	 * @return 1D slice that contains start, start+length (or null if values envelope 0,max)
	 */
	public static final Slice createSlice(double start, double length, int max) {
		int lo = Math.max(0, (int) Math.floor(start));
		int hi = Math.min(max, (int) Math.ceil(start + length));
		return lo <= 0 && hi >= max ? null : new Slice(lo, hi);
	}

	/**
	 * Create SliceND from given parameters
	 * @param beg starting values in fastest to slowest order (i.e. X,Y,Z)
	 * @param end ending values in fastest to slowest order (i.e. X,Y,Z)
	 * @param shape shape in slowest to fastest order (i.e. Z,Y,X)
	 * @return ND slice
	 */
	public static final SliceND createSliceND(double[] beg, double[] end, int[] shape) {
		final int r = beg.length;
		if (r != end.length || r != shape.length) {
			throw new IllegalArgumentException("All input arrays must be same length");
		}
		int[] sStart = new int[r];
		int[] sStop  = new int[r];
		for (int i = 0, j = r - 1; i < r; i++, j--) {
			sStart[i] = Math.max(0, (int) Math.floor(beg[j]));
			sStop[i]  = Math.min(shape[i], (int) Math.ceil(end[j]));
		}
		return new SliceND(shape, sStart, sStop, null);
	}

	/**
	 * Create SLiceND from rectangular ROI
	 * @param rect
	 * @param shape
	 * @return ND slice
	 */
	public static final SliceND createSliceND(IRectangularROI rect, int[] shape) {
		return createSliceND(rect.getPointRef(), rect.getEndPoint(), shape);
	}
}
