/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.utils;

import gda.analysis.DataSet;
import gda.analysis.functions.IFunction;
import gda.analysis.utils.optimisation.FittingProblemDefinition;
import gda.analysis.utils.optimisation.Neldermead;
import gda.analysis.utils.optimisation.ProblemDefinition;

/**
 * The implementation of the Nelder-Mead optimisation for the fitting routines.
 * 
 * It is variously known as the down-hill simplex or amoeba method
 */
public class NelderMead implements IOptimizer {

	double accuracy = 0.1;

	/**
	 * @param accuracy
	 */
	public NelderMead(double accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public void Optimize(DataSet[] coords, DataSet dataValues, IFunction function) throws Exception {

		ProblemDefinition pd = new FittingProblemDefinition(coords, dataValues, function);

		Neldermead nm = new Neldermead();

		double[] bestParams = nm.optimise(function.getParameterValues(), pd, accuracy);

		function.setParameterValues(bestParams);

	}

}
