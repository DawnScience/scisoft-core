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
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;

public class SplitAndCalibrateFramesModel extends AbstractOperationModel {

	@OperationModelField(label = "Calibration configuration file:", file = FileType.EXISTING_FILE, description = "A .json file must be provided from which the appropraite calibration settings can be read.", hint = "Path to calibration config descriptor", fieldPosition = 0)
	private String pathToCalibrationDescriptionConfig = "";

	@OperationModelField(label = "Calibration output folder:", file = FileType.EXISTING_FOLDER, description = "A location to which the individual frame calibrations will be saved", hint = "Output folder to save calibrations", fieldPosition = 1)
	private String pathToOutputFolder = "";
	
	@OperationModelField(label = "Override roll of the reference calibration?", hint = "Set the model to overide the roll of the reference calibration", description = "Option to provide a new roll to prevent a diffraction equivalent orientation being determined", fieldPosition = 2)
	private boolean overrideRoll = false;

	@OperationModelField(label = "New roll value:", description = "Provide an new roll value to apply to all frames", enableif = "overrideRoll", hint = "New roll value")
	private double newRoll = 0.;
	
	@OperationModelField(label="Use old scanning paths?", description="If set, paths based on old scans will be used; only required for backward compatibility", expertOnly = true)
	private boolean useOldScanning = false;

	public void setPathToCalibrationDescriptionConfig(String path) {
		firePropertyChange("pathToCalibrationDescriptionConfig", pathToCalibrationDescriptionConfig,
				this.pathToCalibrationDescriptionConfig = path);
	}
	
	public void setUseOldScanning(boolean flag) {
		firePropertyChange("useOldScanning", useOldScanning, this.useOldScanning = flag);
	}

	public void setPathToOutputFolder(String path) {
		firePropertyChange("pathToOutputFolder", pathToOutputFolder, this.pathToOutputFolder = path);
	}

	public void setOverrideRoll(boolean flag) {
		firePropertyChange("overrideRoll", overrideRoll, this.overrideRoll = flag);
	}

	public void setNewRoll(double roll) {
		firePropertyChange("newRoll", newRoll, this.newRoll = roll);
	}

	public String getPathToCalibrationDescriptionConfig() {
		return pathToCalibrationDescriptionConfig;
	}

	public String getPathToOutputFolder() {
		return pathToOutputFolder;
	}

	public boolean getOverrideRoll() {
		return overrideRoll;
	}

	public double getNewRoll() {
		return newRoll;

	}
	
	public boolean getUseOldScanning() {
		return useOldScanning;
	}
	

}
