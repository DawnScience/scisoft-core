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
 * It is necessary to have knowledge of the sample and beam size. 
 */
public class GeometricCorrectionModel extends AbstractOperationModel {

	@OperationModelField(label = "Beam Height", hint="The height of the incoming beam (mm). This should be less than the sample width.", fieldPosition = 1)
	private double beamHeight = 10;
	
	public double getBeamHeight() {
		return beamHeight;
	}
	
	public void setBeamHeight(double beamHeight) {
		firePropertyChange("beamHeight", this.beamHeight, this.beamHeight = beamHeight);
	}
	
	@OperationModelField(label = "Uncertainty in Beam Height", hint="Uncertainity in beam height (mm)", fieldPosition = 2)
	private double beamHeightErr = 0;
	
	public double getBeamHeightErr() {
		return beamHeightErr; 
	}
	
	public void setBeamHeightErr(double beamHeightErr) {
		firePropertyChange("beamHeightErr", this.beamHeightErr, this.beamHeightErr = beamHeightErr);
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
	
	public void setSampleSizeErr(double sampleSizeErr) {
		firePropertyChange("sampleSizeErr", this.sampleSizeErr, this.sampleSizeErr = sampleSizeErr);
	}
}
