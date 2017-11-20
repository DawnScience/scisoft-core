/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.processing.operations.SelectedFramesModel;

public class InternalStringExternalDataModel extends SelectedFramesModel {
	
	@OperationModelField(dataset = "filePath", label = "Dataset containing filepath",fieldPosition = 1)
	private String filePathDataset = "";
	
	public String getFilePathDataset() {
		return filePathDataset;
	}

	public void setFilePathDataset(String filePathDataset) {
		firePropertyChange("filePathDataset", this.filePathDataset, this.filePathDataset = filePathDataset);
	}
}