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

public class XPDFLorchFTModel extends AbstractOperationModel {

	// TODO: Change defaults to real defaults, rather than ceria SRM defaults
	@OperationModelField(hint="Enter the maximum radius to consider", label="Rmax (Angstroms)")
	private double rMax = 50.0;
	@OperationModelField(hint="Enter the radius increment", label="Rstep (Angstroms)")
	private double rStep = 0.02;
	@OperationModelField(hint="Enter the width of the Lorch Fourier filter", label="Lorch width")
	private double lorchWidth = 0.2;

	public double getrMax() {
		return rMax;
	}
	public void setrMax(double rMax) {
		firePropertyChange("rMax", this.rMax, this.rMax = rMax);
	}
	public double getrStep() {
		return rStep;
	}
	public void setrStep(double rStep) {
		firePropertyChange("rStep", this.rStep, this.rStep = rStep);
	}
	public double getLorchWidth() {
		return lorchWidth;
	}
	public void setLorchWidth(double lorchWidth) {
		firePropertyChange("lorchWidth", this.lorchWidth, this.lorchWidth = lorchWidth);
	}
}
