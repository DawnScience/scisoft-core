package org.eclipse.dawnsci.nexus.appender;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.INexusDevice;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.context.NexusContextFactory;

/**
 * An appender that uses a {@link NexusContext}. This class both implements {@link INexusFileAppender}
 * and extends {@link NexusObjectAppender}. It overrides {@link INexusFileAppender#append(NexusFile, GroupNode)}
 * to create an on-disk {@link NexusContext} with which it then calls {@link #append(GroupNode, NexusContext)}.
 * It overrides {@link NexusObjectAppender#appendNexusObject(NXobject)} to create an in-memory {@link NexusContext}
 * with which it then also calls {@link #append(GroupNode, NexusContext)}. This means that by creating a
 * subclass of this class an implementing {@link #append(GroupNode, NexusContext)}, to use the {@link NexusContext}
 * to make changes to the nexus tree, instances of that subclass can be used both when an {@link INexusFileAppender}
 * is required, and when an {@link INexusDevice} is required.  
 * 
 * @author Matthew Dickie
 */
public abstract class AbstractNexusContextAppender<N extends NXobject> extends NexusObjectAppender<N> implements INexusFileAppender, INexusContextAppender {

	public AbstractNexusContextAppender() {
		// no-arg constructor for spring instantation
	}
	
	public AbstractNexusContextAppender(String name) {
		setName(name);
	}
	
	protected final void appendNexusObject(N nexusObject) throws NexusException {
		// make final to ensure that implementations only override the append method that takes the context 
		append(nexusObject, NexusContextFactory.createLocalNodeInMemoryContext(nexusObject));
	}
	
	public void append(GroupNode groupNode) throws NexusException {
		append(groupNode, NexusContextFactory.createLocalNodeInMemoryContext(groupNode));
	}

	@Override
	public final void append(NexusFile nexusFile, GroupNode groupNode) throws NexusException {
		// make final to ensure that implementations only override the append method that takes the context 
		append(groupNode, NexusContextFactory.createLocalOnDiskContext(nexusFile, groupNode));
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

	@Override
	public void register() {
		super.register(); // why is INexusDevice.super.register() not permitted?
		INexusFileAppender.super.register();
	}
	
}
