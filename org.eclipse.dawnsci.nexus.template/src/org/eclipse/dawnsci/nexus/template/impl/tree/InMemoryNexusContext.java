package org.eclipse.dawnsci.nexus.template.impl.tree;

import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_SUFFIX;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.NexusUtils;
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

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addGroupNode(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, org.eclipse.dawnsci.nexus.NexusBaseClass)
	 */
	@Override
	public GroupNode addGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) {
		logDebug("Creating new group node with name '{}' and nexus class '{}' to parent group '{}'", name, nexusBaseClass, parent);
		NXobject group = NexusNodeFactory.createNXobjectForClass(nexusBaseClass);
		parent.addGroupNode(name, group);
		return group;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addDataNode(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, java.lang.Object)
	 */
	@Override
	public DataNode addDataNode(GroupNode parent, String name, Object value) {
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
	public void addLink(GroupNode parent, String name, String linkPath) throws NexusException {
		logDebug("Linking node or attribute at path '{}' to parent '{}' with name '{}'", linkPath, parent, name); 
		final Object nodeOrAttr = getNodeOrAttribute(linkPath);
		if (nodeOrAttr == null) {
			throw new NexusException("Invalid link path, no such node: " + linkPath);
		} else if (nodeOrAttr instanceof Attribute) {
			logDebug("Copying attribute '{}' with name '{}' to parent '{}'", linkPath, name, parent); 
			final IDataset data = ((Attribute) nodeOrAttr).getValue();
			final Attribute newAttr = TreeFactory.createAttribute(name, data.clone());
			parent.addAttribute(newAttr);
		} else if (((Node) nodeOrAttr).isSymbolicNode()) { // must a Node if not an Attribute
			logDebug("Linking node '{}' with name '{}' to parent '{}'", linkPath, name, parent); 
			final SymbolicNode existingLinkNode = (SymbolicNode) nodeOrAttr;
			final SymbolicNode newLinkNode = NexusNodeFactory.createSymbolicNode(
					existingLinkNode.getSourceURI(), existingLinkNode.getPath());
			parent.addSymbolicNode(name, newLinkNode);
		} else {
			logDebug("Linking node at path '{}' to parent '{}' with name '{}'", linkPath, parent, name); 
			parent.addNode(name, (Node) nodeOrAttr);
		}
	}
	
	private Object getNodeOrAttribute(String linkPath) throws NexusException {
		final String[] pathSegments = linkPath.split(Node.SEPARATOR);
		if (pathSegments.length < 2 || !pathSegments[0].equals("")) {
			// since the link path starts with '/' the first segment will be the empty string
			// there must be at least one other segment
			throw new NexusException("Invalid link path: " + linkPath);
		}
		
		Node node = tree.getGroupNode();
		for (int i = 1; i < pathSegments.length; i++) {
			final String pathSegment = pathSegments[i];
			if (i == pathSegments.length - 1 && pathSegment.endsWith(String.valueOf(ATTRIBUTE_SUFFIX))) {
				// if we're looking for an attribute and this is the last segment use getAttribute instead of getNode
				final String attrName = pathSegment.substring(0, pathSegment.length() - 1);
				return node.getAttribute(attrName);
			} else if (!(node instanceof GroupNode)) {
				throw new NexusException("Invalid link path, no such node: " + linkPath);
			}
			
			// get the next node
			node = ((GroupNode) node).getNode(pathSegments[i]);
		}
		return node;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addAttribute(org.eclipse.dawnsci.analysis.api.tree.Node, java.lang.String, java.lang.Object)
	 */
	@Override
	public void addAttribute(Node parent, String name, Object value) throws NexusException {
		final Attribute attribute = TreeFactory.createAttribute(name, value);
		parent.addAttribute(attribute);
	}
	
}
