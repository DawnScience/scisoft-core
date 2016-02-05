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
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;

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
	

	public static DoubleDataset addWithUncertainty(Dataset a, double b) {
		return operateWithUncertainty(a, b, new AddOp());
	}
	public static DoubleDataset subtractWithUncertainty(Dataset a, double b) {
		return operateWithUncertainty(a, b, new SubtractOp());
	}
	public static DoubleDataset multiplyWithUncertainty(Dataset a, double b) {
		return operateWithUncertainty(a, b, new MultiplyOp());
	}
	public static DoubleDataset divideWithUncertainty(Dataset a, double b) {
		return operateWithUncertainty(a, b, new DivideOp());
	}
	
	private static DoubleDataset operateWithUncertainty(Dataset input, double oprahend, UncertaintyOp uncertaintyOp) {
		DoubleDataset inputUncert;
		DoubleDataset output, outputUncert;
		
		inputUncert = (input.getError() instanceof DoubleDataset) ? (DoubleDataset) input.getError() : null;
		output = (DoubleDataset) DatasetFactory.zeros(input, Dataset.FLOAT64);
		outputUncert = (inputUncert == null) ? null : (DoubleDataset) DatasetFactory.zeros(inputUncert, Dataset.FLOAT64);
		
		IndexIterator iter = input.getIterator();
		
		if (outputUncert != null) {
			while(iter.hasNext()) {
				output.setAbs(iter.index, uncertaintyOp.erator(input.getElementDoubleAbs(iter.index), oprahend));
				outputUncert.setAbs(iter.index, uncertaintyOp.erator(input.getElementDoubleAbs(iter.index), oprahend));
			}
			output.setError(outputUncert);
		} else {
			while (iter.hasNext())
				output.setAbs(iter.index, uncertaintyOp.erator(input.getElementDoubleAbs(iter.index), oprahend));
		}
		return output;
	}

}

interface UncertaintyOp {
	double erator(double a, double b);
}

class AddOp implements UncertaintyOp {
	@Override
	public double erator(double a, double b) {
		return a+b;
	}
}

class SubtractOp implements UncertaintyOp {
	@Override
	public double erator(double a, double b) {
		return a-b;
	}
}

class MultiplyOp implements UncertaintyOp {
	@Override
	public double erator(double a, double b) {
		return a*b;
	}
}

class DivideOp implements UncertaintyOp {
	@Override
	public double erator(double a, double b) {
		return a/b;
	}
}
