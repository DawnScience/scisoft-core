/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class MaskOutliersInQModel extends AbstractOperationModel {

	
	@OperationModelField(hint="Set outlier scale factor", label = "Scale Multiplier")
	private double scale = 5;
	
	@OperationModelField(hint="Check to mask low outliers ", label = "Mask Low")
	private boolean low = false;
	
	@OperationModelField(hint="Check to mask high outliers ", label = "Mask High")
	private boolean high = true;

	public double getScale() {
		return scale;
	}

	public boolean isLow() {
		return low;
	}

	public void setLow(boolean low) {
		firePropertyChange("low", this.low, this.low = low);
	}

	public boolean isHigh() {
		return high;
	}

	public void setHigh(boolean high) {
		firePropertyChange("high", this.high, this.high = high);
	}

	public void setScale(double scale) {
		firePropertyChange("scale", this.scale, this.scale = scale);
	}
	
	
}
