package org.eclipse.dawnsci.nexus.template.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;

abstract class AbstractTemplateNode {

	private final Map<String, Object> attributes = new HashMap<>();
	
	public void addAttribute(String name, Object value) {
		attributes.put(name, value);
	}
	
	public final void apply(Node node) {
		applyTemplate(node);
		addAttributes(node);
	}
	
	protected abstract void applyTemplate(Node node);
	
	private void addAttributes(Node node) {
		for (Map.Entry<String, Object> attributeEntry : attributes.entrySet()) {
			final Attribute attribute = TreeFactory.createAttribute(attributeEntry.getKey(), attributeEntry.getValue());
			node.addAttribute(attribute);
		}
	}
	
}
