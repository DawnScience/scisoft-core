/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;

public class CalibrationUtilsTest {
	
	ArrayList<APeak> peaks= new ArrayList<APeak>();
	
	@Before
	public void initialize() {
		for (int i = 0; i < 20; i++) {
			peaks.add(new Gaussian(i,1,1));
		}
	}

	@Test
	public void testGetPeakList() {
		ArrayList<APeak> peaks = new ArrayList<APeak>();
		for (int i = 0; i < 20; i++) {
			peaks.add(new Gaussian(i,1,1));
		}
		
		Dataset result = CalibrationUtils.getPeakList(peaks);
		
		for (int i = 0; i < 20; i++) {
			Assert.assertEquals(result.getDouble(i), i, 0.001);
		}
	}
	
	@Test
	public void selectSpecifiedPeaks() {
		
		Dataset testpoints = DatasetFactory.createRange(0, 19, 2.2, Dataset.FLOAT64);
		
		Dataset calibPoints = CalibrationUtils.selectSpecifiedPeaks(testpoints, peaks);
		
		for (int i = 0; i < testpoints.getShape()[0]; i++) {
			Assert.assertEquals(Math.round(testpoints.getDouble(i)), calibPoints.getDouble(i), 0.001);
		}
	}
	
	

}
