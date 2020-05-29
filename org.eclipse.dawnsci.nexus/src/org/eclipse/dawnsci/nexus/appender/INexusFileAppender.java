package org.eclipse.dawnsci.nexus.appender;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;

/**
 * An nexus file appender that appends to a GroupNode in a nexus file. The appender will use
 * {@link NexusFile} to write any changes to the given {@link GroupNode} to disk. 
 * 
 * @author Matthew Dickie
 */
public interface INexusFileAppender {
	
	public void append(NexusFile nexusFile, GroupNode groupNode) throws NexusException;

}
