/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;

/**
 * Base model for processing RIXS images
 */
public class RixsBaseModel extends AbstractOperationModel {
	@OperationModelField(label = "Rectangle A", description = "Region to search for elastic peak", enableif = "!(regionsFromFile ?: false)", expertOnly = true)
	private IRectangularROI roiA = null;

	@OperationModelField(label = "Rectangle B", description = "Region to search for elastic peak", enableif = "!(regionsFromFile ?: false)", expertOnly = true)
	private IRectangularROI roiB = null;

	public enum ENERGY_DIRECTION {
		SLOW, // slowest pixel direction
		FAST, // fastest pixel direction
	}

	// orientation (given by energy direction)
	@OperationModelField(label = "Energy direction", description = "Principal direction in which the energy changes", hint = "Slow is vertical; fast is horizontal", expertOnly = true)
	private ENERGY_DIRECTION energyDirection = ENERGY_DIRECTION.SLOW; // in 2D image

	@OperationModelField(label = "Cutoff enable", description = "Use cutoff to remove pixels with high counts", expertOnly = true)
	private boolean useCutoff = false;

	@OperationModelField(label = "Cutoff for pixels", description = "Cutoff as multiple of single photon count", hint = "Check if peaks are clipped when cutoff is too low", enableif = "useCutoff == true", min = 1.0, expertOnly = true)
	private double cutoff = 15.0;

	@OperationModelField(label = "Cutoff window size", description = "Size of window to use for cutoff", hint = "Cuts out a (size, size) window", enableif = "useCutoff == true", min = 1, expertOnly = true)
	private int cutoffSize = 1;

	@OperationModelField(label = "Clip spectra", description = "Clip spectra to avoid fall-off from slope correction", hint = "Set true when image background is not zero", expertOnly = true)
	private boolean clipSpectra = true;

	@OperationModelField(label = "Slope override", description = "Overrides slope value from any processed fit file", hint = "A value used to override the slopes from fit files; can leave blank to not override")
	private Double slopeOverride = null;

	@OperationModelField(label = "Fallback value for counts per photon", description = "Pixel value of single photon if it is confined in a single pixel; used only if it cannot be determined from file", min = 1, expertOnly = true)
	private int countsPerPhoton = 74;

	@OperationModelField(label = "Crop region", description = "Crop any region's lower bound according to detector angle if detector is Andor", hint = "Set true to automatically crop any ROIs", expertOnly = true)
	private boolean cropROI = true;
	
	/**
	 * @return get first region of interest (can be null to signify the entire image)
	 */
	public IRectangularROI getRoiA() {
		return roiA;
	}

	public void setRoiA(IRectangularROI roi) {
		firePropertyChange("setRoiA", this.roiA, this.roiA = roi);
	}

	void internalSetRoiA(IRectangularROI roi) {
		this.roiA = roi;
	}

	/**
	 * @return get second region of interest (can be null to signify only use first ROI)
	 */
	public IRectangularROI getRoiB() {
		return roiB;
	}

	public void setRoiB(IRectangularROI roi) {
		firePropertyChange("setRoiB", this.roiB, this.roiB = roi);
	}

	void internalSetRoiB(IRectangularROI roi) {
		this.roiB = roi;
	}

	/**
	 * @return dimension of image in which energy changes
	 */
	public int getEnergyIndex() {
		return energyDirection.ordinal();
	}

	public void setEnergyIndex(int index) {
		// do nothing to make marshaller happy
	}

	public ENERGY_DIRECTION getEnergyDirection() {
		return energyDirection;
	}

	public void setEnergyDirection(ENERGY_DIRECTION energyDirection) {
		firePropertyChange("setEnergyIndex", this.energyDirection, this.energyDirection = energyDirection);
	}

	/**
	 * @return true if cutoff will be used
	 */
	public boolean isUseCutoff() {
		return useCutoff;
	}

	public void setUseCutoff(boolean useCutoff) {
		firePropertyChange("setUseCutoff", this.useCutoff, this.useCutoff = useCutoff);
	}

	/**
	 * @return upper threshold multiple of single photon (in detector count units) used to ignore pixels
	 */
	public double getCutoff() {
		return cutoff;
	}

	public void setCutoff(double cutoff) {
		firePropertyChange("setCutoff", this.cutoff, this.cutoff = cutoff);
	}

	/**
	 * @return size of window to use for cutoff of pixels that exceed threshold
	 */
	public int getCutoffSize() {
		return cutoffSize;
	}

	public void setCutoffSize(int cutoffSize) {
		firePropertyChange("setCutoff", this.cutoffSize, this.cutoffSize = cutoffSize);
	}

	/**
	 * @return true if spectra should be clipped to avoid fall-off from slope correction
	 */
	public boolean isClipSpectra() {
		return clipSpectra;
	}

	public void setClipSpectra(boolean clipSpectra) {
		firePropertyChange("setClipSpectra", this.clipSpectra, this.clipSpectra = clipSpectra);
	}

	/**
	 * @return slope of elastic line. Non-null values are used to override values from elastic fit files
	 */
	public Double getSlopeOverride() {
		return slopeOverride;
	}

	public void setSlopeOverride(Double slopeOverride) {
		firePropertyChange("setSlopeOverride", this.slopeOverride, this.slopeOverride = slopeOverride);
	}

	/**
	 * @return number of AD counts per photon
	 */
	public int getCountsPerPhoton() {
		return countsPerPhoton;
	}

	public void setCountsPerPhoton(int countsPerPhoton) {
		firePropertyChange("setCountsPerPhoton", this.countsPerPhoton, this.countsPerPhoton = countsPerPhoton);
	}

	/**
	 * @return true if ROIs should be cropped in height when data is from Andor detector
	 */
	public boolean isCropROI() {
		return cropROI;
	}

	public void setCropROI(boolean cropROI) {
		firePropertyChange("setCropROI", this.cropROI, this.cropROI = cropROI);
	}
}
