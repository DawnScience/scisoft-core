/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.peakfinding.peakfinders;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

public class SequenceEntropy extends AbstractSignificanceFilter {
	
	private final static String NAME = "Entropy of a Sequence";

	@Override
	protected void setName() {
		this.name = NAME;

	}
	@Override
	protected double calculateSignificance(int position, int windowSize, IDataset yData) {
		
		IDataset localSeq = yData.getSlice(new int[position - windowSize], new int[position + windowSize], new int[1]);
		DoubleDataset localDoubleSeq = (DoubleDataset)DatasetUtils.cast(localSeq, Dataset.FLOAT64);
		
		int bandwidth = 5;
		double[] fullSequence = localDoubleSeq.getData();
		double[] subSequence = ArrayUtils.remove(fullSequence, windowSize);
		
		
		double windowEntropy = calculateEntropy(fullSequence, bandwidth);
		double entropyLessPoint = calculateEntropy(subSequence, bandwidth);
		
		double sig = windowEntropy - entropyLessPoint;
		
		return sig;
	}
	
	/**
	 * Calculate the entropy of a given sequence using the Parzen window technique
	 * to estimate the probability density at a given point. The bandwidth term is
	 * expressed in the Palshikar paper as:
	 * 		|a_{i} - a_{i+w}|		where a is a member of the sequence and w is a positive integer
	 * Other references seem to suggest this can be any number
	 * @param sequence
	 * @param bandwidth
	 * @return entropy of sequence
	 */
	private double calculateEntropy(double[] sequence, int bandwidth) {
		double entropy = 0.0;
		
		int i, j;
		for (i = 0; i <= sequence.length; i++) {
			double kernelSum = 0.0;
			for(j = 0; j <= sequence.length; j++) {
				//Calculate the Kernel Distribution Estimate across the whole sequence wrt to this point
				double kernelParam = (sequence[i]-sequence[j])/bandwidth;
				double kernelVal = (1/Math.sqrt(2 * Math.PI)) * Math.exp(-(Math.pow(kernelParam, 2)/2));
				kernelSum = kernelSum + kernelVal;
			}
			//From the KDE, calculate the Probability Density using the Parzen 
			//window for the ith point inthe sequence and find the entropy.
			double parzen = 1 / (sequence.length * bandwidth) * kernelSum;
			entropy = entropy + (-parzen * Math.log(parzen));
		}
		
		return entropy;
	}
}
