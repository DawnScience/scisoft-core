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
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class XRegionProfileNormalizeModel extends AbstractOperationModel {

	@OperationModelField(rangevalue = RangeType.XRANGE, hint="Set range in X direction (i.e. 10,20)", label = "X Range")
	private double[] xRange = new double[]{10,20};
	
	@OperationModelField(hint="Set the smoothing ammount of the integrated line", label = "Smoothing Width")
	private int smoothing = 5;
	
	public double[] getxRange() {
		return xRange;
	}

	public void setxRange(double[] xRange) {
		firePropertyChange("xRange", this.xRange, this.xRange = xRange);
	}

	public int getSmoothing() {
		return smoothing;
	}

	public void setSmoothing(int smoothing) {
		firePropertyChange("smoothing", this.smoothing, this.smoothing = smoothing);
	}

}
