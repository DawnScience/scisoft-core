/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Set;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;


public class MaximaDifference extends AbstractPeakFinder {
	
	private final static String NAME = "Maxima Difference";
	
	public MaximaDifference() {
		peakFindParams.put("windowSize", 3.);
		peakFindParams.put("minSignificance", 1.);
	}
	
	@Override
	protected void setName() {
		this.name = NAME;
		
	}
	
	@Override
	public Set<Double> findPeaks(IDataset xData, IDataset yData, int nPeaks) {
		// TODO Auto-generated method stub
		return null;
	}

}
