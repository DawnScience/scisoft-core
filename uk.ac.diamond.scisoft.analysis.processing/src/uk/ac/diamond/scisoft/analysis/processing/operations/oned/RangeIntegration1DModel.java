package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class RangeIntegration1DModel extends AbstractOperationModel {

	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Set integration range",hint="Please set two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] integrationRange = null;
	@OperationModelField(label = "Subtract linear baseline", hint = "Subtract a linear base line from the integrated area")
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
