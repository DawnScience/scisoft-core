package org.eclipse.dawnsci.nexus.appender;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.ServiceHolder;

/**
 * A nexus file appender that appends to a GroupNode in a nexus file. The appender will use
 * {@link NexusFile} to write any changes to the given {@link GroupNode} to disk.
 * Note: the subinterface {@link NexusContextAppender} should be used if it is necessary 
 * 
 * @author Matthew Dickie
 */
public interface INexusFileAppender {
	
	
	
	/**
	 * Returns the name of this appender. This should be the same as the name of the device that
	 * this appender should be used for.
	 * When registered with the {@link INexusFileAppenderService}, this appender will be invoked when
	 * {@link INexusFileAppenderService#appendMetadata(String, NexusFile, GroupNode)}
	 * is called with this device name.
	 * 
	 * @return the name of the device which this is the appender for
	 */
	public String getName();
	
	/**
	 * Appends to the given {@link GroupNode} within the given {@link NexusFile}. The changes made to
	 * the {@link GroupNode} must be made by calling the appropriate method on the given {@link NexusFile},
	 * with the given {@link GroupNode} as an argument,
	 * e.g. {@link NexusFile#createData(GroupNode, org.eclipse.january.dataset.IDataset)}
	 * @param nexusFile nexus file to write to
	 * @param groupNode group node to append
	 * @throws NexusException if the changes could not be made for any reason
	 */
	public void append(NexusFile nexusFile, GroupNode groupNode) throws NexusException;
	
	default void register() {
		ServiceHolder.getNexusFileAppenderService().register(this);
	}
	
}
