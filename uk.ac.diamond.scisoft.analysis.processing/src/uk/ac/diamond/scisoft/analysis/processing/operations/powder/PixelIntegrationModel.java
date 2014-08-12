package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;

public abstract class PixelIntegrationModel extends AbstractOperationModel {

	boolean isPixelSplitting = false;
	int numberOfBins = 1000;
	double[] radialRange = null;
	
	@OperationModelField(min=0, max=360, unit="°")
	double[] azimuthalRange = null;

	public PixelIntegrationModel() {
		super();
	}

	public boolean isPixelSplitting() {
		return isPixelSplitting;
	}

	public int getNumberOfBins() {
		return numberOfBins;
	}

	public double[] getRadialRange() {
		return radialRange;
	}

	public double[] getAzimuthalRange() {
		return azimuthalRange;
	}

	public void setPixelSplitting(boolean isPixelSplitting) {
		firePropertyChange("isPixelSplitting", this.isPixelSplitting, this.isPixelSplitting = isPixelSplitting);
	}

	public void setNumberOfBins(int numberOfBins) {
		firePropertyChange("numberOfBins", this.numberOfBins, this.numberOfBins = numberOfBins);
		
	}

	public void setRadialRange(double[] radialRange) {
		firePropertyChange("radialRange", this.radialRange, this.radialRange = radialRange);
	}

	public void setAzimuthalRange(double[] azimuthalRange) {
		firePropertyChange("azimuthalRange", this.azimuthalRange, this.azimuthalRange = azimuthalRange);
	}

}