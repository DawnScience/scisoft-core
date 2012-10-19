/*
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

package uk.ac.diamond.scisoft.analysis.roi.data;

import org.eclipse.swt.graphics.RGB;

import uk.ac.diamond.scisoft.analysis.axis.AxisValues;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.roi.GridROI;

/**
 * Class to aggregate information associated with a ROI
 * A GridROI is the same as a RectangularROI, but with grid information
 */
public class GridROIData extends RectangularROIData {

	/**
	 * @param roi
	 * @param data
	 */
	public GridROIData(GridROI roi, AbstractDataset data) {
		super(roi, data);
		plotColourRGB = new RGB(0,0,0);
	}

	/**
	 * @param roi
	 * @param profileData
	 * @param axes
	 * @param profileSum
	 */
	public GridROIData(GridROI roi, AbstractDataset[] profileData, AxisValues[] axes, double profileSum) {
		super(roi, profileData, axes, profileSum);
		plotColourRGB = new RGB(0,0,0);
	}
}
