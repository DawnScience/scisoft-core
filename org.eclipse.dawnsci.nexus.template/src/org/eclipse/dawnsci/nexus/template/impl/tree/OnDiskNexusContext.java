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

/**
 * A {@link NexusContext} to abstract adding nodes to an existing nexus file on disk.
 */
public class OnDiskNexusContext implements NexusContext {

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
	public GroupNode addGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) throws NexusException {
		return nexusFile.getGroup(parent, name, nexusBaseClass.toString(), true);
	}

	@Override
	public DataNode addDataNode(GroupNode parent, String name, Object value) throws NexusException {
		final IDataset dataset = DatasetFactory.createFromObject(value);
		return nexusFile.createData(parent, name, dataset);
	}

	@Override
	public void addLink(GroupNode parent, String name, String linkPath) throws NexusException {
		if (linkPath.endsWith(String.valueOf(ATTRIBUTE_SUFFIX))) {
			linkAttribute(parent, name, linkPath);
		} else {
			final String parentPath = nexusFile.getPath(parent);
			final String destinationPath = parentPath + Node.SEPARATOR + name;
			nexusFile.link(linkPath, destinationPath);
		}
	}
	
	private void linkAttribute(GroupNode parent, String name, String linkPath) throws NexusException {
		final Attribute currentAttr = findAttribute(linkPath);
		final IDataset data = currentAttr.getValue().clone();
		final Attribute newAttr = TreeFactory.createAttribute(name, data);
		nexusFile.addAttribute(parent, newAttr);
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
	public void addAttribute(Node parent, String name, Object value) throws NexusException {
		nexusFile.addAttribute(parent, TreeFactory.createAttribute(name, value));
	}

}
