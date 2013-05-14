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

import static org.junit.Assert.*;

import org.junit.Test;

public class ROITest {

	@Test
	public void testEllipticalROI() {
		EllipticalROI roi = new EllipticalROI(10, 0, 0);
		assertTrue(roi.containsPoint(3,3));
		assertTrue(roi.containsPoint(9,0));
		assertTrue(roi.containsPoint(10,0));
		assertFalse(roi.containsPoint(10.0001,0));
		assertFalse(roi.containsPoint(9,9));
	}
}
