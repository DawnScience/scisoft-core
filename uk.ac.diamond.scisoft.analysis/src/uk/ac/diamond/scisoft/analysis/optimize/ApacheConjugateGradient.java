/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


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
			newCoords[i] = (DoubleDataset) DatasetUtils.convertToDataset(coords[i]).cast(Dataset.FLOAT64);
		}

		final DoubleDataset values = (DoubleDataset) DatasetUtils.convertToDataset(data).cast(Dataset.FLOAT64);

		NonLinearConjugateGradientOptimizer cg = new NonLinearConjugateGradientOptimizer(ConjugateGradientFormula.POLAK_RIBIERE);

		//FIXME - this does not work very well for many cases.
		DifferentiableMultivariateRealFunction f = new DifferentiableMultivariateRealFunction() {

			@Override
			public double value(double[] arg0) throws FunctionEvaluationException, IllegalArgumentException {
				function.setParameterValues(arg0);
				return function.residual(true, values, null, newCoords);

			}

			@Override
			public MultivariateRealFunction partialDerivative(final int parameter) {
				
				MultivariateRealFunction result = new MultivariateRealFunction() {
					
					@Override
					public double value(double[] parameters) throws FunctionEvaluationException, IllegalArgumentException {
// FIXME!!! 
						double step = 0.1;
						parameters[parameter] -= step;
						function.setParameterValues(parameters);
						double min = function.residual(true, values, null, newCoords);
						parameters[parameter] += 2.0*step;
						function.setParameterValues(parameters);
						double max = function.residual(true, values, null, newCoords);
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
