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
