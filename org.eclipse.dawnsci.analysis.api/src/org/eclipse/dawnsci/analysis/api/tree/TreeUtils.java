/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.api.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TreeUtils {

	
	private final static Logger logger = LoggerFactory.getLogger(TreeUtils.class);
	
	/**
	 * Get the path which ends with {@value Node#SEPARATOR} if it refers to a group
	 * @param tree
	 * @param node (can be null for root node)
	 * @return path to node, return null if not found
	 */
	public static String getPath(Tree tree, Node node) {
		if (node == null) {
			return Tree.ROOT;
		}
		GroupNode g = tree.getGroupNode();
		if (g == node) {
			return Tree.ROOT;
		}
		String p = getPathDepthFirst(Tree.ROOT, g, node);
		if (node instanceof GroupNode && p != null && !p.endsWith(Node.SEPARATOR)) {
			p = p + Node.SEPARATOR;
		}
		return p;
	}
	
	/**
	 * Returns the node at the given path in the tree, or <code>null</code> if there is no such node.
	 * The path given must be an absolute path, i.e. start with '/'.
	 * @param tree tree
	 * @param path path
	 * @return node, or <code>null</code>
	 */
	public static Node getNode(Tree tree, String path) {
		final String[] pathSegments = path.split(Node.SEPARATOR);
		if (!"".equals(pathSegments[0])) {
			// first character must be '/'
			throw new IllegalArgumentException("Invalid path, must be absolute, was: " + path);
		}
		
		Node node = tree.getGroupNode();
		for (int i = 1; i < pathSegments.length; i++) {
			if (!(node instanceof GroupNode)) {
				return null;
			}
			
			node = ((GroupNode) node).getNode(pathSegments[i]);
		}
		
		return node;
	}
	
	private static String getPathDepthFirst(final String parent, final GroupNode group, final Node node) {
		for (NodeLink l : group) {
			String current = parent + l.getName();
			if (l.getDestination() == node) {
				return current;
			} else if (l.isDestinationGroup()) {
				String p = getPathDepthFirst(current + Node.SEPARATOR, (GroupNode) l.getDestination(), node);
				if (p != null)
					return p;
			}
		}
		return null;
	}
	
	/**
	 * Breadth first search of nexus tree using visitor to determine whether a node link matches
	 * those searched for
	 * @param file
	 * @param finder
	 * @param findFirst
	 * @param mon
	 * @return map with full path as key and NodeLink as value
	 */
	public static Map<String, NodeLink> treeBreadthFirstSearch(GroupNode file, IFindInTree finder, boolean findFirst, IMonitor mon) {
		return treeBreadthFirstSearch(file, finder, findFirst, false, mon);
	}
	
	/**
	 * Breadth first search of nexus tree using visitor to determine whether a node link matches
	 * those searched for
	 * @param file
	 * @param finder
	 * @param findFirst
	 * @param maintainOrder
	 * @param mon
	 * @return map with full path as key and NodeLink as value
	 */
	public static Map<String, NodeLink> treeBreadthFirstSearch(GroupNode file, IFindInTree finder, boolean findFirst, boolean maintainOrder, IMonitor mon) {
		
		Map<String, NodeLink> map = maintainOrder ? new LinkedHashMap<String, NodeLink>() :  new HashMap<String, NodeLink>();
		List<NamedNodeLink> out = new ArrayList<NamedNodeLink>();
		
		Queue<NamedNodeLink> queue = new LinkedList<NamedNodeLink>();
		for (NodeLink nxObject : file) {
			Node node = nxObject.getDestination();
			if (finder.found(nxObject)) {
				
				if (findFirst) {
					map.put(nxObject.getName(),nxObject);
					return map;
				}
				out.add(new NamedNodeLink(nxObject.getName(), nxObject));
			}
			
			if(node instanceof GroupNode) {
				queue.add(new NamedNodeLink(nxObject.getName(), nxObject));
			}
		}
		
		while (queue.size() != 0) {
			NamedNodeLink group = queue.poll();
			GroupNode dest = (GroupNode)group.link.getDestination();
			for (NodeLink nxObject: dest) {
				Node node = nxObject.getDestination();
				if (finder.found(nxObject)) {
					if (findFirst) {
						map.put(group.name + Node.SEPARATOR + nxObject.getName(),nxObject);
						return map;
					}
					out.add(new NamedNodeLink(group.name + Node.SEPARATOR + nxObject.getName(), nxObject));
				}
				
				if(node instanceof GroupNode) {
					queue.add(new NamedNodeLink(group.name + Node.SEPARATOR + nxObject.getName(), nxObject));
				}
			}
			
			if (mon != null && mon.isCancelled()) return null;
		}
		
		for (NamedNodeLink nnl : out) map.put(nnl.name, nnl.link);
		
		return map;
	}
	
	/**
	 * Get a map of all the unique data nodes in a tree
	 * Map keys are the data nodes, map values are the shortest path to each node
	 * 
	 * @param node
	 * @return map
	 */
	public static Map<DataNode,String> getUniqueDataNodes(GroupNode node) {
		HashMap<DataNode, String> nodes = new HashMap<>();
		
		addNodes(node,nodes,"");
		
		return nodes;
		
		
	}
	
	/**
	 * Iterate though a tree and convert ILazyDatasets in DataNodes
	 * to IDatasets, allowing the tree to be written into a file.
	 * 
	 * WARNING: only use on tree structures containing metadata not large datasets
	 * as they will be loaded into memory
	 * 
	 * @param node
	 */
	public static void recursivelyLoadDataNodes(GroupNode node) {
		
		Iterator<String> iterator = node.getNodeNameIterator();
		
		while (iterator.hasNext()) {
			Node n = node.getNode(iterator.next());
			if (n instanceof GroupNode) {
				recursivelyLoadDataNodes((GroupNode)n);
			} else if (n instanceof DataNode){
				DataNode d = (DataNode)n;
				if (d.getDataset() == null) {
					continue;
				}
				try {
					d.setDataset(d.getDataset().getSlice());
				} catch (DatasetException e) {
					logger.error("Could not load lazydataset", e);
				}
			}
		}
	}
	
	private static void addNodes(GroupNode node, Map<DataNode,String> map, String name) {
		Iterator<String> it = node.getNodeNameIterator();
		while (it.hasNext()) {
			String next = it.next();
			Node n = node.getNode(next);
			if (n instanceof DataNode && !map.containsKey(n)) map.put((DataNode)n, name + Node.SEPARATOR + next);
			else if (n instanceof GroupNode) addNodes((GroupNode)n, map, name + Node.SEPARATOR + next);
		}
		
	}
	
	public int getShortestUniqueSeparatorCrop(Set<String> names) {
		List<String> reversed = new ArrayList<>(names.size());
		for (String name : names) reversed.add(new StringBuilder(name).reverse().toString());
		
		Collections.sort(reversed);
		
		int forUnique = 0;
		String[] current = null;
		
		for (String s : reversed) {
			if (current == null) current = s.split(Node.SEPARATOR);
			else {
				String[] split = s.split(Node.SEPARATOR);
				int min = minForUnequal(current, split);
				forUnique = Math.max(min, forUnique);
			}
		}
		
		return forUnique;
		
	}
	
	private int minForUnequal(String[] a, String[] b) {
		
		int size = Math.min(a.length, b.length);
		
		for (int i = 0; i < size; i++) {
			if (!a[i].equals(b[i])) return i;
		}
		
		return size;
	}
	
	private static class NamedNodeLink {
		public String name;
		public NodeLink link;
		
		public NamedNodeLink(String name, NodeLink link) {
			this.name = name;
			this.link = link;
		}
		
	}

	private static final String UPDIR = "/..";
	private static final String CURDIR = "/.";

	/**
	 * Remove ".." and "." from pathname
	 * @param pathname
	 * @return canonical form of pathname
	 */
	public static String canonicalizePath(final String pathname) {
		if (!pathname.contains(UPDIR) && !pathname.contains(CURDIR))
			return pathname;
	
		StringBuilder path = new StringBuilder(pathname);
		int i = 0;
		while ((i = path.indexOf(UPDIR)) >= 0) {
			int k;
			int j = i;
			do {
				k = j;
				j = path.lastIndexOf(Node.SEPARATOR, k - 1);
				if (j <= 0) {
					// can not find SEPARATOR or preserve ROOT
					path.insert(0, Tree.ROOT);
					i++;
					j++;
				}
			} while (path.substring(j, k).equals(CURDIR));
			path.delete(j, i + UPDIR.length());
		}
	
		while ((i = path.indexOf(CURDIR)) >= 0) {
			path.delete(i, i + CURDIR.length());
		}
	
		return path.toString();
	}

	/**
	 * Join together parent and given path
	 * @param parent
	 * @param path
	 * @return joined path
	 */
	public static String join(String parent, String path) {
		if (!parent.endsWith(Node.SEPARATOR)) {
			parent = parent.concat(Node.SEPARATOR);
		}
		return parent.concat(path);
	}

	/**
	 * Join together parent and given path components
	 * @param parent
	 * @param paths path components
	 * @return joined path
	 */
	public static String join(String parent, String... paths) {
		StringBuilder all = new StringBuilder(parent);
		if (parent.endsWith(Node.SEPARATOR)) {
			all.setLength(parent.length() - Node.SEPARATOR.length());
		}
		for (String p : paths) {
			all.append(Node.SEPARATOR);
			all.append(p);
		}
		return all.toString();
	}

	/**
	 * Find node link in tree from path of given type
	 * @param tree
	 * @param path
	 * @param isGroup true if want link to group, otherwise link to dataset
	 * @return group node link or null if not found or is not a group
	 */
	public static NodeLink findNodeLink(Tree tree, String path, boolean isGroup) {
		if (path == null) {
			return null;
		}
		NodeLink link = tree.findNodeLink(path);

		if (link == null) {
			logger.warn("'{}' could not be found", path);
			return null;
		}

		if (isGroup) {
			if (!link.isDestinationGroup()) {
				logger.warn("'{}' was not a group", link.getName());
				return null;
			}
		} else if (!link.isDestinationData()) {
			logger.warn("'{}' was not a dataset", link.getName());
			return null;
		}
		return link;
	}
}
