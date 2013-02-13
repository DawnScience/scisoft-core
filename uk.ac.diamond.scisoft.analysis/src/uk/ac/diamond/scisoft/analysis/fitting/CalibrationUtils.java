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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;

public class CalibrationUtils {

	/**
	 * Function to take an axis along with some peaked data with approximately known positions, and re-map it to another
	 * axis with know peak positions
	 * 
	 * @param data
	 *            The actual collected calibration data
	 * @param originalAxis
	 *            The Axis on which the original data was collected
	 * @param originalAxisApproximatePeakPositions
	 *            The approximate positions where characteristic peaks should appear in the data in the old axis
	 *            coordinates
	 * @param newAxisExactPeakPositions
	 *            The exact positions where the peaks should appear on the new Axis
	 * @param peakFunction
	 *            The funtion with which to fit the individual peaks
	 * @param remapFunction
	 *            The function with which to fit the mapping process from the old axis to the new
	 * @return The new Axis with the same dimensionality as the original data
	 */
	public static AbstractDataset mapAxis(AbstractDataset data, AbstractDataset originalAxis,
			double[] originalAxisApproximatePeakPositions, double[] newAxisExactPeakPositions, APeak peakFunction,
			AFunction remapFunction) {

		double[] peakPositions = refinePeakPositions(data, originalAxis, originalAxisApproximatePeakPositions,
				peakFunction);

		// fit
		
		// create Dataset
		
		return originalAxis;
	}

	/**
	 * Takes an approximate set of peak positions and refines them
	 * 
	 * @param originalAxis
	 *            The Axis on which the original data was collected
	 * @param originalAxisApproximatePeakPositions
	 *            The approximate positions where characteristic peaks should appear in the data in the old axis
	 *            coordinates
	 * @param peakFunction
	 *            The funtion with which to fit the individual peaks
	 * @return val
	 */
	private static double[] refinePeakPositions(AbstractDataset data, AbstractDataset originalAxis,
			double[] originalAxisApproximatePeakPositions, APeak peakFunction) {
		// TODO Auto-generated method stub
		return null;
	}

}
