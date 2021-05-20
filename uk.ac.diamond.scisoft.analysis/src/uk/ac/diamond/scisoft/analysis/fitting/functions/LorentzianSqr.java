/*-
 * Copyright (c) 2015 Gero Flucke, DESY.
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
 * Class for a square variant of the Lorentzian function <br>
 * that has smaller tails: <br>
 * f(x; N, G, x0) = 4*N/(G*pi) * 1/(1 + ( ( x-x0 ) / (G/2) )^2 )^2 <br>
 * where : <br>
 * * N is the peak area (= height * G/2 * pi/2),<br>
 * * x0 is the position of the peak (posn) and <br>
 * * G is a width parameter, related to the full width at half maximum
 *     via G = fwhm / sqrt(sqrt(2.) - 1.).
 *     
 * Literature:
 *    Gibaud et al., J. Phys. Condens. Matter - 10.1088/0953-8984/7/14/005
 *    Langridge et al., Phys Rev. B, 49, 12022 - 10.1103/PhysRevB.49.12022
 *    Christianson et al., Phys. Rev. B, 66, 174105 - 10.1103/PhysRevB.66.174105
 */
public class LorentzianSqr extends APeak {
	private static final long serialVersionUID = -4258235862990855764L;

	private static final String NAME = "LorentzianSqr";
	private static final String DESC = "Similar to a Lorentzian, but smaller tails."
			+ "\n    y(x) = A / [1 + ((x-posn)/a)^2 ]^2"
			+ "\nwhere fwhm = 2*a*sqrt(sqrt(2) - 1), and area = pi * a * A / 2." + PEAK_DESC;

	private static final double[] PARAMS = new double[] { 0, 0, 0 };

	public LorentzianSqr() { 
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
	public LorentzianSqr(double... params) {
		super(params);
	}

	public LorentzianSqr(IParameter... params) {
		super(params);
	}

	public LorentzianSqr(IdentifiedPeak peakParameters) {
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
	public LorentzianSqr(double minPeakPosition, double maxPeakPosition, double maxFWHM, double maxArea) {
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

	private transient double halfWidthPar, pos; // 'height' already declared in APeak

	// conversion between FWHM and parameter G in function:
	private static final double CONST = 0.5/Math.sqrt(Math.sqrt(2.0) - 1.);

	@Override
	protected void calcCachedParameters() {
		pos = getParameterValue(POSN);
		halfWidthPar = getParameterValue(FWHM) * CONST;
		height = getParameterValue(AREA) / (0.5 * Math.PI * halfWidthPar);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double dist = (values[0] - pos) / halfWidthPar;
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
			double dist = (coords[0] - pos) / halfWidthPar; 
			double denominatorSqrt = (dist * dist + 1);
			buffer[i++] = height / (denominatorSqrt * denominatorSqrt);
		}
	}
}
