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

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.DifferentiableMultivariateRealFunction;
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.analysis.MultivariateVectorialFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.general.ConjugateGradientFormula;
import org.apache.commons.math.optimization.general.NonLinearConjugateGradientOptimizer;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;


/**
 * Class which wraps the Apache Commons Conjugate Gradient fitting routine
 * and makes it compatible with the scisoft fitting routines
 */
public class ApacheConjugateGradient implements IOptimizer {

	@Override
	public void optimize(IDataset[] coords, IDataset data, final IFunction function) throws Exception {

		// pull out appropriate data from the inputs in a way that they can be used
		final int numCoords = coords.length;
		final DoubleDataset[] newCoords = new DoubleDataset[numCoords];
		for (int i = 0; i < numCoords; i++) {
			newCoords[i] = (DoubleDataset) DatasetUtils.convertToAbstractDataset(coords[i]).cast(AbstractDataset.FLOAT64);
		}

		final DoubleDataset values = (DoubleDataset) DatasetUtils.convertToAbstractDataset(data).cast(AbstractDataset.FLOAT64);

		NonLinearConjugateGradientOptimizer cg = new NonLinearConjugateGradientOptimizer(ConjugateGradientFormula.POLAK_RIBIERE);

		//FIXME - this does not work very well for many cases.
		DifferentiableMultivariateRealFunction f = new DifferentiableMultivariateRealFunction() {

			@Override
			public double value(double[] arg0) throws FunctionEvaluationException, IllegalArgumentException {
				function.setParameterValues(arg0);
				return function.residual(true, values, newCoords);

			}

			@Override
			public MultivariateRealFunction partialDerivative(final int parameter) {
				
				MultivariateRealFunction result = new MultivariateRealFunction() {
					
					@Override
					public double value(double[] parameters) throws FunctionEvaluationException, IllegalArgumentException {
						double step = 0.1;
						parameters[parameter] -= step;
						function.setParameterValues(parameters);
						double min = function.residual(true, values, newCoords);
						parameters[parameter] += 2.0*step;
						function.setParameterValues(parameters);
						double max = function.residual(true, values, newCoords);
						return -((max-min) / (2.0*step));
					}
				};
				
				return result;
			}

			@Override
			public MultivariateVectorialFunction gradient() {
				
				MultivariateVectorialFunction result = new MultivariateVectorialFunction() {
					
					@Override
					public double[] value(double[] parameters) throws FunctionEvaluationException, IllegalArgumentException {
						double[] result = new double[parameters.length];
						for (int i = 0; i < parameters.length; i++) {
							result[i] = partialDerivative(i).value(parameters);
						}
						return result;
					}
				};
				return result;
			}
		};


		double[] start = function.getParameterValues();


		RealPointValuePair result = cg.optimize(f, GoalType.MINIMIZE, start);
		function.setParameterValues(result.getPoint());
	}

}
