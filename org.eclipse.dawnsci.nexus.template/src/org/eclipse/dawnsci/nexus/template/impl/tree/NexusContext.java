package org.eclipse.dawnsci.nexus.template.impl.tree;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ApplicationMode;

public interface NexusContext {

	public ApplicationMode getApplicationMode();
	
	public GroupNode getNexusRoot() throws NexusException;

	public GroupNode addGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) throws NexusException;

	public DataNode addDataNode(GroupNode parent, String name, Object value) throws NexusException;

	public void addLink(GroupNode parent, String name, String linkPath) throws NexusException;

	public void addAttribute(Node parent, String name, Object value) throws NexusException;

}