package uk.ac.diamond.scisoft.analysis.processing.operations.expressions;

import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;

public class ExpressionServiceHolder {

	private static IExpressionService expressionService;

	public static IExpressionService getExpressionService() {
		return expressionService;
	}

	public static void setExpressionService(IExpressionService expressionService) {
		ExpressionServiceHolder.expressionService = expressionService;
	}
}
