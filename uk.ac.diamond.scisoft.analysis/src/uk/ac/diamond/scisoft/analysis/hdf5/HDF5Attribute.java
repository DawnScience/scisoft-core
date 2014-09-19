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

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;

/**
 * Represent an attribute using a dataset
 */
public class HDF5Attribute implements Serializable {
	private HDF5File file;
	private String node;
	private String name;
	private String type;
	private Dataset value;

	/**
	 * Create an attribute with file, node, name, value and sign
	 * @param hdf5File
	 * @param nodeName
	 * @param attrName
	 * @param attrValue (usually, this is a Java array)
	 * @param isUnsigned true if items are unsigned but held in signed primitives
	 */
	public HDF5Attribute(final HDF5File hdf5File, final String nodeName, final String attrName, final Object attrValue, final boolean isUnsigned) {
		file = hdf5File;
		node = nodeName;
		name = attrName;
		value = DatasetFactory.createFromObject(attrValue, isUnsigned);
	}

	/**
	 * @return hdf5 file of attribute
	 */
	public HDF5File getHDF5File() {
		return file;
	}

	/**
	 * @return name of attribute
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		// Fix to showing values in Value view - you cannot see the attribute
		// values so there would be no point of the view otherwise!
		if (value.getSize()==1 && value.getRank()==1) {
			return "["+value.getString(0)+"]";
		}
		return value.toString();
	}

	/**
	 * @return first element as string
	 */
	public String getFirstElement() {
		return value.getString(0);
	}

	/**
	 * Set name of attribute type
	 * @param name
	 */
	public void setTypeName(String name) {
		type = name;
	}

	/**
	 * @return name of attribute type
	 */
	public String getTypeName() {
		return type;
	}

	/**
	 * Get dataset holding value(s) of attribute
	 * @return dataset
	 */
	public Dataset getValue() {
		return value;
	}

	/**
	 * Set value of attribute from given object
	 * @param obj
	 */
	public void setValue(Object obj) {
		setValue(obj, false);
	}

	/**
	 * Set value of attribute from given object
	 * @param obj
	 * @param isUnsigned
	 *            if true, interpret integer values as unsigned by increasing element bit width
	 */
	public void setValue(Object obj, boolean isUnsigned) {
		value = DatasetFactory.createFromObject(obj, isUnsigned);
	}

	/**
	 * @return shape of attribute dataset
	 */
	public int[] getShape() {
		return value.getShape();
	}

	/**
	 * @return rank of attribute dataset
	 */
	public int getRank() {
		return value.getRank();
	}

	/**
	 * Get number of items in attribute dataset
	 * @return size
	 */
	public int getSize() {
		return value.getSize();
	}

	/**
	 * Get node name
	 * @return name
	 */
	public String getNode() {
		return node;
	}

	/**
	 * Get full name of attribute including node and attribute name
	 * @return name
	 */
	public String getFullName() {
		return node + HDF5Node.ATTRIBUTE + name;
	}

	/**
	 * @return true if attribute contains strings
	 */
	public boolean isString() {
		return value != null && (value instanceof StringDataset);
	}
}
