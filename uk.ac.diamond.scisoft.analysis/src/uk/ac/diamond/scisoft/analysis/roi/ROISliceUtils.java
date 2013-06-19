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

	public static IDataset getDataset(ILazyDataset lz,RectangularROI roi,int[] dims) {

		Slice[] slices = new Slice[lz.getRank()];

		int[] roiStart = roi.getIntPoint();
		int[] roiLength = roi.getIntLengths();

		Slice xSlice = new Slice(roiStart[0], roiStart[0] + roiLength[0], 1);
		Slice ySlice = new Slice(roiStart[1], roiStart[1] + roiLength[1], 1);

		slices[dims[0]] = xSlice;
		slices[dims[1]] = ySlice;

		return lz.getSlice(slices);

	}

	public static IDataset getAxisDataset(IDataset axis, ILazyDataset lz,RectangularROI roi,int dim) {

		Slice[] slices = new Slice[lz.getRank()];

		double[] roiStart = roi.getPoint();
		double[] roiLength = roi.getLengths();

		int start = findPositionOfClosestValueInAxis(axis, roiStart[0]);
		int length = findPositionOfClosestValueInAxis(axis,  roiStart[0]+roiLength[0]);

		Slice xSlice = new Slice(start, length, 1);

		slices[dim] = xSlice;

		return lz.getSlice(slices);

	}
	
	public static IDataset getAxisDatasetTrapzSum(IDataset axis, ILazyDataset lz,RectangularROI roi,int dim) {
		//TODO needs to be checked and tested
		Slice[] slices = new Slice[lz.getRank()];

		double[] roiStart = roi.getPoint();
		double[] roiLength = roi.getLengths();

		int start = findPositionOfClosestValueInAxis(axis, roiStart[0]);
		int end = findPositionOfClosestValueInAxis(axis,  roiStart[0]+roiLength[0]);
		
		Slice xSlice = new Slice(start, end+1, 1);
		
		slices[dim] = xSlice;
		
		AbstractDataset dataBlock = (AbstractDataset)lz.getSlice(slices);
		
		slices[dim].setStart(0);
		slices[dim].setStop(1);
		
		AbstractDataset datasetStart = DatasetUtils.cast((AbstractDataset)lz.getSlice(slices).squeeze(),AbstractDataset.FLOAT32);
		AbstractDataset result = AbstractDataset.zeros(datasetStart, AbstractDataset.FLOAT32);
		AbstractDataset datasetEnd = AbstractDataset.zeros(datasetStart);
		
		for (int i = 1; i < (end-start+1); i++) {
			slices[dim].setStart(i);
			slices[dim].setStop(i+1);
			datasetEnd = DatasetUtils.cast(dataBlock.getSlice(slices),AbstractDataset.FLOAT32);
			datasetStart.iadd(datasetEnd);
			datasetStart.idivide(2.0);
			double val = axis.getDouble(start+i)-axis.getDouble(start+i-1);
			datasetStart.imultiply(val);
			result.iadd(datasetStart);
			datasetStart = datasetEnd;
		}
		return result;

	}
	
	public static IDataset getTrapiziumArea(IDataset axis, ILazyDataset lz, RectangularROI roi, int dim){
		Slice[] slices = new Slice[lz.getRank()];
		double[] roiStart = roi.getPoint();
		double[] roiLength = roi.getLengths();
		int start = findPositionOfClosestValueInAxis(axis, roiStart[0]);
		int end = findPositionOfClosestValueInAxis(axis,  roiStart[0]+roiLength[0]);
		Slice xSlice = new Slice(start, start+1, 1);

		slices[dim] = xSlice;
		
		AbstractDataset dataStart = DatasetUtils.cast((AbstractDataset)lz.getSlice(slices),AbstractDataset.FLOAT32);
		slices[dim].setStart(end);
		slices[dim].setStop(end+1);
		dataStart.iadd(DatasetUtils.cast((AbstractDataset)lz.getSlice(slices),AbstractDataset.FLOAT32));
		dataStart.idivide(2.0);
		dataStart.imultiply(axis.getDouble(end)-axis.getDouble(start));
		
		return dataStart.squeeze();
	}
	
	public static int[] getImageAxis(int traceDim) {
		int[] allDims = new int[]{2,1,0};
		int[] dims = new int[2];
		
		int i =0;
		for(int j : allDims) {
			if (j != traceDim) {
				dims[i] = j;
				i++;
			}
		}
		
		return dims;
	}

	public static int findPositionOfClosestValueInAxis(IDataset dataset, Double val) {
		return Maths.abs(Maths.subtract(dataset, val)).argMin();
	}
	
	
	public static IDataset getAxisDatasetTrapzSumBaselined(IDataset axis, ILazyDataset data, RectangularROI roi, int dim, boolean baseline) {
		
		final AbstractDataset output = ((AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum(axis ,data, roi, dim));
		
		if (baseline) {
			final IDataset datasetBasline = ROISliceUtils.getTrapiziumArea(axis ,data, roi, dim);
			output.isubtract(datasetBasline);
		}
		
		return output;
	}
	

	public static IDataset getDataset(ILazyDataset lz, LinearROI roi, int[] dims) {
		
		int[] start = roi.getIntPoint();
		int[] end = roi.getIntEndPoint();
		
		LineSample ls = new LineSample(start[1], start[0], end[1], end[0], 1);
		
		int len = (int)Math.floor(roi.getLength());
		
		//List<int[]> points = new ArrayList<int[]>(len);
		
		IDataset[] ds = new IDataset[len];
		
		Slice[] slices = new Slice[lz.getRank()];

		Slice xSlice = new Slice();
		Slice ySlice = new Slice();

		slices[dims[0]] = xSlice;
		slices[dims[1]] = ySlice;
		

		int[] points;

		
		int[] shape = new int[]{1,1};
		
		for (int i = 0; i < len; i++) {
			points = ls.getPoint(i+1);
			
			slices[dims[0]].setStart(points[0]);
			slices[dims[0]].setStop(points[0]+1);
			slices[dims[1]].setStart(points[1]);
			slices[dims[1]].setStop(points[1]+1);
			
			ds[i] = lz.getSlice(slices).squeeze().getSlice();
			
			shape = ds[i].getShape();
			
			ds[i].setShape(new int[]{1,shape[0]});
		}
		
		return DatasetUtils.concatenate(ds, 0);
	}
	
public static IDataset getAxisDataset(ILazyDataset lz, RectangularROI roi, int dim) {
		
		int[] start = roi.getIntPoint();
		
		
		Slice[] slices = new Slice[lz.getRank()];

		Slice traceSlice = new Slice();
		
		traceSlice.setStart(start[1]);
		traceSlice.setStop(start[1]+1);

		slices[dim] = traceSlice;
		
		return lz.getSlice(slices).squeeze().getSlice();

	}
}
