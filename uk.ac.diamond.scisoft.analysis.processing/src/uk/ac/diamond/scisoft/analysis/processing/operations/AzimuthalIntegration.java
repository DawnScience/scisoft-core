/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public class AzimuthalIntegration extends AbstractIntegrationOperation<SectorIntegrationModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.azimuthalIntegration";
	}
	
	@Override
    public String getName() {
		return "Sector Azimuthal Profile";
	}


	@Override
	public OperationData process(IDataset slice, IMonitor monitor) throws OperationException {
		
		Dataset mask = DatasetUtils.convertToDataset(getFirstMask(slice));
		SectorROI sector = (SectorROI)getRegion();
		
		if (slice.getRank() != 2) {
			slice = slice.getSliceView().squeeze(true);
		}
		final Dataset[] profile = ROIProfile.sector((Dataset)slice, mask, sector, false, true, false);
		
		Dataset integral = profile[1];
		integral.setName("Azimuthal Profile "+sector.getName());
		

		// If not symmetry profile[3] is null, otherwise plot it.
	    if (profile.length>=4 && profile[3]!=null && sector.hasSeparateRegions()) {
	    	
			throw new OperationException(this, "Symmetry as separate dataset not currently supported!");
	    	
	    } else {
	    	return new OperationData(integral, mask, sector);
	    }

	}
	
}
