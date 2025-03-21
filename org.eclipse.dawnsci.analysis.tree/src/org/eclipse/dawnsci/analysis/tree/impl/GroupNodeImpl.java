/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.tree.impl;

import static java.util.stream.Collectors.toCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.january.dataset.ILazyDataset;

public class GroupNodeImpl extends NodeImpl implements GroupNode, Serializable {
	protected static final long serialVersionUID = 8830337783420707862L;

	private Map<Long, Node> pool;
	private int numDataNodes;
	private int numGroupNodes;
	private final Map<String, NodeLink> nodes;
	private boolean populated = false;

	@Override
	public void setGlobalPool(Map<Long, Node> globalPool) {
		pool = globalPool;
	}

	@Override
	public Map<Long, Node> getGlobalPool() {
		return pool;
	}

	/**
	 * Construct a group node with given object ID
	 * @param oid object ID
	 */
	public GroupNodeImpl(final byte[] oid) {
		super(oid);
		numDataNodes = 0;
		numGroupNodes = 0;
		nodes = new LinkedHashMap<>();
	}

	/**
	 * Construct a group node with given object ID
	 * @param oid object ID
	 */
	public GroupNodeImpl(final long oid) {
		this(toBytes(oid));
	}

	@Override
	public boolean isPopulated() {
		return populated;
	}

	@Override
	public int getNumberOfNodelinks() {
		return nodes.size();
	}

	@Override
	public NodeLink getNodeLink(String name) {
		return nodes.get(name);
	}

	@Override
	public void addNodeLink(final NodeLink link) {
		synchronized (nodes) {
			final String name = link.getName();
			if (nodes.containsKey(name)) {
				Node n = nodes.get(name).getDestination();
				if (link.isDestinationData() && !(n instanceof DataNode)) {
					throw new IllegalArgumentException("Cannot add a data node as there is an existing non data node of same name: " + name);
				}
				if (link.isDestinationGroup() && !(n instanceof GroupNode)) {
					throw new IllegalArgumentException("Cannot add a group node as there is an existing non group node of same name: " + name);
				}
				if (link.isDestinationSymbolic() && !(n instanceof SymbolicNode)) {
					throw new IllegalArgumentException("Cannot add a symbolic node as there is an existing non symbolic node of same name: " + name);
				}
			}
			Node n = link.getDestination();
			if (n instanceof GroupNode || (n instanceof SymbolicNode && name.endsWith(SEPARATOR))) {
				numGroupNodes++;
			} else {
				numDataNodes++;
			}
			nodes.put(name, link);
			populated = true;
		}
	}

	@Override
	public void addNode(final String name, final Node node) {
		if (node == null) {
			return;
		}
	
		if (node instanceof SymbolicNode linkNode) {
			addSymbolicNode(name, linkNode);
		} else if (node instanceof DataNode dataNode) {
			addDataNode(name, dataNode);
		} else if (node instanceof GroupNode groupNode) {
			addGroupNode(name, groupNode);
		}
	}

	@Override
	public int getNumberOfGroupNodes() {
		return numGroupNodes;
	}
	
	@Override
	public boolean containsNode(final String name) {
		return nodes.containsKey(name);
	}

	@Override
	public boolean containsGroupNode(final String name) {
		return nodes.containsKey(name) && nodes.get(name).isDestinationGroup();
	}

	@Override
	public GroupNode getGroupNode(final String name) {
		if (nodes.containsKey(name)) {
			Node n = nodes.get(name).getDestination();
			while (n instanceof SymbolicNode linkNode) {
				n = linkNode.getNode();
			}
			if (n == null) {
				throw new NullPointerException("A symbolic node exists with the given name which cannot be resolved to a group node: " + name);
			}
			if (!(n instanceof GroupNode)) {
				throw new IllegalArgumentException("Existing node with given name is not a group node: " + name);
			}

			return (GroupNode) n;
		}

		return null;
	}

	@Override
	public List<GroupNode> getGroupNodes() {
		List<GroupNode> groupNodes = new ArrayList<>(numGroupNodes);
		final Iterator<String> nodeNameIter = getNodeNameIterator();
		while (nodeNameIter.hasNext()) {
			Node node = getNode(nodeNameIter.next());
			while (node instanceof SymbolicNode linkNode) {
				node = linkNode.getNode();
			}
			if (node instanceof GroupNode groupNode) {
				groupNodes.add(groupNode);
			}
		}
		
		return groupNodes;
	}

	@Override
	public Map<String, GroupNode> getGroupNodeMap() {
		final Map<String, GroupNode> groupNodeMap = new LinkedHashMap<>(numGroupNodes);
		final Iterator<String> nodeNameIter = getNodeNameIterator();
		while (nodeNameIter.hasNext()) {
			final String nodeName = nodeNameIter.next();
			Node node = getNode(nodeName);
			while (node instanceof SymbolicNode linkNode) {
				node = linkNode.getNode();
			}
			if (node instanceof GroupNode groupNode) {
				groupNodeMap.put(nodeName, groupNode);
			}
		}
		
		return groupNodeMap;
	}

	public void addGroupNode(final String name, final GroupNode g) {
		synchronized (nodes) {
			// check that there is not an existing data node with the same name
			if (nodes.containsKey(name)) {
				Node n = nodes.get(name).getDestination();
				if (!(n instanceof GroupNode)) {
					throw new IllegalArgumentException("Cannot add node as group contains node with same name that is not a group node: " + name);
				}
			} else {
				numGroupNodes++;
			}
			// add the new node
			nodes.put(name, createNodeLink(name, g));
			populated = true;
		}
	}

	@Override
	public void removeGroupNode(final String name) {
		if (!nodes.containsKey(name)) {
			throw new IllegalArgumentException("No node exists in this group with the name: " + name);
		}

		Node n = nodes.get(name).getDestination();
		while (n instanceof SymbolicNode linkNode) {
			n = linkNode.getNode();
		}
		if (n == null) {
			throw new NullPointerException("A symbolic node exists with the given name which cannot be resolved to a group node: " + name);
		}
		if (!(n instanceof GroupNode)) {
			throw new IllegalArgumentException("Group of given name does not exist in this group: " + name);
		}

		removeNode(name, true);
	}

	@Override
	public void removeGroupNode(final GroupNode g) {
		for (String n : nodes.keySet()) {
			NodeLink l = nodes.get(n);
			if (l.getDestination().equals(g)) {
				removeNode(n, true);
				return;
			}
		}
		throw new IllegalArgumentException("Given group does not exist in this group");
	}

	@Override
	public int getNumberOfDataNodes() {
		return numDataNodes;
	}

	@Override
	public boolean containsDataNode(final String name) {
		return nodes.containsKey(name) && nodes.get(name).isDestinationData();
	}

	@Override
	public DataNode getDataNode(final String name) {
		if (nodes.containsKey(name)) {
			Node n = nodes.get(name).getDestination();
			while (n instanceof SymbolicNode linkNode) {
				n = linkNode.getNode();
			}
			if (n == null) {
				throw new NullPointerException("A symbolic node exists with the given name which cannot be resolved to a data node: " + name);
			}
			if (!(n instanceof DataNode)) {
				throw new IllegalArgumentException("Existing node with given name is not a data node: " + name);
			}

			return (DataNode) n;
		}

		return null;
	}

	@Override
	public List<DataNode> getDataNodes() {
		final List<DataNode> dataNodes = new ArrayList<>(numDataNodes);
		final Iterator<String> nodeNameIter = getNodeNameIterator();
		while (nodeNameIter.hasNext()) {
			Node node = getNode(nodeNameIter.next());
			while (node instanceof SymbolicNode linkNode) {
				node = linkNode.getNode();
			}
			if (node instanceof DataNode dataNode) {
				dataNodes.add(dataNode);
			}
		}
		
		return dataNodes;
	}

	@Override
	public Map<String, DataNode> getDataNodeMap() {
		final Map<String, DataNode> dataNodeMap = new LinkedHashMap<>(numDataNodes);
		final Iterator<String> nodeNameIter = getNodeNameIterator();
		while (nodeNameIter.hasNext()) {
			final String nodeName = nodeNameIter.next();
			Node node = getNode(nodeName);
			while (node instanceof SymbolicNode linkNode) {
				node = linkNode.getNode();
			}
			if (node instanceof DataNode dataNode) {
				dataNodeMap.put(nodeName, dataNode);
			}
		}
		
		return dataNodeMap;
	}

	public Node getNode(final String name) {
		if (nodes.containsKey(name)) {
			return nodes.get(name).getDestination();
		}
		
		return null;
	}

	@Override
	public void addDataNode(final String name, final DataNode d) {
		synchronized (nodes) {
			if (nodes.containsKey(name)) {
				Node n = nodes.get(name).getDestination();
				if (!(n instanceof DataNode)) {
					throw new IllegalArgumentException("Cannot add node as group contains node with same name that is not a data node: " + name);
				}
			} else {
				numDataNodes++;
			}
			nodes.put(name, createNodeLink(name, d));
			populated = true;
		}
	}

	protected NodeLink createNodeLink(final String name, final Node n) {
		return new NodeLinkImpl(name, this, n);
	}

	private void removeNode(String name, boolean isGroup) {
		nodes.remove(name);

		if (isGroup) {
			numGroupNodes--;
		} else {
			numDataNodes--;
		}
		assert numGroupNodes >= 0 && numDataNodes >= 0;
		populated = numDataNodes > 0 || numGroupNodes > 0;
	}

	@Override
	public void removeDataNode(final String name) {
		if (!nodes.containsKey(name)) {
			throw new IllegalArgumentException("No name exists in this group: " + name);
		}

		Node n = nodes.get(name).getDestination();
		while (n instanceof SymbolicNode linkNode) {
			n = linkNode.getNode();
		}
		if (n == null) {
			throw new NullPointerException("A symbolic node exists with the given name which cannot be resolved to a data node: " + name);
		}
		if (!(n instanceof DataNode)) {
			throw new IllegalArgumentException("Dataset of given name does not exist in this group: " + name);
		}

		removeNode(name, false);
	}

	@Override
	public void removeDataNode(final DataNode d) {
		for (String n : nodes.keySet()) {
			NodeLink l = nodes.get(n);
			if (l.getDestination().equals(d)) {
				removeNode(n, false);
				return;
			}
		}
		throw new IllegalArgumentException("Given dataset does not exist in this group");
	}

	@Override
	public void addSymbolicNode(final String name, final SymbolicNode s) {
		synchronized (nodes) {
			if (nodes.containsKey(name)) {
				Node n = nodes.get(name).getDestination();
				if (!(n instanceof SymbolicNode)) {
					throw new IllegalArgumentException("Cannot add node as group contains node with same name that is not a symbolic node: " + name);
				}
			} else {
				if (name.endsWith(Node.SEPARATOR)) {
					numGroupNodes++;
				} else {
					numDataNodes++;
				}
			}
			nodes.put(name, createNodeLink(name, s));
			populated = true;
		}
	}

	@Override
	public boolean containsSymbolicNode(String name) {
		return nodes.containsKey(name) && nodes.get(name).isDestinationSymbolic();
	}

	@Override
	public SymbolicNode getSymbolicNode(String name) {
		if (nodes.containsKey(name)) {
			Node n = nodes.get(name).getDestination();
			if (!(n instanceof SymbolicNode)) {
				throw new IllegalArgumentException("Existing node with given name is not a symbolic node: " + name);
			}
			
			return (SymbolicNode) n;
		}

		return null;
	}

	@Override
	public void removeSymbolicNode(String name) {
		if (!nodes.containsKey(name)) {
			throw new IllegalArgumentException("No node exists in this group with the name: " + name);
		}

		Node n = nodes.get(name).getDestination();
		if (!(n instanceof SymbolicNode)) {
			throw new IllegalArgumentException("The node with the given name is not a symbolic node: " + name);
		}

		removeNode(name, name.endsWith(SEPARATOR));
	}

	@Override
	public void removeSymbolicNode(SymbolicNode s) {
		for (String n : nodes.keySet()) {
			NodeLink l = nodes.get(n);
			if (l.getDestination().equals(s)) {
				removeNode(n, n.endsWith(SEPARATOR));
				return;
			}
		}
	
		throw new IllegalArgumentException("Given symbolic node does not exist in this group");
	}

	@Override
	public boolean isGroupNode() {
		return true;
	}

	@Override
	public String findLinkedNodeName(Node node) {
		for (Entry<String, NodeLink> e : nodes.entrySet()) {
			if (e.getValue().getDestination() == node) {
				return e.getKey();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(super.toString());
		for (String n : nodes.keySet()) {
			appendNodeString(s, n);
		}

		return s.toString();
	}

	protected void appendNodeString(StringBuilder s, String n) {
		s.append(INDENT);
		s.append(n);
		Node node = nodes.get(n).getDestination();
		if (node instanceof SymbolicNode) {
			s.append('@');
		} else if (node instanceof GroupNode) {
			s.append('/');
		}
//		else {
//				s.append(String.format("(%d)", node.getID()));
//	}
		s.append('\n');
	}

	@Override
	public synchronized Iterator<String> getNodeNameIterator() {
		synchronized (nodes) {
			return new LinkedHashSet<>(nodes.keySet()).iterator();
		}
	}

	@Override
	public List<ILazyDataset> getDatasets(final String name) {
		final ArrayList<ILazyDataset> list = new ArrayList<>();

		for (NodeLink l : this) {
			findDatasets(name, list, l);
		}
		return list;
	}

	private void findDatasets(final String name, final List<ILazyDataset> list, NodeLink link) {
		String oName = link.getName();
		while (link != null && link.isDestinationSymbolic()) {
			SymbolicNode s = (SymbolicNode) link.getDestination();
			link = s.getNodeLink();
		}
		Node n = link == null ? null : link.getDestination();

		if (n == null) {
			return;
		}

		if (n instanceof GroupNode groupNode) {
			for (NodeLink l : groupNode) {
				findDatasets(name, list, l);
			}
		} else if (n instanceof DataNode dataNode) {
			if (oName.equals(name)) {
				ILazyDataset dataset = dataNode.getDataset();
				if (dataset != null && !list.contains(dataset)) {
					list.add(dataset);
				}
			}
		}
	}

	@Override
	public NodeLink findNodeLink(String pathname) {
		String attr = null;
		int a = pathname.lastIndexOf(ATTRIBUTE);
		if (a == 0) {
			throw new IllegalArgumentException("Attribute only path not allowed");
		} else if (a > 0) {
			attr = pathname.substring(a + 1);
			pathname = pathname.substring(0, a);
		}

		int i = pathname.indexOf(SEPARATOR);
		if (i == 0) {
			pathname = pathname.substring(1);
			i = pathname.indexOf(SEPARATOR);
		}

		String link = i < 0 ? pathname : pathname.substring(0, i);
		if (nodes.containsKey(link)) {
			NodeLink nl = nodes.get(link);
			while (nl != null && nl.isDestinationSymbolic()) {
				nl = ((SymbolicNode) nl.getDestination()).getNodeLink();
			}
			if (nl == null) {
				return null;
			}
			if (i < 0) {
				return checkAttribute(nl, attr);
			} else if (nl.isDestinationGroup()) {
				String path = pathname.substring(i + 1);
				if (path.isEmpty()) { // pathname ended in SEPARATOR
					return checkAttribute(nl, attr);
				}
				return checkAttribute(((GroupNode) nl.getDestination()).findNodeLink(path), attr);
			}
		}
		return null;
	}

	@Override
	public Node findNode(String pathName) {
		NodeLink link = findNodeLink(pathName);
		return link == null ? null : link.getDestination();
	}

	static NodeLink checkAttribute(NodeLink nl, String attr) {
		if (attr == null || nl == null) {
			return nl;
		}

		Node n = nl.getDestination();
		if (n != null && n.containsAttribute(attr)) {
			return nl;
		}
		return null;
	}

	@Override
	public Iterator<NodeLink> iterator() {
		return nodes.values().iterator();
	}

	@Override
	public Set<String> getNames() {
		synchronized (nodes) {
			return new LinkedHashSet<>(nodes.keySet());
		}
	}

	@Override
	public Set<String> getGroupNodeNames() {
		return getNames().stream().filter(this::containsGroupNode).collect(toCollection(LinkedHashSet::new));
	}
	
	@Override
	public Set<String> getDataNodeNames() {
		return getNames().stream().filter(this::containsDataNode).collect(toCollection(LinkedHashSet::new));
	}

	@Override
	public Set<String> getSymbolicNodeNames() {
		return getNames().stream().filter(this::containsSymbolicNode).collect(toCollection(LinkedHashSet::new));
	}
	
	public boolean hasSymbolicNode(String nodeName) {
		final Node node = getNode(nodeName);
		return node != null && node.isSymbolicNode();
	}
	
}
