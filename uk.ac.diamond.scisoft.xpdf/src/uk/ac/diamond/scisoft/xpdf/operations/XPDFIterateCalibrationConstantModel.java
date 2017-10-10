/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XPDFIterateCalibrationConstantModel extends AbstractOperationModel {

	
	@OperationModelField(hint="Enter the number of iterations to perform",label = "Number of iterations" )
	private int nIterations = 5;
	@OperationModelField(hint="Enter whether to sort the previously inserted containers",label = "Sort containers?" )
	private boolean sortContainers = false;
	@OperationModelField(hint="Do the fluorescence calculations as part of the calibration", label = "Correct fluorescence?")
	private boolean doingFluorescence = true;
	@OperationModelField(hint="Perform a real fluorescence calculation", label = "Calculate fluorescence?", enableif = "doingFluorescence == true")
	private boolean calculatingFluorescence = true;
	@OperationModelField(hint="Fixed user fluorescence scale", label = "Fluorescence scale", enableif = "calculatingFluorescence == false")
	private double fluorescenceScale = 0.0;
	@OperationModelField(hint="Regenegerate the absorption maps", label="Regenerate absorption maps")
	private boolean regenerateAbsorptionMaps = false;
	@OperationModelField(hint="Number of parallel threads to use for the fluorescence calibration", label = "Parallel threads")
	private int nThreads = 1;
	@OperationModelField(hint="Polarization factor of the incident beam", label = "Polarization factor")
	private double polarizationFactor = 1.0;
	@OperationModelField(hint = "Compton Scattering scaling factor (non-physical)", label = "Compton Scaling")
	private double comptonScaling = 1.0; 
	
	public double getPolarizationFactor() {
		return polarizationFactor;
	}

	public void setPolarizationFactor(double polarizationFactor) {
		firePropertyChange("polarizationFactor", this.polarizationFactor, this.polarizationFactor = polarizationFactor);
	}

	public double getComptonScaling() {
		return comptonScaling;
	}

	public void setComptonScaling(double comptonScaling) {
		firePropertyChange("comptonScaling", this.comptonScaling, this.comptonScaling = comptonScaling);
	}

	public int getnIterations() {
		return nIterations;
	}

	public void setnIterations(int nIterations) {
		firePropertyChange("nIterations", this.nIterations, this.nIterations = nIterations);
	}

	public boolean isSortContainers() {
		return sortContainers;
	}

	public void setSortContainers(boolean sortContainers) {
		firePropertyChange("sortContainers", this.sortContainers, this.sortContainers = sortContainers);
	}

	public boolean isDoingFluorescence() {
		return doingFluorescence;
	}
	
	public void setDoingFluorescence(boolean doFluorescence) {
		firePropertyChange("doingFluorescence", this.doingFluorescence, this.doingFluorescence = doFluorescence);
	}
	
	public boolean isCalculatingFluorescence() {
		return calculatingFluorescence;
	}
	
	public void setCalculatingFluorescence(boolean calcFluorescence) {
		firePropertyChange("calculatingFluorescence", this.calculatingFluorescence, this.calculatingFluorescence = calcFluorescence);
	}
	
	public double getFluorescenceScale() {
		return fluorescenceScale;
	}
	
	public void setFluorescenceScale(double fluorescenceScale) {
		firePropertyChange("fluorescenceScale", this.fluorescenceScale, this.fluorescenceScale = fluorescenceScale);
	}

	public boolean getRegenerateAbsorptionMaps() {
		return regenerateAbsorptionMaps;
	}

	public void setRegenerateAbsorptionMaps(boolean regenerateAbsorptionMaps) {
		firePropertyChange("regenerateAbsorptionMaps", this.regenerateAbsorptionMaps, this.regenerateAbsorptionMaps = regenerateAbsorptionMaps);
	}

	public int getNThreads() {
		return nThreads;
	}
	
	public void setNThreads(int nThreads) {
		firePropertyChange("nThreads", this.nThreads, this.nThreads = nThreads);
	}

}
