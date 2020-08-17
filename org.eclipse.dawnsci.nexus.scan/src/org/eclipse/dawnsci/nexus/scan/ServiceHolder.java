package org.eclipse.dawnsci.nexus.scan;

import org.eclipse.dawnsci.nexus.builder.NexusBuilderFactory;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;

public class ServiceHolder {
	
	private static NexusBuilderFactory nexusBuilderFactory;
	
	public static NexusBuilderFactory getNexusBuilderFactory() {
		return nexusBuilderFactory;
	}
	
	public void setNexusBuilderFactory(NexusBuilderFactory nexusBuilderFactory) {
		ServiceHolder.nexusBuilderFactory = nexusBuilderFactory;
	}
	
	public static INexusDeviceService nexusDeviceService;
	
	public static INexusDeviceService getNexusDeviceService() {
		return nexusDeviceService;
	}
	
	public void setNexusDeviceService(INexusDeviceService nexusDeviceService) {
		ServiceHolder.nexusDeviceService = nexusDeviceService;
	}
	
	private static NexusTemplateService templateService;
	
	public static NexusTemplateService getTemplateService() {
		return templateService;
	}
	
	public void setTemplateService(NexusTemplateService templateService) {
		ServiceHolder.templateService = templateService;
	}

}
