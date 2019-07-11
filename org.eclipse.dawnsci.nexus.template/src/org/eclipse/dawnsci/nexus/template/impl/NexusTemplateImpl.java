package org.eclipse.dawnsci.nexus.template.impl;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.impl.tree.InMemoryNexusContext;
import org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext;
import org.eclipse.dawnsci.nexus.template.impl.tree.OnDiskNexusContext;

class NexusTemplateImpl implements NexusTemplate {

	private final Map<String, Object> yamlMapping;
	
	NexusTemplateImpl(Map<String, Object> yamlMapping) {
		this.yamlMapping = yamlMapping;
	}
	
	protected Map<String, Object> getMapping() {
		return yamlMapping;
	}
	
	@Override
	public void apply(Tree tree) throws NexusException {
		final NexusContext nexusContext = new InMemoryNexusContext(tree);
		applyTemplate(nexusContext);
	}

	@Override
	public void apply(String nexusFilePath) throws NexusException {
		try (NexusFile nexusFile = ServiceHolder.getNexusFileFactory().newNexusFile(nexusFilePath)) {
			nexusFile.openToWrite(false);
			final NexusContext nexusContext = new OnDiskNexusContext(nexusFile);
			applyTemplate(nexusContext);
		}
	}

	private void applyTemplate(NexusContext nexusContext) throws NexusException {
		ApplyNexusTemplateTask applyTemplateTask = new ApplyNexusTemplateTask(this, nexusContext);
		applyTemplateTask.run();
	}

}
