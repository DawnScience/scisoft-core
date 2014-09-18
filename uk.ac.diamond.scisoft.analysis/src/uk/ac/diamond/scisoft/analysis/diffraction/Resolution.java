/*
 * Copyright 2011 Diamond Light Source Ltd.
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

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;


/**
 * Utility class to calculate various aspects of resolution from crystallographic images
 */

public class Resolution {

	/**
	 * @deprecated Use {@link DSpacing#dSpacingsFromPixelCoords(DetectorProperties, DiffractionCrystalEnvironment, int...)}
	 * @param peaks
	 * @param detector
	 * @param diffExp
	 * @return array of inter-peak distances (d spacing) in angstroms
	 */
	@Deprecated
	public static double[] peakDistances(int[] peaks, DetectorProperties detector, DiffractionCrystalEnvironment diffExp) {
		return DSpacing.dSpacingsFromPixelCoords(detector, diffExp, peaks);
	}
	
	/**
	 * @deprecated Use {@link DSpacing#dSpacingsFromPixelCoords(DetectorProperties, DiffractionCrystalEnvironment, double...)}
	 * @param peaks
	 * @param detector
	 * @param diffExp
	 * @return array of inter-peak distances (d spacing) in angstroms
	 */
	@Deprecated
	public static double[] peakDistances(double[] peaks, DetectorProperties detector, DiffractionCrystalEnvironment diffExp) {
		return DSpacing.dSpacingsFromPixelCoords(detector, diffExp, peaks);
	}

	/**
	 * @deprecated Use {@link DSpacing#radiusFromDSpacing(DetectorProperties, DiffractionCrystalEnvironment, double)}
	 * 
	 * @param detector
	 * @param difExp
	 * @param d
	 * @return radius on the resolution ring in PIXELS
	 */
	@Deprecated
	public static double circularResolutionRingRadius(DetectorProperties detector, DiffractionCrystalEnvironment difExp, double d) {
		return DSpacing.radiusFromDSpacing(detector, difExp, d);
	}
}
