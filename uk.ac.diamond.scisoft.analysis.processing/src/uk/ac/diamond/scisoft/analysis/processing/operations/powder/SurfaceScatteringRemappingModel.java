/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class SurfaceScatteringRemappingModel extends AbstractOperationModel {

	@OperationModelField(hint="Tilt of the scatting surface in degrees, positive is tilting the surface toward the beam.", label = "Tilt", unit = "°")
	double pitch;
	
	@OperationModelField(hint="Roll of the surface relative to the detector, in degrees", label = "Roll", unit = "°")
	double roll;
	
	@OperationModelField(hint="Number of bins for the surface parallel component", label = "Para. component bins")
	int binsPara = 1000;
	
	@OperationModelField(hint="Number of bins for the surface perpendicular component", label = "Perp. component bins")
	int binsPerp = 1000;
	
	public void setPitch(double pitch) {
		firePropertyChange("pitch", this.pitch, this.pitch = pitch);
	}
	
	public double getPitch() {
		return pitch;
	}
	
	public void setRoll(double roll) {
		firePropertyChange("roll", this.roll, this.roll = roll);
	}
	
	public double getRoll() {
		return roll;
	}

	public void setBinsPara(int binsPara) {
		firePropertyChange("binsPara", this.binsPara, this.binsPara = binsPara);
	}
	
	public int getBinsPara() {
		return binsPara;
	}
	
	public void setBinsPerp(int binsPerp) {
		firePropertyChange("binsPerp", this.binsPerp, this.binsPerp = binsPerp);
	}
	
	public int getBinsPerp() {
		return binsPerp;
	}
	
}
