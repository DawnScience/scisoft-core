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
//A processing operation to pull through additional data into a results file
//
public class DataPullthroughModel extends AbstractOperationModel {
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the axis to pull through
	@OperationModelField(dataset = "filePath", label = "Dataset to pull through", fieldPosition = 1)
	private String datasetPath = "";
	
	// Set up the getter...
	public String getDatasetPath() {
		return datasetPath;
	}

	// and setter.
	public void setDatasetPath(String datasetPath) {
		firePropertyChange("datasetPath", this.datasetPath, this.datasetPath = datasetPath);
	}
}
