package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class CakePixelIntegrationModel extends PixelIntegrationModel {

	private int numberOfBins2ndAxis = 1000;
	private XAxis axisType = XAxis.Q;
	
	public XAxis getAxisType() {
		return axisType;
	}
	
	public void setAxisType(XAxis axisType) {
		firePropertyChange("axisType", this.axisType, this.axisType = axisType);
	}
	
	public int getNumberOfBins2ndAxis() {
		return numberOfBins2ndAxis;
	}

	public void setNumberOfBins2ndAxis(int numberOfBins2ndAxis) {
		firePropertyChange("numberOfBins2ndAxis", this.numberOfBins2ndAxis, this.numberOfBins2ndAxis = numberOfBins2ndAxis);
	}

}
