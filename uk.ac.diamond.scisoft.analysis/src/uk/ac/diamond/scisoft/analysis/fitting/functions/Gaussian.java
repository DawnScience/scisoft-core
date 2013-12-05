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
 * Class which expands on the AFunction class to give the properties of a gaussian. A 1D implementation
 */
public class Gaussian extends APeak implements IPeak {
	private static final String cname = "Gaussian";
	private static final String cdescription = "y(x) = A exp(-((x-b)^2)/(2*c^2))";
	private static final String[] paramNames = new String[]{"posn", "fwhm", "area"};
	private static final double[] params = new double[]{0, 0, 0};

	public Gaussian() {
		this(params);
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
	public Gaussian(double... params) {
		super(3);
		// make sure that there are 3 parameters, otherwise, throw a sensible error
		if (params.length != 3) 
			throw new IllegalArgumentException("A gaussian peak requires 3 parameters, and it has only been given "+params.length);
		fillParameters(params);
		getParameter(FWHM).setLowerLimit(0.0);

		setNames();
	}

	public Gaussian(IParameter... params) {
		super(3);
		if (params.length != 3) 
			throw new IllegalArgumentException("A gaussian peak requires 3 parameters, and it has only been given "+params.length);
		fillParameters(params);
		getParameter(FWHM).setLowerLimit(0.0);

		setNames();
	}

	public Gaussian(IdentifiedPeak peakParameters) {
		super(3); 
		double range = peakParameters.getMaxXVal() - peakParameters.getMinXVal();
		double fwhm2 = peakParameters.getFWHM() * 2;
		double pos = peakParameters.getPos();
		double maxArea = peakParameters.getHeight() * range * 4;

		IParameter p;
		p = getParameter(POSN);
		p.setValue(pos);
		p.setLimits(pos - fwhm2, pos + fwhm2);

		p = getParameter(FWHM);
		p.setLimits(0, range*2);
		p.setValue(peakParameters.getFWHM() / 2);
		
		p = getParameter(AREA);
		p.setLimits(-maxArea, maxArea);
		p.setValue(peakParameters.getArea() / 2); // area better fitting is generally found if sigma expands into the peak.

		setNames();
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
		super(3);

		internalSetPeakParameters(minPeakPosition, maxPeakPosition, maxFWHM, maxArea);

		setNames();
	}

	private void setNames() {
		name = cname;
		description = cdescription;
		for (int i = 0; i < paramNames.length; i++) {
			IParameter p = getParameter(i);
			p.setName(paramNames[i]);
		}
	}

	private static final double CONST = Math.sqrt(4. * Math.log(2.));

	double pos, sigma, height;
	private void calcCachedParameters() {		
		pos = getParameterValue(POSN);
		sigma = getParameterValue(FWHM) / CONST;
		double area = getParameterValue(AREA);
		height = area / (Math.sqrt(Math.PI) * sigma);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double arg = (values[0] - pos) / sigma; 

		double ex = Math.exp(- arg * arg);
		return height * ex;
	}

	@Override
	public double getHeight() {
		if (isDirty())
			calcCachedParameters();

		return height;
	}
}
