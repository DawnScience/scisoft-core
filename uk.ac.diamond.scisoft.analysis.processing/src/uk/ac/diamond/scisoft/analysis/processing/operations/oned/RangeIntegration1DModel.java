package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class RangeIntegration1DModel extends AbstractOperationModel {

	@OperationModelField(label = "Set integration range",hint="Please set two values, start and end.\nThe values should match the axis .\n\nIf you delete the text, the range is cleared and the whole range used.")
	double[] integrationRange = null;
	@OperationModelField(label = "Subtract linear baseline")
	boolean subtractBaseline = false;
	
	public double[] getIntegrationRange() {
		return integrationRange;
	}
	public void setIntegrationRange(double[] integrationRange) {
		firePropertyChange("integrationRange", this.integrationRange, this.integrationRange = integrationRange);
	}
	public boolean isSubtractBaseline() {
		return subtractBaseline;
	}
	public void setSubtractBaseline(boolean subtractBaseline) {
		firePropertyChange("subtractBaseline", this.subtractBaseline, this.subtractBaseline = subtractBaseline);
	}
	
}
