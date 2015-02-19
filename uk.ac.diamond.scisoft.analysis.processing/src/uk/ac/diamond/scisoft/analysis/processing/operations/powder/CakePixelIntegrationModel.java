/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class CakePixelIntegrationModel extends PixelIntegrationModel {

	@OperationModelField(min=2,max=1000000, hint="Set number of bins for azimuthal axis",label = "Set number of azimuthal bins" )
	private Integer numberOfBins2ndAxis = null;
	@OperationModelField(hint="Set value for X axis after integration", label = "Select X Axis")
	private XAxis axisType = XAxis.Q;
	
	public XAxis getAxisType() {
		return axisType;
	}
	
	public void setAxisType(XAxis axisType) {
		firePropertyChange("axisType", this.axisType, this.axisType = axisType);
	}
	
	public Integer getNumberOfBins2ndAxis() {
		return numberOfBins2ndAxis;
	}

	public void setNumberOfBins2ndAxis(Integer numberOfBins2ndAxis) {
		firePropertyChange("numberOfBins2ndAxis", this.numberOfBins2ndAxis, this.numberOfBins2ndAxis = numberOfBins2ndAxis);
	}

}
