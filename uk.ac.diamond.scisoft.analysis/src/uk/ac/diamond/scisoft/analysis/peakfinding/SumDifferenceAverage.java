/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;


/**
 * Method 2 in: 
 * "Simple Algorithm Peak Detection Time Series, Palshikar (Tata Research)"
 * 
 * Compute the average of the signed differences of between a the point of 
 * interest and it's windowSize left and right neighbours. Take the average of 
 * these two averages.
 */
public class SumDifferenceAverage extends AbstractSignificanceFilter {
	
	private final static String NAME = "Average Difference";
	
	public SumDifferenceAverage() {
		//Change the windowSize default here.
	}
	
	@Override
	protected void setName() {
		this.name = NAME;
	}
	
	@Override
	public double calcPosPeakSig(int position, int windowSize, IDataset yData) {
		double posVal = yData.getDouble(position);
		
		//Calculate the averages of the signed differences across the left & 
		//right windows. N.B. left & right diffs are in opposite directions. 
		Double leftAvg = 0.0;
		Double rightAvg = 0.0;
		for(int i = 0; i < windowSize; i++) {
			leftAvg = leftAvg + (posVal - yData.getDouble(position-i-1));
			rightAvg = rightAvg + (posVal - yData.getDouble(position+i+1));
		}
		leftAvg = leftAvg / windowSize;
		rightAvg = rightAvg / windowSize;
		
		//Calculate the average of the averages of the differences (i.e. significance)
		double sig = (leftAvg + rightAvg)/2; 
		
		return sig;
	}
}