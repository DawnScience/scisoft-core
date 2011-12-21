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
import org.apache.commons.math.analysis.MultivariateRealFunction;
import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.RealPointValuePair;
import org.apache.commons.math.optimization.direct.NelderMead;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;


/**
 * Class which wraps the Apache Commons Nelder Mead routine
 * and makes it compatible with the scisoft fitting routines
 */
public class ApacheNelderMead implements IOptimizer {

	@Override
	public void optimize(IDataset[] coords, IDataset data, final IFunction function) throws Exception {
		
		// Pull out the data which is required from the inputs
		final int numCoords = coords.length;
		final DoubleDataset[] newCoords = new DoubleDataset[numCoords];
		for (int i = 0; i < numCoords; i++) {
			newCoords[i] = (DoubleDataset) DatasetUtils.convertToAbstractDataset(coords[i]).cast(AbstractDataset.FLOAT64);
		}

		final DoubleDataset values = (DoubleDataset) DatasetUtils.convertToAbstractDataset(data).cast(AbstractDataset.FLOAT64);

		// create an instance of the fitter
		NelderMead nm = new NelderMead();
		
		// provide the fitting function which wrappers all the normal fitting functionality
		MultivariateRealFunction f1 = new MultivariateRealFunction() {
			
			@Override
			public double value(double[] arg0) throws FunctionEvaluationException, IllegalArgumentException {
				function.setParameterValues(arg0);
				return function.residual(true, values, newCoords);
			}
		};
			
		// preform the optimisation
		double[] start = function.getParameterValues();
		
		RealPointValuePair result = nm.optimize(f1, GoalType.MINIMIZE, start);
		
		// set the input functions parameters to be the result before finishing.
		function.setParameterValues(result.getPoint());
		

	}

}
