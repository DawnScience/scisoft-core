/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import java.util.Arrays;

import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.optimization.VectorialPointValuePair;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

public class ApacheLevenbergMarquardt implements IOptimizer {

	@Override
	public void optimize(IDataset[] coords, final IDataset data, final IFunction function) throws Exception {

		// pull out appropriate data from the inputs in a way that they can be used
		final int numCoords = coords.length;
		final DoubleDataset[] newCoords = new DoubleDataset[numCoords];
		for (int i = 0; i < numCoords; i++) {
			newCoords[i] = (DoubleDataset) DatasetUtils.convertToDataset(coords[i]).cast(Dataset.FLOAT64);
		}

		final DoubleDataset values = (DoubleDataset) DatasetUtils.convertToDataset(data).cast(Dataset.FLOAT64);

		LevenbergMarquardtOptimizer cg = new LevenbergMarquardtOptimizer();

		DifferentiableMultivariateVectorialFunction dmvf = new DifferentiableMultivariateVectorialFunction() {
			
			@Override
			public double[] value(double[] arg0) throws FunctionEvaluationException, IllegalArgumentException {
				function.setParameterValues(arg0);
				return ((DoubleDataset)function.calculateValues(newCoords)).getData().clone();
			}
			
			@Override
			public MultivariateMatrixFunction jacobian() {
				return new MultivariateMatrixFunction() {
					
					@Override
					public double[][] value(double[] arg0) throws FunctionEvaluationException, IllegalArgumentException {
						
						int length = values.getData().length;
						double[][] result = new double[length][];
						IParameter[] params = function.getParameters();
						

							for (int j = 0; j< length ;j++) {
								double[] d = new double[arg0.length];
								for (int i = 0; i < arg0.length; i++) {
									d[i] = function.partialDeriv(params[i], newCoords[0].getData()[j]);
								}
								result[j] = d;
							}
							
							
						return result;
					}
				};
			}
		};
		
		double[] start = function.getParameterValues();

		double[] weights = ((double[])values.getBuffer()).clone();
		Arrays.fill(weights, 1);
		
		VectorialPointValuePair result = cg.optimize(dmvf, (double[])values.getBuffer(), weights, start);
		function.setParameterValues(result.getPoint());
	}

}
