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

import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
import org.apache.commons.math3.optim.nonlinear.vector.Target;
import org.apache.commons.math3.optim.nonlinear.vector.Weight;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;


/**
 * Conic section fit function which returns the coordinates (interleaved) for the points
 * specified by the geometric parameters and an array of angles
 */
public interface IConicSectionFitFunction extends MultivariateVectorFunction {

	/**
	 * Set points used in fit
	 * @param x
	 * @param y
	 */
	public void setPoints(IDataset x, IDataset y);

	/**
	 * @return array of interleaved coordinates
	 */
	public Target getTarget();

	/**
	 * @return default weights of 1
	 */
	public Weight getWeight();

	/**
	 * Calculate distance squared to nearest point of conic section
	 * @param parameters
	 * @return squared distances
	 * @throws IllegalArgumentException
	 */
	public IDataset calcDistanceSquared(double[] parameters) throws IllegalArgumentException;

	/**
	 * @return a matrix function that calculates the Jacobian
	 */
	public MultivariateMatrixFunction jacobian();
}
