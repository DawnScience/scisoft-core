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

public class DerivativeModel extends AbstractOperationModel {

	@OperationModelField(min=1,max =5, hint="Enter the derivative order, between 1 and 5",label = "Derivative Order" )
	private int derivative = 1;
	@OperationModelField(min=1, hint="Enter the smoothing size",label = "Smoothing" )
	private int smoothing = 1;

	public int getDerivative() {
		return derivative;
	}

	public int getSmoothing() {
		return smoothing;
	}

	public void setSmoothing(int smoothing) {
		firePropertyChange("smoothing", this.smoothing, this.smoothing = smoothing);
	}

	public void setDerivative(int derivative) {
		firePropertyChange("derivative", this.derivative, this.derivative = derivative);
	}
	
}
