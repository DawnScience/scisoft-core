/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IndexIterator;

import uk.ac.diamond.scisoft.analysis.utils.SimpleUncertaintyPropagationMath;

/**
 * A class providing error propagating static methods
 */
public class ErrorPropagationUtils {
	
	/**
	 * Adds a scalar to a Dataset, propagating uncertainties.
	 * <p>
	 * If the Dataset operand has uncertainty (error) metadata, then the errors
	 * are correctly propagated to the uncertainty metadata of the resultant
	 * Dataset.
	 * @param a
	 * 			Dataset operand
	 * @param b
	 * 			scalar operand
	 * @return Dataset of the sum of a and b, with correctly propagated
	 * 			uncertainties.
	 */
	
	public static DoubleDataset addWithUncertainty(Dataset a, Dataset b) {
		return operateWithUncertainty(a, b, new Add());
	}
	
	/**
	 * Subtracts a scalar from a Dataset, propagating uncertainties.
	 * <p>
	 * If the Dataset operand has uncertainty (error) metadata, then the errors
	 * are correctly propagated to the uncertainty metadata of the resultant
	 * Dataset.
	 * @param a
	 * 			Dataset operand
	 * @param b
	 * 			scalar operand
	 * @return Dataset of the difference of a and b, with correctly propagated
	 * 			uncertainties.
	 */
	
	public static DoubleDataset subtractWithUncertainty(Dataset a, Dataset b) {
		return operateWithUncertainty(a, b, new Subtract());
	}
	
	/**
	 * Multiplies a Dataset by a scalar, propagating uncertainties.
	 * <p>
	 * If the Dataset operand has uncertainty (error) metadata, then the errors
	 * are correctly propagated to the uncertainty metadata of the resultant
	 * Dataset.
	 * @param a
	 * 			Dataset operand
	 * @param b
	 * 			scalar operand
	 * @return Dataset of the product of a and b, with correctly propagated
	 * 			uncertainties.
	 */
	
	public static DoubleDataset multiplyWithUncertainty(Dataset a, Dataset b) {
		return operateWithUncertainty(a, b, new Multiply());
	}
	
	/**
	 * Divides a Dataset by a scalar, propagating uncertainties.
	 * <p>
	 * If the Dataset operand has uncertainty (error) metadata, then the errors
	 * are correctly propagated to the uncertainty metadata of the resultant
	 * Dataset.
	 * @param a
	 * 			Dataset operand
	 * @param b
	 * 			scalar or dataset operand
	 * @return Dataset of the ratio of a and b, with correctly propagated
	 * 			uncertainties.
	 */
	
	public static DoubleDataset divideWithUncertainty(Dataset a, Dataset b) {
		return operateWithUncertainty(a, b, new Divide());
	}
	
	private static DoubleDataset operateWithUncertainty(Dataset input, Dataset oprahend, UncertaintyOperator operator) {

		if (oprahend.getSize() != 1 && input.getSize() != oprahend.getSize()) throw new IllegalArgumentException("Cannot process datasets of these shapes!");
		
		Dataset inputUncert = input.getErrors();
		Dataset oprahendUncert = oprahend.getErrors();
		DoubleDataset output = (DoubleDataset) DatasetFactory.zeros(input.getShapeRef());
		DoubleDataset outputUncert = (inputUncert == null) ? null : DatasetFactory.zeros(inputUncert.getShapeRef());
		//assume data and errors are either not views or common views
		IndexIterator iter = input.getIterator();
		
		if (oprahend.getSize() == 1) {
			double val = oprahend.getDouble();
			if (inputUncert != null) {
				double[] out = new double[2];
				while(iter.hasNext()) {
					operator.operate(input.getElementDoubleAbs(iter.index), val, inputUncert.getElementDoubleAbs(iter.index), out);
					output.setAbs(iter.index, out[0]);
					outputUncert.setAbs(iter.index, out[1]);
				}
				output.setErrors(outputUncert);
			} else {
				while (iter.hasNext())
					output.setAbs(iter.index, operator.operate(input.getElementDoubleAbs(iter.index), val));
			}
			
			return output;
		}
		
		if (inputUncert != null && oprahendUncert == null) {
			double[] out = new double[2];
			while(iter.hasNext()) {
				operator.operate(input.getElementDoubleAbs(iter.index), oprahend.getElementDoubleAbs(iter.index), inputUncert.getElementDoubleAbs(iter.index), out);
				output.setAbs(iter.index, out[0]);
				outputUncert.setAbs(iter.index, out[1]);
			}
			output.setErrors(outputUncert);
		} else if  (inputUncert != null && oprahendUncert != null) {
			double[] out = new double[2];
			while(iter.hasNext()) {
				operator.operate(input.getElementDoubleAbs(iter.index), oprahend.getElementDoubleAbs(iter.index), inputUncert.getElementDoubleAbs(iter.index),oprahendUncert.getElementDoubleAbs(iter.index), out);
				output.setAbs(iter.index, out[0]);
				outputUncert.setAbs(iter.index, out[1]);
			}
			output.setErrors(outputUncert);
		} else {
			while (iter.hasNext()){
				output.setAbs(iter.index, operator.operate(input.getElementDoubleAbs(iter.index), oprahend.getElementDoubleAbs(iter.index)));
			}
		}
		return output;
	}

}

interface UncertaintyOperator {
	void operate(double a, double b, double ae, double be, double[] out);
	
	void operate(double a, double b, double ae, double[] out);
	
	double operate(double a, double b);
}

class Add implements UncertaintyOperator {

	@Override
	public void operate(double a, double b, double ae, double be, double[] out) {
		SimpleUncertaintyPropagationMath.add(a, b, ae, be, out);
	}

	@Override
	public void operate(double a, double b, double ae, double[] out) {
		out[0] = a+b;
		out[1] = ae;
 	}

	@Override
	public double operate(double a, double b) {
		return a+b;
	}


}

class Subtract implements UncertaintyOperator {

	@Override
	public void operate(double a, double b, double ae, double be, double[] out) {
		SimpleUncertaintyPropagationMath.subtract(a, b, ae, be, out);
		
	}

	@Override
	public void operate(double a, double b, double ae, double[] out) {
		out[0] = a-b;
		out[1] = ae;
	}

	@Override
	public double operate(double a, double b) {
		return a-b;
	}

}

class Multiply implements UncertaintyOperator {

	@Override
	public void operate(double a, double b, double ae, double be, double[] out) {
		SimpleUncertaintyPropagationMath.multiply(a, b, ae, be, out);
		
	}

	@Override
	public void operate(double a, double b, double ae, double[] out) {
		SimpleUncertaintyPropagationMath.multiply(a, b, ae, out);
		
	}

	@Override
	public double operate(double a, double b) {
		return a*b;
	}

}

class Divide implements UncertaintyOperator {

	@Override
	public void operate(double a, double b, double ae, double be, double[] out) {
		SimpleUncertaintyPropagationMath.divide(a, b, ae, be, out);
		
	}

	@Override
	public void operate(double a, double b, double ae, double[] out) {
		SimpleUncertaintyPropagationMath.divide(a, b, ae, out);
		
	}

	@Override
	public double operate(double a, double b) {
		return a/b;
	}

}
