/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class InterpolateXRMCToProjectiveModel extends AbstractOperationModel {
	@OperationModelField(label = "x range", hint = "Range of the projective x axis")
	private double[] xpRange = null;
	
	@OperationModelField(label = "y range", hint = "Range of the projective y axis")
	private double[] ypRange = null;
	
	@OperationModelField(label = "Array size", hint = "Size of the projective coordinate array")
	private int[] size = null;

	public double[] getXpRange() {
		return this.xpRange;
	}
	public void setXpRange(double[] xpRange) {
		firePropertyChange("xpRange", this.xpRange, this.xpRange = xpRange);
	}

	public double[] getYpRange() {
		return this.ypRange;
	}
	public void setYpRange(double[] ypRange) {
		firePropertyChange("ypRange", this.ypRange, this.ypRange = ypRange);
	}
	
	public int[] getSize() {
		return this.size;
	}
	public void setSize(int[] size) {
		firePropertyChange("size", this.size, this.size = size);
	}

}
