package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class Expression2DModel extends Expression1DModel {

	@OperationModelField(label = "Y Axis Function",hint = "Function to apply to the y-axis e.g. yaxis - dat:min(yaxis,0)")
	String axisExpressionY = "yaxis - dat:min(yaxis,0)";
	
	public String getAxisExpressionY() {
		return axisExpressionY;
	}
	public void setAxisExpressionY(String axisExpressionY) {
		firePropertyChange("axisExpression", this.axisExpressionY, this.axisExpressionY = axisExpressionY);
	}
	
}
