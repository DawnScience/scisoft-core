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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Models the mappings for a primary data field from the other data fields within the same device. 
 * @author Matthew Dickie
 */
public class PrimaryDataFieldModel {
	
	record AxisFieldDimensionModel(Integer defaultAxisDimension, int[] dimensionMappings) { }; // NOSONAR: should really override equals and hashcode for deep equality check, but not strictly necessary here
	
	private final Map<String, AxisFieldDimensionModel> axisFieldDimensionModels = new LinkedHashMap<>();

	public void addAxisField(String axisFieldName, Integer defaultAxisDimension,
			int[] dimensionMappings) {
		axisFieldDimensionModels.put(axisFieldName,
				new AxisFieldDimensionModel(defaultAxisDimension, dimensionMappings));
	}
	
	public List<String> getAxisFieldNames() {
		return new ArrayList<>(axisFieldDimensionModels.keySet());
	}
	
	public Integer getDefaultAxisDimension(String dataFieldName) {
		AxisFieldDimensionModel axisFieldDimensionModel = axisFieldDimensionModels.get(dataFieldName);
		if (axisFieldDimensionModel != null) {
			return axisFieldDimensionModel.defaultAxisDimension();
		}
		
		return null;
	}
	
	public int[] getDimensionMappings(String dataFieldName) {
		AxisFieldDimensionModel axisFieldDimensionModel = axisFieldDimensionModels.get(dataFieldName);
		if (axisFieldDimensionModel != null) {
			return axisFieldDimensionModel.dimensionMappings();
		}
		
		return null; // NOSONAR - null means use default dimension mappings for scan
	}
	
}
