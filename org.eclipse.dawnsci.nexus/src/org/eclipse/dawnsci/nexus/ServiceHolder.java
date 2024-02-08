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

import org.eclipse.dawnsci.nexus.appender.INexusFileAppenderService;
import org.eclipse.dawnsci.nexus.device.INexusDeviceAdapterFactory;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.validation.NexusValidationService;

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;
import uk.ac.diamond.osgi.services.ServiceProvider;

@Deprecated(since="9.35", forRemoval=true)
public class ServiceHolder {

	private static final DeprecationLogger logger = DeprecationLogger.getLogger(ServiceHolder.class);

	public static INexusFileFactory getNexusFileFactory() {
		logger.deprecatedMethod("getNexusFileFactory()", "9.35",
			  "ServiceProvider.getService(INexusFileFactory.class)");
		return ServiceProvider.getService(INexusFileFactory.class);
	}

	public void setNexusFileFactory(INexusFileFactory nexusFileFactory) {
		logger.deprecatedMethod("setNexusFileFactory()", "9.35", "Nothing, just remove");
	}

	public static INexusDeviceService getNexusDeviceService() {
		logger.deprecatedMethod("getNexusDeviceService()", "9.35",
			  "ServiceProvider.getService(INexusDeviceService.class)");
		return ServiceProvider.getService(INexusDeviceService.class);
	}

	public void setNexusDeviceService(INexusDeviceService nexusDeviceService) {
		logger.deprecatedMethod("setNexusDeviceService()", "9.35", "Nothing, just remove");
	}

	public static INexusFileAppenderService getNexusFileAppenderService() {
		logger.deprecatedMethod("getNexusFileAppenderService()", "9.35",
			  "ServiceProvider.getService(INexusFileAppenderService.class)");
		return ServiceProvider.getService(INexusFileAppenderService.class);
	}

	public void setNexusFileAppenderService(INexusFileAppenderService nexusFileAppenderService) {
		logger.deprecatedMethod("setNexusFileAppenderService()", "9.35", "Nothing, just remove");
	}
	
	public static INexusDeviceAdapterFactory<?> getNexusDeviceAdapterFactory() {
		logger.deprecatedMethod("getNexusDeviceAdapterFactory()", "9.35",
			  "ServiceProvider.getService(INexusDeviceAdapterFactory.class)");
		return ServiceProvider.getService(INexusDeviceAdapterFactory.class);
	}

	public void setNexusDeviceAdapterFactory(INexusDeviceAdapterFactory<?> nexusDeviceAdapterFactory) {
		logger.deprecatedMethod("setNexusDeviceAdapterFactory()", "9.35", "Nothing, just remove");
	}
	
	public static NexusValidationService getNexusValidationService() {
		logger.deprecatedMethod("getNexusValidationService()", "9.35",
			  "ServiceProvider.getService(NexusValidationService.class)");
		return ServiceProvider.getService(NexusValidationService.class);
	}

	public void setNexusValidationService(NexusValidationService nexusValidationService) {
		logger.deprecatedMethod("setNexusValidationService()", "9.35", "Nothing, just remove");
	}
}
