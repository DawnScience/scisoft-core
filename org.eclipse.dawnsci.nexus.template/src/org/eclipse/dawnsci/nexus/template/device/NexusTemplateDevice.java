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
 * An {@link INexusDevice} that creates a nexus object according to a template.
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
public class NexusTemplateDevice<N extends NXobject> implements INexusDevice<N> {

	private String name;
	
	private NexusTemplate nexusTemplate;
	
	private String templateFilePath;
	
	private String templateString;
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the template as a map. This can be used for very simple template and can be configured in spring, e.g.
	 * &lt;bean id="templateDevice" class="org.eclipse.dawnsci.nexus.template.device"&gt;
	 * @param templateMap
	 */
	public void setTemplateMap(Map<String, Object> templateMap) {
		Objects.requireNonNull(name);
		nexusTemplate = TemplateServiceHolder.getNexusTemplateService().createTemplate(name, templateMap);
	}
	
	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	public void setTemplateString(String templateString) {
		this.templateString = templateString;
	}

	@Override
	public NexusObjectProvider<N> getNexusProvider(NexusScanInfo info) throws NexusException {
		checkInitialized();
		
		@SuppressWarnings("unchecked")
		final N nexusObject = (N) nexusTemplate.createNew();
		return new NexusObjectWrapper<>(getName(), nexusObject); 
	}

	private void checkInitialized() throws NexusException {
		if (nexusTemplate == null) {
			if (templateFilePath != null) {
				nexusTemplate = TemplateServiceHolder.getNexusTemplateService().loadTemplate(templateFilePath);
			} else if (templateString != null) {
				nexusTemplate = TemplateServiceHolder.getNexusTemplateService().createTemplateFromString(templateString);
			} else {
				throw new NexusException("The template device has not been initialized. "
						+ "Please call one of the set methods before using this object.");
			}
		}
	}

}