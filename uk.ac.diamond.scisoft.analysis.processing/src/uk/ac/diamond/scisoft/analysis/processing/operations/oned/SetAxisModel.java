/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.oned;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


//@author Tim Snow


public class SetAxisModel extends AbstractOperationModel {
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the location of the dataset
	@OperationModelField(dataset = "filePath", label = "Axis dataset", fieldPosition = 0)
	String datasetLocation = "";
	
	
	// Set up the getter...
	public String getDatasetLocation() {
		return datasetLocation;
	}
	
	
	// and setter.
	public void setDatasetLocation(String datasetLocation) {
		firePropertyChange("datasetLocation", this.datasetLocation, this.datasetLocation = datasetLocation);
	}
}