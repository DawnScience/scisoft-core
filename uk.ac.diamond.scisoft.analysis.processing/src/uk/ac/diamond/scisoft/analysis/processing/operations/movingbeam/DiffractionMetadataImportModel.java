/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.movingbeam;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class DiffractionMetadataImportModel extends AbstractOperationModel {

	@OperationModelField(hint="Path to the calibration NeXus file", file = FileType.EXISTING_FILE, label = "Calibration File:", fieldPosition=0)
	private String filePath = "";

	@OperationModelField(hint="Enter the dataset name for the calibration of the target detector", dataset = "filePath", label = "Target detector dataset",fieldPosition = 1)
	private String detectorDataset = null;
	
	@OperationModelField(hint ="Enter the dataset path to use as the calibrated reference position (Dimension 0)", dataset="filePath", label =  "Calibrated Position (Dimension 0)", description = "Path to node with position for the X (Dimension 0)", fieldPosition=3 )
	private String positionZeroDataset = null;
	
	@OperationModelField(hint ="Enter the dataset path to use as the calibrated reference position (Dimension 1)", dataset="filePath", label =  "Calibrated Position (Dimension 1)",  description = "Path to node with position for the Y (Dimension 1)",fieldPosition=4)
	private String positionOneDataset = null;
	
	@OperationModelField(description="Choose to overide any position information in file", label="Override position",fieldPosition=5)
	private boolean overridePosition = false;
	
	@OperationModelField(hint="Set the override position", enableif = "overridePosition", label="New position",fieldPosition = 6)
	private double[] currentPosition = new double[2]; 
	
	
	

	public String getFilePath() {
		return filePath;
	}
	public String getDetectorDataset() {
		return detectorDataset;
	}
	
	public String getPositionZeroDataset() {
		return  positionZeroDataset;
	}
	
	public String getPositionOneDataset() {
		return positionOneDataset;
	}
	

	public boolean getOverridePosition() {
		return overridePosition;
	}
	
	public double[] getCurrentPosition() {
		return currentPosition;
	}
	
	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
	public void setDetectorDataset(String dataset) {
		firePropertyChange("detectorDataset", this.detectorDataset, this.detectorDataset = dataset);
	}
	
	public void setPositionZeroDataset(String dataset) {
		firePropertyChange("positionZeroDataset", this.positionZeroDataset, this.positionZeroDataset = dataset);
	}
	
	public void setPositionOneDataset(String dataset) {
		firePropertyChange("positionOneDataset", this.positionOneDataset, this.positionOneDataset = dataset);
	}
		
	public void setOverridePosition(boolean flag) {
		firePropertyChange("overridePosition", this.overridePosition, this.overridePosition = flag);
	}
	
	public void setCurrentPosition(double[] newPos) {
		firePropertyChange("currentPosition", this.currentPosition,this.currentPosition = newPos);
	}
	

}
