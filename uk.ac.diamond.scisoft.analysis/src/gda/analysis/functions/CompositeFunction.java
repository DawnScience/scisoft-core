/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.functions;


import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;


/**
 * Class which contains all the information about a particular function which is made up out of several other
 * functions
 */
public class CompositeFunction extends AFunction {

	private List<Parameter> parameterList; // holds a list of all parameters
	private List<AFunction> functionList; // holds a list of all functions
	private NavigableMap<Integer, AFunction> functionMap; // holds a mapping from offsets from parameters to functions

	/**
	 * This constructor is simply to start an empty composite function.
	 */
	public CompositeFunction() {
		super(0);
		parameterList = new ArrayList<Parameter>();
		functionList = new ArrayList<AFunction>();
		functionMap = new TreeMap<Integer, AFunction>();
	}

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(CompositeFunction.class);

	/**
	 * Adds a new function of the type AFunction to the array
	 * 
	 * @param function
	 */
	public void addFunction(AFunction function) {
		addFunction(parameterList, functionList, functionMap, function);
	}

	private void addFunction(List<Parameter> plist, List<AFunction> flist, NavigableMap<Integer, AFunction> fmap, AFunction function) {
		int psize = plist.size();
		flist.add(function);
		fmap.put(psize, function);			
		for (Parameter p : function.parameters) {
			plist.add(p);
		}		
	}

	/**
	 * Removes a function from the list
	 * 
	 * @param index
	 *            The position in the vector to be removed
	 */
	public void removeFunction(int index) {
		List<Parameter> plist = new ArrayList<Parameter>();
		List<AFunction> flist = new ArrayList<AFunction>();
		NavigableMap<Integer, AFunction> fmap = new TreeMap<Integer, AFunction>();

		int nfuncs = functionList.size();
		if (index > nfuncs) {
			logger.error("Index exceed bounds");
			throw new IndexOutOfBoundsException("Index exceed bounds");
		}
		for (int n = 0; n < nfuncs; n++) {
			if (n != index) {
				AFunction f = functionList.get(n);
				addFunction(plist, flist, fmap, f);
			}
		}
		parameterList = plist;
		functionList = flist;
		functionMap = fmap;
	}

	@Override
	public int getNoOfFunctions() {
		return functionList.size();
	}

	@Override
	public AFunction getFunction(int index) {
		return functionList.get(index);
	}

	/**
	 * Function that evaluates the whole function at a single point
	 * 
	 * @param position
	 *            The value to evaluate the function at
	 * @return A double containing the evaluated value.
	 */
	@Override
	public double val(double... position) {
		// Check x and a size for correctness
		double y = 0.;
		// Just sum over the individual functions
		for (AFunction f : functionList)
			y += f.val(position);

		return y;
	}

	/**
	 * Create a set of datasets each containing the composite function and its constituent
	 * parts evaluated over the values
	 * 
	 * @param values
	 *            datasets containing all the values to evaluate the function at
	 * @return an array of datasets
	 */
	public DoubleDataset[] display(DoubleDataset... values) {
		if (values == null || values.length == 0) {
			
		}
		int noOfFunctions = getNoOfFunctions();
		
		DoubleDataset[] outputs = new DoubleDataset[noOfFunctions + 1];

		outputs[0] = makeDataSet(values);
		outputs[0].setName("Composite function");

		// now add the data for each bit in turn
		int j = 1;
		for (AFunction f : functionList) {
			outputs[j] = f.makeDataSet(values);
			outputs[j++].setName(f.getName());
		}

		return outputs;
	}

	/**
	 * Create a set of datasets each containing the composite function and its constituent
	 * parts evaluated over the values
	 * 
	 * @param XValues
	 *            A dataset containing all the X values to calculate the data at
	 * @param DataValues
	 *            The data that is being fitted too, for visual help.
	 * @return an array of datasets 
	 */
	public DoubleDataset[] display(DoubleDataset XValues, DoubleDataset DataValues) {
		int noOfFunctions = getNoOfFunctions();

		DoubleDataset[] outputs = new DoubleDataset[noOfFunctions + 4];

		outputs[0] = new DoubleDataset(DataValues);

		outputs[1] = makeDataSet(XValues);
		outputs[1].setName("Composite function");
		// now add the data
		for (int i = 0; i < XValues.getSize(); i++) {
			double value = val(XValues.get(i));
			outputs[1].set(value, i);
		}

		// now add the errors to the graph, this should provide a good view to
		// how good the fit is quite nicely.
		outputs[2] = new DoubleDataset(XValues);
		outputs[2].setName("Error Value");
		double offset = DataValues.min().doubleValue() - ((DataValues.max().doubleValue() - DataValues.min().doubleValue()) / 5.0);
		for (int i = 0; i < XValues.getSize(); i++) {
			double value = (val(XValues.get(i)) - DataValues.get(i)) + offset;
			outputs[2].set(value, i);
		}

		outputs[3] = new DoubleDataset(XValues);
		outputs[3].setName("Error Offset");
		// centre for the error
		for (int i = 0; i < XValues.getSize(); i++) {
			double value = offset;
			outputs[3].set(value, i);
		}

		// now add the data for each bit in turn
		int j = 4;
		for (AFunction f : functionList) {
			outputs[j] = f.makeDataSet(XValues);
			outputs[j++].setName(f.getName());
		}

		return outputs;

	}

	/**
	 * Function that returns the link to a particular parameter
	 * 
	 * @param index
	 *            the index of the parameter
	 * @return a pointer to the parameter requested
	 * @throws IndexOutOfBoundsException
	 *             Error raised if the index is too large
	 */
	@Override
	public Parameter getParameter(int index) throws IndexOutOfBoundsException {
		try {
			return parameterList.get(index);
		} catch (IndexOutOfBoundsException e) {
			logger.error("Index not in range");
			throw new IndexOutOfBoundsException("Index not in range");
		}
	}

	/**
	 * Function that gets a double array of all the parameters values
	 * 
	 * @return The double array of all the parameter values
	 */
	@Override
	public Parameter[] getParameters() {
		Parameter[] result = new Parameter[getNoOfParameters()];
		int n = 0;
		for (Parameter p : parameterList) {
			result[n++] = p;
		}

		return result;
	}

	@Override
	public int getNoOfParameters() {
		return parameterList.size();
	}

	@Override
	public double getParameterValue(int index) {
		return getParameter(index).getValue();
	}

	@Override
	public double[] getParameterValues() {
		double[] result = new double[getNoOfParameters()];
		int n = 0;
		for (Parameter p : parameterList) {
			result[n++] = p.getValue();
		}

		return result;
	}

	@Override
	public void setParameterValues(double... params) {
		int nparams = Math.min(params.length, getNoOfParameters());
		for (int n = 0; n < nparams; n++) {
			parameterList.get(n).setValue(params[n]);
		}
		for (AFunction f : functionList)
			f.dirty = true;
		dirty = true;
	}

	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		int nf = functionList.size();
		if (nf > 1) {
			for (int n = 0; n < nf; n++) {
				out.append(String.format("Function %d - ", n));
				out.append(functionList.get(n).toString());
			}
		} else if (nf == 1)
			out.append(functionList.get(0).toString());

		return out.toString();
	}

	@Override
	public double partialDeriv(int Parameter, double... position) throws IndexOutOfBoundsException {
		Integer count = functionMap.floorKey(Parameter);
		if (count == null) {
			throw new IndexOutOfBoundsException("There are not enough parameters in the composite function");
		}
		return functionMap.get(count).partialDeriv(Parameter - count, position);
	}

}
