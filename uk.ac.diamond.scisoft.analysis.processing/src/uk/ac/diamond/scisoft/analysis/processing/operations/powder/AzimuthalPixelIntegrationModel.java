package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class AzimuthalPixelIntegrationModel extends PixelIntegrationModel {

	XAxis axisType = XAxis.Q;
	
	public XAxis getAxisType() {
		return axisType;
	}
	
	public void setAxisType(XAxis axisType) {
		firePropertyChange("axisType", this.axisType, this.axisType = axisType);
	}

}
