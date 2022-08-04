package org.eclipse.dawnsci.nexus.appender;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.dawnsci.nexus.ServiceHolder;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.january.DatasetException;

/**
 * An appender that copies {@link Node}s from an external NeXus file into the node being appended.
 * 
 * @author Matthew Dickie
 */
public class NexusNodeCopyAppender<N extends NXobject> extends AbstractNexusContextAppender<N> {

	
	/**
	 * Path to the NeXus file from which to copy
	 */
	private String externalFilePath;
	
	
	/**
	 * Paths to the NeXus nodes to copy
	 */
	private Set<String> nodePaths;
	
	
	/**
	 * By default, each node specified in {@link #nodePaths} is copied to the parent device node.
	 * If a custom target path is needed it can be mapped to the appropriate node here.
	 */
	private Map<String, String> customTargetPerNode = Collections.emptyMap();
	
	
	/**
	 * Where a node specified in {@link #nodePaths} is a {@link GroupNode}
	 * and contains children that should not be copied, these can be mapped here.
	 */
	private Map<String, Set<String>> excludedNodesPerGroup = Collections.emptyMap();
	
	
	@Override
	public void append(GroupNode parent, NexusContext context) throws NexusException {
		if (externalFilePath == null) return;
		Objects.requireNonNull(nodePaths, "nodePaths not set for appender: " + getName());

		try (NexusFile nexusFile = ServiceHolder.getNexusFileFactory().newNexusFile(externalFilePath)) {
			nexusFile.openToRead();
			NexusUtils.loadNexusTree(nexusFile); // recursively loads all GroupNodes in the file
			
			for (String nodePath : nodePaths) {
				
				GroupNode targetNode = getTargetNode(nodePath, parent);
				
				final Node node = nexusFile.getNode(nodePath);
				
				if (node == null) {
					throw new NexusException("No such node " + nodePath + " within external file: " + externalFilePath);
				}
				
				if (node.isDataNode()) {
					try {
						processDataNode(node, getNodeName(nodePath), context, targetNode);
					} catch (DatasetException e) {
						throw new NexusException("Could not appent data node " + nodePath + " of file " + externalFilePath);
					}
				} else if (node.isGroupNode()) {
					GroupNode groupNode = (GroupNode) node;
					Set<String> excludedNodes = excludedNodesPerGroup.getOrDefault(nodePath, Collections.emptySet());
					removeExcluded(groupNode, excludedNodes);
					processGroupNode(groupNode, context, targetNode);
				}
			}
		}
	}

	private String getNodeName(String nodePath) {
		/* 
		 * Hack alert:
		 * Node objects are not named; the name is in the NodeLink.
		 * For now we treat the node like a file in the file system!
		 */
		return Paths.get(nodePath).getFileName().toString();
	}

	/**
	 * Returns the {@link GroupNode} to be the target for the copy of the given node path.
	 * This will be the node for the device this appender is attached to,
	 * unless a custom target is specified for the given node path in {@link #customTargetPerNode} 
	 */
	private GroupNode getTargetNode(String nodePath, GroupNode parent) {
		if (customTargetPerNode.containsKey(nodePath)) {
			String customGroupName = customTargetPerNode.get(nodePath);
			
			if (!parent.containsGroupNode(customGroupName)) {
				parent.addGroupNode(customGroupName, NexusNodeFactory.createGroupNode());
			}
			
			return parent.getGroupNode(customGroupName);
		}
		return parent;
	}
	
	private void processDataNode(Node nodeToCopy, String nodeName, NexusContext context, GroupNode parentNode) throws NexusException, DatasetException {
		DataNode dataNode = getDataNode(nodeToCopy);
		context.addNode(parentNode, nodeName, dataNode);
	}
	
	/**
	 * Casts the {@link Node} to {@link DataNode}
	 * and replaces its lazy dataset with a full dataset
	 */
	private DataNode getDataNode(Node node) throws DatasetException {
		DataNode dataNode = (DataNode) node;
		dataNode.setDataset(dataNode.getDataset().getSlice());
		return dataNode;
	}

	private void processGroupNode(GroupNode groupToCopy, NexusContext context, GroupNode parentNode) throws NexusException {
		// loads all data into memory, replacing lazy dataset with in-memory ones
		TreeUtils.recursivelyLoadDataNodes(groupToCopy);
		for (Iterator<String> iterator = groupToCopy.getNodeNameIterator(); iterator.hasNext();) {
			final String nodeName = iterator.next();
			final Node node = groupToCopy.getNode(nodeName);
			context.addNode(parentNode, nodeName, node);
		}
	}

	private void removeExcluded(final GroupNode groupToCopy, Set<String> excluded) {
		for (String name : excluded) {
			final Node node = groupToCopy.getNode(name);
			if (node.isGroupNode()) {
				groupToCopy.removeGroupNode((GroupNode) node);
			} else if (node.isDataNode()) {
				groupToCopy.removeDataNode((DataNode) node);
			} else if (node.isSymbolicNode()) {
				groupToCopy.removeSymbolicNode((SymbolicNode) node);
			}
		}
	}
	
	public void setNodePaths(Set<String> nodePaths) {
		this.nodePaths = nodePaths;
	}

	public void setCustomTargetPerNode(Map<String, String> customTargetPerNode) {
		this.customTargetPerNode = customTargetPerNode;
	}
	
	public void setExcludedPerNode(Map<String, Set<String>> excludedPerNode) {
		this.excludedNodesPerGroup = excludedPerNode;
	}

	public void setExternalFilePath(String externalFilePath) {
		this.externalFilePath = externalFilePath;
	}
	
}
