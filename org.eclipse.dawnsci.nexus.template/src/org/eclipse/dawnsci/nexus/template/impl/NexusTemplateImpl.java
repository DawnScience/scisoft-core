package org.eclipse.dawnsci.nexus.template.impl;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.context.NexusContextFactory;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of a NexusTemplate. Essentially this just holds the YAML content as a {@link Map}. 
 * @author Matthew Dickie
 */
public class NexusTemplateImpl implements NexusTemplate {

	private static final Logger logger = LoggerFactory.getLogger(NexusTemplateImpl.class);
	
	/**
	 * The YAML as a {@link Map} from {@link String} property names to {@link Object} values,
	 * where the values may either a child mapping, if the value is a {@link Map}, or the
	 * value of that property whether as a scalar or some collection type.
	 */
	private final Map<String, Object> yamlMapping;
	
	private final String templateName;
	
	NexusTemplateImpl(String templateName, Map<String, Object> yamlMapping) {
		this.templateName = templateName;
		this.yamlMapping = yamlMapping;
	}
	
	protected Map<String, Object> getMapping() {
		return yamlMapping;
	}
	
	@Override
	public void apply(Tree tree) throws NexusException {
		logger.debug("Applying template {} to in-memory nexus tree", templateName);
		final NexusContext nexusContext = NexusContextFactory.createInMemoryContext(tree);
		applyTemplate(nexusContext);
	}
	
	@Override
	public GroupNode createNew() throws NexusException {
		logger.debug("Creating new nexus object from template {}", templateName);
		final NexusContext nexusContext = NexusContextFactory.createNewLocalNodeContext();
		applyTemplate(nexusContext);
		return nexusContext.getNexusRoot();
	}
	
	@Override
	public void apply(GroupNode node) throws NexusException {
		logger.debug("Applying template {} to nexus group", templateName);
		final NexusContext nexusContext = NexusContextFactory.createLocalNodeInMemoryContext(node);
		applyTemplate(nexusContext);
	}

	@Override
	public void apply(String nexusFilePath) throws NexusException {
		logger.debug("Applying template {} to nexus file {}", templateName, nexusFilePath);
		try (NexusFile nexusFile = ServiceHolder.getNexusFileFactory().newNexusFile(nexusFilePath)) {
			nexusFile.openToWrite(false);
			applyToNexusFile(nexusFile);
		}
	}

	@Override
	public void apply(NexusFile nexusFile) throws NexusException {
		logger.debug("Applying template {} to nexus file {}", templateName, nexusFile.getFilePath());
		applyToNexusFile(nexusFile);
	}

	@Override
	public void apply(NexusFile nexusFile, GroupNode groupNode) throws NexusException {
		logger.debug("Applying template {} to node {} within nexus file {}", templateName,
				nexusFile.getPath(groupNode), nexusFile.getFilePath());
		final NexusContext nexusContext = NexusContextFactory.createLocalOnDiskContext(nexusFile, groupNode);
		applyTemplate(nexusContext);
	}
	
	@Override
	public void apply(NexusContext context) throws NexusException {
		applyTemplate(context);
	}

	private void applyToNexusFile(NexusFile nexusFile) throws NexusException {
		final NexusContext nexusContext = NexusContextFactory.createOnDiskContext(nexusFile);
		applyTemplate(nexusContext);
		nexusFile.flush();
	}
	
	private void applyTemplate(NexusContext nexusContext) throws NexusException {
		final ApplyNexusTemplateTask applyTemplateTask = new ApplyNexusTemplateTask(this, nexusContext);
		applyTemplateTask.run();
	}

}
