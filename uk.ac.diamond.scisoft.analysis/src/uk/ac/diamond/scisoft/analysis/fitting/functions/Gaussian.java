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
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


/**
 * Class which expands on the AFunction class to give the properties of a Gaussian. A 1D implementation
 */
public class Gaussian extends APeak {
	private static final String NAME = "Gaussian";
	private static final String DESC = "A Gaussian or normal distribution."
			+ "\n    y(x) = A exp( -(x-posn)^2 / a^2 )"
			+ "\nwhere fwhm = 2*a*sqrt(ln(2)), and area = sqrt(pi) * a * A." + PEAK_DESC;
	private static final double[] PARAMS = new double[]{0, 0, 0};

	public Gaussian() {
		this(PARAMS);
	}

	/**
	 * Constructor which takes the three parameters required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- FWHM (full width at half maximum)
	 *     Parameter 3 	- Area
	 * </pre>
	 * 
	 * @param params
	 */
	public Gaussian(double... params) {
		super(params);
	}

	public Gaussian(IParameter... params) {
		super(params);
	}

	public Gaussian(IdentifiedPeak peakParameters) {
		super(peakParameters);
	}

	/**
	 * Constructor which takes more sensible values for the parameters, which also incorporates the limits which they
	 * can be in, reducing the overall complexity of the problem
	 * 
	 * @param minPeakPosition
	 *            The minimum value the peak position of the Gaussian
	 * @param maxPeakPosition
	 *            The maximum value of the peak position
	 * @param maxFWHM
	 *            Full width at half maximum
	 * @param maxArea
	 *            The maximum area of the peak
	 */
	public Gaussian(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
		super(minPeakPosition, maxPeakPosition, maxFWHM, maxArea);
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private static final double CONST = Math.sqrt(4. * Math.log(2.));

	private transient double pos, fr;

	@Override
	protected void calcCachedParameters() {
		pos = getParameterValue(POSN);
		fr = CONST / getParameterValue(FWHM);
		double area = getParameterValue(AREA);
		height = fr * area / Math.sqrt(Math.PI);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double arg = fr * (values[0] - pos); 

		return height * Math.exp(- arg * arg);
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
			double arg = fr * (coords[0] - pos); 

			buffer[i++] = height * Math.exp(- arg * arg);
		}
	}
}
