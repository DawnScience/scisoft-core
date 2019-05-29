/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * The model for generally performing geometric corrections on reflectometry measurements.
 * It is necessary to have knowledge of the sample and slit size. 
 */
public class GeometricCorrectionModel extends AbstractOperationModel {

	@OperationModelField(label = "Slit Height", hint="Distance between the pre-sample slits (mm). This should be less than the sample width.", fieldPosition = 1)
	private double slitHeight = 10;
	
	public double getSlitHeight() {
		return slitHeight;
	}
	
	public void setSlitHeight(double slitHeight) {
		firePropertyChange("slitHeight", this.slitHeight, this.slitHeight = slitHeight);
	}
	
	@OperationModelField(label = "Uncertainty in Slit Height", hint="Uncertainity in slit height (mm)", fieldPosition = 2)
	private double slitHeightErr = 0;
	
	public double getSlitHeightErr() {
		return slitHeightErr; 
	}
	
	public void setSlitHeightErr(double slitHeightErr) {
		firePropertyChange("slitHeightErr", this.slitHeightErr, this.slitHeightErr = slitHeightErr);
	}
	
	@OperationModelField(label = "Sample Width", hint="Length of sample in beam direction (mm)", fieldPosition = 3)
	private double sampleSize = 10;
	
	public double getSampleSize() {
		return sampleSize;
	}
	
	public void setSampleSize(double sampleSize) {
		firePropertyChange("sampleSize", this.sampleSize, this.sampleSize = sampleSize);
	}
	
	@OperationModelField(label = "Uncertainty in Sample Width", hint="Uncertainity in sample width (mm)", fieldPosition = 4)
	private double sampleSizeErr = 0;
	
	public double getSampleSizeErr() {
		return sampleSizeErr; 
	}
	
	public void setSampleSizeErr(double slitHeightErr) {
		firePropertyChange("sampleSizeErr", this.sampleSizeErr, this.sampleSizeErr = sampleSizeErr);
	}
}
