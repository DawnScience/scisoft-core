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
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.AbstractIntegrationOperation;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public class BoxIntegration extends AbstractIntegrationOperation<BoxIntegrationModel> {

	public enum Direction {
		X,Y,
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.boxIntegration";
	}

	
	@Override
    public String getName() {
		return "Box Integration";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		IDataset mask = null;
		try {
			ILazyDataset firstMask = getFirstMask(input);
			if (firstMask != null) mask = firstMask.getSlice();
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		RectangularROI rect = (RectangularROI)getRegion();
		
		
		final Dataset[] profile = ROIProfile.box((Dataset)input, (Dataset)mask, rect);
		
		Dataset x = profile[0];
		x.setName("Box X Profile "+rect.getName());
		
		Dataset y = profile[1];
		y.setName("Box Y Profile "+rect.getName());
		

		// If not symmetry profile[3] is null, otherwise plot it.
		OperationData ret = new OperationData(model.getDirection() == Direction.X ? x : y);
//	    ret.setAuxData(rect);

	    return ret;

	}
	


}
