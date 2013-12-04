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
		if (function instanceof Cubic) {
			fBean.setType(FunctionBean.TYPE_CUBIC);
		} else if (function instanceof Fermi) {
			fBean.setType(FunctionBean.TYPE_FERMI);
		} else if (function instanceof FermiGauss) {
			fBean.setType(FunctionBean.TYPE_FERMI_GAUSS);
		} else if (function instanceof Gaussian) {
			fBean.setType(FunctionBean.TYPE_GAUSSIAN);
		} else if (function instanceof Lorentzian) {
			fBean.setType(FunctionBean.TYPE_LORENTZIAN);
		} else if (function instanceof Offset) {
			fBean.setType(FunctionBean.TYPE_OFFSET);
		} else if (function instanceof PearsonVII) {
			fBean.setType(FunctionBean.TYPE_PEARSONVII);
		} else if (function instanceof Polynomial) {
			fBean.setType(FunctionBean.TYPE_POLYNOMIAL);
		} else if (function instanceof PseudoVoigt) {
			fBean.setType(FunctionBean.TYPE_PSEUDO_VOIGT);
		} else if (function instanceof Quadratic) {
			fBean.setType(FunctionBean.TYPE_QUADRATIC);
		} else if (function instanceof Step) {
			fBean.setType(FunctionBean.TYPE_STEP);
		} else if (function instanceof StraightLine) {
			fBean.setType(FunctionBean.TYPE_STRAIGHT_LINE);
		}
		
		return fBean;
	}

	/**
	 * Method that converts a function bean to an IFunction
	 * @param fBean Bean
	 * @return IFunction
	 */
	public static IFunction functionBeanToIFunction(FunctionBean fBean){
		IParameter[] params = fBean.getParameters();
		switch (fBean.getType()) {
		case FunctionBean.TYPE_CUBIC:
			return new Cubic(params);
		case FunctionBean.TYPE_FERMI:
			return new Fermi(params);
		case FunctionBean.TYPE_FERMI_GAUSS:
			return new FermiGauss(params);
		case FunctionBean.TYPE_GAUSSIAN:
			return new Gaussian(params);
		case FunctionBean.TYPE_LORENTZIAN:
			return new Lorentzian(params);
		case FunctionBean.TYPE_OFFSET:
			return new Offset(params);
		case FunctionBean.TYPE_PEARSONVII:
			return new PearsonVII(params);
		case FunctionBean.TYPE_POLYNOMIAL:
			return new Polynomial(params);
		case FunctionBean.TYPE_PSEUDO_VOIGT:
			return new PseudoVoigt(params);
		case FunctionBean.TYPE_QUADRATIC:
			return new Quadratic(params);
		case FunctionBean.TYPE_STEP:
			return new Step(params);
		case FunctionBean.TYPE_STRAIGHT_LINE:
			return new StraightLine(params);
		default:
			throw new IllegalArgumentException("The bean contains a nondescribed function argument");
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
