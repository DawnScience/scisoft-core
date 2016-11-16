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
	Integer binsPara = null;
	
	@OperationModelField(hint="Number of bins for the surface perpendicular component", label = "Perp. component bins")
	Integer binsPerp = null;
	
	@OperationModelField(label = "Perp. range",hint="Two values, start and end, separated by a comma i.e. 2,4.The values should be in q.If you delete the text, the range is cleared and the whole image used.")
	double[] perpRange = null;
	
	@OperationModelField(label = "Para. range",hint="Two values, start and end, separated by a comma i.e. 2,4.The values should be in q.If you delete the text, the range is cleared and the whole image used.")
	double[] parRange = null;
	
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

	public void setBinsPara(Integer binsPara) {
		firePropertyChange("binsPara", this.binsPara, this.binsPara = binsPara);
	}
	
	public Integer getBinsPara() {
		return binsPara;
	}
	
	public void setBinsPerp(Integer binsPerp) {
		firePropertyChange("binsPerp", this.binsPerp, this.binsPerp = binsPerp);
	}
	
	public Integer getBinsPerp() {
		return binsPerp;
	}
	
	public double[] getPerpRange() {
		return perpRange;
	}

	public void setPerpRange(double[] perpRange) {
		firePropertyChange("perpRange", this.perpRange, this.perpRange = perpRange);
	}

	public double[] getParRange() {
		return parRange;
	}
	
	public void setParRange(double[] parRange) {
		firePropertyChange("parRange", this.parRange, this.parRange = parRange);
	}
	
}
