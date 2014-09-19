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
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

public class RotatedCartesianBoxModel extends AbstractOperationModel {

	private IRectangularROI roi;
	
	public RotatedCartesianBoxModel() {
		roi = new RectangularROI(0,0,10,10,0);
	}

	public IRectangularROI getRoi() {
		return roi;
	}

	public void setRoi(IRectangularROI roi) {
		this.roi = roi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roi == null) ? 0 : roi.hashCode());
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
		RotatedCartesianBoxModel other = (RotatedCartesianBoxModel) obj;
		if (roi == null) {
			if (other.roi != null)
				return false;
		} else if (!roi.equals(other.roi))
			return false;
		return true;
	}
}
