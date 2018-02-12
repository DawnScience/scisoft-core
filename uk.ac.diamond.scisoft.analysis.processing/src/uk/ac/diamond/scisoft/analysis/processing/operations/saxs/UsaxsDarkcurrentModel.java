/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


//@author Tim Snow


public class UsaxsDarkcurrentModel extends AbstractOperationModel {
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the location of the darkcurrent file
	@OperationModelField(hint="Darkcurrent file location", file = FileType.EXISTING_FILE, label = "File containing the darkcurrent data", fieldPosition = 0)
	private String darkcurrentScanFilepath = "Darkcurrent.nxs";
	
	
	// Set up the getter...
	public String getDarkcurrentScanFilepath() {
		return darkcurrentScanFilepath;
	}
	
	
	// and setter.
	public void setDarkcurrentScanFilepath(String darkcurrentScanFilepath) {
		firePropertyChange("darkcurrentScanFilepath", this.darkcurrentScanFilepath, this.darkcurrentScanFilepath = darkcurrentScanFilepath);
	}
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the location of the dataset
	@OperationModelField(dataset = "getDarkcurrentScanFilepath", label = "Dataset path", fieldPosition = 2)
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