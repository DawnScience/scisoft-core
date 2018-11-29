/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ReadMultiPositionDetectorInformationModel extends AbstractOperationModel {

	@OperationModelField(label = "Offset enable?", description = "Offset the detector distance", fieldPosition = 1)
	private boolean offsetSample = false;

	@OperationModelField(dataset = "filePath", label = "Dataset", description = "Enter the name of the dataset to be used", fieldPosition = 2, enableif= "offsetSample==true")
	private String datasetName = "";

	@OperationModelField(label = "Offset sign", description = "Check to add offset, uncheck to subtract", fieldPosition = 3, enableif= "offsetSample==true")
	private boolean addOffset = false;

	public boolean isAddOffset() {
		return addOffset;
	}

	public void setAddOffset(boolean addOffset) {
		firePropertyChange("addOffset", this.addOffset, this.addOffset = addOffset);
	}

	public boolean isOffsetSample() {
		return offsetSample;
	}

	public void setOffsetSample(boolean offsetSample) {
		firePropertyChange("offsetSample", this.offsetSample, this.offsetSample = offsetSample);
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		firePropertyChange("datasetName", this.datasetName, this.datasetName = datasetName);
	}

}
