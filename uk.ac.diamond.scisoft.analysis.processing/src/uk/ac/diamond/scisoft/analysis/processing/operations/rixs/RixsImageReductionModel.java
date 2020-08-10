/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class RixsImageReductionModel extends RixsImageReductionBaseModel {

	public enum FIT_FILE_OPTION {
		NEXT_SCAN,
		SAME_SCAN,
		PREVIOUS_SCAN,
		MANUAL_OVERRIDE,
	}

	@OperationModelField(label = "Elastic line fit file option", hint = "Use next (or same or previous) scan's processed fit file; manual override by given file")
	private FIT_FILE_OPTION fitFileOption = FIT_FILE_OPTION.NEXT_SCAN;

	@OperationModelField(label = "Elastic line fit file", file = FileType.EXISTING_FILE, hint = "Can be empty then calibration file is used", enableif = "fitFileOption.toString() == \"MANUAL_OVERRIDE\"")
	private String fitFile;

	/**
	 * @return path to file that contains processed fit file
	 */
	public String getFitFile() {
		return fitFile;
	}

	public void setFitFile(String fitFile) {
		firePropertyChange("setFitFile", this.fitFile, this.fitFile = fitFile);
	}

	@OperationModelField(label = "Read regions from file", description = "Use regions defined in processed fit or calibration file", expertOnly = true)
	private boolean regionsFromFile = true;

	@OperationModelField(label = "Use per frame fits", description = "Use individual fits from each frame (rather than average slope)", expertOnly = true)
	private boolean perFrameFits = false;

	@OperationModelField(label = "Fallback directory for elastic line fits", description = "This is used as last resort to locate the directory to find processed fit files (when not manually overriden)", file = FileType.EXISTING_FOLDER, expertOnly = true)
	private String fitDirectory;

	/**
	 * @return option to work out which fit file to use
	 */
	public FIT_FILE_OPTION getFitFileOption() {
		return fitFileOption;
	}

	public static final String SET_FITFILE_OPTION = "setFitFileOption";

	public void setFitFileOption(FIT_FILE_OPTION fitFileOption) {
		firePropertyChange(SET_FITFILE_OPTION, this.fitFileOption, this.fitFileOption = fitFileOption);
	}

	/**
	 * @return if true, read regions of interest from file
	 */
	public boolean isRegionsFromFile() {
		return regionsFromFile;
	}

	public void setRegionsFromFile(boolean regionsFromFile) {
		firePropertyChange("setRegionsFromFile", this.regionsFromFile, this.regionsFromFile = regionsFromFile);
	}

	void internalSetRegionsFromFile(boolean regionsFromFile) {
		this.regionsFromFile = regionsFromFile;
	}

	/**
	 * @return if true, use per frame fits instead of average fit
	 */
	public boolean isPerFrameFits() {
		return perFrameFits;
	}

	public void setPerFrameFits(boolean perFrameFits) {
		firePropertyChange("setIsPerFrameFits", this.perFrameFits, this.perFrameFits = perFrameFits);
	}

	/**
	 * @return path to directory that contains processed fit files
	 */
	public String getFitDirectory() {
		return fitDirectory;
	}

	public void setFitDirectory(String fitDirectory) {
		firePropertyChange("setFitDirectory", this.fitDirectory, this.fitDirectory = fitDirectory);
	}
}
