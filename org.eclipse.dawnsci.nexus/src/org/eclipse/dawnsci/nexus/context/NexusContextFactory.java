package org.eclipse.dawnsci.nexus.context;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.context.impl.InMemoryNexusContext;
import org.eclipse.dawnsci.nexus.context.impl.LocalInMemoryNexusContext;
import org.eclipse.dawnsci.nexus.context.impl.LocalOnDiskNexusContext;
import org.eclipse.dawnsci.nexus.context.impl.OnDiskNexusContext;

/**
 * Use this factory class to create a {@link NexusContext}.
 * <p>
 * {@link NexusContext}s are used by the nexus template system and elsewhere to abstract whether changes
 * are being made to a nexus tree in-memory or on-disk, and whether the changes are made to the whole
 * nexus tree or locally to a particular node.
 * 
 * @author Matthew Dickie
 */
public class NexusContextFactory {
	
	private NexusContextFactory() {
		// private constructor to prevent instantiation
	}
	
	public static NexusContext createOnDiskContext(NexusFile nexusFile) {
		return new OnDiskNexusContext(nexusFile);
	}
	
	public static NexusContext createLocalOnDiskContext(NexusFile nexusFile, GroupNode groupNode) {
		return new LocalOnDiskNexusContext(nexusFile, groupNode);
	}

	public static NexusContext createInMemoryContext(Tree tree) {
		return new InMemoryNexusContext(tree);
	}
	
	public static NexusContext createLocalNodeInMemoryContext(GroupNode group) {
		return new LocalInMemoryNexusContext(group);
	}

	public static NexusContext createNewLocalNodeContext() {
		return new LocalInMemoryNexusContext();
	}
	
}
