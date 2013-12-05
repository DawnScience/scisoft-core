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

import org.apache.commons.math3.special.Beta;


/**
 * Class which expands on the AFunction class to give the properties of a pearsonVII. A 1D implementation
 * function derived from Gozzo, F. (2004). 
 * First experiments at the Swiss Light Source Materials Science beamline powder diffractometer.
 * Journal of Alloys and Compounds, 362(1-2), 206-217. doi:10.1016/S0925-8388(03)00585-1
 */
public class PearsonVII extends APeak implements IPeak {
	private static final String cname = "PearsonVII";
	private static final String[] paramNames = new String[]{"posn", "fwhm", "area", "power"};
	private static final String cdescription = "y(x) = PearsonVII distribution";
	private static final double[] params = new double[] { 0, 0, 0, 0 };

	public PearsonVII() {
		this(params);
	}

	/**
	 * Constructor which takes the three properties required, which are
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
		super(params);

		setNames();
	}

	public PearsonVII(IParameter... params) {
		super(params);

		setNames();
	}

	private static final int POWER = AREA + 1;
	private static final double DEF_POWER = 2;

	public PearsonVII(IdentifiedPeak peakParameters) {
		super(4); 
		
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
		super(4);

		internalSetPeakParameters(minPeakPosition, maxPeakPosition, maxFWHM, maxArea);

		IParameter p;
		p = getParameter(POWER);
		p.setLimits(1.0, 10.0);
		p.setValue(power);

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

	double pos, halfwp, power, height;

	private void calcCachedParameters() {
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

		double a = (values[0] - pos) / halfwp;

		return height / Math.pow((1.0 + a * a), power);
	}

	@Override
	public double getHeight() {
		if (isDirty())
			calcCachedParameters();

		return height;
	}
}
