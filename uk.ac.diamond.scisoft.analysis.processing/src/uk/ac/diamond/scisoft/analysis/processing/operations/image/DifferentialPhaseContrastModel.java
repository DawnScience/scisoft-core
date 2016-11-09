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
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * Differential Phase Contrast Operation.
 * This is a centroid operation, but with unit conversion to radians.
 * @author Aaron Parsons
 *
 */
public class DifferentialPhaseContrastModel extends AbstractOperationModel {

	@OperationModelField(fieldPosition=0, min=0.0, max=100.0, hint="The distance from the sample to the detector in metres", label="Distance")
	private double dist = 1.0;

	@OperationModelField(fieldPosition=1, min=0.0, max=1e-3, hint="The pixel size of the detector in metres.", label="Pixel Size")
	private double pix = 1e-6;
	
	
	public double getDist() {
		return dist;
	}

	public double getPix() {
		return pix;
	}

	public void setDist(double dist) {
		firePropertyChange("Distance", this.dist, this.dist = dist);
	}

	public void setPix(double pix) {
		firePropertyChange("Pixel Size", this.pix, this.pix = pix);
	}
	
}

