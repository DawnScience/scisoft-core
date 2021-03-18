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
import org.eclipse.january.dataset.DoubleDataset;


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
	private static final long serialVersionUID = 1163306734433900662L;

	private static final String NAME = "Lorentzian";
	private static final String DESC = "A Lorentzian function also known as a Breit-Wigner or Cauchy distribution."
			+ "\n    y(x) = A a^2 / ( a^2 + (x - pos)^2 )"
			+ "\nwhere fwhm = 2*a, and area = pi * a * A." + PEAK_DESC;

	private static final double[] PARAMS = new double[] { 0, 0, 0 };

	public Lorentzian() { 
		this(PARAMS);
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
	public Lorentzian(double... params) {
		super(params);
	}

	public Lorentzian(IParameter... params) {
		super(params);
	}

	public Lorentzian(IdentifiedPeak peakParameters) {
		super(peakParameters);
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
	}

	@Override
	public int getNoOfParameters() {
		return PARAMS.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private transient double halfw, pos;

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
