/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
