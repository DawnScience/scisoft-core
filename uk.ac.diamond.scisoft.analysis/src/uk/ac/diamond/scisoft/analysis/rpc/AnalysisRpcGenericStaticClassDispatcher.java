/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
