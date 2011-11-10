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

package gda.analysis.utils.optimisation;

import gda.analysis.DataSet;
import gda.analysis.functions.IFunction;

/**
 * This implementation is for fitting 1d data to a composite function
 */
public class FittingProblemDefinition implements ProblemDefinition {

	DataSet[] values;
	DataSet data;
	IFunction function;

	/**
	 * Basic constructor, which requires all the input for the function
	 * @param coords the values of the input variables to the function to be fitted
	 * @param data the data to be fitted
	 * @param function the composite function which will be fitted to the data 
	 */
	public FittingProblemDefinition(DataSet[] coords, DataSet data, IFunction function) {
		this.values = coords;
		this.data = data;
		this.function = function;
	}

	@Override
	public double eval(double[] parameters) {
		function.setParameterValues(parameters);
		return function.residual(true, data, values);
	}

	@Override
	public int getNumberOfParameters() {
		return function.getNoOfParameters();
	}

}
