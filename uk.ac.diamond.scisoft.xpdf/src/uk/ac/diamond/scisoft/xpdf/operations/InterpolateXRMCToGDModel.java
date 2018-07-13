package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class InterpolateXRMCToGDModel extends AbstractOperationModel {

	@OperationModelField(label = "δ range", hint = "Range of the δ axis in radians", enableif ="!showGamma && !showDelta")
	private double[] deltaRange = null;
	
	@OperationModelField(label = "γ range", hint = "Range of the γ axis in radians", enableif ="!showGamma && !showDelta")
	private double[] gammaRange = null;
	
	@OperationModelField(label = "Step size", hint = "Size of the (isotropic) step in gamma and delta", enableif ="!showGamma && !showDelta")
	private Double stepSize = null;
	
	@OperationModelField(label = "Show γ", hint = "Display the values of γ", enableif = "!showDelta")
	private boolean showGamma = false;
	
	@OperationModelField(label = "Show δ", hint = "Display the values of δ", enableif = "!showGamma")
	private boolean showDelta = false;
	
	public double[] getDeltaRange() {
		return this.deltaRange;
	}
	
	public void setDeltaRange(double[] deltaRange) {
		firePropertyChange("deltaRange", this.deltaRange, this.deltaRange = deltaRange);
	}

	public double[] getGammaRange() {
		return this.gammaRange;
	}
	
	public void setGammaRange(double[] gammaRange) {
		firePropertyChange("gammaRange", this.gammaRange, this.gammaRange = gammaRange);
	}

	public Double getStepSize() {
		return this.stepSize;
	}
	
	public void setStepSize(Double stepSize) {
		firePropertyChange("stepSize", this.stepSize, this.stepSize = stepSize);
	}
	
	public boolean isShowGamma() {
		return this.showGamma;
	}
	
	public void setShowGamma(boolean showGamma) {
		firePropertyChange("showGamma", this.showGamma, this.showGamma = showGamma);
	}
	
	public boolean isShowDelta() {
		return this.showDelta;
	}
	
	public void setShowDelta(boolean showDelta) {
		firePropertyChange("showDelta", this.showDelta, this.showDelta = showDelta);
	}
	
}
