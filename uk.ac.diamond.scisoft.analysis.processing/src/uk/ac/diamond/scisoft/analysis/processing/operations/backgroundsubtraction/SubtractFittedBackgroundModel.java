/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;

/**
 * Model for fitting of the PDF of background pixels then subtracting
 * a level where given signal to background ratio occurs
 */
public class SubtractFittedBackgroundModel extends AbstractOperationModel {
	enum BackgroundPixelPDF {
		Gaussian;
	}

	@OperationModelField(fieldPosition = 0, label = "Background PDF", expertOnly = true)
	private BackgroundPixelPDF backgroundPDF = BackgroundPixelPDF.Gaussian;

	@OperationModelField(fieldPosition = 1, label = "Positive Only (ignore negative values in data)", expertOnly = true)
	private boolean positiveOnly = true;

	@OperationModelField(fieldPosition = 2, label = "Signal to background ratio", min = 1, expertOnly = true)
	private double ratio = 3.0;

	@OperationModelField(fieldPosition = 3, label = "Dark image file", file = FileType.EXISTING_FILE, hint = "Can be empty then uniform background is assumed")
	private String darkImageFile = null;

	@OperationModelField(fieldPosition = 4, label = "Remove outliers from dark image", hint = "Check to omit cosmic ray events", enableif = "darkImageFile != null", expertOnly = true)
	private boolean removeOutliers = true;

	@OperationModelField(fieldPosition = 5, label = "Gaussian smoothing length parameter", hint = "Can be empty to disable smooth", enableif = "darkImageFile != null", expertOnly = true)
	private Double gaussianSmoothingLength = 10.;

	@OperationModelField(fieldPosition = 6, label = "Use dark image in 2D", hint = "Leave unchecked to use 1D", description = "Use 2D smoothed dark image rather than a summed 1D profile", enableif = "darkImageFile != null", expertOnly = true)
	private boolean mode2D = true;

	@OperationModelField(fieldPosition = 7, label = "Rectangle to match", description = "Image area of dark image to fit", hint = "Leave empty to automatically find shadow region", enableif = "darkImageFile != null", expertOnly = true)
	private IRectangularROI fitRegion = null;

	@OperationModelField(fieldPosition = 8, label = "Offset dark image", hint = "Leave empty for operation to finding from shadow region", description = "Override offset value to add to dark image", enableif = "darkImageFile != null", expertOnly = true)
	private Double darkOffset = null;

	@OperationModelField(fieldPosition = 9, label = "Scale dark image", hint = "This factor is applied before the offset is added", description = "Override scaling of dark image", enableif = "darkOffset != null", expertOnly = true)
	private double darkScaling = 1;

	@OperationModelField(label = "Rectangle 0", description = "Region for profile 0", expertOnly = true)
	private IRectangularROI roiA = null;

	@OperationModelField(label = "Rectangle 1", description = "Region for profile 1", expertOnly = true)
	private IRectangularROI roiB = null;

	public static final int HISTOGRAM_MAX_BINS = 1024*1024;

	/**
	 * @return if true, ignore negative values in background
	 */
	public boolean isPositiveOnly() {
		return positiveOnly;
	}

	public void setPositiveOnly(boolean positiveOnly) {
		firePropertyChange("setPositiveOnly", this.positiveOnly, this.positiveOnly = positiveOnly);
	}

	/**
	 * @return if true, remove outlier values from dark image to make profile
	 */
	public boolean isRemoveOutliers() {
		return removeOutliers;
	}

	public static final String REMOVE_OUTLIER_PROPERTY = "setRemoveOutliers";

	public void setRemoveOutliers(boolean removeOutliers) {
		firePropertyChange(REMOVE_OUTLIER_PROPERTY, this.removeOutliers, this.removeOutliers = removeOutliers);
	}

	/**
	 * @return probability distribution function used to fit background
	 */
	public BackgroundPixelPDF getBackgroundPDF() {
		return backgroundPDF;
	}

	public void setBackgroundPDF(BackgroundPixelPDF pdf) {
		firePropertyChange("setBackgroundPDF", this.backgroundPDF, this.backgroundPDF = pdf);
	}

	/**
	 * @return signal to noise ratio that determines the threshold value at which to cut-off the background
	 */
	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		firePropertyChange("setRatio", this.ratio, this.ratio = ratio);
	}

	/**
	 * @return path to file that contains dark image(s)
	 */
	public String getDarkImageFile() {
		return darkImageFile;
	}

	public static final String DARK_FILE_PROPERTY = "setDarkImageFile";

	public void setDarkImageFile(String darkImageFile) {
		firePropertyChange(DARK_FILE_PROPERTY, this.darkImageFile, this.darkImageFile = darkImageFile);
	}

//	/**
//	 * @return get region of interest (can be null to signify the entire image)
//	 */
//	public IRectangularROI getRoi() {
//		return roi;
//	}
//
//	public void setRoi(IRectangularROI roi) {
//		firePropertyChange("setRoi", this.roi, this.roi = roi);
//	}
//
	/**
	 * @return length parameter used for Gaussian smoothing filter. If null or NaN then no smoothing
	 */
	public Double getGaussianSmoothingLength() {
		return gaussianSmoothingLength;
	}

	public static final String GAUSSIAN_PROPERTY = "setGaussianSmoothingLength";

	public void setGaussianSmoothingLength(Double gaussianSmoothingLength) {
		firePropertyChange(GAUSSIAN_PROPERTY, this.gaussianSmoothingLength, this.gaussianSmoothingLength = gaussianSmoothingLength);
	}

	/**
	 * @return true if a smoothed 2D dark image is to be used
	 */
	public boolean isMode2D() {
		return mode2D;
	}

	public static final String MODE_2D_PROPERTY = "setMode2D";

	public void setMode2D(boolean mode2D) {
		firePropertyChange(MODE_2D_PROPERTY, this.mode2D, this.mode2D = mode2D);
	}

	/**
	 * @return region to fit (instead of finding shadow region)
	 */
	public IRectangularROI getFitRegion() {
		return fitRegion;
	}

	public void setFitRegion(IRectangularROI fitRegion) {
		firePropertyChange("setFitRegion", this.fitRegion, this.fitRegion = fitRegion);
	}

	/**
	 * @return offset to add to dark image. If null or NaN then determine from shadow region
	 */
	public Double getDarkOffset() {
		return darkOffset;
	}

	public void setDarkOffset(Double darkOffset) {
		firePropertyChange("setDarkOffset", this.darkOffset, this.darkOffset = darkOffset);
	}

	/**
	 * @return scaling factor to multiply dark image before an offset is applied
	 */
	public double getDarkScaling() {
		return darkScaling;
	}

	public void setDarkScaling(double darkScaling) {
		firePropertyChange("setDarkScaling", this.darkScaling, this.darkScaling = darkScaling);
	}

	/**
	 * @return get first region of interest (can be null to signify the entire image)
	 */
	public IRectangularROI getRoiA() {
		return roiA;
	}

	public void setRoiA(IRectangularROI roi) {
		firePropertyChange("setRoiA", this.roiA, this.roiA = roi);
	}

	/**
	 * @return get second region of interest (can be null to signify the entire image)
	 */
	public IRectangularROI getRoiB() {
		return roiB;
	}

	public void setRoiB(IRectangularROI roi) {
		firePropertyChange("setRoiB", this.roiB, this.roiB = roi);
	}
}
