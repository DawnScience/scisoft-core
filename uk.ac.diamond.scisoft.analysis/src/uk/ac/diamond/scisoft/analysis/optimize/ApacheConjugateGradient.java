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
