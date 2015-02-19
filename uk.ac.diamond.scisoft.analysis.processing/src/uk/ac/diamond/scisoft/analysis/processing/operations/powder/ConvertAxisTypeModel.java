/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class ConvertAxisTypeModel extends AbstractOperationModel {
	
	@OperationModelField(hint="Set value for X axis after integration", label = "Select X Axis")
	private XAxis axisType = XAxis.Q;
	
	@OperationModelField(hint="Use wavelength from detector calibration.", label = "Use Calibrated Wavelength")
	private boolean useCalibratedWavelength = true;
	
	@OperationModelField(hint="Specify a wavelength to use.", label = "Wavelength")
	private Double userWavelength = null;
	
	public XAxis getAxisType() {
		return axisType;
	}
	
	public void setAxisType(XAxis axisType) {
		firePropertyChange("axisType", this.axisType, this.axisType = axisType);
	}
	
	public boolean isUseCalibratedWavelength() {
		return useCalibratedWavelength;
	}

	public void setUseCalibratedWavelength(boolean useCalibratedWavelength) {
		firePropertyChange("useCalibratedWavelength", this.useCalibratedWavelength, this.useCalibratedWavelength = useCalibratedWavelength);
	}

	public Double getUserWavelength() {
		return userWavelength;
	}

	public void setUserWavelength(Double userWavelength) {
		firePropertyChange("userWavelength", this.userWavelength, this.userWavelength = userWavelength);
	}
}
