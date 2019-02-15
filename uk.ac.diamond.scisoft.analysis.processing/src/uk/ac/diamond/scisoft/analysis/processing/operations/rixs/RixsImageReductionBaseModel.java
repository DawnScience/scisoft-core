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
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

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

	@OperationModelField(label = "Correlation energy range", description = "Energy range to use correlate spectra", hint = "Start and end energies", rangevalue = RangeType.XRANGE, expertOnly = true)
	private double[] energyRange = null;

	@OperationModelField(label = "Normalize spectra by region size", description = "If true, then divide summed spectra by number of constituent spectra", expertOnly = true)
	private boolean normalizeByRegionSize = true;

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

	// TODO conditional override to fit parameters??

	public enum ENERGY_OFFSET {
		FROM_ELASTIC_LINE_FIT, // use value of intercept in fit
		MANUAL_OVERRIDE, // override if values are available
		TURNING_POINT,
	}

	@OperationModelField(label = "Zero energy offset", description = "Options for how to define the zero energy position", hint = "From elastic line fit; manual override by given values; or from first turning point", expertOnly = true)
	private ENERGY_OFFSET energyOffsetOption = ENERGY_OFFSET.FROM_ELASTIC_LINE_FIT;

	@OperationModelField(label = "Zero energy offset A", description = "Offset value for rectangle A", hint = "Position on energy axis in pixels", expertOnly = true)
	private double energyOffsetA = Double.NaN;

	@OperationModelField(label = "Zero energy offset B", description = "Offset value for rectangle B", hint = "Position on energy axis in pixels", expertOnly = true)
	private double energyOffsetB = Double.NaN;

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
	public boolean isNormalizeByRegionSize() {
		return normalizeByRegionSize;
	}

	public void setNormalizeByRegionSize(boolean normalizeByRegionSize) {
		firePropertyChange("setNnormalizeByRegionSize", this.normalizeByRegionSize, this.normalizeByRegionSize = normalizeByRegionSize);
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
}
