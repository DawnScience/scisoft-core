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
	 * String used a separator between a group and its children
	 */
	public static final String SEPARATOR = "/";

	public HDF5Node(final long oid) {
		attributes = new LinkedHashMap<String, HDF5Attribute>();
		id = oid;
	}

	public long getID() {
		return id;
	}

	public int getNumberOfAttributes() {
		return attributes.size();
	}

	public HDF5Attribute getAttribute(final String name) {
		return attributes.get(name);
	}

	public void addAttribute(final HDF5Attribute a) {
		attributes.put(a.getName(), a);
	}

	public boolean containsAttribute(final String name) {
		return attributes.containsKey(name);
	}

	public Iterator<String> attributeNameIterator() {
		return attributes.keySet().iterator();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		for (String a : attributes.keySet()) {
			out.append(INDENT);
			out.append('@');
			out.append(a);
			out.append(" = ");
			out.append(attributes.get(a));
			out.append('\n');
		}

		return out.toString();
	}
}
