/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class DilateMaskModel extends AbstractOperationModel {

	@OperationModelField(hint="Increase size of masked areas but a set number of pixels", label = "Set dilation size")
	private int numberOfPixelsToDilate = 1;

	public int getNumberOfPixelsToDilate() {
		return numberOfPixelsToDilate;
	}

	public void setNumberOfPixelsToDilate(int numberOfPixelsToDilate) {
		firePropertyChange("pixelsToDilate", this.numberOfPixelsToDilate, this.numberOfPixelsToDilate = numberOfPixelsToDilate);
	}
	
}
