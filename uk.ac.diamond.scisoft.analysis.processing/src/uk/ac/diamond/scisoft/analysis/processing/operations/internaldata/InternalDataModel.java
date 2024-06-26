/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.internaldata;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class InternalDataModel extends AbstractOperationModel {

	//filePath is important for classes that extend this model
	@OperationModelField(dataset = "filePath", label = "Dataset", hint = "Enter the name of the dataset to be used")
	private String datasetName = "";
	
	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		firePropertyChange("datasetName", this.datasetName, this.datasetName = datasetName);
	}
	
}
