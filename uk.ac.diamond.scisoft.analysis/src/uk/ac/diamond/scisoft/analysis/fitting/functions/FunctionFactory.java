/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
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

	private static final Map<String, Class<? extends IFunction>> FUNCTIONS;
	private static final Map<String, Class<? extends IPeak>> PEAKS;
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
	 * Register a function with it's class and whether we should ignore duplicates
	 * in the maps. This is useful for tests.
	 * @param clazz
	 * @param ignoreDuplicates
	 * @throws Exception
	 */
	public static void registerFunction(Class<? extends IFunction> clazz, Boolean ignoreDuplicates) throws Exception {
		registerFunction(clazz, null, ignoreDuplicates, null);
	}
	
	/**
	 * Register a function with it's class, function name and use case list. 
	 * (This is default option that should be used)
	 * @param clazz - function clas
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
						throw new Exception("A peak function is already registered with the name "+name+".");
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
				throw new Exception("A function is already registered with the name "+name+".");
			}
		}
	}
	
	/**
	 * Returns the complete list of function names registered with the factory
	 * @return List of function names (strings)
	 */
	public static List<String> getFunctionNameList() {
		List<String> peakFunctionNames = new ArrayList<String>();
		peakFunctionNames.addAll(FUNCTIONS.keySet());
		return peakFunctionNames;
	}
	
	/**
	 * Returns the complete list of function names registered with the factory as an array
	 * @return String array of function names
	 */
	public static String[] getFunctionNameArray() {
		return FUNCTIONS.keySet().toArray(new String[FUNCTIONS.size()]);
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
	 * Returns the list of peak functions registered with the factory
	 * @return List of strings of peak function names
	 */
	public static List<String> getPeakFnNameList() {
		List<String> peakFunctionNames = new ArrayList<String>();
		peakFunctionNames.addAll(PEAKS.keySet());
		return peakFunctionNames;
	}
	
	/**
	 * Returns the complete list of function names registered with the factory as an array
	 * @return String array of function names
	 */
	public static String[] getPeakFnNameArray() {
		return PEAKS.keySet().toArray(new String[PEAKS.size()]);
	}

	
	/**
	 * Returns the complete group of peak functions with their names and classes
	 * registered with the factory
	 * @return Map of names (strings) and classes (IPeak)
	 */
	public static Map<String, Class<? extends IPeak>> getPeakFns() {
		return PEAKS;
	}
	
	/**
	 * Returns the no argument constructor for the function
	 * @return AFunction
	 */
	public static IFunction getFunction(String name) throws ReflectiveOperationException {
		Class<? extends IFunction> functionClass;
		try {
			functionClass = FUNCTIONS.get(name);
			if(functionClass == null) {
				throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
			}
			return functionClass.newInstance();
		} catch (Exception ne) {
			throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
		}
	}
	
	/**
	 * Returns the no argument constructor for the function
	 * @return AFunction
	 */
	public static IFunction getFunction(String name, double... args) throws ReflectiveOperationException {
		Class<? extends IFunction> functionClass;
		try {
			functionClass = FUNCTIONS.get(name);
			if(functionClass == null) {
				throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
			}
			final Constructor<? extends IFunction> c = functionClass.getConstructor(double[].class);
			return c.newInstance(args);
		} catch (Exception ne) {
			throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
		}
	}
	
	/**
	 * Returns a class implementing IFunction based on the name supplied 
	 * @param name
	 * @return Function class
	 * @throws ClassNotFoundException - if named function is not registered
	 */
	public static Class<? extends IFunction> getClassForFunction(String name) throws ClassNotFoundException {
		Class<? extends IFunction> functionClass;
		try {
			functionClass= FUNCTIONS.get(name);
			if (functionClass == null) {
				throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
			}
			return functionClass;
		} catch (Exception ne) {
			throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
		}
	}
	
	/**
	 * Returns the fully qualified class name represented by this class object as a String.
	 * @param name
	 * @return class name of function
	 * @throws ClassNotFoundException
	 */
	public static String getClassNameForFunction(String name) throws ClassNotFoundException {
		String functionClassName;
		try {
			functionClassName  = FUNCTIONS.get(name).getName();
			if (functionClassName == null) {
				throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
			}
			return functionClassName;
		} catch (Exception ne) {
			throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
		}
	}
	
	/**
	 * Returns the no argument constructor for the function
	 * @return AFunction
	 */
	public static IPeak getPeakFn(String name) throws ReflectiveOperationException {
		Class<? extends IPeak> peakClass;
		try {
			peakClass = PEAKS.get(name);
			if (peakClass == null) {
				throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
			}
			return peakClass.newInstance();
		} catch (Exception ne) {
			throw new ClassNotFoundException("There is no peak function with the name "+name+" registered!");
		}	
	}
	
	/**
	 * Returns a class implementing IFunction based on the name supplied 
	 * @param name
	 * @return Peak function class
	 * @throws ClassNotFoundException - if named peak function is not registered
	 */
	public static Class<? extends IPeak> getClassForPeakFn(String name) throws ClassNotFoundException {
		Class<? extends IPeak> peakClass;
		try {
			peakClass = PEAKS.get(name);
			if (peakClass == null) {
				throw new ClassNotFoundException("There is no function with the name "+name+" registered!");
				}
			return peakClass;
		} catch (Exception ne) {
			throw new ClassNotFoundException("There is no peak function with the name "+name+" registered!");
		}
	}
	
	/**
	 * Returns the no argument constructor for the function
	 * @return AFunction
	 */
	@Deprecated
	public static IFunction getFunction(String name, IParameter[] args) throws Exception {
		Class<? extends IFunction> clazz = FUNCTIONS.get(name);
		
		final Constructor<? extends IFunction> c = clazz.getConstructor(IParameter[].class);
		return c.newInstance((Object[])args);
	}

	/**
	 * Get the class for a name
	 * @param functionName
	 * @return name
	 */
	@Deprecated
	public static Class<? extends IFunction> getClass(String functionName) {
		return FUNCTIONS.get(functionName);
	}

	/**
	 * Get the name of a class.
	 * @param clazz
	 * @return name
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Deprecated
	public static String getName(Class<? extends IFunction> clazz) throws Exception {
		IFunction function = clazz.newInstance();
		return function.getName();
	}
}