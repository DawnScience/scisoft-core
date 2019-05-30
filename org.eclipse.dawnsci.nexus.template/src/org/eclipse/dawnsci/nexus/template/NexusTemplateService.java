package org.eclipse.dawnsci.nexus.template;

import org.eclipse.dawnsci.nexus.NexusException;

/**
 * TODO javadoc
 * 
 * @author wgp76868
 *
 */
public interface NexusTemplateService {
	
	public NexusTemplate loadTemplate(String templateFile) throws NexusException;
	
}
