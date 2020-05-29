package org.eclipse.dawnsci.nexus.appender;

import java.util.Iterator;
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
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.dawnsci.nexus.ServiceHolder;

/**
 * An appender that copies a {@link GroupNode} from an external file into the node being appended.
 * 
 * @author Matthew Dickie
 *
 * @param <N>
 */
public class NexusGroupCopyAppender<N extends NXobject> extends NexusObjectAppender<N> {

	private String externalFilePath;
	
	private String nodePath;
	
	private Set<String> excluded;
	
	public String getExternalFilePath() {
		return externalFilePath;
	}

	public void setExternalFilePath(String externalFilePath) {
		this.externalFilePath = externalFilePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}
	
	public Set<String> getExcluded() {
		return excluded;
	}

	public void setExcluded(Set<String> excluded) {
		this.excluded = excluded;
	}

	@Override
	protected void appendNexusObject(N nexusObject) throws NexusException {
		Objects.requireNonNull(externalFilePath, "externalFilePath not set for appender: " + getName());
		Objects.requireNonNull(externalFilePath, "nodePath not set for appender: " + getName());

		try (NexusFile nexusFile = ServiceHolder.getNexusFileFactory().newNexusFile(externalFilePath)) {
			nexusFile.openToRead();
			NexusUtils.loadNexusTree(nexusFile); // recursively loads all GroupNodes in the file
			final GroupNode groupToCopy = nexusFile.getGroup(nodePath, false);
			if (groupToCopy == null) throw new NexusException("No such group node " + nodePath + " within external file: " + externalFilePath);
			
			// excluded nodes need to be removed before we load the datasets, as their datasets may be too large to load into memory
			removeExcluded(groupToCopy);
			
			// loads all data into memory, replacing lazy dataset with in-memory ones
			TreeUtils.recursivelyLoadDataNodes(groupToCopy);
			for (Iterator<String> iterator = groupToCopy.getNodeNameIterator(); iterator.hasNext();) {
				final String nodeName = iterator.next();
				final Node node = groupToCopy.getNode(nodeName);
				nexusObject.addNode(nodeName, node);
			}
		} 
	}

	private void removeExcluded(final GroupNode groupToCopy) {
		if (excluded == null) return;
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
	
}
