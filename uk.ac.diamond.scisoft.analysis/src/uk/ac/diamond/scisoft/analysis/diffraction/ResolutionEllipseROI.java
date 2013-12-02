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

package uk.ac.diamond.scisoft.analysis.diffraction;

import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;

/**
 *
 */
public class ResolutionEllipseROI extends EllipticalROI {

	double resolution = 0;

	public ResolutionEllipseROI(EllipticalROI eroi, double dSpace) {
		this.setSemiAxes(eroi.getSemiAxes());
		this.setAngle(eroi.getAngle());
		this.setPoint(eroi.getPoint());
		resolution = dSpace;
	}
	
	/**
	 * Set d space in angstrom associated with ellipse
	 * @return resolution
	 */
	public double getResolution() {
		return resolution;
	}

	
	/**
	 * Set d space in angstrom associated with ellipse
	 * @param resolution
	 */
	public void setResolution(double resolution) {
		this.resolution = resolution;
	}
	
}
