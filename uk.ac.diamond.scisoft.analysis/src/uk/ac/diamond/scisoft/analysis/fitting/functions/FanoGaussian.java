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
import org.eclipse.january.dataset.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.utils.Faddeeva;


/**
 * Class for a modified Fano profile convolved with a Gaussian
 */
public class FanoGaussian extends AFunction {
	private static final String NAME = "Fano-Gaussian";
	private static final String DESC = "A modified Fano profile convolved Gaussian function."
			+ "\n    y(x) = Convolve(MFano(x; posn, l_fwhm, q), Gaussian(x; 0, g_fwhm, area)"
			+ "\nwhere MFano(x; posn, fw, q) = (1/(pi*fw*(q^2-1))) * [ (q fw / 2 + (x-posn))^2 / (fw^2/4 + (x-posn)^2) - 1],"
			+ "\nposn is the resonant position, l_fwhm is the Lorentzian full width, and q is the Fano parameter and > 1.";

	private static final String[] PARAM_NAMES = new String[] {"posn", "l_fwhm", "area", "g_fwhm", "q"};
	private static final int POSN = 0;
	private static final int FWHM = 1;
	private static final int AREA = 2;
	private static final int FWHMG = 3;
	private static final int FANO = 4;

	private static final double[] PARAMS = new double[] {0, 1, 2, 1.5, 2};
	private static final double Q_LOWER = 1.0 + Math.ulp(1.);

	public FanoGaussian() {
		this(PARAMS);
	}

	/**
	 * Constructor which takes the five parameters required, which are
	 * 
	 * <pre>
	 *     posn - resonant position
	 *     l_fwhm - Lorentzian line width
	 *     area - Gaussian area
	 *     g_fwhm - Gaussian width
	 *     q - Fano parameter, > 1
	 * </pre>
	 * 
	 * @param params
	 */
	public FanoGaussian(double... params) {
		super(PARAMS.length);
		if (params.length != PARAMS.length) {
			throw new IllegalArgumentException("A Fano-Gaussian function requires 5 parameters, and it has been given " + params.length);
		}

		if (params[FANO] <= 1) {
			throw new IllegalArgumentException("Fano parameter must be greater than 1");
		}
		setParameterValues(params);
		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(AREA).setLowerLimit(0.0);
		getParameter(FWHMG).setLowerLimit(0.0);
		getParameter(FANO).setLowerLimit(Q_LOWER);
	}

	public FanoGaussian(IParameter... params) {
		super(PARAMS.length);
		if (params.length != PARAMS.length) { 
			throw new IllegalArgumentException("A Fano-Gaussian function requires 5 parameters, and it has been given " + params.length);
		}

		if (params[FANO].getValue() < 1) {
			throw new IllegalArgumentException("Fano parameter must be greater than 1");
		}
		setParameters(params);
		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(AREA).setLowerLimit(0.0);
		getParameter(FWHMG).setLowerLimit(0.0);
		getParameter(FANO).setLowerLimit(Q_LOWER);
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private static final double CONST = Math.sqrt(8 * Math.log(2.));

	private transient double r, ft, fr, fq, zi;

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
		double q = getParameterValue(FANO);
		fq = 2 * q / (q * q - 1);
		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			calcCachedParameters();
		}

		Complex z = new Complex(fr * (values[0] - r), zi);
		Complex w = Faddeeva.w(z, 0);

		double result = ft * (w.getReal() + fq * w.getImaginary());
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

			buffer[i++] = ft * (w.getReal() + fq * w.getImaginary());
		}

	}
}
