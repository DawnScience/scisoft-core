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
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


/**
 * Class that wrappers the Lorentzian function (aka Breit-Wigner or Cauchy distribution) <br>
 * y(x) = A x(half)^2 / ( x(half)^2 + (x-a)^2 ) <br>
 * where : <br>
 * A is the height<br>
 * a is the position of the peak.<br>
 * and <br>
 * x(half) is the half width at half maximum, known as gamma
 */
public class Lorentzian extends APeak {
	private static final String NAME = "Lorentzian";
	private static final String DESC = "y(x) = A x(half)^2 / ( x(half)^2 + (x-a)^2 )\nwhere A is the height\na is the position of the peak\nx(half) is the half width at half maximum, known as gamma";
	private static String[] PARAM_NAMES = new String[]{"posn", "fwhm", "area"};
	private static final double[] PARAMS = new double[] { 0, 0, 0 };

	public Lorentzian() { 
		this(PARAMS);
	}

	/**
	 * Constructor which takes the three properties required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- half width at half maximum
	 *     Parameter 3 	- Area
	 * </pre>
	 * 
	 * @param params
	 */
	public Lorentzian(double... params) {
		super(PARAMS.length);
		fillParameters(params);

		getParameter(FWHM).setLowerLimit(0.0);
		setNames();
	}

	public Lorentzian(IParameter... params) {
		super(PARAMS.length);
		fillParameters(params);

		getParameter(FWHM).setLowerLimit(0.0);
		setNames();
	}

	public Lorentzian(IdentifiedPeak peakParameters) {
		super(PARAMS.length);

		double range = peakParameters.getMaxXVal() - peakParameters.getMinXVal();
		double maxArea = peakParameters.getHeight() * range * 4;

		IParameter p;
		p = getParameter(POSN);
		p.setValue(peakParameters.getPos());
		p.setLimits(peakParameters.getMinXVal(), peakParameters.getMaxXVal());

		// fwhm
		p = getParameter(FWHM);
		p.setLimits(0, range*2);
		p.setValue(peakParameters.getFWHM() / 2);

		// area
		p = getParameter(AREA);
		p.setLimits(-maxArea, maxArea);
		p.setValue(peakParameters.getArea() / 2);

		setNames();
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
	public Lorentzian(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
		super(PARAMS.length);

		internalSetPeakParameters(minPeakPosition, maxPeakPosition, maxFWHM, maxArea);

		setNames();
	}

	private void setNames() {
		name = NAME;
		description = DESC;
		for (int i = 0; i < PARAM_NAMES.length; i++) {
			IParameter p = getParameter(i);
			p.setName(PARAM_NAMES[i]);
		}
	}

	double halfw, pos;

	@Override
	protected void calcCachedParameters() {
		pos = getParameterValue(POSN);
		halfw = getParameterValue(FWHM) / 2.0;
		height = getParameterValue(AREA) / (Math.PI * halfw);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double dist = (values[0] - pos) / halfw;
		return height / ( dist * dist + 1);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty())
			calcCachedParameters();

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			double dist = (coords[0] - pos) / halfw; 

			buffer[i++] = height / ( dist * dist + 1);
		}
	}
}
