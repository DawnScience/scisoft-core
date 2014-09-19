/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.roi;

import static org.junit.Assert.assertTrue;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.roi.GridPreferences;
import org.eclipse.dawnsci.analysis.dataset.roi.GridROI;
import org.eclipse.dawnsci.analysis.dataset.roi.LinearROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;
import org.junit.Test;

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
