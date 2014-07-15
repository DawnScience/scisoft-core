/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.osgi;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import py4j.ClassLoaderService;
import uk.ac.diamond.scisoft.analysis.api.ClassLoaderExtensionPoint;

import com.thoughtworks.xstream.core.util.CompositeClassLoader;

/**
 * Implementation of ClassLoaderService which allows access to split packages
 */
public class ClassLoaderServiceImpl extends AbstractServiceFactory implements ClassLoaderService {

	private CompositeClassLoader loader;

	public ClassLoaderServiceImpl() {
		System.out.println("Starting class loader service");
	}

	private void init() {
		IExtensionRegistry reg = RegistryFactory.getRegistry();
		if (reg == null)
			return; // not running within OSGi?

		loader = new CompositeClassLoader();
		for (IConfigurationElement i : reg.getConfigurationElementsFor(ClassLoaderExtensionPoint.ID)) {
			try {
				Object e = i.createExecutableExtension(ClassLoaderExtensionPoint.ATTR);
				loader.add(e.getClass().getClassLoader());
			} catch (Exception ne) {
				// do nothing
			}
		}
	}

	@Override
	public ClassLoader getClassLoader() {
		if (loader == null) {
			init(); // delay initialization till registry exists
		}
		return loader;
	}

	@Override
	public Object create(@SuppressWarnings("rawtypes") Class serviceInterface, IServiceLocator parentLocator, IServiceLocator locator) {
        if (serviceInterface == ClassLoaderService.class) {
        	return new ClassLoaderServiceImpl();
        }
		return null;
	}
}
