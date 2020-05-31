package org.eclipse.dawnsci.nexus.context.impl;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.dawnsci.nexus.context.NexusContextType;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOnDiskNexusContext implements NexusContext {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractOnDiskNexusContext.class);
	
	protected final NexusFile nexusFile;
	
	public AbstractOnDiskNexusContext(NexusFile nexusFile) {
		this.nexusFile = nexusFile;
	}
	
	@Override
	public NexusContextType getContextType() {
		return NexusContextType.ON_DISK;
	}
	
	protected void logDebug(String pattern, Object... arguments) {
		if (logger.isDebugEnabled()) {
			// replace any nodes with their paths within the nexus file
			// getting the path uses a breadth-first search, so we only do it if we have to
			final Object[] argsWithNodePaths = Arrays.stream(arguments).map(
					arg -> arg instanceof Node ? getPath((Node) arg) : arg).toArray();
			logger.debug(pattern, argsWithNodePaths);
		}
	}
	
	private String getPath(Node node) {
		return nexusFile.getPath(node);
	}
	
	@Override
	public GroupNode createGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) throws NexusException {
		logDebug("Creating new group node with name '{}' and nexus class '{}' to parent group '{}'", name, nexusBaseClass, parent);
		return nexusFile.getGroup(parent, name, nexusBaseClass.toString(), true);
	}

	@Override
	public DataNode createDataNode(GroupNode parent, String name, Object value) throws NexusException {
		logDebug("Creating new data node with name '{}' and value '{}' to parent group '{}'", name, value, parent);
		final IDataset dataset = DatasetFactory.createFromObject(value);
		return nexusFile.createData(parent, name, dataset);
	}
	
	@Override
	public void createAttribute(Node parent, String name, Object value) throws NexusException {
		nexusFile.addAttribute(parent, TreeFactory.createAttribute(name, value));
	}

	@Override
	public void addNode(GroupNode parent, String name, Node node) throws NexusException {
		nexusFile.addNode(parent, name, node);
	}
	
}
