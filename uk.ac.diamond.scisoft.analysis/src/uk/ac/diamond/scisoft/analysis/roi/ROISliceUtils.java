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
		int length = findPositionOfClosestValueInAxis(axis,  roiStart[0]+roiLength[0]);

		Slice xSlice = new Slice(start, length, 1);
		//Slice xSlice = new Slice(start, start+1, 1);
		
		slices[dim] = xSlice;
		
		AbstractDataset dataBlock = (AbstractDataset)lz.getSlice(slices);
		
		slices[dim].setStart(0);
		slices[dim].setStop(1);
		
		AbstractDataset datasetStart = DatasetUtils.cast((AbstractDataset)lz.getSlice(slices).squeeze(),AbstractDataset.FLOAT32);
		AbstractDataset result = AbstractDataset.zeros(datasetStart, AbstractDataset.FLOAT32);
		AbstractDataset datasetEnd = AbstractDataset.zeros(datasetStart);
		
		for (int i = 1; i < (length-start); i++) {
			slices[dim].setStart(i);
			slices[dim].setStop(i+1);
			datasetEnd = dataBlock.getSlice(slices);
			datasetStart.iadd(datasetEnd);
			datasetStart.idivide(2);
			datasetStart.imultiply(axis.getDouble(start+i)-axis.getDouble(start+i-1));
			result.iadd(datasetStart);
			datasetStart = datasetEnd;
		}
		return result;

	}
	
	public static IDataset getTrapiziumArea(IDataset axis, ILazyDataset lz, RectangularROI roi, int dim){
		Slice[] slices = new Slice[lz.getRank()];
		double[] roiStart = roi.getPoint();
		double[] roiLength = roi.getLengths();
		//TODO needs to be checked and tested
		int start = findPositionOfClosestValueInAxis(axis, roiStart[0]);
		int length = findPositionOfClosestValueInAxis(axis,  roiStart[0]+roiLength[0]);

		Slice xSlice = new Slice(start, start+1, 1);

		slices[dim] = xSlice;
		
		AbstractDataset dataStart = DatasetUtils.cast((AbstractDataset)lz.getSlice(slices),AbstractDataset.FLOAT32);
		
		slices[dim].setStart(length);
		slices[dim].setStop(length+1);
		dataStart.iadd(lz.getSlice(slices));
		dataStart.idivide(2);
		dataStart.imultiply(roiLength[0]);
		
		return dataStart.squeeze();
	}

	public static int findPositionOfClosestValueInAxis(IDataset dataset, Double val) {
		return Maths.abs(Maths.subtract(dataset, val)).argMin();
	}


}
