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

public class NexusTemplateServiceImpl implements NexusTemplateService {

	private final Yaml yaml = new Yaml(); // note should only be used by one thread
	
	@Override
	public NexusTemplate loadTemplate(String templateFilePath) throws NexusException {
		try (Reader reader = Files.newBufferedReader(Paths.get(templateFilePath))) {
			return createTemplate(yaml.load(reader));
		} catch (IOException e) {
			throw new NexusException("Could not read template file " + templateFilePath);
		}
	}
	
	private NexusTemplate createTemplate(Map<String, Object> yamlMapping) {
		return new NexusTemplateImpl(yamlMapping);
	}
	
	public void applyTemplate(String templateFilePath, String sourceFilePath, String destinationFilePath) throws NexusException {
		final NXroot root = loadNexusTree(sourceFilePath);
		
		final NexusTemplate template = loadTemplate(templateFilePath);
		template.apply(root);
//		saveNexusTree(root, destinationFilePath);
	}
	
	public NexusTemplate loadTemplateFromString(String templateString) throws NexusException {
		// load a nexus template directly from a string. method for testing
		return createTemplate(yaml.load(templateString));
	}

	private NXroot loadNexusTree(String filePath) throws NexusException {
		// TODO move this method to NexusUtils
		try (NexusFile nexusFile = ServiceHolder.getNexusFileFactory().newNexusFile(filePath)) {
			nexusFile.openToRead(); 
			TreeFile treeFile = NexusUtils.loadNexusTree(nexusFile);
			return (NXroot) treeFile.getGroupNode();
		}
	}
	
	private void saveNexusTree(NXroot root, String destinationFilePath) throws NexusException {
		// TODO move this method to NexusUtils or remove if not required
		try (NexusFile nexusFile = ServiceHolder.getNexusFileFactory().newNexusFile(destinationFilePath, true)) {
			nexusFile.createAndOpenToWrite();
			nexusFile.addNode("/", root);
			nexusFile.flush();
		}
	}

}
