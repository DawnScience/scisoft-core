/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.BoxIntegration.Direction;

public class BoxIntegrationModel extends IntegrationModel {

//	@OperationModelField(label="Box Region", hint="The region to use with the operation.\n\nClick the '...' button to open the region dialog.")
//	protected IROI region;
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
