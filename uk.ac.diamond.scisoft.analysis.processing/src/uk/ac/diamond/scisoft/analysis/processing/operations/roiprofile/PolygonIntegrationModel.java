/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IROI;

public class PolygonIntegrationModel extends AbstractOperationModel {
	
	// make perhaps invisible?
	@OperationModelField(hint="Define the region of interest", label = "Region of Interest", editable=false)
	protected IROI regionOfInterest = null;

	public IROI getRegionOfInterest() {
		return regionOfInterest;
	}

	public void setRegionOfInterest(IROI regionOfInterest) {
		firePropertyChange("regionOfInterest", this.regionOfInterest, this.regionOfInterest = regionOfInterest != null ? regionOfInterest.copy() : null);
	}

}
