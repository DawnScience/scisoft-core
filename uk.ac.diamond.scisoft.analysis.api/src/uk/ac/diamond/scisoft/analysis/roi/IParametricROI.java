/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi;

/**
 * A ROI whose boundary can be parameterised
 */
public interface IParametricROI extends IOrientableROI {

	/**
	 * Get point on boundary of ROI for given parameter
	 *
	 * @param parameter
	 * @return point coordinates
	 */
	public double[] getPoint(double parameter);

	/**
	 * Calculate parameter values at which ROI may intersect horizontal line of given y
	 *
	 * @param y
	 * @return parameters (can be null for non-intersection, or contain one [NaN in degenerate case] or more values)
	 */
	public double[] getHorizontalIntersectionParameters(double y);

	/**
	 * Calculate parameter values at which ROI may intersect vertical line of given x
	 *
	 * @param x
	 * @return parameters (can be null for non-intersection or degenerate case,
	 * or contain one or more values)
	 */
	public double[] getVerticalIntersectionParameters(double x);
}
