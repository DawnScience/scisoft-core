/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * Model for fitting elastic line in RIXS images
 */
public class ElasticLineReductionModel extends RixsBaseModel {

	@OperationModelField(label = "Width of strip", description = "Used to find elastic line", hint = "0 for minimizing FWHM of summed spectra, 1 for using column maxima, 2+ for summing and fitting line", min = 0, enableif = "slopeOverride == null")
	private int delta = 1;

	@OperationModelField(label = "Maximum slope", description = "Maximum value allowed for elastic line slope", min = 1e-6, max = 1, enableif = "slopeOverride == 0", expertOnly = true)
	private double maxSlope = 0.2;
	
	@OperationModelField(label = "Maximum deviation", description = "Maximum distance allowed for included points", min = 1e-6, enableif = "delta != 0 && slopeOverride == 0", expertOnly = true)
	private double maxDev = 1.5;

	@OperationModelField(label = "Minimum number of fit points", description = "Minimum number required for valid elastic line fit", min = 3, enableif = "delta != 0", expertOnly = true)
	private int minPoints = 5;

	@OperationModelField(label = "Minimum number of photons", description = "Minimum number required before attempting to find elastic line", min = 20, expertOnly = true)
	private int minPhotons = 100;

	@OperationModelField(label = "Elastic peak fitting width factor", description = "Factor used to select range of points to fit elastic peak. The range is initial peak position +/- factor * initial FHWM", hint = "Multiplier for FWHM", min = 0.5, expertOnly = true)
	private double peakFittingFactor = 8;

	/**
	 * @return width of strip to use to find elastic line
	 */
	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		firePropertyChange("setRatio", this.delta, this.delta = delta);
	}

	/**
	 * @return maximum slope to constrain straight line fit
	 */
	public double getMaxSlope() {
		return maxSlope;
	}

	public void setMaxSlope(double maxSlope) {
		firePropertyChange("setMaxSlope", this.maxSlope, this.maxSlope = maxSlope);
	}

	/**
	 * @return maximum deviation allowed; used to prune outliers
	 */
	public double getMaxDev() {
		return maxDev;
	}

	public void setMaxDev(double maxDev) {
		firePropertyChange("setMaxDev", this.maxDev, this.maxDev = maxDev);
	}

	/**
	 * @return minimum number of points allowed for a valid straight line fit
	 */
	public int getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(int minPoints) {
		firePropertyChange("setMinPoints", this.minPoints, this.minPoints = minPoints);
	}

	public int getMinPhotons() {
		return minPhotons;
	}

	/**
	 * @param minimum number of photons required before attempting to find elastic line
	 */
	public void setMinPhotons(int minPhotons) {
		firePropertyChange("setMinPhotons", this.minPhotons, this.minPhotons = minPhotons);
	}

	/**
	 * @return factor used to select range of points to fit elastic peak. The range is initial peak position +/- factor * initial FHWM
	 * 
	 */
	public double getPeakFittingFactor() {
		return peakFittingFactor;
	}

	public void setPeakFittingFactor(double peakFittingFactor) {
		firePropertyChange("setPeakFittingFactor", this.peakFittingFactor, this.peakFittingFactor = peakFittingFactor);
	}
}
