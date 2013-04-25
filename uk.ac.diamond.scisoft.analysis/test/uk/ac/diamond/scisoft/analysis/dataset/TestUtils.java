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

import java.util.Arrays;

import org.junit.Assert;

public class TestUtils {
	/**
	 * Assert equality of datasets where each element is true if abs(a - b) <= absTol + relTol*abs(b)
	 * @param calc
	 * @param expected
	 * @param relTolerance
	 * @param absTolerance
	 */
	public static void assertDatasetEquals(AbstractDataset calc, AbstractDataset expected, double relTolerance, double absTolerance) {
		Assert.assertEquals("Rank", expected.getRank(), calc.getRank());
		Assert.assertArrayEquals("Shape", expected.getShape(), calc.getShape());
		Assert.assertEquals("Itemsize", expected.getElementsPerItem(), calc.getElementsPerItem());
		IndexIterator at = calc.getIterator(true);
		IndexIterator bt = expected.getIterator();
		final int is = calc.getElementsPerItem();

		while (at.hasNext() && bt.hasNext()) {
			for (int j = 0; j < is; j++) {
				double bv = calc.getElementDoubleAbs(at.index + j);
				double av = expected.getElementDoubleAbs(bt.index + j);
				
				Assert.assertEquals("Value does not match at " + Arrays.toString(at.getPos()) + "; " + j +
						": ", av, bv, absTolerance + relTolerance*Math.abs(av));
			}
		}
	}
}
