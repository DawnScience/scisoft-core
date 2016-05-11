/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class Crop2DModel extends AbstractOperationModel {
	
	@OperationModelField(rangevalue = RangeType.XSINGLE,hint="Lower bound of crop in X-direction", label = "X Start", fieldPosition = 0)
	private Double xMin = null;
	
	@OperationModelField(rangevalue = RangeType.XSINGLE,hint="Upper bound of crop in X-direction", label = "X Stop", fieldPosition = 1)
	private Double xMax = null;
	
	@OperationModelField(rangevalue = RangeType.YSINGLE,hint="Lower bound of crop in Y-direction", label = "Y Start", fieldPosition = 2)
	private Double yMin = null;
	
	@OperationModelField(rangevalue = RangeType.YSINGLE,hint="Upper bound of crop in Y-direction", label = "Y Stop", fieldPosition = 3)
	private Double yMax = null;

	public Double getxMin() {
		return xMin;
	}

	public void setxMin(Double xMin) {
		firePropertyChange("xMin", this.xMin, this.xMin = xMin);
	}

	public Double getxMax() {
		return xMax;
	}

	public void setxMax(Double xMax) {
		firePropertyChange("xMax", this.xMax, this.xMax = xMax);
		this.xMax = xMax;
	}

	public Double getyMin() {
		return yMin;
	}

	public void setyMin(Double yMin) {
		firePropertyChange("yMin", this.yMin, this.yMin = yMin);
	}

	public Double getyMax() {
		return yMax;
	}

	public void setyMax(Double yMax) {
		firePropertyChange("yMax", this.yMax, this.yMax = yMax);
	}

}
