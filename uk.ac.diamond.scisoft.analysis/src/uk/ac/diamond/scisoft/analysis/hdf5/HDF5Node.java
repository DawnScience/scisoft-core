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
			out.append(ATTRIBUTE);
			out.append(a);
			out.append(" = ");
			out.append(attributes.get(a));
			out.append('\n');
		}

		return out.toString();
	}
}
