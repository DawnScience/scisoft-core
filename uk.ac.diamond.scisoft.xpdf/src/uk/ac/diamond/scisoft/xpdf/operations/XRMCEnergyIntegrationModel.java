package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XRMCEnergyIntegrationModel extends AbstractOperationModel {

	@OperationModelField(label = "Integration limits", description = "Energy bin indices to integrate the energy between", hint = "[start, stop]", enableif = "transmissionCorrection")
	private int[] integrationLimits = null;
	@OperationModelField(label = "Correct detector transmission", description = "Perform the energy-dependent transmission correction before integrating the data")
	private boolean transmissionCorrection = true;
	
	public void setIntegrationLimits(int[] limits) {
		firePropertyChange("integrationLimits", this.integrationLimits, this.integrationLimits = limits);
	}
	public void setTransmissionCorrection(boolean tc) {
		firePropertyChange("transmissionCorrection", this.transmissionCorrection, this.transmissionCorrection = tc);
	}
	
	public int[] getIntegrationLimits() {
		return this.integrationLimits;
	}
	public boolean isTransmissionCorrection() {
		return this.transmissionCorrection;
	}
}
