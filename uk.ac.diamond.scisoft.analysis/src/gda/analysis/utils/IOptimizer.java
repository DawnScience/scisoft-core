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

/**
 * The Interface for the Optimizers
 */
public interface IOptimizer {

	/**
	 * The standard optimise function
	 * 
	 * @param coords
	 *            A DataSet containing the coordinate positions
	 * @param yAxis
	 *            A DataSet containing the data to be fitted to
	 * @param function
	 *            The (possibly composite) function which contains the line function to be fitted to.
	 * @throws Exception 
	 */
	public void Optimize(DataSet[] coords, DataSet yAxis, IFunction function) throws Exception;

}
