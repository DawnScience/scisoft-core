/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class Crop1DModel extends AbstractOperationModel {
	
	@OperationModelField(rangevalue = RangeType.XSINGLE, hint="Set beginning of range to crop data to", label = "Start", fieldPosition = 0)
	private Double min = null;
	
	@OperationModelField(rangevalue = RangeType.XSINGLE,hint="Set end of range to crop data to", label = "Stop",fieldPosition = 1)
	private Double max = null;
	
	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		firePropertyChange("min", this.min, this.min = min);
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		firePropertyChange("max", this.max, this.max = max);
	}
}
