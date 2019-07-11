package org.eclipse.dawnsci.nexus.template.impl;

import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.ATTRIBUTE_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.COPY_GROUP_SUFFIX;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_AXIS_SUBSTITUTIONS;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_NODE_PATH;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_VALUE;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.NODE_TYPE_SUFFIX_CHARS;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.GROUP_SUFFIX;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.nexus.NXdata;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants;
import org.eclipse.dawnsci.nexus.template.impl.tree.NexusContext;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.PositionIterator;

/**
 * A task to apply a nexus template to a nexus tree.
 */
public class ApplyNexusTemplateTask  {
	
	private enum NexusNodeType {
		GROUP, DATA, ATTRIBUTE, GROUP_COPY
	}
	
	private static final String ERROR_MESSAGE_PATTERN_DATA_NODE_ILLEGAL_MAPPING_ENTRY_NAME =
			"Illegal yaml property name ''{0}'' in data node ''{1}''. "
			+ " Only '" + MAPPING_NAME_VALUE + "' and attributes (ending with '@') are permitted.";
	
	private final NexusTemplateImpl template;
	private final NexusContext nexusContext;
	
	ApplyNexusTemplateTask(NexusTemplateImpl template, NexusContext nexusContext) {
		this.template = template;
		this.nexusContext = nexusContext;
	}
	
	public void run() throws NexusException {
		applyMappingToGroupNode(nexusContext.getNexusRoot(), template.getMapping());
	}

	private void applyMappingToGroupNode(GroupNode parentGroup, Map<String, Object> yamlMapping) throws NexusException {
		for (Map.Entry<String, Object> yamlMappingEntry : yamlMapping.entrySet()) {
			final String nodeName = getNodeName(yamlMappingEntry.getKey()); 
			final Object nodeValue = yamlMappingEntry.getValue();
			switch (getNodeType(yamlMappingEntry)) {
				case GROUP:
					addGroupNode(parentGroup, nodeName, nodeValue);
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
					throw new IllegalArgumentException(getNodeType(yamlMappingEntry).toString());
			}
		}
	}
	
	private void addGroupNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		final Optional<String> linkPath = getLinkPath(nodeValue);
		if (linkPath.isPresent()) {
			addLinkNode(parentGroup, nodeName, linkPath.get());
		} else if (nodeValue instanceof Map) {
			final GroupNode childGroup = nexusContext.createGroupNode(parentGroup, nodeName, getNexusClass(nodeName, nodeValue));
			@SuppressWarnings("unchecked")
			final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
			// recursively apply the mapping to the child group
			applyMappingToGroupNode(childGroup, mapping);
		} else {
			throw new NexusException("The value of a group node must be a mapping"); // Impossible due to the way yaml is parsed?
		}
	}
	
	private void addLinkNode(GroupNode parentGroup, String nodeName, String linkPath) throws NexusException {
		nexusContext.createNodeLink(parentGroup, nodeName, linkPath);
	}
	
	private Optional<String> getLinkPath(Object nodeValue) {
		if (nodeValue instanceof String && ((String) nodeValue).startsWith(Node.SEPARATOR)) {
			return Optional.of((String) nodeValue);
		}
		return Optional.empty();
	}
	
	private NexusBaseClass getNexusClass(String nodeName, Object nodeValue) throws NexusException {
		if (!(nodeValue instanceof Map)) {
			// the value must be a mapping, not a scalar or list (or null)
			throw new NexusException("The value for a group node should be a mapping: " + nodeName); // mapping is the YAML term
		}

		@SuppressWarnings("unchecked")
		final Map<String, Object> childMapping = (Map<String, Object>) nodeValue;

		// find the nexus class
		final String nexusClassString = (String) childMapping.get(NexusTemplateConstants.ATTRIBUTE_NAME_NX_CLASS + '@');
		if (nexusClassString == null) {
			throw new NexusException("The nexus class for group " + nodeName + " is not specified"); 
		}
		
		try {
			return NexusBaseClass.getBaseClassForName(nexusClassString);
		} catch (IllegalArgumentException e) {
			throw new NexusException("The specified nexus class '" + nexusClassString + "' for group '" + nodeName + "' does not exist.");
		}
	}
	
	private void copyGroupNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		if (!(nodeValue instanceof Map)) {
			throw new NexusException("The value for a group node should be a mapping: " + nodeName); // mapping is the YAML term
		}
		
		@SuppressWarnings("unchecked")
		final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
		
		// find the NXdata group to copy - TODO allow more general copying of nodes? is there a use case?
		final String nodePath = (String) mapping.get(MAPPING_NAME_NODE_PATH);
		if (nodePath == null) {
			throw new NexusException("The mapping for copied NXdata group '" + nodeName + "' must specify a nodePath");
		}
		
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
		if (axisSubstitutions == null) {
			throw new NexusException("The mapping for copied NXdata group '" + nodeName + "' must specify " +
					MAPPING_NAME_AXIS_SUBSTITUTIONS);
		}

		// no other entries are allowed
		if (mapping.size() > 2) {
			throw new NexusException("Invalid mapping for copied node '" + nodeName + "'. "
					+ "The only permitted mapping for a copied NXdata group are " +
					MAPPING_NAME_NODE_PATH + " and " + MAPPING_NAME_AXIS_SUBSTITUTIONS);
		}
		
		// a function to do the axis name substitution
		final UnaryOperator<String> substitionFunction = name ->
			axisSubstitutions.entrySet().stream()
				.filter(substitution -> name.startsWith(substitution.getKey()))
				.findAny()
				.map(substitution -> name.replace(substitution.getKey(), substitution.getValue()))
				.orElse(name);
		
		// create the new destination node
		final GroupNode dest = nexusContext.createGroupNode(parentGroup, nodeName, NexusBaseClass.NX_DATA);

		// copy all the child nodes, both data and group nodes, applying the axis substitutions
		final Iterator<String> nodeNameIter = source.getNodeNameIterator();
		while (nodeNameIter.hasNext()) {
			final String childNodeName = nodeNameIter.next();
			final String childNodePath = nodePath + Node.SEPARATOR + childNodeName; 
			final String destName = substitionFunction.apply(childNodeName);
			nexusContext.createNodeLink(dest, destName, childNodePath); 
		}

		// copy all attributes to the new attributes
		final Iterator<String> attrNameIter = source.getAttributeNameIterator();
		while (attrNameIter.hasNext()) {
			final String attrName = attrNameIter.next();
			final Attribute attribute = source.getAttribute(attrName);
			final String destName = substitionFunction.apply(attrName);
			IDataset attrDataset = attribute.getValue();
			if (attrName.equals(NXdata.NX_ATTRIBUTE_AXES)) {
				// special case for the axes attribute, we need to apply the substitution to the values in the dataset as well
				attrDataset = attribute.getValue().clone();
				final PositionIterator iter = new PositionIterator(attrDataset.getShape());
				int[] pos;
				while (iter.hasNext()) {
					pos = iter.getPos();
					final String axisName = attrDataset.getString(pos);
					final String newAxisName = substitionFunction.apply(axisName);
					attrDataset.set(newAxisName, pos);
				}
			}
			nexusContext.createAttribute(dest, destName, attrDataset);
		}
	}
	
	private void addDataNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		final Optional<String> linkPath = getLinkPath(nodeValue);
		if (linkPath.isPresent()) {
			addLinkNode(parentGroup, nodeName, linkPath.get());
		} else if (nodeValue instanceof Map) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
			final Object value = mapping.get(MAPPING_NAME_VALUE);
			if (value == null) {
				throw new NexusException("The mapping for a data node must specify a value: " + nodeName);
			}
			
			DataNode dataNode = nexusContext.createDataNode(parentGroup, nodeName, value);
			applyMappingToDataNode(dataNode, nodeName, mapping);
		} else {
			nexusContext.createDataNode(parentGroup, nodeName, nodeValue);
		}
	}
	
	private void applyMappingToDataNode(DataNode dataNode, String dataNodeName, Map<String, Object> yamlMapping) throws NexusException {
		for (Map.Entry<String, Object> yamlMappingEntry : yamlMapping.entrySet()) {
			final String childNodeName = getNodeName(yamlMappingEntry.getKey());
			final Object nodeValue = yamlMappingEntry.getValue();
			// data node mappings can only contain attributes and the 
			switch (getNodeType(yamlMappingEntry)) {
				case ATTRIBUTE:
					addAttribute(dataNode, childNodeName, nodeValue);
					break;
				case DATA:
					// 'value' denotes the value of this data node. 
					if (!childNodeName.equals(MAPPING_NAME_VALUE)) {
						throw new NexusException(MessageFormat.format(ERROR_MESSAGE_PATTERN_DATA_NODE_ILLEGAL_MAPPING_ENTRY_NAME, childNodeName, dataNodeName));
					}
					break;
				default:
					throw new NexusException("Invalid property '" + childNodeName + "' for data node '" + dataNodeName +"'");
			}
		}
	}
	
	private void addAttribute(Node node, String attributeName, Object attributeValue) throws NexusException {
		final Optional<String> linkPath = getLinkPath(attributeValue);
		if (linkPath.isPresent()) {
			if (!linkPath.get().endsWith(String.valueOf(ATTRIBUTE_SUFFIX))) {
				throw new NexusException("Link path must be to attribute (i.e. it must end with '@'), for attribute with name " + attributeName + " and link path " + linkPath);
			}
			nexusContext.copyAttribute(node, attributeName, linkPath.get());
		} else if (attributeValue instanceof Map) {
			throw new NexusException("The value for an attribute node cannot be a mapping: " + attributeName);
		} else if (attributeName.equals(NexusTemplateConstants.ATTRIBUTE_NAME_NX_CLASS)) {
			// ignore NX_class attribute, it has already been dealt with 
		} else {
			nexusContext.createAttribute(node, attributeName, attributeValue);
		}
	}
	
	private String getNodeName(String fullNodeName) {
		final char finalChar = fullNodeName.charAt(fullNodeName.length() - 1);
		if (NODE_TYPE_SUFFIX_CHARS.indexOf(finalChar) != -1) {
			return fullNodeName.substring(0, fullNodeName.length() - 1);
		}
		
		return fullNodeName;
	}
	
	private NexusNodeType getNodeType(Map.Entry<String, Object> templateEntry) {
		final String nodeName = templateEntry.getKey();
		final char finalChar = nodeName.charAt(nodeName.length() - 1);
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
