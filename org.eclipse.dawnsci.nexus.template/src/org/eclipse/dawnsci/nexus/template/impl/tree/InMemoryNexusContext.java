package org.eclipse.dawnsci.nexus.template.impl.tree;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ApplicationMode;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link NexusContext} to abstract adding nodes to an in-memory nexus tree.
 */
public class InMemoryNexusContext implements NexusContext {
	
	private static final Logger logger = LoggerFactory.getLogger(InMemoryNexusContext.class);
	
	private final Tree tree;
	
	public InMemoryNexusContext(Tree tree) {
		this.tree = tree;
	}
	
	@Override
	public ApplicationMode getApplicationMode() {
		return ApplicationMode.IN_MEMORY;
	}
	
	private void logDebug(String pattern, Object... arguments) {
		if (logger.isDebugEnabled()) {
			// replace any nodes with their paths within the nexus file
			// getting the path uses a breadth-first search, so we only do it if we have to
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i] instanceof Node) {
					arguments[i] = TreeUtils.getPath(tree, (Node) arguments[i]);
				}
			}
			logger.debug(pattern, arguments);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#getNexusRoot()
	 */
	@Override
	public GroupNode getNexusRoot() {
		return tree.getGroupNode();
	}

	@Override
	public Node getNode(String path) throws NexusException {
		return TreeUtils.getNode(tree, path);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addGroupNode(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, org.eclipse.dawnsci.nexus.NexusBaseClass)
	 */
	@Override
	public GroupNode createGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) {
		logDebug("Creating new group node with name '{}' and nexus class '{}' to parent group '{}'", name, nexusBaseClass, parent);
		NXobject group = NexusNodeFactory.createNXobjectForClass(nexusBaseClass);
		parent.addGroupNode(name, group);
		return group;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addDataNode(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, java.lang.Object)
	 */
	@Override
	public DataNode createDataNode(GroupNode parent, String name, Object value) {
		logDebug("Creating new data node with name '{}' and value '{}' to parent group '{}'", name, value, parent);
		final DataNode dataNode = NexusNodeFactory.createDataNode();
		parent.addDataNode(name, dataNode);
		dataNode.setDataset(DatasetFactory.createFromObject(value));
		return dataNode;
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
		
		// extract the attribute name
		final String attrSegment = linkPath.substring(index + 1);
		if (!attrSegment.endsWith(String.valueOf(NexusTemplateConstants.ATTRIBUTE_SUFFIX))) {
			throw new NexusException("Not a valid attribute path: " + linkPath);
		}
		final String attrName = attrSegment.substring(0, attrSegment.length() - 1);
		
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
