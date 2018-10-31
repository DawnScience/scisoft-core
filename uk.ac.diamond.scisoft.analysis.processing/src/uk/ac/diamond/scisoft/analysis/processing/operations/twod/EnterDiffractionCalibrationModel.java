/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class EnterDiffractionCalibrationModel extends AbstractOperationModel {
	
	@OperationModelField(hint = "Distance to the detector centre", label = "Detector distance", unit = "mm")
	private double detectorDistance = 100;
	
	@OperationModelField(hint = "Beam centre in pixel coordinates, X direction", label = "Beam centre X", unit = "px")
	private double beamCentreX = 0;
	
	@OperationModelField(hint = "Beam centre in pixel coordinates, Y direction", label = "Beam centre Y", unit = "px")
	private double beamCentreY = 0;
	
	@OperationModelField(hint = "Beam energy", label = "Energy", unit = "keV")
	private double energy = 12;
	
	@OperationModelField(hint = "Detector pitch", label = "Pitch", unit = "°")
	private double pitch = 0;
	
	@OperationModelField(hint = "Detector roll", label = "Roll", unit = "°")
	private double roll = 0;
	
	@OperationModelField(hint = "Detector yaw", label = "Yaw", unit = "°")
	private double yaw = 0;
	
	@OperationModelField(hint = "Pixel size, assumed isotropic", label = "Pixel size", unit = "mm")
	private double pixelSize = 0.172;

	
	public double getDetectorDistance() {
		return detectorDistance;
	}

	public void setDetectorDistance(double detectorDistance) {
		firePropertyChange("detectorDistance", this.detectorDistance, this.detectorDistance = detectorDistance);
	}

	public double getBeamCentreX() {
		return beamCentreX;
	}

	public void setBeamCentreX(double beamCentreX) {
		firePropertyChange("beamCentreX", this.beamCentreX, this.beamCentreX = beamCentreX);
	}

	public double getBeamCentreY() {
		return beamCentreY;
	}

	public void setBeamCentreY(double beamCentreY) {
		firePropertyChange("beamCentreY", this.beamCentreY, this.beamCentreY = beamCentreY);
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		firePropertyChange("energy", this.energy, this.energy = energy);
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		firePropertyChange("pitch", this.pitch, this.pitch = pitch);
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double roll) {
		firePropertyChange("roll", this.roll, this.roll = roll);
	}

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		firePropertyChange("yaw", this.yaw, this.yaw = yaw);
	}

	public double getPixelSize() {
		return pixelSize;
	}

	public void setPixelSize(double pixelSize) {
		firePropertyChange("pixelSize", this.pixelSize, this.pixelSize = pixelSize);
	}

}
