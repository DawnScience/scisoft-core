/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class InterpolateMissingDataModel extends AbstractOperationModel {

	@OperationModelField(hint="A missing data value indicating data to be ignored", label = "Missing Data Value")
	private Double mdi = null;

	public Double getMdi() {
		return mdi;
	}

	public void setMdi(Double mdi) {
		firePropertyChange("mdi", mdi, this.mdi = mdi);
	}
}
