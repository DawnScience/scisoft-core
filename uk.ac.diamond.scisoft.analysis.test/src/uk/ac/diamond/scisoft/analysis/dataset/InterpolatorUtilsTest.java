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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InterpolatorUtilsTest {

	@Test
	public void test() {
		
		Dataset im = DatasetFactory.createRange(0.0,10000.0,1.0, Dataset.FLOAT32);
		im = im.reshape(100,100);
		Dataset off = Maths.sin(DatasetFactory.createRange(0.0, 10.0, 0.1,Dataset.FLOAT32));
		Dataset axis = DatasetFactory.createRange(-5.0, 5.0, 0.1, Dataset.FLOAT32);
		
		Dataset newaxis = DatasetFactory.createRange(-10.0, 10.0, 0.1, Dataset.FLOAT32);
		
		Dataset output = InterpolatorUtils.remapOneAxis(im, 0, off, axis, newaxis);
		
		// check that some values are correct
		assertEquals("Coordinate incorrect", 1468.249, output.getDouble(62,29), 0.1);
		assertEquals("Coordinate incorrect", 7124.733, output.getDouble(127,56), 0.1);
		assertEquals("Coordinate incorrect", Double.NaN, output.getDouble(179,33), 0.1);
		assertEquals("Coordinate incorrect", 9600.669, output.getDouble(144,2), 0.1);
		assertEquals("Coordinate incorrect", 379.814, output.getDouble(53,63), 0.1);
		assertEquals("Coordinate incorrect", 225.239, output.getDouble(54,97), 0.1);
		assertEquals("Coordinate incorrect", 7118.775, output.getDouble(120,94), 0.1);
	}
	

}
