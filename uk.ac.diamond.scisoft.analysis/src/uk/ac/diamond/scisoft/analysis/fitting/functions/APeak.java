/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IPeak;


/**
 * A peak function is determined at least three parameters:
 *  position, full width at half-maximum, area
 * <p>
 * If height of peak can be calculated then set height in the overriding
 * calcCachedParameters method.
 */
public abstract class APeak extends AFunction implements IPeak {
	protected static final String[] PARAM_NAMES = new String[]{"posn", "fwhm", "area"};
	protected static final String PEAK_DESC = "\nA peak function is described by"
			+ "\n    'posn' is the position of maximum of the peak,"
			+ "\n    'fwhm' is the full-width at half-maximum value of the peak,"
			+ "\n    'area' is the area of the peak.";

	protected static final int POSN = 0;
	protected static final int FWHM = 1;
	protected static final int AREA = 2;

	protected double height; // height of peak

	public APeak(int n) {
		super(n);
	}

	/**
	 * Constructor which takes the three properties required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- FWHM (full width at half maximum)
	 *     Parameter 3 	- Area
	 * </pre>
	 * 
	 * @param params
	 */
	public APeak(double... params) {
		super(3);
		if (params.length != 3) {
			throw new IllegalArgumentException("A peak requires 3 parameters, and it has been given " + params.length);
		}
		setParameterValues(params);
		setNames();

		getParameter(FWHM).setLowerLimit(0.0);
	}

	/**
	 * Constructor which takes the three properties required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- FWHM (full width at half maximum)
	 *     Parameter 3 	- Area
	 * </pre>
	 * 
	 * @param params
	 */
	public APeak(IParameter... params) {
		super(3);
		if (params.length != 3) {
			throw new IllegalArgumentException("A peak requires 3 parameters, and it has been given " + params.length);
		}
		setParameters(params);
		setNames();

		getParameter(FWHM).setLowerLimit(0.0);
	}

	public APeak(IdentifiedPeak peakParameters) {
		super(3);

		setParameters(peakParameters);
		setNames();
	}

	protected void setParameters(IdentifiedPeak peakParameters) {
		double range = peakParameters.getMaxXVal() - peakParameters.getMinXVal();
		double maxArea = peakParameters.getHeight() * range * 4;

		IParameter p;
		p = getParameter(POSN);
		p.setValue(peakParameters.getPos());
		p.setLimits(peakParameters.getMinXVal(), peakParameters.getMaxXVal());

		p = getParameter(FWHM);
		p.setLimits(0, range*2);
		p.setValue(peakParameters.getFWHM() / 2);
		
		p = getParameter(AREA);
		p.setLimits(-maxArea, maxArea);
		p.setValue(peakParameters.getArea() / 2); // area better fitting is generally found if sigma expands into the peak.
	}

	/**
	 * Constructor which takes more sensible values for the parameters, which also incorporates the limits which they
	 * can be in, reducing the overall complexity of the problem
	 * 
	 * @param minPeakPosition
	 *            The minimum value of the peak position
	 * @param maxPeakPosition
	 *            The maximum value of the peak position
	 * @param maxFWHM
	 *            Full width at half maximum
	 * @param maxArea
	 *            The maximum area of the peak
	 */
	public APeak(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
		super(3);

		setNames();
		internalSetPeakParameters(minPeakPosition, maxPeakPosition, maxFWHM, maxArea);
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
		if (isDirty()) {
			calcCachedParameters();
		}

		return height;
	}

	/**
	 * Implement this to set height and any other internally-used values
	 */
	abstract protected void calcCachedParameters();
}
