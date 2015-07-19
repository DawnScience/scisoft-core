/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;


/**
 * Method 3 in: 
 * "Simple Algorithm Peak Detection Time Series, Palshikar (Tata Research)"
 * 
 * Compute the average of the windowSize left and right neighbours of the point
 * of interest. Calculate the difference of this value and the point and then
 * take the average of these values.
 */
public class AverageWindowAverageDiffs extends AbstractSignificanceFilter {
	
	private final static String NAME = "Average of Window-Average Differences";
	
	public AverageWindowAverageDiffs() {
		super();
		//Change the windowSize default here.
	}
	
	@Override
	protected void setName() {
		this.name = NAME;
	}
	
	@Override
	public double calculateSignificance(int position, int windowSize, IDataset yData) {
		double posVal = yData.getDouble(position);
		
		//Calculate the averages of the the left & right windows. 
		//N.B. left & right diffs are in opposite directions. 
		Double leftAvg = 0.0;
		Double rightAvg = 0.0;
		for(int i = 0; i < windowSize; i++) {
			leftAvg = leftAvg + yData.getDouble(position-i-1);
			rightAvg = rightAvg + yData.getDouble(position+i+1);
		}
		leftAvg = leftAvg / windowSize;
		rightAvg = rightAvg / windowSize;
		
		//Calculate the average of difference of the point and the averages (i.e. significance)
		double sig = ((posVal - leftAvg) + (posVal - rightAvg))/2; 
		
		return sig;
	}
}