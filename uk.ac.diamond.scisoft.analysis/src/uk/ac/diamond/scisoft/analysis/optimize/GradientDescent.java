/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;


/**
 * GradientDescent Class
 */
/**
 * Basic implementation of a gradient Descent optimiser
 * only slight change from normal is that this is damped 
 * so that it deals with sloppy surfaces more effectively.
 */
public class GradientDescent extends AbstractOptimizer {

	/**
	 * Setup the logging facilities
	 */
//	private static final Logger logger = LoggerFactory.getLogger(GradientDescent.class);

	double qualityFactor = 0.1;

	public GradientDescent() {
	}

	/**
	 * @param quality
	 */
	public GradientDescent(double quality) {
		setAccuracy(quality);
	}

	public void setAccuracy(double quality) {
		qualityFactor = quality;
	}

	@Override
	void internalOptimize() {
		double[] bestParams = optimise(getParameterValues(), qualityFactor);
		function.setParameterValues(bestParams);
	}

	private double diffsize = 0.001;
	private double stepsize = 0.1;
	
	/**
	 * The main optimisation method
	 * 
	 * @param parameters
	 * @param finishCriteria
	 * @return a double array of the parameters for the optimisation
	 */
	public double[] optimise(double[] parameters,
			double finishCriteria) {

		double[] solution = parameters;
		double[] stepweight = solution.clone();
		for(int i =0; i < stepweight.length; i++) {
			stepweight[i] = 1.0;
		}
		
		double[] oldd = deriv(solution);
		
		while (stepsize > finishCriteria*0.1) {

			double[] d = deriv(solution);
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
			double value = calculateResidual(solution);

			double[] test = solution.clone();
			
			for(int i = 0; i < d.length; i++) {
				test[i] -= d[i]*stepsize*stepweight[i];
			}
			double testValue = calculateResidual(test);
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
	 * @param position
	 * @return the normalised derivative
	 */
	private double[] deriv(double[] position) {
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
				double posvalue = calculateResidual(pos);
				double negvalue = calculateResidual(neg);
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
