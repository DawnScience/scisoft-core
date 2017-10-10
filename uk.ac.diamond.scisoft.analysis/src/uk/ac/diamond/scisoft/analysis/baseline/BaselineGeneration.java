/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.baseline;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IndexIterator;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class BaselineGeneration {

	public static Dataset rollingBallBaseline(Dataset y, int width) {

		Dataset t1 = DatasetFactory.zeros(y);
		Dataset t2 = DatasetFactory.zeros(y);

		for (int i = 0 ; i < y.getSize(); i++) {
			int start = (i-width) < 0 ? 0 : (i - width);
			int end = (i+width) > (y.getSize()) ? (y.getSize()) : (i+width);
			double val = y.getSlice(new int[]{start}, new int[]{end}, null).min().doubleValue();
			t1.set(val, i);
		}

		for (int i = 0 ; i < y.getSize(); i++) {
			int start = (i-width) < 0 ? 0 : (i - width);
			int end = (i+width) > (y.getSize()) ? (y.getSize()) : (i+width);
			double val = t1.getSlice(new int[]{start}, new int[]{end}, null).max().doubleValue();
			t2.set(val, i);
		}

		for (int i = 0 ; i < y.getSize(); i++) {
			int start = (i-width) < 0 ? 0 : (i - width);
			int end = (i+width) > (y.getSize()) ? (y.getSize()) : (i+width);
			double val = (Double)t2.getSlice(new int[]{start}, new int[]{end}, null).mean();
			t1.set(val, i);
		}

		return t1;
	}
	
	public static Dataset iterativePolynomialBaseline(Dataset input, Dataset axis, int polyOrder, int nIterations) {
		if (nIterations < 1) throw new IllegalArgumentException("nIterations must be 1 or greater");
		
		DoubleDataset data = (DoubleDataset)DatasetUtils.cast(input, Dataset.FLOAT64).clone();
		Dataset[] aa = new Dataset[] {axis};
		
		Polynomial polyFit = Fitter.polyFit(aa, data, 1e-15, polyOrder);
		for (int i = 0; i < nIterations; i++) {
				
				DoubleDataset v = polyFit.calculateValues(aa);
				
				IndexIterator it = v.getIterator();
				while (it.hasNext()) {
					double val = data.getAbs(it.index);
					double base = v.getAbs(it.index);
					if (val > base) data.setAbs(it.index, base);
				}
				
				polyFit = Fitter.polyFit(aa, data, 1e-15, polyOrder);
			} 
				
		return polyFit.calculateValues(aa);
	}
	
}
