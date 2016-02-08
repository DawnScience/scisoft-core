/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IPeak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use this factory to return your function by name.
 * 
 * Avoid using concrete instances of functions is reduces flexibility of the product
 * because functions cannot be swapped into different parts of the code.
 * 
 * Instead use something like:
 * <code>
 *     double[]  params = ...
 *     IFunction box    = FunctionFactory.getFunction("Box", params);
 *     
 * </code>
 * 
 * In the ideal world the functions would be package private classes.
 * 
 * 
 * NOTE If using the function actor, functions must also be declared in 'FunctionType'
 * 
 */
@SuppressWarnings("unchecked")
public final class FunctionFactory {

	private static final SortedMap<String, Class<? extends IFunction>> FUNCTIONS;
	private static final SortedMap<String, Class<? extends IPeak>> PEAKS;
	static {
		
		FUNCTIONS = new TreeMap<String, Class<? extends IFunction>>();
		PEAKS = new TreeMap<String, Class<? extends IPeak>>();
		
		/**
		 * Functions *must* have a zero argument constructor.
		 * 
		 * Previously included full list of functions. Now these are 
		 * imported as Extension Points. Left in case of future need.
		 */
//		registerFunctions(
//		);
	}

	private static final Logger logger = LoggerFactory.getLogger(FunctionFactory.class);

	// Not for public use: only used by OSGI
	public FunctionFactory() {

	}

	/**
	 * Register a block of functions with factory.
	 * @param classes
	 */
	@SafeVarargs
	public static void registerFunctions(Boolean ignoreDuplicates, Class<? extends IFunction>... classes) {

		for (Class<? extends IFunction> clazz : classes) {
			try {
				registerFunction(clazz, null, ignoreDuplicates,null);
			} catch (Throwable e) {
				logger.error("Cannot register function "+clazz.getCanonicalName()+"!", e);
			}
		}
	}

	/**
	 * Register a function with its class and whether we should ignore duplicates
	 * in the maps. This is useful for tests.
	 * @param clazz
	 * @param ignoreDuplicates
	 * @throws Exception
	 */
	public static void registerFunction(Class<? extends IFunction> clazz, Boolean ignoreDuplicates) throws Exception {
		registerFunction(clazz, null, ignoreDuplicates, null);
	}
	
	/**
	 * Register a function with its class, function name and use case list. 
	 * (This is default option that should be used)
	 * @param clazz - function class
	 * @param fnName - string of user defined function name
	 * @param useCaseList - list of use cases ids extracted from extension point
	 * @throws Exception
	 */
	public static void registerFunction(Class<? extends IFunction> clazz, String fnName, Set<String> useCaseList) throws Exception {
		registerFunction(clazz, fnName, false, useCaseList);
	}
	
	/**
	 * Full method allowing registration of functions with the factory and also 
	 * (for testing) ignoring of duplicates in the PEAKS and FUNCTIONS maps.
	 * @param clazz
	 * @param fnName
	 * @param ignoreDuplicates
	 * @throws Exception
	 */
	public static void registerFunction(Class<? extends IFunction> clazz, String fnName, Boolean ignoreDuplicates, Set<String> useCaseList) throws Exception {
		final IFunction function = clazz.newInstance();
		final String name;
		
		//Register with a different name to the one in the class
		if (fnName != null) {
			name = fnName;
		} else {
			name = function.getName(); 
		}

		//Add function to FUNCTION map
		if (!FUNCTIONS.containsKey(name)) {
			FUNCTIONS.put(name, clazz);
			
			//If this is a peak function, register it in the PEAK map too
			if (function instanceof IPeak) {
				if (!PEAKS.containsKey(name)) {
					PEAKS.put(name, (Class<? extends IPeak>) clazz);
				} else {
					if (ignoreDuplicates) {
						//pass
					} else {
						throw new IllegalArgumentException("A peak function is already registered with the name "+name+".");
					}
				}
			}
			if (useCaseList != null) {
				FunctionUseCaseService.setFunctionUseCases(name, useCaseList);
			}
		} else {
			if (ignoreDuplicates) {
				//Pass
			} else {
				throw new IllegalArgumentException("A function is already registered with the name "+name+".");
			}
		}
	}

	/**
	 * @return set of function names
	 */
	public static Set<String> getFunctionNames() {
		return Collections.unmodifiableSet(FUNCTIONS.keySet());
	}

	/**
	 * @return set of peak function names
	 */
	public static Set<String> getPeakFunctionNames() {
		return Collections.unmodifiableSet(PEAKS.keySet());
	}

	/**
	 * Returns the complete set of functions with their names and classes
	 * registered with the factory
	 * @return Map of names (strings) and classes (IFunction)
	 */
	public static Map<String, Class<? extends IFunction>> getFunctions() {
		return FUNCTIONS;
	}

	/**
	 * Returns an instance of a function
	 * @param name of registered function
	 * @return IFunction
	 * @throws IllegalArgumentException if not found or could not create 
	 */
	public static IFunction getFunction(String name) {
		Class<? extends IFunction> functionClass = getFunctionClass(name);
		try {
			return functionClass.newInstance();
		} catch (Exception e) {
			logger.error("Could not create instance of {}", functionClass, e);
			throw new IllegalArgumentException("Could not create instance of " + functionClass, e);
		}
	}

	/**
	 * Returns an instance of a function with given arguments
	 * @param name of registered function
	 * @param args arguments for constructor
	 * @return IFunction
	 * @throws IllegalArgumentException if not found or could not create 
	 */
	public static IFunction getFunction(String name, double... args) {
		Class<? extends IFunction> functionClass = getFunctionClass(name);
		try {
			final Constructor<? extends IFunction> c = functionClass.getConstructor(double[].class);
			return c.newInstance(args);
		} catch (Exception e) {
			logger.error("Could not create instance of {}", functionClass, e);
			throw new IllegalArgumentException("Could not create instance of " + functionClass, e);
		}
	}

	/**
	 * Returns a registered function class
	 * @param name of registered function
	 * @return IFunction class
	 * @throws IllegalArgumentException if not found
	 */
	public static Class<? extends IFunction> getFunctionClass(String name) {
		Class<? extends IFunction> functionClass = FUNCTIONS.get(name);
		if (functionClass == null) {
			logger.error("There is no function with the name '{}' registered!", name);
			throw new IllegalArgumentException("There is no function with the name " + name + " registered!");
		}
		return functionClass;
	}

	/**
	 * Returns an instance of a peak
	 * @param name of registered peak
	 * @return IPeak
	 * @throws IllegalArgumentException if not found or could not create 
	 */
	public static IPeak getPeakFunction(String name) {
		Class<? extends IPeak> peakClass = getPeakFunctionClass(name);
		try {
			return peakClass.newInstance();
		} catch (Exception e) {
			logger.error("Could not create instance of {}", peakClass, e);
			throw new IllegalArgumentException("Could not create instance of " + peakClass, e);
		}
	}

	/**
	 * Returns a registered peak class
	 * @param name of registered peak
	 * @return IPeak class
	 * @throws IllegalArgumentException if not found
	 */
	public static Class<? extends IPeak> getPeakFunctionClass(String name) {
		Class<? extends IPeak> peakClass = PEAKS.get(name);
		if (peakClass == null) {
			logger.error("There is no peak function with the name '{}' registered!", name);
			throw new IllegalArgumentException("There is no peak function with the name " + name + " registered!");
		}
		return peakClass;
	}
}
