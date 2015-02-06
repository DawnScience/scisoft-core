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
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunctionFactoryExtensionService;
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
		 */
		registerFunctions(
				uk.ac.diamond.scisoft.analysis.fitting.functions.Box.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Cubic.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.CubicSpline.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Fermi.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.FermiGauss.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.GaussianND.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.LorentzianSqr.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Offset.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Quadratic.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.Step.class,
				uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine.class
		);
	}

	private static final Logger logger = LoggerFactory.getLogger(FunctionFactory.class);

	// Not for public use: only used by OSGI
	public FunctionFactory() {

	}

	/**
	 * Provides OSGI with pointer to the extension service from which function
	 * will be loaded (Injected by OSGI).
	 * @param lf
	 */
	public static void setFunctionFactoryService(IFunctionFactoryExtensionService lf) {
		try {
			/**
			 * Tell the extension points to load in.
			 */
			if (lf != null)
				lf.registerExtensionPoints();
		} catch (Throwable t) {
			logger.error("Problem getting extension service");
		}
	}

	/**
	 * Register functions with factory.
	 * @param classes
	 */
	private static void registerFunctions(Class<? extends IFunction>... classes) {
		
        for (Class<? extends IFunction> clazz : classes) {
    		try {
			    registerFunction(clazz);
    		} catch (Throwable e) {
    			logger.error("Cannot register function "+clazz.getCanonicalName()+"!", e);
    		}
		}
	}
	
	/**
	 * Call to register external functions with the factory in function (and peak) maps
	 * 
	 * @param clazz
	 * @throws Exception
	 */
	public static void registerFunction(Class<? extends IFunction> clazz) throws Exception {
		final IFunction function = clazz.newInstance();
		final String    name     = function.getName();
		//Add function to FUNCTION map
		if (!FUNCTIONS.containsKey(name)) {
			FUNCTIONS.put(name, clazz);
		} else {
			throw new Exception("The function "+name+" is registered twice!");
		}
		//If the class is a Peak, add function to PEAK map
		if (function instanceof IPeak) {
			if (!PEAKS.containsKey(name)) {
				PEAKS.put(name, (Class<? extends IPeak>) clazz);
			} else {
				throw new Exception("The peak "+name+" is registered twice!");
			}
		}
	}
	
	/**
	 * Returns the complete list of function names registered with the factory
	 * @return Collection of function names (strings)
	 */
	public static Collection<String> getFunctionNames() {
		return FUNCTIONS.keySet();
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
	 * @return Collection of strings of peak function names
	 */
	public static Collection<String> getPeakFunctionNames() {
		return PEAKS.keySet();
	}
	
	/**
	 * Returns the complete set of peak functions with their names and classes
	 * registered with the factory
	 * @return Map of names (strings) and classes (IPeak)
	 */
	public static Map<String, Class<? extends IPeak>> getPeakFunctions() {
		return PEAKS;
	}
	
	/**
	 * Returns the no argument constructor for the function
	 * @return AFunction
	 */
	public static IFunction getFunction(String name) throws Exception {
		Class<? extends IFunction> clazz = FUNCTIONS.get(name);
		return clazz.newInstance();
	}
	
	/**
	 * Returns the no argument constructor for the function
	 * @return AFunction
	 */
	public static IFunction getFunction(String name, double... args) throws Exception {
		Class<? extends IFunction> clazz = FUNCTIONS.get(name);
		
		final Constructor<? extends IFunction> c = clazz.getConstructor(double[].class);
		return c.newInstance(args);
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
	
	/**
	 * Returns a class implementing IFunction based on the name supplied 
	 * @param functionName
	 * @return Function class
	 * @throws Exception - if named function is not registered
	 */
	public static Class<? extends IFunction> getClassForFunctionName(String functionName) throws Exception {
		Class<? extends IFunction> functionClass  = FUNCTIONS.get(functionName);
		
		if (functionClass == null) {
			throw new Exception("There is no function with the name "+functionName+" registered!");
		}
		return functionClass;
	}
	
	/**
	 * Returns a class implementing IFunction based on the name supplied 
	 * @param peakFunctionName
	 * @return Peak function class
	 * @throws Exception - if named peak function is not registered
	 */
	public static Class<? extends IPeak> getClassForPeakFunctionName(String peakFunctionName) throws Exception {
		Class<? extends IPeak> peakClass = PEAKS.get(peakFunctionName);
		
		if (peakClass == null) {
			throw new Exception("There is no function with the name "+peakFunctionName+" registered!");
		}
		return peakClass;
	}
}