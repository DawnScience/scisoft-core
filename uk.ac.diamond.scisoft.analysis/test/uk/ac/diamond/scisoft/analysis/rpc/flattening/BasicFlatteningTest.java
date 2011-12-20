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

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

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
		Assert.assertArrayEquals(rectangularROI.getPoint(), unflattenedROI.getPoint(), 0.0);
		Assert.assertArrayEquals(rectangularROI.getLengths(), unflattenedROI.getLengths(), 0.0);
	}

}
