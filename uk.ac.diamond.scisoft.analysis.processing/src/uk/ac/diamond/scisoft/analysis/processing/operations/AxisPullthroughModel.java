/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations;


import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

//
// @ Author: Tim Snow
//
// A model for a processing operation to pull through additional axes
//
public class AxisPullthroughModel extends AbstractOperationModel {
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the axis to pull through
	@OperationModelField(dataset = "filePath", label = "Axis to pull through", fieldPosition = 1)
	private String axisPath = "";
	
	// Set up the getter...
	public String getAxisPath() {
		return axisPath;
	}

	// and setter.
	public void setAxisPath(String axisPath) {
		firePropertyChange("axisPath", this.axisPath, this.axisPath = axisPath);
	}
}
