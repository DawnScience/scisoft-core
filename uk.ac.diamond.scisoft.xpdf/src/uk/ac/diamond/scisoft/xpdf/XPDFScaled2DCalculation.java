/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.january.dataset.Dataset;

/**
 * A class that performs a given function at a reduced resolution.
 * <p>
 * Each subclass has a defined calculate method, which takes two
 * {@link Dataset}s and returns another as a result. The calculation is
 * performed at a reduced resolution compared, and the result scaled back up to
 * the original resolution. 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 *
 */
abstract public class XPDFScaled2DCalculation {

	int maxGridSize;
	
	/**
	 * Constructor specifying a maximum grid size.
	 * @param maxGridSize
	 * 					reduce the resolution of the calculation if the inputs
	 * 					are larger than this size in total size
	 */
	public XPDFScaled2DCalculation(int maxGridSize) {
		this.maxGridSize = maxGridSize;
	}
	
	/**
	 * Performs a calculation taking two {@link Dataset}s and returning a third.
	 * @param gamma
	 * 			the first two dimensional parameter.
	 * @param delta
	 * 			the second two dimensional parameter.
	 * @return the result of the specified function.
	 */
	protected Dataset calculate(Dataset gamma, Dataset delta) {
		return null;
	}
	
	/**
	 * Provides the grid resizing for the scaled calculation.
	 * @param gamma
	 * 			the first two dimensional parameter of the {@link calculate()} method.
	 * @param delta
	 * 			the second two dimensional parameter of the {@link calculate()} method.
	 * @return
	 * 		the full resolution result of the calculation.
	 */
	final public Dataset run(Dataset gamma, Dataset delta) {
		// Grid size for the high resolution data
		int nXHigh = delta.getShape()[0];
		int nYHigh = delta.getShape()[1];

		int[] nXYLow = new int[2];
		
		restrictGridSize(maxGridSize, nXHigh, nYHigh, nXYLow);
		
		int nXLow = nXYLow[0];
		int nYLow = nXYLow[1];

		// Down sampling of the angular coordinates for faster calculations
		Dataset gammaDown = XPDFRegrid.two(gamma, nXLow, nYLow);
		Dataset deltaDown = XPDFRegrid.two(delta, nXLow, nYLow);
		
		Dataset resultLow = calculate(gammaDown, deltaDown);
		
		// Up-sample the absorption back to the original resolution and return
		return XPDFRegrid.two(resultLow, nXHigh, nYHigh);
	}

	/**
	 * Performs a calculation taking a {@link Dataset} and returning a second.
	 * @param twoTheta
	 * 			the two dimensional parameter.
	 * @return the result of the specified function.
	 */
	protected Dataset calculateTwoTheta(Dataset twoTheta) {
		return null;
	}
	
	/**
	 * Provides the grid resizing for the scaled calculation.
	 * @param twoTheta
	 * 			the two dimensional parameter of the {@link calculateTwoTheta()} method.
	 * @return
	 * 		the full resolution result of the calculation.
	 */
	final public Dataset runTwoTheta(Dataset twoTheta) {
		// Grid shape for the high resolution data
		int nXHigh = twoTheta.getShape()[0];
		int nYHigh = twoTheta.getShape()[1];
		
		int[] nXYLow = new int[2];
		
		restrictGridSize(maxGridSize, nXHigh, nYHigh, nXYLow);
		
		int nXLow = nXYLow[0];
		int nYLow = nXYLow[1];
		
		// Down-sampling of the angular coordinate for faster calculations
		Dataset twoThetaLow = XPDFRegrid.two(twoTheta, nXLow, nYLow);
		
		Dataset resultLow = calculateTwoTheta(twoThetaLow);
		
		// Up-sample the result back to the original resolution and return
		return XPDFRegrid.two(resultLow, nXHigh, nYHigh);
		
	}
	
	/**
	 * Returns smaller grid axes, based on the lengths of the originals and the maximum grid size.
	 * @param maxGrid
	 * 				maximum number of grid points to use.
	 * @param nXHigh
	 * 				original length of the x axis (dimension 0)
	 * @param nYHigh
	 * 				original length of the y axis (dimension 1)
	 * @param nXYLow
	 * 				return the values of the axis lengths using a 2 element int
	 * 				array, since Java has no pass by reference
	 */
	private void restrictGridSize(int maxGrid, int nXHigh, int nYHigh, int[] nXYLow) {
		int nXLow, nYLow;
		// Grid size for the low resolution calculations
		if (nXHigh*nYHigh < maxGrid) {
			nXLow = nXHigh;
			nYLow = nYHigh;
		} else {
			// Sort the axes
			int smallerDim, largerDim;
			boolean isXSmaller = nXHigh < nYHigh;
			if (isXSmaller) {
				smallerDim = nXHigh;
				largerDim = nYHigh;
			} else {
				smallerDim = nYHigh;
				largerDim = nXHigh;
			}

			// Deal with one axis being rather short
			if (smallerDim <= 2) {
				largerDim = (largerDim*smallerDim > maxGrid) ? maxGrid/smallerDim : largerDim;
			} else {
				double scale = maxGrid/(1.0*smallerDim*largerDim);
				smallerDim = (int) Math.ceil(Math.sqrt(scale) * smallerDim);
				smallerDim = (smallerDim < 2) ? 2 : smallerDim;
				largerDim = maxGrid/smallerDim;
			}
			
			// Unsort the axes
			if (isXSmaller) {
				nXLow = smallerDim;
				nYLow = largerDim;
			} else {
				nXLow = largerDim;
				nYLow = smallerDim;
			}
		
		}
		nXYLow[0] = nXLow;
		nXYLow[1] = nYLow;
	}

	
}
