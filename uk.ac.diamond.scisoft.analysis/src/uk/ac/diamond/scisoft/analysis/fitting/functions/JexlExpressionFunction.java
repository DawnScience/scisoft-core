/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.expressions.IExpressionEngine;
import org.eclipse.dawnsci.analysis.api.expressions.IExpressionService;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JexlExpressionFunction extends AFunction {
	private static Logger logger = LoggerFactory.getLogger(JexlExpressionFunction.class);
	/**
	 * The name of the required variable (the x in y(x)=...)
	 */
	private static final String X = "x";
	private static final String NAME = "JexlExpressionFunction";
	private static final String DESC = "A function defined by a Jexl expression.";

	/**
	 * Type of errors that {@link JexlExpressionFunction#setExpression(String)}
	 * can raise.
	 * <p>
	 * Apart from {@link #NO_ERROR} all other errors mean that there will be no
	 * parameters reported by {@link IFunction#getParameters()}
	 */
	public static enum JexlExpressionFunctionError {
		/**
		 * There is no error with the current expression. It is ready to be
		 * evaluated.
		 */
		NO_ERROR,
		/**
		 * There is no expression set.
		 */
		NO_EXPRESSION,
		/**
		 * The expression engine failed to load/be created. Catastrophic
		 * failure.
		 */
		NO_ENGINE,

		/**
		 * The expression passed to the expression engine failed. Examine the
		 * underlying exception (caught from calling setExpression)
		 */
		INVALID_EXPRESSION,

		/**
		 * The expression was parsed by the expression engine, but it had no "x"
		 * variable so is invalid.
		 */
		NO_X,
	}

	public static class JexlExpressionFunctionException extends Exception {
		private JexlExpressionFunctionError error;

		private JexlExpressionFunctionException(
				JexlExpressionFunctionError error) {
			super(error.toString());
			this.error = error;
		}

		public JexlExpressionFunctionException(
				JexlExpressionFunctionError error, Exception e) {
			super(error.toString(), e);
			this.error = error;
		}

		/**
		 * Get the Error enum.
		 *
		 * @return the error
		 */
		public JexlExpressionFunctionError getError() {
			return error;
		}
	}

	private String jexlExpression = null;
	private transient IExpressionEngine engine;
	private transient JexlExpressionFunctionError expressionError = JexlExpressionFunctionError.NO_EXPRESSION;
	private transient Map<String, IParameter> beforeParametersMap;
	private transient IExpressionService service;

	public JexlExpressionFunction() {
		super(0);
	}

	public JexlExpressionFunction(IExpressionService service) {
		super(0);
		init(service);
	}


	public JexlExpressionFunction(IExpressionService service,
			String jexlExpression) {
		super(0);
		init(service);
		try {
			setExpression(jexlExpression);
		} catch (JexlExpressionFunctionException e) {
			// The error state is saved in expressionError
		}
	}
	
	public void setService(IExpressionService service) {
		init(service);
	}

	private void init(IExpressionService service) {
		name = NAME;
		description = DESC;

		try {
			this.service = service;
			this.engine = service.getExpressionEngine();

			Map<String, Object> func = engine.getFunctions();
			// add to functions then set back
			func.put("func", JexlFunctionConnector.class);
			engine.setFunctions(func);
		} catch (Exception e) {
			// No engine available
			this.engine = null;
		}
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC);
	}

	/**
	 * Returns the current error state. See {@link JexlExpressionFunctionError}
	 * for details
	 *
	 * @return the error state.
	 */
	public JexlExpressionFunctionError getExpressionError() {
		return expressionError;
	}

	/**
	 * Get the currently in use expression.
	 *
	 * @return the currently in use expression.
	 */
	public String getExpression() {
		return jexlExpression;
	}

	/**
	 * Set a new Jexl expression to use.
	 * <p>
	 * This method attempts to preserve IParameters from the last successful
	 * call to setExpression so that modifying an expression in a UI does not
	 * cause previously set parameters to be lost.
	 *
	 * @param jexlExpression
	 * @throws JexlExpressionFunctionException
	 */
	public void setExpression(String jexlExpression)
			throws JexlExpressionFunctionException {
		if (expressionError == JexlExpressionFunctionError.NO_ERROR) {
			// Save the last working set of parameters before updating
			beforeParametersMap = new HashMap<>();
			IParameter[] beforeParameters = getParameters();
			for (IParameter beforeParam : beforeParameters) {
				beforeParametersMap.put(beforeParam.getName(), beforeParam);
			}
		}

		parameters = new IParameter[0];
		setDirty(true);

		this.jexlExpression = jexlExpression;
		if (engine == null) {
			expressionError = JexlExpressionFunctionError.NO_ENGINE;
			throw new JexlExpressionFunctionException(expressionError);
		}

		if (jexlExpression == null) {
			expressionError = JexlExpressionFunctionError.NO_EXPRESSION;
			throw new JexlExpressionFunctionException(expressionError);
		}

		try {
			engine.createExpression(jexlExpression);
		} catch (Exception e) {
			// save these errors?
			expressionError = JexlExpressionFunctionError.INVALID_EXPRESSION;
			throw new JexlExpressionFunctionException(expressionError, e);
		}

		Collection<String> parameterNames = engine
				.getVariableNamesFromExpression();
		if (!parameterNames.contains(X)) {
			expressionError = JexlExpressionFunctionError.NO_X;
			throw new JexlExpressionFunctionException(expressionError);
		}

		// No error, update parameters
		expressionError = JexlExpressionFunctionError.NO_ERROR;

		List<IParameter> newParamsList = new ArrayList<>();
		for (String paramName : parameterNames) {
			if (X.equals(paramName))
				continue;
			if (beforeParametersMap != null
					&& beforeParametersMap.containsKey(paramName)) {
				newParamsList.add(beforeParametersMap.get(paramName));
			} else {
				Parameter parameter = new Parameter();
				parameter.setName(paramName);
				newParamsList.add(parameter);
			}
		}
		parameters = newParamsList
				.toArray(new IParameter[parameterNames.size() - 1]);

		if (parent != null) {
			parent.updateParameters();
		}
	}

	private void calcCachedParameters() {
		int noOfParameters = getNoOfParameters();
		HashMap<String, Object> jexlLoadedValues = new HashMap<String, Object>(noOfParameters + 1);
		for (int i = 0; i < noOfParameters; i++) {
			IParameter p = getParameter(i);
			jexlLoadedValues.put(p.getName(), p.getValue());
		}
		engine.addLoadedVariables(jexlLoadedValues);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (expressionError != JexlExpressionFunctionError.NO_ERROR) {
			logger.error("There is a problem with the Jexl expression");
			throw new IllegalStateException("There is a problem with the Jexl expression");
		}

		if (isDirty()) {
			calcCachedParameters();
		}

		// TODO handle multivariate functions (i.e. those that have more than one coordinates)
		if (values.length == 0) {
			logger.error("No coordinates given to evaluate in expression");
			throw new IllegalStateException("No coordinates given to evaluate in expression");
		} else if (values.length > 1) {
			logger.warn("More than one dimension in coordinates given but ignored");
		}
		engine.addLoadedVariable(X, values[0]);

		Object ob;
		try {
			ob = engine.evaluate();
		} catch (Exception e) {
			logger.error("Could not evaluate expression");
			throw new IllegalStateException("Could not evaluate expression");
		}
		try {
			return (double) ob;
		} catch (ClassCastException cce) {
			logger.error("Object returned from expression was not a double");
			throw new IllegalStateException("Object returned from expression was not a double");
		}
	}

	private DoubleDataset evaluate(IDataset[] values) {
		// TODO handle multivariate functions (i.e. those that have more than one coordinate dataset)
		if (values.length == 0) {
			logger.error("No coordinates given to evaluate in expression");
			throw new IllegalStateException("No coordinates given to evaluate in expression");
		} else if (values.length > 1) {
			logger.warn("More than one dimension in coordinates given but ignored");
		}
		engine.addLoadedVariable(X, values[0]);
		Object ob;
		try {
			ob = engine.evaluate();
			if (ob instanceof IDataset) {
				return DatasetUtils.cast(DoubleDataset.class, (IDataset) ob);
			}
			logger.error("Object returned from expression was not a dataset");
			throw new IllegalStateException("Object returned from expression was not a dataset");
		} catch (Exception e) {
			logger.error("Could not evaluate expression");
			throw new IllegalStateException("Could not evaluate expression");
		}
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (expressionError != JexlExpressionFunctionError.NO_ERROR) {
			logger.error("There is a problem with the Jexl expression");
			throw new IllegalStateException("There is a problem with the Jexl expression");
		}

		if (isDirty()) {
			calcCachedParameters();
		}

		DoubleDataset ob = evaluate(it.getValues());
		if (ob!= null) {
			data.setSlice(ob);
		}
	}

	@Override
	public int hashCode() {
		return 31 * super.hashCode()
				+ ((jexlExpression == null) ? 0 : jexlExpression.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;
		JexlExpressionFunction other = (JexlExpressionFunction) obj;

		if (jexlExpression == null)
			return other.jexlExpression == null;
		return jexlExpression.equals(other.jexlExpression);
	}

	@Override
	public JexlExpressionFunction copy() {
		IParameter[] localParameters = getParameters();
		JexlExpressionFunction function = new JexlExpressionFunction(service, jexlExpression);

		for (int i = 0; i < localParameters.length; i++) {
			IParameter p = localParameters[i];
			function.parameters[i] = new Parameter(p);
		}
		return function;
	}

	public IExpressionEngine getEngine() {
		return engine;
	}

	@Override
	public boolean isValid() {
		return super.isValid()
				&& getExpressionError() == JexlExpressionFunctionError.NO_ERROR;
	}

	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		int n = getNoOfParameters();
		out.append(String.format("'%s %s' has %d parameters:\n", name,
				getExpression(), n));
		for (int i = 0; i < n; i++) {
			IParameter p = getParameter(i);
			out.append(String.format("%d) %s = %g in range [%g, %g]\n", i,
					p.getName(), p.getValue(), p.getLowerLimit(),
					p.getUpperLimit()));
		}
		return out.toString();
	}
}
