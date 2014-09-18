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

package uk.ac.diamond.scisoft.analysis.dataset;


import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class ImageTest {

	@Test
	// TODO not really a test as such, but checks to make sure there are no execution errors
	public void testregrid() {
		
		Dataset ds = Random.rand(new int[] {100,100});
		Dataset pow = DoubleDataset.createRange(100);
		pow.ipower(2);
		
		Dataset tile = pow.reshape(pow.getShape()[0],1);
		Dataset x = DatasetUtils.tile(tile, 100);
		
		Dataset y = DatasetUtils.transpose(x);
		
		Dataset lin = DoubleDataset.createRange(-100,900,10);
		
		// now apply the Transform
		@SuppressWarnings("unused")
		Dataset result = Image.regrid(ds, x, y, lin, lin);
		
	}
	
	@Test
	public void testMedianFilter() {
		Dataset ds = DoubleDataset.createRange(1000);
		Dataset result = Image.medianFilter(ds, new int[] {3});
		assertEquals(result.getDouble(2), ds.getDouble(2), 0.001);
		
		ds = ds.reshape(new int[] {10,100});
		result = Image.medianFilter(ds, new int[] {3,3});
		assertEquals(result.getDouble(5,5), ds.getDouble(5,5), 0.001);
		
		ds = ds.reshape(new int[] {10,10,10});
		result = Image.medianFilter(ds, new int[] {3,3,3});
		assertEquals(result.getDouble(5,5,5), ds.getDouble(5,5,5), 0.001);
		
		ds = IntegerDataset.createRange(1000);
		result = Image.medianFilter(ds, new int[] {3});
		assertEquals(result.getDouble(2), ds.getDouble(2), 0.001);
		
		ds = ds.reshape(new int[] {10,100});
		result = Image.medianFilter(ds, new int[] {3,3});
		assertEquals(result.getDouble(5,5), ds.getDouble(5,5), 0.001);
		
		ds = ds.reshape(new int[] {10,10,10});
		result = Image.medianFilter(ds, new int[] {3,3,3});
		assertEquals(result.getDouble(5,5,5), ds.getDouble(5,5,5), 0.001);
	}
	
	@Test
	public void testConvolutionFilter() {
		Dataset ds = DoubleDataset.createRange(1000);
		Dataset kernel = DoubleDataset.ones(27);
		Dataset result = Image.convolutionFilter(ds, kernel);
		assertEquals(120, result.getDouble(2), 0.001);
		
		ds = ds.reshape(new int[] {10,100});
		kernel = kernel.reshape(3,9);
		result = Image.convolutionFilter(ds, kernel);
		assertEquals(ds.getDouble(5,5)*27, result.getDouble(5,5), 0.001);
		
		ds = ds.reshape(new int[] {10,10,10});
		kernel = kernel.reshape(3,3,3);
		result = Image.convolutionFilter(ds, kernel);
		assertEquals(ds.getDouble(5,5,5)*27, result.getDouble(5,5,5), 0.001);
		
	}
	
	
	@Test
	public void testSobelFilter() {
		Dataset ds = Random.rand(new int[] {100,100});
		Image.sobelFilter(ds);
	}
}
