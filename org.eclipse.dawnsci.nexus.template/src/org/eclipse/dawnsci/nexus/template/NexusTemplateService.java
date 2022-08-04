package org.eclipse.dawnsci.nexus.template;

import java.util.Map;

import org.eclipse.dawnsci.nexus.NexusException;

/**
 * A service to create {@link NexusTemplate}s. The main way to to this is from a YAML file,
 * using {@link #loadTemplate(String)}. A template can also be created from a YAML string
 * using {@link #loadTemplateFromString(String)}. Additionally 
 * 
 * @author Matthew Dickie
 */
public interface NexusTemplateService {
	
	/**
	 * Loads a template from the given file path. The file must be in YAML format.
	 * @param templateFilePath template file path
	 * @return the template created
	 * @throws NexusException if the template could not be loaded for any reason
	 */
	public NexusTemplate loadTemplate(String templateFilePath) throws NexusException;
	
	/**
	 * Creates a template from the given template string. The string must be in YAML format.
	 * @param templateString the template string to load
	 * @return the template created
	 * @throws NexusException if the template could not be created for any reason
	 */
	public NexusTemplate createTemplateFromString(String templateString) throws NexusException;

	/**
	 * Creates a template from the given template map. The map is a potentially nested map of
	 * template entries - see the template documentation for more details.
	 * @param templateName the name of the template
	 * @param yamlMapping the yaml mapping
	 * @return the template created
	 */
	public NexusTemplate createTemplate(String templateName, Map<String, Object> yamlMapping);
	
}
