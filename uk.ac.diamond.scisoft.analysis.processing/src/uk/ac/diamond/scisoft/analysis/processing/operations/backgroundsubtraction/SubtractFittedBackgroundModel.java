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

	enum RegionCount {
		One,
		Two;

		@Override
		public String toString() {
			return String.valueOf(getCount());
		}

		public int getCount() {
			return ordinal() + 1;
		}
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

	@OperationModelField(fieldPosition = 6, label = "Use dark image in 2D", hint = "Uncheck to use 1D", description = "Use 2D smoothed dark image rather than a summed 1D profile", enableif = "darkImageFile != null", expertOnly = true)
	private boolean mode2D = true;

	@OperationModelField(fieldPosition = 7, label = "Number of regions to use", hint = "1 or 2", min = 1, max = 2, description = "Specify number of regions of dark image", enableif = "darkImageFile != null", expertOnly = true)
	private RegionCount regionCount = RegionCount.One;

	@OperationModelField(fieldPosition = 8, label = "Rectangle 0", description = "Region for profile 0", expertOnly = true)
	private IRectangularROI roiA = null;

	@OperationModelField(fieldPosition = 9, label = "Region in rectangle 0 to match", description = "Image area of dark image to fit", hint = "Leave empty to automatically find fit region; can span rectangle 1", enableif = "darkImageFile != null", expertOnly = true)
	private IRectangularROI fitRegionA = null;

	@OperationModelField(fieldPosition = 10, label = "Rectangle 1", description = "Region for profile 1", enableif = "regionCount == '2'", expertOnly = true)
	private IRectangularROI roiB = null;

	@OperationModelField(fieldPosition = 11, label = "Region in rectangle 1 to match", description = "Image area of dark image to fit", hint = "Leave empty to automatically find fit region (if region 0 does not intersect rectangle 1)", enableif = "darkImageFile != null && regionCount == '2'", expertOnly = true)
	private IRectangularROI fitRegionB = null;

	@OperationModelField(fieldPosition = 12, label = "Offset dark image", hint = "Leave empty for operation to finding from fitregion", description = "Override offset value to add to dark image", enableif = "darkImageFile != null", expertOnly = true)
	private Double darkOffset = null;

	@OperationModelField(fieldPosition = 13, label = "Scale dark image", hint = "This factor is applied before the offset is added", description = "Override scaling of dark image", enableif = "darkOffset != null", expertOnly = true)
	private double darkScaling = 1;

	@OperationModelField(fieldPosition = 14, label = "Drop zone width factor", hint = "Scaling for width of drop zone to match for dark image", description = "Override width scaling of drop-to-shadow zone used to find scaling and offset of dark image", enableif = "darkImageFile != null && darkOffset == null", expertOnly = true)
	private double darkFWHMScaling = 5;

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

	public static final String REGION_PROPERTY = "setRegion";

	/**
	 * @return number of regions to use
	 */
	public RegionCount getRegionCount() {
		return regionCount;
	}

	public void setRegionCount(RegionCount regionCount) {
		firePropertyChange(REGION_PROPERTY + "Count", this.regionCount, this.regionCount = regionCount);
	}

	/**
	 * @return get first region of interest (can be null to signify the entire image)
	 */
	public IRectangularROI getRoiA() {
		return roiA;
	}

	public void setRoiA(IRectangularROI roi) {
		firePropertyChange(REGION_PROPERTY, this.roiA, this.roiA = roi);
	}

	/**
	 * @return get second region of interest (can be null to signify the entire image)
	 */
	public IRectangularROI getRoiB() {
		return roiB;
	}

	public void setRoiB(IRectangularROI roi) {
		firePropertyChange(REGION_PROPERTY, this.roiB, this.roiB = roi);
	}

	public static final String FIT_REGION_PROPERTY = "setFitRegion";

	/**
	 * @return get first fit region of interest (can be null to signify find automatically)
	 */
	public IRectangularROI getFitRegionA() {
		return fitRegionA;
	}

	public void setFitRegionA(IRectangularROI fitRegionA) {
		firePropertyChange(FIT_REGION_PROPERTY, this.fitRegionA, this.fitRegionA = fitRegionA);
	}

	/**
	 * @return get second fit region of interest (can be null to signify find automatically)
	 */
	public IRectangularROI getFitRegionB() {
		return fitRegionB;
	}

	public void setFitRegionB(IRectangularROI fitRegionB) {
		firePropertyChange(FIT_REGION_PROPERTY, this.fitRegionB, this.fitRegionB = fitRegionB);
	}

	/**
	 * @return drop zone width scaling factor
	 */
	public double getDarkFWHMScaling() {
		return darkFWHMScaling;
	}

	public void setDarkFWHMScaling(double darkFWHMScaling) {
		firePropertyChange(FIT_REGION_PROPERTY, this.darkFWHMScaling, this.darkFWHMScaling = darkFWHMScaling);
	}
}
