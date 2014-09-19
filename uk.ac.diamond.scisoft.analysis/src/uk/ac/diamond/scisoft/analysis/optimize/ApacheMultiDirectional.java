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
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.direct.MultiDirectional;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


/**
 * Class which wraps the Apache Commons Multi Directional fitting routine
 * and makes it compatible with the scisoft fitting routines
 */
public class ApacheMultiDirectional implements IOptimizer {

	@Override
	public void optimize(IDataset[] coords, IDataset data, final IFunction function) throws Exception {
		
		// Pull out the data which is required from the inputs
		final int numCoords = coords.length;
		final DoubleDataset[] newCoords = new DoubleDataset[numCoords];
		for (int i = 0; i < numCoords; i++) {
			newCoords[i] = (DoubleDataset) DatasetUtils.convertToDataset(coords[i]).cast(Dataset.FLOAT64);
		}

		final DoubleDataset values = (DoubleDataset) DatasetUtils.convertToDataset(data).cast(Dataset.FLOAT64);

		// create an instance of the fitter
		MultiDirectional md = new MultiDirectional();
		
		// provide the fitting function which wrappers all the normal fitting functionality
		MultivariateRealFunction f1 = new MultivariateRealFunction() {
			
			@Override
			public double value(double[] arg0) throws FunctionEvaluationException, IllegalArgumentException {
				function.setParameterValues(arg0);
				return function.residual(true, values, null, newCoords);
			}
		};
			
		double[] start = function.getParameterValues();
		
		// preform the optimisation
		RealPointValuePair result = md.optimize(f1, GoalType.MINIMIZE, start);
		
		// set the input functions parameters to be the result before finishing.
		function.setParameterValues(result.getPoint());
	
	}

}
