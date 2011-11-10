/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.hdf5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

/**
 * A group acts like a file system directory. It can contain a reference to a global pool of nodes which is used for
 * checking linked nodes
 */
public class HDF5Group extends HDF5Node {
	protected Map<Long, HDF5Node> pool; // global pool of nodes

	public void setGlobalPool(Map<Long, HDF5Node> globalPool) {
		pool = globalPool;
	}

	public Map<Long, HDF5Node> getGlobalPool() {
		return pool;
	}

	private int datasets;
	private int groups;
	private final Map<String, HDF5NodeLink> nodes;

	public HDF5Group(final long oid) {
		super(oid);
		datasets = 0;
		groups = 0;
		nodes = new LinkedHashMap<String, HDF5NodeLink>();
	}

	public HDF5NodeLink getNodeLink(String name) {
		return nodes.get(name);
	}

	public int getNumberOfGroups() {
		return groups;
	}

	public boolean containsGroup(final String name) {
		return nodes.containsKey(name) && nodes.get(name).getDestination() instanceof HDF5Group;
	}

	public void addGroup(final String path, final String name, final HDF5Group g) {
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
		nodes.put(name, new HDF5NodeLink(path, name, this, g));
	}

	public void addGroup(final String path, final String name, final HDF5SymLink g) {
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
		nodes.put(name, new HDF5NodeLink(path, name, this, g));
	}

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

	public int getNumberOfDatasets() {
		return datasets;
	}

	public boolean containsDataset(final String name) {
		return nodes.containsKey(name) && nodes.get(name).getDestination() instanceof HDF5Dataset;
	}

	public void addDataset(final String path, final String name, final HDF5Dataset d) {
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
		nodes.put(name, new HDF5NodeLink(path, name, this, d));
	}

	public void addDataset(final String path, final String name, final HDF5SymLink d) {
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
		nodes.put(name, new HDF5NodeLink(path, name, this, d));
	}

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

	public void addNode(String path, String name, HDF5Node node) {
		if (node == null)
			return;

		if (node instanceof HDF5SymLink) {
			if (name.endsWith(SEPARATOR)) {
				addGroup(path, name, (HDF5SymLink) node);
			} else {
				addDataset(path, name, (HDF5SymLink) node);
			}
		} else if (node instanceof HDF5Dataset) {
			addDataset(path, name, (HDF5Dataset) node);
		} else if (node instanceof HDF5Group) {
			addGroup(path, name, (HDF5Group) node);
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

	public Iterator<HDF5NodeLink> getNodeLinkIterator() {
		return nodes.values().iterator();
	}

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

		final Iterator<HDF5NodeLink> iter = getNodeLinkIterator();

		while (iter.hasNext()) {
			findDatasets(name, list, iter.next());
		}
		return list;
	}

	private void findDatasets(final String name, final List<ILazyDataset> list, final HDF5NodeLink link) {
		HDF5Node n = null;
		if (link.isDestinationASymLink()) {
			if (link.getName().equals(name)) {
			}
			HDF5SymLink slink = (HDF5SymLink) link.getDestination();
			if (slink.isDataset())
				n = slink.getNode();
		} else {
			n = link.getDestination();
		}

		if (n == null)
			return;

		if (n instanceof HDF5Group) {
			final Iterator<HDF5NodeLink> iter = ((HDF5Group) n).getNodeLinkIterator();

			while (iter.hasNext()) {
				findDatasets(name, list, iter.next());
			}
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
		int i = pathname.indexOf(HDF5Node.SEPARATOR);

		if (i == 0) {
			pathname = pathname.substring(1);
			i = pathname.indexOf(HDF5Node.SEPARATOR);
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
		}
		return null;
	}
}
