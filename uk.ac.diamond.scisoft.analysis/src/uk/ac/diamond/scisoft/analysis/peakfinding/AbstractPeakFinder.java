/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;

public abstract class AbstractPeakFinder implements IPeakFinder {
	
	protected String name;
	protected Map<String, Double> peakFindParams = new TreeMap<String, Double>();
	
	public AbstractPeakFinder() {
		setName();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Map<String, Double> getParameters() {
		return peakFindParams;
	}

	@Override
	public Double getParameter(String pName) throws Exception {
		if(peakFindParams.containsKey(pName)){
			return peakFindParams.get(pName);
		}
		throw new Exception("Cannot find peak finding parameter "+pName);
	}

	@Override
	public void setParameter(String pName, Double pValue) throws Exception {
		if (peakFindParams.containsKey(pName)) {
			peakFindParams.put(pName, pValue);
		}
		throw new Exception("Cannot find peak finding parameter "+pName);
	}

	@Override
	public Set<Double> findPeaks(IDataset xData, IDataset yData, int nPeaks) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Set the class variable name to the name of the peak finder.
	 */
	protected abstract void setName();
}
