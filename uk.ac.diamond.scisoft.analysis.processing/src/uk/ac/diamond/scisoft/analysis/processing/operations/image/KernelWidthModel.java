/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class KernelWidthModel extends AbstractOperationModel {

	@OperationModelField(hint="Set width of filter kernel in pixels", label = "Set Width")
	private int width = 3;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		firePropertyChange("width", this.width, this.width = width);
	}
	
}
