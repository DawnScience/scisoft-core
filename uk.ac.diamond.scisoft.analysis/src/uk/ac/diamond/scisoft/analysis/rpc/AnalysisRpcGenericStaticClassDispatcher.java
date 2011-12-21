/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
