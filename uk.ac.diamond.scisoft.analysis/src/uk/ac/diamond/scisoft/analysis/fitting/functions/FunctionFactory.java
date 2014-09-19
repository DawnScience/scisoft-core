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
 *     AFunction box    = FunctionFactory.getFunction("Box", params);
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
	static {
		
		FUNCTIONS = new TreeMap<String, Class<? extends IFunction>>();
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
	 * Call to register external functions with the factory
	 * 
	 * @param clazz
	 * @throws Exception
	 */
	public static void registerFunction(Class<? extends IFunction> clazz) throws Exception {
		final IFunction function = clazz.newInstance();
		final String    name     = function.getName();
		if (!FUNCTIONS.containsKey(name)) {
			FUNCTIONS.put(name, clazz);
		} else {
			throw new Exception("The function "+name+" is registered twice!!");
		}	
	}

	public static Collection<String> getFunctionNames() {
		return FUNCTIONS.keySet();
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
	public static String getName(Class<? extends IFunction> clazz) throws Exception {
		IFunction function = clazz.newInstance();
		return function.getName();
	}

}
