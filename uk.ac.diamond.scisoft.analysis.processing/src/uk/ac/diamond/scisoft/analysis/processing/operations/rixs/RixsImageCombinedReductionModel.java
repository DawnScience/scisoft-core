/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;


public class RixsImageCombinedReductionModel extends RixsImageReductionBaseModel {

	public enum SCAN_OPTION {
		NEXT_SCAN,
		SAME_SCAN,
		PREVIOUS_SCAN,
		OVERRIDE_FILE,
	}

	@OperationModelField(label = "Elastic line scan", description = "Used to work out which scan file to find an elastic line", hint = "Use next (or same or previous) scan")
	private SCAN_OPTION scanOption = SCAN_OPTION.NEXT_SCAN;

	@OperationModelField(label = "Elastic line scan file", description = "When elastic line scan set to override, set this to scan file", file = FileType.EXISTING_FILE, enableif = "scanOption == 'OVERRIDE_FILE'")
	private String elScanFile = null;

	@OperationModelField(label = "Ignore linked elastic scans", description = "Used to allow 'Elastic line scan' field to override any linked scan", hint = "Use if linked scan should be ignored", expertOnly = true)
	private boolean ignoreLinkedScan = false;

	@OperationModelField(label = "Elastic line cutoff enable", description = "Use cutoff to remove pixels with high counts", expertOnly = true)
	private boolean elUseCutoff = false;

	@OperationModelField(label = "Elastic line cutoff for pixels", description = "Cutoff as multiple of single photon count", hint = "Check if peaks are clipped when cutoff is too low", enableif = "elUseCutoff == true", min = 1.0, expertOnly = true)
	private double elCutoff = 5.0;

	@OperationModelField(label = "Elastic line cutoff window size", description = "Size of window to use for cutoff", hint = "Cuts out a (size, size) window", enableif = "elUseCutoff == true", min = 1, expertOnly = true)
	private int elCutoffSize = 1;

	/**
	 * @return option to work out which scan file to use for elastic line
	 */
	public SCAN_OPTION getScanOption() {
		return scanOption;
	}

	void internalSetScanOption(SCAN_OPTION scanOption) {
		 this.scanOption = scanOption;
	}

	public void setScanOption(SCAN_OPTION scanOption) {
		firePropertyChange("setScanOption", this.scanOption, this.scanOption = scanOption);
	}

	/**
	 * @return path to scan file that contains elastic line
	 */
	public String getElScanFile() {
		return elScanFile;
	}

	public void setElScanFile(String elScanFile) {
		firePropertyChange("setElScanFile", this.elScanFile, this.elScanFile = elScanFile);
	}

	/**
	 * @return true if linked scans should be ignored
	 */
	public boolean isIgnoreLinkedScan() {
		return ignoreLinkedScan;
	}

	public void setIgnoreLinkedScan(boolean ignoreLinkedScan) {
		firePropertyChange("setIgnoreLinkedScan", this.ignoreLinkedScan, this.ignoreLinkedScan = ignoreLinkedScan);
	}

	@OperationModelField(label = "Width of strip", description = "Used to find elastic line", hint = "0 for minimizing FWHM of summed spectra, 1 for using column maxima, 2+ for summing and fitting line", min = 0)
	private int delta = 1;

	/**
	 * @return width of strip to use to find elastic line
	 */
	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		firePropertyChange("setDelta", this.delta, this.delta = delta);
	}

	/**
	 * @return true if cutoff will be used for elastic line reduction
	 */
	public boolean isElUseCutoff() {
		return elUseCutoff;
	}

	public void setElUseCutoff(boolean useCutoff) {
		firePropertyChange("setElUseCutoff", this.elUseCutoff, this.elUseCutoff = useCutoff);
	}

	/**
	 * @return upper threshold multiple of single photon (in detector count units) used to ignore pixels for elastic line reduction
	 */
	public double getElCutoff() {
		return elCutoff;
	}

	public void setElCutoff(double cutoff) {
		firePropertyChange("setElCutoff", this.elCutoff, this.elCutoff = cutoff);
	}

	/**
	 * @return size of window to use for cutoff of pixels that exceed threshold for elastic line reduction
	 */
	public int getElCutoffSize() {
		return elCutoffSize;
	}

	public void setElCutoffSize(int cutoffSize) {
		firePropertyChange("setElCutoffSize", this.elCutoffSize, this.elCutoffSize = cutoffSize);
	}

}
