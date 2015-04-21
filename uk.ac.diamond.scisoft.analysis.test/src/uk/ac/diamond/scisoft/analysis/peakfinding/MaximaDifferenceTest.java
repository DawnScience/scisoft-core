/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
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
		Assert.assertEquals(true, paramMap.containsKey("minSignificance"));
		
		Assert.assertEquals(3, maxDiff.getParameter("windowSize"));
		Assert.assertEquals(1, maxDiff.getParameter("minSignificance"));
	}
	
	@Test
	public void singlePeakFinding() {
		Dataset xData = PeakyData.getxAxisRange();
		Dataset yData = PeakyData.makeGauPeak().calculateValues(xData);
		
		Double expectedPos = 0.3785 * PeakyData.getxAxisMax();
		int expectedPosIndex = 1091;//Maths.abs(Maths.subtract(xData.getSlice((Slice)null), expectedPos)).argMin();
		
		List<Integer> foundPeaks = maxDiff.findPeaks(xData, yData, null);
		
		Assert.assertEquals(78, foundPeaks.size());
		Assert.assertEquals(expectedPosIndex, foundPeaks.get(0), 0.001);
		
	}

}
