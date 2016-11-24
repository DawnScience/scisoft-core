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


//The model for exporting a NXcanSAS file 


// @author Tim Snow


// Let's find somewhere to save this file!
public class Export1DNXcanSASModel extends AbstractOperationModel{

	// Where should we be exporting to?	
	@OperationModelField(label = "Output Directory", hint="Enter the path to output directory", file = FileType.EXISTING_FOLDER, fieldPosition = 2)
	private String outputDirectoryPath = "";

	// Now the getters and setters
	public String getOutputDirectoryPath() {
		return outputDirectoryPath;
	}

	public void setOutputDirectoryPath(String outputDirectoryPath) {
		firePropertyChange("outputDirectoryPath", this.outputDirectoryPath, this.outputDirectoryPath = outputDirectoryPath);
	}


	// Should the files have a numerical prefix? Useful for multiframe files...
	@OperationModelField(label = "Pad with zeros", hint = "Should you want to have a fixed length for the appended frame number, useful for ordering in lists.", min = 1)
	private Integer paddingZeros = 5;

	// Now the getters and setters
	public Integer getPaddingZeros() {
		return paddingZeros;
	}

	public void setPaddingZeros(Integer paddingZeros) {
		firePropertyChange("paddingZeros", this.paddingZeros, this.paddingZeros = paddingZeros);
	}
}
