/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

@Atomic
public class BoxIntegration extends BoxImageIntegrationCommon<BoxIntegrationModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.boxIntegration";
	}

	@Override
    public String getName() {
		return "Box Integration";
	}
	
	@Override
	protected Dataset selectData(Dataset x, Dataset y) {
		return (model.getDirection() == Direction.X) ? x : y;
	}

	@Override
	protected RectangularROI getRect(IDataset input) {
		return (RectangularROI) getRegion();
	}
	
}
