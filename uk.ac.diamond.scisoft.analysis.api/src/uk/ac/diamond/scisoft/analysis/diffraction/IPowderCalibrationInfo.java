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

package uk.ac.diamond.scisoft.analysis.diffraction;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Stores information from a powder calibration run for persistence into a NeXus file
 */
public interface IPowderCalibrationInfo {

	/**
	 * Get the name of the calibration standard used
	 * @return name
	 */
	public String getCalibrantName();

	/**
	 * Get the path to file containing the calibration image
	 * @return imagePath
	 */
	public String getCalibrationImagePath();

	/**
	 * Get the name of the detector calibrated
	 * @return detectorName
	 */
	public String getDetectorName();

	/**
	 * Get a string description of the calibration method
	 * @return method
	 */
	public String getMethodDescription();

	/**
	 * Get the d-space valued of the standard used in calibration
	 * @return dSpace
	 */
	public IDataset getCalibrantDSpaceValues();

	/**
	 * Get the indicies of the d-space values used in the calibration
	 * @return indicies
	 */
	public IDataset getUsedDSpaceIndexValues();
	
	/**
	 * Get the residual from the calibration process
	 * @return residual
	 */
	public double getResidual();
	
	/**
	 * For NXcite class
	 * [0] = description
	 * [1] = doi
	 * [2] = endnote
	 * [3] = bibtex
	 * 
	 * @return citeArray
	 */
	public String[] getCitationInformation();
}
