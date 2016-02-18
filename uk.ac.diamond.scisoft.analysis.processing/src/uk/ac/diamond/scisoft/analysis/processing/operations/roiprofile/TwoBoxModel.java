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

public class TwoBoxModel extends AbstractOperationModel {

	@OperationModelField(hint="Define the first region of interest", label = "Box 1")
	private RectangularROI box1 = new RectangularROI(0, 0, 10, 10, 0);
	@OperationModelField(hint="Define the second region of interest.", label = "Box 2")
	private RectangularROI box2 = new RectangularROI(0, 0, 10, 10, 0);
	public RectangularROI getBox1() {
		return box1;
	}
	public void setBox1(RectangularROI box1) {
		firePropertyChange("box1", this.box1, this.box1 = box1);
	}
	public RectangularROI getBox2() {
		return box2;
	}
	public void setBox2(RectangularROI box2) {
		firePropertyChange("box2", this.box2, this.box2 = box2);
	}
	
}
