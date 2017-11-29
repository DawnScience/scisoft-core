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
public class ElasticLineFitModel extends RixsBaseModel {

	@OperationModelField(label = "Width of strip to use to find elastic line", hint = "1 for using column maxima, 2+ for summing and fitting line", min = 1)
	private int delta = 25;

	@OperationModelField(label = "Maximum magnitude of straight line slope", min = 1e-6, max = 1)
	private double maxSlope = 0.2;
	
	@OperationModelField(label = "Maximum deviation from straight line for included points", min = 1e-6)
	private double maxDev = 1.5;

	@OperationModelField(label = "Minimum number of points allowed for valid straight line fit", min = 3)
	private int minPoints = 5;

	@OperationModelField(label = "Minimum number of photons required before attempting to find elastic line", min = 20)
	private int minPhotons = 150;

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
}
