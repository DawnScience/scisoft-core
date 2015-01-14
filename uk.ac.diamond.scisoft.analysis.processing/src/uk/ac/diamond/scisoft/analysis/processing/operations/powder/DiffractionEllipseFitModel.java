/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class DiffractionEllipseFitModel extends AbstractOperationModel {

	double qValue = 0;
	double qDelta = 0;

	public double getqValue() {
		return qValue;
	}

	public double getqDelta() {
		return qDelta;
	}

	public void setqDelta(double qDelta) {
		firePropertyChange("qDelta", this.qDelta, this.qDelta = qDelta);
	}

	public void setqValue(double qValue) {
		firePropertyChange("qValue", this.qValue, this.qValue = qValue);
	}

}
