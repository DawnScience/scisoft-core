package org.eclipse.dawnsci.nexus.appender;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;

/**
 * A service for appending metadata for devices to a nexus file. This service is used when the appenders should
 * write changes directly to disk, rather than an in-memory tree.
 *  
 * @author Matthew Dickie 
 */
public interface INexusFileAppenderService {
	
	/**
	 * Registers the given appender with this service. The appender will be called when
	 * {@link #appendMetadata(String, NexusFile, GroupNode)} is called with the given
	 * device name.  
	 * 
	 * @param appender appender
	 */
	public void register(INexusFileAppender appender);
	
	/**
	 * Append metadata to the given nexus file at the given path, using the appender for the given device name.
	 * 
	 * @param deviceName
	 * @param nexusFile
	 * @param path
	 * @throws NexusException
	 */
	public void appendMetadata(String deviceName, NexusFile nexusFile, String path) throws NexusException;
	
	/**
	 * @param deviceName
	 * @param nexusFile
	 * @param groupNode
	 * @throws NexusException
	 */
	public void appendMetadata(String deviceName, NexusFile nexusFile, GroupNode groupNode) throws NexusException;

}
