package org.eclipse.dawnsci.nexus.scan;

import org.eclipse.dawnsci.nexus.builder.NexusBuilderFactory;
import org.eclipse.dawnsci.nexus.device.INexusDeviceService;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.eclipse.dawnsci.nexus.validation.NexusValidationService;

public class ServiceHolder {
	
	private static NexusBuilderFactory nexusBuilderFactory;
	
	private static NexusTemplateService templateService;

	private static INexusDeviceService nexusDeviceService;
	
	private static NexusValidationService nexusValidationService;

	public static NexusBuilderFactory getNexusBuilderFactory() {
		return nexusBuilderFactory;
	}
	
	public void setNexusBuilderFactory(NexusBuilderFactory nexusBuilderFactory) {
		ServiceHolder.nexusBuilderFactory = nexusBuilderFactory;
	}
	
	public static INexusDeviceService getNexusDeviceService() {
		return nexusDeviceService;
	}
	
	public void setNexusDeviceService(INexusDeviceService nexusDeviceService) {
		ServiceHolder.nexusDeviceService = nexusDeviceService;
	}
	
	public static NexusTemplateService getTemplateService() {
		return templateService;
	}
	
	public void setTemplateService(NexusTemplateService templateService) {
		ServiceHolder.templateService = templateService;
	}
	
	public static NexusValidationService getNexusValidationService() {
		return nexusValidationService;
	}
	
	public void setNexusValidationService(NexusValidationService nexusValidationService) {
		ServiceHolder.nexusValidationService = nexusValidationService;
	}

}
