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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

public class MedianTest extends TestCase {

	@Test
	public void testExecute() {
		double[] x = {1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11., 12.};
		
		Dataset d = new DoubleDataset(x);
		//Test for single dataset
		Median m = new Median(5);
		Dataset filterResults = m.value(d).get(0);
		//assuming edge cases use smaller, asymmetric window
		assertEquals(filterResults.getDouble(0),2, 1e-8);
		//clear of edge effects
		assertEquals(filterResults.getDouble(5),6, 1e-8);
	}

	
}
