package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class CakePixelIntegrationModel extends PixelIntegrationModel {

	@OperationModelField(min=1, hint="Set number of bins for azimuthal axis",label = "Set number of azimuthal bins" )
	private int numberOfBins2ndAxis = 1000;
	@OperationModelField(hint="Set value for X axis after integration", label = "Select X Axis")
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
