/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class DetectorEfficiencyCalculationModel extends AbstractOperationModel {
	
	
	@OperationModelField(hint="Enter the sensor's chemical composition",label = "Sensor's Chemical Composition", fieldPosition = 1)
	private String sensorComposition = "Si";

	public String getSensorComposition() {
		return sensorComposition;
	}
	
	public void setSensorComposition(String sensorComposition) {
		firePropertyChange("sensorComposition", this.sensorComposition, this.sensorComposition = sensorComposition);
	}
	
	
	@OperationModelField(hint="Enter the sensor density, expressed in g/cm3",label = "Sensor Density", unit = "g/cm3", fieldPosition = 2)
	private double sensorDensity = 2.3290;

	public double getSensorDensity() {
		return sensorDensity;
	}
	
	public void setSensorDensity(double sensorDensity) {
		firePropertyChange("sensorDensity", this.sensorDensity, this.sensorDensity = sensorDensity);
	}
	
	
	@OperationModelField(hint="Enter the detector sensor thickness, expressed in cm",label = "Sensor thickness", unit = "cm", fieldPosition = 3)
	private double sensorThickness = 0.045;

	public double getSensorThickness() {
		return sensorThickness;
	}

	public void setSensorThickness(double sensorThickness) {
		firePropertyChange("sensorThickness", this.sensorThickness, this.sensorThickness = sensorThickness);
	}
	
	
	@OperationModelField(label = "Manually enter beam energy?", hint="Insert the beam energy manually, for when there's no calibrated or measured value for the beam energy associated with the dataset", fieldPosition = 4)
	private boolean manualEnergyValueUsed = false;
	
	public boolean isManualEnergyValueUsed() {
		return manualEnergyValueUsed;
	}
	
	public void setManualEnergyValueUsed(boolean manualEnergyValueUsed) {
		firePropertyChange("manualEnergyValueUsed", this.manualEnergyValueUsed, this.manualEnergyValueUsed = manualEnergyValueUsed);
	}
	
	
	@OperationModelField(hint="Enter the beam energy, expressed in keV", label = "Beam energy", unit = "keV", enableif = "manualEnergyValueUsed == true", fieldPosition = 5)
	private double beamEnergy;

	public double getBeamEnergy() {
		return beamEnergy;
	}

	public void setBeamEnergy(double beamEnergy) {
		firePropertyChange("beamEnergy", this.beamEnergy, this.beamEnergy = beamEnergy);
	}
}
