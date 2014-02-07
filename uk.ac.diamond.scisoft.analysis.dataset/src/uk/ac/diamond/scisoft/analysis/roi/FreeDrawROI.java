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
 * Class for a FreeDraw ROI
 */
public class FreeDrawROI extends PolylineROI implements Serializable {

	/**
	 * Zero point line
	 */
	public FreeDrawROI() {
		super();
	}

	public FreeDrawROI(double[] start) {
		super(start);
	}

	public FreeDrawROI(double x, double y) {
		super(x, y);
	}

	@Override
	public String toString() {
		/**
		 * You cannot have pts.toString() if the pts contains this! It
		 * is a recursive call. Fix to defect https://trac/scientific_software/ticket/1943
		 */
		if (pts.contains(this)) return super.toString();
		return pts.toString();
	}
}
