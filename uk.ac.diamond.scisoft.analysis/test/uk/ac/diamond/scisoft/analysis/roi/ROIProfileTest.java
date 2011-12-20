/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.roi;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
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
		AbstractDataset[] sets = ROIProfile.line(input, roi, 1.0);
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
		AbstractDataset[] sets = ROIProfile.box(input, roi);
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
		AbstractDataset[] sets = ROIProfile.sector(input, roi);
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
		AbstractDataset[] sets = ROIProfile.box(input, roi);
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
