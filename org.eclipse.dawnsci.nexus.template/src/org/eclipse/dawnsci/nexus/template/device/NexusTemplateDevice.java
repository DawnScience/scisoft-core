package org.eclipse.dawnsci.nexus.template.device;

import java.util.Map;
import java.util.Objects;

import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusScanInfo;
import org.eclipse.dawnsci.nexus.builder.NexusObjectProvider;
import org.eclipse.dawnsci.nexus.builder.NexusObjectWrapper;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.TemplateServiceHolder;

/**
 * An {@link INexusDevice} that creates a nexus object according to a template. The template to
 * be specified by directly setting a template map using {@link #setTemplateMap(Map)}, or by specifying the
 * path to the template file by using {@link #setTemplateFilePath(String)}. This can be done
 * easily in the spring configuration. 
 * 
 * @author Matthew Dickie
 *
 * @param <N> class of nexus object, a subinterface of {@link NXobject}
 */
public class NexusTemplateDevice<N extends NXobject> implements INexusDevice<N> {

	private String name;
	
	private NexusTemplate nexusTemplate;
	
	private String templateFilePath;
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTemplateMap(Map<String, Object> templateMap) {
		Objects.requireNonNull(name);
		nexusTemplate = TemplateServiceHolder.getNexusTemplateService().createTemplate(name, templateMap);
	}

	@Override
	public NexusObjectProvider<N> getNexusProvider(NexusScanInfo info) throws NexusException {
		if (nexusTemplate == null) {
			Objects.requireNonNull(templateFilePath);
			nexusTemplate = TemplateServiceHolder.getNexusTemplateService().loadTemplate(templateFilePath);
		}
		
		@SuppressWarnings("unchecked")
		final N nexusObject = (N) nexusTemplate.createNew();
		return new NexusObjectWrapper<>(getName(), nexusObject); 
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

}