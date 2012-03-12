/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.hdf5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

/**
 * A group acts like a file system directory. It holds a map of node links and can contain a
 * reference to a global pool of nodes which is used for checking linked nodes
 */
public class HDF5Group extends HDF5Node implements Iterable<HDF5NodeLink> {
	protected Map<Long, HDF5Node> pool; // global pool of nodes

	/**
	 * Set a reference to the global pool of nodes
	 * @param globalPool
	 */
	public void setGlobalPool(Map<Long, HDF5Node> globalPool) {
		pool = globalPool;
	}

	/**
	 * @return global pool of cached nodes
	 */
	public Map<Long, HDF5Node> getGlobalPool() {
		return pool;
	}

	private int datasets;
	private int groups;
	private final Map<String, HDF5NodeLink> nodes;

	/**
	 * Construct a HDF5 group with given object ID
	 * @param oid object ID
	 */
	public HDF5Group(final long oid) {
		super(oid);
		datasets = 0;
		groups = 0;
		nodes = new LinkedHashMap<String, HDF5NodeLink>();
	}

	/**
	 * @param name
	 * @return node link to child node of given name
	 */
	public HDF5NodeLink getNodeLink(String name) {
		return nodes.get(name);
	}

	/**
	 * @return number of nodelinks held in group
	 */
	public int getNumberOfNodelinks() {
		return nodes.size();
	}

	/**
	 * @return number of child groups in group
	 */
	public int getNumberOfGroups() {
		return groups;
	}

	/**
	 * @param name
	 * @return true if group contains child group of given name
	 */
	public boolean containsGroup(final String name) {
		return nodes.containsKey(name) && nodes.get(name).isDestinationAGroup();
	}

	/**
	 * Add node link
	 * @param link
	 */
	public void addNodeLink(final HDF5NodeLink link) {
		final String name = link.getName();
		if (nodes.containsKey(name)) {
			HDF5Node n = nodes.get(name).getDestination();
			if (n instanceof HDF5SymLink)
				n = ((HDF5SymLink) n).getNode();
			if (link.isDestinationADataset() && !(n instanceof HDF5Dataset)) {
				throw new IllegalArgumentException("Cannot add a group as there is a non-group of same name");
			}
			if (link.isDestinationAGroup() && !(n instanceof HDF5Group)) {
				throw new IllegalArgumentException("Cannot add a group as there is a non-dataset of same name");
			}
		}
		HDF5Node n = link.getDestination();
		if (n instanceof HDF5SymLink)
			n = ((HDF5SymLink) n).getNode();
		if (n instanceof HDF5Group) {
			groups++;
		} else {
			datasets++;
		}
		nodes.put(name, link);
	}

	/**
	 * Add (child) group with given path and name 
	 * @param file
	 * @param path
	 * @param name
	 * @param g group
	 */
	public void addGroup(final HDF5File file, final String path, final String name, final HDF5Group g) {
		if (nodes.containsKey(name)) {
			HDF5Node n = nodes.get(name).getDestination();
			if (n instanceof HDF5SymLink)
				n = ((HDF5SymLink) n).getNode();
			if (n instanceof HDF5Dataset) {
				throw new IllegalArgumentException("Cannot add a group as there is a dataset of same name");
			}
		} else {
			groups++;
		}
		nodes.put(name, new HDF5NodeLink(file, path, name, this, g));
	}

	/**
	 * Add linked node with given path and name
	 * @param file
	 * @param path
	 * @param name
	 * @param s symbolic link
	 */
	public void addSymlink(final HDF5File file, final String path, final String name, final HDF5SymLink s) {
		if (nodes.containsKey(name)) {
			HDF5Node n = nodes.get(name).getDestination();
			if (n instanceof HDF5SymLink)
				n = ((HDF5SymLink) n).getNode();
			if (n instanceof HDF5Dataset) {
				throw new IllegalArgumentException("Cannot add a group as there is a dataset of same name");
			}
		} else {
			if (name.endsWith(SEPARATOR)) {
				groups++;
			}
		}
		nodes.put(name, new HDF5NodeLink(file, path, name, this, s));
	}

	/**
	 * Get (child) group of given name 
	 * @param name
	 * @return group
	 */
	public HDF5Group getGroup(final String name) {
		if (nodes.containsKey(name)) {
			HDF5Node n = nodes.get(name).getDestination();
			if (n instanceof HDF5SymLink)
				n = ((HDF5SymLink) n).getNode();
			if (n instanceof HDF5Group)
				return (HDF5Group) n;
		}

		throw new IllegalArgumentException("No such group of given name");
	}

	/**
	 * Remove group of given name
	 * @param name
	 */
	public void removeGroup(final String name) {
		if (!nodes.containsKey(name))
			throw new IllegalArgumentException("No name exists in this group");

		HDF5Node n = nodes.get(name).getDestination();
		if (n instanceof HDF5SymLink)
			n = ((HDF5SymLink) n).getNode();
		if (n instanceof HDF5Dataset)
			throw new IllegalArgumentException("Group of given name does not exist in this group");

		nodes.remove(name);
		groups--;
	}

	/**
	 * Remove given group
	 * @param g group
	 */
	public void removeGroup(final HDF5Group g) {
		for (String n : nodes.keySet()) {
			HDF5NodeLink l = nodes.get(n);
			if (l.getDestination().equals(g)) {
				nodes.remove(n);
				groups--;
				return;
			}
		}
		throw new IllegalArgumentException("Given group does not exist in this group");
	}

	/**
	 * @return number of datasets held in group
	 */
	public int getNumberOfDatasets() {
		return datasets;
	}

	/**
	 * @param name
	 * @return true if group contains dataset of given name
	 */
	public boolean containsDataset(final String name) {
		return nodes.containsKey(name) && nodes.get(name).isDestinationADataset();
	}

	/**
	 * Add given dataset with given path and name 
	 * @param file
	 * @param path
	 * @param name
	 * @param d dataset
	 */
	public void addDataset(final HDF5File file, final String path, final String name, final HDF5Dataset d) {
		if (nodes.containsKey(name)) {
			HDF5Node n = nodes.get(name).getDestination();
			if (n instanceof HDF5SymLink)
				n = ((HDF5SymLink) n).getNode();
			if (n instanceof HDF5Group) {
				throw new IllegalArgumentException("Cannot add a dataset as there is a group of same name");
			}
		} else {
			datasets++;
		}
		nodes.put(name, new HDF5NodeLink(file, path, name, this, d));
	}

	/**
	 * Get dataset of given name
	 * @param name
	 * @return dataset
	 */
	public HDF5Dataset getDataset(final String name) {
		if (nodes.containsKey(name)) {
			HDF5Node n = nodes.get(name).getDestination();
			if (n instanceof HDF5SymLink)
				n = ((HDF5SymLink) n).getNode();
			if (n instanceof HDF5Dataset)
				return (HDF5Dataset) n;
		}

		throw new IllegalArgumentException("No such dataset of given name");
	}

	/**
	 * Remove dataset of given name
	 * @param name
	 */
	public void removeDataset(final String name) {
		if (!nodes.containsKey(name))
			throw new IllegalArgumentException("No name exists in this group");

		HDF5Node n = nodes.get(name).getDestination();
		if (n instanceof HDF5SymLink)
			n = ((HDF5SymLink) n).getNode();
		if (n instanceof HDF5Group)
			throw new IllegalArgumentException("Dataset of given name does not exist in this group");

		nodes.remove(name);
		datasets--;
	}

	/**
	 * Remove given dataset
	 * @param d dataset
	 */
	public void removeDataset(final HDF5Dataset d) {
		for (String n : nodes.keySet()) {
			HDF5NodeLink l = nodes.get(n);
			if (l.getDestination().equals(d)) {
				nodes.remove(n);
				datasets--;
				return;
			}
		}
		throw new IllegalArgumentException("Given dataset does not exist in this group");
	}

	/**
	 * Add given node with given path and name
	 * @param file
	 * @param path
	 * @param name
	 * @param node
	 */
	public void addNode(final HDF5File file, final String path, final String name, final HDF5Node node) {
		if (node == null)
			return;

		if (node instanceof HDF5SymLink) {
			addSymlink(file, path, name, (HDF5SymLink) node);
		} else if (node instanceof HDF5Dataset) {
			addDataset(file, path, name, (HDF5Dataset) node);
		} else if (node instanceof HDF5Group) {
			addGroup(file, path, name, (HDF5Group) node);
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(super.toString());
		for (String n : nodes.keySet()) {
			s.append(INDENT);
			s.append(n);
			HDF5Node node = nodes.get(n).getDestination();
			if (node instanceof HDF5SymLink)
				s.append('@');
			else if (node instanceof HDF5Group)
				s.append('/');
//			else
//				s.append(String.format("(%d)", node.getID()));
			s.append('\n');
		}

		return s.toString();
	}

	/**
	 * @return iterator over child names in group
	 */
	public Iterator<String> getNodeNameIterator() {
		return nodes.keySet().iterator();
	}

//	public List<String> getNodeNames() {
//		List<String> list = new ArrayList<String>();
//		list.addAll(nodes.keySet()); 
//		return list;
//	}

	/**
	 * Recursively find datasets of given name
	 * @param name
	 * @return list of (unique) datasets
	 */
	public List<ILazyDataset> getDatasets(final String name) {
		final ArrayList<ILazyDataset> list = new ArrayList<ILazyDataset>();

		for (HDF5NodeLink l : this)
			findDatasets(name, list, l);

		return list;
	}

	private void findDatasets(final String name, final List<ILazyDataset> list, final HDF5NodeLink link) {
		HDF5Node n = null;
		if (link.isDestinationASymLink()) {
			HDF5SymLink slink = (HDF5SymLink) link.getDestination();
			if (slink.isDataset())
				n = slink.getNode();
		} else {
			n = link.getDestination();
		}

		if (n == null)
			return;

		if (n instanceof HDF5Group) {
			for (HDF5NodeLink l : (HDF5Group) n)
				findDatasets(name, list, l);
		} else if (n instanceof HDF5Dataset) {
			if (link.getName().equals(name)) {
				ILazyDataset dataset = ((HDF5Dataset) n).getDataset();
				if (!list.contains(dataset))
					list.add(dataset);
			}
		}
	}

	/**
	 * Recursively find link to node given by path name 
	 * @param pathname
	 * @return node or null if not found
	 */
	public HDF5NodeLink findNodeLink(String pathname) {
		int i = pathname.indexOf(SEPARATOR);

		if (i == 0) {
			pathname = pathname.substring(1);
			i = pathname.indexOf(SEPARATOR);
		}

		String link = i < 0 ? pathname: pathname.substring(0, i);

		if (nodes.containsKey(link)) {
			HDF5NodeLink node = nodes.get(link);
			if (i < 0) {
				return node;
			}
			String path = pathname.substring(i+1);
			if (node.isDestinationAGroup()) {
				return ((HDF5Group) node.getDestination()).findNodeLink(path);
			}
		} else { // is attribute?
			i = link.indexOf(ATTRIBUTE);
			if (i > 0) {
				link = pathname.substring(0, i);
				String attr = pathname.substring(i+1);
				if (nodes.containsKey(link)) {
					HDF5NodeLink node = nodes.get(link);
					if (node.getDestination().containsAttribute(attr)) {
						return node;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @return iterator over links to children in group
	 */
	@Override
	public Iterator<HDF5NodeLink> iterator() {
		return nodes.values().iterator();
	}
}
