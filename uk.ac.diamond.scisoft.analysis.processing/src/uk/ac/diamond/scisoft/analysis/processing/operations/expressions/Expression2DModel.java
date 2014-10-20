package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

public class Expression2DModel extends Expression1DModel {

	String axisExpressionY = "yaxis - dat:min(yaxis,0)";
	
	public String getAxisExpressionY() {
		return axisExpressionY;
	}
	public void setAxisExpressionY(String axisExpressionY) {
		firePropertyChange("axisExpression", this.axisExpressionY, this.axisExpressionY = axisExpressionY);
	}
	
}
