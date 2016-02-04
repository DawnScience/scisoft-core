/*-
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.utils.Faddeeva;


/**
 * Class for a Voigt profile which is a Lorentzian convolved with a Gaussian
 */
public class Voigt extends APeak {
	private static final String NAME = "Voigt";
	private static final String DESC = "A Voigt profile which is a Lorentzian convolved with a Gaussian function."
			+ "\n    y(x) = Convolve(Lorentzian(x; posn, l_fwhm, area), Gaussian(x; 0, g_fwhm, 1)"
			+ "\nwhere Lorentzian(x; posn, w, area) = (w*area/(2*pi)) / (w^2/4 + (x-posn)^2),"
			+ "\nposn is the resonant position, w is the Lorentzian full width.";

	private static final String[] LOCAL_PARAM_NAMES = new String[] { "posn", "l_fwhm", "area", "g_fwhm" };
	private static final double[] PARAMS = new double[] {0, 1, 1, 1};

	private static final int FWHMG = AREA + 1;
	public Voigt() {
		this(PARAMS);
	}

	/**
	 * Constructor which takes the five parameters required, which are
	 * 
	 * <pre>
	 *     posn - resonant position
	 *     l_fwhm - Lorentzian width
	 *     area - Lorentzian area
	 *     g_fwhm - Gaussian width
	 * </pre>
	 * 
	 * @param params
	 */
	public Voigt(double... params) {
		super(PARAMS.length);
		if (params.length != PARAMS.length) {
			throw new IllegalArgumentException("A Fano-Gaussian function requires 5 parameters, and it has been given " + params.length);
		}

		setParameterValues(params);
		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(FWHMG).setLowerLimit(0.0);
	}

	public Voigt(IParameter... params) {
		super(PARAMS.length);
		if (params.length != PARAMS.length) { 
			throw new IllegalArgumentException("A Fano-Gaussian function requires 5 parameters, and it has been given " + params.length);
		}

		setParameters(params);
		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(FWHMG).setLowerLimit(0.0);
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, LOCAL_PARAM_NAMES);
	}

	private static final double CONST = Math.sqrt(8 * Math.log(2.));

	private transient double r, ft, fr, zi;

	@Override
	protected void calcCachedParameters() {
		r = getParameterValue(POSN);
		double l = getParameterValue(FWHM) / 2.;
		double sigma = getParameterValue(FWHMG) / CONST;
		if (sigma < 5 * Double.MIN_NORMAL) { // fix Lorentzian limit
			sigma = 10 * Double.MIN_NORMAL;
		}
		fr = Math.sqrt(0.5) / sigma;
		zi = fr * l;
		ft = fr * getParameterValue(AREA) / Math.sqrt(Math.PI);
		height = ft * Faddeeva.erfcx(zi);
		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			calcCachedParameters();
		}

		Complex z = new Complex(fr * (values[0] - r), zi);
		Complex w = Faddeeva.w(z, 0);

		double result = ft * w.getReal();
		return result;
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
			Complex z = new Complex(fr * (coords[0] - r), zi);
			Complex w = Faddeeva.w(z, 0);

			buffer[i++] = ft * w.getReal();
		}
	}
}
