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
 * Class that wrappers the box function calculated as a difference of two Fermi functions
 * y(x) = Fermi(mu1, kT1, scale) - Fermi(mu2,kT2, scale)
 */
public class Box extends AFunction {

	private static String cname = "Box";

	private double mu1, kT1, mu2, kT2, scale;
	private Fermi fermi1, fermi2; 
	private static String cdescription = "y(x) = Fermi(mu1, kT1, scale) - Fermi(mu2,kT2, scale)";
	private static String[] paramNames = new String[]{"mu1", "kT1", "mu2", "kT2", "scale"};
	private static double[] params = new double[]{0,0,0,0,0};

	public Box(){
		this(params);
	}

	public Box(double... params) {
		super(params);
		
		double[] fermi1Params = {params[0], params[1], params[4], 0.0};
		double[] fermi2Params = {params[2], params[3], params[4], 0.0};
		
		fermi1 = new Fermi(fermi1Params);
		fermi2 = new Fermi(fermi2Params);
		
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	public Box(IParameter[] params) {
		super(params);
		
		IParameter[] fermi1Params = {getParameter(0), getParameter(1), getParameter(4), new Parameter(0.0)};
		IParameter[] fermi2Params = {getParameter(2), getParameter(3), getParameter(4), new Parameter(0.0)};
		
		fermi1 = new Fermi(fermi1Params);
		fermi2 = new Fermi(fermi2Params);
		
		name = cname;
		description = cdescription;
		for(int i =0; i<paramNames.length;i++)
			setParameterName(paramNames[i], i);
	}

	
	private void calcCachedParameters() {
		mu1 = getParameterValue(0);
		kT1 = getParameterValue(1);
		mu2 = getParameterValue(2);
		kT2 = getParameterValue(3);
		scale = getParameterValue(4);
    
		fermi1 = new Fermi(new double[] {mu1, kT1, scale, 0.0});
		fermi2 = new Fermi(new double[] {mu2, kT2, scale, 0.0});
		
		markParametersClean();
	}

	
	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];
		
		double valFermi1 = fermi1.val(position);
		double valFermi2 = fermi2.val(position);
		
		return valFermi2 - valFermi1;
	}

}
