/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class FunctionFactoryPluginTest {
	
	/**
	 * This class performs a series of tests, relying on the FunctionFactoryExtensionService
	 * to register the functions. It is nearly identical to the FunctionFactoryTest.
	 */
	
	@Test
	public void testGetFunctionNames() {
		Set<String> functionList = FunctionFactory.getFunctionNames();
		//Check that we have a common function
		assertTrue(functionList.contains("Linear"));
	}
	
	@Test
	public void testGetPeakNames() {
		Set<String> peakNames = FunctionFactory.getPeakFunctionNames();
		//Does the array also not/contain our two functions?
		assertTrue(peakNames.contains("Gaussian"));
		assertFalse(peakNames.contains("Polynomial"));
	}
	
	@Test
	public void testGetFunction() {
		try {
			assertEquals(FunctionFactory.getFunctionClass("Linear").newInstance(), FunctionFactory.getFunction("Linear"));
		} catch (Exception e) {
			System.out.println("Could not load Linear function type");
		}
	}
	
	@Test
	public void testGetPeak() {
		try{
			assertEquals(FunctionFactory.getPeakFunctionClass("Lorentzian").newInstance(), FunctionFactory.getPeakFunction("Lorentzian"));
		} catch (Exception e) {
			System.out.println("Could not load Lorentzian peak type");
		}
	}
	
	@Test
	public void testFunctionPeakMaps() {
		//Check that all Peaks are Functions...
		assertTrue(FunctionFactory.getFunctionNames().containsAll(FunctionFactory.getPeakFunctionNames()));
		//... but not all Functions are Peaks
		assertFalse(FunctionFactory.getPeakFunctionNames().containsAll(FunctionFactory.getFunctionNames()));
	}

}
