/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class GeneralDetectorErrorModel extends AbstractOperationModel {

	double gain = 1;
	double gainFluctuation = 0;
	double quantumEfficiency = 1;
	double electronicNoise = 0;
	
	public double getGain() {
		return gain;
	}
	public void setGain(double gain) {
		firePropertyChange("gain", this.gain, this.gain = gain);
	}
	public double getGainFluctuation() {
		return gainFluctuation;
	}
	public void setGainFluctuation(double gainFluctuation) {
		firePropertyChange("gainFluctuation", this.gainFluctuation, this.gainFluctuation = gainFluctuation);
	}
	public double getQuantumEfficiency() {
		return quantumEfficiency;
	}
	public void setQuantumEfficiency(double quantumEfficiency) {
		firePropertyChange("quantumEfficiency", this.quantumEfficiency, this.quantumEfficiency = quantumEfficiency);
	}
	public double getElectronicNoise() {
		return electronicNoise;
	}
	public void setElectronicNoise(double electronicNoise) {
		firePropertyChange("electronicNoise", this.electronicNoise, this.electronicNoise = electronicNoise);
	}
	
}
