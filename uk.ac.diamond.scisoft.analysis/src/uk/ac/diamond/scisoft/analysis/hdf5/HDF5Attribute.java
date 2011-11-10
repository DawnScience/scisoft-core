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

import java.lang.reflect.Array;

import ncsa.hdf.object.Attribute;
import ncsa.hdf.object.Datatype;

/**
 * Extends the attribute class to keep namespace uniform
 */
public class HDF5Attribute extends Attribute {

	private String type;

	public HDF5Attribute(final String attrName, final Datatype attrType, final long[] attrDims) {
		super(attrName, attrType, attrDims);
	}

	public HDF5Attribute(final Attribute a) {
		super(a.getName(), a.getType(), a.getDataDims(), a.getValue());
	}

	@Override
	public String getName() {
		return super.getName();
	}

	@Override
	public String toString() {
		return toString(",");
	}

	/**
	 * @return first element as string
	 */
	public String getFirstElement() {
		return Array.get(getValue(), 0).toString();
	}

	public void setTypeName(String name) {
		type = name;
	}

	public String getTypeName() {
		return type;
	}
}
