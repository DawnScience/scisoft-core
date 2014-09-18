/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParameterTest {

	@Test
	public void testParameterCopyConstructorPreservesName() {
		Parameter parameter = new Parameter();
		parameter.setName("KichwaParameter");
		Parameter copy = new Parameter(parameter);

		assertEquals("KichwaParameter", copy.getName());
	}

	@Test
	public void testSetLimitsChangeLowerLimit() {
		Parameter parameter = new Parameter();
		parameter.setLimits(0, 1);
		parameter.setValue(0.5);

		parameter.setLimits(-1, 1);
		assertEquals(-1, parameter.getLowerLimit(), 0);
		assertEquals(1, parameter.getUpperLimit(), 0);
		assertEquals(0.5, parameter.getValue(), 0);
	}


	@Test
	public void testSetLimitsChangeUpperLimit() {
		Parameter parameter = new Parameter();
		parameter.setLimits(0, 1);
		parameter.setValue(0.5);

		parameter.setLimits(0, 2);
		assertEquals(0, parameter.getLowerLimit(), 0);
		assertEquals(2, parameter.getUpperLimit(), 0);
		assertEquals(0.5, parameter.getValue(), 0);
	}

}
