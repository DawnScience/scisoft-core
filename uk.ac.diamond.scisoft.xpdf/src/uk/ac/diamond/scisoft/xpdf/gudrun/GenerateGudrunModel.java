/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.gudrun;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class GenerateGudrunModel extends AbstractOperationModel {

	
	@OperationModelField(hint="Enter the path to the file to be appended", file = FileType.EXISTING_FILE, label = "Output file path")
	private String filePath = "";
	
	@OperationModelField(hint="Enter the path to the directory in which to place the file", file = FileType.EXISTING_FOLDER, label = "Output file destination directory")
	private String errorFilePath = "";
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
	public String getErrorFilePath() {
		return errorFilePath;
	}
	
	public String getFileDirectory() {
		return errorFilePath;
	}

	public void setErrorFilePath(String errorFilePath) {
		firePropertyChange("errorFilePath", this.errorFilePath, this.errorFilePath = errorFilePath);
	}
	
	public void setFileDirectory(String errorFilePath) {
		firePropertyChange("errorFilePath", this.errorFilePath, this.errorFilePath = errorFilePath);
	}
}
