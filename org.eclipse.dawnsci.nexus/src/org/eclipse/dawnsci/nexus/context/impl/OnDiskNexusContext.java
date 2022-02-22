package org.eclipse.dawnsci.nexus.context.impl;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link NexusContext} to abstract adding nodes to an existing nexus file on disk.
 */
public class OnDiskNexusContext extends AbstractOnDiskNexusContext {
	
	private static final Logger logger = LoggerFactory.getLogger(OnDiskNexusContext.class);

	public OnDiskNexusContext(NexusFile nexusFile) {
		super(nexusFile);
	}
	
	@Override
	public boolean isLocal() {
		return false;
	}

	@Override
	public GroupNode getNexusRoot() throws NexusException {
		return nexusFile.getGroup("/", false);
	}
	
	@Override
	public Node getNode(String path) throws NexusException {
		return nexusFile.getNode(path);
	}
	
	@Override
	public void createNodeLink(GroupNode parent, String name, String linkPath) throws NexusException {
		logDebug("Linking node at path '{}' to parent '{}' with name '{}'", linkPath, parent, name); 
		final String parentPath = nexusFile.getPath(parent); // parent path already ends with path separator '/'
		final String destinationPath = parentPath + name;
		final Node linkTargetNode = nexusFile.getNode(linkPath);
		if (linkTargetNode == null) {
			logger.warn("Cannot add link '{}' to path '{}'. No node found at that path", name, linkPath);
		} else {
			nexusFile.link(linkPath, destinationPath);
		}
	}
	
	@Override
	public void copyAttribute(Node node, String name, String linkPath) throws NexusException {
		logDebug("Copying attribute at path '{}' to node '{}' with name '{}'", linkPath, node, name);
		final Attribute currentAttr = findAttribute(linkPath);
		final IDataset data = currentAttr.getValue().clone();
		final Attribute newAttr = TreeFactory.createAttribute(name, data);
		nexusFile.addAttribute(node, newAttr);
	}
	
	private Attribute findAttribute(String path) throws NexusException {
		// TODO: the NexusFile API should have a method to do this
		final int lastSeparatorIndex = path.lastIndexOf(Node.SEPARATOR);
		if (lastSeparatorIndex < 2) { // "/g/a@' is shortest possible attribute path
			throw new NexusException("Illegal attribute path :" + path);
		}
		final String parentPath = path.substring(0, lastSeparatorIndex);
		final String attrName = path.substring(lastSeparatorIndex + 1, path.length() - 1);
		final Node parentNode = nexusFile.getNode(parentPath);
		if (parentNode == null) {
			throw new NexusException("Cannot link to attribute, parent node doesn't exist: " + path);
		}
		final Attribute attr = parentNode.getAttribute(attrName);
		if (attr == null) {
			throw new NexusException("Cannot link to attribute, attribute doesn't exist in parent: " + path);
		}
		return attr;
	}

}
