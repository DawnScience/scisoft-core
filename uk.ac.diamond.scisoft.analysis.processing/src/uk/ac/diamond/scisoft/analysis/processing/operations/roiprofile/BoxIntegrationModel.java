/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.IntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class BoxIntegrationModel extends IntegrationModel {

	@OperationModelField(label="Direction of Integration", hint="The direction to integrate over the box.")
	private Direction direction = Direction.X;

	public BoxIntegrationModel() {
		super();
		setRegion(new RectangularROI(0d, 0d, 10d, 10d, 0d));
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		firePropertyChange("direction", this.direction, this.direction = direction);
	}
}
