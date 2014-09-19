/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
