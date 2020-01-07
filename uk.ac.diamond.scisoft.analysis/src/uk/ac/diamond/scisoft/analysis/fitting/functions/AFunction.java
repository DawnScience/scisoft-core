/*-
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
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
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

	protected void setNames(String name, String description, String... parameterNames) {
		this.name = name;
		this.description = description;
		int n = Math.min(parameterNames.length, parameters.length);
		for (int i = 0; i < n; i++) {
			IParameter p = getParameter(i);
			p.setName(parameterNames[i]);
		}
	}

	/**
	 * Implement to set names and descriptions for function and its parameters
	 */
	protected abstract void setNames();


	/**
	 * Constructor for zero parameter functions
	 */
	public AFunction() {
		parameters = new IParameter[0];

		setNames();
	}

	/**
	 * Constructor which simply generates the parameters but uninitialised
	 * 
	 * @param numberOfParameters
	 */
	public AFunction(int numberOfParameters) {
		parameters = createParameters(numberOfParameters);

		setNames();
	}

	protected static IParameter[] createParameters(int numberOfParameters) {
		IParameter[] params = new IParameter[numberOfParameters];
		for (int i = 0; i < numberOfParameters; i++) {
			params[i] = new Parameter();
		}
		return params;
	}

	/**
	 * Constructor which takes a list of parameter values as its starting configuration
	 * 
	 * @param params
	 *            An array of starting parameter values as doubles.
	 */
	public AFunction(double... params) {
		parameters = createParameters(params.length);

		setParameterValues(params);
		setNames();
	}

	/**
	 * Constructor which is given a set of parameters to begin with.
	 * 
	 * @param params
	 *            An array of parameters
	 */
	public AFunction(IParameter... params) {
		parameters = createParameters(params.length);

		setParameters(params);
		setNames();
	}

	/**
	 * @param function
	 * @param parameter
	 * @return index of parameter or -1 if parameter is not in function
	 */
	public static int indexOfParameter(IFunction function, IParameter parameter) {
		if (function == null || parameter == null) {
			return -1;
		}

		if (function instanceof AFunction) {
			return ((AFunction) function).indexOfParameter(parameter);
		}

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
		if (indexOfParameter(parameter) == index) {
			return;
		}

		parameters[index] = parameter;
		setDirty(true);
	}

	@Override
	public void setParameterValues(double... params) {
		int nparams = Math.min(params.length, parameters.length);

		for (int j = 0; j < nparams; j++) {
			parameters[j].setValue(params[j]);
		}
		setDirty(true);
	}

	protected void setParameters(IParameter... params) {
		int nparams = Math.min(params.length, parameters.length);

		for (int j = 0; j < nparams; j++) {
			IParameter op = params[j];
			IParameter np = parameters[j];
			np.setValue(op.getValue());
			np.setLimits(op.getLowerLimit(), op.getUpperLimit());
			np.setFixed(op.isFixed());
		}
		setDirty(true);
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

	/**
	 * This implementation is a numerical approximation. Overriding methods should check
	 * for duplicated parameters before doing any calculation and either cope with this
	 * or use this numerical approximation
	 */
	@Override
	public double partialDeriv(IParameter parameter, double... values) {
		if (indexOfParameter(parameter) < 0) {
			return 0;
		}

		return calcNumericalDerivative(A_TOLERANCE, R_TOLERANCE, parameter, values);
	}

	/**
	 * @param param
	 * @return true if there is more than one occurrence of given parameter in function
	 */
	protected boolean isDuplicated(IParameter param) {
		boolean found = false;
		int n = getNoOfParameters();
		for (int i = 0; i < n; i++) {
			if (getParameter(i) == param) {
				if (found) { // found twice
					return true;
				}

				found = true;
			}
		}

		return false;
	}

	private final static double DELTA = 1/256.; // initial value
	private final static double DELTA_FACTOR = 0.25;

	protected final static double A_TOLERANCE = 1e-9; // absolute tolerance
	protected final static double R_TOLERANCE = 1e-9; // relative tolerance

	/**
	 * @param param
	 * @param delta
	 * @return true if delta is large enough to change the parameter value
	 */
	private static final boolean isDeltaLargeEnough(IParameter param, double delta) {
		double v = Math.abs(param.getValue());
		return (v == 0 ? delta : delta * v) > Math.ulp(v);
	}

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
		double absDifference;
		double previousAbsDifference = Double.POSITIVE_INFINITY;
		final double absDifferenceRatio = 1.00; 

		delta *= DELTA_FACTOR;
		while (isDeltaLargeEnough(param, delta)) {
			current = numericalDerivative(delta, param, values);
			acurrent = Math.abs(current);
			absDifference = Math.abs(current - previous);
			if (absDifference <= abs + rel*Math.max(acurrent, aprevious)) {
				break;
			}
			// If the difference is increasing, then we are no longer
			// approaching the convergence criterion. Assume we have just
			// passed the best we are going to get, and break, passing back
			// the previous value.
			if (absDifference > absDifferenceRatio * previousAbsDifference) {
				current = previous;
				break;
			}
			previousAbsDifference = absDifference;
			previous = current;
			aprevious = acurrent;
			delta *= DELTA_FACTOR;
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
		setDirty(true);
		double minval = val(values);
		param.setValue(v + dv);
		setDirty(true);
		double maxval = val(values);
		param.setValue(v);
		setDirty(true);
		return (maxval - minval) / (2. * dv);
	}

	@Override
	public DoubleDataset calculateValues(IDataset... coords) {
		return calculateValues(null, coords);
	}

	private DoubleDataset calculateValues(int[] outShape, IDataset... coords) {
		CoordinatesIterator it = CoordinatesIterator.createIterator(outShape, coords);
		DoubleDataset result = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		fillWithValues(result, it);
		result.setName(name);
		return result;
	}

	@Override
	public DoubleDataset calculatePartialDerivativeValues(IParameter parameter, IDataset... coords) {
		return calculatePartialDerivativeValues(null, parameter, coords);
	}

	private DoubleDataset calculatePartialDerivativeValues(int[] outShape, IParameter parameter, IDataset... coords) {
		CoordinatesIterator it = CoordinatesIterator.createIterator(outShape, coords);
		DoubleDataset result = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		if (indexOfParameter(parameter) >= 0) {
			internalFillWithPartialDerivativeValues(parameter, result, it);
		}
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

	/**
	 * Calculate partial derivatives up to tolerances
	 * @param abs
	 * @param rel
	 * @param param
	 * @param data
	 * @param it
	 */
	protected void calcNumericalDerivativeDataset(double abs, double rel, IParameter param, DoubleDataset data, CoordinatesIterator it) {
		DoubleDataset previous = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		DoubleDataset temp = DatasetFactory.zeros(previous);
		double delta = DELTA;
		fillWithNumericalDerivativeDataset(delta, param, previous, temp, it);
		DoubleDataset current = DatasetFactory.zeros(DoubleDataset.class, it.getShape());

		delta *= DELTA_FACTOR;
		while (isDeltaLargeEnough(param, delta)) {
			fillWithNumericalDerivativeDataset(delta, param, current, temp, it);
			if (Comparisons.allCloseTo(previous, current, rel, abs)) {
				break;
			}

			DoubleDataset t = previous;
			previous = current;
			current = t;
			delta *= DELTA_FACTOR;
		}
//		if (!isDeltaLargeEnough(param, delta)) {
//			logger.warn("Numerical derivative did not converge!");
//		}

		data.setSlice(current);
	}

	/**
	 * Calculate partial derivative. This is a numerical approximation.
	 * @param delta
	 * @param param
	 * @param data
	 * @param temp
	 * @param it
	 */
	private void fillWithNumericalDerivativeDataset(double delta, IParameter param, DoubleDataset data, DoubleDataset temp, CoordinatesIterator it) {
		double v = param.getValue();
		double dv = delta * (v != 0 ? v : 1);

		param.setValue(v + dv);
		setDirty(true);
		fillWithValues(data, it);
		it.reset();
		param.setValue(v - dv);
		setDirty(true);
		fillWithValues(temp, it);
		it.reset();
		data.isubtract(temp);
		data.imultiply(0.5/dv);
		param.setValue(v);
		setDirty(true);
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
			DoubleDataset ddata = DatasetUtils.convertToDataset(data).cast(DoubleDataset.class);
			residual = ddata.residual(calculateValues(ddata.getShapeRef(), coords), DatasetUtils.convertToDataset(weight), false);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(parameters);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AFunction other = (AFunction) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (!Arrays.equals(parameters, other.parameters)) {
			return false;
		}
		return true;
	}

	@Override
	public AFunction copy() throws Exception {
		Constructor<? extends AFunction> c = getClass().getConstructor(IParameter[].class);
		
		//Makes a copy of each parameter, rather passing reference.
		int nParameters = parameters.length;
		IParameter[] paramCopy = new IParameter[nParameters];
		for (int i = 0; i < parameters.length; i++) {
			paramCopy[i] = new Parameter(parameters[i]);
		}
		
		AFunction function = c.newInstance((Object) paramCopy);
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
		List<Double> values = new ArrayList<>();

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
}
