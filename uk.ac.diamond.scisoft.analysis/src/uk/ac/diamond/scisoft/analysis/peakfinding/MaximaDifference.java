/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


public class MaximaDifference extends AbstractPeakFinder {
	
	private final static String NAME = "Maxima Difference";
	
	public MaximaDifference() {
		try {
			initialiseParameter("windowSize", 5, true);
			initialiseParameter("nrStdDevs", 3, true);
		} catch (Exception e) {
			logger.error("Failed to initialise parameters for "+this.getName()+"peak finder!");
		}
	}
	
	@Override
	protected void setName() {
		this.name = NAME;
	}
	
	@Override
	public Set<Integer> findPeaks(IDataset xData, IDataset yData, Integer nPeaks) {
		//Put our peak finding parameters into more accessible variables
		Integer nrStdDevs;
		Integer windowSize;
		try {
			nrStdDevs = (Integer)getParameter("nrStdDevs");
			windowSize = (Integer)getParameter("windowSize");
		} catch(Exception e) {
			logger.error("Could not find specified peak finding parameters");
			return null;
		}
		
		//Calculate the significance function for this data & its mean & SD
		int nrPoints = yData.getSize();
		Dataset significance = new DoubleDataset(yData.getShape());
		for (int i = windowSize; i <= (nrPoints-windowSize-1); i++) {
			double posSig = calcPosPeakSig(i, windowSize, yData);
			significance.set(posSig, i);
		}
		
		//Filter out significance values less than n*SD
		Double sigMean = (Double)significance.mean();
		Double sigStdDev = (Double)significance.stdDeviation();
		Set<Integer> peakIndices = new TreeSet<Integer>();
		for (int i = 0; i < significance.getSize(); i++) {
			Double currSig = significance.getDouble(i);
			if ((currSig > 0) && ((currSig-sigMean) > nrStdDevs * sigStdDev)) {
				peakIndices.add(i);
			}
		}
		
		//Remove significant points less than one windowSize apart
		Iterator<Integer> peakIndIter = peakIndices.iterator();
		Set<Integer> peakPosns = new TreeSet<Integer>(peakIndices); 
		int currInd = peakIndIter.next().intValue();
		int nextInd;
		while (peakIndIter.hasNext()) {
			nextInd = peakIndIter.next().intValue();
			if (Math.abs(currInd - nextInd) <= windowSize) {
				peakPosns.remove(currInd);
			}
			currInd = nextInd;
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
