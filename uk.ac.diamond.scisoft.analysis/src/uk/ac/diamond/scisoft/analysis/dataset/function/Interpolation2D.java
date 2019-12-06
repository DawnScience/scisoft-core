/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.Arrays;

import org.apache.commons.math3.analysis.BivariateFunction;
import org.apache.commons.math3.analysis.interpolation.BicubicInterpolator;
import org.apache.commons.math3.analysis.interpolation.BivariateGridInterpolator;
import org.apache.commons.math3.analysis.interpolation.PiecewiseBicubicSplineInterpolator;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;

/** Two-dimensional interpolations of IDatasets.
 *  
 *  Essentially a wrapper around Apache Commons' {@link org.apache.commons.math3.analysis.interpolation.BivariateGridInterpolator} and related methods, with an interface very similar to the one found
 *  in {@link Interpolation1D}
 *  
 *  @author Tom.Schoonjans@diamond.ac.uk
 */
public class Interpolation2D {

	/** Determines whether the interpolation will be performed for newx and newy as pairs (in which case they need to have matching dimensions),
	 *  which will result in a rank 1 dataset, OR whether the newx and newy datasets will be interpreted as forming an X-Y grid that will be used
	 *  as input for the interpolations, resulting in a rank 2 dataset with shape {newx.getSize(), newy.getSize()}.
	 *
	 */
	public enum BicubicInterpolationOutput {
		ONED,
		TWOD
	}
	
	/** Perform a two-dimensional bicubic interpolation
	 * 
	 * @param oldx an IDataset containing a 1D array of X-values, sorted in increasing order, corresponding to the first dimension of <code>oldxy</code>
	 * @param oldy an IDataset containing a 1D array of Y-values, sorted in increasing order, corresponding to the second dimension of <code>oldxy</code>
	 * @param oldxy an IDataset containing a 2D grid of interpolation points
	 * @param newx an IDataset containing a 1D array of X-values that will be sent to the interpolating function
	 * @param newy an IDataset containing a 1D array of Y-values that will be sent to the interpolating function
	 * @param output_type an {@link BicubicInterpolationOutput} that will determine how <code>newx</code> and <code>newy</code> will be interpreted, and therefore whether a 1D or 2D Dataset will be returned.
	 * @return rank 1 or 2 Dataset, depending on <code>output_type}</code>
	 * @throws NonMonotonicSequenceException
	 * @throws NumberIsTooSmallException
	 */
	public static Dataset bicubicInterpolation(IDataset oldx, IDataset oldy, IDataset oldxy, IDataset newx, IDataset newy, BicubicInterpolationOutput output_type) throws NonMonotonicSequenceException, NumberIsTooSmallException {
		
		return interpolate(oldx, oldy, oldxy, newx, newy, new BicubicInterpolator(), output_type);
		
	}
	
	/** Perform a two-dimensional piecewise-bicubic spline interpolation
	 * 
	 * @param oldx an IDataset containing a 1D array of X-values, sorted in increasing order, corresponding to the first dimension of <code>oldxy</code>
	 * @param oldy an IDataset containing a 1D array of Y-values, sorted in increasing order, corresponding to the second dimension of <code>oldxy</code>
	 * @param oldxy an IDataset containing a 2D grid of interpolation points
	 * @param newx an IDataset containing a 1D array of X-values that will be sent to the interpolating function
	 * @param newy an IDataset containing a 1D array of Y-values that will be sent to the interpolating function
	 * @param output_type an {@link BicubicInterpolationOutput} that will determine how <code>newx</code> and <code>newy</code> will be interpreted, and therefore whether a 1D or 2D Dataset will be returned.
	 * @return rank 1 or 2 Dataset, depending on <code>output_type}</code>
	 * @throws NonMonotonicSequenceException
	 * @throws NumberIsTooSmallException
	 */
	public static Dataset piecewiseBicubicSplineInterpolation(IDataset oldx, IDataset oldy, IDataset oldxy, IDataset newx, IDataset newy, BicubicInterpolationOutput output_type) throws NonMonotonicSequenceException, NumberIsTooSmallException {
		
		return interpolate(oldx,oldy, oldxy, newx, newy, new PiecewiseBicubicSplineInterpolator(), output_type);
		
	}
	
	/** Perform a two-dimensional interpolation
	 * 
	 * @param oldx an IDataset containing a 1D array of X-values, sorted in increasing order, corresponding to the first dimension of <code>oldxy</code>
	 * @param oldy an IDataset containing a 1D array of Y-values, sorted in increasing order, corresponding to the second dimension of <code>oldxy</code>
	 * @param oldxy an IDataset containing a 2D grid of interpolation points
	 * @param newx an IDataset containing a 1D array of X-values that will be sent to the interpolating function
	 * @param newy an IDataset containing a 1D array of Y-values that will be sent to the interpolating function
	 * @param interpolator an instance of {@link org.apache.commons.math3.analysis.interpolation.BivariateGridInterpolator}
	 * @param output_type an {@link BicubicInterpolationOutput} that will determine how <code>newx</code> and <code>newy</code> will be interpreted, and therefore whether a 1D or 2D Dataset will be returned.
	 * @return rank 1 or 2 Dataset, depending on <code>output_type}</code>
	 * @throws NonMonotonicSequenceException
	 * @throws NumberIsTooSmallException
	 */
	public static Dataset interpolate(IDataset oldx, IDataset oldy, IDataset oldxy, IDataset newx, IDataset newy, BivariateGridInterpolator interpolator, BicubicInterpolationOutput output_type) throws NonMonotonicSequenceException, NumberIsTooSmallException {
		
		//check shapes
		if (oldx.getRank() != 1)
			throw new IllegalArgumentException("oldx Shape must be 1D");
		if (oldy.getRank() != 1)
			throw new IllegalArgumentException("oldy Shape must be 1D");
		if (oldxy.getRank() != 2)
			throw new IllegalArgumentException("oldxy Shape must be 2D");
		if (oldx.getShape()[0] != oldxy.getShape()[0])
			throw new IllegalArgumentException("oldx Shape must match oldxy Shape[0]");
		if (oldy.getShape()[0] != oldxy.getShape()[1])
			throw new IllegalArgumentException("oldy Shape must match oldxy Shape[1]");
		if (newx.getRank() != 1)
			throw new IllegalArgumentException("newx Shape must be 1D");
		if (newy.getRank() != 1)
			throw new IllegalArgumentException("newy Shape must be 1D");
		if (output_type == BicubicInterpolationOutput.ONED && newy.getSize() != newx.getSize())
			throw new IllegalArgumentException("newx and newy Size must be identical when expecting a rank 1 dataset result");
		
		DoubleDataset oldx_dd = DatasetUtils.cast(DoubleDataset.class, oldx);
		DoubleDataset oldy_dd = DatasetUtils.cast(DoubleDataset.class, oldy);
		DoubleDataset oldxy_dd = DatasetUtils.cast(DoubleDataset.class, oldxy);

		//unlike in Interpolation1D, we will not be sorting here, as it just too complicated
		//the user will be responsible for ensuring the arrays are properly sorted
		
		//oldxy_dd needs to be transformed into a double[][] array
		//this call may throw an exception that needs handling by the calling method
		BivariateFunction func = interpolator.interpolate(oldx_dd.getData(), oldy_dd.getData(), convertDoubleDataset2DtoPrimitive(oldxy_dd));
		
		Dataset rv = null;

		switch (output_type) {
		case ONED:
			rv = DatasetFactory.zeros(newx.getSize());

			for (int i = 0 ; i < newx.getSize() ; i++) {
				double val = 0.0;
				try {
					val = func.value(newx.getDouble(i), newy.getDouble(i));
					rv.set(val, i);
				} catch (OutOfRangeException e) {
					rv.set(0.0, i);
				}
			}
			break;
		case TWOD:
		default:
			rv = DatasetFactory.zeros(newx.getSize(), newy.getSize());

			for (int i = 0 ; i < newx.getSize() ; i++) {
				for (int j = 0 ; j < newy.getSize() ; j++) {
					double val = 0.0;
					try {
						val = func.value(newx.getDouble(i), newy.getDouble(j));
						rv.set(val, i, j);
					} catch (OutOfRangeException e) {
						rv.set(0.0, i, j);
					}
				}
			}
			break;
		}
		rv.setName(oldxy.getName()+"_interpolated");
		
		return rv;
	}
	
	/** Perform a two-dimensional interpolation, where the new coordinates take the form x(x', y') & y(x',y')
	 * 
	 * @param oldx an IDataset containing a 1D array of X-values, sorted in increasing order, corresponding to the first dimension of <code>oldxy</code>
	 * @param oldy an IDataset containing a 1D array of Y-values, sorted in increasing order, corresponding to the second dimension of <code>oldxy</code>
	 * @param oldxy an IDataset containing a 2D grid of interpolation points
	 * @param newx an IDataset containing a 2D array of X-values that will be sent to the interpolating function
	 * @param newy an IDataset containing a 2D array of Y-values that will be sent to the interpolating function
	 * @param interpolator an instance of {@link org.apache.commons.math3.analysis.interpolation.BivariateGridInterpolator}
	 * @return rank 1 or 2 Dataset, depending on <code>output_type}</code>
	 * @throws NonMonotonicSequenceException
	 * @throws NumberIsTooSmallException
	 */
	public static Dataset interpolate2d(IDataset oldx, IDataset oldy, IDataset oldxy, IDataset newx, IDataset newy, BivariateGridInterpolator interpolator) throws NonMonotonicSequenceException, NumberIsTooSmallException {

		//check shapes
		if (oldx.getRank() != 1)
			throw new IllegalArgumentException("oldx Shape must be 1D");
		if (oldy.getRank() != 1)
			throw new IllegalArgumentException("oldy Shape must be 1D");
		if (oldxy.getRank() != 2)
			throw new IllegalArgumentException("oldxy Shape must be 2D");
		if (oldx.getShape()[0] != oldxy.getShape()[0])
			throw new IllegalArgumentException("oldx Shape must match oldxy Shape[0]");
		if (oldy.getShape()[0] != oldxy.getShape()[1])
			throw new IllegalArgumentException("oldy Shape must match oldxy Shape[1]");
		if (newx.getRank() != 2)
			throw new IllegalArgumentException("newx Shape must be 2D");
		if (newy.getRank() != 2)
			throw new IllegalArgumentException("newy Shape must be 2D");
		if (!Arrays.equals(newy.getShape(), newx.getShape()))
			throw new IllegalArgumentException("newx and newy shapes must be identical");
		
		// Convert the input Datasets to DoubleDatasets
		DoubleDataset oldx_dd = DatasetUtils.cast(DoubleDataset.class, oldx);
		DoubleDataset oldy_dd = DatasetUtils.cast(DoubleDataset.class, oldy);
		DoubleDataset oldxy_dd = DatasetUtils.cast(DoubleDataset.class, oldxy);

		DoubleDataset newx_dd = DatasetUtils.cast(DoubleDataset.class, newx);
		DoubleDataset newy_dd = DatasetUtils.cast(DoubleDataset.class, newy);
		
		BivariateFunction func = interpolator.interpolate(oldx_dd.getData(), oldy_dd.getData(), convertDoubleDataset2DtoPrimitive(oldxy_dd));
		
		// Allocate the return value Dataset
		Dataset rv = DatasetFactory.zeros(newx_dd);
		
		IndexIterator iter = newx_dd.getIterator();
		
		while(iter.hasNext()) {
			double val = 0.0;
			try {
				val = func.value(newx_dd.getElementDoubleAbs(iter.index), newy_dd.getElementDoubleAbs(iter.index));
			} catch (OutOfRangeException e) {
				val = Double.NaN;
			}
			rv.setObjectAbs(iter.index, val);
		}
		
		rv.setName(oldxy.getName()+"_interpolated");
		
		return rv;
	}
	
	private static double[][] convertDoubleDataset2DtoPrimitive(DoubleDataset dataset) {

		int[] shape = dataset.getShapeRef();
		if (dataset.getRank() != 2)
			throw new IllegalArgumentException("dataset Shape must be 2D");
		
		double[][] rv = new double[shape[0]][shape[1]];
		
		for (int row = 0 ; row < dataset.getShape()[0] ; row++) {
			System.arraycopy(dataset.getData(), row * shape[1], rv[row], 0, shape[1]);
		}
		
		return rv;
	}
}
