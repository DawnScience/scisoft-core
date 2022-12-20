/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus.builder.data.impl;

import java.util.Arrays;

import org.eclipse.dawnsci.nexus.NXdata;

/**
 * A model for how the dimensions of a data field correspond to the dimensions
 * of the primary data field (i.e. the <code>@signal</code> field ) of an {@link NXdata} group.
 */
public class AxisFieldModel extends DataFieldModel {
	
	private Integer axisDimension = null;
	
	private boolean defaultAxis = false;
	
	private int[] dimensionMapping = null;
	
	public AxisFieldModel(String sourceFieldName, int fieldRank) {
		super(sourceFieldName, fieldRank);
	}
	
	/**
	 * Sets the axis dimension for this data field to the given value.
	 * This is the dimension of the signal data field of the {@link NXdata} group
	 * that this field is an axis for, if any.
	 * @param axisDimension default axis dimension index
	 */
	public void setAxisDimension(Integer axisDimension) {
		this.axisDimension = axisDimension;
	}

	public Integer getAxisDimension() {
		return axisDimension;
	}
	
	public void setDefaultAxis(boolean defaultAxis) {
		this.defaultAxis = defaultAxis; 
	}
	
	public boolean isDefaultAxis() {
		return defaultAxis;
	}

	/**
	 * Sets the dimension mapping for the given field to the given value.
	 * This is the mapping from the dimensions of this field to the dimensions
	 * of the default data field of the {@link NXdata} group.
	 * @param dimensionMapping dimension mappings
	 */
	public void setDimensionMapping(int... dimensionMapping) {
		this.dimensionMapping = dimensionMapping;
	}

	public int[] getDimensionMapping() {
		return dimensionMapping;
	}

	@Override
	protected void appendMemberFields(StringBuilder sb) {
		super.appendMemberFields(sb);
		sb.append(", axisDimension = " + axisDimension);
		sb.append(", dimensionMapping = " + Arrays.toString(dimensionMapping));
	}
	
}