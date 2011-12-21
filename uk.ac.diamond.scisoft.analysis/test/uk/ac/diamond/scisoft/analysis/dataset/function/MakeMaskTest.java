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

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;

/**
 *
 */
public class MakeMaskTest extends TestCase {

	/**
	 * 
	 */
	@Test
	public void testExecute() {
		float[] x = {1.f, 2.f, 3.f, 4.f, 5.f};
		double[] y = {0, 1, 1, 0, 0};
		AbstractDataset d = new FloatDataset(x, null);
		MakeMask m = new MakeMask(1.2, 3.5);
		List<AbstractDataset> dsets = m.value(d);

		for (int i = 0; i < y.length; i++) {
			assertEquals(y[i], dsets.get(0).getDouble(i), 1e-8);
		}
	}

}
