package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class Expression2DModel extends Expression1DModel {

	@OperationModelField(label = "y axis function", hint = "Use the dnp: and dat: namespaces to access different mathematical functions")
	String axisExpressionY = "yaxis - dat:min(yaxis,0)";
	
	public String getAxisExpressionY() {
		return axisExpressionY;
	}
	public void setAxisExpressionY(String axisExpressionY) {
		firePropertyChange("axisExpression", this.axisExpressionY, this.axisExpressionY = axisExpressionY);
	}
	
}
