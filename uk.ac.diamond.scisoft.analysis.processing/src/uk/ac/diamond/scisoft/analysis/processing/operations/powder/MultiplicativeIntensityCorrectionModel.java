package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;

public class MultiplicativeIntensityCorrectionModel extends AbstractOperationModel {

	private boolean applySolidAngleCorrection = false;
	private boolean applyPolarisationCorrection = false;
	private boolean applyDetectorTransmissionCorrection = false;
	private double polarisationFactor = 0.9;
	private double polarisationAngularOffset = 0;
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
