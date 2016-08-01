/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class BoxSlicer1DModel extends AbstractOperationModel {
	
	
	@OperationModelField(label="Boundary Box Direction", hint = "X or Y direction?" )
	private Direction direction = Direction.Y;
	
	@OperationModelField(label="Boundary Box size (pixels)", hint = "Size of the boundary box used for background calculation" )
	private int boundaryBox = 20;

	private RectangularROI box = new RectangularROI(100d, 100d, 10d, 10d, 0d);
	
	public int getBoundaryBox() {
		return boundaryBox;
	}

	
	public Direction getDirection() {
		return direction;
	}
	
	public void setBoundaryBox(int boundaryBox) {
		firePropertyChange("boundaryBox", this.boundaryBox, this.boundaryBox = boundaryBox);
	}

	public RectangularROI getBox() {
		return box;
	}
	
	public void setDirection(Direction direction) {
		firePropertyChange("direction", this.direction, this.direction = direction);
	}
	
	public void setBox(RectangularROI box) {
		firePropertyChange("box", this.box, this.box = box);
	}
	
	@OperationModelField(label="Fit power", hint = "Fit power" )
	private int fitPower = 2;
	
	public int getFitPower() {
		return fitPower;
	}

	public void setFitPower(int fitPower) {
		firePropertyChange("fitPower", this.fitPower, this.fitPower= fitPower);
	}
}
//TEST1
