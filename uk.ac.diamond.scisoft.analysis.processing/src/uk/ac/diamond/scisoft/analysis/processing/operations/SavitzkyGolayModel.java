/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * Savitzky-Golay filtering operation
 * 
 * @author Tom Schoonjans
 *
 */
public class SavitzkyGolayModel extends AbstractOperationModel {

	@OperationModelField(fieldPosition=0, min=3, hint="The length of the filter window (i.e. the number of coefficients). Must be a positive odd integer.", label="Window")
	private int window = 5;
	
	@OperationModelField(fieldPosition=1, min=1, hint="The order of the polynomial used to fit the samples. Must be less than window.", label="Poly")
	private int poly = 3;
	
	@OperationModelField(fieldPosition=2, min=0, hint="The order of the derivative to compute. Must be a non-negative integer. Use zero if no derivative is required", label="Derivative")
	private int deriv;

	public int getWindow() {
		return window;
	}

	public void setWindow(int window) {
		firePropertyChange("window", this.window, this.window = window);
	}

	public int getPoly() {
		return poly;
	}

	public void setPoly(int poly) {
		firePropertyChange("poly", this.poly, this.poly = poly);
	}

	public int getDeriv() {
		return deriv;
	}

	public void setDeriv(int deriv) {
		firePropertyChange("deriv", this.deriv, this.deriv = deriv);
	}
	


}

