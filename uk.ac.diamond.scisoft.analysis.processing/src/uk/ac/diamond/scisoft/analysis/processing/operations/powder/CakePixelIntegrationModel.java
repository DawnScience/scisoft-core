package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

public class CakePixelIntegrationModel extends AzimuthalPixelIntegrationModel {

	int numberOfBins2ndAxis = 1000;

	public int getNumberOfBins2ndAxis() {
		return numberOfBins2ndAxis;
	}

	public void setNumberOfBins2ndAxis(int numberOfBins2ndAxis) {
		firePropertyChange("numberOfBins2ndAxis", this.numberOfBins2ndAxis, this.numberOfBins2ndAxis = numberOfBins2ndAxis);
	}
}
