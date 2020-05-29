package org.eclipse.dawnsci.nexus.appender;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.context.NexusContextFactory;

/**
 * An appender that uses a {@link NexusContext}. The use of a {@link NexusContext} allows implementations
 * of this class to write appenders that work both in-memory and on-disk.
 * 
 * @author Matthew Dickie
 */
public abstract class AbstractNexusContextAppender<N extends NXobject> extends NexusObjectAppender<N> implements INexusFileAppender, INexusContextAppender {

	protected final void appendNexusObject(N nexusObject) throws NexusException {
		// make final to ensure that implementations only override the append method that takes the context 
		append(nexusObject, NexusContextFactory.createLocalNodeInMemoryContext(nexusObject));
	}
	
	@Override
	public final void append(NexusFile nexusFile, GroupNode groupNode) throws NexusException {
		// make final to ensure that implementations only override the append method that takes the context 
		append(groupNode, NexusContextFactory.createOnDiskContext(nexusFile)); 
	}

	/**
	 * Override this method to use the given context to append to the given group node.
	 * 
	 * @param groupNode groupNode to append to
	 * @param context a context, encapsulating whether changes are being made on disk or in memory.
	 * @see INexusContextAppender#append(GroupNode, NexusContext)
	 */
	@Override
	public abstract void append(GroupNode groupNode, NexusContext context) throws NexusException;
	
}
