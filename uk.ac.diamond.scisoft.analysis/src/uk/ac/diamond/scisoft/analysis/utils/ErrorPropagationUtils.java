/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import org.eclipse.january.dataset.BroadcastIterator;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IndexIterator;

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
	
	/**
	 * Finds the sine of a Dataset, propagating uncertainties.
	 * @param a
	 * 			Dataset operand
	 * @return Dataset of the sine of a, with correctly propagated
	 * 			uncertainties.
	 */
	
	public static DoubleDataset sineWithUncertainty(Dataset a) {
		return operateWithUncertainty(a, new Sin());
	}
	
	
	/**
	 * Finds the arcsine of a Dataset, propagating uncertainties.
	 * @param a
	 * 			Dataset operand
	 * @return Dataset of the arcsine of a, with correctly propagated
	 * 			uncertainties.
	 */
	
	public static DoubleDataset arcSineWithUncertainty(Dataset a) {
		return operateWithUncertainty(a, new ArcSin());
	}
	
	private static DoubleDataset operateWithUncertainty(Dataset input, Dataset operand, BinaryUncertaintyOperator operator) {

		if (operand.getSize() != 1 && input.getSize() != operand.getSize()) throw new IllegalArgumentException("Cannot process datasets of these shapes!");
		
		Dataset inputUncert = input.getErrors();
		Dataset operandUncert = operand.getErrors();
		DoubleDataset output;
		DoubleDataset outputUncert;

		Dataset biggest = input.getSize() > operand.getSize() ? input : (input.getSize() < operand.getSize() ? operand :
			input.getRank() > operand.getRank() ? input : operand);

		double[] out = new double[2];

		if (inputUncert != null) {
			if (operandUncert != null) { // If everyone has errors
				BroadcastIterator iter = BroadcastIterator.createIterator(input, operand, null, true);
				BroadcastIterator itererr = BroadcastIterator.createIterator(inputUncert, operandUncert, null, true);
				iter.setOutputDouble(true);
				itererr.setOutputDouble(true);
				output = iter.getOutput().cast(DoubleDataset.class);
				outputUncert = itererr.getOutput().cast(DoubleDataset.class);
				while (iter.hasNext() && itererr.hasNext()) {
					operator.operate(iter.aDouble, iter.bDouble, itererr.aDouble, itererr.bDouble, out);
					output.setAbs(iter.oIndex, out[0]);
					outputUncert.setAbs(itererr.oIndex, out[1]);
				}
			} else { // If only the input has errors
				BroadcastIterator iter = BroadcastIterator.createIterator(input, operand, null, true);
				BroadcastIterator itererr = BroadcastIterator.createIterator(biggest, inputUncert, null, true);
				iter.setOutputDouble(true);
				itererr.setOutputDouble(true);
				output = iter.getOutput().cast(DoubleDataset.class);
				outputUncert = itererr.getOutput().cast(DoubleDataset.class);
				while (iter.hasNext() && itererr.hasNext()) {
					operator.operate(iter.aDouble, iter.bDouble, itererr.bDouble, out);
					output.setAbs(iter.oIndex, out[0]);
					outputUncert.setAbs(iter.oIndex, out[1]);
				}
			}
		} else if (operandUncert != null) { // If only the operand has errors
			BroadcastIterator iter = BroadcastIterator.createIterator(input, operand, null, true);
			BroadcastIterator itererr = BroadcastIterator.createIterator(biggest, operandUncert, null, true);
			iter.setOutputDouble(true);
			itererr.setOutputDouble(true);
			output = iter.getOutput().cast(DoubleDataset.class);
			outputUncert = itererr.getOutput().cast(DoubleDataset.class);
			while (iter.hasNext() && itererr.hasNext()) {
				operator.operate(iter.aDouble, iter.bDouble, 0, itererr.bDouble, out);
				output.setAbs(iter.oIndex, out[0]);
				outputUncert.setAbs(itererr.oIndex, out[1]);
			}
		} else { // If no one has errors
			BroadcastIterator iter = BroadcastIterator.createIterator(input, operand, null, true);
			iter.setOutputDouble(true);
			output = iter.getOutput().cast(DoubleDataset.class);
			outputUncert = null;
			while (iter.hasNext()) {
				out[0] = operator.operate(iter.aDouble, iter.bDouble);
				output.setAbs(iter.oIndex, out[0]);
			}
		}

		output.setErrors(outputUncert);
		return output;
	}
	
	private static DoubleDataset operateWithUncertainty(Dataset input, UnaryUncertaintyOperator operator) {
		
		Dataset inputUncert = input.getErrors();
		DoubleDataset output;
		DoubleDataset outputUncert;
	
		double[] out = new double[2];
	
		if (inputUncert != null) {
			output = DatasetFactory.zeros(input.getShape());
			outputUncert = DatasetFactory.zeros(input.getShape());
			BroadcastIterator iter = BroadcastIterator.createIterator(input, inputUncert, output, true);
			while (iter.hasNext()) {
				operator.operate(iter.aDouble, iter.bDouble, out);
				output.setAbs(iter.oIndex, out[0]);
				outputUncert.setAbs(iter.oIndex, out[1]);
			}
		} else { // If no errors
			IndexIterator iter = input.getIterator();
			output = DatasetFactory.zeros(input.getShape());
			outputUncert = null;
			while (iter.hasNext()) {
				out[0] = operator.operate(input.getElementDoubleAbs(iter.index));
				output.setAbs(iter.index, out[0]);
			}
		}
		output.setErrors(outputUncert);
		return output;
	}
}

interface BinaryUncertaintyOperator {
	void operate(double a, double b, double ae, double be, double[] out);
	
	void operate(double a, double b, double ae, double[] out);
	
	double operate(double a, double b);
}

interface UnaryUncertaintyOperator {
	void operate(double a, double ae, double[] out);
		
	double operate(double a);
}

class Add implements BinaryUncertaintyOperator {

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

class Subtract implements BinaryUncertaintyOperator {

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

class Multiply implements BinaryUncertaintyOperator {

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

class Divide implements BinaryUncertaintyOperator {

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
	
class Sin implements UnaryUncertaintyOperator {

	@Override
	public void operate(double a, double ae, double[] out) {
		SimpleUncertaintyPropagationMath.sin(a, ae, out);
	}

	@Override
	public double operate(double a) {
		return Math.sin(a);
	}
}

class ArcSin implements UnaryUncertaintyOperator {

	@Override
	public void operate(double a, double ae, double[] out) {
		SimpleUncertaintyPropagationMath.arcsin(a, ae, out);
	}

	@Override
	public double operate(double a) {
		return Math.asin(a);
	}
}