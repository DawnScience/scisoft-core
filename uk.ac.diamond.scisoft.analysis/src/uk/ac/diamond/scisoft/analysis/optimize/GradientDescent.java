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

package uk.ac.diamond.scisoft.analysis.optimize;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;

/**
 * GradientDescent Class
 */
/**
 * Basic implementation of a gradient Descent optimiser
 * only slight change from normal is that this is damped 
 * so that it deals with sloppy surfaces more effectively.
 */
public class GradientDescent implements IOptimizer {

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
		qualityFactor = quality;
	}

	public void setAccuracy(double quality) {
		qualityFactor = quality;
	}

	private IFunction function = null;
	DoubleDataset[] coordinates;
	DoubleDataset dataValues;

	@Override
	public void optimize(IDataset[] coords, IDataset values, final IFunction function) throws Exception {
		final int numCoords = coords.length;
		coordinates = new DoubleDataset[numCoords];
		for (int i = 0; i < numCoords; i++) {
			coordinates[i] = (DoubleDataset) DatasetUtils.convertToAbstractDataset(coords[i]).cast(AbstractDataset.FLOAT64);
		}

		dataValues = (DoubleDataset) DatasetUtils.convertToAbstractDataset(values).cast(AbstractDataset.FLOAT64);

		this.function = function;

		double[] bestParams = optimise(function.getParameterValues(), qualityFactor);

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
			function.setParameterValues(solution);
			double value = function.residual(true, dataValues, coordinates);

			double[] test = solution.clone();
			
			for(int i = 0; i < d.length; i++) {
				test[i] -= d[i]*stepsize*stepweight[i];
			}
			function.setParameterValues(test);
			double testValue = function.residual(true, dataValues, coordinates);
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
				function.setParameterValues(pos);
				double posvalue = function.residual(true, dataValues, coordinates);
				function.setParameterValues(neg);
				double negvalue = function.residual(true, dataValues, coordinates);
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
