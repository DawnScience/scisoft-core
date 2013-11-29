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


import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;


/**
 * Class which contains all the information about a particular function which is made up out of several other
 * functions
 */
public class CompositeFunction extends AOperator {

	private List<IFunction> functionList; // holds a list of all functions
	private NavigableMap<Integer, IFunction> functionMap; // holds a mapping from offsets from parameters to functions

	/**
	 * This constructor is simply to start an empty composite function.
	 */
	public CompositeFunction() {
		super();
		functionList = new ArrayList<IFunction>();
		functionMap = new TreeMap<Integer, IFunction>();
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
	@Override
	public void addFunction(IFunction function) {
		addFunction(params, functionList, functionMap, function);
	}

	private void addFunction(List<IParameter> plist, List<IFunction> flist, NavigableMap<Integer, IFunction> fmap, IFunction function) {
		int psize = plist.size();
		flist.add(function);
		fmap.put(psize, function);
		for (int i = 0, imax = function.getNoOfParameters(); i < imax; i++) {
			IParameter p = function.getParameter(i);
			plist.add(p);
		}		
	}

	@Override
	public boolean isExtendible() {
		return true;
	}

	@Override
	public int getRequiredFunctions() {
		return -1;
	}

	/**
	 * Removes a function from the list
	 * 
	 * @param index
	 *            The position in the vector to be removed
	 */
	@Override
	public void removeFunction(int index) {
		List<IParameter> plist = new ArrayList<IParameter>();
		List<IFunction> flist = new ArrayList<IFunction>();
		NavigableMap<Integer, IFunction> fmap = new TreeMap<Integer, IFunction>();

		int nfuncs = functionList.size();
		if (index >= nfuncs) {
			logger.error("Index exceed bounds");
			throw new IndexOutOfBoundsException("Index exceed bounds");
		}
		for (int n = 0; n < nfuncs; n++) {
			if (n != index) {
				IFunction f = functionList.get(n);
				addFunction(plist, flist, fmap, f);
			}
		}
		params= plist;
		functionList = flist;
		functionMap = fmap;
	}

	@Override
	public int getNoOfFunctions() {
		return functionList.size();
	}

	@Override
	public AFunction getFunction(int index) {
		return (AFunction) functionList.get(index);
	}
	
	@Override
	public IFunction[] getFunctions() {
		return functionList.toArray(new IFunction[functionList.size()]);
	}

	@Override
	public void setFunction(int index, IFunction function) {
		List<IParameter> plist = new ArrayList<IParameter>();
		List<IFunction> flist = new ArrayList<IFunction>();
		NavigableMap<Integer, IFunction> fmap = new TreeMap<Integer, IFunction>();

		int nfuncs = functionList.size();
		if (index > nfuncs) {
			logger.error("Index exceed bounds");
			throw new IndexOutOfBoundsException("Index exceed bounds");
		}
		for (int n = 0; n < nfuncs; n++) {
			if (n != index) {
				IFunction f = functionList.get(n);
				addFunction(plist, flist, fmap, f);
			} else {
				addFunction(plist, flist, fmap, function);
			}
		}
		params = plist;
		functionList = flist;
		functionMap = fmap;
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
		for (IFunction f : functionList)
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

		outputs[0] = makeDataset(values);
		outputs[0].setName("Composite function");

		// now add the data for each bit in turn
		int j = 1;
		for (IFunction f : functionList) {
			outputs[j] = (DoubleDataset) DatasetUtils.cast(f.makeDataset(values), AbstractDataset.FLOAT64);
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

		// now add the data
		outputs[1] = makeDataset(XValues);
		outputs[1].setName("Composite function");

		// now add the errors to the graph, this should provide a good view to
		// how good the fit is quite nicely.
		outputs[2] = (DoubleDataset) Maths.subtract(outputs[1], DataValues);
		outputs[2].setName("Error Value");
		double offset = DataValues.min().doubleValue() - ((DataValues.max().doubleValue() - DataValues.min().doubleValue()) / 5.0);
		outputs[2].isubtract(offset);

		outputs[3] = new DoubleDataset(XValues.getShapeRef());
		outputs[3].setName("Error Offset");
		outputs[3].fill(offset);

		// now add the data for each bit in turn
		int j = 4;
		for (IFunction f : functionList) {
			outputs[j] = (DoubleDataset) DatasetUtils.cast(f.makeDataset(XValues), AbstractDataset.FLOAT64);
			outputs[j++].setName(f.getName());
		}

		return outputs;

	}

	@Override
	public double partialDeriv(int parameter, double... position) throws IndexOutOfBoundsException {
		Integer count = functionMap.floorKey(parameter);
		if (count == null) {
			throw new IndexOutOfBoundsException("There are not enough parameters in the composite function");
		}
		return functionMap.get(count).partialDeriv(parameter - count, position);
	}

	/**
	 * Attempts to cast and return the function at i as a Peak
	 * @param i
	 * @return IPeak
	 * @throws ClassCastException
	 */
	public IPeak getPeak(int i) {
		return (IPeak) getFunction(i);
	}

	@Override
	public CompositeFunction copy() throws Exception {
		CompositeFunction copy = new CompositeFunction();
		for (IFunction function : functionList) {
			copy.addFunction(function.copy());
		}
		return copy;
	}
}
