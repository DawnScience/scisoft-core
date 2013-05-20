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

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

public class ROISliceTest {
	
	@Test
	public void testLine() {
		
		AbstractDataset input = AbstractDataset.ones(new int[] {20,30,40}, AbstractDataset.FLOAT32);
		
		AbstractDataset axis0 = DatasetUtils.linSpace(0, 19, 20, AbstractDataset.FLOAT32);
		AbstractDataset axis1 = DatasetUtils.linSpace(0, 29, 30, AbstractDataset.FLOAT32);
		AbstractDataset axis2 = DatasetUtils.linSpace(0, 39, 40, AbstractDataset.FLOAT32);
		
		RectangularROI roi = new RectangularROI(10, 10, 5, 5, 0);
		
		AbstractDataset out0 = (AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum(axis0, input, roi, 0);
		Assert.assertArrayEquals(new int[]{30, 40},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum(axis1, input, roi, 1);
		Assert.assertArrayEquals(new int[]{20, 40},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum(axis2, input, roi, 2);
		Assert.assertArrayEquals(new int[]{20, 30},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getTrapiziumArea(axis0, input, roi, 0);
		Assert.assertArrayEquals(new int[]{30, 40},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getTrapiziumArea(axis1, input, roi, 1);
		Assert.assertArrayEquals(new int[]{20, 40},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getTrapiziumArea(axis2, input, roi, 2);
		Assert.assertArrayEquals(new int[]{20, 30},out0.getShape());
		Assert.assertEquals(5.0, out0.getDouble(0),0);
		
		//times axis by 2
		
		axis0.imultiply(2);
		axis1.imultiply(2);
		axis2.imultiply(2);
		
		roi.setLengths(10, 10);
		
		out0 = (AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum(axis0, input, roi, 0);
		Assert.assertArrayEquals(new int[]{30, 40},out0.getShape());
		Assert.assertEquals(10.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum(axis1, input, roi, 1);
		Assert.assertArrayEquals(new int[]{20, 40},out0.getShape());
		Assert.assertEquals(10.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getAxisDatasetTrapzSum(axis2, input, roi, 2);
		Assert.assertArrayEquals(new int[]{20, 30},out0.getShape());
		Assert.assertEquals(10.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getTrapiziumArea(axis0, input, roi, 0);
		Assert.assertArrayEquals(new int[]{30, 40},out0.getShape());
		Assert.assertEquals(10.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getTrapiziumArea(axis1, input, roi, 1);
		Assert.assertArrayEquals(new int[]{20, 40},out0.getShape());
		Assert.assertEquals(10.0, out0.getDouble(0),0);
		
		out0 = (AbstractDataset)ROISliceUtils.getTrapiziumArea(axis2, input, roi, 2);
		Assert.assertArrayEquals(new int[]{20, 30},out0.getShape());
		Assert.assertEquals(10.0, out0.getDouble(0),0);
		
	}

}
