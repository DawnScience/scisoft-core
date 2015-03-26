/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public abstract class PixelIntegrationModel extends AbstractOperationModel {

	//hint="check to use pixel splitting integration algorithm, unchecked uses non-splitting algorithm"
	@OperationModelField(label = "Pixel splitting")
	private boolean pixelSplitting = false;
	@OperationModelField(min=2,max=1000000, hint="Set number of bins for integration axis, leave blank for maximum pixel distance on detector",label = "Number of bins" )
	private Integer numberOfBins = null;
	
	@OperationModelField(label = "Radial range",hint="Please set two values, start and end, separated by a comma i.e. 2,4.The values should match the axis selected (i.e. q, 2 theta, pixel).If you delete the text, the range is cleared and the whole image used.")
	double[] radialRange = null;
	
	@OperationModelField(label = "Azimuthal range", hint="Please set two values, start and end, separated by a comma i.e. -90,90",min=-180, max=180, unit="°")
	double[] azimuthalRange = null;

	public PixelIntegrationModel() {
		super();
	}

	public boolean isPixelSplitting() {
		return pixelSplitting;
	}

	public Integer getNumberOfBins() {
		return numberOfBins;
	}

	public double[] getRadialRange() {
		return radialRange;
	}

	public double[] getAzimuthalRange() {
		return azimuthalRange;
	}

	public void setPixelSplitting(boolean pixelSplitting) {
		firePropertyChange("pixelSplitting", this.pixelSplitting, this.pixelSplitting = pixelSplitting);
	}

	public void setNumberOfBins(Integer numberOfBins) {
		firePropertyChange("numberOfBins", this.numberOfBins, this.numberOfBins = numberOfBins);
		
	}

	public void setRadialRange(double[] radialRange) {
		firePropertyChange("radialRange", this.radialRange, this.radialRange = radialRange);
	}

	public void setAzimuthalRange(double[] azimuthalRange) {
		firePropertyChange("azimuthalRange", this.azimuthalRange, this.azimuthalRange = azimuthalRange);
	}

}