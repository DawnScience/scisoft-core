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
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

/**
 * Base model for processing RIXS images
 */
public class RixsBaseModel extends AbstractOperationModel {
	@OperationModelField(label = "Rectangle A to search for elastic peak")
	private IRectangularROI roiA = new RectangularROI(49.68, 0, 872.83, 1623.58, 0);

	@OperationModelField(label = "Rectangle B to search for elastic peak")
	private IRectangularROI roiB = new RectangularROI(1200, 600, 700, 400, 0);

	public enum ENERGY_DIRECTION {
		SLOW, // slowest pixel direction
		FAST, // fastest pixel direction
	}

	// orientation (given by energy direction)
	@OperationModelField(label = "Energy direction", hint = "Slow is vertical; fast is horizontal")
	private ENERGY_DIRECTION energyDirection = ENERGY_DIRECTION.SLOW; // in 2D image

	@OperationModelField(label = "Cutoff for pixels as multiple of single photon", hint = "Use a negative value to not apply")
	private double cutoff = 5.0;

	public enum ENERGY_OFFSET {
		FROM_ELASTIC_LINE_FIT, // use value of intercept in fit
		MANUAL_OVERRIDE, // override if values are available
		TURNING_POINT,
	}

	@OperationModelField(label = "Zero energy offset option", hint = "From elastic line fit; manual override by given values; or from first turning point")
	private ENERGY_OFFSET energyOffsetOption = ENERGY_OFFSET.FROM_ELASTIC_LINE_FIT;

	@OperationModelField(label = "Zero energy offset value for rectangle A", hint = "Position on energy axis in pixels")
	private double energyOffsetA = Double.NaN;

	@OperationModelField(label = "Zero energy offset value for rectangle B", hint = "Position on energy axis in pixels")
	private double energyOffsetB = Double.NaN;

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
	 * @return get second region of interest (can be null to signify only use first ROI)
	 */
	public IRectangularROI getRoiB() {
		return roiB;
	}

	public void setRoiB(IRectangularROI roi) {
		firePropertyChange("setRoiB", this.roiB, this.roiB = roi);
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
	 * @return upper threshold multiple of single photon (in detector count units) used to ignore pixels
	 */
	public double getCutoff() {
		return cutoff;
	}

	public void setCutoff(double cutoff) {
		firePropertyChange("setCutoff", this.cutoff, this.cutoff = cutoff);
	}

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
}
