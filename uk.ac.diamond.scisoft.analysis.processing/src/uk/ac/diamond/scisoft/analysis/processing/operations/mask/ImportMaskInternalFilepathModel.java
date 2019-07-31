/*
 * Copyright (c) 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;


import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;


public class ImportMaskInternalFilepathModel extends AbstractOperationModel {

	@OperationModelField(dataset = "filePath", label = "Dataset containing filepath",fieldPosition = 1)
	private String filePathDataset = "";
	
	public String getFilePathDataset() {
		return filePathDataset;
	}

	public void setFilePathDataset(String filePathDataset) {
		firePropertyChange("filePathDataset", this.filePathDataset, this.filePathDataset = filePathDataset);
	}
}