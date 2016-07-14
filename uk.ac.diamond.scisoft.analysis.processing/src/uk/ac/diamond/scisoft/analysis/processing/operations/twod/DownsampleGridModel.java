/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class DownsampleGridModel extends AbstractOperationModel {

	@OperationModelField(label = "Approximage image size", hint = "Size of the largest dimension of the output image, setting to large may cause memory issues.")
	private int imageSize = 10000;

	public int getImageSize() {
		return imageSize;
	}

	public void setImageSize(int imageSize) {
		firePropertyChange("imageSize", this.imageSize, this.imageSize = imageSize);
	}
	
}
