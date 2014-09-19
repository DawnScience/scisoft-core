/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test is intended to be as minimal as possible to demonstrate operation of the flattening.
 * For full testing see the {@link FlatteningTestAbstract} hierarchy
 */
public class BasicFlatteningTest {

	@Test 
	public void basicTest() {
		final IRootFlattener f = new RootFlattener();
		
		Assert.assertEquals(18, f.unflatten(f.flatten(18)));
		Assert.assertEquals("hello", f.unflatten(f.flatten("hello")));
		
		int[] intArray = {1, 2, 3};
		Assert.assertArrayEquals(intArray, (int[])f.unflatten(f.flatten(intArray)));
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("one", 1);
		map.put("two", 2);
		map.put("three", 3);
		Assert.assertEquals(map, f.unflatten(f.flatten(map)));
		
		RectangularROI rectangularROI = new RectangularROI(100.0, 2.3);
		RectangularROI unflattenedROI = (RectangularROI)f.unflatten(f.flatten(rectangularROI));
		Assert.assertEquals(rectangularROI.getAngle(), unflattenedROI.getAngle(), 0.0);
		Assert.assertArrayEquals(rectangularROI.getPointRef(), unflattenedROI.getPointRef(), 0.0);
		Assert.assertArrayEquals(rectangularROI.getLengths(), unflattenedROI.getLengths(), 0.0);
	}

}
