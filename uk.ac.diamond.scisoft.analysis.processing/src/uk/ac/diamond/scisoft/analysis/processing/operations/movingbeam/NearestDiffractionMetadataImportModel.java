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

public class NearestDiffractionMetadataImportModel extends AbstractOperationModel {
	
	@OperationModelField(hint="Path to the calibration scan NeXus file", file = FileType.EXISTING_FILE, description = "File used for obtaining the scan motor positions of each calibration", label = "Calibration File:", fieldPosition=0)
	private String filePath = "";
	
	@OperationModelField(hint="Path to the calibration files folder", file = FileType.EXISTING_FOLDER, description = "Folder should contain calibrations in .nxs format and be saved with the frame number appended to them", label = "Path to Calibrations:", fieldPosition=1)
	private String calibsFolder = "";
	
//	@OperationModelField(hint = "Prevent extrapolation outside acquisition grid", label="Bound grid")
//	private boolean limit = false;
	
	@OperationModelField(hint="Enter the dataset name for the calibration of the target detector", label = "Target detector dataset",fieldPosition = 2)
	private String detectorDataset = null;
	
	@OperationModelField(hint ="Enter the dataset path to use as the calibrated reference position (Dimension 0)", dataset="filePath", label =  "Calibrated Position (Dimension 0)", description = "Path to node with position for the X (Dimension 0)", fieldPosition=3 )
	private String positionZeroDataset = null;
	
	@OperationModelField(hint ="Enter the dataset path to use as the calibrated reference position (Dimension 1)", dataset="filePath", label =  "Calibrated Position (Dimension 1)",  description = "Path to node with position for the Y (Dimension 1)",fieldPosition=4)
	private String positionOneDataset = null;
	
	@OperationModelField(hint = "Regex pattern match", label = "regex frame matcher", description= "Frame pattern matcher based on regular expression.")
	private String regex = ".*_frame_\\d\\d\\d.nxs";
	
	public String getDetectorDataset() {
		return detectorDataset;
	}
	
	public String getPositionZeroDataset() {
		return  positionZeroDataset;
	}
	
	public String getPositionOneDataset() {
		return positionOneDataset;
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
	
	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
	
	public void setCalibsFolder(String filePath) {
		firePropertyChange("calibsFolder", this.calibsFolder, this.calibsFolder = filePath);
	}
	
//	public void setLimit(boolean flag) {
//		firePropertyChange("limit", this.limit, this.limit = flag);
//		
//	}
	
	public void setRegex(String pattern) {
		firePropertyChange("regex", this.regex, this.regex=pattern);
	}
	
	
	public String getFilePath() {
		return filePath;
	}
	
//	public boolean isBounded() {
//		return limit;
//	}
	
	public String getCalibsFolder() {
		return calibsFolder;
	}
	
	public String getRegex() {
		return this.regex;
	}

}
