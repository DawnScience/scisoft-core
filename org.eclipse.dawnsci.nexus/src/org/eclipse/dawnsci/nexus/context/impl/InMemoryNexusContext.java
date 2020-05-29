package org.eclipse.dawnsci.nexus.context.impl;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.january.dataset.IDataset;

/**
 * A {@link NexusContext} to abstract adding nodes to an in-memory nexus tree. This
 * context should be used when we have access to the whole nexus tree. Unlike {@link LocalInMemoryNexusContext}
 * it allows the creation of hard links to any existing node in the nexus tree. It also allows
 * copying of group nodes and attribute values from elsewhere in the nexus tree.
 */
public class InMemoryNexusContext extends AbstractInMemoryNexusContext {
	
	/**
	 * Creates a new {@link InMemoryNexusContext} for the given nexus tree.
	 * @param tree the tree object containing the root node of the nexus tree
	 */
	public InMemoryNexusContext(Tree tree) {
		super(tree);
	}
	
	@Override
	public Node getNode(String path) throws NexusException {
		return TreeUtils.getNode(tree, path);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addLink(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, java.lang.String)
	 */
	@Override
	public void createNodeLink(GroupNode parent, String name, String linkPath) throws NexusException {
		logDebug("Linking node '{}' with name '{}' to parent '{}'", linkPath, name, parent);
		final Node node = TreeUtils.getNode(tree, linkPath);
		if (node == null) {
			throw new NexusException("Invalid link path, no such node: " + linkPath);
		} else if (node.isSymbolicNode()) {
			final SymbolicNode existingLinkNode = (SymbolicNode) node;
			final SymbolicNode newLinkNode = NexusNodeFactory.createSymbolicNode(
					existingLinkNode.getSourceURI(), existingLinkNode.getPath());
			parent.addSymbolicNode(name, newLinkNode);
		} else {
			parent.addNode(name, (Node) node);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addAttribute(org.eclipse.dawnsci.analysis.api.tree.Node, java.lang.String, java.lang.Object)
	 */
	@Override
	public void createAttribute(Node parent, String name, Object value) throws NexusException {
		final Attribute attribute = TreeFactory.createAttribute(name, value);
		parent.addAttribute(attribute);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#copyAttribute(org.eclipse.dawnsci.analysis.api.tree.Node, java.lang.String, java.lang.String)
	 */
	@Override
	public void copyAttribute(Node node, String name, String linkPath) throws NexusException {
		logDebug("Copying attribute '{}' with name '{}' to node '{}'", linkPath, name, node); 
		final int index = linkPath.lastIndexOf(Node.SEPARATOR);
		if (index < 3 || index > linkPath.length() - 2) { // shorted path is "/g/@a"
			throw new NexusException("Not a valid attribute path: " + linkPath);
		}
		
		final String nodePath = linkPath.substring(0, index);
		
		// extract the attribute name, note linkPath may optionally end with '@' indicating an attribute
		final String attrSegment = linkPath.substring(index + 1);
		final String attrName = attrSegment.charAt(attrSegment.length() - 1) == '@' ?
				attrSegment.substring(0, attrSegment.length() - 1) : attrSegment;
		
		// get the node that the existing attribute is on
		final Node currentNode = TreeUtils.getNode(tree, nodePath);
		if (currentNode == null) {
			throw new NexusException("No such attribute: " + nodePath);
		}
		
		// get the existing attribute
		final Attribute attribute = currentNode.getAttribute(attrName);
		if (attribute == null) {
			throw new NexusException("No such attribute: " + nodePath);
		}
		
		// create a new attribute with a clone of the existing dataset
		final IDataset data = attribute.getValue();
		final Attribute newAttr = TreeFactory.createAttribute(name, data.clone());
		node.addAttribute(newAttr);
	}
	
}
