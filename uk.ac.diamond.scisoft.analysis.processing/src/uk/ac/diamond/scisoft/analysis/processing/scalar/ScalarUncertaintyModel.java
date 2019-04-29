/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.scalar;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ScalarUncertaintyModel extends AbstractOperationModel {

	@OperationModelField(label = "Value", hint="Amount by which to shift the x-axis", fieldPosition = 1)
	private double value = 0;
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		firePropertyChange("value", this.value, this.value = value);
	}
	
	@OperationModelField(label = "Uncertainty", hint="Uncertainity in shift to be propagated")
	private double error = 0;
	
	public double getError() {
		return error; 
	}
	
	public void setError(double error) {
		firePropertyChange("error", this.error, this.error = error);
	}
}