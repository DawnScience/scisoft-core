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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.StringDataset;

/**
 * Represent an attribute using a dataset
 */
public class HDF5Attribute {

	private String node;
	private String name;
	private String type;
	private AbstractDataset value;

	/**
	 * Create an attribute with node, name and value
	 * @param nodeName
	 * @param attrName
	 * @param attrValue (usually, this is a Java array)
	 */
	public HDF5Attribute(final String nodeName, final String attrName, final Object attrValue) {
		this(nodeName, attrName, attrValue, false);
	}

	/**
	 * Create an attribute with node, name, value and sign
	 * @param nodeName
	 * @param attrName
	 * @param attrValue (usually, this is a Java array)
	 * @param isUnsigned true if items are unsigned but held in signed primitives
	 */
	public HDF5Attribute(final String nodeName, final String attrName, final Object attrValue, boolean isUnsigned) {
		node = nodeName;
		name = attrName;
		value = AbstractDataset.array(attrValue, isUnsigned);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * @return first element as string
	 */
	public String getFirstElement() {
		return value.getString(0);
	}

	public void setTypeName(String name) {
		type = name;
	}

	public String getTypeName() {
		return type;
	}

	public AbstractDataset getValue() {
		return value;
	}

	public void setValue(Object obj) {
		setValue(obj, false);
	}

	public void setValue(Object obj, boolean isUnsigned) {
		value = AbstractDataset.array(obj, isUnsigned);
	}

	public int[] getShape() {
		return value.getShape();
	}

	public int getRank() {
		return value.getRank();
	}

	public int getSize() {
		return value.getSize();
	}

	public String getNode() {
		return node;
	}

	public String getFullName() {
		return node + HDF5Node.ATTRIBUTE + name;
	}

	public boolean isString() {
		return value != null && (value instanceof StringDataset);
	}
}
