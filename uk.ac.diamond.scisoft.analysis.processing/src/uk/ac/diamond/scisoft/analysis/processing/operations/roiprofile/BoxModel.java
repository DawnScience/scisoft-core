/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

public class BoxModel extends AbstractOperationModel {

	@OperationModelField(hint="Define the region of interest.", label = "Box")
	private RectangularROI box = new RectangularROI(0, 0, 10, 10, 0);
	public RectangularROI getBox() {
		return box;
	}
	public void setBox(RectangularROI box) {
		firePropertyChange("box", this.box, this.box = box);
	}
	
}
