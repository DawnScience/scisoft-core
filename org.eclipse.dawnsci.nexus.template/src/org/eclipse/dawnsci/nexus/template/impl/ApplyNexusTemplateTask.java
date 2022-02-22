package org.eclipse.dawnsci.nexus.template.impl;

import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_NAME_NX_CLASS;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.COPY_GROUP_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.GROUP_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_AXIS_SUBSTITUTIONS;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_NODE_PATH;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_VALUE;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.NODE_TYPE_SUFFIX_CHARS;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NXobject;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.context.NexusContext;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.PositionIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A task to apply a nexus template to a nexus tree.
 */
public class ApplyNexusTemplateTask  {
	
	/**
	 * An enumeration of the types of change that a node in the YAML mapping can represent.
	 */
	private enum NexusNodeType {
		GROUP,
		DATA,
		ATTRIBUTE,
		GROUP_COPY
	}
	
	private static final Logger logger = LoggerFactory.getLogger(ApplyNexusTemplateTask.class);
	
	private static final String ERROR_MESSAGE_PATTERN_DATA_NODE_ILLEGAL_MAPPING_ENTRY_NAME =
			"Illegal yaml property name ''{0}'' in data node ''{1}''. "
			+ " Only '" + MAPPING_NAME_VALUE + "' and attributes (ending with '@') are permitted.";
	
	private final NexusTemplateImpl template;
	private final NexusContext nexusContext;
	
	public ApplyNexusTemplateTask(NexusTemplateImpl template, NexusContext nexusContext) {
		this.template = template;
		this.nexusContext = nexusContext;
	}
	
	/**
	 * Applies the template to the nexus tree or file wrapped by the {@link NexusContext}.
	 * @throws NexusException
	 */
	public void run() throws NexusException {
		final GroupNode rootNode = nexusContext.getNexusRoot();
		if (rootNode == null) {
			createGroupNode(null, null, template.getMapping(), false);
		} else {
			applyMappingToGroupNode(nexusContext.getNexusRoot(), template.getMapping());
		}
	}

	/**
	 * Applies the mapping defined by the given {@link Map} to the given {@link GroupNode}.
	 * @param parentGroup
	 * @param mapping
	 * @throws NexusException
	 */
	private void applyMappingToGroupNode(GroupNode parentGroup, Map<String, Object> mapping) throws NexusException {
		// for each element in the mapping, get the node name and value and apply according to the type
		for (Map.Entry<String, Object> mappingEntry : mapping.entrySet()) {
			final String nodeName = getNodeName(mappingEntry.getKey()); 
			final Object nodeValue = mappingEntry.getValue();
			final NexusNodeType nodeType = getNodeType(mappingEntry.getKey());
			final boolean skipNode = checkCanAddNodeOrSkip(parentGroup, nodeName, nodeType, nodeValue); // we skip existing groupNodes
			
			logger.debug("Processing mapping {} of type {}", nodeName, nodeType);
			switch (nodeType) {
				case GROUP:
					addGroupNode(parentGroup, nodeName, nodeValue, skipNode); // only group nodes can currently be skipped 
					break;
				case DATA:
					addDataNode(parentGroup, nodeName, nodeValue);
					break;
				case ATTRIBUTE:
					addAttribute(parentGroup, nodeName, nodeValue);
					break;
				case GROUP_COPY:
					copyGroupNode(parentGroup, nodeName, nodeValue);
					break;
				default:
					throw new IllegalArgumentException("Unknown node type: " + nodeType.toString());
			}
		}
	}

	/**
	 * Checks whether a node of the given name and type can be added at the given location and throws an exception.
	 * 
	 * @return <code>true</code> if the node already exists and it should be skipped, <code>false</code> if it does not exist
	 *    and should be created
	 * @throws NexusException if the node already exists and it should not be skipped
	 */
	private boolean checkCanAddNodeOrSkip(GroupNode parentGroup, final String nodeName, NexusNodeType nodeType, Object nodeValue) throws NexusException {
		if (nodeType == NexusNodeType.ATTRIBUTE) {
			if (parentGroup.containsAttribute(nodeName) && !nodeName.equals(ATTRIBUTE_NAME_NX_CLASS)) {
				throw new NexusException(MessageFormat.format("Cannot add attribute ''{0}'' to the parent node, as there is an existing attribute with the same name", nodeName));
			}
			return true;
		}
		
		if (parentGroup.containsNode(nodeName)) {
			if (parentGroup.getNode(nodeName).isGroupNode() && nodeType == NexusNodeType.GROUP) {
				final GroupNode existingChildGroup = (GroupNode) nexusContext.getExistingChildNode(parentGroup, nodeName);
				final NexusBaseClass existingGroupClass = getNexusClass(nodeName, existingChildGroup);
				final NexusBaseClass templateGroupClass = getNexusClass(nodeName, nodeValue); 
				if (existingGroupClass != templateGroupClass) {
					throw new NexusException(MessageFormat.format("Cannot process group ''{0}'', existing group has NXclass ''{1}'' was ''{2}''",
							nodeName, existingGroupClass, templateGroupClass));
				}
				
				// if we see a group that already exists, we should skip it and process the children
				return true;
			}
			
			final String newNodeTypeStr = nodeType == NexusNodeType.DATA ? "DataNode" : "GroupNode";
			final String existingNodeTypeStr = parentGroup.containsDataNode(nodeName) ? "DataNode" : "GroupNode";
			throw new NexusException(MessageFormat.format("Cannot add {0} ''{1}'' to the parent node, as there is an existing {2} with the same name",
					newNodeTypeStr, nodeName, existingNodeTypeStr));
		}
		return false;
	}
	
	/**
	 * Add the group node with the given and nodeValue to the given parent node.
	 * The nodeValue is expected to be either a {@link String}, in which case it is interpreted as a link to
	 * elsewhere in the Nexus tree, or a {@link Map} 
	 * @param parentGroup
	 * @param nodeName
	 * @param nodeValue
	 * @param skipNode <code>true</code> to skip this node and process parents
	 * @throws NexusException
	 */
	private void addGroupNode(GroupNode parentGroup, String nodeName, Object nodeValue, boolean skipNode) throws NexusException {
		final Optional<String> linkPath = getLinkPath(nodeValue);
		if (linkPath.isPresent()) {
			logger.debug("Adding link {} to group node path {}", nodeName, linkPath);
			addLinkNode(parentGroup, nodeName, linkPath.get());
		} else if (nodeValue instanceof Map) {
			createGroupNode(parentGroup, nodeName, nodeValue, skipNode);
		} else {
			throw new NexusException("The value of a group node must be a mapping"); // Impossible due to the way yaml is parsed?
		}
	}

	private void createGroupNode(GroupNode parentGroup, String nodeName, Object nodeValue, boolean skipNode) throws NexusException {
		final GroupNode childGroup = createOrGetChildNode(parentGroup, nodeName, nodeValue, skipNode);
		
		@SuppressWarnings("unchecked")
		final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
		// recursively apply the mapping to the child group
		applyMappingToGroupNode(childGroup, mapping);
	}

	private GroupNode createOrGetChildNode(GroupNode parentGroup, String nodeName, Object nodeValue, boolean getExisting)
			throws NexusException {
		if (getExisting) {
			return (GroupNode) nexusContext.getExistingChildNode(parentGroup, nodeName);
		}
		
		return nexusContext.createGroupNode(parentGroup, nodeName, getNexusClass(nodeName, nodeValue));
	}
	
	/**
	 * Add a link to the given parent group node with the given name to the node at the given path.
	 * @param parentGroup parent group
	 * @param nodeName name
	 * @param linkPath path to link to
	 * @throws NexusException
	 */
	private void addLinkNode(GroupNode parentGroup, String nodeName, String linkPath) throws NexusException {
		nexusContext.createNodeLink(parentGroup, nodeName, linkPath);
	}
	
	/**
	 * If the given object is a link path (is a String starting with '/'), returns an {@link Optional}
	 * containing the link path, otherwise the optional is empty.
	 * @param nodeValue
	 * @return optional of link path
	 */
	private Optional<String> getLinkPath(Object nodeValue) {
		if (nodeValue instanceof String && ((String) nodeValue).startsWith(Node.SEPARATOR)) {
			return Optional.of((String) nodeValue);
		}
		return Optional.empty();
	}
	
	/**
	 * Gets the nexus class for the given nodeValue. The node value is expected to be a {@link Map}
	 * with an entry 'NX_class@'.
	 * @param nodeName
	 * @param nodeValue
	 * @return the nexus class
	 * @throws NexusException
	 */
	private NexusBaseClass getNexusClass(String nodeName, Object nodeValue) throws NexusException {
		if (!(nodeValue instanceof Map)) {
			// the value must be a mapping, not a scalar or list (or null)
			throw new NexusException("The value for a group node should be a mapping: " + nodeName); // mapping is the YAML term
		}

		@SuppressWarnings("unchecked")
		final Map<String, Object> childMapping = (Map<String, Object>) nodeValue;

		// find the nexus class
		final String nexusClassString = (String) childMapping.get(ATTRIBUTE_NAME_NX_CLASS + '@');
		if (nexusClassString == null) {
			throw new NexusException("The nexus class for group " + nodeName + " is not specified"); 
		}
		
		try {
			return NexusBaseClass.getBaseClassForName(nexusClassString);
		} catch (IllegalArgumentException e) {
			throw new NexusException("The specified nexus class '" + nexusClassString + "' for group '" + nodeName + "' does not exist.");
		}
	}
	
	private NexusBaseClass getNexusClass(String groupName, GroupNode groupNode) throws NexusException {
		if (groupNode instanceof NXobject) {
			return ((NXobject) groupNode).getNexusBaseClass();
		}
		
		final Attribute nxClassAttr = groupNode.getAttribute(ATTRIBUTE_NAME_NX_CLASS);
		if (nxClassAttr == null) {
			throw new NexusException("Existing group node ''" + groupName + "'' does not have an NXclass attribute");
		}
		
		return NexusBaseClass.getBaseClassForName(nxClassAttr.getFirstElement());
	}
	
	/**
	 * Creates a new group node in the given parent group with the given name as a copy
	 * of an existing node, as specified in the given nodeValue (expected to be a {@link Map}).
	 * @param parentGroup
	 * @param nodeName
	 * @param nodeValue
	 * @throws NexusException
	 */
	private void copyGroupNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		if (!(nodeValue instanceof Map)) {
			throw new NexusException("The value for a group node should be a mapping: " + nodeName); // mapping is the YAML term
		}
		
		@SuppressWarnings("unchecked")
		final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
		
		// the only allowed entries are 'nodePath' and 'axisSubstiutions'
		if (mapping.size() > 2) {
			throw new NexusException("Invalid mapping for copied node '" + nodeName + "'. "
					+ "The only permitted mapping for a copied NXdata group are " +
					MAPPING_NAME_NODE_PATH + " and " + MAPPING_NAME_AXIS_SUBSTITUTIONS);
		}
		
		
		// find the NXdata group to copy - TODO allow more general copying of nodes? is there a use case?
		final String nodePath = (String) mapping.get(MAPPING_NAME_NODE_PATH);
		if (nodePath == null) {
			throw new NexusException("The mapping for copied NXdata group '" + nodeName + "' must specify a nodePath");
		}
		
		logger.debug("Copying group node {} from {}", nodeName, nodePath);
		Node node = nexusContext.getNode(nodePath);
		if (node == null) {
			throw new NexusException("Cannot create group '" + nodeName + "', no such group: " + nodePath);
		}
		if (!(node instanceof NXdata)) {
			throw new NexusException("Cannot create group '" + nodeName + "'. The node at the given path is not an NXdata group: " + nodePath);
		}
		final NXdata source = (NXdata) node;
		
		// get the axis substitutions
		@SuppressWarnings("unchecked")
		final Map<String, String> axisSubstitutions = (Map<String, String>) mapping.get(MAPPING_NAME_AXIS_SUBSTITUTIONS);
		final UnaryOperator<String> axesSubstititionFunction = createAxesSubstitutionFunction(nodeName, axisSubstitutions);
		
		// create the new destination node
		final GroupNode dest = nexusContext.createGroupNode(parentGroup, nodeName, NexusBaseClass.NX_DATA);

		// copy all the child nodes, both data and group nodes, applying the axis substitutions
		final Iterator<String> nodeNameIter = source.getNodeNameIterator();
		while (nodeNameIter.hasNext()) {
			final String childNodeName = nodeNameIter.next();
			final String childNodePath = nodePath + Node.SEPARATOR + childNodeName; 
			final String destName = axesSubstititionFunction.apply(childNodeName);
			logger.debug("Adding link {} to path {}", destName, childNodePath);
			nexusContext.createNodeLink(dest, destName, childNodePath); 
		}

		// copy all the attributes of the old group to new attributes
		copyAttributes(source, dest, axesSubstititionFunction);
	}

	/**
	 * Creates and returns a function that applies axes substitutions based on those in the given mapping.  
	 * @param nodeName
	 * @param mapping
	 * @return 
	 * @throws NexusException
	 */
	private UnaryOperator<String> createAxesSubstitutionFunction(String nodeName, final Map<String, String> axisSubstitutions)
			throws NexusException {
		if (axisSubstitutions == null) {
			throw new NexusException("The mapping for copied NXdata group '" + nodeName + "' must specify " +
					MAPPING_NAME_AXIS_SUBSTITUTIONS);
		}

		// a function to do the axis name substitution
		return name -> axisSubstitutions.entrySet().stream()
				.filter(substitution -> name.startsWith(substitution.getKey()))
				.findAny()
				.map(substitution -> name.replace(substitution.getKey(), substitution.getValue()))
				.orElse(name);
	}

	/**
	 * Copy all the attributes from the given {@link NXdata} node to the given destination
	 * {@link GroupNode}. 
	 * @param source
	 * @param dest
	 * @param axesSubstition
	 * @throws NexusException
	 */
	private void copyAttributes(final NXdata source, final GroupNode dest,
			final UnaryOperator<String> axesSubstition) throws NexusException {
		final Iterator<String> attrNameIter = source.getAttributeNameIterator();
		while (attrNameIter.hasNext()) {
			final String attrName = attrNameIter.next();
			final Attribute attribute = source.getAttribute(attrName);
			final String destName = axesSubstition.apply(attrName);
			IDataset attrDataset = attribute.getValue();
			logger.debug("Copying attribute {} from attribute {}", destName, attrName);  
			
			// special case for the axes attribute, we need to apply the substitution to the values in the dataset as well
			if (attrName.equals(NXdata.NX_ATTRIBUTE_AXES)) {
				logger.debug("Applying axes substitions to axes attribute");
				attrDataset = attribute.getValue().clone();
				final PositionIterator iter = new PositionIterator(attrDataset.getShape());
				int[] pos;
				while (iter.hasNext()) {
					pos = iter.getPos();
					final String axisName = attrDataset.getString(pos);
					final String newAxisName = axesSubstition.apply(axisName);
					attrDataset.set(newAxisName, pos);
				}
			}
			
			nexusContext.createAttribute(dest, destName, attrDataset);
		}
	}
	
	/**
	 * Add a data node with the given name and value to the given parent group.
	 * If the value is a {@link String} starting with '/' then the value is interpreted as a link
	 * path and the data node at that path is added to the parent group. Otherwise the
	 * value given is converted into a dataset and a new data node is created.
	 * The nodeValue is interpreted in one of the following ways based on its value
	 * <ul>
	 *   <li>If {@code nodeValue} is a {@link String} starting with '/' it will be interpreted as a node link,
	 *     and the existing data node at that path in the nexus tree will be added to the parent node;</li>
	 *   <li>If {@code nodeValue} is a {@link Map}, then a new data node is created. This map must have a 'value'
	 *      entry which will be converted into a dataset and set as the value of the new data node. All other
	 *      entries must be attributes (ending in '@');</li> 
	 *   <li>Any other object will be converted into a dataset and set as the value of the new data node.</li>  
	 * </ul> 
	 * @param parentGroup
	 * @param nodeName
	 * @param nodeValue
	 * @throws NexusException
	 */
	private void addDataNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		final Optional<String> linkPath = getLinkPath(nodeValue);
		if (linkPath.isPresent()) {
			if (getNodeType(linkPath.get()) == NexusNodeType.ATTRIBUTE) {
				throw new NexusException("Cannot add data node link " + nodeName + " as link path " + linkPath.get() + " is to an attribute");
			}
			// link the node at the given path to the parent group
			logger.debug("Adding link {} to data node at path {}", nodeName, linkPath.get());
			addLinkNode(parentGroup, nodeName, linkPath.get());
		} else if (nodeValue instanceof Map) {
			// the value is a map, with a 'value' entry giving the value, and attributes
			@SuppressWarnings("unchecked")
			final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
			addDataNodeForMapping(parentGroup, nodeName, mapping);
		} else {
			// create a new data node with the given value
			logger.debug("Adding data node {} with value {}", nodeName, nodeValue);
			nexusContext.createDataNode(parentGroup, nodeName, nodeValue);
		}
	}
	
	/**
	 * Creates a data node for the given mapping. The mapping must contain only a 'value' entry, whose
	 * value is converted into a dataset and set as the value of the new data node, and attributes.
	 * @param parentGroup
	 * @param nodeName
	 * @param mapping
	 * @throws NexusException
	 */
	private void addDataNodeForMapping(GroupNode parentGroup, String nodeName, Map<String, Object> mapping) throws NexusException {
		final Object value = mapping.get(MAPPING_NAME_VALUE);
		if (value == null) {
			throw new NexusException("The mapping for a data node must specify a value: " + nodeName);
		}
		
		logger.debug("Adding data node {} with value {}", nodeName, value);
		final DataNode dataNode = nexusContext.createDataNode(parentGroup, nodeName, value);
		for (Map.Entry<String, Object> yamlMappingEntry : mapping.entrySet()) {
			final String childNodeName = getNodeName(yamlMappingEntry.getKey());
			final Object nodeValue = yamlMappingEntry.getValue();
			// data node mappings can only contain attributes and the 'value'
			switch (getNodeType(yamlMappingEntry.getKey())) {
				case ATTRIBUTE:
					addAttribute(dataNode, childNodeName, nodeValue);
					break;
				case DATA:
					// 'value' is the only allowed non-attribute value, and we've already used this to create the data node 
					if (!childNodeName.equals(MAPPING_NAME_VALUE)) {
						throw new NexusException(MessageFormat.format(ERROR_MESSAGE_PATTERN_DATA_NODE_ILLEGAL_MAPPING_ENTRY_NAME, childNodeName, nodeName));
					}
					break;
				default:
					throw new NexusException("Invalid property '" + childNodeName + "' for data node '" + nodeName +"'");
			}
		}
	}
	
	/**
	 * Add an attribute to the given node with the given name and value. If the value is a string starting
	 * with '/' this is treated as a node path, and the value of the attribute at that path is copied
	 * as the value of the new attribute. 
	 * @param node
	 * @param name
	 * @param value
	 * @throws NexusException
	 */
	private void addAttribute(Node node, String name, Object value) throws NexusException {
		final Optional<String> linkPath = getLinkPath(value);
		if (linkPath.isPresent()) {
			if (!linkPath.get().endsWith(String.valueOf(ATTRIBUTE_SUFFIX))) {
				throw new NexusException("Link path must be to attribute (i.e. it must end with '@'), for attribute with name " + name + " and link path " + linkPath);
			}
			logger.debug("Copying attribute {} from {}", name, linkPath.get());  
			nexusContext.copyAttribute(node, name, linkPath.get());
		} else if (value instanceof Map) {
			throw new NexusException("The value for an attribute node cannot be a mapping: " + name);
		} else if (name.equals(ATTRIBUTE_NAME_NX_CLASS)) {
			// ignore NX_class attribute, it has already been dealt with 
		} else {
			logger.debug("Creating attribute {} with value {}", name, value);
			nexusContext.createAttribute(node, name, value);
		}
	}
	
	/**
	 * Extracts the node name from the given string. This discards any suffix character
	 * e.g. '@' for attributes, '/' for groups.
	 * @param fullNodeName
	 * @return node name
	 */
	private String getNodeName(String fullNodeName) {
		final char finalChar = fullNodeName.charAt(fullNodeName.length() - 1);
		if (NODE_TYPE_SUFFIX_CHARS.indexOf(finalChar) != -1) {
			return fullNodeName.substring(0, fullNodeName.length() - 1);
		}
		
		return fullNodeName;
	}
	
	/**
	 * Returns the node type for the
	 * @param templateEntry
	 * @return
	 */
	private NexusNodeType getNodeType(String fullNodeName) {
		final char finalChar = fullNodeName.charAt(fullNodeName.length() - 1);
		if (finalChar == GROUP_SUFFIX) {
			return NexusNodeType.GROUP;
		} else if (finalChar == ATTRIBUTE_SUFFIX) {
			return NexusNodeType.ATTRIBUTE;
		} else if (finalChar == COPY_GROUP_SUFFIX) {
			return NexusNodeType.GROUP_COPY;
		}

		return NexusNodeType.DATA;
	}

}
