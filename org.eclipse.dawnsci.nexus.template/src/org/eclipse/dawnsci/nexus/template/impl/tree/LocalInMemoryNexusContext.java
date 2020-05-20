package org.eclipse.dawnsci.nexus.template.impl.tree;

import java.net.URI;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;

/**
 * A {@link NexusContext} for making changes to a local tree. As we only have the
 * local sub-tree, this context only supports creating soft links ({@link SymbolicNode}) to
 * other nodes in the tree.
 * 
 * @author Matthew Dickie
 */
public class LocalInMemoryNexusContext extends AbstractInMemoryNexusContext {
	
	/**
	 * Use this context when using the context to create a new node from scratch.
	 */
	public LocalInMemoryNexusContext() {
		super();
	}
	
	/**
	 * Create a new context for applying changes to an existing {@link GroupNode}. 
	 * @param node group node to apply the context to
	 */
	public LocalInMemoryNexusContext(GroupNode node) {
		setRootNode(node);
	}

	@Override
	public void createNodeLink(GroupNode parent, String name, String linkPath) throws NexusException {
		// when creating a link without the overall tree, we can't create a hard link, only create a soft link with a SymbolicNode
		logDebug("Linking node '{}' with name '{}' to parent '{}'", linkPath, name, parent);
		final SymbolicNode symbolicNode = TreeFactory.createSymbolicNode(NexusNodeFactory.getNextOid(), (URI) null, null, linkPath);
		parent.addSymbolicNode(name, symbolicNode);
	}
	
	@Override
	public Node getNode(String path) throws NexusException {
		// we can't get an node by path as we don't have access to the rest of the tree
		throw new UnsupportedOperationException("Cannot get node by path when without when applying template locally");
	}

	@Override
	public void copyAttribute(Node node, String name, String linkPath) throws NexusException {
		// Cannot copy an attribute as we don't have access to the rest of the tree
		throw new UnsupportedOperationException("Cannot get node by path when without when applying template locally");
	}
	
}
