package org.eclipse.dawnsci.nexus.template;

public class TemplateServiceHolder {
	
	private static NexusTemplateService nexusTemplateService;
	
	public static NexusTemplateService getNexusTemplateService() {
		return nexusTemplateService;
	}
	
	public void setNexusTemplateService(NexusTemplateService nexusTemplateService) {
		TemplateServiceHolder.nexusTemplateService = nexusTemplateService;
	}

}
