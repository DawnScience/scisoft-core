/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.dataset.function.LineSample;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

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
	public static Slice[] getSlicesFromRectangularROI(RectangularROI roi, int step) {

		Slice[] slices = new Slice[2];

		int[] roiStart = roi.getIntPoint();
		int[] roiLength = roi.getIntLengths();

		if (roiLength[0] < 1) roiLength[0] = 1;
		if (roiLength[1] < 1) roiLength[1] = 1;

		slices[0] = new Slice(roiStart[0], roiStart[0] + roiLength[0], step);
		slices[1] = new Slice(roiStart[1], roiStart[1] + roiLength[1], step);

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
	 */
	public static IDataset getDataset(ILazyDataset lz, RectangularROI roi, Slice[] slices, int[] order, int step) {
		
		Slice[] roiSlice = getSlicesFromRectangularROI(roi, step);
		
		Slice[] sl = checkSlices(lz.getRank(), slices);
		
		sl[order[0]] = roiSlice[0];
		sl[order[1]] = roiSlice[1];
		
		return lz.getSlice(sl);
		
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
	public static Slice getSliceFromRectangularXAxis1D(RectangularROI roi, IDataset axis, int step) {
		
		double[] roiStart = roi.getPoint();
		double[] roiLength = roi.getLengths();

		int start = findPositionOfClosestValueInAxis(axis, roiStart[0]);
		int end = findPositionOfClosestValueInAxis(axis,  roiStart[0]+roiLength[0]);
		
		Slice xSlice = new Slice(start, end+1, step);

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
	 */
	public static IDataset getAxisDatasetTrapzSum(ILazyDataset lz, IDataset axis, RectangularROI roi, Slice[] slices, int dim, int step) {
		

		Slice[] sl = checkSlices(lz.getRank(), slices);

		sl[dim] = getSliceFromRectangularXAxis1D(roi,axis, step);

		int start = sl[dim].getStart();
		int end = sl[dim].getEnd();

		AbstractDataset dataBlock = (AbstractDataset)lz.getSlice(sl);
		
		sl = new Slice[lz.getRank()];
		
		sl[dim] = new Slice(0,1);

		AbstractDataset datasetStart = DatasetUtils.cast(dataBlock.getSlice(sl),Dataset.FLOAT32);
		AbstractDataset result = AbstractDataset.zeros(datasetStart, Dataset.FLOAT32);
		AbstractDataset datasetEnd = AbstractDataset.zeros(datasetStart);

		for (int i = 1; i < (end-start+1); i++) {
			sl[dim].setStart(i);
			sl[dim].setStop(i+1);
			datasetEnd = DatasetUtils.cast(dataBlock.getSlice(sl),Dataset.FLOAT32);
			datasetStart.iadd(datasetEnd);
			datasetStart.idivide(2.0);
			double val = axis.getDouble(start+i)-axis.getDouble(start+i-1);
			datasetStart.imultiply(val);
			result.iadd(datasetStart);
			datasetStart = datasetEnd;
		}
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
	public static IDataset getTrapiziumArea(ILazyDataset lz, IDataset axis, RectangularROI roi, Slice[] slices, int dim, int step){
		Slice[] sl = checkSlices(lz.getRank(), slices);

		sl[dim] = getSliceFromRectangularXAxis1D(roi,axis, step);

		int start = sl[dim].getStart();
		int end = sl[dim].getEnd();
		
		sl[dim].setStop(start+1);
		
		AbstractDataset dataStart = DatasetUtils.cast(lz.getSlice(sl),Dataset.FLOAT32);
		sl[dim].setStart(end);
		sl[dim].setStop(end+1);
		dataStart.iadd(DatasetUtils.cast(lz.getSlice(sl),Dataset.FLOAT32));
		dataStart.idivide(2.0);
		dataStart.imultiply(axis.getDouble(end)-axis.getDouble(start));
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
	 */
	public static IDataset getAxisDatasetTrapzSumBaselined(ILazyDataset lz, IDataset axis, RectangularROI roi, Slice[] slices, int dim, int step,boolean baseline) {

		final AbstractDataset output = ((AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum( lz,  axis,  roi,  slices,  dim,  step));

		if (baseline) {
			final IDataset datasetBasline = ROISliceUtils.getTrapiziumArea( lz,  axis,  roi,  slices,  dim,  step);
			output.isubtract(datasetBasline);
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
	 */
	public static IDataset getYAxisDataset2D(ILazyDataset lz, RectangularROI roi, Slice[] slices, int dim){
		
		Slice[] sl = checkSlices(lz.getRank(), slices);
		
		int[] roiStart = roi.getIntPoint();
		
		Slice xSlice = new Slice(roiStart[1], roiStart[1]+1, 1);
		
		sl[dim] = xSlice;
		
		IDataset out = lz.getSlice(sl);
		
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
	 */
	public static IDataset getYAxisDataset2DAverage(ILazyDataset lz, RectangularROI roi, Slice[] slices, int dim){
		
		Slice[] sl = checkSlices(lz.getRank(), slices);
		
		int[] roiStart = roi.getIntPoint();
		int[] roiEnd = roi.getIntLengths();
		
		Slice xSlice = new Slice(roiStart[1], roiStart[1]+roiEnd[1], 1);
		
		sl[dim] = xSlice;
		
		AbstractDataset out = (AbstractDataset)lz.getSlice(sl);
		
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
	public static IDataset getDataset(ILazyDataset lz, LinearROI roi, Slice[] slices, int[] order, int step) {

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
			points = ls.getPoint(i);

			sl[order[0]].setStart(points[0]);
			sl[order[0]].setStop(points[0]+1);
			sl[order[1]].setStart(points[1]);
			sl[order[1]].setStop(points[1]+1);

			//ds[i] = lz.getSlice(sl).squeeze().getSlice();
			ds[i] = lz.getSlice(sl).squeeze();
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
}
