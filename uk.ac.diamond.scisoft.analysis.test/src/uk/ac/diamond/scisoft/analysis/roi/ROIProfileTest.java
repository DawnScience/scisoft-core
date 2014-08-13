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

package uk.ac.diamond.scisoft.analysis.roi;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.roi.GridROI;
import uk.ac.diamond.scisoft.analysis.roi.LinearROI;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

/**
 * seems ROIProfile is different from what the GUI does, so we need some 
 * tests
 */
public class ROIProfileTest {

	@Test
	public void testLine() {
		DoubleDataset input = new DoubleDataset(new double[] { 1.0, 2.0, 3.0, 4.0 }, 2, 2);
		LinearROI roi = new LinearROI(new double[] {0.0, 0.0}, new double[] {0.0, 1.0});
		Dataset[] sets = ROIProfile.line(input, roi, 1.0);
		for (int i = 0; i < sets.length; i++) {
			DoubleDataset s = (DoubleDataset) sets[i];
			if (s != null) {
				for (int j = 0; j < s.getSize(); j++) {
					assertTrue("positive numbers expected in integration over positive dataset", 
							s.getAbs(j) > 0); 
				}
			}
		}
		
	}

	@Test
	public void testBox() {
		DoubleDataset input = new DoubleDataset(new double[] { 0.0, 1.0, 2.0, 3.0 }, 2, 2);
		RectangularROI roi = new RectangularROI(0, 0, 1, 1, 0);
		Dataset[] sets = ROIProfile.box(input, roi);
		for (int i = 0; i < sets.length; i++) {
			DoubleDataset s = (DoubleDataset) sets[i];
			if (s != null) {
				for (int j = 0; j < s.getSize(); j++) {
					assertTrue("negative number in integration over non-negative dataset", 
							s.getAbs(j) >= 0); 
				}
			}
		}
	}

	@Test
	public void testSector() {
		DoubleDataset input = new DoubleDataset(new double[] { 0.0, 1.0, 1.0, 1.0 }, 2, 2);
		SectorROI roi = new SectorROI(0, 0, 0, 3, 0, 2*Math.PI);
		Dataset[] sets = ROIProfile.sector(input, roi);
		for (int i = 0; i < sets.length; i++) {
			DoubleDataset s = (DoubleDataset) sets[i];
			if (s != null) {
				for (int j = 0; j < s.getSize(); j++) {
					assertTrue("negative number in integration over non-negative dataset", 
							s.getAbs(j) >= 0); 
				}
			}
		}
	}
	
	@Test
	public void testGrid() {
		DoubleDataset input = new DoubleDataset(new double[] { 0.0, 1.0, 2.0, 3.0 }, 2, 2);
		RectangularROI roi = new GridROI(0, 0, 1, 1, 0, 10, 10, false, false, new GridPreferences());
		Dataset[] sets = ROIProfile.box(input, roi);
		for (int i = 0; i < sets.length; i++) {
			DoubleDataset s = (DoubleDataset) sets[i];
			if (s != null) {
				for (int j = 0; j < s.getSize(); j++) {
					assertTrue("negative number in integration over non-negative dataset", 
							s.getAbs(j) >= 0); 
				}
			}
		}
	}
}
