/*-
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;

/**
 * Class which is the fundamentals for any function which is to be used in a composite function. If the isPeak value is
 * specified as true, then the first parameter must be that peak's position
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

	protected void fillParameters(IParameter... params) {
		parameters = new IParameter[params.length];
		for (int i = 0; i < params.length; i++) {
			IParameter p = params[i];
			parameters[i] = new Parameter(p);
		}
	}

	/**
	 * @param f
	 * @param p
	 * @return index of parameter or -1 if parameter is not in function
	 */
	public static int indexOfParameter(IFunction f, IParameter p) {
		if (f == null || p == null)
			return -1;

		if (f instanceof AFunction)
			return ((AFunction) f).indexOfParameter(p);

		for (int j = 0, jmax = f.getNoOfParameters(); j < jmax; j++) {
			if (p == f.getParameter(j)) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * @param p
	 * @return index of parameter or -1 if parameter is not in function
	 */
	protected int indexOfParameter(IParameter p) {
		for (int i = 0; i < parameters.length; i++) {
			if (p == parameters[i]) {
				return i;
			}
		}
		return -1;
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

	protected void fillParameters(double... params) {
		parameters = new Parameter[params.length];
		for (int i = 0; i < params.length; i++) {
			parameters[i] = new Parameter(params[i]);
		}
	}

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
	public double[] getParameterValues() {
		int n = getNoOfParameters();
		double[] result = new double[n];
		for (int j = 0; j < n; j++) {
			result[j] = getParameterValue(j);
		}
		return result;
	}

	@Override
	public void setParameter(int index, IParameter parameter) {
		int j = indexOfParameter(parameter);
		if (j == index)
			return;
		if (j >= 0)
			throw new IllegalArgumentException("Cannot set parameter as it is already used in function");

		parameters[index] = parameter;
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

	@Override
	public double partialDeriv(IParameter param, double... values) {
		for (int i = 0, imax = getNoOfParameters(); i < imax; i++) {
			IParameter p = getParameter(i);
			if (p == param)
				return numericalDerivative(DELTA, param, values);
		}

		return 0;
	}

	private final static double DELTA = 1e-4;

	/**
	 * Calculate partial derivative. This is a numerical approximation.
	 * @param param
	 * @param values
	 * @return partial derivative
	 */
	protected double numericalDerivative(double delta, IParameter param, double... values) {
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
	public CoordinatesIterator getIterator(IDataset... coords) {
		if (coords == null || coords.length == 0) {
			logger.error("No coordinates given to evaluate function");
			throw new IllegalArgumentException("No coordinates given to evaluate function");
		}

		CoordinatesIterator it;
		if (coords.length == 1) {
			it = new CoordinateDatasetIterator(coords[0]);
		} else {
			int[] shape = coords[0].getShape();
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

	@Override
	public DoubleDataset calculateValues(IDataset... coords) {
		CoordinatesIterator it = getIterator(coords);
		DoubleDataset result = new DoubleDataset(it.getShape());
		fillWithValues(result, it);
		result.setName(name);
		return result;
	}

	@Override
	public DoubleDataset calculatePartialDerivativeValues(IParameter param, IDataset... coords) {
		CoordinatesIterator it = getIterator(coords);
		DoubleDataset result = new DoubleDataset(it.getShape());
		if (indexOfParameter(param) >= 0)
			fillWithPartialDerivativeValues(param, result, it);
		result.setName(name);
		return result;
	}

	/**
	 * Fill dataset with values
	 * @param data
	 * @param it
	 */
	abstract public void fillWithValues(DoubleDataset data, CoordinatesIterator it);

	/**
	 * Fill dataset with partial derivatives. Override this numerical approximation
	 * @param param
	 * @param data
	 * @param it
	 */
	public void fillWithPartialDerivativeValues(IParameter param, DoubleDataset data, CoordinatesIterator it) {
		fillWithNumericalDerivativeDataset(DELTA, param, data, it);
	}

	/**
	 * Calculate partial derivative. This is a numerical approximation.
	 * @param delta
	 * @param param
	 * @param data
	 * @param it
	 */
	protected void fillWithNumericalDerivativeDataset(double delta, IParameter param, DoubleDataset data, CoordinatesIterator it) {
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


	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void setDirty(boolean isDirty) {
		dirty = isDirty;
	}

	@Override
	public double weightedResidual(boolean allValues, IDataset weight, IDataset data, IDataset... values) {
		double residual = 0;
		if (allValues) {
			DoubleDataset ddata = (DoubleDataset) DatasetUtils.convertToAbstractDataset(data).cast(AbstractDataset.FLOAT64);
			if (weight == null) {
				residual = ddata.residual(calculateValues(values));
			} else {
				residual = ddata.residual(calculateValues(values), DatasetUtils.convertToAbstractDataset(weight), false);
			}
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
	public double residual(boolean allValues, IDataset data, IDataset... values) {
		return weightedResidual(allValues, null, data, values);
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
		
		AFunction function =  c.newInstance();
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
}
