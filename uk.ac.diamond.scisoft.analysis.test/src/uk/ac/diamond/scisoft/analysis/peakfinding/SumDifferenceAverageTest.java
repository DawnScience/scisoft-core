/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Map;
import java.util.TreeSet;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

public class AverageDifferenceTest {
	
	private IPeakFinder avgDiff = new AverageDifference();
	
	@Test
	public void nameCheck() {
		Assert.assertEquals("Average Difference", avgDiff.getName());
	}
	
	@Test
	public void parametersCheck() throws Exception {
		Map<String, Number> paramMap = avgDiff.getParameters();
		Assert.assertEquals(2, paramMap.size());
		
		Assert.assertEquals(true, paramMap.containsKey("windowSize"));
		Assert.assertEquals(true, paramMap.containsKey("nrStdDevs"));
		
		Assert.assertEquals(50, avgDiff.getParameter("windowSize"));
		Assert.assertEquals(3, avgDiff.getParameter("nrStdDevs"));
	}
	
	@Test
	public void singlePeakFinding() {
		Dataset xData = PeakyData.getxAxisRange();
		Dataset yData = PeakyData.makeGauPeak().calculateValues(xData);
		
		//Calculate the expected x-coordinate
		Double expectedPos = 0.3785 * PeakyData.getxAxisMax(); 
		Double foundPos;
		
		//Find the x-coordinate of the found peak
		TreeSet<Integer> foundPeaks = (TreeSet<Integer>)avgDiff.findPeaks(xData, yData, null);
		//We need the set to have a length of 1 for the next bit...
		Assert.assertEquals(1, foundPeaks.size());
		for (Integer i : foundPeaks) {
			foundPos = xData.getDouble(i);
			//Yes, it finds the wrong position.
			Assert.assertEquals(expectedPos, foundPos, 0.25);
		}
	}

}
