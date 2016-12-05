/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.export;


// Imports from org.eclipse
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


//The model for exporting 2D BSL files 


// @author Tim Snow


// Let's find somewhere to save this file!
public class Export2DBSLModel extends AbstractOperationModel{

	// Where should we be exporting to?	
	@OperationModelField(label = "Output Directory", hint="Enter the path to output directory", file = FileType.EXISTING_FOLDER, fieldPosition = 1)
	private String outputDirectoryPath = "";

	// Now the getters and setters
	public String getOutputDirectoryPath() {
		return outputDirectoryPath;
	}

	public void setOutputDirectoryPath(String outputDirectoryPath) {
		firePropertyChange("outputDirectoryPath", this.outputDirectoryPath, this.outputDirectoryPath = outputDirectoryPath);
	}
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the Scalers data
	@OperationModelField(dataset = "filePath", label = "Path to Scalers data", fieldPosition = 2)
	private String scalersFilePath = "/entry1/Scalers/data";
	
	// Set up the getter...
	public String getScalersFilePath() {
		return scalersFilePath;
	}

	// and setter.
	public void setScalersFilePath(String scalersFilePath) {
		firePropertyChange("scalersFilePath", this.scalersFilePath, this.scalersFilePath = scalersFilePath);
	}
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "filePath", label = "Path to count time", fieldPosition = 3)
	private String countTimeFilePath = "/entry1/Scalers/count_time";
	
	// Set up the getter...
	public String getCountTimeFilePath() {
		return countTimeFilePath;
	}

	// and setter.
	public void setCountTimeFilePath(String countTimeFilePath) {
		firePropertyChange("countTimeFilePath", this.countTimeFilePath, this.countTimeFilePath = countTimeFilePath);
	}
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "filePath", label = "Path to wait time", fieldPosition = 4)
	private String waitTimeFilePath = "/entry1/Scalers/wait_time";
	
	// Set up the getter...
	public String getWaitTimeFilePath() {
		return waitTimeFilePath;
	}

	// and setter.
	public void setWaitTimeFilePath(String waitTimeFilePath) {
		firePropertyChange("waitTimeFilePath", this.waitTimeFilePath, this.waitTimeFilePath = waitTimeFilePath);
	}
}
