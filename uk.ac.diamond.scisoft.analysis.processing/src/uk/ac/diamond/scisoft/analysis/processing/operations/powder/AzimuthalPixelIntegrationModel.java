package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class AzimuthalPixelIntegrationModel extends PixelIntegrationModel {

	@OperationModelField(hint="Set value for X axis after integration", label = "Select X Axis")
	private XAxis axisType = XAxis.Q;
	
	public XAxis getAxisType() {
		return axisType;
	}
	
	public void setAxisType(XAxis axisType) {
		firePropertyChange("axisType", this.axisType, this.axisType = axisType);
	}

}
