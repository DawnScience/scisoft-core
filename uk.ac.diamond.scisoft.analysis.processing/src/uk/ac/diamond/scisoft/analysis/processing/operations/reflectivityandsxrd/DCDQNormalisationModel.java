/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class DCDQNormalisationModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to the data file", file = FileType.EXISTING_FILE, label = "File", fieldPosition = 1)
	private String filePath = "";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
	@OperationModelField(dataset = "filePath", label = "Q or theta dataset", hint = "Enter the name of the dataset to be used as the q or theta values", fieldPosition = 2)
	private String datasetQName = "qdcd_";
	
	public String getDatasetQName() {
		return datasetQName;
	}

	public void setDatasetQName(String datasetQName) {
		firePropertyChange("datasetQName", this.datasetQName, this.datasetQName = datasetQName);
	}
	
	@OperationModelField(dataset = "filePath", label = "Intensity Dataset", hint = "Enter the name of the dataset to be used for the intensity values", fieldPosition = 3)
	private String datasetIName = "adc2";
	
	public String getDatasetIName() {
		return datasetIName;
	}

	public void setDatasetIName(String datasetIName) {
		firePropertyChange("datasetIName", this.datasetIName, this.datasetIName = datasetIName);
	}
	
}
