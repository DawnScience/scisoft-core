/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ARPESGoldCalibrationCorrectionModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter the path to the calibration NeXus file", file = FileType.EXISTING_FILE, label = "Select Calibration File:")
	private String filePath = "";
	private double energyOffset = 0.0;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}

	public double getEnergyOffset() {
		return energyOffset;
	}

	public void setEnergyOffset(double energyOffset) {
		this.energyOffset = energyOffset;
	}

}
