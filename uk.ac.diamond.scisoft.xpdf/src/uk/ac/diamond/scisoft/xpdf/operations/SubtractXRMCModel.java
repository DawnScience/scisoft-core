/*
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class SubtractXRMCModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter a value for the fixed gain", label = "Fixed gain")
	private Double fixedGain = null;
	@OperationModelField(hint="Enter a value for the final scaling", label="Final scaling")
	private double scaling = 1.0;
	
	public Double getFixedGain() {
		return fixedGain;
	}
	public void setFixedGain(Double fixedGain) {
		firePropertyChange("fixedGain", this.fixedGain, this.fixedGain = fixedGain);
	}
	
	public double getScaling() {
		return scaling;
	}
	public void setScaling(double scaling) {
		firePropertyChange("scaling", this.scaling, this.scaling = scaling);
	}

}
