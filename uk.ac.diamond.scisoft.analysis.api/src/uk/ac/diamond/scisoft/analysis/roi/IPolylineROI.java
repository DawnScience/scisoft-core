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

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Interface for a polyline ROI
 */
public interface IPolylineROI extends IROI, Iterable<IROI> {

	/**
	 * @return number of points
	 */
	public int getNumberOfPoints();

	/**
	 * @param i
	 * @return i-th point as point ROI
	 */
	public IROI getPoint(int i);

	/**
	 * @return two datasets with x and y coordinates 
	 */
	public IDataset[] makeCoordinateDatasets();

	/**
	 * Add point to polyline
	 * @param point
	 */
	public void insertPoint(IROI point);

	/**
	 * Set point of polyline at index
	 * @param i index
	 * @param point
	 */
	public void setPoint(int i, IROI point);

	/**
	 * Remove all points
	 */
	public void removeAllPoints();

	@Override
	public IPolylineROI copy();
}
