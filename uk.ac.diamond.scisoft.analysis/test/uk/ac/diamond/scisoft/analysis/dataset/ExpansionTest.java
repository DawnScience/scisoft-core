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

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpansionTest {

	@Test
	public void testExpansion() {
		IntegerDataset d = new IntegerDataset(2,2);
		d.set(8920,2,0);
		d.set(8853,2,1);
		try {
			d.get(2,2);
			fail("access to uninitialised values allowed");
		} catch (ArrayIndexOutOfBoundsException e) {
			// expected
		}
		assertArrayEquals("shape incorrect", new int[] {3,2}, d.getShape());
		assertEquals("array length incorrect", 6, d.getData().length);
		assertArrayEquals("array incorrect", new int[] {0,0,0,0,8920,8853}, d.getData());
	}
}
