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

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.*;

import gda.analysis.io.ScanFileHolderException;

import java.util.List;

import org.junit.Test;

public class InterpolatorUtilsTest {

	@Test
	public void testGetInterpolatedResultFromNinePoints() throws Exception {
		AbstractDataset x = Random.rand(new int[] {3,3});
		AbstractDataset y = Random.rand(new int[] {3,3});
		
		List<AbstractDataset> res = DatasetUtils.meshGrid(DoubleDataset.arange(3),DoubleDataset.arange(3));
		
		x.imultiply(0.4).iadd(res.get(0));
		y.imultiply(0.4).iadd(res.get(1));
		
		AbstractDataset vals = Random.rand(new int[] {3,3});
		
		double test = InterpolatorUtils.GetInterpolatedResultFromNinePoints(vals, x, y, 1.5, 1.5);
		
		System.out.println(test);
	}
	
	@Test
	public void testRegrid() throws Exception {
		
		AbstractDataset ds = Random.rand(new int[] {100,100});
		AbstractDataset pow = DoubleDataset.arange(100);
		pow.ipower(2);
		
		AbstractDataset tile = pow.reshape(pow.getShape()[0],1);
		AbstractDataset x = DatasetUtils.tile(tile, 100);
		
		AbstractDataset y = DatasetUtils.transpose(x);
		
		AbstractDataset lin = DoubleDataset.arange(10,100,5);
		
		// now apply the Transform
		
		AbstractDataset result = InterpolatorUtils.regrid(ds, x, y, lin, lin);
		
		
	}
	

}
