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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.function.Centroid;
import org.junit.Test;

/**
 *
 */
public class CentroidTest extends TestCase {
	Dataset d;

	@Override
	public void setUp() {
		d = new IntegerDataset(100,60);
		d.fill(1);
	}

	/**
	 * 
	 */
	@Test
	public void testCentroid() {
		Centroid cen = new Centroid();
		List<Double> csets = cen.value(d);
		assertEquals("Centroid test, y", 50., csets.get(0), 1e-8);
		assertEquals("Centroid test, x", 30., csets.get(1), 1e-8);
	}
}

	
