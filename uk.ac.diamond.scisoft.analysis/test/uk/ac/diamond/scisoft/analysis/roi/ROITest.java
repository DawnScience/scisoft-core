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

		RectangularROI rect = new RectangularROI(20, 0);
		assertFalse(roi.isContainedBy(rect));

		rect.setPoint(-10, -10);
		assertTrue(roi.isContainedBy(rect));
		rect.setLengths(20, 10);
		assertFalse(roi.isContainedBy(rect));

		rect.setPoint(-10, -5);
		roi.setSemiAxis(1, 5);
		assertTrue(roi.isContainedBy(rect));

		roi.setAngleDegrees(90);
		assertFalse(roi.isContainedBy(rect));

		rect.setPoint(-5, -10);
		assertFalse(roi.isContainedBy(rect));

		rect.setLengths(10, 20);
		assertTrue(roi.isContainedBy(rect));

		roi.setAngleDegrees(45);
		assertFalse(roi.isContainedBy(rect));

		rect.setPoint(-10, -10);
		rect.setLengths(20, 20);
		assertTrue(roi.isContainedBy(rect));

		double d = Math.sqrt(0.5*(10*10 + 5*5));
		rect.setPoint(-d, -d);
		rect.setLengths(2*d, 2*d);
		assertTrue(roi.isContainedBy(rect));

		d *= 0.99;
		rect.setPoint(-d, -d);
		rect.setLengths(2*d, 2*d);
		assertFalse(roi.isContainedBy(rect));
	}
}
