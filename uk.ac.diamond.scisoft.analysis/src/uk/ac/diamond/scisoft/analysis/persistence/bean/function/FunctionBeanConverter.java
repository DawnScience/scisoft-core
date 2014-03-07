/*-
 * Copyright 2013 Diamond Light Source Ltd.
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
package uk.ac.diamond.scisoft.analysis.persistence.bean.function;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Cubic;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Fermi;
import uk.ac.diamond.scisoft.analysis.fitting.functions.FermiGauss;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IParameter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Parameter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Quadratic;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Step;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;

/**
 * Class used to convert from an IFunction to a FunctionBean and vice-versa
 *
 */
public class FunctionBeanConverter {

	private static Logger logger = LoggerFactory.getLogger(FunctionBeanConverter.class);

	/**
	 * Method that converts an IFunction to a FunctionBean
	 * @param name
	 * @param function
	 * @return FunctionBean
	 */
	public static FunctionBean iFunctionToFunctionBean(String name, IFunction function){
		FunctionBean fBean = new FunctionBean();
		fBean.setName(name);
		IParameter[] iParameters = function.getParameters();
		Parameter[] parameters = new Parameter[iParameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = new Parameter(iParameters[i]);
		}
		fBean.setParameters(parameters);
		fBean.setType(function.getClass().getName());

		return fBean;
	}

	/**
	 * Method that converts a function bean to an IFunction using reflection
	 * @param fBean Bean
	 * @return IFunction
	 */
	public static IFunction functionBeanToIFunction(FunctionBean fBean) {
		IParameter[] params = fBean.getParameters();
		try {
			Class<?> clazz = Class.forName(fBean.getType());
			Constructor<?> constructor = clazz.getConstructor(IParameter[].class);
			return (IFunction) constructor.newInstance((Object)params);
		} catch (ClassNotFoundException e) {
			logger.error("The Class could not be found:" + e.getMessage());
			return null;
		} catch (NoSuchMethodException e) {
			logger.error("Constructor does not exist:" + e.getMessage());
			return null;
		} catch (SecurityException e) {
			logger.error("Security manager might be preventing reflection:" + e.getMessage());
			return null;
		} catch (InstantiationException e) {
			logger.error("Could not instantiate class:" + e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			logger.error("Constructor does not exist:" + e.getMessage());
			return null;
		} catch (IllegalArgumentException e) {
			logger.error("Illegal arguments:" + e.getMessage());
			return null;
		} catch (InvocationTargetException e) {
			logger.error("Invocation Exception:" + e.getMessage());
			return null;
		}
	}

	/**
	 * Method that returns true if the type of ROI is supported by the ROIBeanConverter
	 * @return boolean
	 */
	public static boolean isAFunctionSupported(IFunction function){
		if (function instanceof Cubic) {
			return true;
		} else if (function instanceof Fermi) {
			return true;
		} else if (function instanceof FermiGauss) {
			return true;
		} else if (function instanceof Gaussian) {
			return true;
		} else if (function instanceof Lorentzian) {
			return true;
		} else if (function instanceof Offset) {
			return true;
		} else if (function instanceof PearsonVII) {
			return true;
		} else if (function instanceof Polynomial) {
			return true;
		} else if (function instanceof PseudoVoigt) {
			return true;
		} else if (function instanceof Quadratic) {
			return true;
		} else if (function instanceof Step) {
			return true;
		} else if (function instanceof StraightLine) {
			return true;
		}
		return false;
	}
}
