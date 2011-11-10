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

package gda.analysis.utils.optimisation;


/**
 * Basic implementation of a gradient Descent optimiser
 * only slight change from normal is that this is damped 
 * so that it deals with sloppy surfaces more effectively.
 */
public class GradientDescent {

	private double diffsize = 0.001;
	private double stepsize = 0.1;
	
	/**
	 * The main optimisation method
	 * 
	 * @param parameters
	 * @param problemDefinition
	 * @param finishCriteria
	 * @return a double array of the parameters for the optimisation
	 * @throws Exception 
	 */
	public double[] optimise(double[] parameters,
			ProblemDefinition problemDefinition, double finishCriteria) throws Exception {

		double[] solution = parameters;
		double[] stepweight = solution.clone();
		for(int i =0; i < stepweight.length; i++) {
			stepweight[i] = 1.0;
		}
		
		double[] oldd = deriv(solution, problemDefinition);
		
		while (stepsize > finishCriteria*0.1) {

			double[] d = deriv(solution, problemDefinition);
			// now adjust the stepweights
			for(int i = 0; i < stepweight.length; i++) {
				if(d[i]*oldd[i] < 0) {
					stepweight[i] *= 0.5;
				} else {
					stepweight[i] *= 1.1;
				}
				if(stepweight[i] > 1.0) {
					stepweight[i] = 1.0;
				}
			}
			
			oldd = d;
			
			double value = problemDefinition.eval(solution);
			
			double[] test = solution.clone();
			
			for(int i = 0; i < d.length; i++) {
				test[i] -= d[i]*stepsize*stepweight[i];
			}
			double testValue = problemDefinition.eval(test);
			if(testValue < value) {
				solution = test;
				stepsize *= 1.1;
			} else {
				stepsize *= 0.75;
			}
			
			//System.out.println("Value = "+ value + " :: testValue = " + testValue);
			//System.out.println("Stepsize = " + stepsize +" :: Quality = " + problemDefinition.eval(solution) + " :: params " + solution[0] + " , " + solution [1] + " , " + solution[2] + " , " + solution[3]);
			//System.out.println(problemDefinition.eval(solution) + " , " +solution[0] + " , " + solution [1] + " , " + solution[2]+ " , " + solution[3] );
			//System.out.println(stepsize + " , " +stepweight[0] + " , " + stepweight [1] + " , " + stepweight[2]+ " , " + stepweight[3] );
		}
		
		return solution;
	}
	
	/**
	 * returns the normalised derivative.
	 * @param position
	 * @param problemDefinition
	 * @return the normalised derivative
	 * @throws Exception
	 */
	private double[] deriv(double[] position, ProblemDefinition problemDefinition) throws Exception {
		double[] deriv = position.clone();
		double length = 0.0;
		boolean stepSizeOk = false;
		while (!stepSizeOk) {
			stepSizeOk = true;
			for(int i = 0; i < deriv.length; i++) {
				double[] pos = position.clone();
				double[] neg = position.clone();
				pos[i] += diffsize;
				neg[i] -= diffsize;
				double posvalue = problemDefinition.eval(pos);
				double negvalue = problemDefinition.eval(neg);
				deriv[i] = (posvalue - negvalue)*(2.0*diffsize);
				if (deriv[i] == 0) {
					stepSizeOk=false;
					diffsize *= 1.5;
					break;
				}
				length += deriv[i]*deriv[i];
			}
		}
		length = Math.sqrt(length);
		
		for(int i = 0; i < deriv.length; i++) {
			deriv[i] = deriv[i] / length;
		}
		
		return deriv;
	}
	
}
