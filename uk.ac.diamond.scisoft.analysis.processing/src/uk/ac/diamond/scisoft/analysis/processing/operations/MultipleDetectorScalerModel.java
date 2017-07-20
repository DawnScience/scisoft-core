/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations;


import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

// This processing plugin scales the intensity recorded on an uncalibrated detector when another, calibrated, detector 
// at a different sample-to-detector distance can be used as a reference point. This is achieved via the inverse square law 

public class MultipleDetectorScalerModel extends AbstractOperationModel {
	
	// filePath is important for classes that extend this model
	@OperationModelField(dataset = "filePath", label = "Calibrated Detector Distance", hint = "Calibrated detector's distance", fieldPosition = 1)
	private String detectorOneDistanceDataset = "";
	
	public String getDetectorOneDistanceDataset() {
		return detectorOneDistanceDataset;
	}

	public void setDetectorOneDistanceDataset(String detectorOneDistanceDataset) {
		firePropertyChange("detectorOneDistanceDataset", this.detectorOneDistanceDataset, this.detectorOneDistanceDataset = detectorOneDistanceDataset);
	}

	@OperationModelField(dataset = "filePath", label = "Uncalibrated Detector Distance", hint = "Uncalibrated detector's distance", fieldPosition = 2)
	private String detectorTwoDistanceDataset = "";
	
	public String getDetectorTwoDistanceDataset() {
		return detectorTwoDistanceDataset;
	}

	public void setDetectorTwoDistanceDataset(String detectorTwoDistanceDataset) {
		firePropertyChange("detectorTwoDistanceDataset", this.detectorTwoDistanceDataset, this.detectorTwoDistanceDataset = detectorTwoDistanceDataset);
	}
}
