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
	private static final double FWHM_TO_SIGMA = 1./Math.sqrt(8.*Math.log(2.));
	private static String cname = "Gaussian";
	private static String cdescription = "y(x) = A exp(-((x-b)^2)/(2*c^2))";
	private static String[] paramNames = new String[]{"pos", "FWHM", "area"};
	private static double[] defaultparams = new double[]{0,0,0};

	public Gaussian(){
		super(defaultparams);
	}

	/**
	 * Constructor which takes the three properties required, which are
	 * 
	 * <pre>
	 *     Parameter 1	- Position
	 *     Parameter 2 	- FWHM (full width half maximum)
	 *     Parameter 3 	- Area
	 * </pre>
	 * 
	 * @param params
	 */
	public Gaussian(double... params) {
		// make sure that there are 3 parameters, otherwise, throw a sensible error
		if(params.length != 3) 
			throw new IllegalArgumentException("A gaussian peak requires 3 parameters, and it has only been given "+params.length);
		fillParameters(params);
		getParameter(1).setLowerLimit(0.0);
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	public Gaussian(IParameter[] params) {
		if(params.length != 3) 
			throw new IllegalArgumentException("A gaussian peak requires 3 parameters, and it has only been given "+params.length);
		fillParameters(params);
		getParameter(1).setLowerLimit(0.0);
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	public Gaussian(IdentifiedPeak peakParameters){
		super(3); 
		double range = peakParameters.getMaxXVal()-peakParameters.getMinXVal();
		double fwham2 = peakParameters.getFWHM()*2;
		double pos = peakParameters.getPos();
		double maxArea = (peakParameters.getHeight()*2)*(range*2);
		
		
 		getParameter(0).setValue(pos);
		getParameter(0).setLimits(pos - fwham2, pos+fwham2);
		
		getParameter(1).setLimits(0,range*2);
		getParameter(1).setValue(peakParameters.getFWHM()/2);
		
		//area better fitting is generally found if sigma expands into the peak.
		getParameter(2).setLimits(-maxArea,maxArea);
		getParameter(2).setValue(peakParameters.getArea());
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
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

		getParameter(0).setValue(minPeakPosition + ((maxPeakPosition - minPeakPosition) / 2.0));
		getParameter(0).setLimits(minPeakPosition, maxPeakPosition);

		getParameter(1).setLimits(0.0, maxFWHM);
		getParameter(1).setValue(maxFWHM / 10.0);

		getParameter(2).setLowerLimit(-maxArea);
		getParameter(2).setUpperLimit(maxArea);
		// better fitting is generally found if sigma expands into the peak.
		getParameter(2).setValue(maxArea / 2.0);

		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
		
	}
	
	public Gaussian createPeakFunction(double minPosition, double maxPosition, double maxArea, double maxFWHM) {
		return new Gaussian(minPosition,  maxPosition,  maxArea,  maxFWHM);
	}
	
	double pos, sigma, norm;
	private void calcCachedParameters() {		
		pos = getParameterValue(0);
		sigma = getParameterValue(1) * FWHM_TO_SIGMA;
		double area = getParameterValue(2);
		norm = area / Math.sqrt(2.0 * Math.PI * sigma * sigma);

		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];
		double arg = (position - pos) / sigma; 

		double ex = Math.exp(-0.5 * arg * arg);
		return norm * ex;
	}

	@Override
	public String toString() {
		final StringBuilder out = new StringBuilder();

		out.append(String.format("Gaussian position has value %f within the bounds [%f,%f]\n", getParameterValue(0),
				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit()));
		out.append(String.format("Gaussian FWHM     has value %f within the bounds [%f,%f]\n", getParameterValue(1),
				getParameter(1).getLowerLimit(), getParameter(1).getUpperLimit()));
		out.append(String.format("Gaussian area    has value %f within the bounds [%f,%f]", getParameterValue(2),
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
