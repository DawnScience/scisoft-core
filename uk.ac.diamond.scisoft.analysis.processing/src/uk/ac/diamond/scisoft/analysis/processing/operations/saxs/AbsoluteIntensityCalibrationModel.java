/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// @author Tim Snow


// The model for a DAWN process to perform an absolute intensity calibration
public class AbsoluteIntensityCalibrationModel extends AbstractOperationModel {


	private boolean useInternalCalibrant = true;
	
	public boolean getUseInternalCalibrant() {
		return useInternalCalibrant;
	}
	
	public void setUseInternalCalibrant(boolean useInternalCalibrant) {
		firePropertyChange("useInternalCalibrant", this.useInternalCalibrant, this.useInternalCalibrant = useInternalCalibrant);
	}
	
	// Get the location of the calibration file
	@OperationModelField(hint="Absolute intensity calibration, e.g. glassy carbon, file path", file = FileType.EXISTING_FILE, label = "Calibration file", fieldPosition = 2, enableif = "useInternalCalibrant == false")
	private String absoluteScanFilePath = "GlassyCarbon_T.dat";
	
	// Set up the getter...
	public String getAbsoluteScanFilePath() {
		return absoluteScanFilePath;
	}

	// and setter.
	public void setAbsoluteScanFilePath(String absoluteScanFilePath) {
		firePropertyChange("absoluteScanFilePath", this.absoluteScanFilePath, this.absoluteScanFilePath = absoluteScanFilePath);
	}

	
	// Get the range over which the calibration is performed
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Radial range",hint="Two values, start and end, separated by a comma i.e. 2,4.The values should match the axis selected (i.e. q, 2 theta, pixel).If you delete the text, the range is cleared and the whole lineplot is used.", fieldPosition = 1)
	double[] radialRange = {0.015, 0.15};

	// Set up the getter...
	public double[] getRadialRange() {
		return radialRange;
	}	
	
	// and setter.
	public void setRadialRange(double[] radialRange) {
		firePropertyChange("radialRange", this.radialRange, this.radialRange = radialRange);
	}	

	// Sneakily show the user the scaling factor
	@OperationModelField(label = "Scaling Factor",hint="A quick way to show the user the scaling factor in lieu of not being able to achieve this in another fashion.", editable = false, fieldPosition = 100)
	double scalingFactor = 0.00;

	// Set up the getter...
	public double getScalingFactor() {
		return scalingFactor;
	}	
	
	// and setter.
	public void setScalingFactor(double scalingFactor) {
		firePropertyChange("scalingFactor", this.scalingFactor, this.scalingFactor = scalingFactor);
	}	

	// Sneakily show the user the scaling factor
	@OperationModelField(label = "Scaling Factor Standard Deviation",hint="A quick way to show the user the scaling factor standard deviation in lieu of not being able to achieve this in another fashion.", editable = false, fieldPosition = 100)
	double scalingFactorError = 0.00;

	// Set up the getter...
	public double getScalingFactorError() {
		return scalingFactorError;
	}	
	
	// and setter.
	public void setScalingFactorError(double scalingFactorError) {
		firePropertyChange("scalingFactorError", this.scalingFactorError, this.scalingFactorError = scalingFactorError);
	}	
}


