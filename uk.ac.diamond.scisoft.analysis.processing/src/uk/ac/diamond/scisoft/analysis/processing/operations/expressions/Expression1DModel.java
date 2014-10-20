package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class Expression1DModel extends AbstractOperationModel {
	
	String dataExpression = "dnp:power(data,2)";
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
