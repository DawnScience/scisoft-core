package org.eclipse.dawnsci.nexus.template.impl;

import static java.util.Objects.requireNonNull;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.MAPPING_NAME_VALUE;
import static org.eclipse.dawnsci.nexus.template.NexusTemplateConstants.PATH_SEPARATOR;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NXroot;
import org.eclipse.dawnsci.nexus.NexusBaseClass;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusNodeFactory;
import org.eclipse.dawnsci.nexus.template.NexusTemplateConstants;

/**
 * A task to apply a nexus template to a nexus tree.
 */
public class ApplyNexusTemplateTask  {
	
	private enum NexusNodeType {
		GROUP, DATA, ATTRIBUTE, LINK
	}
	
	private static final String ERROR_MESSAGE_PATTERN_DATA_NODE_ILLEGAL_MAPPING_ENTRY_NAME =
			"Illegal yaml property name ''{0}'' in data node ''{1}''. "
			+ " Only '" + MAPPING_NAME_VALUE + "' and attributes (ending with '@') are permitted.";
	
	private final NexusTemplateImpl template;
	private final GroupNode rootNode;
	
	ApplyNexusTemplateTask(NexusTemplateImpl template, NXroot rootNode) {
		this.template = template;
		this.rootNode = rootNode;
	}
	
	public void run() throws NexusException {
		requireNonNull(rootNode);
		applyMappingToGroupNode(rootNode, template.getMapping());
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
				case LINK:
					addLinkNode(parentGroup, nodeName, nodeValue);
					break;
				case ATTRIBUTE:
					addAttribute(parentGroup, nodeName, nodeValue);
					break;
			}
		}
	}
	
	private void addGroupNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		final GroupNode childGroup = NexusNodeFactory.createNXobjectForClass(getNexusClass(nodeName, nodeValue));
		parentGroup.addGroupNode(nodeName, childGroup);
		
		if (nodeValue instanceof Map) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
			// recursively apply the mapping to the child group
			applyMappingToGroupNode(childGroup, mapping);
		} else {
			throw new AssertionError("The value of a group node must be a mapping"); // Impossible due to the way yaml is parsed?
		}
	}
	
	private void addLinkNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		final String linkPath = getLinkPath(nodeValue);
		Objects.requireNonNull(linkPath, "linkPath not expected to be null"); // sanity check
		final Node linkedNode = resolveLink(nodeName, linkPath);
		parentGroup.addNode(nodeName, linkedNode);

		if (nodeValue instanceof Map) {
			throw new AssertionError("A link node is not expected to contain a mapping");
			// TODO remove if we're sure we won't support adding attributes or group/data nodes to linked nodes
//			@SuppressWarnings("unchecked")
//			final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
//			if (linkedNode instanceof GroupNode) {
//				applyMappingToGroupNode((GroupNode) linkedNode, mapping, true);
//			} else if (linkedNode instanceof DataNode) {
//				applyMappingToDataNode((DataNode) linkedNode, nodeName, mapping, true);
//			}
		}
	}
	
	private String getLinkPath(Object nodeValue) {
		if (nodeValue instanceof String && ((String) nodeValue).charAt(0) == PATH_SEPARATOR) {
			return (String) nodeValue;
			// TODO remove if we're sure we won't support adding attributes or group/data nodes to linked nodes
//		} else if (nodeValue instanceof Map) {
//			@SuppressWarnings("unchecked")
//			final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
//			return (String) mapping.get(MAPPING_NAME_LINK); // may be null
		}
		return null;
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
	
	private void addDataNode(GroupNode parentGroup, String nodeName, Object nodeValue) throws NexusException {
		final DataNode dataNode = NexusNodeFactory.createDataNode();
		parentGroup.addDataNode(nodeName, dataNode);
		if (nodeValue instanceof Map) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> mapping = (Map<String, Object>) nodeValue;
			applyMappingToDataNode(dataNode, nodeName, mapping);
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

	private Node resolveLink(String nodeName, String linkPath) throws NexusException {
		final String[] pathSegments = linkPath.split(Node.SEPARATOR);
		if (pathSegments.length < 2 || !pathSegments[0].equals("")) {
			// since the link path starts with '/', the first segment will be the empty string
			// there must be at least one other segment
			throw new NexusException("The node '" + nodeName +"' specifies an invalid link path: " + linkPath);
		}
		
		Node node = rootNode;
		for (int i = 1; i < pathSegments.length; i++) {
			if (node instanceof GroupNode) {
				node = ((GroupNode) node).getNode(pathSegments[i]);
			} else {
				throw new NexusException("Cannot link to path '" + linkPath + "', no such element '" + pathSegments[i] + "'");
			}
			
			if (node == null) {
				throw new NexusException("Cannot link to path '" + linkPath + "', no such element '" + pathSegments[i] + "'");
			}
		}
		
		return node;
	}
	
	private void addAttribute(Node node, String attributeName, Object attributeValue) throws NexusException {
		if (attributeValue instanceof Map) {
			throw new NexusException("The value for an attribute node cannot be a mapping: " + attributeName);
		}
		
		if (!attributeName.equals(NexusTemplateConstants.ATTRIBUTE_NAME_NX_CLASS)) { // ignore, this was already dealt with 
			node.addAttribute(TreeFactory.createAttribute(attributeName, attributeValue));
		}
	}
	
	private String getNodeName(String fullNodeName) {
		final char finalChar = fullNodeName.charAt(fullNodeName.length() - 1);
		if (finalChar == '/' || finalChar == '@') {
			return fullNodeName.substring(0, fullNodeName.length() - 1);
		}
		return fullNodeName;
	}
	
	private NexusNodeType getNodeType(Map.Entry<String, Object> templateEntry) {
		if (getLinkPath(templateEntry.getValue()) != null) {
			return NexusNodeType.LINK;
		}
		
		final String nodeName = templateEntry.getKey();
		final char finalChar = nodeName.charAt(nodeName.length() - 1);
		if (finalChar == '/') {
			return NexusNodeType.GROUP;
		} else if (finalChar == '@') {
			return NexusNodeType.ATTRIBUTE;
		}

		return NexusNodeType.DATA;
	}

}
