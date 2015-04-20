/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


public class MaximaDifference extends AbstractPeakFinder {
	
	private final static String NAME = "Maxima Difference";
	
	public MaximaDifference() {
		try {
			initialiseParameter("windowSize", 3, true);
			initialiseParameter("minSignificance", 1, true);
		} catch (Exception e) {
			logger.error("Failed to initialise parameters for "+this.getName()+"peak finder!");
		}
	}
	
	@Override
	protected void setName() {
		this.name = NAME;
	}
	
	@Override
	public List<Integer> findPeaks(IDataset xData, IDataset yData, Integer nPeaks) {
		List<Integer> peakPosns = new ArrayList<Integer>();
		
		//Put our peak finding parameters into more accessible variables
		Integer minSignificance;
		Integer windowSize;
		try {
			minSignificance = (Integer)getParameter("minSignificance");
			windowSize = (Integer)getParameter("windowSize");
		} catch(Exception e) {
			logger.error("Could not find specified peak finding parameters");
			return null;
		}
		
		int nrPoints = yData.getSize();
		
		for(int i = windowSize; i <= (nrPoints-windowSize-1); i++) {
			double posSig = calcPosPeakSig(i, windowSize, yData);
			
			if (posSig >= minSignificance) {
				peakPosns.add(i);
			}
		}
		
		return peakPosns;
	}
	
	public double calcPosPeakSig(int position, int windowSize, IDataset yData) {
		double posVal = yData.getDouble(position);
		
		//Calculate the differences between the position & each point across
		//the two windows. N.B. left & right diffs are in opposite directions. 
		IDataset leftDiffs = new DoubleDataset(windowSize);
		IDataset rightDiffs = new DoubleDataset(windowSize);
		for(int i = 0; i < windowSize; i++) {
			leftDiffs.set(posVal-yData.getDouble(position-i-1), i);
			rightDiffs.set(posVal-yData.getDouble(position+i+1), i);
		}
		
		//Calculate the average of the maximum of the differences (i.e. significance)
		double sig = (leftDiffs.max().doubleValue() + rightDiffs.max().doubleValue())/2; 
		
		return sig;
	}
}
