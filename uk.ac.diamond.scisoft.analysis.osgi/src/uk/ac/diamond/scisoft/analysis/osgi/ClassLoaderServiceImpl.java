/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.osgi;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.dawnsci.analysis.api.api.ClassLoaderExtensionPoint;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

import py4j.ClassLoaderService;

import com.thoughtworks.xstream.core.util.CompositeClassLoader;

/**
 * Implementation of ClassLoaderService which allows access to split packages
 */
public class ClassLoaderServiceImpl extends AbstractServiceFactory implements ClassLoaderService {

	private CompositeClassLoader loader;

	static {
		System.out.println("Starting class loader service");
	}
	public ClassLoaderServiceImpl() {
		// Important do nothing here, OSGI may start the service more than once.
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
