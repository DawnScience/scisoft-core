/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class PolynomialSmoothModel extends AbstractOperationModel {

	@OperationModelField(min=2, hint="Enter window size (should be even)",label = "Window Size" )
	private int window = 2;
	@OperationModelField(min=1, hint="Enter the smoothing polynomial order",label = "Polynomial Order" )
	private int polynomial = 1;
	
	
	public int getWindow() {
		return window;
	}
	public void setWindow(int window) {
		firePropertyChange("window", this.window, this.window = window);
	}
	
	public int getPolynomial() {
		return polynomial;
	}
	public void setPolynomial(int polynomial) {
		firePropertyChange("polynomial", this.polynomial, this.polynomial = polynomial);
	}
	
}
