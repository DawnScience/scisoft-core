/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.hdf5;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Base class for HDF5 objects such as groups and datasets
 */
public class HDF5Node implements Serializable {
	protected LinkedHashMap<String, HDF5Attribute> attributes;
	protected static final String INDENT = "    ";
	private final long id;

	/**
	 * String used as separator between a group and its children
	 */
	public static final String SEPARATOR = "/";

	/**
	 * String used as separator between a node and its attributes
	 */
	public static final String ATTRIBUTE = "@";

	/**
	 * Construct a HDF5 node with given object ID
	 * @param oid object ID
	 */
	public HDF5Node(final long oid) {
		attributes = new LinkedHashMap<String, HDF5Attribute>();
		id = oid;
	}

	/**
	 * @return ID
	 */
	public long getID() {
		return id;
	}

	/**
	 * @return number of attributes on node
	 */
	public int getNumberOfAttributes() {
		return attributes.size();
	}

	/**
	 * Get attribute of given name from node
	 * @param name
	 * @return attribute (can be null if there is no attribute of given name)
	 */
	public HDF5Attribute getAttribute(final String name) {
		return attributes.get(name);
	}

	/**
	 * Add given attribute to node
	 * @param a
	 */
	public void addAttribute(final HDF5Attribute a) {
		attributes.put(a.getName(), a);
	}

	/**
	 * @param name
	 * @return true if node contains attribute of given name
	 */
	public boolean containsAttribute(final String name) {
		return attributes.containsKey(name);
	}

	/**
	 * @return iterator over attribute names in node
	 */
	public Iterator<String> getAttributeNameIterator() {
		return attributes.keySet().iterator();
	}

	/**
	 * @return iterator over attribute names in node
	 */
	public Iterator<HDF5Attribute> getAttributeIterator() {
		return attributes.values().iterator();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		for (String a : attributes.keySet()) {
			out.append(INDENT);
			out.append(ATTRIBUTE);
			out.append(a);
			out.append(" = ");
			out.append(attributes.get(a));
			out.append('\n');
		}

		return out.toString();
	}
}
