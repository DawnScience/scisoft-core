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
 * Class which expands on the AFunction class to give the properties of a Gaussian. A 1D implementation
 */
public class Gaussian extends APeak {
	private static final long serialVersionUID = 8659824721733743658L;

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

	private static final double CONST_A = Math.sqrt(4. * Math.log(2.));
	private static final double CONST_B = 1.0 / Math.sqrt(Math.PI);

	private transient double pos, fr;

	/**
	 * Calculate height of Gausssian
	 * @param area
	 * @param fwhm
	 * @return height of Gaussian
	 */
	public static double calcHeight(double area, double fwhm) {
		return CONST_A * CONST_B * area / fwhm;
	}

	@Override
	protected void calcCachedParameters() {
		pos = getParameterValue(POSN);
		fr = CONST_A / getParameterValue(FWHM);
		height = fr * CONST_B * getParameterValue(AREA);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			calcCachedParameters();
		}

		final double arg = fr * (values[0] - pos); 
		return height * Math.exp(-(arg * arg));
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			calcCachedParameters();
		}

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			double arg = fr * (coords[0] - pos); 

			buffer[i++] = height * Math.exp(-(arg * arg));
		}
	}
	
	@Override
	public double partialDeriv(IParameter parameter, double... position) {
		if (isDirty()) {
			calcCachedParameters();
		}

		if (isDuplicated(parameter)) {
			return super.partialDeriv(parameter, position);
		}

		int i = indexOfParameter(parameter);
		final double del = position[0] - pos;
		final double arg = fr * del;

		switch (i) {
		case 0:
			return 2*height*arg*fr*Math.exp(-(arg * arg));
		case 1:
			final double sqarg = arg * arg;
			return fr*height*(2*sqarg - 1)*Math.exp(-sqarg)/CONST_A;
		case 2:
			return CONST_B * fr * Math.exp(-(arg * arg));
		default:
			return 0;
		}
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			calcCachedParameters();
		}

		if (isDuplicated(parameter)) {
			super.fillWithPartialDerivativeValues(parameter, data, it);
			return;
		}

		int j = indexOfParameter(parameter);

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		switch (j) {
		case 0:
			while (it.hasNext()) {
				final double del = coords[0] - pos;
				final double arg = fr * del;
				buffer[i++] = 2*height*arg*fr*Math.exp(-(arg * arg));
			}
			break;
		case 1:
			while (it.hasNext()) {
				final double del = coords[0] - pos;
				final double arg = fr * del;
				final double sqarg = arg * arg;
				buffer[i++] = fr*height*(2*sqarg - 1)*Math.exp(-sqarg)/CONST_A;
			}
			break;
		case 2:
			while (it.hasNext()) {
				final double del = coords[0] - pos;
				final double arg = fr * del;
				buffer[i++] = CONST_B * fr * Math.exp(-(arg * arg));
			}
			break;
		default:
			break;
		}
	}
}
