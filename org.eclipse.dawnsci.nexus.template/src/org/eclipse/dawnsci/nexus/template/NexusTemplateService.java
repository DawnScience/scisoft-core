package org.eclipse.dawnsci.nexus.template;

import java.util.Map;

import org.eclipse.dawnsci.nexus.NexusException;

/**
 * A service load and apply templates to nexus files.
 * 
 * @author Matthew Dickie
 */
public interface NexusTemplateService {
	
	/**
	 * Load a template from the given file path
	 * @param templateFilePath template file path
	 * @return the template
	 * @throws NexusException if the template could not be loaded for any reason
	 */
	public NexusTemplate loadTemplate(String templateFilePath) throws NexusException;

	public NexusTemplate createTemplate(String templateName, Map<String, Object> yamlMapping);
	
}
