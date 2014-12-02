/*
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
 * Class which expands on the AFunction class to give the properties of a pearsonVII. A 1D implementation
 * function derived from Gozzo, F. (2004). 
 * First experiments at the Swiss Light Source Materials Science beamline powder diffractometer.
 * Journal of Alloys and Compounds, 362(1-2), 206-217. doi:10.1016/S0925-8388(03)00585-1
 */
public class PearsonVII extends APeak implements IPeak {
	private static final String NAME = "PearsonVII";
	private static final String DESC = "y(x) = PearsonVII distribution";
	private static final String[] PARAM_NAMES = new String[]{"posn", "fwhm", "area", "power"};
	private static final double[] PARAMS = new double[] { 0, 0, 0, 0 };

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
		fillParameters(params);

		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(POWER).setLowerLimit(1.0);
		getParameter(POWER).setUpperLimit(10.0);
		setNames();
	}

	public PearsonVII(IParameter... params) {
		super(PARAMS.length);
		fillParameters(params);

		getParameter(FWHM).setLowerLimit(0.0);
		getParameter(POWER).setLowerLimit(1.0);
		getParameter(POWER).setUpperLimit(10.0);
		setNames();
	}

	private static final int POWER = AREA + 1;
	private static final double DEF_POWER = 2;

	public PearsonVII(IdentifiedPeak peakParameters) {
		super(PARAMS.length); 
		
		// pos
		double range = peakParameters.getMaxXVal()-peakParameters.getMinXVal();
		IParameter p;
		p = getParameter(POSN);
		p.setValue(peakParameters.getPos());
		p.setLimits(peakParameters.getMinXVal(), peakParameters.getMaxXVal());
		
		// fwhm
		p = getParameter(FWHM);
		p.setLimits(0, range*2);
		p.setValue(peakParameters.getFWHM()/2);

		// area
		// better fitting is generally found if sigma expands into the peak.
		p = getParameter(AREA);
		p.setLimits(0, peakParameters.getHeight()*range*4);
		p.setValue(peakParameters.getArea()/2);

		// power
		p = getParameter(POWER);
		p.setValue(DEF_POWER);
		p.setLimits(1.0, 10.0);

		setNames();
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

	double pos, halfwp, power;

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

	public double getPos() {
		return pos;
	}

	public void setPos(double pos) {
		this.pos = pos;
	}

	public double getHalfwp() {
		return halfwp;
	}

	public void setHalfwp(double halfwp) {
		this.halfwp = halfwp;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(halfwp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pos);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(power);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PearsonVII other = (PearsonVII) obj;
		if (Double.doubleToLongBits(halfwp) != Double.doubleToLongBits(other.halfwp))
			return false;
		if (Double.doubleToLongBits(pos) != Double.doubleToLongBits(other.pos))
			return false;
		if (Double.doubleToLongBits(power) != Double.doubleToLongBits(other.power))
			return false;
		return true;
	}
}
