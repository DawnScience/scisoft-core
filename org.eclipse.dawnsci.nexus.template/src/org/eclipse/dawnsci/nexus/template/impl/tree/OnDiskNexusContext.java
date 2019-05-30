package org.eclipse.dawnsci.nexus.template.impl.tree;

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
		final String parentPath = nexusFile.getPath(parent);
		final String destinationPath = parentPath + Node.SEPARATOR + name;
		nexusFile.link(linkPath, destinationPath);
	}

	@Override
	public void addAttribute(Node parent, String name, Object value) throws NexusException {
		nexusFile.addAttribute(parent, TreeFactory.createAttribute(name, value));
	}

}
