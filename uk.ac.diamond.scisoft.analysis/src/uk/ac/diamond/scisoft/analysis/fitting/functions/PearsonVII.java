/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.apache.commons.math3.special.Beta;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


/**
 * Class which expands on the AFunction class to give the properties of a Pearson VII. A 1D implementation
 * function derived from Gozzo, F. (2004). 
 * First experiments at the Swiss Light Source Materials Science beamline powder diffractometer.
 * Journal of Alloys and Compounds, 362(1-2), 206-217. doi:10.1016/S0925-8388(03)00585-1
 */
public class PearsonVII extends APeak {
	private static final String NAME = "PearsonVII";
	private static final String DESC = "The Pearson type VII distribution."
			+ "\n    y(x) = A / [ 1 + (x - posn)^2 / a^2 ]^m"
			+ "\nwhere fwhm = 2*a*sqrt(2^(1/m) - 1), area = a * A * Beta(m + 1/2, 1/2), and power = m."
			+ "\nWhen m nears 1 the peak becomes Lorentzian and as m approaches infinity, the peak becomes Gaussian."
			+ PEAK_DESC;
	private static final String[] LOCAL_PARAM_NAMES = new String[] { PARAM_NAMES[0], PARAM_NAMES[1], PARAM_NAMES[2], "power" };
	private static final double[] PARAMS = new double[] { 0, 0, 0, 1.0 };

	public PearsonVII() {
		this(PARAMS);
	}

	/**
	 * Constructor which takes the four properties required, which are
	 * 
	 * <pre>
	 *    position
	 *    FWHM
	 *    area
	 *    power
	 * </pre>
	 * 
	 * @param params
	 */
	public PearsonVII(double[] params) {
		super(PARAMS.length);
		setParameterValues(params);

		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(POWER).setLowerLimit(1.0);
		getParameter(POWER).setUpperLimit(10.0);
	}

	public PearsonVII(IParameter... params) {
		super(PARAMS.length);
		setParameters(params);

		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(POWER).setLowerLimit(1.0);
		getParameter(POWER).setUpperLimit(10.0);
	}

	private static final int POWER = AREA + 1;
	private static final double DEF_POWER = 2;

	public PearsonVII(IdentifiedPeak peakParameters) {
		super(PARAMS.length); 

		setParameters(peakParameters);

		IParameter p;
		p = getParameter(POWER);
		p.setValue(DEF_POWER);
		p.setLimits(1.0, 10.0);
	}

	/**
	 * Constructor which takes more sensible values for the parameters, which also incorporates the limits which they
	 * can be in, reducing the overall complexity of the problem
	 * 
	 * @param minPeakPosition
	 *            The minimum value the peak position of the Pearson VII
	 * @param maxPeakPosition
	 *            The maximum value of the peak position
	 * @param maxFWHM
	 *            The maximum full width half maximum
	 * @param maxArea
	 *            The maximum area under the PDF 
	 * 
	 * There is also a power parameter for the Pearson VII distribution. This parameter defines form as
	 * somewhere between a Gaussian and a Lorentzian function. When m = 1 the function is Lorentzian and
	 * m = infinity the function is Gaussian. With this constructor the mixing parameter is set to 2 with
	 * the lower limit set to 1 and the upper limit set to 10.
	 */
	public PearsonVII(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
		this(minPeakPosition, maxPeakPosition, maxFWHM, maxArea, DEF_POWER);
	}

	public PearsonVII(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea, double power) {
		super(PARAMS.length);

		internalSetPeakParameters(minPeakPosition, maxPeakPosition, maxFWHM, maxArea);

		IParameter p;
		p = getParameter(POWER);
		p.setLimits(1.0, 10.0);
		p.setValue(power);
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, LOCAL_PARAM_NAMES);
	}

	private transient double pos, halfwp, power;

	@Override
	protected void calcCachedParameters() {
		pos = getParameterValue(POSN);
		power = getParameterValue(POWER);
		halfwp = 0.5 * getParameterValue(FWHM) / Math.sqrt(Math.pow(2, 1. / power)  - 1);
		double beta = Math.exp(Beta.logBeta(power - 0.5,  0.5));
		height = getParameterValue(AREA) / (beta * halfwp);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double arg = (values[0] - pos) / halfwp;

		return height / Math.pow((1.0 + arg * arg), power);
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
			double arg = (coords[0] - pos) / halfwp; 

			buffer[i++] = height / Math.pow((1.0 + arg * arg), power);
		}
	}
}
