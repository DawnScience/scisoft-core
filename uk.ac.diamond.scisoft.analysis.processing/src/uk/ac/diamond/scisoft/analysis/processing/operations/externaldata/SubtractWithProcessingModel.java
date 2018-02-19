/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.processing.operations.internaldata.InternalDataSelectedFramesModel;

public class SubtractWithProcessingModel extends InternalDataSelectedFramesModel {
	
	@OperationModelField(hint="Enter the path to the data file", file = FileType.EXISTING_FILE, label = "File", fieldPosition = 0)
	private String filePath = "";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
	private boolean useSameDataset = false;
	
	public boolean getUseSameDataset() {
		return useSameDataset;
	}
	
	public void SetUseSameDataset(boolean useSameDataset) {
		firePropertyChange("useSameDataset", this.useSameDataset, this.useSameDataset = useSameDataset);
	}
}
