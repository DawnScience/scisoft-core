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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FunctionFactoryTest {

	/**
	 * This class performs a series of tests, populating the FunctionFactory itself during set-up rather than using the
	 * FunctionFactoryExtensionService It is nearly identical to the other FunctionFactoryPluginTest. There is an
	 * additional test for registering a differently named function.
	 */

	@Rule
	public ExpectedException thrower = ExpectedException.none();

	@Before
	public void setup() {
		FunctionFactory.registerFunctions(true, Gaussian.class, Lorentzian.class, Polynomial.class, StraightLine.class);
	}

	/**
	 * Specific to JUnit test
	 */
	@Test
	public void testPeakRegistration() throws Exception {
		// Re-register the Gaussian function with a different name.
		FunctionFactory.registerFunction(Gaussian.class, "myGauss", null);

		// Check it is the same Gaussian and that it is not another type of function
		assertEquals(FunctionFactory.getPeakFunction("myGauss"), FunctionFactory.getPeakFunction("Gaussian"));
		assertFalse((FunctionFactory.getPeakFunction("myGauss").equals(FunctionFactory.getPeakFunction("Lorentzian"))));
	}

	/**
	 * Specific to JUnit test
	 */
	@Test
	public void testFunctionRegistration() {
		thrower.expect(IllegalArgumentException.class);
		FunctionFactory.getPeakFunction("Polynomial");
	}

	@Test
	public void testGetFunctionNames() {
		Set<String> functionSet = FunctionFactory.getFunctionNames();
		// Check that we have a common function
		assertTrue(functionSet.contains("Gaussian"));
	}

	@Test
	public void testGetPeakNames() {
		Set<String> peakList = FunctionFactory.getPeakFunctionNames();
		// Check that we have a common function...
		assertTrue(peakList.contains("Gaussian"));
		// ... and we don't have non-peak functions in here
		assertFalse(peakList.contains("Polynomial"));
	}

	@Test
	public void testGetFunction() {
		try {
			assertEquals(FunctionFactory.getFunctionClass("Linear").newInstance(),
					FunctionFactory.getFunction("Linear"));
		} catch (Exception e) {
			System.out.println("Could not load Linear function type");
		}
	}

	@Test
	public void testGetPeak() {
		try {
			assertEquals(FunctionFactory.getPeakFunctionClass("Lorentzian").newInstance(),
					FunctionFactory.getPeakFunction("Lorentzian"));
		} catch (ReflectiveOperationException e) {
			System.out.println("Could not load Lorentzian peak type");
		}
	}

	@Test
	public void testFunctionPeakMaps() {
		// Check that all Peaks are Functions...
		assertTrue(FunctionFactory.getFunctionNames().containsAll(FunctionFactory.getPeakFunctionNames()));
		// ... but not all Functions are Peaks
		assertFalse(FunctionFactory.getPeakFunctionNames().containsAll(FunctionFactory.getFunctionNames()));
	}

}
