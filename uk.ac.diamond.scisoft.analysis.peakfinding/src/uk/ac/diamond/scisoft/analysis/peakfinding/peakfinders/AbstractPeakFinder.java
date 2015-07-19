/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinder;
import org.eclipse.dawnsci.analysis.api.peakfinding.IPeakFinderParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPeakFinder implements IPeakFinder {
	
	/* Add logging facility */
	protected static transient final Logger logger = LoggerFactory.getLogger(AbstractPeakFinder.class);
	
	protected String name;
	protected Set<IPeakFinderParameter> peakFindParams = new HashSet<IPeakFinderParameter>();
	
	public AbstractPeakFinder() {
		setName();
	}
	
	/**
	 * Set the class variable name to the name of the peak finder.
	 */
	protected abstract void setName();
	
	/**
	 * Initialise a parameter with a name, value and whether it's an integer or
	 * not, by adding it to the parameters set
	 * @param pName name of parameter
	 * @param pValue value to assign to parameter
	 * @param isInt should the value be an integer or not (double if not)
	 */
	protected void initialiseParameter(String pName, boolean isInt, Number pValue) throws Exception {
		peakFindParams.add(new PeakFinderParameter(pName, isInt, pValue));
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Set<IPeakFinderParameter> getParameters() {
		return peakFindParams;
	}

	@Override
	public IPeakFinderParameter getParameter(String pName) throws Exception {
		Iterator<IPeakFinderParameter> paramIter = peakFindParams.iterator();
		while (paramIter.hasNext()) {
			IPeakFinderParameter currParam = paramIter.next();
			if (currParam.getName().equals(pName)) return currParam;
		}
		throw new Exception("Cannot find peak finding parameter "+pName);
	}

	@Override
	public Number getParameterValue(String pName) throws Exception {
		IPeakFinderParameter currParam = getParameter(pName);
		return currParam.getValue();
	}

	@Override
	public void setParameter(String pName, Number pValue) throws Exception {
		IPeakFinderParameter currParam = getParameter(pName);
		currParam.setValue(pValue);
	}
}
