/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class FunctionExtensionFactory {

	private static final String FITTING_FUNCTIONS_EXTENSIONS = "uk.ac.diamond.scisoft.analysis.fittingfunction";

	private static final String ATTR_CLASS = "class"; //$NON-NLS-1$
	private static final String ATTR_ID = "id"; //$NON-NLS-1$
	private static final String ATTR_NAME = "name"; //$NON-NLS-1$
	
	private static FunctionExtensionFactory functionExtensionFactory;

	// Map of Function Names to IConfigurationElements
	protected Map<String, IConfigurationElement> functionsMap = new HashMap<>();

	
	
	private FunctionExtensionFactory() {
		final IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor(
				FITTING_FUNCTIONS_EXTENSIONS);
		for (final IConfigurationElement e : configs) {
			functionsMap.put(e.getAttribute(ATTR_NAME), e);
		}
	}
	
	public static synchronized FunctionExtensionFactory getFunctionExtensionFactory() {
		if (functionExtensionFactory == null) {
			functionExtensionFactory = new FunctionExtensionFactory();
		}
		return functionExtensionFactory;
	}

	public String[] getFittingFunctionNames() {
		return functionsMap.keySet().toArray(new String[0]);
	}

	public IFunction getFittingFunction(String name) throws CoreException {
		Object object;
		IConfigurationElement element = functionsMap.get(name);
		object = element.createExecutableExtension(ATTR_CLASS);

		if (object instanceof IFunction) {
			return (IFunction) object;
		}
		return null;

	}
}
