/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;


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

	public Lorentzian(IParameter[] params) {
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
	public String toString() {
		final StringBuilder out = new StringBuilder();
		out.append(String.format("Lorentzian position has value %f within the bounds [%f,%f]\n", getParameterValue(0),
				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit()));
		out.append(String.format("Lorentzian area     has value %f within the bounds [%f,%f]\n", getParameterValue(1),
				getParameter(1).getLowerLimit(), getParameter(1).getUpperLimit()));
		out.append(String.format("Lorentzian gamma    has value %f within the bounds [%f,%f]", getParameterValue(2),
				getParameter(2).getLowerLimit(), getParameter(2).getUpperLimit()));
		return out.toString();
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
