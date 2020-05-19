package org.eclipse.dawnsci.nexus.template.impl.tree;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ApplicationMode;
import org.eclipse.january.dataset.DatasetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract superclass of in-memory nexus contexts, used to abstract making changes
 * to a nexus tree in-memory. The subclass {@link InMemoryNexusContext} should be used
 * if the whole nexus tree is available, {@link LocalInMemoryNexusContext} should be used
 * when this is not the case. 
 * 
 * @author Matthew Dickie
 */
public abstract class AbstractInMemoryNexusContext implements NexusContext {
	
	private static final Logger logger = LoggerFactory.getLogger(InMemoryNexusContext.class);
	
	protected Tree tree = null;
	
	private GroupNode rootNode = null;
	
	protected AbstractInMemoryNexusContext(Tree tree) {
		this.tree = tree;
		setRootNode(tree.getGroupNode());
	}
	
	public AbstractInMemoryNexusContext() {
		// do nothing, tree and root node not applied yet
	}
	
	@Override
	public ApplicationMode getApplicationMode() {
		return ApplicationMode.IN_MEMORY;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#getNexusRoot()
	 */
	@Override
	public GroupNode getNexusRoot() {
		return rootNode;
	}

	protected void setRootNode(GroupNode rootNode) {
		this.rootNode = rootNode;
		if (tree == null) {
			// create a dummy tree to hold the root node
			this.tree = TreeFactory.createTree(0, null);
			tree.setGroupNode(rootNode);
		}
	}
	
	protected void logDebug(String pattern, Object... arguments) {
		if (logger.isDebugEnabled()) {
			// replace any nodes with their paths within the nexus file
			// getting the path uses a breadth-first search, so we only do it if we have to
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i] instanceof Node) {
					arguments[i] = getPathString((Node) arguments[i]);
				}
			}
			logger.debug(pattern, arguments);
		}
	}
	
	protected String getPathString(Node node) {
		if (tree == null) throw new IllegalStateException("Root node not yet created");
		return TreeUtils.getPath(tree, node);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addGroupNode(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, org.eclipse.dawnsci.nexus.NexusBaseClass)
	 */
	@Override
	public GroupNode createGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) {
		logDebug("Creating new group node with name '{}' and nexus class '{}' to parent group '{}'", name, nexusBaseClass, parent);
		NXobject group = NexusNodeFactory.createNXobjectForClass(nexusBaseClass);
		if (parent == null) {
			if (rootNode != null) throw new IllegalStateException("root node already created");
			setRootNode(group);
		} else {
			parent.addGroupNode(name, group);
		}
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
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addAttribute(org.eclipse.dawnsci.analysis.api.tree.Node, java.lang.String, java.lang.Object)
	 */
	@Override
	public void createAttribute(Node parent, String name, Object value) throws NexusException {
		final Attribute attribute = TreeFactory.createAttribute(name, value);
		parent.addAttribute(attribute);
	}
	
}
