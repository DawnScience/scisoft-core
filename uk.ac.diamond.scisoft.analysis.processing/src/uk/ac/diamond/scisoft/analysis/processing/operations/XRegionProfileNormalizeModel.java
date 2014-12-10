/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XRegionProfileNormalizeModel extends AbstractOperationModel {

	@OperationModelField(hint="Set lower bound of crop in X-direction", label = "X Beginning")
	private double xStart = 10;
	
	@OperationModelField(hint="Set upper bound of crop in X-direction", label = "X End")
	private double xEnd = 20;
	
	@OperationModelField(hint="Set the smoothing ammount of the integrated line", label = "Smoothing Width")
	private int smoothing = 5;
	

	public double getxStart() {
		return xStart;
	}

	public void setxStart(double xStart) {
		this.xStart = xStart;
	}

	public double getxEnd() {
		return xEnd;
	}

	public void setxEnd(double xEnd) {
		this.xEnd = xEnd;
	}

	public int getSmoothing() {
		return smoothing;
	}

	public void setSmoothing(int smoothing) {
		this.smoothing = smoothing;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + smoothing;
		long temp;
		temp = Double.doubleToLongBits(xEnd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xStart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XRegionProfileNormalizeModel other = (XRegionProfileNormalizeModel) obj;
		if (smoothing != other.smoothing)
			return false;
		if (Double.doubleToLongBits(xEnd) != Double
				.doubleToLongBits(other.xEnd))
			return false;
		if (Double.doubleToLongBits(xStart) != Double
				.doubleToLongBits(other.xStart))
			return false;
		return true;
	}

}
