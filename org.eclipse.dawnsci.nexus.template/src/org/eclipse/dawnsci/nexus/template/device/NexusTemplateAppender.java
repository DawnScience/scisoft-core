package org.eclipse.dawnsci.nexus.template.device;

import java.util.Map;
import java.util.Objects;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.appender.AbstractNexusContextAppender;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.TemplateServiceHolder;
import org.eclipse.dawnsci.nexus.template.impl.NexusTemplateImpl;

/**
 * An appender that appends to a nexus object according to a template. This can be used
 * to append to nexus objects both in-memory and on-disk.
 * 
 * @author Matthew Dickie
 *
 * @param <N> class of nexus object, a subinterface of {@link NXobject}
 */
public class NexusTemplateAppender<N extends NXobject> extends AbstractNexusContextAppender<N> {

	private NexusTemplate nexusTemplate;
	
	private String templateFilePath;
	
	public NexusTemplateAppender() {
		// no-arg constructor for spring configuration
	}
	
	public NexusTemplateAppender(String name) {
		setName(name);
	}
	
	public void setTemplateMap(Map<String, Object> templateMap) {
		Objects.requireNonNull(getName());
		nexusTemplate = TemplateServiceHolder.getNexusTemplateService().createTemplate(getName(), templateMap);
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath; 
	}
	
	private void checkInitialized() throws NexusException {
		if (nexusTemplate == null) {
			Objects.requireNonNull(templateFilePath);
			nexusTemplate = TemplateServiceHolder.getNexusTemplateService().loadTemplate(templateFilePath);
		}
	}
	
	@Override
	public void append(GroupNode groupNode, NexusContext context) throws NexusException {
		checkInitialized();
		nexusTemplate.apply(context); 
	}
	
}
