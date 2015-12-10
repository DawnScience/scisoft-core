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
	
}
