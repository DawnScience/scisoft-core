/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.xrmc;

import javax.vecmath.Vector3d;

import org.apache.commons.math3.analysis.interpolation.PiecewiseBicubicSplineInterpolatingFunction;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IndexIterator;

/**
 * Given detector properties, an intensity dataset and the coordinates of the
 * intensity grid in scattering angle γ and δ, return the intensity on the
 * described detector.
 * 
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class InterpolateIntensityFromGammaDelta {
	private PiecewiseBicubicSplineInterpolatingFunction interp;
	
	private Dataset realGamma;
	private Dataset realDelta;
	private Dataset gammaAxis;
	private Dataset deltaAxis;
	
	private Dataset xrmcIntensity;
	private Dataset realIntensity;
	
	private DetectorProperties dProp;

	/**
	 * Performs the interpolation.
	 * <p>
	 * Performs the interpolation of the data defined in gamma, delta coordinates to the pixel grid of the detector with the given {@link DetectorProperties}. 
	 * @param dPropIn
	 * 				The {@link DetectorProperties} of the target detector.
	 * @param intensity
	 * 					The dataset to be interpolated.
	 * @param gammaAxis
	 * 					The values of gamma on the horizontal axis of the intensity grid.
	 * @param deltaAxis
 	 * 					The values of delta on the horizontal axis of the intensity grid.
	 * @return The interpolated {@link Dataset}.
	 */
	public static Dataset calculate(DetectorProperties dPropIn, Dataset intensity, Dataset gammaAxis, Dataset deltaAxis) {
		InterpolateIntensityFromGammaDelta obj = new InterpolateIntensityFromGammaDelta(dPropIn, intensity, gammaAxis, deltaAxis);

		obj.generateInterpolator();

		obj.generateRealGammaDelta();
		obj.interpolateIntensity();
		
		return obj.realIntensity;
	}
	
	/**
	 * Returns the values of the gamma coordinate on the detector.
	 * @param dPropIn
	 * 				The {@link DetectorProperties} of the detector.
	 * @return The values of the gamma scattering angle at the centre of each pixel on the detector.
	 */
	public static Dataset getGamma(DetectorProperties dPropIn) {
		return objectForCoordinates(dPropIn).realGamma;
	}
	
	/**
	 * Returns the values of the delta coordinate on the detector.
	 * @param dPropIn
	 * 				The {@link DetectorProperties} of the detector.
	 * @return The values of the gamma scattering angle at the centre of each pixel on the detector.
	 */
	public static Dataset getDelta(DetectorProperties dPropIn) {
		return objectForCoordinates(dPropIn).realDelta;
	}
	
	private static InterpolateIntensityFromGammaDelta objectForCoordinates(DetectorProperties dPropIn) {
		InterpolateIntensityFromGammaDelta obj = new InterpolateIntensityFromGammaDelta();
		obj.setDetectorProperties(dPropIn);
		
		obj.generateRealGammaDelta();

		return obj;
	}
	
	private InterpolateIntensityFromGammaDelta() {
	}

	private InterpolateIntensityFromGammaDelta(DetectorProperties dPropIn, Dataset intensity, Dataset gammaAxis, Dataset deltaAxis) {
		this.setDetectorProperties(dPropIn);
		this.setXRMCData(intensity, gammaAxis, deltaAxis);
	}
	
	private void setDetectorProperties(DetectorProperties dPropIn) {
		this.dProp = dPropIn;
	}
	
	private void setXRMCData(Dataset intensity, Dataset gammaAxis, Dataset deltaAxis) {
		this.xrmcIntensity = intensity;
		this.gammaAxis = gammaAxis;
		this.deltaAxis = deltaAxis;
	}
	
	// Generates the gamma and delta arrays on the real detector
	private void generateRealGammaDelta() {
		int nX = dProp.getPx();
		int nY = dProp.getPy();
		
		realGamma = DatasetFactory.zeros(nX, nY);
		realDelta = DatasetFactory.zeros(nX, nY);
		
		for (int i = 0; i < nX; i++) {
			for (int j = 0; j < nY; j++) {
				Vector3d r = dProp.pixelPosition(j+0.5, i+0.5);
				realGamma.set(Math.atan2(r.x, r.z), i, j);
				realDelta.set(Math.atan2(r.y, Math.hypot(r.x, r.z)), i, j);
			}
		}
	}
	
	private void generateInterpolator() {
		int ng = gammaAxis.getSize();
		int nd = deltaAxis.getSize();
		
		// Prepare the double arrays from the Datasets
		double[] gamma = new double[ng];
		double[] delta = new double[nd];
		
		double[][] intensity = new double[ng][nd];
		
		for (int i = 0; i < ng; i++) {
			gamma[i] = gammaAxis.getDouble(i);
			for (int j = 0; j < nd; j++) {
				intensity[i][j] = xrmcIntensity.getDouble(i, j);
			}
		}
		for (int j = 0; j < nd; j++) {
			delta[j] = deltaAxis.getDouble(j);
		}
		
		interp = new PiecewiseBicubicSplineInterpolatingFunction(gamma, delta, intensity);
	}
	
	private void interpolateIntensity() {
		realIntensity = DatasetFactory.zeros(realGamma);
		double fillValue = (double) xrmcIntensity.min();
		
		IndexIterator iter = realGamma.getIterator();
		
		while(iter.hasNext()) {
			double gamma = realGamma.getElementDoubleAbs(iter.index);
			double delta = realDelta.getElementDoubleAbs(iter.index);
			if (interp.isValidPoint(gamma, delta))
				realIntensity.setObjectAbs(iter.index, interp.value(gamma, delta));
			else
				realIntensity.setObjectAbs(iter.index, fillValue);
		}
		
	}

}
