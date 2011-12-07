/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting.functions;


/**
 * Class that wrappers the box function calculated as a difference of two Fermi functions
 * y(x) = Fermi(mu1, kT1, scale) - Fermi(mu2,kT2, scale)
 */
public class Box extends AFunction {

	private static String cname = "Box";

	private double mu1, kT1, mu2, kT2, scale;
	private Fermi fermi1, fermi2; 
	
	public Box(double... params) {
		super(params);
		
		double[] fermi1Params = {params[0], params[1], params[4], 0.0};
		double[] fermi2Params = {params[2], params[3], params[4], 0.0};
		
		fermi1 = new Fermi(fermi1Params);
		fermi2 = new Fermi(fermi2Params);
		
		name = cname;
	}

	public Box(IParameter[] params) {
		super(params);
		
		IParameter[] fermi1Params = {getParameter(0), getParameter(1), getParameter(4), new Parameter(0.0)};
		IParameter[] fermi2Params = {getParameter(2), getParameter(3), getParameter(4), new Parameter(0.0)};
		
		fermi1 = new Fermi(fermi1Params);
		fermi2 = new Fermi(fermi2Params);
		
		name = cname;
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
