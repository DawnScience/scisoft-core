/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

public class ROIUtils {

	/**
	 * @param roib
	 * @return new list to hold ROIs of same type
	 */
	public static ROIList<?> createNewROIList(ROIBase roib) {
		if (roib instanceof PointROI)
			return new PointROIList();
		else if (roib instanceof PolylineROI)
			return new PolylineROIList();
		else if (roib instanceof PolygonalROI)
			return new PolygonalROIList();
		else if (roib instanceof LinearROI)
			return new LinearROIList();
		else if (roib instanceof RectangularROI)
			return new RectangularROIList();
		else if (roib instanceof SectorROI)
			return new SectorROIList();
		else if (roib instanceof CircularROI)
			return new CircularROIList();
		else if (roib instanceof EllipticalROI)
			return new EllipticalROIList();
		else if (roib instanceof EllipticalFitROI)
			return new EllipticalFitROIList();

		return null;
	}
}
