package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class Expression1DModel extends AbstractOperationModel {
	
	@OperationModelField(label = "Data Function",hint = "Function to apply to the data e.g. dnp:power(data,2)")
	String dataExpression = "dnp:power(data,2)";
	@OperationModelField(label = "X Axis Function",hint = "Function to apply to the x-axis e.g. xaxis - dat:min(xaxis,0)")
	String axisExpressionX = "xaxis - dat:min(xaxis,0)";
	
	public String getDataExpression() {
		return dataExpression;
	}
	public void setDataExpression(String dataExpression) {
		firePropertyChange("dataExpression", this.dataExpression, this.dataExpression = dataExpression);
	}
	public String getAxisExpressionX() {
		return axisExpressionX;
	}
	public void setAxisExpressionX(String axisExpressionX) {
		firePropertyChange("axisExpression", this.axisExpressionX, this.axisExpressionX = axisExpressionX);
	}
}
