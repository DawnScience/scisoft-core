/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.ExternalDataModel;

public class ConcatenatingTwoDatasetsModel extends ExternalDataModel {

	enum ConcatenationAxis {
		AXIS_0(0),
		AXIS_1(1);
		
		private final int axis;
		
		ConcatenationAxis(int axis) {
			this.axis = axis;
		}
		
		public int getAxis() {
			return axis;
		}
		
		@Override
		public String toString() {
			return String.format("%d", this.axis);
		}
	}
	
	@OperationModelField(hint="Dataset name (leave empty to use input dataset name", editable = true, label = "Dataset name", fieldPosition = 1)
	private String datasetName = "";
	
	@OperationModelField(hint="The axis along which the concatenation will occur", editable = true, label = "Concatenation axis", fieldPosition = 2)
	private ConcatenationAxis axis = ConcatenationAxis.AXIS_0;
	
	public ConcatenationAxis getAxis() {
		return axis;
	}
	
	public void setAxis(ConcatenationAxis axis) {
		firePropertyChange("axis", this.axis, this.axis = axis);
	}
	
	public String getDatasetName() {
		return datasetName;
	}
	
	public void setDatasetName(String datasetName) {
		firePropertyChange("datasetName", this.datasetName, this.datasetName = datasetName);
	}
}
