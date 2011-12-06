/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.functions;


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
	private static String cname = "Lorentzian";

	/**
	 * Constructor which takes the three properties required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- Height
	 *     Parameter 3 	- half width at half maximum
	 * </pre>
	 * 
	 * @param params
	 */
	public Lorentzian(double... params) {
		super(params);
		name = cname;
	}

	public Lorentzian(Parameter[] params) {
		super(params);
		name = cname;
	}

	public Lorentzian(IdentifiedPeak peakParameters) {
		super(3);

		double range = peakParameters.getMaxXVal()-peakParameters.getMinXVal();
		getParameter(0).setValue(peakParameters.getPos());
		getParameter(0).setLowerLimit(peakParameters.getMinXVal());//-range);
		getParameter(0).setUpperLimit(peakParameters.getMaxXVal());//+range);

		// height
		getParameter(1).setLowerLimit(0);
		getParameter(1).setUpperLimit(peakParameters.getHeight()*2);
		getParameter(1).setValue(peakParameters.getHeight());

		//fwhm
		getParameter(2).setLowerLimit(0);
		getParameter(2).setUpperLimit(range*2);
		getParameter(2).setValue(peakParameters.getFWHM()/4);

		name = cname;
	}

	/**
	 * Constructor which takes more sensible values for the parameters, which also incorporates the limits which they
	 * can be in, reducing the overall complexity of the problem
	 * 
	 * @param minPeakPosition
	 *            The minimum value of the peak position
	 * @param maxPeakPosition
	 *            The maximum value of the peak position
	 * @param maxHeight
	 *            The maximum height of the peak
	 * @param maxHalfWidth
	 *            The maximum half width at half maximum
	 */
	public Lorentzian(double minPeakPosition, double maxPeakPosition, double maxHeight, double maxHalfWidth) {

		super(3);

		getParameter(0).setValue((minPeakPosition + maxPeakPosition) / 2.0);
		getParameter(0).setLowerLimit(minPeakPosition);
		getParameter(0).setUpperLimit(maxPeakPosition);

		getParameter(1).setLowerLimit(0.0);
		getParameter(1).setUpperLimit(maxHeight);
		getParameter(1).setValue(maxHeight / 2.0);

		getParameter(2).setLowerLimit(0.0);
		getParameter(2).setUpperLimit(maxHalfWidth);
		// better fitting is generally found if sigma expands into the peak.
		getParameter(2).setValue(maxHalfWidth / 10.0);

		name = cname;
	}

	public Lorentzian createPeakFunction(double minPosition, double maxPosition, double maxArea, double maxFWHM) {
		double maxHalfWidth = maxFWHM / 2;
		double maxHeight = 2 * maxArea / maxHalfWidth; // triangular approximation of the max height of the peak.
		return new Lorentzian(minPosition, maxPosition, maxHeight, maxHalfWidth);
	}
	
	double hwhm, hwhm_sq, mean, one_by_pi, area;

	private void calcCachedParameters() {
		mean = getParameterValue(0);
		double FWHM = getParameterValue(1);
		area = getParameterValue(2);
		
		hwhm = FWHM/2.0;
		hwhm_sq = hwhm*hwhm;
		one_by_pi = 1.0/Math.PI;
		
		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];
		double dist = position - mean;
		double result = one_by_pi * (hwhm / ((dist*dist) + hwhm_sq));
		return area * result;
	}

	@Override
	public void disp() {
		// FIXME
//
//		String out = String.format("Lorentzian position has value %f within the bounds [%f,%f]", getParameterValue(0),
//				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit());
//		TerminalPrinter.print(out);
//
//		out = String.format("Lorentzian area     has value %f within the bounds [%f,%f]", getParameterValue(1),
//				getParameter(1).getLowerLimit(), getParameter(1).getUpperLimit());
//		TerminalPrinter.print(out);
//
//		out = String.format("Lorentzian gamma    has value %f within the bounds [%f,%f]", getParameterValue(2),
//				getParameter(2).getLowerLimit(), getParameter(2).getUpperLimit());
//		TerminalPrinter.print(out);
	}

	@Override
	public double getArea() {
		return getParameter(2).getValue(); 
	}

	@Override
	public double getFWHM() {
		return getParameter(1).getValue();
	}

	@Override
	public double getPosition() {
		return getParameter(0).getValue();
	}

}
