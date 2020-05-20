package org.eclipse.dawnsci.nexus.template.device;

import java.util.Map;
import java.util.Objects;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.device.NexusObjectAppender;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.TemplateServiceHolder;

/**
 * An {@link INexusDevice} that appends to a nexus object according to a template.
 * This appender can easily be created in spring. To do so, set either the template map (suitable only for very simple templates),
 * or the path of the template file.
 * 
 * @author Matthew Dickie
 *
 * @param <N> class of nexus object, a subinterface of {@link NXobject}.
 */
public class NexusTemplateAppender<N extends NXobject> extends NexusObjectAppender<N> {
	
	private NexusTemplate nexusTemplate;
	
	private String templateFilePath;
	
	public void setTemplateMap(Map<String, Object> templateMap) {
		Objects.requireNonNull(getName());
		nexusTemplate = TemplateServiceHolder.getNexusTemplateService().createTemplate(getName(), templateMap);
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath; 
	}
	
	@Override
	protected void appendNexusObject(N nexusObject) throws NexusException {
		if (nexusTemplate == null) {
			Objects.requireNonNull(templateFilePath);
			nexusTemplate = TemplateServiceHolder.getNexusTemplateService().loadTemplate(templateFilePath);
		}
		
		nexusTemplate.apply(nexusObject);
	}

}
