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
		fBean.setType(function.getClass().getName());

		return fBean;
	}

	/**
	 * Method that converts a function bean to an IFunction
	 * @param fBean Bean
	 * @return IFunction
	 */
	public static IFunction functionBeanToIFunction(FunctionBean fBean){
		IParameter[] params = fBean.getParameters();
		if (fBean.getType().equals(Cubic.class.getName()))
			return new Cubic(params);
		else if (fBean.getType().equals(Fermi.class.getName()))
			return new Fermi(params);
		else if (fBean.getType().equals(FermiGauss.class.getName()))
			return new FermiGauss(params);
		else if (fBean.getType().equals(Gaussian.class.getName()))
			return new Gaussian(params);
		else if (fBean.getType().equals(Lorentzian.class.getName()))
			return new Lorentzian(params);
		else if (fBean.getType().equals(Offset.class.getName()))
			return new Offset(params);
		else if (fBean.getType().equals(PearsonVII.class.getName()))
			return new PearsonVII(params);
		else if (fBean.getType().equals(Polynomial.class.getName()))
			return new Polynomial(params);
		else if (fBean.getType().equals(PseudoVoigt.class.getName()))
			return new PseudoVoigt(params);
		else if (fBean.getType().equals(Quadratic.class.getName()))
			return new Quadratic(params);
		else if (fBean.getType().equals(Step.class.getName()))
			return new Step(params);
		else if (fBean.getType().equals(StraightLine.class.getName()))
			return new StraightLine(params);
		else
			throw new IllegalArgumentException("The bean contains a nondescribed function argument");
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
