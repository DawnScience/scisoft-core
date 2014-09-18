package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public abstract class PixelIntegrationModel extends AbstractOperationModel {

	@OperationModelField(hint="check to use pixel splitting integration algorithm, unchecked uses non-splitting algorithm", label = "Use pixel splitting")
	private boolean pixelSplitting = false;
	@OperationModelField(min=2, hint="Set number of bins for integration axis",label = "Set number of bins" )
	private int numberOfBins = 1000;
	
	@OperationModelField(label = "Set radial range",hint="Please set two values, start and end.\nThe values should match the axis selected (i.e. q, 2 theta, pixel).\n\nIf you delete the text, the range is cleared and the whole image used.")
	double[] radialRange = null;
	
	@OperationModelField(label = "Set azimuthal range",min=0, max=360, unit="°")
	double[] azimuthalRange = null;

	public PixelIntegrationModel() {
		super();
	}

	public boolean isPixelSplitting() {
		return pixelSplitting;
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

	public void setPixelSplitting(boolean pixelSplitting) {
		firePropertyChange("pixelSplitting", this.pixelSplitting, this.pixelSplitting = pixelSplitting);
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