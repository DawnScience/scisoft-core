package org.eclipse.dawnsci.nexus.template.impl.tree;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ApplicationMode;
import org.eclipse.january.dataset.DatasetFactory;

/**
 * TODO: better name for this class
 */
public class InMemoryNexusContext implements NexusContext {
	
	private final NXroot root;
	
	public InMemoryNexusContext(NXroot root) {
		this.root = root;
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
		return root;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addGroupNode(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, org.eclipse.dawnsci.nexus.NexusBaseClass)
	 */
	@Override
	public GroupNode addGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) {
		NXobject group = NexusNodeFactory.createNXobjectForClass(nexusBaseClass);
		parent.addGroupNode(name, group);
		return group;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext#addDataNode(org.eclipse.dawnsci.analysis.api.tree.GroupNode, java.lang.String, java.lang.Object)
	 */
	@Override
	public DataNode addDataNode(GroupNode parent, String name, Object value) {
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
		final String[] pathSegments = linkPath.split(Node.SEPARATOR);
		if (pathSegments.length < 2 || !pathSegments[0].equals("")) {
			// since the link path starts with '/', the first segment will be the empty string
			// there must be at least one other segment
			throw new NexusException("The node '" + name +"' specifies an invalid link path: " + linkPath);
		}
		
		Node node = root;
		for (int i = 1; i < pathSegments.length; i++) {
			if (node instanceof GroupNode) {
				node = ((GroupNode) node).getNode(pathSegments[i]);
			} else {
				throw new NexusException("Cannot link to path '" + linkPath + "', no such element '" + pathSegments[i] + "'");
			}
			
			if (node == null) {
				throw new NexusException("Cannot link to path '" + linkPath + "', no such element '" + pathSegments[i] + "'");
			}
		}
		
		parent.addNode(name, node);
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
