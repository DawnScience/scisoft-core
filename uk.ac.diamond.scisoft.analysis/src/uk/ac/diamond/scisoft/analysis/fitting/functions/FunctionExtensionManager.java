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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

public class FunctionExtensionManager {
	
	private static final String FITTING_FUNCTIONS_EXTENSIONS = "uk.ac.diamond.scisoft.analysis.fittingfunction";
	
	private static final String ATTR_CLASS = "class"; //$NON-NLS-1$
	private static final String ATTR_ID = "id"; //$NON-NLS-1$
	private static final String ATTR_NAME = "name"; //$NON-NLS-1$
	
	// Map of Function Names to IConfigurationElements
	protected Map<String, IConfigurationElement> functionsMap;
	
	/*
	 * Initialize the map of functions from the 
	 */
	private void initializeFunctionsMap(){
		functionsMap = new HashMap<String, IConfigurationElement>();
		
		IExtensionPoint extensionPoint= Platform.getExtensionRegistry().getExtensionPoint(FITTING_FUNCTIONS_EXTENSIONS);
		IConfigurationElement[] infos= extensionPoint.getConfigurationElements();
		for (int i = 0; i < infos.length; i++) {
			String id = infos[i].getAttribute(ATTR_NAME); //$NON-NLS-1$
			functionsMap.put(id, infos[i]);
		}
	}
	
	
	public static String[] getFittingFunctionNames() {
		final List<String> ret = new ArrayList<String>();
	    final IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor(FITTING_FUNCTIONS_EXTENSIONS);
	    for (final IConfigurationElement e : configs) {
            ret.add(e.getAttribute(ATTR_NAME));
	    }
        return ret.toArray(new String[]{});		
	}
	
	public static IFunction getFittingFunction(String name) throws CoreException{
		final IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor(FITTING_FUNCTIONS_EXTENSIONS);
	    for (final IConfigurationElement e : configs) {
	    	String attribute = e.getAttribute(ATTR_NAME);
	    	Object object;
	    	if (attribute.equals(name)){
				object = e.createExecutableExtension(ATTR_CLASS);
				if (object instanceof IFunction){
					return (IFunction) object;
				}
	    	}
	    }
		 return null;
		
	}
}
