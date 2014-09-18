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

import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateFunctionPenaltyAdapter;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;


/**
 * Class which wraps the Apache Commons Nelder Mead routine
 * and makes it compatible with the scisoft fitting routines
 */
public class ApacheNelderMead implements IOptimizer {

	
	private static final double REL_TOL = 1e-21;
	private static final double ABS_TOL = 1e-21;
	private static final int MAX_EVAL = 100000;
	
	@Override
	public void optimize(IDataset[] coords, IDataset data, final IFunction function) throws Exception {
		optimize(coords, data, function, MAX_EVAL);
	}
		
	public void optimize(IDataset[] coords, IDataset data, final IFunction function, int maxEvals) throws Exception {
		
		// Pull out the data which is required from the inputs
		final int numCoords = coords.length;
		final DoubleDataset[] newCoords = new DoubleDataset[numCoords];
		for (int i = 0; i < numCoords; i++) {
			newCoords[i] = (DoubleDataset) DatasetUtils.convertToDataset(coords[i]).cast(Dataset.FLOAT64);
		}

		final DoubleDataset values = (DoubleDataset) DatasetUtils.convertToDataset(data).cast(Dataset.FLOAT64);
		
		// Get the objective function.
		MultivariateFunctionPenaltyAdapter of = ((AFunction)function).getApacheMultivariateFunction(values, newCoords);
			
		// preform the optimisation
		double[] start = ((AFunction)function).getParameterValuesNoFixed();
		
		MultivariateOptimizer opt = new SimplexOptimizer(REL_TOL,ABS_TOL);
		
		PointValuePair result = opt.optimize(new InitialGuess(start), GoalType.MINIMIZE,
				new ObjectiveFunction(of), new MaxEval(maxEvals),
				new NelderMeadSimplex(start.length));	
		
		double[] results = result.getPoint();
		
		// set the input functions parameters to be the result before finishing.
		((AFunction)function).setParameterValuesNoFixed(results);

	}

}
