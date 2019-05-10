/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class DiffractionMetadataImportModel extends AbstractOperationModel {

	@OperationModelField(hint="Path to the calibration NeXus file", file = FileType.EXISTING_FILE, label = "Calibration File:")
	private String filePath = "";

	@OperationModelField(hint="Enter the dataset name for the calibration of the target detector", dataset = "filePath", label = "Target detector dataset")
	private String detectorDataset = null;

	public String getFilePath() {
		return filePath;
	}
	public String getDetectorDataset() {
		return this.detectorDataset;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	public void setDetectorDataset(String dataset) {
		firePropertyChange("detectorDataset", this.detectorDataset, this.detectorDataset = dataset);
	}

}
