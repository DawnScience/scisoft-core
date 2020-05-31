package org.eclipse.dawnsci.nexus.context.impl;

import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;

public class LocalOnDiskNexusContext extends AbstractOnDiskNexusContext {

	private GroupNode rootNode;
	
	public LocalOnDiskNexusContext(NexusFile nexusFile, GroupNode groupNode) {
		super(nexusFile);
		this.rootNode = groupNode;
	}
	
	@Override
	public boolean isLocal() {
		return true;
	}

	@Override
	public GroupNode getNexusRoot() throws NexusException {
		return rootNode;
	}

	@Override
	public Node getNode(String path) throws NexusException {
		// we can't get an node by path as we don't have access to the rest of the tree
		throw new UnsupportedOperationException("Cannot get node by path in a local context");
	}

	@Override
	public void copyAttribute(Node node, String name, String linkPath) throws NexusException {
		throw new UnsupportedOperationException("Cannot copy an attribute in a local context");
	}

	@Override
	public void createNodeLink(GroupNode parent, String name, String linkPath) throws NexusException {
		// TODO surely there should be way to do this with a soft link, and also for external links
		throw new UnsupportedOperationException("Cannot create a link in a local context");
	}

}
