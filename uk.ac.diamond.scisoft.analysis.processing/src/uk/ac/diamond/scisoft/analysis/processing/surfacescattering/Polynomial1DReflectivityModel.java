/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.surfacescattering;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class Polynomial1DReflectivityModel extends AbstractOperationModel {

	@OperationModelField(label="Boundary Box Direction", hint = "X or Y direction?" )
	private Direction direction = Direction.Y;
	
	@OperationModelField(label="Boundary Box size (pixels)", hint = "Size of the boundary box used for background calculation" )
	private int boundaryBox = 20;

	@OperationModelField(label="beam height", hint = "beam height" )
	private double beamHeight = 1;

	@OperationModelField(label="Fit power", hint = "Fit power" )
	private int fitPower = 5;
	
	@OperationModelField(label="footprint", hint = "footprint of smaple in mm" )
	private double footprint = 1;
	
	@OperationModelField(label="angular fudge factor", hint = "angular fudge factor" )
	private double angularFudgeFactor = 1;
	
	@OperationModelField(label="Path to normalisation file", hint = "Path to normalisation file" )
	private String path = "/scratch/233990.dat";
	
	private RectangularROI box = new RectangularROI(100d, 100d, 10d, 10d, 0d);
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getBoundaryBox() {
		return boundaryBox;
	}

	public void setBoundaryBox(int boundaryBox) {
		firePropertyChange("boundaryBox", this.boundaryBox, this.boundaryBox = boundaryBox);
	}

	public void setDirection(Direction direction) {
		firePropertyChange("direction", this.direction, this.direction = direction);
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		firePropertyChange("path", this.path, this.path= path);
	}

	public double getAngularFudgeFactor() {
		return angularFudgeFactor;
	}
	
	public void setAngularFudgeFactor(double angularFudgeFactor) {
		firePropertyChange("angularFudgeFactor", this.angularFudgeFactor, this.angularFudgeFactor= angularFudgeFactor);
	}

	public double getFootprint() {
		return footprint;
	}

	public void setFootprint(double footprint) {
		firePropertyChange("footprint", this.footprint, this.footprint= footprint);
	}

	public int getFitPower() {
		return fitPower;
	}

	public void setFitPower(int fitPower) {
		firePropertyChange("fitPower", this.fitPower, this.fitPower= fitPower);
	}

	public double getBeamHeight() {
		return beamHeight;
	}

	public void setBeamHeight(double beamHeight) {
		firePropertyChange("beamHeight", this.beamHeight, this.beamHeight= beamHeight);
	}
	
	public RectangularROI getBox() {
		return box;
	}
	
	public void setBox(RectangularROI box) {
		firePropertyChange("box", this.box, this.box = box);
	}
	
	
}
