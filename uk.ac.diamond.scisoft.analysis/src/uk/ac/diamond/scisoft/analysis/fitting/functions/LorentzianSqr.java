/*
 * Copyright (c) 2015 Gero Flucke, DESY.
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
 * Class for a square variant of the Lorentzian function <br>
 * that has smaller tails: <br>
 * f(x; N, G, x0) = 4*N/(G*pi) * 1/(1 + ( ( x-x0 ) / (G/2) )^2 )^2 <br>
 * where : <br>
 * * N is the peak area (= height * G/2 * pi/2),<br>
 * * x0 is the position of the peak (posn) and <br>
 * * G is a width parameter, related to the full width at half maximum
 *     via G = fwhm / sqrt(sqrt(2.) - 1.).
 */
public class LorentzianSqr extends APeak {
	private static final String NAME = "LorentzianSqr";
	private static final String DESC = "Similar to a Lorentzian, but smaller tails:\n"
			+ "f(x; N, G, x0) =\n"
			+ "    4*N/(G*pi) * 1/(1 + ( ( x-x0 ) / (G/2) )^2 )^2 \n"
			+ "where\n"
			+ "- N is the area (=height* G/2 * pi/2),\n"
			+ "- x0 is the position of the peak (posn) and\n"
			+ "- G is a width parameter\n"
			+ "     G = fwhm / sqrt(sqrt(2.) - 1.)."; 
	private static String[] PARAM_NAMES = new String[]{"posn", "fwhm", "area"};
	private static final double[] PARAMS = new double[] { 0, 0, 0 };

	public LorentzianSqr() { 
		this(PARAMS);
	}

	/**
	 * Constructor which takes the three properties required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- full width at half maximum
	 *     Parameter 3 	- Area
	 * </pre>
	 * 
	 * @param params
	 */
	public LorentzianSqr(double... params) {
		super(PARAMS.length);
		fillParameters(params);

		getParameter(FWHM).setLowerLimit(0.0);
		setNames();
	}

	public LorentzianSqr(IParameter... params) {
		super(PARAMS.length);
		fillParameters(params);

		getParameter(FWHM).setLowerLimit(0.0);
		setNames();
	}

	public LorentzianSqr(IdentifiedPeak peakParameters) {
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
		p.setValue(peakParameters.getFWHM());

		// area
		p = getParameter(AREA);
		p.setLimits(-maxArea, maxArea);
		p.setValue(peakParameters.getArea()); // / 2); // GF: why by two (as in Lorentzian)?

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
	public LorentzianSqr(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
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

	double widthPar, pos;

	// conversion between FWHM and parameter G in function:
	private static final double CONST = 1./Math.sqrt(Math.sqrt(2.0) - 1.);
	@Override
	protected void calcCachedParameters() {
		pos = getParameterValue(POSN);
		widthPar = getParameterValue(FWHM) * CONST;
		height = getParameterValue(AREA) / (0.5 * Math.PI * widthPar);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double dist = (values[0] - pos) / widthPar;
		double denominatorSqrt = (dist * dist + 1);
		return height / (denominatorSqrt * denominatorSqrt);
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
			double dist = (coords[0] - pos) / widthPar; 
			double denominatorSqrt = (dist * dist + 1);
			buffer[i++] = height / (denominatorSqrt * denominatorSqrt);
		}
	}

//	public double getHalfw() {
//		return halfw;
//	}
//
//	public void setHalfw(double halfw) {
//		this.halfw = halfw;
//	}

	public double getPos() {
		return pos;
	}

	public void setPos(double pos) {
		this.pos = pos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(widthPar);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pos);
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
		LorentzianSqr other = (LorentzianSqr) obj;
		// other data members tested already in super.equals(..)
		if (Double.doubleToLongBits(widthPar) != Double.doubleToLongBits(other.widthPar))
			return false;
		if (Double.doubleToLongBits(pos) != Double.doubleToLongBits(other.pos))
			return false;
		return true;
	}
}
