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

public interface IRectangularROI extends IROI {

	/**
	 * @param i
	 * @return length in given dimension
	 */
	public double getLength(int i);

	/**
	 * @return angle, in radians
	 */
	public double getAngle();

	/**
	 * Add given amounts to lengths
	 * @param dx
	 * @param dy
	 */
	public void addToLengths(double dx, double dy);
	
	/**
	 * End of the rectangle
	 * @return end
	 */
	public double[] getEndPoint();
	
	/**
	 * Sides of the rectangle
	 * @return sides
	 */
	public double[] getLengths();
}
