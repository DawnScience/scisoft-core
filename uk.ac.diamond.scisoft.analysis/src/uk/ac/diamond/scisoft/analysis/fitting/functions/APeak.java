/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;


/**
 * A peak function is determined at least three parameters:
 *  position, full width at half-maximum, area
 * <p>
 * If height of peak can be calculated then set height in the overriding
 * calcCachedParameters method.
 */
public abstract class APeak extends AFunction implements IPeak {
	protected static final int POSN = 0;
	protected static final int FWHM = 1;
	protected static final int AREA = 2;

	protected double height; // height of peak

	public APeak(int n) {
		super(n);
	}

	protected void internalSetPeakParameters(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
		IParameter p;
		p = getParameter(POSN);
		p.setValue((maxPeakPosition + minPeakPosition) / 2.0);
		p.setLimits(minPeakPosition, maxPeakPosition);

		p = getParameter(FWHM);
		p.setLimits(0.0, maxFWHM);
		p.setValue(maxFWHM / 5.0);

		p = getParameter(AREA);
		p.setLimits(-maxArea, maxArea);
		p.setValue(maxArea / 2.0); // better fitting is generally found if sigma expands into the peak.
	}

	@Override
	public double getPosition() {
		return getParameterValue(POSN);
	}

	@Override
	public double getFWHM() {
		return getParameterValue(FWHM);
	}

	@Override
	public double getArea() {
		return getParameterValue(AREA);
	}

	@Override
	public double getHeight() {
		if (isDirty())
			calcCachedParameters();

		return height;
	}

	/**
	 * Implement this to set height and any other internally-used values
	 */
	abstract protected void calcCachedParameters();
}
