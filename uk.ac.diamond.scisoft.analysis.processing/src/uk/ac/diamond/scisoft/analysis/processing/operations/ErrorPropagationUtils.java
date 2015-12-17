/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

public class ErrorPropagationUtils {

	public static DoubleDataset[] multiplyWithError(Dataset input, Dataset error, double scale) {
		
		double tmp = 0;
		DoubleDataset out = (DoubleDataset)DatasetFactory.zeros(input.getShape(), Dataset.FLOAT64);
		DoubleDataset oute = input.getError() == null ? null : (DoubleDataset)DatasetFactory.zeros(input.getShape(), Dataset.FLOAT64);
		double absScale = Math.abs(scale);
		
		for (int i = 0; i< input.getSize(); i++) {
			tmp = input.getElementDoubleAbs(i);
			out.setAbs(i, tmp*scale);
			
			if (oute != null) oute.setAbs(i, error.getElementDoubleAbs(i)*absScale);
			
		}
		
		return new DoubleDataset[]{out,oute};
	}
	
	
	
}
