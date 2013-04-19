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

import java.io.Serializable;

/**
 * Class for a polygonal ROI is a closed form of a polyline ROI (end point is implicitly the start point)
 */
public class PolygonalROI extends PolylineROI implements Serializable, Iterable<PointROI> {

	public PolygonalROI() {
		super();
	}

	public PolygonalROI(double[] start) {
		super(start);
	}

	/**
	 * @return number of sides (or points)
	 */
	@Override
	public int getSides() {
		return super.getNumberOfPoints();
	}

	@Override
	public PolygonalROI copy() {
		PolygonalROI c = new PolygonalROI(spt.clone());
		for (int i = 1, imax = pts.size(); i < imax; i++)
			c.insertPoint(pts.get(i).spt.clone());
		c.name = name;
		c.plot = plot;
		return c;
	}
}
