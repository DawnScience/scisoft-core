/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * Model for reducing RIXS images to spectra
 */
public class RixsImageReductionModel extends RixsBaseModel {

	@OperationModelField(label = "Elastic line fit file", file = FileType.EXISTING_FILE, hint = "Can be empty then calibration file is used")
	private String fitFile;

	@OperationModelField(label = "Energy calibration file", file = FileType.EXISTING_FILE, hint = "Energy dispersion value has precedence")
	private String calibrationFile;

	@OperationModelField(label = "Size of window to find photon event", min = 3)
	private int window = 3;

	@OperationModelField(label = "Photon event peak: lower threshold as multiple of single photon below which to ignore event", min = 0)
	private double lowThreshold = 0.2;

	@OperationModelField(label = "Photon event peak: upper threshold as multiple of single photon above which to ignore event", min = 0)
	private double highThreshold = 2.0;

	@OperationModelField(label = "Energy dispersion at detector (in eV/pixel)", hint = "Leave blank or NaN to read from file", min = 0)
	private double energyDispersion = Double.NaN;

	@OperationModelField(label = "Supersampling for event centroids", min = 2)
	private int bins = 2;

	@OperationModelField(label = "Read regions from file")
	private boolean regionsFromFile = true;

	public enum CORRELATE_ORDER {
		FIRST, // use first spectrum and work forwards
		LAST,  // use last spectrum and work backwards
	}

	@OperationModelField(label = "Spectrum correlation order", hint = "Which spectrum to start correlating from")
	private CORRELATE_ORDER correlateOrder= CORRELATE_ORDER.LAST;

	public enum CORRELATE_PAIR {
		ALL_PAIRS, // every pairs of spectra
		CONSECUTIVE_PAIR, // consecutive pairs only
	}

	@OperationModelField(label = "Spectrum correlation option", hint = "All pairs of spectra; consecutive pairs only")
	private CORRELATE_PAIR correlateOption = CORRELATE_PAIR.CONSECUTIVE_PAIR; // in 2D image

	// TODO conditional override to fit parameters??

	/**
	 * @return path to file that contains processed fit file
	 */
	public String getFitFile() {
		return fitFile;
	}

	public void setFitFile(String fitFile) {
		firePropertyChange("setFitFile", this.fitFile, this.fitFile = fitFile);
	}

	/**
	 * @return path to file that contains processed calibration file
	 */
	public String getCalibrationFile() {
		return calibrationFile;
	}

	public void setCalibrationFile(String calibrationFile) {
		firePropertyChange("setCalibrationFile", this.calibrationFile, this.calibrationFile = calibrationFile);
	}

	/**
	 * @return size of window to check for photon event
	 */
	public int getWindow() {
		return window;
	}

	public void setWindow(int window) {
		firePropertyChange("setWindow", this.window, this.window = window);
	}

	/**
	 * @return lower threshold for event peak
	 */
	public double getLowThreshold() {
		return lowThreshold;
	}

	public void setLowThreshold(double lowThreshold) {
		firePropertyChange("setLowThreshold", this.lowThreshold, this.lowThreshold = lowThreshold);
	}

	public double getHighThreshold() {
		return highThreshold;
	}

	/**
	 * @return upper threshold for event peak
	 */
	public void setHighThreshold(double highThreshold) {
		firePropertyChange("setHighThreshold", this.highThreshold, this.highThreshold = highThreshold);
	}

	/**
	 * @return energy dispersion at detector (in eV/pixel). Can be NaN to indicate read from file
	 */
	public double getEnergyDispersion() {
		return energyDispersion;
	}

	public void setEnergyDispersion(double energyDispersion) {
		firePropertyChange("setEnergyDispersion", this.energyDispersion, this.energyDispersion = energyDispersion);
	}

	/**
	 * @return number of bins to use in super-sampling for event centroids
	 */
	public int getBins() {
		return bins;
	}

	public void setBins(int bins) {
		firePropertyChange("setBins", this.bins, this.bins = bins);
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

	/**
	 * @return order for spectrum correlator
	 */
	public CORRELATE_ORDER getCorrelateOrder() {
		return correlateOrder;
	}

	public void setCorrelateOrder(CORRELATE_ORDER correlateOrder) {
		firePropertyChange("setCorrelateOrder", this.correlateOrder, this.correlateOrder = correlateOrder);
	}

	/**
	 * @return option for spectrum correlator
	 */
	public CORRELATE_PAIR getCorrelateOption() {
		return correlateOption;
	}

	public void setCorrelateOption(CORRELATE_PAIR correlateOption) {
		firePropertyChange("setCorrelateOption", this.correlateOption, this.correlateOption = correlateOption);
	}

}
