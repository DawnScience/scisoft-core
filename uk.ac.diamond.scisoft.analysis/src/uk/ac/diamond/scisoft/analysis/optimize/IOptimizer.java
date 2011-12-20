/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * The interface for the optimizers
 */
public interface IOptimizer {

	/**
	 * The standard optimize function
	 * 
	 * @param coords
	 *            An array of datasets containing the coordinate positions
	 * @param data
	 *            A dataset containing the data to be fitted to
	 * @param function
	 *            A (possibly composite) function to fit
	 * @throws Exception 
	 */
	public void optimize(IDataset[] coords, IDataset data, IFunction function) throws Exception;
}
