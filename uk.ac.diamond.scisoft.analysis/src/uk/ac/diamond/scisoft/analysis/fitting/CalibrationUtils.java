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

import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

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
	 * @param peakClass
	 *            The class with which to fit the individual peaks
	 * @param polynomialOrder
	 *            The order of the polynomial with which to fit the mapping process from the old axis to the new
	 * @return The new Axis with the same dimensionality as the original data
	 * @throws Exception 
	 */
	public static Dataset mapAxis(Dataset data, Dataset originalAxis,
			Dataset originalAxisApproximatePeakPositions, Dataset newAxisExactPeakPositions, Class<? extends APeak> peakClass,
			int polynomialOrder) throws Exception {

		Dataset peakPositions = refinePeakPositions(data, originalAxis, originalAxisApproximatePeakPositions,
				peakClass);

		// fit the data with a polynomial
		Polynomial fitResult = Fitter.polyFit(new Dataset[] {peakPositions} ,newAxisExactPeakPositions, 1e-15, polynomialOrder);
		
		// convert the dataset
		Dataset newAxis = fitResult.calculateValues(originalAxis);
		
		return newAxis;
	}

	/**
	 * Takes an approximate set of peak positions and refines them
	 * 
	 * @param originalAxis
	 *            The Axis on which the original data was collected
	 * @param originalAxisApproximatePeakPositions
	 *            The approximate positions where characteristic peaks should appear in the data in the old axis
	 *            coordinates
	 * @param peakClass
	 *            The class with which to fit the individual peaks
	 * @return val
	 */
	public static Dataset refinePeakPositions(Dataset data, Dataset originalAxis,
			Dataset originalAxisApproximatePeakPositions, Class<? extends APeak> peakClass) {
		
		int numPeaks = originalAxisApproximatePeakPositions.getSize();
		
		List<APeak> fitResult = Generic1DFitter.fitPeaks(originalAxis, data, peakClass, numPeaks );
		
		Dataset refinedPositions = selectSpecifiedPeaks(originalAxisApproximatePeakPositions, fitResult);
		
		return refinedPositions;
	}

	/**
	 * Given a list of APeak functions, find the ones which most closely match a set of input positions
	 * @param originalAxisApproximatePeakPositions the positions to match peaks to
	 * @param peakList the list of APeaks containing all the peaks to try to match
	 * @return the closest real peak position matches to the original positions.
	 */
	public static Dataset selectSpecifiedPeaks(Dataset originalAxisApproximatePeakPositions,
			List<APeak> peakList) {
		
		Dataset peakPositions = getPeakList(peakList);
		Dataset resultPositions = new DoubleDataset(originalAxisApproximatePeakPositions.getShape());
		
		for (int i = 0; i < originalAxisApproximatePeakPositions.getSize(); i++) {
			Dataset compare = Maths.subtract(peakPositions, originalAxisApproximatePeakPositions.getDouble(i));
			compare = Maths.abs(compare);
			resultPositions.set(peakPositions.getDouble(compare.minPos()),i);
		}
		
		return resultPositions;
	}

	/**
	 * Gets a dataset of peak positions out of a list of APeaks
	 * @param peakList a list of APeak functions
	 * @return the dataset of peak positions.
	 */
	public static Dataset getPeakList(List<APeak> peakList) {
		int n = peakList.size();
		Dataset peakPositons = new DoubleDataset(n);
		for (int i = 0; i < n; i++) {
			peakPositons.set(peakList.get(i).getPosition(), i);
		}
		return peakPositons;
	}

}
