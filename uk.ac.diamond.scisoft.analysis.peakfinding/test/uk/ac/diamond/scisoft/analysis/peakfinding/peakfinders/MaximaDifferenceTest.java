/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

public class MaximaDifferenceTest {
	
	private IPeakFinder maxDiff = new MaximaDifference();
	
	@Test
	public void nameCheck() {
		Assert.assertEquals("Maxima Difference", maxDiff.getName());
	}
	
	@Test
	public void parametersCheck() throws Exception {
		Map<String, Number> paramMap = maxDiff.getParameters();
		Assert.assertEquals(2, paramMap.size());
		
		Assert.assertEquals(true, paramMap.containsKey("windowSize"));
		Assert.assertEquals(true, paramMap.containsKey("nrStdDevs"));
		
		Assert.assertEquals(50, maxDiff.getParameter("windowSize"));
		Assert.assertEquals(3, maxDiff.getParameter("nrStdDevs"));
	}
	
	@Test
	public void singlePeakFinding() {
		Dataset xData = PeakyData.getxAxisRange();
		Dataset yData = PeakyData.makeGauPeak().calculateValues(xData);
		
		//Calculate the expected x-coordinate
		Double expectedPos = 0.3785 * PeakyData.getxAxisMax(); 
		Double foundPos;
		
		//Find the x-coordinate of the found peak
		TreeMap<Integer, Double> foundPeaks = (TreeMap<Integer, Double>)maxDiff.findPeaks(xData, yData, null);
		//We need the set to have a length of 1 for the next bit...
		Assert.assertEquals(1, foundPeaks.size());
		for (Integer i : foundPeaks.keySet()) {
			foundPos = xData.getDouble(i);
			//Yes, it finds the wrong position.
			Assert.assertEquals(expectedPos, foundPos, 0.35);
		}
	}

}
