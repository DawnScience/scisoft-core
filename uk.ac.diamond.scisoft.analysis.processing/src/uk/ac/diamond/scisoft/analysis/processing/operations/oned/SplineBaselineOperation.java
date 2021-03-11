/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import java.util.Arrays;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;

/**
 * Subtract a spline baseline from a set of data.
 */
@Atomic
public class SplineBaselineOperation extends AbstractOperation<SplineBaselineModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		Dataset cor = subtractSplineBaseline(DatasetUtils.convertToDataset(input), getControlPoints());
		
		if (!cor.equals(input)) copyMetadata(input, cor);
		
		return new OperationData(cor);
	}

	/**
	 * Gets the points to be fitted to zero. 	
	 * @return a dataset of the points to be zero.
	 */
	private Dataset getControlPoints() {

		double[] xknots = model.getXControlPoints();
		if (xknots == null) return null;
		
		Arrays.sort(xknots);
		
		return DatasetFactory.createFromObject(xknots);
	}


	/**
	 * Performs the subtraction of the spline baseline.
	 * <p>
	 * Given the x position of the knot points, return a copy of the data which
	 * has had a spline subtracted such that the knot points become zero. 
	 * @param input
	 * 			input data
	 * @param knots
	 * 			position of the zeroes in the new data
	 * @return
	 * 		data with the baseline subtracted.
	 */
	private Dataset subtractSplineBaseline(Dataset input, Dataset knots) {
		if (knots == null || knots.getSize() <= 0)// || knots.getShape().length < 2 )
		// Without sufficient data points for a fit, return the original data
			return input;
		
		Dataset xaxis = null;
		// Get the axis, or create one.
		ILazyDataset[] firstAxes = getFirstAxes(input);
		if (firstAxes != null && firstAxes.length != 0 && firstAxes[0] != null) {
			try {
				xaxis = DatasetUtils.sliceAndConvertLazyDataset(AbstractOperation.getFirstAxes(input)[0]);
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		} else {
			xaxis = DatasetFactory.createRange(DoubleDataset.class, input.getSize());
		}

		// y values at the xs
		Dataset ys = Maths.interpolate(xaxis, input, knots, null, null);

		Dataset ybase = null;
		
		switch (knots.getSize()) {
		case 0:
			ybase = DatasetFactory.zeros(xaxis, DoubleDataset.class);
			break;
		case 1:
			// Subtract a constant
			ybase = Maths.multiply(ys.getDouble(0), DatasetFactory.ones(xaxis, DoubleDataset.class));
			break;
		case 2:
			// Subtract a linear fit
			ybase = Interpolation1D.linearInterpolation(knots, ys, xaxis);

			// Extrapolating function for the linear fit
			PolynomialFunctionLagrangeForm linearLagrange = 
					new PolynomialFunctionLagrangeForm(
							new double[]{knots.getDouble(0), knots.getDouble(1)},
							new double[]{ys.getDouble(0), ys.getDouble(1)});

			// Calculate the values at the extrapolated points
			for (int i = 0; i < xaxis.getSize(); i++) 
				if ( (xaxis.getDouble(i) < knots.min().doubleValue()) || (xaxis.getDouble(i) >= knots.max().doubleValue()) )
					ybase.set(linearLagrange.value(xaxis.getDouble(i)), i);
			
			break;
		default:
			// Spline interpolation between the outer most knots
			ybase = Interpolation1D.splineInterpolation(knots, ys, xaxis);
			
			// Extrapolation
			final int degree = 3;
			double[] lowerInterval = new double[degree+1];
			double[] lowerValues = new double[degree+1];
			double[] upperInterval = new double[degree+1];
			double[] upperValues = new double[degree+1];
			
			// Get 4 x and y values for the end intervals. the end points, the
			// next-to-end points and two equispaced points in between.
			for (int i = 0; i <= degree; i++) {
				lowerInterval[i] = ((degree-i)*knots.getDouble(0) + i*knots.getDouble(1))/degree;
				lowerValues[i] = Interpolation1D.splineInterpolation(knots, ys, DatasetFactory.createFromObject(Arrays.copyOfRange(lowerInterval, i, i+1))).getDouble(0);
				upperInterval[i] = ((degree-i)*knots.getDouble(knots.getSize()-2) + i*knots.getDouble(knots.getSize()-1))/degree;
				upperValues[i] = Interpolation1D.splineInterpolation(knots, ys, DatasetFactory.createFromObject(Arrays.copyOfRange(upperInterval, i, i+1))).getDouble(0);
			}
			
			// Lower (smaller value) and upper (larger value) extrapolating
			// functions. 4 points define a cubic, to match the cubic spline
			// used in the interpolation
			PolynomialFunctionLagrangeForm lowerLagrange = new PolynomialFunctionLagrangeForm(lowerInterval, lowerValues);
			PolynomialFunctionLagrangeForm upperLagrange = new PolynomialFunctionLagrangeForm(upperInterval, upperValues);

			// Loop through all points. If the x value falls outside the knots,
			// replace the y-value of the baseline with the interpolated value.
			for (int i = 0; i < xaxis.getSize(); i++) 
				if (xaxis.getDouble(i) < knots.min().doubleValue())
					ybase.set(lowerLagrange.value(xaxis.getDouble(i)), i);
				else if (xaxis.getDouble(i) >= knots.max().doubleValue())
					ybase.set(upperLagrange.value(xaxis.getDouble(i)), i);
			
			break;
		}
		// Finally, perform the subtraction.
		return Maths.subtract(input, ybase);
	}





	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SplineBaselineOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}
