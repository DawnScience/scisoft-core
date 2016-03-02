/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class CoordinateMaskModel extends AbstractOperationModel {
	
	@OperationModelField(label = "Range",hint="Please set two values, start and end, separated by a comma i.e. 2,4. The values should match the coordinate selected (i.e. q, 2 theta, pixel).If you delete the text, the range is cleared and the whole image used.")
	double[] coordinateRange = null;

	@OperationModelField(hint="Set coordinate for to mask over", label = "Coordinate")
	private XAxis coordinateType = XAxis.Q;

	@OperationModelField(label = "Mask range?", hint="Mask over the range when checked, outside the range otherwise")
	private boolean maskedInside = false;
	
	public boolean isMaskedInside() {
		return this.maskedInside;
	}
	
	public void setMaskedInside(boolean maskedInside) {
		firePropertyChange("maskedInside", this.maskedInside, this.maskedInside = maskedInside);
	}
	
	public double[] getCoordinateRange() {
		return this.coordinateRange;
	}
	
	public void setCoordinateRange(double[] coordinateRange) {
		firePropertyChange("coordinateRange", this.coordinateRange, this.coordinateRange = coordinateRange);
	}
	
	public XAxis getCoordinateType() {
		return this.coordinateType;
	}
	
	public void setCoordinateType(XAxis coordinateType) {
		firePropertyChange("coordinateType", this.coordinateType, this.coordinateType = coordinateType);
	}
	
}
