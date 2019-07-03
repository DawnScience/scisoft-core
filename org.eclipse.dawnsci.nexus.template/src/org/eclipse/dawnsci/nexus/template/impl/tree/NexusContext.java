package org.eclipse.dawnsci.nexus.template.impl.tree;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ApplicationMode;

/**
 * Encapsulates a nexus tree with methods to modify that tree. This is because modifying a nexus tree
 * needs to be done differently depending on whether we're just modifying an in-memory tree that has yet
 * to be saved to disk, or an existing nexus file. The use of an class implementing this interface
 * allows client code to work in both situations.
 * 
 * @author Matthew Dickie
 */
public interface NexusContext {

	/**
	 * Gets the application mode, {@link ApplicationMode#IN_MEMORY} if this context modifies an
	 * in-memory nexus tree, or {@link ApplicationMode#ON_DISK} if it modifies an existing nexus file.
	 * @return the application mode
	 */
	public ApplicationMode getApplicationMode();
	
	/**
	 * Returns the {@link GroupNode} at the root of the nexus tree.
	 * @return the root node
	 * @throws NexusException if the root node could not be returned for any reason
	 */
	public GroupNode getNexusRoot() throws NexusException;

	/**
	 * Creates and returns a new {@link GroupNode} as a child node of the given parent group node,
	 * with the given name and Nexus class.
	 * 
	 * @param parent parent group node 
	 * @param name name of new group node
	 * @param nexusBaseClass the nexus class of the new group node
	 * @return the new group node
	 * @throws NexusException if the new group node could not be created for any reason
	 */
	public GroupNode createGroupNode(GroupNode parent, String name, NexusBaseClass nexusBaseClass) throws NexusException;

	/**
	 * Creates and returns a new {@link DataNode} with the given value as a child of the given
	 * parent node. The new data node will contains a dataset holding the given value. This will be a scalar
	 * dataset if the given value is scalar. 
	 * @param parent parent group node
	 * @param name name of new data node
	 * @param value value of new data node
	 * @return the new data node
	 * @throws NexusException if the new data node could not be created for any reason
	 */
	public DataNode createDataNode(GroupNode parent, String name, Object value) throws NexusException;

	/**
	 * Adds the existing node at the given path as a child of the given parent group node with
	 * the given name. Normally this will create a hard-link. In the case where the node at the
	 * given path is linked to by an external link to another file, the external link will be
	 * copied. 
	 * 
	 * @param parent parent group node
	 * @param name name of linked node within the given parent group node
	 * @param linkPath the path of the node to link to within this file
	 * @throws NexusException if the node could not be linked for any reason
	 */
	public void createNodeLink(GroupNode parent, String name, String linkPath) throws NexusException;

	/**
	 * Creates a new attribute of the given {@link Node} (which may be a {@link DataNode} or a
	 * {@link GroupNode}.
	 * 
	 * @param node node to add the attribute to
	 * @param name name of new attribute
	 * @param value the value of the attribute
	 * @throws NexusException if the attribute could not be created for any reason 
	 */
	public void createAttribute(Node node, String name, Object value) throws NexusException;

	/**
	 * Creates a copy of the attribute at the given path and adds it to the given node
	 * with the given name. The path to the attribute to copy must end with
	 * {@link NexusTemplateConstants#ATTRIBUTE_SUFFIX}, '@'.
	 * 
	 * @param node node to add copied attribute to
	 * @param name name of the new attribute
	 * @param linkPath the path to the attribute to copy
	 * @throws NexusException if the attribute could not be copied for any reason
	 */
	public void copyAttribute(Node node, String name, String linkPath) throws NexusException;	

}