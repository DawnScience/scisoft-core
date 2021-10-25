/*
 * Copyright 2021 Diamond Light Source Ltd.
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


public class PyrometryModel extends AbstractOperationModel {

	enum OperationToDisplay {
		PLANCK("Planck Function"),
		WIEN("Wien Function"),
		TWOCOLOUR("Two Colour Histogram")
		;
		private final String text;

	    /**
	     * @param text
	     */
		OperationToDisplay(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	}

	@OperationModelField(dataset = "filePath", label = "Spectrum", description = "The location of the pyrometry data within the file", fieldPosition = 1)
	private String spectrumDataset = "";
	@OperationModelField(dataset = "filePath", label = "Wavelength", description = "The location of the x axis (wavelength / nm) within the file", fieldPosition = 2)
	private String wavelengthDataset = "";
	@OperationModelField(label = "Calibration File", description = "Path to the raw calibration file", file = FileType.EXISTING_FILE, fieldPosition = 3)
	private String calibrationFilePath = "";
	@OperationModelField(dataset = "calibrationFilePath", label = "Calibration", description = "The location of the calibration spectrum", fieldPosition = 4)
	private String calibrationDataset = "";
	@OperationModelField(label = "Bulb Temperature", description = "The temperature of the bulb used in the calibration (K)", fieldPosition = 5)
	private int calibrationTemperature = 3000;
	@OperationModelField(label = "Wavelength Range",description = "Please set two values, start and end, in nm, separated by a comma i.e. 400,600", fieldPosition=6)
	private double[] wavelengthRange = {550.,750.};
	@OperationModelField(label = "Figure to Plot", description = "Choose whether to subtract the reduced and remapped form of frame A from B or divide A from B", fieldPosition = 7)
	private OperationToDisplay operationSelected = OperationToDisplay.PLANCK;
	@OperationModelField(label = "Δλ for Two Colour", description = "The width in nm of the window for the sliding two colour pyrometry", fieldPosition = 8)
	private double deltaLambda = 50.;

	/**
	 * @return The internal dataset to use as the spectrum
	 */
	public String getSpectrumDataset() {
		return spectrumDataset;
	}
	public void setSpectrumDataset(String spectrumDataset) {
		firePropertyChange("spectrumDataset", this.spectrumDataset, this.spectrumDataset = spectrumDataset);
	}

	/**
	 * @return The internal dataset to use as the wavelength
	 */
	public String getWavelengthDataset() {
		return wavelengthDataset;
	}
	public void setWavelengthDataset(String wavelengthDataset) {
		firePropertyChange("wavelengthDataset", this.wavelengthDataset, this.wavelengthDataset = wavelengthDataset);
	}

	/**
	 * @return the dataset location within the calibration file to use as a calibration spectrum
	 */
	public String getCalibrationDataset() {
		return calibrationDataset;
	}
	public void setCalibrationDataset(String calibrationDataset) {
		firePropertyChange("calibrationDataset", this.calibrationDataset, this.calibrationDataset = calibrationDataset);
	}

	/**
	 * @return the file path containing the calibration data
	 */
	public String getCalibrationFilePath() {
		return calibrationFilePath;
	}
	public void setCalibrationFilePath(String calibrationFilePath) {
		firePropertyChange("calibrationFilePath", this.calibrationFilePath, this.calibrationFilePath = calibrationFilePath);
	}

	/**
	 * @return the temperature of the bulb used in the calibration
	 */
	public int getCalibrationTemperature() {
		return calibrationTemperature;
	}
	public void setCalibrationTemperature(int calibrationTemperature) {
		firePropertyChange("calibrationTemperature", this.calibrationTemperature, this.calibrationTemperature = calibrationTemperature);
	}

	/**
	 * @return the upper and lower limits of the range over which fitting is requested
	 */
	public double[] getWavelengthRange() {
		return wavelengthRange;
	}
	public void setWavelengthRange(double[] wavelengthRange) {
		firePropertyChange("wavelengthRange", this.wavelengthRange, this.wavelengthRange = wavelengthRange);
	}

	/**
	 * @return which of the possible plots to show in the results pane
	 */
	public OperationToDisplay getOperationSelected() {
		return operationSelected;
	}
	public void setOperationSelected (OperationToDisplay operationSelected) {
		firePropertyChange("operationSelected", this.operationSelected, this.operationSelected = operationSelected);
	}

	/**
	 * @return the width in nm of the window for the sliding two colour pyrometry
	 */
	public double getDeltaLambda() {
		return deltaLambda;
	}
	public void setDeltaLambda(double deltaLambda) {
		firePropertyChange("deltaLambda", this.deltaLambda, this.deltaLambda = deltaLambda);
	}

}
