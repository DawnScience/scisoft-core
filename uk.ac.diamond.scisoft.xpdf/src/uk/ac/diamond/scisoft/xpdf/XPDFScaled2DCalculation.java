/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

abstract public class XPDFScaled2DCalculation {

	int maxGridSize;
	
	abstract protected Dataset calculate(Dataset gamma, Dataset delta);
	
	public XPDFScaled2DCalculation(int maxGridSize) {
		this.maxGridSize = maxGridSize;
	}
	
	public Dataset run(Dataset gamma, Dataset delta) {
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
		
		Dataset absorption = calculate(gammaDown, deltaDown);
//		= calculateAbsorptionFluorescence(gammaDown, deltaDown,
//				Arrays.asList(new XPDFComponentGeometry[] {attenuatorGeometry}),
//				Arrays.asList(new Double[] {attenuationCoefficient}), Arrays.asList(new Double[] {attenuationCoefficient}),
//				beamData,
//				doUpstreamAbsorption, doDownstreamAbsorption, true);
		
		// Upsample the absorption back to the original resolution and return
		return XPDFRegrid.two(absorption, nXHigh, nYHigh);
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
