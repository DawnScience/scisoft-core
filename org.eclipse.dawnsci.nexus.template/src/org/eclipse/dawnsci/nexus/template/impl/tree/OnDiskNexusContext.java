package org.eclipse.dawnsci.nexus.template.impl.tree;

import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_SUFFIX;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ApplicationMode;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link NexusContext} to abstract adding nodes to an existing nexus file on disk.
 */
public class OnDiskNexusContext implements NexusContext {

	private static final Logger logger = LoggerFactory.getLogger(OnDiskNexusContext.class); 
	
	private final NexusFile nexusFile;
	
	public OnDiskNexusContext(NexusFile nexusFile) {
		this.nexusFile = nexusFile;
	}

	@Override
	public ApplicationMode getApplicationMode() {
		return ApplicationMode.ON_DISK;
	}

	@Override
	public GroupNode getNexusRoot() throws NexusException {
		return nexusFile.getGroup("/", false);
	}
	
	@Override
	public Node getNode(String path) throws NexusException {
		return nexusFile.getNode(path);
	}

	private void logDebug(String pattern, Object... arguments) {
		if (logger.isDebugEnabled()) {
			// replace any nodes with their paths within the nexus file
			// getting the path uses a breadth-first search, so we only do it if we have to
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i] instanceof Node) {
					arguments[i] = nexusFile.getPath((Node) arguments[i]);
				}
			}
			logger.debug(pattern, arguments);
		}
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
	public void createNodeLink(GroupNode parent, String name, String linkPath) throws NexusException {
		logDebug("Linking node at path '{}' to parent '{}' with name '{}'", linkPath, parent, name); 
		final String parentPath = nexusFile.getPath(parent); // parent path already ends with path separator '/'
		final String destinationPath = parentPath + name;
		nexusFile.link(linkPath, destinationPath);
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

	@Override
	public void createAttribute(Node parent, String name, Object value) throws NexusException {
		nexusFile.addAttribute(parent, TreeFactory.createAttribute(name, value));
	}

}
