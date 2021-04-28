package org.eclipse.dawnsci.nexus.template;

import org.eclipse.dawnsci.nexus.INexusFileFactory;

public class TemplateServiceHolder {

	private static NexusTemplateService nexusTemplateService;
	private static INexusFileFactory nexusFileFactory;

	public static NexusTemplateService getNexusTemplateService() {
		return nexusTemplateService;
	}

	public void setNexusTemplateService(NexusTemplateService nexusTemplateService) {
		TemplateServiceHolder.nexusTemplateService = nexusTemplateService;
	}

	public static INexusFileFactory getNexusFileFactory() {
		return nexusFileFactory;
	}

	public void setNexusFileFactory(INexusFileFactory nexusFileFactory) {
		TemplateServiceHolder.nexusFileFactory = nexusFileFactory;
	}
}
