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

public class MultiplicativeIntensityCorrectionModel extends AbstractOperationModel {

	@OperationModelField(label = "Apply solid angle correction")
	private boolean applySolidAngleCorrection = false;
	@OperationModelField(label = "Apply polarisation correction")
	private boolean applyPolarisationCorrection = false;
	@OperationModelField(label = "Apply detector transmission correction")
	private boolean applyDetectorTransmissionCorrection = false;
	@OperationModelField(label = "Set polarisation factor")
	private double polarisationFactor = 0.9;
	@OperationModelField(label = "Set polarisation angular offset")
	private double polarisationAngularOffset = 0;
	@OperationModelField(label = "Set transmitted fraction")
	private double transmittedFraction = 0;
	
	public boolean isApplySolidAngleCorrection() {
		return applySolidAngleCorrection;
	}
	public boolean isApplyPolarisationCorrection() {
		return applyPolarisationCorrection;
	}
	public double getPolarisationFactor() {
		return polarisationFactor;
	}
	public double getPolarisationAngularOffset() {
		return polarisationAngularOffset;
	}
	public void setApplySolidAngleCorrection(boolean isApplySolidAngleCorrection) {
		firePropertyChange("applySolidAngleCorrection", this.applySolidAngleCorrection, this.applySolidAngleCorrection = isApplySolidAngleCorrection);
	}
	public void setApplyPolarisationCorrection(boolean isApplyPolarisationCorrection) {
		firePropertyChange("applyPolarisationCorrection", this.applyPolarisationCorrection, this.applyPolarisationCorrection = isApplyPolarisationCorrection);
	}
	public void setPolarisationFactor(double polarisationFactor) {
		firePropertyChange("polarisationFactor", this.polarisationFactor, this.polarisationFactor = polarisationFactor);
	}
	public void setPolarisationAngularOffset(double polarisationAngularOffset) {
		firePropertyChange("polarisationAngularOffset", this.polarisationAngularOffset, this.polarisationAngularOffset = polarisationAngularOffset);
	}
	
	public boolean isApplyDetectorTransmissionCorrection() {
		return applyDetectorTransmissionCorrection;
	}
	public void setAppyDetectorTransmissionCorrection(
			boolean isAppyDetectorTransmissionCorrection) {
		firePropertyChange("applyDetectorTransmissionCorrection", this.applyDetectorTransmissionCorrection, this.applyDetectorTransmissionCorrection = isAppyDetectorTransmissionCorrection);
	}
	public double getTransmittedFraction() {
		return transmittedFraction;
	}
	public void setTransmittedFraction(double transmittedFraction) {
		firePropertyChange("transmittedFraction", this.transmittedFraction, this.transmittedFraction = transmittedFraction);
	}
}
