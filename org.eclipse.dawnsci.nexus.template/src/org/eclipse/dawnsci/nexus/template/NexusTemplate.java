package org.eclipse.dawnsci.nexus.template;

import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusException;

/**
 * TODO javadoc
 * 
 * @author wgp76868
 *
 */
public interface NexusTemplate {
	
	public void apply(NXroot nexusRoot) throws NexusException;
	
	public void apply(String nexusFilePath) throws NexusException;
	
}
