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
public class RixsImageReductionBaseModel extends RixsBaseModel {

	@OperationModelField(label = "Energy calibration file", description = "This is also used to locate the directory to find processed fit files (when not manually overriden)", file = FileType.EXISTING_FILE, hint = "Energy dispersion value has precedence")
	private String calibrationFile;

	@OperationModelField(label = "Size of window", description = "Window used to find photon event", min = 3, expertOnly = true)
	private int window = 3;

	@OperationModelField(label = "Photon event peak: lower threshold", hint = "Multiple of single photon below which to ignore event", min = 0, expertOnly = true)
	private double lowThreshold = 0.2;

	@OperationModelField(label = "Photon event peak: upper threshold", hint = "Multiple of single photon above which to ignore event", min = 0, expertOnly = true)
	private double highThreshold = 2.0;

	@OperationModelField(label = "Energy dispersion at detector (in eV/pixel)", description = "Overrides the value in calibration file",  hint = "Leave blank or NaN to read from file", min = 0)
	private double energyDispersion = Double.NaN;

	@OperationModelField(label = "Supersampling for event centroids", description = "Number of sub-divisions per pixel edge", min = 2, expertOnly = true)
	private int bins = 2;

	public enum CORRELATE_ORDER {
		FIRST, // use first spectrum and work forwards
		LAST,  // use last spectrum and work backwards
	}

	@OperationModelField(label = "Spectrum correlation order", description = "Which spectrum to start correlating from", expertOnly = true)
	private CORRELATE_ORDER correlateOrder = CORRELATE_ORDER.LAST;

	public enum CORRELATE_PHOTON {
		ALL_PAIRS, // every pairs of spectra
		CONSECUTIVE_PAIR, // consecutive pairs only
		USE_INTENSITY_SHIFTS, // use shifts from intensity correlations
	}

	@OperationModelField(label = "Spectrum correlation option", description = "Photon correlation options", hint = "All pairs of spectra; consecutive pairs only; use intensity shifts", expertOnly = true)
	private CORRELATE_PHOTON correlateOption = CORRELATE_PHOTON.USE_INTENSITY_SHIFTS; // in 2D image

	@OperationModelField(label = "Correlation energy range", description = "Energy range to use correlate spectra", hint = "Start and end energies (separated by a comma)", expertOnly = true)
	private double[] energyRange = null;

	@OperationModelField(label = "Average spectrum along slope", description = "If true, then take average rather than sum along slope to create each spectrum ", expertOnly = true)
	private boolean normalizeByRows = false;

	@OperationModelField(label = "Normalization dataset", description = "Use named dataset to normalize spectra", expertOnly = true, dataset = "currentFilePath")
	private String normalizationPath = "/entry/m4c1/m4c1";

	@OperationModelField(label = "Selection of frames to use", description = 
			"A comma-separated (or semicolon-separated) list of sub-ranges.\n"
			+ "Each sub-range can be a single integer or two integers (start/end) separated by\n"
			+ "a colon. Integers can be negative to imply a count from the end of the range.\n"
			+ "The end integer must be greater than the start integer. The end integer of\n"
			+ "sub-ranges is inclusive. Also the last sub-range can omit the end integer.\n"
			+ "Finally, each sub-range can be excluded with an exclamation mark prefix; if\n"
			+ "any exclamation mark appears in a multi-range string then the default is that\n"
			+ "the entire range is included and specified sub-ranges with \"!\" are excluded.",
			hint = "E.g. 1 or 2,-1 or 0,2:4 or !3 or !1:-2", expertOnly = true)
	private String frameSelection = "";

	public enum ENERGY_OFFSET {
		FROM_ELASTIC_LINE_FIT, // use value of intercept in fit
		MANUAL_OVERRIDE, // override if values are available
		TURNING_POINT,
	}

	@OperationModelField(label = "Zero energy offset", description = "Option to define the zero energy position", hint = "From elastic line fit; manual override by given values; or from first turning point", expertOnly = true)
	private ENERGY_OFFSET energyOffsetOption = ENERGY_OFFSET.FROM_ELASTIC_LINE_FIT;

	@OperationModelField(label = "Zero energy offset A", description = "Offset value for rectangle A", hint = "Position on energy axis in pixels", enableif = "energyOffsetOption == 'MANUAL_OVERRIDE'", expertOnly = true)
	private double energyOffsetA = Double.NaN;

	@OperationModelField(label = "Zero energy offset B", description = "Offset value for rectangle B", hint = "Position on energy axis in pixels", enableif = "energyOffsetOption == 'MANUAL_OVERRIDE'", expertOnly = true)
	private double energyOffsetB = Double.NaN;

	public enum XIP_OPTION {
		DONT_USE,     // ignore any XIP data
		USE_IF_FOUND, // use if XIP data found in file
		USE_EXTERNAL, // use XIP data found in external file
	}

	@OperationModelField(label = "XIP usage", description = "Option to use centroid data generated by XIP area detector plugin for XCAM", hint = "Do not use; use if available in file; use from external file", expertOnly = true)
	private XIP_OPTION xipOption = XIP_OPTION.USE_IF_FOUND;

	@OperationModelField(label = "Save centroids", description = "Option to save all centroid positions and values", expertOnly = true)
	private boolean saveAllPositions = false;

	@OperationModelField(label = "Centroid correction file", description = "This is used to locate a file containing lookup tables for centroid correction", file = FileType.EXISTING_FILE, hint = "File path for correction tables")
	private String centroidLookupFile = null;

	/**
	 * @return option for zero energy offset
	 */
	public ENERGY_OFFSET getEnergyOffsetOption() {
		return energyOffsetOption;
	}

	public void setEnergyOffsetOption(ENERGY_OFFSET energyOffsetOption) {
		firePropertyChange("setEnergyOffsetOption", this.energyOffsetOption, this.energyOffsetOption = energyOffsetOption);
	}

	/**
	 * @return pixel position on energy axis of zero energy loss for rectangle A
	 */
	public double getEnergyOffsetA() {
		return energyOffsetA;
	}

	public void setEnergyOffsetA(double energyOffsetA) {
		firePropertyChange("setEnergyOffsetA", this.energyOffsetA, this.energyOffsetA = energyOffsetA);
	}

	/**
	 * @return pixel position on energy axis of zero energy loss for rectangle B
	 */
	public double getEnergyOffsetB() {
		return energyOffsetB;
	}

	public void setEnergyOffsetB(double energyOffsetB) {
		firePropertyChange("setEnergyOffsetB", this.energyOffsetB, this.energyOffsetB = energyOffsetB);
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
	 * @return order for spectrum correlator
	 */
	public CORRELATE_ORDER getCorrelateOrder() {
		return correlateOrder;
	}

	public void setCorrelateOrder(CORRELATE_ORDER correlateOrder) {
		firePropertyChange("setCorrelateOrder", this.correlateOrder, this.correlateOrder = correlateOrder);
	}

	/**
	 * @return option for photon event spectrum correlation
	 */
	public CORRELATE_PHOTON getCorrelateOption() {
		return correlateOption;
	}

	public void setCorrelateOption(CORRELATE_PHOTON correlateOption) {
		firePropertyChange("setCorrelateOption", this.correlateOption, this.correlateOption = correlateOption);
	}

	/**
	 * @return true if spectra should be normalized by region size
	 */
	public boolean isNormalizeByRows() {
		return normalizeByRows;
	}

	public void setNormalizeByRows(boolean normalizeByRows) {
		firePropertyChange("setNormalizeByRows", this.normalizeByRows, this.normalizeByRows = normalizeByRows);
	}

	/*
	 * @deprecated For deserialization only, needed for backward compatibility
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void setNormalizeByRegionSize(boolean normalizeByRegionSize) {
		setNormalizeByRows(!normalizeByRegionSize); // take complement as previously it did nothing
	}

	/**
	 * @return path to normalization dataset, can be null
	 */
	public String getNormalizationPath() {
		return normalizationPath;
	}

	public void setNormalizationPath(String normalizationPath) {
		firePropertyChange("setNormalizationPath", this.normalizationPath, this.normalizationPath = normalizationPath);
	}

	void internalSetNormalizationPath(String normalizationPath) {
		this.normalizationPath = normalizationPath;
	}

	/**
	 * @return selection of frames as a multi-range string
	 */
	public String getFrameSelection() {
		return frameSelection;
	}

	public void setFrameSelection(String frameSelection) {
		firePropertyChange("setFrameSelection", this.frameSelection, this.frameSelection = frameSelection);
	}

	/**
	 * @return start and end energies to use to correlate spectra. Can be null
	 */
	public double[] getEnergyRange() {
		return energyRange;
	}

	public void setEnergyRange(double[] energyRange) {
		firePropertyChange("setEnergyRange", this.energyRange, this.energyRange = energyRange);
	}

	/**
	 * @return option for including XIP data
	 */
	public XIP_OPTION getXIPOption() {
		return xipOption;
	}

	public void setXIPOption(XIP_OPTION xipOption) {
		firePropertyChange("setXIPOption", this.xipOption, this.xipOption = xipOption);
	}

	/**
	 * @return if true then save all centroid positions
	 */
	public boolean isSaveAllPositions() {
		return saveAllPositions;
	}

	public void setSaveAllPositions(boolean saveAllPositions) {
		firePropertyChange("setSaveAllPositions", this.saveAllPositions, this.saveAllPositions = saveAllPositions);
	}

	/**
	 * @return file path to lookup tables for centroid correction
	 */
	public String getCentroidLookupFile() {
		return centroidLookupFile;
	}

	public void setCentroidLookupFile(String centroidLookupFile) {
		firePropertyChange("setCentroidLookupFile", this.centroidLookupFile, this.centroidLookupFile = centroidLookupFile);
	}
}
