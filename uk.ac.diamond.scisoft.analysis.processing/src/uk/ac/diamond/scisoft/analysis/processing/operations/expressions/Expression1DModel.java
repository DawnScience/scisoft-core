package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class Expression1DModel extends AbstractOperationModel {
	
	@OperationModelField(label = "Enter data function")
	String dataExpression = "dnp:power(data,2)";
	@OperationModelField(label = "Enter x axis function")
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
