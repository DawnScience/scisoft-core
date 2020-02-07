package org.eclipse.dawnsci.nexus.template.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * Implementation of the template service, uses SnakeYaml to read the template file internally.
 * 
 * @author Matthew Dickie
 */
public class NexusTemplateServiceImpl implements NexusTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(NexusTemplateServiceImpl.class);
	
	/**
	 * The SnakeYAML Yaml object, essentially a Facade to the snakeyaml API.
	 */
	private final Yaml yaml = new Yaml(); // note should only be used by one thread
	
	@Override
	public synchronized NexusTemplate loadTemplate(String templateFilePath) throws NexusException {
		final Path filePath = Paths.get(templateFilePath);
		try (Reader reader = Files.newBufferedReader(filePath)) {
			logger.debug("Creating template from file: {}", templateFilePath);
			return createTemplate(filePath.getFileName().toString(), yaml.load(reader));
		} catch (IOException e) {
			throw new NexusException("Could not read template file " + templateFilePath);
		}
	}
	
	@Override
	public NexusTemplate createTemplate(String templateName, Map<String, Object> yamlMapping) {
		return new NexusTemplateImpl(templateName, yamlMapping);
	}
	
	/**
	 * Load a template from a String. This method is used for testing and is not public API.
	 * @param templateString
	 * @return
	 */
	public synchronized NexusTemplate loadTemplateFromString(String templateString) {
		logger.debug("Creating template from string: {}", templateString);
		return createTemplate("<string>", yaml.load(templateString));
	}

}
