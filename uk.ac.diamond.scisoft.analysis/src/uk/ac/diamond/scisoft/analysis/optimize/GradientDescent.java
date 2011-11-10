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
import gda.analysis.utils.optimisation.ProblemDefinition;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * GradientDescent Class
 */
public class GradientDescent implements IOptimizer {

	/**
	 * Setup the logging facilities
	 */
//	private static final Logger logger = LoggerFactory.getLogger(GradientDescent.class);

	double qualityFactor = 0.0;

	/**
	 * @param quality
	 */
	public GradientDescent(double quality) {
		qualityFactor = quality;
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

		gda.analysis.utils.optimisation.GradientDescent gd = new gda.analysis.utils.optimisation.GradientDescent();

		double[] bestParams = gd.optimise(function.getParameterValues(), pd, qualityFactor);

		function.setParameterValues(bestParams);

	}

}
