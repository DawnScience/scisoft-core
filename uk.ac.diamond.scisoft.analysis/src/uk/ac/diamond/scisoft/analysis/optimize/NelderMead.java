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

package uk.ac.diamond.scisoft.analysis.optimize;

import gda.analysis.functions.IFunction;
import gda.analysis.utils.optimisation.Neldermead;
import gda.analysis.utils.optimisation.ProblemDefinition;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

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
	public void optimize(IDataset[] coords, IDataset dataValues, final IFunction function) throws Exception {
		final int numCoords = coords.length;
		final DoubleDataset[] newCoords = new DoubleDataset[numCoords];
		for (int i = 0; i < numCoords; i++) {
			newCoords[i] = (DoubleDataset) DatasetUtils.convertToAbstractDataset(coords[i]).cast(AbstractDataset.FLOAT64);
		}

		final DoubleDataset values = (DoubleDataset) DatasetUtils.convertToAbstractDataset(dataValues).cast(AbstractDataset.FLOAT64);

		final int numberParameters = function.getNoOfParameters();
		ProblemDefinition pd = new ProblemDefinition() {

			@Override
			public int getNumberOfParameters() {
				return numberParameters;
			}

			@Override
			public double eval(double[] parameters) throws Exception {
				function.setParameterValues(parameters);
				return function.residual(true, values, newCoords);
			}
		};

		Neldermead nm = new Neldermead();

		double[] bestParams = nm.optimise(function.getParameterValues(), pd, accuracy);

		function.setParameterValues(bestParams);

	}

}
