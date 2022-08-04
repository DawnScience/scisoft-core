package org.eclipse.dawnsci.nexus.template.device;

import java.util.Map;
import java.util.Objects;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.INexusDevice;
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
 * <p>
 * The template can be specified in one of three ways:
 * <ul>
 *   <li>by directly setting a template map using {@link #setTemplateMap(Map)};</li>
 *   <li>by specifying the path to the template file by using {@link #setTemplateFilePath(String)};</li>
 *   <li>or as a string by using {@link #setTemplateString(String)}.</li>
 * </ul>
 * This can be done easily in the spring configuration. <em>Note: only call one of these set methods should be called</em>.
 * 
 * @author Matthew Dickie
 *
 * @param <N> class of nexus object, a subinterface of {@link NXobject}
 */
public class NexusTemplateAppender<N extends NXobject> extends AbstractNexusContextAppender<N> {

	private NexusTemplate nexusTemplate;
	
	private String templateFilePath;
	
	private String templateString;
	
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
	
	public void setTemplateString(String templateString) {
		this.templateString = templateString;	
	}

	private void checkInitialized() throws NexusException {
		if (nexusTemplate == null) {
			if (templateFilePath != null) {
				nexusTemplate = TemplateServiceHolder.getNexusTemplateService().loadTemplate(templateFilePath);
			} else if (templateString != null) {
				nexusTemplate = TemplateServiceHolder.getNexusTemplateService().createTemplateFromString(templateString);
			} else {
				throw new NexusException("The appender has not been initialized. "
						+ "One of the set methods should be called before using this class");
			}
		}
	}
	
	@Override
	public void append(GroupNode groupNode, NexusContext context) throws NexusException {
		checkInitialized();
		nexusTemplate.apply(context); 
	}
	
}
