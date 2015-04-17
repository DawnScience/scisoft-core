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

import junit.framework.Assert;

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

}
