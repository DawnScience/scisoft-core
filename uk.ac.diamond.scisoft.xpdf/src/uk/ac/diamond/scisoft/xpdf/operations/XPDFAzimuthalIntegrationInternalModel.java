/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.Arrays;

import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalPixelIntegrationModel;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class XPDFAzimuthalIntegrationInternalModel extends
		AzimuthalPixelIntegrationModel {

	double[] azimuthRange;
	double[] radialRange;
	boolean pixelSplitting;
	Integer nBins;
	
	public XPDFAzimuthalIntegrationInternalModel(double[] azimuthalRange, double[] radialRange, boolean pixelSplitting, Integer nBins) {
		this.azimuthRange = (azimuthalRange != null) ? Arrays.copyOf(azimuthalRange, 2) : null;
		this.radialRange = (radialRange != null) ? Arrays.copyOf(radialRange, 2) : null;
		this.pixelSplitting = pixelSplitting;
		this.nBins = (nBins != null) ? nBins : null;
	}
	
	@Override
	public XAxis getAxisType() {
		return XAxis.Q;
	}
	
	@Override
	public boolean isLogRadial() {
		return false;
	}
	
	@Override
	public boolean isPixelSplitting() {
		return pixelSplitting;
	}
	
	@Override
	public Integer getNumberOfBins() {
		return nBins;
	}

	@Override
	public double[] getRadialRange() {
		return radialRange;
	}

	@Override
	public double[] getAzimuthalRange() {
		return azimuthRange;
	}


}
