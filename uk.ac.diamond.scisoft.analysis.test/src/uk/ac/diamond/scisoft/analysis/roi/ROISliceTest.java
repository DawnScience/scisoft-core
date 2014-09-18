/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.LinearROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.junit.Assert;
import org.junit.Test;

public class ROISliceTest {
	
	@Test
	public void testNDSlices() {
		Dataset input = DatasetFactory.ones(new int[] {10,20,30,40}, Dataset.FLOAT32);
		
		RectangularROI roi = new RectangularROI(10, 10, 5, 5, 0);
		
		Slice[] slices = new Slice[input.getRank()];
		
		slices[0] = new Slice(0, 1);
		
		int[] order = new int[] {3,2,1,0};
		
		Dataset output = (Dataset) ROISliceUtils.getDataset(input,roi, slices, new int[]{order[0],order[1]}, 1);
		
		output.squeeze();

		int[] shape = output.getShape();
		
		Assert.assertArrayEquals(new int[]{20, 5,5}, shape);
		
		output = (Dataset) ROISliceUtils.getDataset(input,roi, slices, new int[]{order[0],order[1]}, 1);
		
		Dataset mean;
		
		if (order[0] > order[1]) mean = output.mean(order[0]).mean(order[1]);
		else mean = output.mean(order[1]).mean(order[0]);
		
		mean.squeeze();
		shape = mean.getShape();
		Assert.assertEquals(20, shape[0]);
		
	}
	@Test
	public void getAxisDatasetTrapzSumTestNDSlices() {
		//TODO test different dimensions
		//Create ND array and axes
		Dataset input = DatasetFactory.ones(new int[] {10,20,30,40}, Dataset.FLOAT32);
//		Dataset axis0 = DatasetUtils.linSpace(0, 9, 10, Dataset.FLOAT32);
//		Dataset axis1 = DatasetUtils.linSpace(0, 19, 20, Dataset.FLOAT32);
//		Dataset axis2 = DatasetUtils.linSpace(0, 29, 30, Dataset.FLOAT32);
		Dataset axis3 = DatasetUtils.linSpace(0, 39, 40, Dataset.FLOAT32);
		
		//Create ROI and slices
		RectangularROI roi = new RectangularROI(10, 10, 5, 5, 0);
		Slice[] slices = new Slice[input.getRank()];
		
		//Test basic slice from 0-1
		slices[0] = new Slice(0, 1);
		int[] order = new int[] {3,2,1,0};
		
		Dataset out0 = (Dataset) ROISliceUtils.getAxisDatasetTrapzSum(input, axis3,roi, slices, order[0], 1);
		
		//Dataset out0 = (Dataset)ROISliceUtils.getAxisDatasetTrapzSum(axis0, input, roi, 0);
		Assert.assertArrayEquals(new int[]{20, 30},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0, 0),0);
		
		//Test basic slice from 3-4
		slices[0] = new Slice(3, 4);
		out0 = (Dataset) ROISliceUtils.getAxisDatasetTrapzSum(input, axis3,roi, slices, order[0], 1);
		
		Assert.assertArrayEquals(new int[]{20, 30},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0, 0),0);
		
		
		//Test basic slice from 3-4, reduced range
		slices[0] = new Slice(3, 4);
		slices[2] = new Slice(5, 15);
		out0 = (Dataset) ROISliceUtils.getAxisDatasetTrapzSum(input, axis3,roi, slices, order[0], 1);

		Assert.assertArrayEquals(new int[]{20, 10},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0, 0),0);
		
	}
	
	@Test
	public void getDatasetLineROINDSlices() {
		Dataset input = DatasetFactory.ones(new int[] {10,20,30,40}, Dataset.FLOAT32);
//		Dataset axis0 = DatasetUtils.linSpace(0, 9, 10, Dataset.FLOAT32);
//		Dataset axis1 = DatasetUtils.linSpace(0, 19, 20, Dataset.FLOAT32);
//		Dataset axis2 = DatasetUtils.linSpace(0, 29, 30, Dataset.FLOAT32);
//		Dataset axis3 = DatasetUtils.linSpace(0, 39, 40, Dataset.FLOAT32);
		
		LinearROI roi = new LinearROI(new double[]{2.0,2.0}, new double[]{15.0,20.0});
		
		Slice[] slices = new Slice[input.getRank()];
		slices[0] = new Slice(0, 1);
		int[] order = new int[] {3,2,1,0};

		Dataset output = (Dataset) ROISliceUtils.getDataset(input, roi,slices, new int[]{order[0],order[1]},1);
		int[] shape = output.getShape();
		Assert.assertEquals(23,shape[0],0);
		Assert.assertEquals(20,shape[1],0);
		
		output = (Dataset) ROISliceUtils.getDataset(input, roi,slices, new int[]{order[1],order[0]},1);
		shape = output.getShape();
		Assert.assertEquals(23,shape[0],0);
		Assert.assertEquals(20,shape[1],0);
		
		output = (Dataset) ROISliceUtils.getDataset(input, roi,slices, new int[]{order[0],order[2]},1);
		shape = output.getShape();
		Assert.assertEquals(23,shape[0],0);
		Assert.assertEquals(30,shape[1],0);
	}
	
	

}
