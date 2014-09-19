/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Concrete class that filters on static methods and delegates to the instance provided.
 */
public class AnalysisRpcGenericStaticClassDispatcher extends AbstractAnalysisRpcGenericDispatcher {
	
	/**
	 * @see AbstractAnalysisRpcGenericDispatcher#AbstractAnalysisRpcGenericDispatcher(Class)
	 */
	public AnalysisRpcGenericStaticClassDispatcher(Class<?> delegate) {
		super(delegate);
	}

	/**
	 * Filter out non-static methods
	 */
	@Override
	protected boolean isMethodOk(Method method, String dispatchMethodName, Class<?>[] dispatchArgTypes) {
		if (!Modifier.isStatic(method.getModifiers())) {
			return false;
		}

		return super.isMethodOk(method, dispatchMethodName, dispatchArgTypes);
	}

	@Override
	protected Object getInvokeObject() {
		// Always static methods, therefore null
		return null;
	}
}
