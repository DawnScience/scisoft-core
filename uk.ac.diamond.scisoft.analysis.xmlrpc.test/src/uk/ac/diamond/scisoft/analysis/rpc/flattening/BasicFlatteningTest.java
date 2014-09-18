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
