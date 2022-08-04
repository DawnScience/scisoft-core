/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.nexus.appender.INexusFileAppenderService;
import org.eclipse.dawnsci.nexus.device.INexusDeviceAdapterFactory;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.validation.NexusValidationService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class ServiceHolder {
	
	private static INexusDeviceService nexusDeviceService;
	
	private static INexusFileFactory nexusFileFactory;
	
	private static INexusFileAppenderService nexusFileAppenderService;
	
	private static INexusDeviceAdapterFactory<?> nexusDeviceAdapterFactory;
	
	private static NexusValidationService nexusValidationService;
	
	public static INexusFileFactory getNexusFileFactory() {
		// default to using the BundleContext if not set and OSGi is running
		// TODO: we should remove ServiceHolders and find another solution, see DAQ-2239
		return nexusFileFactory != null ? nexusFileFactory : getService(INexusFileFactory.class);
	}
	
	public void setNexusFileFactory(INexusFileFactory nexusFileFactory) {
		ServiceHolder.nexusFileFactory = nexusFileFactory;
	}
	
	public static INexusDeviceService getNexusDeviceService() {
		return nexusDeviceService != null ? nexusDeviceService : getService(INexusDeviceService.class);
	}
	
	public void setNexusDeviceService(INexusDeviceService nexusDeviceService) {
		ServiceHolder.nexusDeviceService = nexusDeviceService;
	}

	public static INexusFileAppenderService getNexusFileAppenderService() {
		return nexusFileAppenderService != null ? nexusFileAppenderService : getService(INexusFileAppenderService.class);
	}
	
	public void setNexusFileAppenderService(INexusFileAppenderService nexusFileAppenderService) {
		ServiceHolder.nexusFileAppenderService = nexusFileAppenderService;
	}
	
	public static INexusDeviceAdapterFactory<?> getNexusDeviceAdapterFactory() {
		return nexusDeviceAdapterFactory != null ? nexusDeviceAdapterFactory : getService(INexusDeviceAdapterFactory.class);
	}
	
	public void setNexusDeviceAdapterFactory(INexusDeviceAdapterFactory<?> nexusDeviceAdapterFactory) {
		ServiceHolder.nexusDeviceAdapterFactory = nexusDeviceAdapterFactory;
	}
	
	public static NexusValidationService getNexusValidationService() {
		return nexusValidationService != null ? nexusValidationService : getService(NexusValidationService.class);
	}
	
	public void setNexusValidationService(NexusValidationService nexusValidationService) {
		ServiceHolder.nexusValidationService = nexusValidationService;
	}
	
	private static <T> T getService(Class<T> serviceClass) {
		if (!Platform.isRunning()) return null;
			
		final Bundle bundle = FrameworkUtil.getBundle(ServiceHolder.class);
		if (bundle == null) return null; // OSGi framework may not be running, this is the case for JUnit tests
		final BundleContext context = bundle.getBundleContext();
		final ServiceReference<T> serviceRef = context.getServiceReference(serviceClass);
		if (serviceRef == null) return null;
		return context.getService(serviceRef);
	}
	
}
