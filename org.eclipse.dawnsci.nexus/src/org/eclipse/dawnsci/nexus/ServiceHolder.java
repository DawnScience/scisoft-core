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

public class ServiceHolder {
	
	private static INexusDeviceService nexusDeviceService;
	
	private static INexusFileFactory nexusFileFactory;
	
	private static INexusFileAppenderService nexusFileAppenderService;
	
	private static INexusDeviceAdapterFactory<?> nexusDeviceAdapterFactory;
	
	public static INexusFileFactory getNexusFileFactory() {
		return nexusFileFactory;
	}
	
	public void setNexusFileFactory(INexusFileFactory nexusFileFactory) {
		ServiceHolder.nexusFileFactory = nexusFileFactory;
	}
	
	public static INexusDeviceService getNexusDeviceService() {
		return nexusDeviceService;
	}
	
	public void setNexusDeviceService(INexusDeviceService nexusDeviceService) {
		ServiceHolder.nexusDeviceService = nexusDeviceService;
	}

	public static INexusFileAppenderService getNexusFileAppenderService() {
		return nexusFileAppenderService;
	}
	
	public void setNexusFileAppenderService(INexusFileAppenderService nexusFileAppenderService) {
		ServiceHolder.nexusFileAppenderService = nexusFileAppenderService;
	}
	
	public static INexusDeviceAdapterFactory<?> getNexusDeviceAdapterFactory() {
		return nexusDeviceAdapterFactory;
	}
	
	public void setNexusDeviceAdapterFactory(INexusDeviceAdapterFactory<?> nexusDeviceAdapterFactory) {
		ServiceHolder.nexusDeviceAdapterFactory = nexusDeviceAdapterFactory;
	}
	
}
