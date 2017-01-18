/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;

public class SimpleSectorIntegrationModel extends AbstractOperationModel {

	private SectorROI sector = new SectorROI();

	public SectorROI getSector() {
		return sector;
	}

	public void setSector(SectorROI sector) {
		firePropertyChange("sector", this.sector, this.sector = sector);
	}
	
	
}
