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

	@OperationModelField(label = "Solid angle",hint = "Check to apply the solid angle correction to the data")
	private boolean applySolidAngleCorrection = false;
	@OperationModelField(label = "Polarisation",hint = "Check to apply the polarisation correction to the data")
	private boolean applyPolarisationCorrection = false;
	@OperationModelField(label = "Detector transmission",hint = "Check to apply the detector transmission correction to the data")
	private boolean applyDetectorTransmissionCorrection = false;
	@OperationModelField(label = "Polarisation factor",hint = "Polarisation factor value used in the polarisation correction")
	private double polarisationFactor = 0.9;
	@OperationModelField(label = "Polarisation angular offset", hint = "Set the offset of the polarisation relative to the detector")
	private double polarisationAngularOffset = 0;
	@OperationModelField(label = "Detector transmitted fraction", hint = "Set the fraction of radiation transmitted for the detector transmission correction")
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
	public void setApplyDetectorTransmissionCorrection(
			boolean applyDetectorTransmissionCorrection) {
		firePropertyChange("applyDetectorTransmissionCorrection", this.applyDetectorTransmissionCorrection, this.applyDetectorTransmissionCorrection = applyDetectorTransmissionCorrection);
	}
	public double getTransmittedFraction() {
		return transmittedFraction;
	}
	public void setTransmittedFraction(double transmittedFraction) {
		firePropertyChange("transmittedFraction", this.transmittedFraction, this.transmittedFraction = transmittedFraction);
	}
}
