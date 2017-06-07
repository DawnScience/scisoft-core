/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class TwoDPolynomialBackgroundFitAndSubtractModel extends BoxSlicerModel {

	@OperationModelField(label="Allow negative values", hint="Allow negative values")
	private boolean allowNegativeValues = false;

	public boolean isAllowNegativeValues() {
		return allowNegativeValues;
	}

	public void setAllowNegativeValues(boolean allowNegativeValues) {
		this.allowNegativeValues = allowNegativeValues;
		firePropertyChange("allowNegativeValues", this.allowNegativeValues, this.allowNegativeValues = allowNegativeValues);
	}
	
	
}
