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

import java.io.Serializable;

/**
 * Class that wrappers the Fermi function from Fermi-Dirac distribution
 * y(x) = scale / (exp((x - mu)/kT) + 1) + C
 */
public class FermiGauss extends AFunction implements Serializable{
	
	private static String cname = "Fermi * Gaussian";

	private static String[] paramNames = new String[]{"mu", "kT", "scale", "Constant", "sigma"};

	private double mu, kT, scale, C, sigma;

	private static String cdescription = "y(x) = (scale / (exp((x - mu)/kT) + 1) + C) * exp(-((x)^2)/(2*sigma^2))";

	private static double[] params = new double[]{0,0,0,0,0};

	public FermiGauss(){
		this(params);
	}

	public FermiGauss(double... params) {
		super(params);
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	public FermiGauss(IParameter[] params) {
		super(params);
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 * 
	 * @param minMu
	 *            minimum Mu value
	 * @param maxMu
	 *            maximum Mu value
	 * @param minkT
	 *            minimum kT value
	 * @param maxkT
	 *            maximum kT value
	 * @param minScale
	 *            minimum scale value
	 * @param maxScale
	 *            maximum scale value
	 * @param minC
	 *            minimum C value
	 * @param maxC
	 *            maximum C value
	 */
	public FermiGauss(double minMu, double maxMu, double minkT, double maxkT,
					double minScale, double maxScale, double minC, double maxC,
					double minSigma, double maxSigma) {

		super(5);

		getParameter(0).setLimits(minMu, maxMu);
		getParameter(0).setValue((minMu + maxMu) / 2.0);

		getParameter(1).setLimits(minkT, maxkT);
		getParameter(1).setValue((minkT + maxkT) / 2.0);
		
		getParameter(2).setLimits(minScale, maxScale);
		getParameter(2).setValue((minScale + maxScale) / 2.0);
		
		getParameter(3).setLimits(minC, maxC);
		getParameter(3).setValue((minC + maxC) / 2.0);
		
		getParameter(4).setLimits(minSigma, maxSigma);
		getParameter(4).setValue((minSigma + maxSigma) / 2.0);

		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	private void calcCachedParameters() {
		mu = getParameterValue(0);
		kT = getParameterValue(1);
		scale = getParameterValue(2);
		C = getParameterValue(3);
		sigma = getParameterValue(4);
		
		markParametersClean();
	}


	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];
		
		double sum = 0.0;
		
		for (double p = -2.0*sigma; p < 2.0*sigma; p += 0.2*sigma) {
			
			double gArg = (p) / sigma; 
			double ex = Math.exp(-0.5 * gArg * gArg);
			
			double arg = ((position-p) - mu) / kT;
			double point = (scale/(Math.exp(arg) + 1.0) + C);
			
			sum += ex*point;
		}
		
		return sum;
	}

}
