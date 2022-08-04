package org.eclipse.dawnsci.nexus.appender;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.context.NexusContext;

/**
 * A {@link INexusContextAppender} is an appender that uses a {@link NexusContext} to makes changes to the
 * a {@link GroupNode}. The {@link NexusContext} encapsulates whether the changes are made in-memory or on-disk.
 * 
 * @author Matthew Dickie
 */
public interface INexusContextAppender {
	
	/**
	 * Appends to the given {@link GroupNode} using the given {@link NexusContext}. Note that the given
	 * context <em>must</em> be used to make any change to the nexus tree, it must not be changed
	 * (i.e. child nodes added) directly.
	 * @param groupNode group node to append to
	 * @param context the {@link NexusContext} to use to make changes to the group
	 * @throws NexusException if the change could not be made for any reason
	 */
	public void append(GroupNode groupNode, NexusContext context) throws NexusException;

}
