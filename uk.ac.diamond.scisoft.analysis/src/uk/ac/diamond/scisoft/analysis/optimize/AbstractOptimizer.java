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

package uk.ac.diamond.scisoft.analysis.optimize;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IParameter;

public abstract class AbstractOptimizer implements IOptimizer {

	protected IFunction function;
	protected ArrayList<IParameter> params; // list of free parameters
	protected int n; // number of free parameters
	protected DoubleDataset[] coords;
	protected DoubleDataset data;
	protected DoubleDataset weight;

	public AbstractOptimizer() {
		params = new ArrayList<IParameter>();
		weight = null;
	}

	/**
	 * initialize parameters by finding unique and unfixed ones
	 */
	private void initializeParameters() {
		params.clear();
		n = function.getNoOfParameters();
		for (int i = 0; i < n; i++) {
			IParameter p = function.getParameter(i);
			if (p.isFixed())
				continue;

			boolean found = false;
			for (IParameter op : params) {
				if (p == op) {
					found = true;
					break;
				}
			}
			if (!found)
				params.add(p);
		}
		n = params.size();
	}

	public void setWeight(DoubleDataset weight) {
		this.weight = weight;
	}

	@Override
	public void optimize(IDataset[] coordinates, IDataset data, IFunction function) throws Exception {
		this.function = function;
		int nc = coordinates.length;
		this.coords = new DoubleDataset[nc];
		for (int i = 0; i < nc; i++) {
			coords[i] = (DoubleDataset) DatasetUtils.cast(coordinates[i], AbstractDataset.FLOAT64);
		}

		this.data = (DoubleDataset) DatasetUtils.cast(data, AbstractDataset.FLOAT64);
		initializeParameters();
		internalOptimize();
	}

	public DoubleDataset[] getCoords() {
		return coords;
	}

	public DoubleDataset getData() {
		return data;
	}

	public IFunction getFunction() {
		return function;
	}

	/**
	 * @return list of unfixed and unique parameters
	 */
	public List<IParameter> getParameters() {
		return params;
	}

	/**
	 * @return array of unfixed and unique parameter values
	 */
	public double[] getParameterValues() {
		double[] values = new double[n];
		for (int i = 0; i < n; i++) {
			values[i] = params.get(i).getValue();
		}
		return values;
	}

	/**
	 * Set parameter values back in function
	 * @param parameters
	 */
	public void setParameterValues(double[] parameters) {
		if (parameters.length > n) {
			throw new IllegalArgumentException("Number of parameters should match number of unfixed and unique parameters in function");
		}
		for (int i = 0; i < parameters.length; i++) {
			params.get(i).setValue(parameters[i]);
		}
		function.setDirty(true);
	}

	public DoubleDataset calculateValues() {
		return (DoubleDataset) DatasetUtils.cast(function.calculateValues(coords), AbstractDataset.FLOAT64);
	}

	public double calculateResidual(double[] parameters) {
		setParameterValues(parameters);
		return function.residual(true, data, weight, coords);
	}

	public double calculateResidual() {
		return function.residual(true, data, weight, coords);
	}

	private final static double DELTA = 1/256.; // initial value
	private final static double DELTA_FACTOR = 0.25;

	private int indexOfParameter(IParameter parameter) {
		for (int i = 0; i < n; i++) {
			if (parameter == params.get(i))
				return i;
		}
		return -1;
	}

	public double calculateResidualDerivative(IParameter parameter, double[] parameters) {
		if (indexOfParameter(parameter) < 0)
			return 0;

		setParameterValues(parameters);
		CoordinatesIterator it = AFunction.createIterator(coords);
		DoubleDataset result = new DoubleDataset(it.getShape());

		return calculateNumericalDerivative(1e-9, 1e-9, parameter, result, it);
	}

	private double calculateNumericalDerivative(double abs, double rel, IParameter parameter, DoubleDataset result, CoordinatesIterator it) {
		double delta = DELTA;
		double previous = evaluateNumericalDerivative(delta, parameter, result, it);
		double current = 0;

		while (delta > Double.MIN_NORMAL) {
			delta *= DELTA_FACTOR;
			current = evaluateNumericalDerivative(delta, parameter, result, it);
			if (Math.abs(current - previous) <= abs + rel*Math.max(Math.abs(current), Math.abs(previous)))
				break;
			previous = current;
		}

		return current;
	}

	private double evaluateNumericalDerivative(double delta, IParameter parameter, DoubleDataset current, CoordinatesIterator it) {
		double v = parameter.getValue();
		double dv = delta * (v != 0 ? v : 1);

		double d = 0;
		parameter.setValue(v + dv);
		function.setDirty(true);
		if (function instanceof AFunction) {
			((AFunction) function).fillWithValues(current, it);
			it.reset();
			d = data.residual(current, weight, false);
		} else {
			d = function.residual(true, data, weight, coords);
		}

		parameter.setValue(v - dv);
		function.setDirty(true);
		if (function instanceof AFunction) {
			((AFunction) function).fillWithValues(current, it);
			it.reset();
			d -= data.residual(current, weight, false);
		} else {
			d -= function.residual(true, data, weight, coords);
		}

		return d * 0.5/dv;
	}

	/**
	 * This should use do the work and set the parameters
	 */
	abstract void internalOptimize() throws Exception;
}
