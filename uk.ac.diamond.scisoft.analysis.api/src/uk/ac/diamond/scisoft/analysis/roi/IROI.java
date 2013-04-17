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

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;

/**
 * Region of interest interface
 */
public interface IROI extends Serializable {

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param name
	 */
	public void setName(String name);

	/**
	 * @param point The start (or centre) point to set
	 */
	public void setPoint(double[] point);

	/**
	 * @param point The start (or centre) point to set
	 */
	public void setPoint(int[] point);

	/**
	 * @param x
	 * @param y
	 */
	public void setPoint(int x, int y);

	/**
	 * @param x 
	 * @param y 
	 */
	public void setPoint(double x, double y);

	/**
	 * @return Returns reference to the start (or centre) point
	 */
	public double[] getPointRef();

	/**
	 * @return Returns copy of the start (or centre) point
	 */
	public double[] getPoint();

	/**
	 * @return Returns the start (or centre) point's x value
	 */
	public double getPointX();

	/**
	 * @return Returns the start (or centre) point's y value
	 */
	public double getPointY();

	/**
	 * @return Returns the start (or centre) point
	 */
	public int[] getIntPoint();

	/**
	 * Add an offset to start (or centre) point
	 * 
	 * @param pt
	 */
	public void addPoint(int[] pt);
	/**
	 * To account for a down-sampling of the dataset, change ROI
	 * @param subFactor
	 */
	public void downsample(double subFactor);

	/**
	 * @param require set true if plot required 
	 */
	public void setPlot(boolean require);

	/**
	 * @return true if plot is enabled
	 */
	public boolean isPlot();

	/**
	 * @return a copy of ROI
	 */
	public IROI copy();
}
