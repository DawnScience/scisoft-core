/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPeakFinder implements IPeakFinder {
	
	/* Add logging facility */
	protected static transient final Logger logger = LoggerFactory.getLogger(AbstractPeakFinder.class);
	
	protected String name;
	private Map<String, Boolean> paramIsInteger = new TreeMap<String, Boolean>();
	protected Map<String, Number> peakFindParams = new TreeMap<String, Number>();
	
	public AbstractPeakFinder() {
		setName();
	}
	
	/**
	 * Set the class variable name to the name of the peak finder.
	 */
	protected abstract void setName();
	
	/**
	 * Initialise a parameter with a name, value and whether it's an integer or
	 * not, by populating the two maps each with one entry.
	 * @param pName name of parameter
	 * @param pValue value to assign to parameter
	 * @param isInt should the value be an integer or not (double if not)
	 */
	protected void initialiseParameter(String pName, Number pValue, boolean isInt) throws Exception {
		//Record whether we're expecting integers of not
		paramIsInteger.put(pName, isInt);
		
		//Check the given value is consistent with the expected type
		if((isInt) && (pValue == (Integer)pValue)) {
			peakFindParams.put(pName, pValue);
		} else if (!isInt) {
			peakFindParams.put(pName, pValue);
		} else {
			throw new Exception("Parameter should be an Integer, found Double.");
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Map<String, Number> getParameters() {
		return peakFindParams;
	}

	@Override
	public Number getParameter(String pName) throws Exception {
		if(peakFindParams.containsKey(pName)){
			return peakFindParams.get(pName);
		}
		throw new Exception("Cannot find peak finding parameter "+pName);
	}

	@Override
	public void setParameter(String pName, Number pValue) throws Exception {
		//Check that the parameter is already initialised, if not, stop!
		if(!peakFindParams.containsKey(pName)){
			throw new Exception("Cannot find peak finding parameter "+pName);
		}
		
		//Check whether given value is consistent with the expected type
		if((paramIsInteger.get(pName)) && (pValue instanceof Integer)) {
			peakFindParams.put(pName, pValue);
		} else if (!paramIsInteger.get(pName)) {
			peakFindParams.put(pName, pValue);
		} else {
			throw new Exception("Parameter should be an Integer, found Double.");
		}
	}
}
