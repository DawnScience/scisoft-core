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
import org.yaml.snakeyaml.scanner.ScannerException;

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
	private final Yaml yamlParser = new Yaml(); // note: not thread-safe, must only be called by synchronized methods
	
	private synchronized Map<String, Object> loadTemplate(Reader reader) throws ScannerException {
		return yamlParser.load(reader);
	}
	
	private synchronized Map<String, Object> loadTemplateString(String templateString) throws ScannerException {
		return yamlParser.load(templateString);
	}
	
	@Override
	public NexusTemplate loadTemplate(String templateFilePath) throws NexusException {
		final Path filePath = Paths.get(templateFilePath);
		try (Reader reader = Files.newBufferedReader(filePath)) {
			logger.debug("Creating template from file: {}", templateFilePath);
			return createTemplate(filePath.getFileName().toString(), loadTemplate(reader));
		} catch (ScannerException | IOException e) {
			throw new NexusException("Could not read template file: " + templateFilePath);
		}
	}
	
	@Override
	public NexusTemplate createTemplate(String templateName, Map<String, Object> templateMap) {
		return new NexusTemplateImpl(templateName, templateMap);
	}
	
	@Override
	public NexusTemplate createTemplateFromString(String templateString) throws NexusException {
		try {
			logger.debug("Creating template from string: {}", templateString);
			return createTemplate("<string>", loadTemplateString(templateString));
		} catch (ScannerException e) {
			throw new NexusException("Could not parse template string: " + templateString);
		}
	}

}
