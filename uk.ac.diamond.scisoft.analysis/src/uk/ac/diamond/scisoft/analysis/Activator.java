/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	@SuppressWarnings("unused")
	private static Activator     activator;
	private static BundleContext context;
	
	@Override
	public void start(BundleContext c) throws Exception {
		activator = this;
		context = c;
	}

	@Override
	public void stop(BundleContext c) throws Exception {
		activator = null;
		context = c;
	}
	
	public static Object getService(final Class<?> serviceClass) {
		// Important that NPE is thrown if context is null. We must have a context to get other services.
		ServiceReference<?> ref = context.getServiceReference(serviceClass);
		if (ref==null) return null;
		return context.getService(ref);
	}
}
