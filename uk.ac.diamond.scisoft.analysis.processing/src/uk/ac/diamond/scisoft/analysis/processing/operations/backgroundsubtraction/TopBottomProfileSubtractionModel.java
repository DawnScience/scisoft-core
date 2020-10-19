/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Benedikt Daurer

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * Model for subtracting a common horizontal line based on 
 * the average line profile in a given region
 */
public class TopBottomProfileSubtractionModel extends AbstractOperationModel {
	
	@OperationModelField(label = "Window fraction", hint = "The fraction of top/bottom half used for calculating the average profle.")
	private double windowFraction = 0.5;

	public double getWindowFraction() {
		return windowFraction;
	}
	
	public void setWindowFraction(double windowFraction) {
		firePropertyChange("windowFraction", this.windowFraction, this.windowFraction = windowFraction);
	}
	
	@OperationModelField(label = "Center offset", hint = "Vertical offset between image center and beam center given in pixels between")
	private int centerOffset = 0;

	public int getCenterOffset() {
		return centerOffset;
	}
	
	public void setCenterOffset(int centerOffset) {
		firePropertyChange("centerOffset", this.centerOffset, this.centerOffset = centerOffset);
	}


}