/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.processing.operations.IntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class ImageIntegrationModel extends ImageIntegrationEncumbranceModel {

	@OperationModelField(label="Direction of Integration", hint="The direction to integrate in.")
	private Direction direction = Direction.X;
	@OperationModelField(label="Do average?", hint="Average the data along the chosen axis, rather than sum.")
	private boolean doAverage = false;
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		firePropertyChange("direction", this.direction, this.direction = direction);
	}

	public boolean isDoAverage() {
		return doAverage;
	}
	
	public void setDoAverage(boolean doAverage) {
		firePropertyChange("doAverage", this.doAverage, this.doAverage = doAverage);
	}
}

class ImageIntegrationEncumbranceModel extends IntegrationModel {
	
}