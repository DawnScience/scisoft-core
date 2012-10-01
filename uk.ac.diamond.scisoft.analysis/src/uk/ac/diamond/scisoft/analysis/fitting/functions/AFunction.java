/*
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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

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

	/**
	 * Constructor which is given a set of parameters to begin with.
	 * 
	 * @param params
	 *            An array of parameters
	 */
	public AFunction(IParameter[] params) {
		fillParameters(params);
	}

	protected void fillParameters(IParameter[] params) {
		parameters = new IParameter[params.length];
		for (int i = 0; i < params.length; i++) {
			IParameter p = params[i];
			parameters[i] = new Parameter(p.getValue(), p.getLowerLimit(), p.getUpperLimit());
			parameters[i].setFixed(p.isFixed());
		}
	}

	/**
	 * Constructor which takes a list of parameter values as its starting configuration
	 * 
	 * @param params
	 *            An array of starting parameter values as doubles.
	 */
	public AFunction(double[] params) {
		fillParameters(params);
	}
	
	protected void fillParameters(double[] params) {
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
	public String getParameterName(int index) {
		return parameters[index].getName();
	}

	@Override
	public void setParameterName(String name, int index) {
		parameters[index].setName(name);
	}

	@Override
	public IParameter[] getParameters() {
		IParameter[] params = new IParameter[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			params[i] = new Parameter(parameters[i]);
		}
		return params;
	}

	@Override
	public int getNoOfParameters() {
		return parameters.length;
	}

	@Override
	public int getNoOfFunctions() {
		return 1;
	}

	@Override
	public IFunction getFunction(int index) {
		return this;
	}

	@Override
	public double getParameterValue(int index) {
		return parameters[index].getValue();
	}

	@Override
	public double[] getParameterValues() {
		double[] result = new double[parameters.length];
		for (int j = 0; j < parameters.length; j++) {
			result[j] = getParameterValue(j);
		}
		return result;
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
		out.append(String.format("'%s' has %d parameters:\n", name, parameters.length));
		for (int i = 0; i < getNoOfParameters(); i++) {
			out.append(String.format("%d) %f in range [%f, %f]\n", i, parameters[i].getValue(),
					parameters[i].getLowerLimit(), parameters[i].getUpperLimit()));
		}
		return out.toString();
	}

	private final static double PERT = 1e-4;

	@Override
	public double partialDeriv(int parameter, double... position) {
		final double v = parameters[parameter].getValue();

		if (v != 0) {
			parameters[parameter].setValue(v*(1-PERT));
			dirty = true;
			final double minval = val(position);
			parameters[parameter].setValue(v*(1+PERT));
			dirty = true;
			final double maxval = val(position);
			parameters[parameter].setValue(v);
			dirty = true;
			return (maxval - minval) / (2. * PERT * v);
		}

		parameters[parameter].setValue(-PERT);
		dirty = true;
		final double minval = val(position);
		parameters[parameter].setValue(PERT);
		dirty = true;
		final double maxval = val(position);
		parameters[parameter].setValue(0);
		dirty = true;
		return (maxval - minval) / (2. * PERT);
	}

	@Override
	public DoubleDataset makeDataset(IDataset... values) {
		DoubleDataset result = makeSerialDataset(values);
		result.setName(name);
		return result;
	}

	protected DoubleDataset makeSerialDataset(IDataset... values) {
		// make the dataset
		if (values == null || values.length == 0) {
			logger.error("No values given to evaluate function");
			throw new IllegalArgumentException("No values given to evaluate function");
		}

		int rank = checkAndGetRank(values);

		if (rank == 1) {
//			logger.info("Make dataset from hypergrid");
			return makeDatasetFromHypergrid(values);
		}

		return makeDatasetFromDatasets(values);
	}

	// check for type of independent variables definition
	private static int checkAndGetRank(IDataset... values) {
		int varlen = values.length;
		int rank = values[0].getRank();
		for (int i = 1; i < varlen; i++) {
			if (values[i].getRank() != rank) {
				logger.error("Input value dataset ({}) does not all possess the correct rank {}", i, rank);
				throw new IllegalArgumentException("Input value dataset (" + i + ") does not all possess the correct rank " + rank);
			}
		}

		return rank;
	}

	protected DoubleDataset makeDatasetFromHypergrid(IDataset... values) {
		int endrank = values.length - 1;
		int[] shape = new int[endrank + 1];
		for (int i = 0; i <= endrank; i++) {
			shape[i] = values[i].getShape()[0];
		}

		DoubleDataset result = new DoubleDataset(shape);

		double[] data = result.getData();
		IndexIterator iter = result.getIterator(true);
		int[] pos = iter.getPos();
		double[] coords = new double[endrank + 1];

		while (iter.hasNext()) {
			// shortcut that sets all coords only when starting a row
			if (pos[endrank] == 0) {
				for (int i = 0; i <= endrank; i++) {
					coords[i] = values[i].getDouble(pos[i]);
				}
			} else {
				coords[endrank] = values[endrank].getDouble(pos[endrank]);
			}
			data[iter.index] = val(coords);
		}
		return result;
	}

	protected DoubleDataset makeDatasetFromDatasets(IDataset... values) {
		int varlen = values.length;
		int[] shape = values[0].getShape();

		DoubleDataset result = new DoubleDataset(shape);

		double[] data = result.getData();
		IndexIterator iter = result.getIterator(true);
		int[] pos = iter.getPos();
		double[] coords = new double[varlen];

		while (iter.hasNext()) {
			for (int i = 0; i < varlen; i++) {
				coords[i] = values[i].getDouble(pos);
			}
			data[iter.index] = val(coords);
		}
		return result;
	}

	public boolean areParametersDirty() {
		return dirty;
	}

	public void markParametersClean() {
		dirty = false;
	}

	@Override
	public double residual(boolean allValues, IDataset data, IDataset... values) {
		double residual = 0;
		if (allValues) {
			DoubleDataset ddata = (DoubleDataset) DatasetUtils.convertToAbstractDataset(data).cast(AbstractDataset.FLOAT64);
			double[] dbuffer = ddata.getData();
			IndexIterator iter = ddata.getIterator(true);
			int rank = checkAndGetRank(values);
			int[] pos = iter.getPos();
			if (rank == 1) {
				int endrank = values.length - 1;
				double[] coords = new double[endrank + 1];

				while (iter.hasNext()) {
					// shortcut that sets all coords only when starting a row
					if (pos[endrank] == 0) {
						for (int i = 0; i <= endrank; i++) {
							coords[i] = values[i].getDouble(pos[i]);
						}
					} else {
						coords[endrank] = values[endrank].getDouble(pos[endrank]);
					}
					double dev = dbuffer[iter.index] - val(coords);
					residual += dev*dev;
				}
			} else {
				int varlen = values.length;
				double[] coords = new double[varlen];

				while (iter.hasNext()) {
					for (int i = 0; i < varlen; i++) {
						coords[i] = values[i].getDouble(pos);
					}
					double dev = dbuffer[iter.index] - val(coords);
					residual += dev*dev;
				}
			}
		} else {
			// stochastic sampling of coords;
//			int NUMBER_OF_SAMPLES = 100;
			//TODO
			logger.error("Stochastic sampling has not been implemented yet");
			throw new UnsupportedOperationException("Stochastic sampling has not been implemented yet");
		}

		return residual;
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
}
