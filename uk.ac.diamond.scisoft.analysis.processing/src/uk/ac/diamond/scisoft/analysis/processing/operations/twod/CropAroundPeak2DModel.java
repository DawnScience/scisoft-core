/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class CropAroundPeak2DModel extends AbstractOperationModel {

	@OperationModelField(label = "x-Box Length", hint="The size of the cropped box in the x-Dimension.", fieldPosition = 1)
	private double xBoxLength = 10;
	
	public double getXBoxLength() {
		return xBoxLength;
	}
	
	public void setXBoxLength(double xBoxLength) {
		firePropertyChange("xBoxLength", this.xBoxLength, this.xBoxLength = xBoxLength);
	}
	
	@OperationModelField(label = "y-Box Length", hint="The size of the cropped box in the y-Dimension.", fieldPosition = 2)
	private double yBoxLength = 10;
	
	public double getYBoxLength() {
		return yBoxLength;
	}
	
	public void setYBoxLength(double yBoxLength) {
		firePropertyChange("yBoxLength", this.yBoxLength, this.yBoxLength = yBoxLength);
	}

}