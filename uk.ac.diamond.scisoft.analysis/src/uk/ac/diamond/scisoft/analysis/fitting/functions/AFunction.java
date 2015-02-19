/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateFunctionPenaltyAdapter;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base abstract class for IFunction implementation. At a minimum, the fillWithValues() method needs
 * to be added. The fillWithPartialDerivativeValues() and/or calculatePartialDerivativeValues()
 * methods can be overridden if exact derivatives are needed.
 * 
 * Note, if the implemented function can alter the number of parameters then it should call its
 * parent operator's update parameters method.
 */
public abstract class AFunction implements IFunction, Serializable {

	/**
	 * Setup the logging facilities
	 */
	private static transient final Logger logger = LoggerFactory.getLogger(AFunction.class);

	/**
	 * The array of parameters which specify all the variables in the minimisation problem
	 */
	protected IParameter[] parameters;

	/**
	 * The name of the function, a description more than anything else.
	 */
	protected String name = "default";

	/**
	 * The description of the function
	 */
	protected String description = "default";

	protected boolean dirty = true;

	protected IMonitor monitor = null;

	protected IOperator parent;
	
	protected final Set<String> functionUseCaseIDs = new HashSet<>();

	/**
	 * Constructor which simply generates the parameters but uninitialised
	 * 
	 * @param numberOfParameters
	 */
	public AFunction(int numberOfParameters) {
		parameters = new Parameter[numberOfParameters];
		for (int i = 0; i < numberOfParameters; i++) {
			parameters[i] = new Parameter();
		}
	}

	/**
	 * Constructor which takes a list of parameter values as its starting configuration
	 * 
	 * @param params
	 *            An array of starting parameter values as doubles.
	 */
	public AFunction(double... params) {
		if (params != null)
			fillParameters(params);
	}

	/**
	 * Constructor which is given a set of parameters to begin with.
	 * 
	 * @param params
	 *            An array of parameters
	 */
	public AFunction(IParameter... params) {
		if (params != null)
			fillParameters(params);
	}

	protected void fillParameters(double... params) {
		if (parameters == null)
			parameters = new IParameter[params.length];
		int n = Math.min(params.length, parameters.length);
		for (int i = 0; i < n; i++) {
			parameters[i] = new Parameter(params[i]);
		}
	}

	protected void fillParameters(IParameter... params) {
		if (parameters == null)
			parameters = new IParameter[params.length];
		int n = Math.min(params.length, parameters.length);
		for (int i = 0; i < n; i++) {
			IParameter p = params[i];
			parameters[i] = new Parameter(p);
		}
	}

	/**
	 * @param function
	 * @param parameter
	 * @return index of parameter or -1 if parameter is not in function
	 */
	public static int indexOfParameter(IFunction function, IParameter parameter) {
		if (function == null || parameter == null)
			return -1;

		if (function instanceof AFunction)
			return ((AFunction) function).indexOfParameter(parameter);

		for (int j = 0, jmax = function.getNoOfParameters(); j < jmax; j++) {
			if (parameter == function.getParameter(j)) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * @param parameter
	 * @return index of parameter or -1 if parameter is not in function
	 */
	protected int indexOfParameter(IParameter parameter) {
		for (int i = 0; i < parameters.length; i++) {
			if (parameter == parameters[i]) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String newName) {
		name = newName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String newDescription) {
		description = newDescription;
	}

	@Override
	public IParameter getParameter(int index) {
		return parameters[index];
	}

	@Override
	public IParameter[] getParameters() {
		IParameter[] params = new IParameter[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			params[i] = parameters[i];
		}
		return params;
	}

	@Override
	public int getNoOfParameters() {
		return parameters.length;
	}

	@Override
	public double getParameterValue(int index) {
		return parameters[index].getValue();
	}

	@Override
	final public double[] getParameterValues() {
		int n = getNoOfParameters();
		double[] result = new double[n];
		for (int j = 0; j < n; j++) {
			result[j] = getParameterValue(j);
		}
		return result;
	}

	@Override
	public void setParameter(int index, IParameter parameter) {
		if (indexOfParameter(parameter) == index)
			return;

		parameters[index] = parameter;
		dirty = true;
	}

	@Override
	public void setParameterValues(double... params) {
		int nparams = Math.min(params.length, parameters.length);

		for (int j = 0; j < nparams; j++) {
			parameters[j].setValue(params[j]);
		}
		dirty = true;
	}

	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		int n = getNoOfParameters();
		out.append(String.format("'%s' has %d parameters:\n", name, n));
		for (int i = 0; i < n; i++) {
			IParameter p = getParameter(i);
			out.append(String.format("%d) %s = %g in range [%g, %g]\n", i, p.getName(), p.getValue(),
					p.getLowerLimit(), p.getUpperLimit()));
		}
		return out.toString();
	}

	@Override
	@Deprecated
	public double partialDeriv(int index, double... values) {
		return partialDeriv(getParameter(index), values);
	}

	/**
	 * This implementation is a numerical approximation. Overriding methods should check
	 * for duplicated parameters before doing any calculation and either cope with this
	 * or use this numerical approximation
	 */
	@Override
	public double partialDeriv(IParameter parameter, double... values) {
		if (indexOfParameter(parameter) < 0)
			return 0;

		return calcNumericalDerivative(A_TOLERANCE, R_TOLERANCE, parameter, values);
	}

	/**
	 * @param param
	 * @return true if there is more than one occurrence of given parameter in function
	 */
	protected boolean isDuplicated(IParameter param) {
		int c = 0;
		int n = getNoOfParameters();
		for (int i = 0; i < n; i++) {
			if (getParameter(i) == param) {
				c++;
				return c > 1;
			}
		}

		return false;
	}

	private final static double DELTA = 1/256.; // initial value
	private final static double DELTA_FACTOR = 0.25;

	protected final static double A_TOLERANCE = 1e-9; // absolute tolerance
	protected final static double R_TOLERANCE = 1e-9; // relative tolerance

	/**
	 * @param abs
	 * @param rel
	 * @param param
	 * @param values
	 * @return partial derivative up to tolerances
	 */
	protected double calcNumericalDerivative(double abs, double rel, IParameter param, double... values) {
		double delta = DELTA;
		double previous = numericalDerivative(delta, param, values);
		double aprevious = Math.abs(previous);
		double current = 0;
		double acurrent = 0;

		while (delta > Double.MIN_NORMAL) {
			delta *= DELTA_FACTOR;
			current = numericalDerivative(delta, param, values);
			acurrent = Math.abs(current);
			if (Math.abs(current - previous) <= abs + rel*Math.max(acurrent, aprevious))
				break;
			previous = current;
			aprevious = acurrent;
		}

		return current;
	}

	/**
	 * Calculate partial derivative. This is a numerical approximation.
	 * @param param
	 * @param values
	 * @return partial derivative
	 */
	private double numericalDerivative(double delta, IParameter param, double... values) {
		double v = param.getValue();
		double dv = delta * (v != 0 ? v : 1);

		param.setValue(v - dv);
		dirty = true;
		double minval = val(values);
		param.setValue(v + dv);
		dirty = true;
		double maxval = val(values);
		param.setValue(v);
		dirty = true;
		return (maxval - minval) / (2. * dv);
	}

	@Override
	public DoubleDataset makeDataset(IDataset... values) {
		return calculateValues(values);
	}

	/**
	 * @param coords
	 * @return a coordinate iterator
	 */
	final static public CoordinatesIterator createIterator(IDataset... coords) {
		if (coords == null || coords.length == 0) {
			logger.error("No coordinates given to evaluate function");
			throw new IllegalArgumentException("No coordinates given to evaluate function");
		}

		CoordinatesIterator it;
		int[] shape = coords[0].getShape();
		if (coords.length == 1) {
			it = coords[0].getElementsPerItem() == 1 ? new DatasetsIterator(coords) : new CoordinateDatasetIterator(coords[0]);
		} else {
			boolean same = true;
			for (int i = 1; i < shape.length; i++) {
				if (!Arrays.equals(shape, coords[i].getShape())) {
					same = false;
					break;
				}
			}
			if (same && shape.length == 1) // override for 1D datasets
				same = false;

			it = same ? new DatasetsIterator(coords) : new HypergridIterator(coords);
		}
		return it;
	}

	final public CoordinatesIterator getIterator(IDataset... coords) {
		return createIterator(coords);
	}

	@Override
	public DoubleDataset calculateValues(IDataset... coords) {
		CoordinatesIterator it = getIterator(coords);
		DoubleDataset result = new DoubleDataset(it.getShape());
		fillWithValues(result, it);
		result.setName(name);
		return result;
	}

	@Override
	public DoubleDataset calculatePartialDerivativeValues(IParameter parameter, IDataset... coords) {
		CoordinatesIterator it = getIterator(coords);
		DoubleDataset result = new DoubleDataset(it.getShape());
		if (indexOfParameter(parameter) >= 0)
			internalFillWithPartialDerivativeValues(parameter, result, it);
		result.setName(name);
		return result;
	}

	private void internalFillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		if (isDuplicated(parameter)) {
			calcNumericalDerivativeDataset(A_TOLERANCE, R_TOLERANCE, parameter, data, it);
		} else {
			fillWithPartialDerivativeValues(parameter, data, it);
		}
	}

	/**
	 * Fill dataset with values. Implementations should reset the iterator before use
	 * @param data
	 * @param it
	 */
	abstract public void fillWithValues(DoubleDataset data, CoordinatesIterator it);

	/**
	 * Fill dataset with partial derivatives. Implementations should reset the iterator before use
	 * <p>
	 * This implementation is a numerical approximation.
	 * <p>
	 * Note that is called only if there are no duplicated parameters otherwise,
	 * a numerical approximation is used. To change this behaviour, also override
	 * {@link #calculatePartialDerivativeValues(IParameter, IDataset...)}
	 * @param parameter
	 * @param data
	 * @param it
	 */
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		calcNumericalDerivativeDataset(A_TOLERANCE, R_TOLERANCE, parameter, data, it);
	}

	private static final double SMALLEST_DELTA = Double.MIN_NORMAL * 1024 * 1024;

	/**
	 * Calculate partial derivatives up to tolerances
	 * @param abs
	 * @param rel
	 * @param param
	 * @param data
	 * @param it
	 */
	protected void calcNumericalDerivativeDataset(double abs, double rel, IParameter param, DoubleDataset data, CoordinatesIterator it) {
		DoubleDataset previous = new DoubleDataset(it.getShape());
		double delta = DELTA;
		fillWithNumericalDerivativeDataset(delta, param, previous, it);
		DoubleDataset current = new DoubleDataset(it.getShape());

		while (delta > SMALLEST_DELTA) {
			delta *= DELTA_FACTOR;
			fillWithNumericalDerivativeDataset(delta, param, current, it);
			if (Comparisons.allCloseTo(previous, current, rel, abs))
				break;

			DoubleDataset temp = previous;
			previous = current;
			current = temp;
		}
		if (delta <= SMALLEST_DELTA) {
			logger.warn("Numerical derivative did not converge!");
		}

		data.setSlice(current);
	}

	/**
	 * Calculate partial derivative. This is a numerical approximation.
	 * @param delta
	 * @param param
	 * @param data
	 * @param it
	 */
	private void fillWithNumericalDerivativeDataset(double delta, IParameter param, DoubleDataset data, CoordinatesIterator it) {
		double v = param.getValue();
		double dv = delta * (v != 0 ? v : 1);

		param.setValue(v + dv);
		dirty = true;
		fillWithValues(data, it);
		it.reset();
		param.setValue(v - dv);
		dirty = true;
		DoubleDataset temp = new DoubleDataset(it.getShape());
		fillWithValues(temp, it);
		data.isubtract(temp);
		data.imultiply(0.5/dv);
		param.setValue(v);
		dirty = true;
	}

	/**
	 * @return true if any parameters have changed
	 */
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void setDirty(boolean isDirty) {
		dirty = isDirty;
	}

	@Override
	public double residual(boolean allValues, IDataset data, IDataset weight, IDataset... coords) {
		double residual = 0;
		if (allValues) {
			DoubleDataset ddata = (DoubleDataset) DatasetUtils.convertToDataset(data).cast(Dataset.FLOAT64);
			residual = ddata.residual(calculateValues(coords), DatasetUtils.convertToDataset(weight), false);
		} else {
			// stochastic sampling of coords;
//			int NUMBER_OF_SAMPLES = 100;
			//TODO
			logger.error("Stochastic sampling has not been implemented yet");
			throw new UnsupportedOperationException("Stochastic sampling has not been implemented yet");
		}

		if (monitor != null) {
			monitor.worked(1);
			if (monitor.isCancelled()) {
				throw new IllegalMonitorStateException("Monitor cancelled");
			}
		}

		return residual;
	}

	@Override
	@Deprecated
	public double residual(boolean allValues, IDataset data, IDataset... coords) {
		return residual(allValues, data, null, coords);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dirty ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(parameters);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AFunction other = (AFunction) obj;
		if (dirty != other.dirty)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(parameters, other.parameters))
			return false;
		return true;
	}

	@Override
	public AFunction copy() throws Exception {
		Constructor<? extends AFunction> c = getClass().getConstructor();

		IParameter[] localParameters = getParameters();
		
		AFunction function = c.newInstance();
		function.fillParameters(localParameters);
		return function;
	}

	@Override
	public IMonitor getMonitor() {
		return monitor;
	}

	@Override
	public void setMonitor(IMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public IOperator getParentOperator() {
		return parent;
	}

	@Override
	public void setParentOperator(IOperator parent) {
		this.parent = parent;
	}
	
	/**
	 * Generate a Apache MultivariateFunctionPenaltyAdapter from the function
	 * @param inputValues A dataset containing the data values for the optimisation
	 * @param inputCoords A dataset containing the coordinates for the optimization
	 * @return the bounded MultivariateFunctionPenaltyAdapter
	 */
	public MultivariateFunctionPenaltyAdapter getApacheMultivariateFunction(IDataset inputValues, IDataset[] inputCoords) {
		
		final AFunction function = this;
		final IDataset values = inputValues;
		final IDataset[] coords = inputCoords;
		
		MultivariateFunction multivariateFunction = new MultivariateFunction() {
			
			@Override
			public double value(double[] arg0) {
				function.setParameterValuesNoFixed(arg0);
				
				double result = function.residual(true, values, null, coords);
				
				return result;
			}
		};
		
		double offset = 1e12;
		double[] lowerb = getLowerBoundsNoFixed();
		double[] upperb = getUpperBoundsNoFixed();
		double[] scale = new double[lowerb.length];
		for (int i = 0; i < scale.length; i++) {
			scale[i] = offset*0.25;
		}
		
		MultivariateFunctionPenaltyAdapter multivariateFunctionPenaltyAdapter = new MultivariateFunctionPenaltyAdapter(multivariateFunction, lowerb, upperb, offset, scale);
		
		return multivariateFunctionPenaltyAdapter;
	}
	
	/**
	 * Get the parameter values as an array, excluding parameters which are fixed
	 * @return a double[] of non fixed parameter values
	 */
	public double[] getParameterValuesNoFixed() {
		
		ArrayList<Double> values = new ArrayList<Double>();
		
		for (int i = 0; i < getNoOfParameters(); i++) {
			if (getParameter(i).isFixed() == false) {
				values.add(getParameter(i).getValue());
			}
		}
		
		double[] start = new double[values.size()];
		
		for (int i= 0; i < start.length; i++) {
			start[i] = values.get(i);
		}
		
		return start;
		
	}
	
	/**
	 * Get the parameter upper bounds as an array, excluding parameters which are fixed
	 * @return a double[] of non fixed parameter upper bounds
	 */
	public double[] getUpperBoundsNoFixed() {
		
		ArrayList<Double> values = new ArrayList<Double>();
		
		for (int i = 0; i < getNoOfParameters(); i++) {
			if (getParameter(i).isFixed() == false) {
				values.add(getParameter(i).getUpperLimit());
			}
		}
		
		double[] start = new double[values.size()];
		
		for (int i= 0; i < start.length; i++) {
			start[i] = values.get(i);
		}
		
		return start;
		
	}
	
	/**
	 * Get the parameter lower bounds as an array, excluding parameters which are fixed
	 * @return a double[] of non fixed parameter lower bounds
	 */
	public double[] getLowerBoundsNoFixed() {
		
		ArrayList<Double> values = new ArrayList<Double>();
		
		for (int i = 0; i < getNoOfParameters(); i++) {
			if (getParameter(i).isFixed() == false) {
				values.add(getParameter(i).getLowerLimit());
			}
		}
		
		double[] start = new double[values.size()];
		
		for (int i= 0; i < start.length; i++) {
			start[i] = values.get(i);
		}
		
		return start;
		
	}
	
	/**
	 * Set the values of all non fixed parameters
	 * @param values
	 */
	public void setParameterValuesNoFixed(double[] values) {
		
		int argpos = 0;
		for (int i = 0; i < getNoOfParameters(); i++) {
			if (getParameter(i).isFixed() == false) {
				getParameter(i).setValue(values[argpos]);
				argpos++;
			}
		}
		
		setDirty(true);
	}
	
	/**
	 * 
	 */
	public void setUseCases(List<String> useCaseIDs) {
		for (String ucid : useCaseIDs) {
			functionUseCaseIDs.add(ucid);
		}
	}
	
	public Set<String> getAllUseCases() {
		return functionUseCaseIDs;
	}
	
	public boolean hasUseCase(String ucid) {
		return functionUseCaseIDs.contains(ucid);
	}
}
