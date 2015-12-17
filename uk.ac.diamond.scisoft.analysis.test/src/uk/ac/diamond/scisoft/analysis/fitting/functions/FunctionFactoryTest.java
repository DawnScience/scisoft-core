/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FunctionFactoryTest {

	/**
	 * This class performs a series of tests, populating the FunctionFactory
	 * itself during set-up rather than using the FunctionFactoryExtensionService
	 * It is nearly identical to the other FunctionFactoryPluginTest.
	 * There is an additional test for registering a differently named function.
	 */
	
	@Rule
	public ExpectedException thrower = ExpectedException.none();
	
	@Before
	public void setup() {
		FunctionFactory.registerFunctions(true, Gaussian.class,
				Lorentzian.class, Polynomial.class, StraightLine.class);
	}
	
	/**
	 * Specific to JUnit test
	 */
	@Test
	public void testPeakRegistration() throws Exception{
		//Re-register the Gaussian function with a different name.
		FunctionFactory.registerFunction(Gaussian.class, "myGauss", null);
		
		//Check it is the same Gaussian and that it is not another type of function
		assertEquals(FunctionFactory.getPeakFn("myGauss"), FunctionFactory.getPeakFn("Gaussian"));
		assertFalse((FunctionFactory.getPeakFn("myGauss").equals(FunctionFactory.getPeakFn("Lorentzian"))));

	}
	
	/**
	 * Specific to JUnit test
	 */
	@Test
	public void testFunctionRegistration() throws Exception{
		thrower.expect(ClassNotFoundException.class);
		FunctionFactory.getPeakFn("Polynomial");
	}
	
	@Test
	public void testGetFunctionNames() {
		List<String> functionList = FunctionFactory.getFunctionNameList();
		//Check that we have a common function
		assertTrue(functionList.contains("Gaussian"));
		
		String[] functionArray = FunctionFactory.getFunctionNameArray();
		//Does the array also contain our function?
		assertTrue(Arrays.asList(functionArray).contains("Gaussian"));
		//Check that both sets of data are the same length...
		assertEquals(functionArray.length, functionList.size());
		//... and are actually the same
		assertArrayEquals(functionArray, functionList.toArray());
	}
	
	@Test
	public void testGetPeakNames() {
		List<String> peakList = FunctionFactory.getPeakFnNameList();
		//Check that we have a common function...
		assertTrue(peakList.contains("Gaussian"));
		//... and we don't have non-peak functions in here
		assertFalse(peakList.contains("Polynomial"));
		
		String[] peakArray = FunctionFactory.getPeakFnNameArray();
		//Does the array also not/contain our two functions?
		assertTrue(Arrays.asList(peakArray).contains("Gaussian"));
		assertFalse(Arrays.asList(peakArray).contains("Polynomial"));
		//Check that both sets of data are the same length...
		assertEquals(peakArray.length, peakList.size());
		//... and are actually the same
		assertArrayEquals(peakArray, peakList.toArray());
	}
	
	@Test
	public void testGetFunction() {
		try {
			assertEquals(FunctionFactory.getClassForFunction("Linear").newInstance(), FunctionFactory.getFunction("Linear"));
		} catch (Exception e) {
			System.out.println("Could not load Linear function type");
		}
	}
	
	@Test
	public void testGetPeak() {
		try{
			assertEquals(FunctionFactory.getClassForPeakFn("Lorentzian").newInstance(), FunctionFactory.getPeakFn("Lorentzian"));
		} catch (ReflectiveOperationException e) {
			System.out.println("Could not load Lorentzian peak type");
		}
	}
	
	@Test
	public void testFunctionPeakMaps() {
		//Check that all Peaks are Functions...
		assertTrue(FunctionFactory.getFunctionNameList().containsAll(FunctionFactory.getPeakFnNameList()));
		//... but not all Functions are Peaks
		assertFalse(FunctionFactory.getPeakFnNameList().containsAll(FunctionFactory.getFunctionNameList()));
	}

}
