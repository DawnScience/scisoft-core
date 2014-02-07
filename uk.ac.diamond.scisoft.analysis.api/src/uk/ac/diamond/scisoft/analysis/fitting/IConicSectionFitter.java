/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;


/**
 * Conic section fitter interface
 */
public interface IConicSectionFitter {

	/**
	 * @return array of parameters
	 */
	public double[] getParameters();

	/**
	 * @return root mean square of residuals
	 */
	public double getRMS();

	/**
	 * Fit points given by x, y datasets to a conic section. If no initial parameters are
	 * given, then an algebraic fit is performed then a non-linear least squares fitting routine
	 * is used to provide the best geometric fit.
	 * @param x
	 * @param y
	 * @param init parameters (can be null)
	 */
	public void geometricFit(IDataset x, IDataset y, double[] init);

	/**
	 * Fit points given by x, y datasets to a conic section using an algebraic fit.
	 * @param x
	 * @param y
	 */
	public void algebraicFit(IDataset x, IDataset y);

	/**
	 * Create a function that is used by optimizer with optional
	 * x, y datasets. The datasets can be null if the fitter has already been used.
	 * @param x can be null
	 * @param y can be null
	 * @return function
	 */
	public IConicSectionFitFunction getFitFunction(IDataset x, IDataset y);
}
