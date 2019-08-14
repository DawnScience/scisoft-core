package org.eclipse.dawnsci.nexus.template.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.yaml.snakeyaml.Yaml;

/**
 * Implementation of the template service, uses SnakeYaml to read the template file internally.
 * 
 * @author Matthew Dickie
 */
public class NexusTemplateServiceImpl implements NexusTemplateService {

	private final Yaml yaml = new Yaml(); // note should only be used by one thread
	
	@Override
	public synchronized NexusTemplate loadTemplate(String templateFilePath) throws NexusException {
		try (Reader reader = Files.newBufferedReader(Paths.get(templateFilePath))) {
			return createTemplate(yaml.load(reader));
		} catch (IOException e) {
			throw new NexusException("Could not read template file " + templateFilePath);
		}
	}
	
	private NexusTemplate createTemplate(Map<String, Object> yamlMapping) {
		return new NexusTemplateImpl(yamlMapping);
	}
	
	public synchronized NexusTemplate loadTemplateFromString(String templateString) {
		// load a nexus template directly from a string. method for testing
		return createTemplate(yaml.load(templateString));
	}

}
